package com.upb.fils.freds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void goToModuleMenu(View view){

        Intent intent = new Intent(this, ModuleMenu.class);
        intent.putExtra("language",((Button) view).getText());
        startActivity(intent);
    }
}
