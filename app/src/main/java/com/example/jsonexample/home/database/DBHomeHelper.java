package com.example.jsonexample.home.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.jsonexample.home.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

public class DBHomeHelper extends SQLiteOpenHelper {

    public static final String HOME_TABLE = "HOME_TABLE";
    public static final String TITLE_COLUMN = "TITLE";
    public static final String ID_HOME_COLUMN = "ID_HOME";
    public static final String PDF_URL_COLUMN = "PDF_URL";
    public static final String TIME_COLUMN = "TIME";
    public static final String IMAGE_URL_COLUMN = "IMAGE_URL";

    public DBHomeHelper(@Nullable Context context) {
        super(context, "home.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + HOME_TABLE + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_HOME_COLUMN + " INTEGER, " + TITLE_COLUMN + " TEXT, " + IMAGE_URL_COLUMN + " TEXT, " + PDF_URL_COLUMN + " TEXT, " + TIME_COLUMN + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public int add_movie(HomeModel homeModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_HOME_COLUMN, homeModel.getIdHome());
        contentValues.put(TITLE_COLUMN, homeModel.getTitle());
        contentValues.put(TIME_COLUMN, homeModel.getTime());
        contentValues.put(PDF_URL_COLUMN, homeModel.getPdfURL());
        contentValues.put(IMAGE_URL_COLUMN, homeModel.getImageURL());

        long result = sqLiteDatabase.insert(HOME_TABLE, null, contentValues);
        sqLiteDatabase.close();
        return (int)result;
    }

    public List<HomeModel> getMovies() {
        List<HomeModel> moviesList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + HOME_TABLE + " ORDER BY " + ID_HOME_COLUMN + " DESC ", null);

        if(cursor.moveToFirst()) {
            do{
                int idHome = cursor.getInt(1);
                String title = cursor.getString(2);
                String imageUrl = cursor.getString(3);
                String pdfURL = cursor.getString(4);
                String time = cursor.getString(5);
                HomeModel homeModel = new HomeModel(idHome, title, imageUrl, pdfURL, time);
                moviesList.add(homeModel);

            } while (cursor.moveToNext());

        }

        cursor.close();
        sqLiteDatabase.close();
        return moviesList;
    }
}
