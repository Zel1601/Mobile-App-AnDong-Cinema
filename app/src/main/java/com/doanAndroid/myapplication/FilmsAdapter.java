package com.doanAndroid.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FilmsAdapter extends BaseAdapter {
    private List<Film> films;
    private LayoutInflater inflater;
    private View.OnClickListener editClickListener;
    private View.OnClickListener deleteClickListener;

    // Corrected constructor name
    public FilmsAdapter(Context context, List<Film> films) {
        this.films = films;
        this.inflater = LayoutInflater.from(context);
    }

    public void setOnEditClickListener(View.OnClickListener listener) {
        this.editClickListener = listener;
    }

    public void setOnDeleteClickListener(View.OnClickListener listener) {
        this.deleteClickListener = listener;
    }

    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public Object getItem(int position) {
        return films.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void removeItem(Film film) {
        films.remove(film);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.item_list_film, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivFilmItem = view.findViewById(R.id.ivFilmItem);
            viewHolder.tvNameFilm = view.findViewById(R.id.tvNameFilm);
            viewHolder.tvTimeFilm = view.findViewById(R.id.tvTimeFilm);
            viewHolder.tvDateFilm = view.findViewById(R.id.tvDateFilm);
            viewHolder.tvGheFilm = view.findViewById(R.id.tvGheFilm);
            viewHolder.tvPriceFilm = view.findViewById(R.id.tvPriceFilm);
            viewHolder.tvTheLoaiFilm = view.findViewById(R.id.tvTheLoaiFilm);
            viewHolder.btnEdit = view.findViewById(R.id.btnEdit);
            viewHolder.btnDelete = view.findViewById(R.id.btnDelete);
            // ... (initialize other views)

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Film film = films.get(position);
        viewHolder.ivFilmItem.setImageResource(film.getImage());
        viewHolder.tvNameFilm.setText(film.getTitle());
        viewHolder.tvTimeFilm.setText("Thời Lượng :" + String.valueOf(film.getTimeFilm()));
        viewHolder.tvDateFilm.setText("Khởi Chiếu :"+film.getDate());
        viewHolder.tvGheFilm.setText("Số Ghế :" +String.valueOf(film.getSoghe()));
        viewHolder.tvPriceFilm.setText("Giá :"+String.valueOf(film.getPrice()));
        viewHolder.tvTheLoaiFilm.setText("Thể Loại"+String.valueOf(film.getTheloai()));
        // ... (set other data)

        viewHolder.btnEdit.setTag(position);
        viewHolder.btnDelete.setTag(position);

        // Xử lý sự kiện khi nút Edit được nhấn
        viewHolder.btnEdit.setOnClickListener(editClickListener);

        // Xử lý sự kiện khi nút Delete được nhấn
        viewHolder.btnDelete.setOnClickListener(deleteClickListener);

        return view;
    }

    static class ViewHolder {
        ImageView ivFilmItem;
        TextView tvNameFilm;
        TextView tvTimeFilm;
        TextView tvDateFilm;
        TextView tvGheFilm;
        TextView tvPriceFilm;
        TextView tvTheLoaiFilm;

        ImageButton btnEdit;
        ImageButton btnDelete;
        // ... (other views)
    }
}
