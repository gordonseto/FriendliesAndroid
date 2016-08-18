package com.friendliesapp.friendlies.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.friendliesapp.friendlies.Holders.BroadcastViewHolder;
import com.friendliesapp.friendlies.Model.Broadcast;

import java.util.ArrayList;
import com.friendliesapp.friendlies.R;

/**
 * Created by gordonseto on 16-08-17.
 */
public class BroadcastsAdapter extends RecyclerView.Adapter<BroadcastViewHolder> {

    private ArrayList<Broadcast> broadcasts;

    public BroadcastsAdapter(ArrayList<Broadcast> broadcasts) {
        this.broadcasts = broadcasts;
    }

    @Override
    public BroadcastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View broadcastCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_broadcast, parent, false);

        return new BroadcastViewHolder(broadcastCard);
    }

    @Override
    public void onBindViewHolder(BroadcastViewHolder holder, int position) {

        Broadcast broadcast = broadcasts.get(position);
        holder.configureCell(broadcast);
    }

    @Override
    public int getItemCount() {
        return broadcasts.size();
    }
}
