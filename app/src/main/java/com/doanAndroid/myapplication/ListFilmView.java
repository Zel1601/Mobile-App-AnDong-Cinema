package com.doanAndroid.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListFilmView extends AppCompatActivity {

    private ListView listView;
    private FilmsAdapter filmAdapter;

    private List<Film> films;
    private DatabaseHelper databaseHelper;
    private FloatingActionButton fabAddFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_film_view);

        listView = findViewById(R.id.lvFilmList);
        films = new ArrayList<>();
        filmAdapter = new FilmsAdapter(this, films);
        listView.setAdapter(filmAdapter);

        databaseHelper = new DatabaseHelper(this);

        films.addAll(getDummyFilmList());

        films.addAll(databaseHelper.getALLFilms());
        filmAdapter.notifyDataSetChanged();

        fabAddFilm = findViewById(R.id.fabAddFilm);
        fabAddFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListFilmView.this, AddFilm.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Film selectedFilm = (Film) filmAdapter.getItem(position);

                Intent editIntent = new Intent(ListFilmView.this, EditFilm.class);
                editIntent.putExtra("film_name", selectedFilm.getTitle());
                startActivity(editIntent);
            }
        });

        filmAdapter.setOnEditClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Film selectedFilm = (Film) filmAdapter.getItem(position);

                Intent editIntent = new Intent(ListFilmView.this, EditFilm.class);
                editIntent.putExtra("film_name", selectedFilm.getTitle());
                startActivity(editIntent);
            }
        });

        filmAdapter.setOnDeleteClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Film selectedFilm = (Film) filmAdapter.getItem(position);

                showDeleteConfirmationDialog(selectedFilm);
            }
        });
    }

    private void showDeleteConfirmationDialog(Film film) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa Phim này?");

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFilm(film);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void deleteFilm(Film film) {
        long result = databaseHelper.deleteFilm(film.getTitle());

        if (result > 0) {
            films.remove(film);
            filmAdapter.notifyDataSetChanged();
            Toast.makeText(ListFilmView.this, "Đã xóa Phim", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ListFilmView.this, "Lỗi khi xóa Phim", Toast.LENGTH_SHORT).show();
        }
    }


    public void updateFilmList(Film newFilm) {
        films.add(newFilm);
        filmAdapter.notifyDataSetChanged();
    }

    private List<Film> getDummyFilmList() {
        List<Film> films = new ArrayList<>();
        films.add(new Film("Aquarman", R.drawable.hanhdong1, 124 ,1,"24/12/2023",24,45000));
        films.add(new Film("Phi Công Siêu Đẳng", R.drawable.hd2,130, 1, "27/05/2023",24,45000));
        films.add(new Film("Xin Chào JADDO", R.drawable.hh1, 76, 2, "15/12/2023",24,50000));
        films.add(new Film("Thiếu Niên Và Chim Diệc", R.drawable.hh2, 123, 2, "15/12/2023",24,50000));
        films.add(new Film("WONKA", R.drawable.family1, 116, 3, "08/12/2023",24,50000));
        films.add(new Film("MỘT MÌNH VẪN ỔN'T", R.drawable.family2, 103, 3, "15/12/2023",24,75000));
        films.add(new Film("VÒNG LẶP QUỶ DỮ", R.drawable.kd1, 107, 4,"15/12/2023",24,35000));
        films.add(new Film("Quỷ Cẩu", R.drawable.kd2,99, 4, "29/12/2023",24,40000));

        return films;
    }

    protected void onResume() {
        super.onResume();
        films.clear();

        // Thêm danh sách cố định vào danh sách thể loại
        films.addAll(getDummyFilmList());

        // Hiển thị danh sách thể loại từ cơ sở dữ liệu
        films.addAll(databaseHelper.getALLFilms());
        filmAdapter.notifyDataSetChanged();
    }
}