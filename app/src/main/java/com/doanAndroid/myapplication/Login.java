package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Login extends AppCompatActivity {


    private EditText usernameEditText, passwordEditText;
    private RadioButton adminRadioButton, userRadioButton;
    private Button loginButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.Username);
        passwordEditText = findViewById(R.id.Password);
        adminRadioButton = findViewById(R.id.AdminSta);
        userRadioButton = findViewById(R.id.UserSta);
        loginButton = findViewById(R.id.Login);

        databaseHelper = new DatabaseHelper(this);

        Button registerButton = findViewById(R.id.Register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển đến Activity Register
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (adminRadioButton.isChecked()) {
                    if (databaseHelper.isValidAdminLogin(username, password)) {
                        // Đăng nhập thành công, điều hướng đến giao diện admin
                        // Add code để chuyển đến giao diện admin ở đây
                        Intent intent = new Intent(Login.this, AdminMainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Login.this, "Admin login successful", Toast.LENGTH_SHORT).show();
                        saveLoggedInUser(username);
                    } else {
                        Toast.makeText(Login.this, "Invalid admin credentials", Toast.LENGTH_SHORT).show();
                    }
                } else if (userRadioButton.isChecked()) {
                    if (databaseHelper.isValidUserLogin(username, password)) {
                        // Đăng nhập cho người dùng
                        // Add code để xử lý đăng nhập cho người dùng ở đây
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        saveLoggedInUser(username);
                    } else {
                        Toast.makeText(Login.this, "Inv alid user credentials", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
    private boolean isUserRegistered() {
        // Kiểm tra trạng thái đã đăng ký từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("userRegistered", false);
    }

    private void saveLoggedInUser(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("loggedInUser", username);
        editor.apply();
    }


}