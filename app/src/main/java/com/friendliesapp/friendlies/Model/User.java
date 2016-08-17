package com.friendliesapp.friendlies.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gordonseto on 16-08-17.
 */
public class User {

    private String facebookId;
    private Bitmap profilePhoto;
    private String displayName;
    private String uid;
    private String gamerTag;
    private ArrayList<String> characters;
    private Double lastAvailable;
    private Map<String, Boolean> friends;
    private Map<String, Boolean> wantsToAdd;
    private Map<String, String> wantsToBeAddedBy;
    private Map<String, Boolean> conversations;
    private Map<String, Boolean> followers;
    private Map<String, Boolean> isBlockedBy;
    private Map<String, Boolean> isBlocking;
    private Boolean onlyFriends;

    FirebaseDatabase firebase;

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public Bitmap getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(Bitmap profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGamerTag() {
        return gamerTag;
    }

    public void setGamerTag(String gamerTag) {
        this.gamerTag = gamerTag;
    }

    public ArrayList<String> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<String> characters) {
        this.characters = characters;
    }

    public Double getLastAvailable() {
        return lastAvailable;
    }

    public void setLastAvailable(Double lastAvailable) {
        this.lastAvailable = lastAvailable;
    }

    public Map<String, Boolean> getFriends() {
        return friends;
    }

    public void setFriends(Map<String, Boolean> friends) {
        this.friends = friends;
    }

    public Map<String, Boolean> getWantsToAdd() {
        return wantsToAdd;
    }

    public void setWantsToAdd(Map<String, Boolean> wantsToAdd) {
        this.wantsToAdd = wantsToAdd;
    }

    public Map<String, String> getWantsToBeAddedBy() {
        return wantsToBeAddedBy;
    }

    public void setWantsToBeAddedBy(Map<String, String> wantsToBeAddedBy) {
        this.wantsToBeAddedBy = wantsToBeAddedBy;
    }

    public Map<String, Boolean> getConversations() {
        return conversations;
    }

    public void setConversations(Map<String, Boolean> conversations) {
        this.conversations = conversations;
    }

    public Map<String, Boolean> getFollowers() {
        return followers;
    }

    public void setFollowers(Map<String, Boolean> followers) {
        this.followers = followers;
    }

    public Map<String, Boolean> getIsBlockedBy() {
        return isBlockedBy;
    }

    public void setIsBlockedBy(Map<String, Boolean> isBlockedBy) {
        this.isBlockedBy = isBlockedBy;
    }

    public Map<String, Boolean> getIsBlocking() {
        return isBlocking;
    }

    public void setIsBlocking(Map<String, Boolean> isBlocking) {
        this.isBlocking = isBlocking;
    }

    public Boolean getOnlyFriends() {
        return onlyFriends;
    }

    public void setOnlyFriends(Boolean onlyFriends) {
        this.onlyFriends = onlyFriends;
    }

    public Boolean shouldSeeLastAvailable = false;

    public User(String uid) {
        this.uid = uid;
    }

    public User(){}

    public void downloadUserInfo(final OnDownloadFinishedListener listener){
        if (uid == null) { return; }
        User myUser = this;
        firebase = FirebaseDatabase.getInstance();
        firebase.getReference("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                displayName = (String)dataSnapshot.child("displayName").getValue();
                if (displayName == null) { displayName = ""; }
                gamerTag = (String)dataSnapshot.child("gamerTag").getValue();
                if (gamerTag == null) { gamerTag = ""; }
                characters = (ArrayList<String>)dataSnapshot.child("characters").getValue();
                if (characters == null) { characters = new ArrayList<String>(); }
                facebookId = (String)dataSnapshot.child("facebookId").getValue();
                if (facebookId == null) { facebookId = "";}
                lastAvailable = (Double)dataSnapshot.child("lastAvailable").getValue();
                if (lastAvailable == null) { lastAvailable = null; }
                conversations = (Map<String, Boolean>)dataSnapshot.child("conversations").getValue();
                if (conversations == null) { new HashMap<String, Boolean>();}
                onlyFriends = (Boolean)dataSnapshot.child("onlyFriends").getValue();
                if (onlyFriends == null) { onlyFriends = false; }
                Log.i("MYAPP", "downloaded " + displayName);
                listener.onDownloadFinished();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
