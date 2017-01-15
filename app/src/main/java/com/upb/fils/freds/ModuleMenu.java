package com.upb.fils.freds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ModuleMenu extends AppCompatActivity {

    String language;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_menu);
        language = getIntent().getStringExtra("language");

        lv = (ListView) findViewById(R.id.listViewOfModules);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                dbManager.getInstance(this, null, null, 1).getAllLessonsForLanguage );

        lv.setAdapter(arrayAdapter);
    }







}
