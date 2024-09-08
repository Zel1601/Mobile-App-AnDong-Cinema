package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TextView txtTenUser = findViewById(R.id.txttenuser);
        Button btnEditProfile = findViewById(R.id.btnEditprofile);
        Button btnDoiMK = findViewById(R.id.btnDoimk);
        Button btnDangXuat = findViewById(R.id.btnDangxuat);

        String tenNguoiDung = "Người Dùng";
        txtTenUser.setText(tenNguoiDung);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User.this, EditProfile.class));
            }
        });


        LinearLayout navigationLayout = findViewById(R.id.navigationLayout);
        Button btnAccount = findViewById(R.id.btnAccount);
        Button btnHome = findViewById(R.id.btnHome);
        Button btnBooking = findViewById(R.id.btnBooking);

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khi nhấn vào nút "Tài Khoản", mở UserActivity
                Intent intent = new Intent(User.this, User.class);
                startActivity(intent);
            }
        });

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, ListBookTicket.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Đổi Mật Khẩu"
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User.this, ChangePassword.class));
            }
        });

        // Xử lý sự kiện khi nhấn nút "Đăng Xuất"
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongbao();
            }
        });
    }
        private void thongbao() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác Nhận Đăng Xuất");
            builder.setMessage("Bạn chắc chắn muốn đăng xuất?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Thực hiện đăng xuất và chuyển về màn hình đăng nhập
                    backToLoginActivity();
                }
            });
            builder.setNegativeButton("Không", null);
            builder.show();
        }

        private void backToLoginActivity() {
            // Thực hiện chuyển về màn hình đăng nhập
            Intent intent = new Intent(User.this, Login.class);
            startActivity(intent);
            finish(); // Đóng User Activity để ngăn người dùng quay lại màn hình User sau khi đăng xuất
        }
    }
