package com.friendliesapp.friendlies.Model;

import android.location.Location;

import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;

/**
 * Created by gordonseto on 16-08-17.
 */
public class Broadcast {

    private String key;
    private String authorUid;
    private User author;
    private GeoLocation geolocation;
    private Double time;
    private Boolean hasSetup = false;
    private String broadcastDesc = "";
    private User user;

    FirebaseDatabase firebase;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAuthorUid() {
        return authorUid;
    }

    public void setAuthorUid(String authorUid) {
        this.authorUid = authorUid;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public GeoLocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(GeoLocation geolocation) {
        this.geolocation = geolocation;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Boolean getHasSetup() {
        return hasSetup;
    }

    public void setHasSetup(Boolean hasSetup) {
        this.hasSetup = hasSetup;
    }

    public String getBroadcastDesc() {
        return broadcastDesc;
    }

    public void setBroadcastDesc(String broadcastDesc) {
        this.broadcastDesc = broadcastDesc;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Broadcast(String key, String authorUid, String broadcastDesc, Boolean hasSetup, GeoLocation geolocation, Double time){
        this.key = key;
        this.authorUid = authorUid;
        this.broadcastDesc = broadcastDesc;
        this.hasSetup = hasSetup;
        this.geolocation = geolocation;
        this.time = time;
    }

    public Broadcast(){}
}