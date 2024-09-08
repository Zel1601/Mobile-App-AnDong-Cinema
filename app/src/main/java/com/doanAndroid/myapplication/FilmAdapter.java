package com.doanAndroid.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private List<Film> films;
    private OnFilmClickListener onFilmClickListener;

    public FilmAdapter(List<Film> films) {
        this.films = films;
    }

    public void setOnFilmClickListener(OnFilmClickListener listener) {
        this.onFilmClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Film film = films.get(position);
        holder.filmTitle.setText(film.getTitle());
        holder.filmImage.setImageResource(film.getImage()); // Cần thêm phương thức getImage trong class Film

        // Thêm sự kiện click cho mỗi item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onFilmClickListener != null) {
                    onFilmClickListener.onFilmClick(film);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView filmTitle;
        private ImageView filmImage;

        ViewHolder(View itemView) {
            super(itemView);
            filmTitle = itemView.findViewById(R.id.filmTitle);
            filmImage = itemView.findViewById(R.id.filmImage);
        }
    }

    // Interface để lắng nghe sự kiện click trên mỗi item
    public interface OnFilmClickListener {
        void onFilmClick(Film film);
    }
}
