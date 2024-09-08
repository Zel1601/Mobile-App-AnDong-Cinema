package com.doanAndroid.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doanAndroid.myapplication.CategoryFilm;
import com.doanAndroid.myapplication.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<CategoryFilm> categories;
    private OnCategoryClickListener onCategoryClickListener;

    public CategoryAdapter(List<CategoryFilm> categories) {
        this.categories = categories;
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(CategoryFilm category);
    }

    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.onCategoryClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryFilm category = categories.get(position);
        holder.categoryName.setText(category.getName());
        holder.categoryImage.setImageResource(category.getImage());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        private ImageView categoryImage;

        ViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryImage = itemView.findViewById(R.id.categoryImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCategoryClickListener != null) {
                        onCategoryClickListener.onCategoryClick(categories.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}