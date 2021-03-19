package com.example.wiscpets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThirdActivity extends AppCompatActivity {

int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText textView6 = findViewById(R.id.editText4);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);

        if (noteid != -1) {
            Note note = Main2Activity.notes.get(noteid);
            String noteContent = note.getContent();
            textView6.setText(noteContent);
        }
    }
    public void clickFunction2 (View view){
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper Helper = new DBHelper(sqLiteDatabase);
        EditText editText = findViewById(R.id.editText4);

        String UserInput = editText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "");

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        String title;
        if(noteid == -1){
            title = "NOTE_" + (Main2Activity.notes.size() + 1);
            Log.i("title third  ", title);

            Helper.saveNotes(username, title, UserInput, date);
        }
        else{
            title = "NOTE_" + (noteid + 1);
            Helper.updateNote(title, date, UserInput);
        }
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

}
