package com.example.wiscpets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wiscpets.app.app_activity_contact_us;
import com.example.wiscpets.app.app_event_add_to_calendar;
import com.example.wiscpets.app.app_make_phone_call_activity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Locale;

//This is the notebook landing page
public class MainActivityNotebookLanding extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public static ArrayList<Note> notes = new ArrayList<>();
    TextView textView2;
    SearchView editsearch;
    ArrayAdapter adapter;
    ArrayList<String> displayNotes;
    Toolbar t;
    DrawerLayout drawer;

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

        drawer = findViewById(R.id.draw_activity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, t, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //sickness checker
                    case R.id.nav_check:
                        Intent c_check = new Intent(MainActivityNotebookLanding.this, MainActivitySickChecker.class);
                        startActivity(c_check);
                        break;

                    //consultant
                    case R.id.nav_check_triage:
                        Intent c_check_triage = new Intent(MainActivityNotebookLanding.this, WiscPetLoginActivity.class);
                        startActivity(c_check_triage);
                        break;

                    //search for nearby hospital
                    case R.id.nav_hosp:   // search for nearby hospital
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse("https://www.google.com/maps/search/animal+hospital/@43.0665967,-89.4442024,12z/data=!4m8!2m7!3m6!1sanimal+hospital!2sUniversity+of+Wisconsin+School+of+Medicine+and+Public+Health,+750+Highland+Ave,+Madison,+WI+53726!3s0x8807aced2e217f59:0x40c12eea006ffa4a!4m2!1d-89.430191!2d43.0775032"));
                        startActivity(browserIntent);
                        break;

                    //emergency call
                    case R.id.nav_sos:
                        Intent in = new Intent(MainActivityNotebookLanding.this, app_make_phone_call_activity.class);
                        startActivity(in);
                        break;
                    //today's weather
                    case R.id.nav_weather:
                        Intent browserIntentW = new Intent(Intent.ACTION_VIEW);
                        browserIntentW.setData(Uri.parse("https://weather.com/weather/hourbyhour/l/53726:4:US"));
                        startActivity(browserIntentW);
                        break;

                    //add to calender
                    case R.id.nav_events:
                        Intent inE = new Intent(MainActivityNotebookLanding.this, app_event_add_to_calendar.class);
                        startActivity(inE);
                        break;

                    //add to notes
                    case R.id.nav_note:
                        Intent c_note = new Intent(MainActivityNotebookLanding.this, MainActivityNotebookLanding.class);
                        startActivity(c_note);
                        break;

                    //about
                    case R.id.nav_about:
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivityNotebookLanding.this);
                        alertDialogBuilder.setMessage("This app provides information regarding your pets' health." +
                                " Veterinarians and pet owners can use the Consultation page for doctor's visit." +
                                " The notebook features allows users to take notes about their pet ");
                        alertDialogBuilder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        //  Toast.makeText(MainActivity.this,"You clicked yes    button",Toast.LENGTH_LONG).show();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        break;

                    //Under connection-share
                    case R.id.nav_share:
                        Intent myIntent = new Intent(Intent.ACTION_SEND);
                        myIntent.setType("text/plain");
                        startActivity(Intent.createChooser(myIntent, "SHARE USING"));
                        break;
                    //Under connection-contact us
                    case R.id.nav_cntus:
                        Intent c_us = new Intent(MainActivityNotebookLanding.this, app_activity_contact_us.class);
                        startActivity(c_us);
                        break;


                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
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
