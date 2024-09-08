package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BookTicket extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);

        databaseHelper = new DatabaseHelper(this);

        String tenPhim = getIntent().getStringExtra("TEN_PHIM");
        int soGhe = getIntent().getIntExtra("SO_GHE", 0);
        float giaVe = getIntent().getFloatExtra("GIA_VE", 0);

        // Hiển thị dữ liệu lên giao diện
        TextView tvFilm = findViewById(R.id.tvFilm);
        TextView tvSoGhe = findViewById(R.id.tvSoGhe);
        TextView tvPrice = findViewById(R.id.tvPrice);


        tvFilm.setText("Tên Phim: " + tenPhim);
        tvSoGhe.setText("Số Ghế: " + soGhe);
        tvPrice.setText("Giá Vé: " + giaVe + " VNĐ");

        Button btnThanhToan = findViewById(R.id.btnThanhToan);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin từ EditText và Intent
                String tenPhim = getIntent().getStringExtra("TEN_PHIM");
                int soGhe = getIntent().getIntExtra("SO_GHE", 0);
                float giaVe = getIntent().getFloatExtra("GIA_VE", 0);

                // Lấy giá trị từ EditText
                EditText edtSoghe = findViewById(R.id.edtSoghe);
                String soGheDaNhap = edtSoghe.getText().toString();

                try {
                    // Chuyển đổi số ghế từ chuỗi sang số nguyên
                    int soGheNhap = Integer.parseInt(soGheDaNhap);

                    // Kiểm tra số ghế nhập có hợp lệ không
                    if (soGheNhap <= 0 || soGheNhap > soGhe) {
                        Toast.makeText(BookTicket.this, "Số ghế không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Tính giá trị cho totalPrice
                    float totalPrice = giaVe * soGheNhap;

                    BookTicketItem bookingItem = new BookTicketItem(tenPhim, soGheDaNhap, String.valueOf(totalPrice));

                    // Lưu thông tin đặt vé vào cơ sở dữ liệu
                    long result = databaseHelper.addBooking(bookingItem.getFilmName(), bookingItem.getSeatNumber(), bookingItem.getTotalPrice());

                    if (result != -1) {
                        Toast.makeText(BookTicket.this, "Đặt vé thành công", Toast.LENGTH_SHORT).show();

                        // Khởi tạo Intent để mở màn hình CheckOut và truyền tham số giá vé và số ghế
                        Intent checkOutIntent = new Intent(BookTicket.this, CheckOut.class);
                        checkOutIntent.putExtra("TEN_PHIM", tenPhim);
                        checkOutIntent.putExtra("GIA_VE", giaVe);
                        checkOutIntent.putExtra("SO_GHE_NHAP", soGheNhap);

                        // Thực hiện chuyển đến màn hình CheckOut
                        startActivity(checkOutIntent);
                    } else {
                        Toast.makeText(BookTicket.this, "Đặt vé không thành công", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(BookTicket.this, "Số ghế không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });


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