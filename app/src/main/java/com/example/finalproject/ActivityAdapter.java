package com.example.finalproject;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private ArrayList<myItemActivity> listItems;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;
    ActivityAdapter(Context context, ArrayList<myItemActivity> listItemsIn) {
        this.mInflater=LayoutInflater.from(context);
        this.listItems=listItemsIn;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =mInflater.inflate(R.layout.activity_view_recycler,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        myItemActivity activityItem = listItems.get(position);
        holder.nameActivity.setText(activityItem.getNameActivity());
        if(activityItem.getPhotoStatus()!=null)
            Glide.with(this.context).load(Uri.parse(activityItem.getPhotoStatus())).into(holder.photoStatus);
       // Log.d("Holder PhotoStatus is:","aa"+Uri.parse(listItems.get(position).getPhotoStatus()));
        holder.date.setText(activityItem.getStringDate());
        //holder.photoStatus.setImageResource(R.drawable.background);
        //holder.avatar.setImageResource(R.drawable.background);
        holder.status.setText(activityItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameActivity;
        ImageView avatar;
        ImageView photoStatus;
        TextView status;
        TextView date;
        ViewHolder(View itemView) {
            super(itemView);
            nameActivity = (TextView) itemView.findViewById(R.id.parsed_name);
            avatar=(ImageView) itemView.findViewById(R.id.parsed_avatar);
            photoStatus=(ImageView)itemView.findViewById(R.id.parsed_image);
            status=(TextView) itemView.findViewById(R.id.parsed_status);
            date=(TextView)itemView.findViewById(R.id.parsed_date);
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
