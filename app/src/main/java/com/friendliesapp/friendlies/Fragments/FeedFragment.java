package com.friendliesapp.friendlies.Fragments;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.friendliesapp.friendlies.Model.Broadcast;
import com.friendliesapp.friendlies.Model.User;
import com.friendliesapp.friendlies.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment implements LocationListener {

    FirebaseDatabase firebase;

    LocationManager locationManager;
    String provider;
    Location currentLocation;

    final int locationUpdateTime = 5 * 1000 * 60;

    ArrayList<Broadcast> broadcasts = new ArrayList<Broadcast>();
    ArrayList<Map<String, Object>> broadcastKeys = new ArrayList<Map<String, Object>>();

    boolean hasLoadedBroadcasts = false;
    int receivedBroadcasts;

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebase = FirebaseDatabase.getInstance();

        beginFeedVC();

        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    public void beginFeedVC(){
        locationAuthStatus();
    }

    public void queryBroadcasts(){
        if (currentLocation != null){
            if (broadcasts.size() == 0) {
                //display loading circle
            }
            hasLoadedBroadcasts = true;

            GeoFire geofire = new GeoFire(firebase.getReference("geolocations"));

            Double radius;
            SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("com.friendliesapp.friendlies", Context.MODE_PRIVATE);
            radius = Double.parseDouble(String.valueOf(sharedPreferences.getFloat("SEARCH_RADIUS", 30)));
            Log.i("MYAPP", String.valueOf(radius));

            broadcastKeys.clear();
            broadcasts.clear();

            final GeoQuery circleQuery = geofire.queryAtLocation(new GeoLocation(currentLocation.getLatitude(), currentLocation.getLongitude()), radius);
            circleQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                @Override
                public void onKeyEntered(String key, GeoLocation location) {
                    Map<String, Object> broadcastKey = new HashMap<String, Object>();
                    broadcastKey.put("key", key);
                    broadcastKey.put("location", location);
                    broadcastKeys.add(broadcastKey);
                }
                @Override
                public void onKeyExited(String key) {

                }
                @Override
                public void onKeyMoved(String key, GeoLocation location) {

                }
                @Override
                public void onGeoQueryReady() {
                    circleQuery.removeAllListeners();
                    getBroadcastsFromKeys();
                }
                @Override
                public void onGeoQueryError(DatabaseError error) {

                }
            });
        }
    }

    public void getBroadcastsFromKeys(){
        receivedBroadcasts = 0;
        for (Map<String, Object> broadcastKey : broadcastKeys){
            if (String.valueOf(broadcastKey.get("key")) != null) {
                String key = String.valueOf(broadcastKey.get("key"));
                if (broadcastKey.get("location") != null) {
                    GeoLocation location = (GeoLocation) broadcastKey.get("location");
                    Log.i("MYAPP", key + " " + String.valueOf(location));
                    firebase.getReference("broadcasts").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Broadcast broadcast = dataSnapshot.getValue(Broadcast.class);
                            Log.i("MYAPP", broadcast.toString());

                            receivedBroadcasts++;

                            broadcast.getAuthor().downloadUserInfo(new User.OnDownloadFinishedListener() {
                                @Override
                                public void onDownloadFinished() {
                                    
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        }
    }

    public void locationAuthStatus(){
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request location
        } else {
            locationManager.requestLocationUpdates(provider, locationUpdateTime, 1000, this);
            currentLocation = locationManager.getLastKnownLocation(provider);
            if (currentLocation != null){
                onLocationChanged(currentLocation);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        if (!hasLoadedBroadcasts) {
            queryBroadcasts();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}