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

public class CategorysAdapter extends BaseAdapter {
    private List<CategoryFilm> categoryFilms;
    private LayoutInflater inflater;
    private View.OnClickListener editClickListener;
    private View.OnClickListener deleteClickListener;

    public CategorysAdapter(Context context, List<CategoryFilm> categoryFilms) {
        this.categoryFilms = categoryFilms;
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
        return categoryFilms.size();
    }

    @Override
    public CategoryFilm getItem(int position) {
        return categoryFilms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.item_list_category, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivCategoryItem = view.findViewById(R.id.ivCategoryItem);
            viewHolder.tvIdCategory = view.findViewById(R.id.tvIdCategory);
            viewHolder.tvNameCategory = view.findViewById(R.id.tvNameCategory);
            viewHolder.btnEdit = view.findViewById(R.id.btnEdit);
            viewHolder.btnDelete = view.findViewById(R.id.btnDelete);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        CategoryFilm categoryFilm = categoryFilms.get(position);
        viewHolder.ivCategoryItem.setImageResource(categoryFilm.getImage());
        viewHolder.tvIdCategory.setText("Mã Thể Loại :"+String.valueOf(categoryFilm.getId()));
        viewHolder.tvNameCategory.setText("Tên Thể Loại :"+ categoryFilm.getName());

        // Lưu vị trí của mục trong danh sách cho cả btnEdit và btnDelete
        viewHolder.btnEdit.setTag(position);
        viewHolder.btnDelete.setTag(position);

        // Xử lý sự kiện khi nút Edit được nhấn
        viewHolder.btnEdit.setOnClickListener(editClickListener);

        // Xử lý sự kiện khi nút Delete được nhấn
        viewHolder.btnDelete.setOnClickListener(deleteClickListener);

        return view;
    }

    static class ViewHolder {
        ImageView ivCategoryItem;
        TextView tvIdCategory;
        TextView tvNameCategory;
        ImageButton btnEdit;
        ImageButton btnDelete;
    }
}
