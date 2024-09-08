package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button registerButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.Username);
        passwordEditText = findViewById(R.id.Password);
        registerButton = findViewById(R.id.Register);

        databaseHelper = new DatabaseHelper(this);

        ImageButton backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại trang trước đó khi nhấn nút quay lại
                onBackPressed();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                long result = databaseHelper.addUser(username, password);

                if (result > 0) {
                    // Lưu trạng thái đã đăng ký vào SharedPreferences
                    saveUserRegisteredStatus();

                    Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    // Chuyển đến giao diện người dùng sau khi đăng ký thành công
                    navigateToMainActivity();
                } else {
                    Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveUserRegisteredStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("userRegistered", true);
        editor.apply();
    }


    private void navigateToMainActivity() {
        // Chuyển đến giao diện người dùng sau khi đăng ký thành công
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
        finish();  // Đóng activity hiện tại để không quay lại nó khi nhấn nút back
    }
}
