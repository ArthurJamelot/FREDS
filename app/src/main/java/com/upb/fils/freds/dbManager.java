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

public class DBManager {

    static final String table_name = "data_table";

    // Names of columns
    static final String id_col = "_id";
    static final String language_col = "mLanguage";
    static final String lesson_col = "mLesson";
    static final String word_in_english_col = "mWordEN";
    static final String word_col = "mWord";
    static final String done_col = "mDone";

    //tags for "done" or "not done"
    static final String isDoneTag    = "DONE";
    static final String isNotDoneTag = "TEST";

    //language tags
    private static final String lang_ro      = "Romanian";

    private DBManagerHelper dbManagerHelper;
    private SQLiteDatabase db;
    private static boolean firstLaunch=true;

    public DBManager(Context context) {
        dbManagerHelper= new DBManagerHelper(context, "data", null, 1);
        if (firstLaunch) {
            this.createEntries();
            firstLaunch = false;
        }
    }

    public void open(){
        db = dbManagerHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    /*
    This method inserts an entry in the DB.
    Returns the id of the Entry if it's succeeded.
    */
    public long insertEntry(String language, String lesson, String word_english, String word) {
        open();
        ContentValues values = new ContentValues();
        values.put(language_col, language);
        values.put(lesson_col, lesson);
        values.put(word_in_english_col, word_english);
        values.put(word_col, word);
        // tag the word as not done
        values.put(done_col, isNotDoneTag);
        long r =  db.insert(table_name, null, values);
        close();
        return r;
    }

    // Will create entries in the table
    public void createEntries() {
        //Create words in Romanian for lesson 1
        String word_pairs [][] = new String[][]{
                {"Hello",           "Salut"},
                {"How are you?",    "Ce faci?"}
        };

        for(int i = 0; i < word_pairs.length; i++) {
            insertEntry(lang_ro, "Introduction", word_pairs[i][0], word_pairs[i][1]);
        }
    }

    public Cursor getAllWordsForLessonAndLanguage(String lesson, String language) {
        Cursor c = db.query(table_name,
                new String[] {id_col, word_in_english_col, word_col, done_col},
                language_col +" = '" + language +"' AND " + lesson_col + " = '" + lesson + "'",
                null, null, null, null);
        return c;
    }

    public ArrayList<String> getAllLessonsForLanguage(String language) {
        open();
        Cursor c = db.query(true ,table_name,  new String[] {lesson_col}, language_col + " = '" + language + "'", null, null, null, null, null);

        ArrayList<String> results = new ArrayList<String>();

        if (c.getCount() != 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                results.add(c.getString(0));
                c.moveToNext();
            }
        }

        close();
        return results;
    }

    // Gets the id of one word and sets its "DONE" attribute to "done"
    public void validateWord(int id){
        open();
            db.execSQL("UPDATE " + table_name  + " SET " + done_col + " = " + isDoneTag + " WHERE " + id_col + " = " + id +";");
        close();
    }
}
