package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class ListBookTicket extends AppCompatActivity {

    // Khai báo đối tượng BookingDataSource
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_ticket);

        databaseHelper = new DatabaseHelper(this);
        ArrayList<BookTicketItem> bookingList = databaseHelper.getAllBookings();

        displayBookTicketList(bookingList);
    }


    private void displayBookTicketList(ArrayList<BookTicketItem> list) {
        ArrayAdapter<BookTicketItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        ListView listView = findViewById(R.id.Danhsachdatve);
        listView.setAdapter(adapter);

        LinearLayout navigationLayout = findViewById(R.id.navigationLayout);
        Button btnAccount = findViewById(R.id.btnAccount);
        Button btnHome = findViewById(R.id.btnHome);
        Button btnBooking = findViewById(R.id.btnBooking);

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khi nhấn vào nút "Tài Khoản", mở UserActivity
                Intent intent = new Intent(ListBookTicket.this, User.class);
                startActivity(intent);
            }
        });

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListBookTicket.this, ListBookTicket.class);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListBookTicket.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}