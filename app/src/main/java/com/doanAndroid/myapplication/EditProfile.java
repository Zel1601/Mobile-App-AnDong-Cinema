package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity {

    private EditText edtHo, edtTen, edtEmail, edtBirth, edtSDT;
    private Button btnSave, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Ánh xạ các thành phần giao diện
        edtHo = findViewById(R.id.EdtHo);
        edtTen = findViewById(R.id.EdtTen);
        edtEmail = findViewById(R.id.EdtEmail);
        edtBirth = findViewById(R.id.EdtBirth);
        edtSDT = findViewById(R.id.EdtSDT);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        // Xử lý sự kiện khi nút "Lưu" được nhấn
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileData();
            }
        });

        // Xử lý sự kiện khi nút "Trở Lại" được nhấn
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity và quay trở lại màn hình trước đó
            }
        });
    }

    private void saveProfileData() {
        // Lấy dữ liệu từ các EditText
        String ho = edtHo.getText().toString();
        String ten = edtTen.getText().toString();
        String email = edtEmail.getText().toString();
        String birth = edtBirth.getText().toString();
        String sdt = edtSDT.getText().toString();

        // Gọi hàm để lưu dữ liệu xuống database (cần triển khai hàm này trong DatabaseHelper)
        saveDataToDatabase(ho, ten, email, birth, sdt);

        // Chuyển đến Activity ViewProfile và truyền dữ liệu
        Intent intent = new Intent(EditProfile.this, ViewProfile.class);
        intent.putExtra("ho", ho);
        intent.putExtra("ten", ten);
        intent.putExtra("email", email);
        intent.putExtra("birth", birth);
        intent.putExtra("sdt", sdt);
        startActivity(intent);
    }

    // Hàm này cần được triển khai trong DatabaseHelper
    private void saveDataToDatabase(String ho, String ten, String email, String birth, String sdt) {
        // Lấy tên người dùng từ SharedPreferences
        String username = getUsernameFromSharedPreferences();

        // Kiểm tra xem tên người dùng có tồn tại hay không
        if (!username.isEmpty()) {
            // Gọi hàm trong DatabaseHelper để cập nhật thông tin người dùng
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.updateUserInfo(username, ho, ten, email, birth, sdt);
            // Đóng DatabaseHelper sau khi sử dụng để tránh rò rỉ bộ nhớ
            databaseHelper.close();
        } else {
            // Hiển thị thông báo lỗi nếu không tìm thấy tên người dùng
            Toast.makeText(this, "Username not found in SharedPreferences", Toast.LENGTH_SHORT).show();
        }
    }

    private String getUsernameFromSharedPreferences() {
        // Triển khai logic lấy tên người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }
}
