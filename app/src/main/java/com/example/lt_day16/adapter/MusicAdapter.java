package com.example.lt_day16.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lt_day16.R;
import com.example.lt_day16.callback.OnMusicItemClickListener;
import com.example.lt_day16.model.Music;
import com.example.lt_day16.ultil.Const;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private Context context;
    private List<Music> arrMusic;
    private OnMusicItemClickListener callback;

    public MusicAdapter(Context context, List<Music> arrMusic, OnMusicItemClickListener callback) {
        this.context = context;
        this.arrMusic = arrMusic;
        this.callback = callback;
    }

    public MusicAdapter(int rv_music) {
    }

    @NonNull
    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.ViewHolder holder, final int position) {
        Music music = arrMusic.get(position);
        holder.tvAuthor.setText(music.getAlbum());
        holder.tvName.setText(music.getTitle());

        Glide.with(context).
                load(Const.BASE_URL + music.getImage()).
                placeholder(R.mipmap.ic_launcher)// khi ảnh chưa load được thì lấy ảnh này
                .into(holder.imgAvt);// đây là ảnh khi load thành công
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onMusicItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrMusic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvt;
        private TextView tvName, tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvt = itemView.findViewById(R.id.img_avt);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAuthor = itemView.findViewById(R.id.tv_author);
        }
    }
}
