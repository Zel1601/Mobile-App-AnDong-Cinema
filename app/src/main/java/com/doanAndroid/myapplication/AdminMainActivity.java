package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Button btnAddTheLoai = findViewById(R.id.btnAddTheLoai);
        Button btnAddFilm = findViewById(R.id.btnAddFilm);
        Button btnHome = findViewById(R.id.btnHome);
        Button btnDoanhThu = findViewById(R.id.btnDoanhThu);

        btnAddTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện chức năng khi nhấn vào nút "Add Category"
                // Ví dụ: Mở một activity mới để thêm thể loại
                Intent intent = new Intent(AdminMainActivity.this, ListCategoryView.class);
                startActivity(intent);
            }
        });


        // Sự kiện khi nhấn vào nút "Add Film"
        btnAddFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện chức năng khi nhấn vào nút "Add Film"
                // Ví dụ: Mở một activity mới để thêm phim
                Intent intent = new Intent(AdminMainActivity.this, ListFilmView.class);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện chức năng khi nhấn vào nút "Home"
                // Ví dụ: Mở trang chủ hoặc thực hiện hành động mong muốn
                Intent intent = new Intent(AdminMainActivity.this, AdminMainActivity.class);
                startActivity(intent);
            }
        });

        // Sự kiện khi nhấn vào nút "Doanh Thu"
        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện chức năng khi nhấn vào nút "Doanh Thu"
                // Ví dụ: Mở activity hiển thị thông tin doanh thu
                Intent intent = new Intent(AdminMainActivity.this, DoanhThuActivity.class);
                startActivity(intent);
            }
        });

    }
}