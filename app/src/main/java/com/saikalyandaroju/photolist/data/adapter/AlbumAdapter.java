package com.saikalyandaroju.photolist.data.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.saikalyandaroju.photolist.R;
import com.saikalyandaroju.photolist.data.model.Albums;

import java.util.List;
import java.util.Random;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {
    private List<Albums> albums;
    private OnItemClickListener onItemClickListener;


    public void setAlbums(List<Albums> albums) {
        this.albums = albums;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        return new AlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {

        Albums album = albums.get(position);
        holder.cardView.setCardBackgroundColor(getRandomColor());
        holder.id.setText("Album Id : " + album.getId());
        holder.userId.setText("User Id : " + album.getUserId());
    }

    @Override
    public int getItemCount() {
        return albums != null ? albums.size() : 0;
    }

    public class AlbumHolder extends RecyclerView.ViewHolder {
        TextView id, userId;
        MaterialCardView cardView;

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.album_id);
            userId = itemView.findViewById(R.id.user_id);
            cardView=itemView.findViewById(R.id.albumcard);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(albums.get(position));
                    }
                }
            });
        }
    }
    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
    public interface OnItemClickListener {
        void onItemClick(Albums albums);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
