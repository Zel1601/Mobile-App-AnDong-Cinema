package com.doanAndroid.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user.db";
    public static final String TABLE_NAME = "user_table";
    private static final String COL_ID = "ID";
    public static final String COL_USERNAME = "USERNAME";
    public static final String COL_PASSWORD = "PASSWORD";
    private static final String COL_IS_ADMIN = "IS_ADMIN";

    private static final String COL_HO = "HO";
    private static final String COL_TEN = "TEN";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_BIRTH = "BIRTH";
    private static final String COL_SDT = "SDT";

    private static final String BOOKING_TABLE_NAME = "booking_table";
    private static final String COL_BOOKING_ID = "booking_id";
    private static final String COL_FILM_NAME = "film_name";
    private static final String COL_SEAT_NUMBER = "seat_number";
    private static final String COL_TOTAL_PRICE = "total_price";

    private static final String CATEGORY_TABLE_NAME = "category_table"; // Thêm dòng này
    private static final String COL_CATEGORY_NAME = "CATEGORY_NAME"; // Thêm dòng này
    private static final String FILM_TABLE_NAME = "FILM_TABLE";
    private static final String COL_FILM_TIME = "FILM_TIME";
    private static final String COL_FILM_GHE ="FILM_SO_GHE";
    private static final String COL_FILM_DATE ="FILM_DATE";
    private static final String COL_FILM_PRICE ="FILM_PRICE";
    private static final String COL_FILM_THELOAI ="FILM_THE_LOAI";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng người dùng
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_IS_ADMIN + " INTEGER, " +
                COL_HO + " TEXT, " +
                COL_TEN + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_BIRTH + " TEXT, " +
                COL_SDT + " TEXT)";
        db.execSQL(createTable);

        // Insert tài khoản admin
        ContentValues adminValues = new ContentValues();
        adminValues.put(COL_USERNAME, "admin");
        adminValues.put(COL_PASSWORD, "admin123");
        adminValues.put(COL_IS_ADMIN, 1);
        db.insertWithOnConflict(TABLE_NAME, null, adminValues, SQLiteDatabase.CONFLICT_IGNORE);

        // Tạo bảng đặt vé
        String createBookingTable = "CREATE TABLE IF NOT EXISTS " + BOOKING_TABLE_NAME + " (" +
                COL_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FILM_NAME + " TEXT, " +
                COL_SEAT_NUMBER + " TEXT, " +
                COL_TOTAL_PRICE + " TEXT)";
        db.execSQL(createBookingTable);

        String createCategoryTable = "CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CATEGORY_NAME + " TEXT)";
        db.execSQL(createCategoryTable);


        String createFilmTable = "CREATE TABLE IF NOT EXISTS " + FILM_TABLE_NAME + "(" +
                COL_FILM_NAME + " TEXT, " +
                COL_FILM_TIME + " TEXT, " +
                COL_FILM_DATE + " TEXT, " +
                COL_FILM_GHE + " TEXT, " +
                COL_FILM_PRICE + " TEXT, " +
                COL_FILM_THELOAI + " INTEGER)";
        db.execSQL(createFilmTable);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FILM_TABLE_NAME);
        onCreate(db);
    }


    public long addBooking(String filmName, String seatNumber, String totalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FILM_NAME, filmName);
        contentValues.put(COL_SEAT_NUMBER, seatNumber);
        contentValues.put(COL_TOTAL_PRICE, totalPrice);
        long result = db.insert(BOOKING_TABLE_NAME, null, contentValues);
        db.close();
        return result;
    }

    public long addCategory(int categoryId, String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, categoryId);
        contentValues.put(COL_CATEGORY_NAME, categoryName);
        long result = db.insert(CATEGORY_TABLE_NAME, null, contentValues);
        db.close();
        return result;
    }

    public long addFilm(String filmName, int filmTime, String filmDate, int filmGhe, float filmPrice, int filmTheLoai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FILM_NAME, filmName);
        contentValues.put(COL_FILM_TIME, filmTime);
        contentValues.put(COL_FILM_DATE, filmDate);
        contentValues.put(COL_FILM_GHE, filmGhe);
        contentValues.put(COL_FILM_PRICE, filmPrice);
        contentValues.put(COL_FILM_THELOAI, filmTheLoai);
        long result = db.insert(FILM_TABLE_NAME, null, contentValues);
        db.close();
        return result;
    }


    public ArrayList<BookTicketItem> getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<BookTicketItem> bookingList = new ArrayList<>();

        Cursor cursor = db.query(BOOKING_TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String filmName = cursor.getString(cursor.getColumnIndex(COL_FILM_NAME));
                String seatNumber = cursor.getString(cursor.getColumnIndex(COL_SEAT_NUMBER));
                String totalPrice = cursor.getString(cursor.getColumnIndex(COL_TOTAL_PRICE));

                BookTicketItem bookingItem = new BookTicketItem(filmName, seatNumber, totalPrice);
                bookingList.add(bookingItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bookingList;
    }

    public ArrayList<CategoryFilm> getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CategoryFilm> categoryList = new ArrayList<>();

        Cursor cursor = db.query(CATEGORY_TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int categoryId = cursor.getInt(cursor.getColumnIndex(COL_ID));
                String categoryName = cursor.getString(cursor.getColumnIndex(COL_CATEGORY_NAME));

                CategoryFilm categoryItem = new CategoryFilm(categoryId, categoryName, R.drawable.hanhdong);
                categoryList.add(categoryItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return categoryList;
    }

    public ArrayList<Film> getALLFilms(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Film> filmList = new ArrayList<>();

        Cursor cursor = db.query(FILM_TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {
                String filmName = cursor.getString(cursor.getColumnIndex(COL_FILM_NAME));
                int filmTime = cursor.getInt(cursor.getColumnIndex(COL_FILM_TIME));
                int filmGhe = cursor.getInt(cursor.getColumnIndex(COL_FILM_GHE));
                String filmDate = cursor.getString(cursor.getColumnIndex(COL_FILM_DATE));
                float filmPrice = cursor.getFloat(cursor.getColumnIndex(COL_FILM_PRICE));
                int filmTheLoai = cursor.getInt(cursor.getColumnIndex(COL_FILM_THELOAI));

                Film filmItem = new Film(filmName, R.drawable.family1,filmTime, filmGhe , filmDate, filmTheLoai ,filmPrice );
                filmList.add(filmItem);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return  filmList;
    }

    public boolean checkOldPassword(String username, String enteredOldPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_USERNAME + "=? AND " + COL_PASSWORD + "=?";
        String[] selectionArgs = {username, enteredOldPassword};

        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }


    public boolean isValidAdminLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_USERNAME + "=? AND " + COL_PASSWORD + "=? AND " + COL_IS_ADMIN + "=?";
        String[] selectionArgs = {username, password, "1"};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean isValidUserLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_USERNAME + "=? AND " + COL_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }


    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_IS_ADMIN, 0);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result;
    }

    public void updateUserInfo(String username, String ho, String ten, String email, String birth, String sdt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_HO, ho);
        contentValues.put(COL_TEN, ten);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_BIRTH, birth);
        contentValues.put(COL_SDT, sdt);
        db.update(TABLE_NAME, contentValues, COL_USERNAME + "=?", new String[]{username});
        db.close();
    }

    public Cursor getUserInfo(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_USERNAME + "=?";
        String[] selectionArgs = {username};
        return db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    public long deleteCategory(int categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COL_ID + "=?";
        String[] whereArgs = {String.valueOf(categoryId)};
        long result = db.delete(CATEGORY_TABLE_NAME, whereClause, whereArgs);
        db.close();
        return result;
    }

    public long deleteFilm(String filmName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COL_FILM_NAME + "=?";
        String[] whereArgs = {filmName};
        long result = db.delete(FILM_TABLE_NAME, whereClause, whereArgs);
        db.close();
        return result;
    }

    public long updateCategory(int categoryId, String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CATEGORY_NAME, categoryName);

        String whereClause = COL_ID + "=?";
        String[] whereArgs = {String.valueOf(categoryId)};

        long result = db.update(CATEGORY_TABLE_NAME, contentValues, whereClause, whereArgs);
        db.close();
        return result;
    }

    public long updateFilm(String filmName, int filmTime, String filmDate, int filmGhe, float filmPrice, int filmTheLoai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FILM_NAME, filmName);
        contentValues.put(COL_FILM_TIME, filmTime);
        contentValues.put(COL_FILM_DATE, filmDate);
        contentValues.put(COL_FILM_GHE, filmGhe);
        contentValues.put(COL_FILM_PRICE, filmPrice);
        contentValues.put(COL_FILM_THELOAI, filmTheLoai);

        String whereClause = COL_FILM_NAME + "=?";
        String[] whereArgs = {String.valueOf(filmName)};

        long result = db.update(FILM_TABLE_NAME, contentValues, whereClause, whereArgs);
        db.close();
        return result;
    }


}
