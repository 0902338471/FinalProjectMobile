package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private ArrayList<myItemActivity> listItems;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    ActivityAdapter(Context context, ArrayList<myItemActivity> listItemsIn) {
        this.mInflater=LayoutInflater.from(context);
        this.listItems=listItemsIn;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =mInflater.inflate(R.layout.myitemactivity,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        myItemActivity activityItem = listItems.get(position);
        holder.nameActivity.setText(activityItem.getNameActivity());
        holder.photoStatus.setImageResource(R.drawable.background);
        holder.avatar.setImageResource(R.drawable.background);
        holder.status.setText(activityItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        EditText nameActivity;
        ImageView avatar;
        ImageView photoStatus;
        EditText status;
        ViewHolder(View itemView) {
            super(itemView);
            nameActivity = (EditText) itemView.findViewById(R.id.nameActivity);
            avatar=(ImageView) itemView.findViewById(R.id.avartar);
            photoStatus=(ImageView)itemView.findViewById(R.id.content_image);
            status=(EditText)itemView.findViewById(R.id.status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    myItemActivity getItem(int id)
    {
        return listItems.get(id);
    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
