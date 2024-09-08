package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheckOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        float giaVe = getIntent().getFloatExtra("GIA_VE", 0);
        int soGheNhap = getIntent().getIntExtra("SO_GHE_NHAP", 0);

        // Hiển thị thông tin trên giao diện
        TextView tvFilm = findViewById(R.id.tvFilm);
        TextView tvSum = findViewById(R.id.tvSum);

        // Tính tổng tiền
        float tongTien = giaVe * soGheNhap;

        // Hiển thị thông tin
        tvFilm.setText("Tên Film: " + getIntent().getStringExtra("TEN_PHIM"));
        tvSum.setText("Tổng Tiền: " + tongTien + " VNĐ");

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Đóng Activity khi nút trở lại được nhấn
                finish();
            }
        });
    }
}