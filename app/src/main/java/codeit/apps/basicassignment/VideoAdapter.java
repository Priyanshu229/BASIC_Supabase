package codeit.apps.basicassignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    public List<videos> videos;
    public List<videos> filteredVideos;
    private Context context;
    private static OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(videos video);
    }

    public VideoAdapter(Context context, List<videos> videos, OnItemClickListener listener) {
        this.context = context;
        this.videos = videos;
        this.filteredVideos = new ArrayList<>(videos);
        this.listener = listener;
    }

    public void filter(String query) {
        filteredVideos.clear();
        if (TextUtils.isEmpty(query)) {
            filteredVideos.addAll(videos);
        } else {
            String lowerCaseQuery = query.toLowerCase(Locale.getDefault());
            for (videos video : videos) {
                if (video.getTitle().toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)) {
                    filteredVideos.add(video);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, likesTextView, channelTextView;
        ImageView thumbnailIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.rvTitle);
            likesTextView = itemView.findViewById(R.id.rvLikes);
            channelTextView = itemView.findViewById(R.id.rvChannel);
            thumbnailIV = itemView.findViewById(R.id.thumbnailImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(videos.get(position));
                    }

                }
            });
        }

        public void bind(final videos video, final OnItemClickListener listener) {
            Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(video.getVideourl(), MediaStore.Images.Thumbnails.MINI_KIND);
            if (thumbnail != null) {
                thumbnailIV.setImageBitmap(thumbnail);
            } else {
                // Handle case when thumbnail is null (e.g., no thumbnail generated)
                thumbnailIV.setImageResource(R.drawable.default_thumb); // Set a default image or handle null case accordingly
            }
            titleTextView.setText(video.getTitle());
            likesTextView.setText("likes : "+String.valueOf(video.getLikes()));
            channelTextView.setText(video.getChannelname());
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(videos.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }


}
