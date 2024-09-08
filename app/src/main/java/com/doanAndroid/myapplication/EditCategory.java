package com.doanAndroid.myapplication;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditCategory extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView ivEditCategoryItem;
    private EditText etEditCategoryId;
    private EditText etEditCategoryName;
    private Button btnSaveEdit;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        databaseHelper = new DatabaseHelper(this);

        ivEditCategoryItem = findViewById(R.id.ivEditCategoryItem);
        etEditCategoryId = findViewById(R.id.etEditCategoryId);
        etEditCategoryName = findViewById(R.id.etEditCategoryName);
        btnSaveEdit = findViewById(R.id.btnSaveEdit);

        // Lấy dữ liệu từ Intent (mã thể loại và tên thể loại được truyền từ màn hình danh sách)
        int categoryId = getIntent().getIntExtra("category_id", 0);
        String categoryName = getIntent().getStringExtra("category_name");

        // Hiển thị dữ liệu hiện tại trong EditText
        etEditCategoryId.setText(String.valueOf(categoryId));
        etEditCategoryName.setText(categoryName);

        // Thiết lập sự kiện click cho nút Lưu
        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện lưu thông tin chỉnh sửa vào cơ sở dữ liệu hoặc nơi bạn lưu trữ dữ liệu
                saveEditedCategory();
            }
        });

        // Thiết lập sự kiện click cho ImageView để chọn ảnh
        ivEditCategoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });
    }

    // Phương thức để lưu thông tin chỉnh sửa
    private void saveEditedCategory() {
        // Lấy dữ liệu từ EditText
        int editedCategoryId = Integer.parseInt(etEditCategoryId.getText().toString());
        String editedCategoryName = etEditCategoryName.getText().toString();

        // Thực hiện cập nhật thông tin chỉnh sửa vào cơ sở dữ liệu
        long result = databaseHelper.updateCategory(editedCategoryId, editedCategoryName);

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

    // Xử lý kết quả trả về từ trình chọn ảnh
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
