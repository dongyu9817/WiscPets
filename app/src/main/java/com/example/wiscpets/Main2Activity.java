package com.example.wiscpets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

//This is the notebook landing page
public class Main2Activity extends AppCompatActivity {
    public static ArrayList<Note> notes = new ArrayList<>();
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5" , Context.MODE_PRIVATE);
        textView2 = (TextView) findViewById(R.id.textView2);
        Intent intent = getIntent();
        String user = sharedPreferences.getString("username", "");
        Log.i("SP MA2", user);
        textView2.setText( "Welcome " + user + "!");

        //get sql instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper Helper = new DBHelper(sqLiteDatabase);

        //initiate the notes object

        ArrayList<String> displayNotes = new ArrayList<>();
        notes = Helper.readNotes(user);

        for (Note note : notes){
            Log.i("note content ", "HELLO");
            displayNotes.add(String.format("Title: %s\nDate: %s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.ListView);
        listView.setAdapter((adapter));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
        //create array list object
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        switch (item.getItemId()){

            case R.id.item3:
                Toast.makeText(this, "add note selected", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, ThirdActivity.class);
                startActivity(intent2);

                return true;
            case R.id.item2:
                Toast.makeText(this, "logout selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, WiscPetLoginActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("username").apply();
                startActivity(intent);
                gotoActivity1 ();
                return true;

            default:return super.onOptionsItemSelected(item);
        }
    }
    public void gotoActivity1 (){
        Intent intent = new Intent(Main2Activity.this,WiscPetLoginActivity.class);
         startActivity(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }


}
