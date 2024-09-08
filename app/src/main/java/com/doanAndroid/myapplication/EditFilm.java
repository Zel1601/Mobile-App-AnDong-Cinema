package com.doanAndroid.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditFilm extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView ivEditFilmItem;
    private EditText etEditedName;
    private EditText etEditedTime;
    private EditText etEditedDate;
    private EditText etEditedSeat;
    private EditText etEditedPrice;
    private EditText etEditedTheLoai;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_film);

        databaseHelper = new DatabaseHelper(this);
        ivEditFilmItem = findViewById(R.id.ivEditFilmItem);
        etEditedName = findViewById(R.id.etEditedName);
        etEditedTime = findViewById(R.id.etEditedTime);
        etEditedDate = findViewById(R.id.etEditedDate);
        etEditedSeat = findViewById(R.id.etEditedSeat);
        etEditedPrice = findViewById(R.id.etEditedPrice);
        etEditedTheLoai = findViewById(R.id.etEditedTheLoai);
        btnSave = findViewById(R.id.btnSave);

        String filmName = getIntent().getStringExtra("film_name");
        int filmTime = getIntent().getIntExtra("film_time", 0);
        String filmDate = getIntent().getStringExtra("film_Date");
        int filmGhe = getIntent().getIntExtra("film_ghe",0);
        float filmPrice = getIntent().getFloatExtra("film_price",0);
        int filmTheLoai = getIntent().getIntExtra("film_the_loai",0);


        etEditedName.setText(filmName);
        etEditedTime.setText(String.valueOf(filmTime));
        etEditedDate.setText(filmDate);
        etEditedSeat.setText(String.valueOf(filmGhe));
        etEditedPrice.setText(String.valueOf(filmPrice));
        etEditedTheLoai.setText(String.valueOf(filmTheLoai));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện lưu thông tin chỉnh sửa vào cơ sở dữ liệu hoặc nơi bạn lưu trữ dữ liệu
                saveEditedFilm();
            }
        });

        // Thiết lập sự kiện click cho ImageView để chọn ảnh
        ivEditFilmItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });
    }

    private void saveEditedFilm() {
        // Lấy dữ liệu từ EditText
        String name = etEditedName.getText().toString();
        int time = Integer.parseInt(etEditedTime.getText().toString());
        String Date = etEditedDate.getText().toString();
        int ghe = Integer.parseInt(etEditedSeat.getText().toString());
        float price = Float.parseFloat(etEditedPrice.getText().toString());
        int theloai = Integer.parseInt(etEditedTheLoai.getText().toString());

        // Thực hiện cập nhật thông tin chỉnh sửa vào cơ sở dữ liệu
        long result = databaseHelper.updateFilm(name,time,Date,ghe,price,theloai);

        if (result > 0) {
            // Gửi kết quả trở lại màn hình danh sách
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            // Xử lý lỗi (nếu cần)
            Toast.makeText(this, "Lỗi khi cập nhật thể loại", Toast.LENGTH_SHORT).show();
        }
    }


    // Phương thức để mở trình chọn ảnh
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Lấy đường dẫn của ảnh đã chọn
            Uri selectedImageUri = data.getData();
            String imagePath = getPathFromUri(selectedImageUri);

            // Hiển thị ảnh đã chọn trong ImageView
            // (Chưa triển khai, bạn cần thay thế dòng này bằng logic hiển thị ảnh)
            // ivEditCategoryItem.setImageURI(selectedImageUri);
        }
    }

    // Phương thức để lấy đường dẫn từ Uri
    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String imagePath = cursor.getString(column_index);
            cursor.close();
            return imagePath;
        }

        return null;
    }
}
