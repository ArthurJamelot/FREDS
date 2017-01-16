package com.upb.fils.freds;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.upb.fils.freds.DBManager.done_col;
import static com.upb.fils.freds.DBManager.id_col;
import static com.upb.fils.freds.DBManager.language_col;
import static com.upb.fils.freds.DBManager.lesson_col;
import static com.upb.fils.freds.DBManager.table_name;
import static com.upb.fils.freds.DBManager.word_col;
import static com.upb.fils.freds.DBManager.word_in_english_col;


public class DBManagerHelper extends SQLiteOpenHelper {

    public DBManagerHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_name + "(" +
                id_col + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                language_col + " VARCHAR, " +
                lesson_col + " VARCHAR, " +
                word_in_english_col + " VARCHAR, " +
                word_col + " VARCHAR, " +
                done_col + " VARCHAR " +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
