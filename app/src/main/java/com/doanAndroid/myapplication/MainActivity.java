package com.doanAndroid.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<CategoryFilm> categories;
    private List<Film> films;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Danh sách thể loại cố định
        categories = new ArrayList<>();
        categories.add(new CategoryFilm(3,"Gia đình", R.drawable.giadinh));
        categories.add(new CategoryFilm(4,"Kinh dị", R.drawable.kinhdi));
        categories.add(new CategoryFilm(2,"Hoạt hình", R.drawable.hoathinh));
        categories.add(new CategoryFilm(1,"Hành động", R.drawable.hanhdong));

        // Danh sách phim cố định
        films = new ArrayList<>();
        films.add(new Film("Aquarman", R.drawable.hanhdong1, 124 ,1,"24/12/2023",24,45000));
        films.add(new Film("Phi Công Siêu Đẳng", R.drawable.hd2,130, 1, "27/05/2023",24,45000));
        films.add(new Film("Xin Chào JADDO", R.drawable.hh1, 76, 2, "15/12/2023",24,50000));
        films.add(new Film("Thiếu Niên Và Chim Diệc", R.drawable.hh2, 123, 2, "15/12/2023",24,50000));
        films.add(new Film("WONKA", R.drawable.family1, 116, 3, "08/12/2023",24,50000));
        films.add(new Film("MỘT MÌNH VẪN ỔN'T", R.drawable.family2, 103, 3, "15/12/2023",24,75000));
        films.add(new Film("VÒNG LẶP QUỶ DỮ", R.drawable.kd1, 107, 4,"15/12/2023",24,35000));
        films.add(new Film("Quỷ Cẩu", R.drawable.kd2,99, 4, "29/12/2023",24,40000));

        // Lấy danh sách thể loại từ cơ sở dữ liệu
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<CategoryFilm> categoriesFromDB = dbHelper.getAllCategories();
        categories.addAll(categoriesFromDB);

        // Lấy danh sách phim từ cơ sở dữ liệu
        List<Film> filmsFromDB = dbHelper.getALLFilms();
        films.addAll(filmsFromDB);

        // Khởi tạo RecyclerView và Adapter cho danh sách thể loại
        RecyclerView categoryRecyclerView = findViewById(R.id.categoryView);
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);

        categoryAdapter.setOnCategoryClickListener(new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(CategoryFilm category) {
                // Truyền thông tin về thể loại khi nhấn vào
                Intent intent = new Intent(MainActivity.this, FilmListActivity.class);
                intent.putExtra("CATEGORY_ID", category.getId());
                startActivity(intent);
            }
        });

        // Set hướng là HORIZONTAL cho danh sách thể loại
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

        categoryRecyclerView.setAdapter(categoryAdapter);

        // Khởi tạo RecyclerView và Adapter cho danh sách film
        RecyclerView filmRecyclerView = findViewById(R.id.homeFilmView);
        FilmAdapter filmAdapter = new FilmAdapter(films);

        // Set hướng là HORIZONTAL cho danh sách film
        filmRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

        filmRecyclerView.setAdapter(filmAdapter);

        filmAdapter.setOnFilmClickListener(new FilmAdapter.OnFilmClickListener() {
            @Override
            public void onFilmClick(Film film) {
                // Mở màn hình chi tiết với thông tin về film được chọn
                Intent intent = new Intent(MainActivity.this, FilmDetail.class);
                intent.putExtra("FILM_TITLE", film.getTitle());
                intent.putExtra("FILM_IMAGE", film.getImage());
                intent.putExtra("SOGHE", film.getSoghe());
                intent.putExtra("FILM_TIME", film.getTimeFilm());
                intent.putExtra("FILM_THELOAI", film.getTheloai());
                intent.putExtra("FILM_DATE", film.getDate());
                intent.putExtra("FILM_PRICE", film.getPrice() );
                startActivity(intent);
            }
        });

        LinearLayout navigationLayout = findViewById(R.id.navigationLayout);
        Button btnAccount = findViewById(R.id.btnAccount);
        Button btnHome = findViewById(R.id.btnHome);
        Button btnBooking = findViewById(R.id.btnBooking);

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khi nhấn vào nút "Tài Khoản", mở UserActivity
                Intent intent = new Intent(MainActivity.this, User.class);
                startActivity(intent);
            }
        });

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListBookTicket.class);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
