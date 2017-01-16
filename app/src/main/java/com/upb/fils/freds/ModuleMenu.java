package com.upb.fils.freds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ModuleMenu extends AppCompatActivity {

    String language;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_menu);
        language = getIntent().getStringExtra("language");

        lv = (ListView) findViewById(R.id.listViewOfModules);

        DBManager dbManager = new DBManager(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                dbManager.getAllLessonsForLanguage(language) );

        lv.setAdapter(arrayAdapter);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), Lesson.class);
                intent.putExtra("language", language);
                intent.putExtra("lesson", ((TextView) view).getText());
                startActivity(intent);
            }
        };
        lv.setOnItemClickListener(onItemClickListener);
    }







}
