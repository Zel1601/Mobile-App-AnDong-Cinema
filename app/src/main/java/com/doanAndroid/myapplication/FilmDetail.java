package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FilmDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        Intent intent = getIntent();
        String filmTitle = intent.getStringExtra("FILM_TITLE");
        int filmImage = intent.getIntExtra("FILM_IMAGE", 0);
        int filmTime = intent.getIntExtra("FILM_TIME", 0);
        int filmSoGhe = intent.getIntExtra("SOGHE",0);
        int filmTheloai = intent.getIntExtra("FILM_THELOAI", 0);
        String filmDate = intent.getStringExtra("FILM_DATE");
        float filmGiave = intent.getFloatExtra("FILM_PRICE", 0.0f);

        // Hiển thị dữ liệu trên giao diện
        ImageView ivAnh = findViewById(R.id.ivAnh);
        TextView tenphim = findViewById(R.id.tenphim);
        TextView soghe = findViewById(R.id.soghe);
        TextView thoigian = findViewById(R.id.thoigian);
        TextView ngaychieu = findViewById(R.id.ngaychieu);
        TextView theloai = findViewById(R.id.theloai);

        ivAnh.setImageResource(filmImage);
        tenphim.setText(filmTitle);
        soghe.setText("Số Ghế: "); // Thêm xử lý nếu có thông tin về số ghế
        thoigian.setText("Thời Gian: " + filmTime + " phút");
        soghe.setText("Số Ghế :" +filmSoGhe);
        ngaychieu.setText("Khởi Chiếu: " + filmDate);
        theloai.setText("Thể Loại: " + filmTheloai); // Thêm xử lý nếu có thông tin về thể loại

        // Thêm sự kiện cho nút đặt vé
        Button btnbook = findViewById(R.id.btnbook);
        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent bookTicketIntent = new Intent(FilmDetail.this, BookTicket.class);
                bookTicketIntent.putExtra("TEN_PHIM", filmTitle);
                bookTicketIntent.putExtra("SO_GHE", filmSoGhe);
                bookTicketIntent.putExtra("GIA_VE", filmGiave);
                startActivity(bookTicketIntent);
            }
        });

        // Thêm sự kiện cho nút trở lại
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