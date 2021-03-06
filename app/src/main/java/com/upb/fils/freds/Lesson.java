package com.upb.fils.freds;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static com.upb.fils.freds.DBManager.done_col;
import static com.upb.fils.freds.DBManager.id_col;
import static com.upb.fils.freds.DBManager.word_col;
import static com.upb.fils.freds.DBManager.word_in_english_col;

public class Lesson extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        listView = (ListView) findViewById(R.id.ListViewLessonElements);

        String[] columns = new String[]{id_col, word_in_english_col, word_col, done_col};

        DBManager db = new DBManager(this);
        db.open();
        Cursor myData = db.getAllWordsForLessonAndLanguage("Introduction", "Romanian");

        String[] from = new String[]{word_in_english_col, word_col, done_col};
        int[] to = new int[]{R.id.word_in_english, R.id.word_in_foreign_language, R.id.test_yourself};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row_lesson_element, myData, from, to, 0);

        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Say " + ((TextView)view.findViewById(R.id.word_in_foreign_language)).getText() + "!");
                try {
                    startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Your device doesn\'t support speech input",
                            Toast.LENGTH_SHORT).show();
                }

            }
        };
        listView.setOnItemClickListener(itemClickListener);
        db.close();
    }


}
