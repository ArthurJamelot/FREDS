package com.upb.fils.freds;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;

/**
 * Created by Vlad Saceanu on 01/15/17.
 */

public class dbManager extends SQLiteOpenHelper{

    private static final String table_name = "data_table";

    // Names of columns
    private static final String id_col = "_id";
    private static final String language_col = "mLanguage";
    private static final String lesson_col = "mLesson";
    private static final String word_in_english_col = "mWordEN";
    private static final String word_col = "mWord";
    private static final String done_col = "mDone";

    //tags for "done" or "not done"
    private static final String isDoneTag    = "DONE";
    private static final String isNotDoneTag = "TEST";

    //language tags
    private static final String lang_ro      = "ROMANIAN";

    private SQLiteDatabase db;

    public dbManager (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public void onCreate (SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_name + "(" +
                id_col + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                language_col + " VARCHAR, " +
                lesson_col + " INTEGER, " +
                word_in_english_col + " VARCHAR, " +
                word_col + " VARCHAR, " +
                done_col + " VARCHAR " +
                ")"
        );

        // Create entries in the database
        createEntries();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*
    This method inserts an entry in the DB.
    Returns the id of the Entry if it's succeeded.
    */
    public long insertEntry(String language, int lesson, String word_english, String word) {
        ContentValues values = new ContentValues();
        values.put(language_col, language);
        values.put(lesson_col, lesson);
        values.put(word_in_english_col, word_english);
        values.put(word_col, word);
        // tag the word as not done
        values.put(done_col, isNotDoneTag);
        return db.insert(table_name, null, values);
    }

    // Will create entries in the table
    private void createEntries() {
        //Create words in Romanian for lesson 1
        String word_pairs [][] = new String[][]{
                {"Hello",           "Salut"},
                {"How are you?",    "Ce faci?"}
        };

        for(int i = 0; i < word_pairs.length; i++) {
            insertEntry(lang_ro, 1, word_pairs[i][0], word_pairs[i][1]);
        }
    }
}
