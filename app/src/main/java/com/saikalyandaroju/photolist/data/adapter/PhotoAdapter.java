package com.saikalyandaroju.photolist.data.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.saikalyandaroju.photolist.R;
import com.saikalyandaroju.photolist.data.model.AlbumPhotos;
import com.saikalyandaroju.photolist.data.model.Albums;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> implements Filterable {

    private List<AlbumPhotos> albumPhotos;
    private List<AlbumPhotos> duplicateAlbumPhotos;
    private OnItemClickListener onItemClickListener;


    public void setAlbumPhotos(List<AlbumPhotos> albumPhotos) {
        this.albumPhotos = albumPhotos;
        this.duplicateAlbumPhotos = new ArrayList<>(albumPhotos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        AlbumPhotos albumPhoto = albumPhotos.get(position);
        Picasso.get().load(albumPhoto.getThumbnailUrl()).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground).into(holder.thumb);
        holder.title.setText("Title : " + albumPhoto.getTitle());
        holder.albumId.setText("Album Id :" + albumPhoto.getAlbumId());
    }

    @Override
    public int getItemCount() {
        return albumPhotos != null ? albumPhotos.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<AlbumPhotos> filteredlist = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredlist.addAll(duplicateAlbumPhotos);
            } else {
                String filterpattern = constraint.toString().toLowerCase().trim();
                for (AlbumPhotos c : duplicateAlbumPhotos) {
                    if (c.getTitle().toLowerCase().trim().contains(filterpattern)) {
                        filteredlist.add(c);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredlist;
            results.count = filteredlist.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (!TextUtils.isEmpty(constraint)) {
                albumPhotos.clear();
                albumPhotos.addAll((ArrayList<AlbumPhotos>) results.values);
            } else {
                albumPhotos.clear();
                albumPhotos.addAll(duplicateAlbumPhotos);
            }

            notifyDataSetChanged();
        }
    };
    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
    public class PhotoHolder extends RecyclerView.ViewHolder {
        TextView title, albumId;
        ImageView thumb;


        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            albumId = itemView.findViewById(R.id.albumid);
            thumb = itemView.findViewById(R.id.thumbnail);

            thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(albumPhotos.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(AlbumPhotos albumPhotos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
