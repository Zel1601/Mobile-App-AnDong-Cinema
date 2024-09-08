package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class DoanhThuActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu);

        databaseHelper = new DatabaseHelper(this);

        // Lấy danh sách đặt vé từ cơ sở dữ liệu
        ArrayList<BookTicketItem> bookingList = databaseHelper.getAllBookings();

        // Hiển thị số lượt đặt vé
        TextView tvDoanhThuTitle = findViewById(R.id.tvDoanhThuTitle);
        tvDoanhThuTitle.setText("Doanh thu - Số lượt đặt vé: " + bookingList.size());

        // Tính tổng số tiền
        float totalRevenue = 0;
        for (BookTicketItem bookingItem : bookingList) {
            totalRevenue += Float.parseFloat(bookingItem.getTotalPrice());
        }

        // Hiển thị tổng số tiền
        TextView tvDoanhThuInfo = findViewById(R.id.tvDoanhThuInfo);
        tvDoanhThuInfo.setText("Tổng số tiền: " + totalRevenue + " VNĐ");
    }
}
