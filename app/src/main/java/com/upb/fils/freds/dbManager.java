package com.upb.fils.freds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;

import java.util.ArrayList;

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
    private static final String lang_ro      = "Romanian";

    private SQLiteDatabase db;

    public dbManager (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, 1);
    }

    public void open(){
        db = this.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void onCreate (SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_name + "(" +
                id_col + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                language_col + " VARCHAR, " +
                lesson_col + " VARCHAR, " +
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
    public long insertEntry(String language, String lesson, String word_english, String word) {
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
            insertEntry(lang_ro, "Introduction", word_pairs[i][0], word_pairs[i][1]);
        }
    }

    public Cursor getAllWordsForLessonAndLanguage(int lesson, String language) {
        Cursor c = db.query(table_name,
                new String[] {id_col, word_in_english_col, word_col, done_col},
                language_col +" = " + language +" AND " + lesson_col + " = " + lesson,
                null, null, null, null);
        return c;
    }

    public ArrayList<String> getAllLessonsForLanguage(String language) {
        Cursor c = db.query(true ,table_name,  new String[] {lesson_col}, language_col + " = " + language, null, null, null, null, null);

        ArrayList<String> results = new ArrayList<String>();

        if (c.getCount() != 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                results.add(c.getString(0));
                c.moveToNext();
            }
        }

        return results;
    }

    // Gets the id of one word and sets its "DONE" attribute to "done"
    public void validateWord(int id){
        //Search for the ID in the db
        Cursor c = db.query(table_name, new String[] {id_col}, id_col + " = " + id, null, null, null, null, null);
        if(c.getCount() != 1) {
            //TODO trigger an error
            return;
        }
        else {
            bdd.execSQL("UPDATE " + table_name  + " SET " + done_col + " = " + isDoneTag + " WHERE " + id_col + " = " + id +";");
        }
    }
}
