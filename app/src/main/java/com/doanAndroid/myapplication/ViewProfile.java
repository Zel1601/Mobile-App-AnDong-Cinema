package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Button btnBack = findViewById(R.id.btnBack);

        // Ánh xạ các thành phần giao diện
        TextView txtHo = findViewById(R.id.txtHo);
        TextView txTen = findViewById(R.id.txtTen);
        TextView txtEmail = findViewById(R.id.txtEmail);
        TextView txtNgaySinh = findViewById(R.id.txtNgaySinh);
        TextView txtSDT = findViewById(R.id.txtSDT);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String ho = intent.getStringExtra("ho");
        String ten = intent.getStringExtra("ten");
        String email = intent.getStringExtra("email");
        String birth = intent.getStringExtra("birth");
        String sdt = intent.getStringExtra("sdt");

        // Hiển thị dữ liệu trong TextView
        txtHo.setText(ho);
        txTen.setText(ten);
        txtEmail.setText(email);
        txtNgaySinh.setText(birth);
        txtSDT.setText(sdt);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewProfile.this, User.class));
            }
        });
    }
}