package com.centura.videoplayer.playerActivity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.centura.videoplayer.R;
import com.centura.videoplayer.data.Models.VideoResponseModel;
import com.centura.videoplayer.playerActivity.PlayerActivityPresenterImpl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;


/**
 * Created by Manikandan Baskaran on 22-01-2019.
 */

public class RecentListAdapter extends RecyclerView.Adapter<RecentListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<VideoResponseModel> arrVideoResponseModel;

    private PlayerActivityPresenterImpl playerActivityPresenter;
    public RecentListAdapter(Context mContext, ArrayList<VideoResponseModel> arrVideoResponseModel
            , PlayerActivityPresenterImpl playerActivityPresenter) {
        this.mContext = mContext;
        this.arrVideoResponseModel = arrVideoResponseModel;
        this.playerActivityPresenter = playerActivityPresenter;
    }

    @NonNull
    @Override
    public RecentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.related_video_content, viewGroup, false);
        return new RecentListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentListAdapter.ViewHolder viewHolder, int i) {
        VideoResponseModel videoResponseModel = arrVideoResponseModel.get(i);

        viewHolder.tvTitle.setText(videoResponseModel.getTitle());
        viewHolder.tvDesc.setText(videoResponseModel.getDescription());
        Picasso.get().load(videoResponseModel.getThumb()).into(viewHolder.ivThumb);
        viewHolder.cvRecent.setOnClickListener(view ->
        {
            Log.d(TAG, "onBindViewHolder: " + i);
            playerActivityPresenter.onItemSelected(videoResponseModel.getId());

        });
    }

    @Override
    public int getItemCount() {
        return arrVideoResponseModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivThumb;
        private TextView tvTitle, tvDesc;
        private CardView cvRecent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.ivVideoThump);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            cvRecent = itemView.findViewById(R.id.cvRecent);
        }
    }
}
