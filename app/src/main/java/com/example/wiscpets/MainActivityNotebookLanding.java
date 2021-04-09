package com.example.wiscpets;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

//This is the notebook landing page
public class MainActivityNotebookLanding extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public static ArrayList<Note> notes = new ArrayList<>();
    TextView textView2;
    SearchView editsearch;
    ArrayAdapter adapter;
    ArrayList<String> displayNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5" , Context.MODE_PRIVATE);
        textView2 = (TextView) findViewById(R.id.textView2);
        Intent intent = getIntent();
        String user = sharedPreferences.getString("username", "user");
        Log.i("SP MA2", user);

        //get sql instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper Helper = new DBHelper(sqLiteDatabase);

        //initiate the notes object

        displayNotes = new ArrayList<>();
        notes = Helper.readNotes(user);

        int counter = 0;
        for (Note note : notes){
            Log.i("note content ", "HELLO");
            displayNotes.add(String.format("Title: %s\nDate: %s", note.getTitle(), note.getDate()));
            counter++;

        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.ListView);
        listView.setAdapter((adapter));

        TextView textViewTotalNotes = findViewById(R.id.textViewTotalNotes);
        textViewTotalNotes.setText(counter + " Notes");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
        //delete dialog

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d ("alert", "print success");
                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivityNotebookLanding.this);
                ab.setMessage("Are you sure to delete?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
                        DBHelper Helper = new DBHelper(sqLiteDatabase);
                        Helper.deleteNote(position);
                        dialog.dismiss();

                        Note noteDelete = notes.get(position);
                        Toast.makeText(MainActivityNotebookLanding.this,noteDelete.getTitle()+" is deleted",Toast.LENGTH_SHORT).show();

                    }

                })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        })
                        .show();
                return false;
            }
        });

        //create array list object

        //add new note
        ImageView addNote = findViewById(R.id.addButton);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivityNotebookLanding.this, "add note selected", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MainActivityNotebookLanding.this, ThirdActivity.class);
                startActivity(intent2);
            }
        });
        //search
        editsearch = (SearchView) findViewById(R.id.searchBar);
        editsearch.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        String charText = text.toLowerCase(Locale.getDefault());
        Log.i ("searchbar", "okay");

        if (charText.length() == 0) {
            SQLiteDatabase sqLiteDatabase = getApplicationContext().openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

            DBHelper Helper = new DBHelper(sqLiteDatabase);
            notes = Helper.readNotes("user");
            for (Note note : notes){
                displayNotes.add(String.format("Title: %s\nDate: %s", note.getTitle(), note.getDate()));
                Log.i ("searchbar", "okay");
            }

        } else {
            displayNotes.clear();
            SQLiteDatabase sqLiteDatabase = getApplicationContext().openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

            DBHelper Helper = new DBHelper(sqLiteDatabase);
            notes = Helper.readNotes("user");

            for (Note note : notes){
                if (note.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    displayNotes.add(String.format("Title: %s\nDate: %s", note.getTitle(), note.getDate()));
                }
                Log.i ("searchbar", "okay");
            }
        }
        adapter.notifyDataSetChanged();
        return false;
    }


}
