package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {

    private EditText edtPassword, edtNewPass;
    private Button btnXacNhan, btnBack;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Ánh xạ các thành phần giao diện
        edtPassword = findViewById(R.id.Password);
        edtNewPass = findViewById(R.id.NewPass);
        btnXacNhan = findViewById(R.id.btnxacnhan);
        btnBack = findViewById(R.id.btnBack);

        databaseHelper = new DatabaseHelper(this);

        // Xử lý sự kiện khi nút "Xác Nhận" được nhấn
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleChangePassword();
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

    private void handleChangePassword() {
        // Lấy tên người dùng từ SharedPreferences
        String username = getLoggedInUser();

        // Kiểm tra xem username có giá trị hợp lệ hay không
        if (!username.isEmpty()) {
            String oldPassword = edtPassword.getText().toString();
            String newPassword = edtNewPass.getText().toString();

            // Kiểm tra xem mật khẩu cũ có đúng không
            if (databaseHelper.checkOldPassword(username, oldPassword)) {
                // Thực hiện đổi mật khẩu và lưu mật khẩu mới vào cơ sở dữ liệu
                saveNewPassword(username, newPassword);

                // Hiển thị thông báo thành công
                Toast.makeText(ChangePassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                // Đóng Activity và quay lại màn hình trước đó
                finish();
            } else {
                // Hiển thị thông báo mật khẩu cũ không đúng
                Toast.makeText(ChangePassword.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Xử lý trường hợp username không hợp lệ
            Toast.makeText(ChangePassword.this, "Tên người dùng không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }

    // Hàm kiểm tra mật khẩu cũ
    private boolean checkOldPassword(String username, String enteredOldPassword) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String selection = DatabaseHelper.COL_USERNAME + "=? AND " + DatabaseHelper.COL_PASSWORD + "=?";
        String[] selectionArgs = {username, enteredOldPassword};
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Hàm lưu mật khẩu mới
    private void saveNewPassword(String username, String newPassword) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_PASSWORD, newPassword);
        db.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.COL_USERNAME + "=?", new String[]{username});
        db.close();
    }


    private String getLoggedInUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInUser", "");
    }



}
