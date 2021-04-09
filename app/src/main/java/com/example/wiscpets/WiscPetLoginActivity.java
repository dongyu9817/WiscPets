package com.example.wiscpets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.wiscpets.app.app_home;
import com.example.wiscpets.app.app_make_phone_call_activity;
import com.example.wiscpets.app.app_settings_activity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

/**
 * login into the Application.
 */
public class WiscPetLoginActivity extends AppCompatActivity {
    Toolbar t;
    DrawerLayout drawer;
    EditText username;
    EditText password;
    ImageView enter;
    DatabaseManager db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiscpetlogin);
        drawer = findViewById(R.id.draw_activity);
        db = new DatabaseManager();

        t = (Toolbar) findViewById(R.id.toolbar);

        //For account signup
        Button signupBtn = (Button) findViewById(R.id.signup_Button);
        //if signup btn is clicked, go to account create page

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c_check = new Intent(WiscPetLoginActivity.this, CreateAccountActivity.class);
                startActivity(c_check);
            }
        });

        //enter is the login button, it will be clicked after user enters username and password
        enter = findViewById(R.id.imageView7);

        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_data_sync, false);
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String marketPref = sharedPref.getString("sync_frequency", "-1");
        Boolean switchPref = sharedPref.getBoolean("example_switch", false);
        String message = getString(R.string.market_message) + marketPref +
                getString(R.string.recommend_message) + switchPref;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, t, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView nav = findViewById(R.id.nav_view);
        enter.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         EditText user = findViewById(R.id.username_field);
                                         EditText pass = findViewById(R.id.password_field);
                                         String username = user.getText().toString();
                                         String password = pass.getText().toString();
                                         String token = db.createToken(username, password);
                                         boolean loginResult = db.validate(token);
                                         Intent gotoConsultation = new Intent(WiscPetLoginActivity.this, MainActivityConsultationOwners.class);
                                         startActivity(gotoConsultation);

                                         if (loginResult) {
                                             Toast.makeText(getBaseContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                                         } else {
                                             Toast.makeText(getBaseContext(), "Username or Password is incorrect. Try again!", Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                 }
        );


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //sickness checker
                    case R.id.nav_check:
                        Intent c_check = new Intent(WiscPetLoginActivity.this, MainActivitySickChecker.class);
                        startActivity(c_check);
                        break;

                    //consultant
                    case R.id.nav_check_triage:
                        Intent c_check_triage = new Intent(WiscPetLoginActivity.this, WiscPetLoginActivity.class);
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
                        Intent in = new Intent(WiscPetLoginActivity.this, app_make_phone_call_activity.class);
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
                        Intent inE = new Intent(WiscPetLoginActivity.this, app_event_add_to_calendar.class);
                        startActivity(inE);
                        break;

                    //add to notes
                    case R.id.nav_note:
                        Intent c_note = new Intent(WiscPetLoginActivity.this, MainActivityNotebookLanding.class);
                        startActivity(c_note);
                        break;

                    //about
                    case R.id.nav_about:
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WiscPetLoginActivity.this);
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

                    //Under connection-share
                    case R.id.nav_share:
                        Intent myIntent = new Intent(Intent.ACTION_SEND);
                        myIntent.setType("text/plain");
                        startActivity(Intent.createChooser(myIntent, "SHARE USING"));
                        break;
                    //Under connection-contact us
                    case R.id.nav_cntus:
                        Intent c_us = new Intent(WiscPetLoginActivity.this, app_activity_contact_us.class);
                        startActivity(c_us);
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
//public class MainActivity extends AppCompatActivity {
//
//
//    public void clickFunction (View view){
//        Log.i("Info", "button pressed" );
//        EditText myTextField = (EditText) findViewById (R.id.editText2);
//        Toast.makeText(this, myTextField.getText().toString(), Toast.LENGTH_LONG).show();
//        String str = myTextField.getText().toString();
//        gotoActivity2(str);
//    }
//
//    public void gotoActivity2 (String s){
//        Log.i("username mainactivity ", s);
//        Intent intent = new Intent(this,Main2Activity.class);
//        intent.putExtra("message", s);
//        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
//        sharedPreferences.edit().putString("username", s).apply();
//        startActivity(intent);
//
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//       // Intent intent = getIntent();
//
//        String usernameKey = "username";
//        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5" , Context.MODE_PRIVATE);
//
//        if (!sharedPreferences.getString(usernameKey, "").equals("")){
//
//            String login = sharedPreferences.getString(usernameKey, "");
//            Intent intent = new Intent(this, Main2Activity.class);
//            intent.putExtra("message", login);
//            startActivity(intent);
//
//        }
//        else {
//            setContentView(R.layout.activity_main);
//        }
//    }
//}
