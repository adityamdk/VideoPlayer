package com.example.mandyamd.videoplayer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mandyamd.videoplayer.R;
import com.example.mandyamd.videoplayer.activity.MainActivity;
import com.example.mandyamd.videoplayer.model.VideoData;

import java.util.List;


import com.squareup.picasso.Picasso;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {


    private List<VideoData> videos;

    private static Context mContext;
    private MainActivity.RecyclerViewClickListener mListener;

    public static class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MainActivity.RecyclerViewClickListener mListener;
        LinearLayout videoLayout;
        ImageView thumbnail;
        TextView title;
        TextView description;
        TextView duration;

        public VideoHolder(View itemView, MainActivity.RecyclerViewClickListener listener) {
            super(itemView);
            videoLayout = itemView.findViewById(R.id.videos_layout);
            thumbnail = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            duration = itemView.findViewById(R.id.duration);
            //  thumbnail.setOnClickListener((View.OnClickListener) this);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mListener.onClick(v, getAdapterPosition());
            // Intent myIntent = new Intent(mContext, Player.class);
            //v.getContext().startActivity(myIntent);
        }
    }

    public VideoAdapter(List<VideoData> video, Context context, MainActivity.RecyclerViewClickListener listener) {
        this.videos = video;
        mContext = context;
        mListener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int i) {

        String thumbnailUrl = videos.get(i).getThumbnail();
        Picasso.with(mContext)
                .load(thumbnailUrl)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.thumbnail);

        holder.title.setText("Title : "+videos.get(i).getTitle());
        holder.title.setTextColor(Color.RED);
        holder.description.setTextColor(Color.DKGRAY);
        holder.description.setText("Description : "+videos.get(i).getDescription());
    }


    @NonNull
    @Override
    public VideoAdapter.VideoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_video, viewGroup, false);
        return new VideoHolder(v, mListener);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}
