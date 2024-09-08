package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFilm extends AppCompatActivity {

    private EditText edtName;
    private EditText edtTime;
    private EditText edtDate;
    private EditText edtGhe;
    private EditText edtPrice;
    private EditText edtTheLoai;

    private Button btnAddFilm;
    private DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);

        edtName = findViewById(R.id.edtName);
        edtTime = findViewById(R.id.edtTime);
        edtDate = findViewById(R.id.edtDate);
        edtGhe = findViewById(R.id.edtGhe);
        edtPrice = findViewById(R.id.edtPrice);
        edtTheLoai = findViewById(R.id.edtTheLoai);
        btnAddFilm = findViewById(R.id.btnAddFilm);
        databaseHelper = new DatabaseHelper(this);

        btnAddFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                int time =Integer.parseInt(edtTime.getText().toString());
                String date = edtDate.getText().toString();
                int ghe = Integer.parseInt(edtGhe.getText().toString());
                float price = Float.parseFloat(edtPrice.getText().toString());
                int theloai = Integer.parseInt(edtTheLoai.getText().toString());

                long result = databaseHelper.addFilm(name,time, date, ghe,price,theloai);

                if (result != -1) {
                    // Thêm thành công, cập nhật danh sách thể loại
                    Film newFilm = new Film(name, R.drawable.family1, time, theloai, date, ghe, price);
                    updateFilmList(newFilm);

                    // Đóng hoạt động
                    finish();
                } else {
                    // Hiển thị thông báo lỗi nếu cần
                    Toast.makeText(AddFilm.this, "Lỗi khi thêm thể loại", Toast.LENGTH_SHORT).show();
                }
            }

            private void updateFilmList(Film newFilm) {
                ListFilmView parentActivity  = (ListFilmView) getParent();
                if(parentActivity != null)
                {
                    parentActivity.updateFilmList(newFilm);
                }
            }
        });
    }
}