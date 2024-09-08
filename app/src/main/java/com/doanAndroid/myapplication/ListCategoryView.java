package com.doanAndroid.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListCategoryView extends AppCompatActivity {
    private ListView listView;
    private CategorysAdapter categorysAdapter;
    private List<CategoryFilm> categoryFilms;
    private DatabaseHelper databaseHelper;
    private FloatingActionButton fabAddFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category_view);

        listView = findViewById(R.id.lvCategoryList);
        categoryFilms = new ArrayList<>();
        categorysAdapter = new CategorysAdapter(this, categoryFilms);
        listView.setAdapter(categorysAdapter);

        databaseHelper = new DatabaseHelper(this);

        // Thêm danh sách cố định vào danh sách thể loại
        categoryFilms.addAll(getCategory());

        // Hiển thị danh sách thể loại từ cơ sở dữ liệu
        categoryFilms.addAll(databaseHelper.getAllCategories());
        categorysAdapter.notifyDataSetChanged();

        fabAddFilm = findViewById(R.id.fabAddFilm);
        fabAddFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListCategoryView.this, AddCategory.class);
                startActivity(intent);
            }
        });

        // Sự kiện click cho mỗi mục trên danh sách
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý sự kiện khi một mục được chọn
                CategoryFilm selectedCategory = categorysAdapter.getItem(position);

                // Mở màn hình Edit với dữ liệu của mục đã chọn
                Intent editIntent = new Intent(ListCategoryView.this, EditCategory.class);
                editIntent.putExtra("category_id", selectedCategory.getId());
                startActivity(editIntent);
            }
        });

        // Sự kiện click cho nút Edit trên mỗi mục
        categorysAdapter.setOnEditClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                CategoryFilm selectedCategory = categorysAdapter.getItem(position);

                // Mở màn hình Edit với dữ liệu của mục đã chọn
                Intent editIntent = new Intent(ListCategoryView.this, EditCategory.class);
                editIntent.putExtra("category_id", selectedCategory.getId());
                startActivity(editIntent);
            }
        });

        // Sự kiện click cho nút Delete trên mỗi mục
        categorysAdapter.setOnDeleteClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                CategoryFilm selectedCategory = categorysAdapter.getItem(position);

                // Hiển thị hộp thoại xác nhận xóa
                showDeleteConfirmationDialog(selectedCategory);
            }
        });
    }

    public void updateCategoryList(CategoryFilm newCategory) {
        categoryFilms.add(newCategory);
        categorysAdapter.notifyDataSetChanged();
    }

    private void showDeleteConfirmationDialog(CategoryFilm category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa thể loại này?");

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xóa mục từ danh sách và cập nhật giao diện
                deleteCategory(category);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Đóng dialog nếu người dùng chọn Hủy
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void deleteCategory(CategoryFilm category) {
        // Xóa mục từ cơ sở dữ liệu
        long result = databaseHelper.deleteCategory(category.getId());

        if (result > 0) {
            // Xóa thành công, cập nhật danh sách thể loại và cập nhật giao diện
            categoryFilms.remove(category);
            categorysAdapter.notifyDataSetChanged();
            Toast.makeText(ListCategoryView.this, "Đã xóa thể loại", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ListCategoryView.this, "Lỗi khi xóa thể loại", Toast.LENGTH_SHORT).show();
        }
    }

    private List<CategoryFilm> getCategory() {
        List<CategoryFilm> categoryFilms = new ArrayList<>();
        categoryFilms.add(new CategoryFilm(3, "Gia đình", R.drawable.giadinh));
        categoryFilms.add(new CategoryFilm(4, "Kinh dị", R.drawable.kinhdi));
        categoryFilms.add(new CategoryFilm(2, "Hoạt hình", R.drawable.hoathinh));
        categoryFilms.add(new CategoryFilm(1, "Hành động", R.drawable.hanhdong));

        return categoryFilms;
    }

    @Override
    protected void onResume() {
        super.onResume();
        categoryFilms.clear();

        // Thêm danh sách cố định vào danh sách thể loại
        categoryFilms.addAll(getCategory());

        // Hiển thị danh sách thể loại từ cơ sở dữ liệu
        categoryFilms.addAll(databaseHelper.getAllCategories());
        categorysAdapter.notifyDataSetChanged();
    }
}
