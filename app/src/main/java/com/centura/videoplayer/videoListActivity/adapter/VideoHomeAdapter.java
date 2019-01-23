package com.centura.videoplayer.videoListActivity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.centura.videoplayer.R;
import com.centura.videoplayer.data.Models.VideoResponseModel;
import com.centura.videoplayer.videoListActivity.HomePresenterImpl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Manikandan Baskaran on 19-01-2019.
 */
public class VideoHomeAdapter extends RecyclerView.Adapter<VideoHomeAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<VideoResponseModel> arrVideoResponseModel;
    private HomePresenterImpl homePresenter;

    public VideoHomeAdapter(Context context, ArrayList<VideoResponseModel> videoResponseModels, HomePresenterImpl homePresenter) {
        this.mContext = context;
        this.arrVideoResponseModel = videoResponseModels;
        this.homePresenter = homePresenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_content_layout_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        VideoResponseModel videoResponseModel = arrVideoResponseModel.get(i);

        viewHolder.tvTitle.setText(videoResponseModel.getTitle());
        viewHolder.tvDesc.setText(videoResponseModel.getDescription());
        Picasso.get().load(videoResponseModel.getThumb()).into(viewHolder.ivVideoThump);
        viewHolder.cvVideo.setOnClickListener(view -> homePresenter.itemClicked(i, videoResponseModel));
    }

    @Override
    public int getItemCount() {
        return arrVideoResponseModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivVideoThump;
        private TextView tvTitle, tvDesc;
        private CardView cvVideo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivVideoThump = itemView.findViewById(R.id.ivVideoThump);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            cvVideo = itemView.findViewById(R.id.cvVideo);
        }
    }
}
