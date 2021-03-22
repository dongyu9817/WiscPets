package com.example.wiscpets.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


import com.example.wiscpets.R;
import com.example.wiscpets.WiscPetLoginActivity;

public class app_home extends AppCompatActivity {
Timer t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_home);

        t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i1 = new Intent(com.example.wiscpets.app.app_home.this, WiscPetLoginActivity.class);

                //Intent i1 = new Intent(app_home.this, MainActivity.class);
                startActivity(i1);
                finish();
            }
        },3000);
    }
}
