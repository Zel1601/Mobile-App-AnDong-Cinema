package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCategory extends AppCompatActivity {
    private EditText etCategoryCode;
    private EditText etCategoryName;
    private Button btnAddCategory;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        etCategoryCode = findViewById(R.id.etCategoryCode);
        etCategoryName = findViewById(R.id.etCategoryName);
        btnAddCategory = findViewById(R.id.btnAddCategory);
        databaseHelper = new DatabaseHelper(this);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int categoryId = Integer.parseInt(etCategoryCode.getText().toString());
                String categoryName = etCategoryName.getText().toString();

                // Thêm thông tin vào cơ sở dữ liệu
                long result = databaseHelper.addCategory(categoryId, categoryName);

                if (result != -1) {
                    // Thêm thành công, cập nhật danh sách thể loại
                    CategoryFilm newCategory = new CategoryFilm(categoryId, categoryName, R.drawable.hanhdong1);
                    updateCategoryList(newCategory);

                    // Đóng hoạt động
                    finish();
                } else {
                    // Hiển thị thông báo lỗi nếu cần
                    Toast.makeText(AddCategory.this, "Lỗi khi thêm thể loại", Toast.LENGTH_SHORT).show();
                }
            }

            // Thêm phương thức để cập nhật danh sách thể loại
            private void updateCategoryList(CategoryFilm newCategory) {
                ListCategoryView parentActivity = (ListCategoryView) getParent();
                if (parentActivity != null) {
                    parentActivity.updateCategoryList(newCategory);
                }
            }
        });
    }
}