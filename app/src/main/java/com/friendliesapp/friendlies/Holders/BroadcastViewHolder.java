package com.friendliesapp.friendlies.Holders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.friendliesapp.friendlies.Model.Broadcast;
import com.friendliesapp.friendlies.Model.User;
import com.friendliesapp.friendlies.R;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by gordonseto on 16-08-17.
 */
public class BroadcastViewHolder extends RecyclerView.ViewHolder {

    private TextView displayName;
    private TextView gamerTag;
    private ImageView userPhoto;
    private LinearLayout characterLinearLayout;
    private EditText broadcastDesc;
    private TextView timeLabel;
    private TextView distanceLabel;
    private TextView setupLabel;
    private Switch setupSwitch;
    private ImageView removeButton;

    Broadcast broadcast;
    FirebaseDatabase firebase;

    final int MAX_TEXT = 80;

    Boolean isAuthor = false;

    public BroadcastViewHolder(View itemView) {
        super(itemView);

        displayName = (TextView)itemView.findViewById(R.id.displayName);
        gamerTag = (TextView)itemView.findViewById(R.id.gamerTag);
        userPhoto = (ImageView)itemView.findViewById(R.id.userPhoto);
        characterLinearLayout = (LinearLayout)itemView.findViewById(R.id.characterLinearLayout);
        broadcastDesc = (EditText) itemView.findViewById(R.id.broadcastDesc);
        timeLabel = (TextView)itemView.findViewById(R.id.timeLabel);
        distanceLabel = (TextView)itemView.findViewById(R.id.distanceLabel);
        setupLabel = (TextView)itemView.findViewById(R.id.setupLabel);
        setupSwitch = (Switch)itemView.findViewById(R.id.setupSwitch);
        removeButton = (ImageView)itemView.findViewById(R.id.removeButton);
    }

    public void configureCell(Broadcast broadcast){
        if (broadcast.getUser() != null) {
            this.broadcast = broadcast;

            displayName.setText(broadcast.getUser().getDisplayName());
            gamerTag.setText(broadcast.getUser().getGamerTag());
            broadcastDesc.setText(broadcast.getBroadcastDesc());
            timeLabel.setText(String.valueOf(broadcast.getTime()));

            arrangeLinearLayoutCharacters(broadcast.getUser(), characterLinearLayout);
        }
    }

    public void arrangeLinearLayoutCharacters(User user, LinearLayout linearLayout){
        linearLayout.removeAllViews();

        if (user.getCharacters() != null) {
            for (String character : user.getCharacters()){
                Log.i("MYAPP", "adding character");
                ImageView imageView = new ImageView(linearLayout.getContext());
                imageView.setImageResource(linearLayout.getContext().getResources().getIdentifier("image" + character, "drawable", linearLayout.getContext().getPackageName()));
                linearLayout.addView(imageView);
            }
        }
    }
}
