package com.example.wiscpets;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout ;
import com.google.android.material.navigation.NavigationView;

import com.example.wiscpets.app.app_activity_contact_us;
import com.example.wiscpets.app.app_event_add_to_calendar;
import com.example.wiscpets.app.app_home;
import com.example.wiscpets.app.app_make_phone_call_activity;
import com.example.wiscpets.app.app_settings_activity;
import com.example.wiscpets.sickcheckmodule.activity_symptoms;

public class MainActivitySickChecker extends AppCompatActivity {
    Toolbar t;
    DrawerLayout drawer;
    EditText nametext;
    EditText agetext;
    ImageView enter;
    RadioButton dog;
    RadioButton cat;
    //RadioButton covid;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.draw_activity);
        t = (Toolbar) findViewById(R.id.toolbar);
        nametext = findViewById(R.id.nametext);
        agetext = findViewById(R.id.agetext);
        enter = findViewById(R.id.imageView7);
        dog = findViewById(R.id.dog);
        cat = findViewById(R.id.cat);
       // covid = findViewById(R.id.Covid);

        rg=findViewById(R.id.rg1);
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_data_sync, false);
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String marketPref = sharedPref.getString("sync_frequency", "-1");
        Boolean switchPref = sharedPref.getBoolean("example_switch", false);
        String message = getString(R.string.market_message) + marketPref +
                getString(R.string.recommend_message) + switchPref;
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, t, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView nav = findViewById(R.id.nav_view);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nametext.getText().toString();
                if (name.trim().length() <= 0) {
                    Toast.makeText(MainActivitySickChecker.this, "Enter Pet's Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                String age = agetext.getText().toString();
                if (age.trim().length() <= 0) {
                    Toast.makeText(MainActivitySickChecker.this, "Enter Pet's Age", Toast.LENGTH_SHORT).show();
                    return;
                }
                String selectedType= new String();
                int id=rg.getCheckedRadioButtonId();
                switch(id)
                {
                    case R.id.dog:
                        selectedType = "Dog";
                        break;

                    case R.id.cat:
                        selectedType = "Cat";
                        break;
                        /*
                    case R.id.Covid:  //Covid-19 check
                        selectedType = "Covid";
                        break;
                        */

                }
                Intent symp = new Intent(MainActivitySickChecker.this, activity_symptoms.class);
                startActivity(symp);
                symp.putExtra("name",name);
                symp.putExtra("selectedType",selectedType);
                if (selectedType.length() > 0)
                    startActivity(symp);
                else
                    Toast.makeText(MainActivitySickChecker.this, "Please select pet type", Toast.LENGTH_LONG).show();
            }
        });
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.nav_sos:
                        Intent in = new Intent(MainActivitySickChecker.this, app_make_phone_call_activity.class);
                        startActivity(in);
                    break;
                    case R.id.nav_events:
                        Intent inE = new Intent(MainActivitySickChecker.this, app_event_add_to_calendar.class);
                        startActivity(inE);
                        break;
                    case R.id.nav_share:
                        Intent myIntent = new Intent(Intent.ACTION_SEND);
                        myIntent.setType("text/plain");
                        startActivity(Intent.createChooser(myIntent,"SHARE USING"));
                        break;
                    case R.id.nav_hosp:   // search for nearby hospital
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse("https://www.google.com/maps/search/animal+hospital/@43.0665967,-89.4442024,12z/data=!4m8!2m7!3m6!1sanimal+hospital!2sUniversity+of+Wisconsin+School+of+Medicine+and+Public+Health,+750+Highland+Ave,+Madison,+WI+53726!3s0x8807aced2e217f59:0x40c12eea006ffa4a!4m2!1d-89.430191!2d43.0775032"));
                        startActivity(browserIntent);
                        break;
                    case R.id.nav_note:
                        Intent c_note = new Intent(MainActivitySickChecker.this, MainActivityNotebookLanding.class);
                        startActivity(c_note);
                        break;
                    case R.id.nav_cntus:
                        Intent c_us = new Intent(MainActivitySickChecker.this, app_activity_contact_us.class);
                        startActivity(c_us);
                        break;
                        /*


                    case R.id.nav_check_triage:
                       // Intent c_check_triage = new Intent(MainActivity.this, ERMainLoginActivity.class);
                        Intent c_check_triage = new Intent(MainActivitySickChecker.this, ClinicMainLoginActivity.class);

                        startActivity( c_check_triage);
                        break;
                        */

                    case R.id.nav_weather:
                        Intent browserIntentW = new Intent(Intent.ACTION_VIEW);
                        browserIntentW.setData(Uri.parse("https://weather.com/weather/hourbyhour/l/53726:4:US"));
                        startActivity(browserIntentW);
                      break;
                    case R.id.nav_about:
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivitySickChecker.this);
                        alertDialogBuilder.setMessage("This app provides initial diagnosis about your pets' health." +
                                " Nurse and veterinarian can use Triage for doctor's visit." +
                                " Notes taking, weather forecast, clinic search, event management and phone call functions are provided too. ");
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
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    /**
     * Inflates the menu, and adds items to the action bar if it is present.
     *
     * @param menu Menu to inflate.
     * @return Returns true if the menu inflated.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, app_settings_activity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent tIntent = new Intent(this, app_home.class);
        startActivity(tIntent);
    }
}
