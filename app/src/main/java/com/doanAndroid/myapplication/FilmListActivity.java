package com.doanAndroid.myapplication;// Trong FilmListActivity.java
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doanAndroid.myapplication.Film;
import com.doanAndroid.myapplication.FilmAdapter;
import com.doanAndroid.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FilmListActivity extends AppCompatActivity {

    private List<Film> allFilms;  // Danh sách tất cả các phim
    private List<Film> filteredFilms;  // Danh sách phim sau khi lọc
    private TextView categoryNameTextView;
    private List<CategoryFilm> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);


        categories = new ArrayList<>();
        categories.add(new CategoryFilm(3,"Gia đình", R.drawable.giadinh));
        categories.add(new CategoryFilm(4,"Kinh dị", R.drawable.kinhdi));
        categories.add(new CategoryFilm(2,"Hoạt hình", R.drawable.hoathinh));
        categories.add(new CategoryFilm(1,"Hành động", R.drawable.hanhdong));

        // Khởi tạo danh sách tất cả các phim
        allFilms = new ArrayList<>();
        allFilms.add(new Film("Aquarman", R.drawable.hanhdong1, 124, 1, "24/12/2023",24,45000));
        allFilms.add(new Film("Phi Công Siêu Đẳng", R.drawable.hd2, 130, 1, "27/05/2023",24,45000));
        allFilms.add(new Film("Xin Chào JADDO", R.drawable.hh1, 76, 2, "15/12/2023",24,50000));
        allFilms.add(new Film("Thiếu Niên Và Chim Diệc", R.drawable.hh2, 123, 2, "15/12/2023",24, 50000));
        allFilms.add(new Film("WONKA", R.drawable.family1, 116, 3, "08/12/2023",24,50000));
        allFilms.add(new Film("MỘT MÌNH VẪN ỔN'T", R.drawable.family2, 103, 3, "15/12/2023",24,75000));
        allFilms.add(new Film("VÒNG LẶP QUỶ DỮ", R.drawable.kd1, 107, 4,"15/12/2023",24,35000));
        allFilms.add(new Film("Quỷ Cẩu", R.drawable.kd2,99, 4, "29/12/2023",24,40000));
        // Thêm các phim khác theo thể loại được chọn

        // Lấy tên thể loại từ Intent
        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        // Thiết lập tên thể loại trên TextView

        // Lọc danh sách phim theo thể loại
        int categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);
        filteredFilms = filterFilmsByCategory(categoryId);

        // Khởi tạo RecyclerView và Adapter cho danh sách phim
        RecyclerView filmRecyclerView = findViewById(R.id.filmRecyclerView);
        FilmAdapter filmAdapter = new FilmAdapter(filteredFilms);

        filmRecyclerView.setAdapter(filmAdapter);
        filmRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Hàm lọc danh sách phim theo thể loại
    private List<Film> filterFilmsByCategory(int categoryId) {
        List<Film> filteredList = new ArrayList<>();
        for (Film film : allFilms) {
            if (film.getTheloai() == categoryId) {
                filteredList.add(film);
            }
        }
        return filteredList;
    }
}
