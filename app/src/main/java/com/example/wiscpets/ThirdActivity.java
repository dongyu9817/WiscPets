package com.example.wiscpets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
        EditText noteEditTitle = findViewById(R.id.noteEditTitle);

        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);

        if (noteid != -1) {
            Note note = MainActivityNotebookLanding.notes.get(noteid);
            String noteTitle = note.getTitle();
            String noteContent = note.getContent();
            noteEditTitle.setText(noteTitle);
            textView6.setText(noteContent);
        }
    }
    public void clickFunction2 (View view){
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper Helper = new DBHelper(sqLiteDatabase);
        EditText editText = findViewById(R.id.editText4);

        String UserInput = editText.getText().toString();
        EditText editTextTitle = findViewById(R.id.noteEditTitle);

        String UserInputTitle = editTextTitle.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "user");

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        String title;
        if(noteid == -1){
            title = "NOTE_" + (MainActivityNotebookLanding.notes.size() + 1);
            title = UserInputTitle;
            Log.i("title third  ", title);

            Helper.saveNotes(username, title, UserInput, date);
        }
        else{
            //title = "NOTE_" + (noteid + 1);
            title = UserInputTitle;
            Helper.updateNote(title, date, UserInput, (noteid + 1));
        }
        Intent intent = new Intent(this, MainActivityNotebookLanding.class);
        startActivity(intent);
    }

    public void clickFunction3(View view) {
        Context context = getApplicationContext();
        AlertDialog.Builder ab = new AlertDialog.Builder(ThirdActivity.this);
        ab.setMessage("Are you sure to delete?");
        ab.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
                DBHelper Helper = new DBHelper(sqLiteDatabase);
                if (noteid == -1) {
                    //give up edit
                } else {
                    Helper.deleteNote((noteid + 1));
                    dialog.dismiss();
                    EditText editTextTitle = findViewById(R.id.noteEditTitle);
                    String UserInputTitle = editTextTitle.getText().toString();
                    Toast.makeText(ThirdActivity.this, UserInputTitle + " is deleted", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(ThirdActivity.this, MainActivityNotebookLanding.class);
                startActivity(intent);
            }
        });
        ab.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(ThirdActivity.this, MainActivityNotebookLanding.class);
                startActivity(intent);
            }
        });
        ab.show();

    }


}
