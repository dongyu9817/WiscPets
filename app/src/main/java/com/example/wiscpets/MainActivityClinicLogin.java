package com.example.wiscpets;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wiscpets.app.app_settings_activity;
import com.example.wiscpets.clinic_module.AboutERVisitActivity;
import com.google.android.material.navigation.NavigationView;


//This activity is for Clinic
/** login into the Clinic Consult. */
public class MainActivityClinicLogin extends AppCompatActivity {

	Toolbar t;
	DrawerLayout drawer;
	ImageView enter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clinic_consult_login);
		drawer = findViewById(R.id.draw_activity);
		t = (Toolbar) findViewById(R.id.toolbar);
		enter = findViewById(R.id.imageView7);

//		PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
//		PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false);
//		PreferenceManager.setDefaultValues(this, R.xml.pref_data_sync, false);
//		SharedPreferences sharedPref =
//				PreferenceManager.getDefaultSharedPreferences(this);
//		String marketPref = sharedPref.getString("sync_frequency", "-1");
//		Boolean switchPref = sharedPref.getBoolean("example_switch", false);
//		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, t, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//		drawer.addDrawerListener(toggle);
//		toggle.syncState();
//		NavigationView nav = findViewById(R.id.nav_view);
//
//		// Sets the screen orientation to portrait.
//		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//
//
//
//
//		enter.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Intent intent = new Intent(getApplicationContext(), MainActivityClinic.class);
//				EditText usernameText = (EditText) findViewById(R.id.username_field);
//				String username = usernameText.getText().toString().trim();
//				// Obtain password entered
//				EditText passwordText = (EditText) findViewById(R.id.password_field);
//				String password = passwordText.getText().toString().trim();
//				if ((password.trim().length() == 0) || (username.trim().length() == 0))
//				{
//					// Displays error message: incorrect username/password message - empty
//					Toast toast = Toast.makeText(getApplicationContext(), "Please enter user name and password", Toast.LENGTH_SHORT);
//					toast.setGravity(Gravity.BOTTOM, 0, 0);
//					toast.show();
//				}
//
//				/*
//				else {
//					// Check if username and password are correct.
//					UserClient user = userManagerObj.getUser(username, password, mDbHelper);
//					if (user != null && !username.matches("") && !password.matches("") ) { //
//						intent.putExtra("user", user);
//						intent.putExtra("eradmin", erAdmin);
//						startActivityForResult(intent, 0);
//					} else {
//						// Displays error message: incorrect username/password.
//						Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.incorrect_login), Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.BOTTOM, 0, 0);
//						toast.show();
//					}
//				}*/
//			}
//		});
//
//
//		nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//			@Override
//			public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//				switch(menuItem.getItemId())
//				{
//					case R.id.nav_check:
//						Intent c_check = new Intent(ClinicMainLoginActivity.this, MainActivitySickChecker.class);
//						startActivity( c_check);
//						break;
//					case R.id.nav_check_triage:
//						Intent c_check_triage = new Intent(ClinicMainLoginActivity.this, ClinicMainLoginActivity.class);
//						startActivity( c_check_triage);
//						break;
//					case R.id.nav_sos:
//						Intent in = new Intent(ClinicMainLoginActivity.this, app_make_phone_call_activity.class);
//						startActivity(in);
//						break;
//					case R.id.nav_events:
//						Intent inE = new Intent(ClinicMainLoginActivity.this, app_event_add_to_calendar.class);
//						startActivity(inE);
//						break;
//					case R.id.nav_share:
//						Intent myIntent = new Intent(Intent.ACTION_SEND);
//						myIntent.setType("text/plain");
//						startActivity(Intent.createChooser(myIntent,"SHARE USING"));
//						break;
//					case R.id.nav_hosp:   // search for nearby hospital
//						Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//						browserIntent.setData(Uri.parse("https://www.google.com/maps/search/animal+hospital/@43.0665967,-89.4442024,12z/data=!4m8!2m7!3m6!1sanimal+hospital!2sUniversity+of+Wisconsin+School+of+Medicine+and+Public+Health,+750+Highland+Ave,+Madison,+WI+53726!3s0x8807aced2e217f59:0x40c12eea006ffa4a!4m2!1d-89.430191!2d43.0775032"));
//						startActivity(browserIntent);
//						break;
//					case R.id.nav_cntus:
//						Intent c_us = new Intent(ClinicMainLoginActivity.this, app_activity_contact_us.class);
//						startActivity(c_us);
//						break;
//					case R.id.nav_note:
//						//Intent c_note = new Intent(ClinicMainLoginActivity.this, MainActivityNote.class);
//						//startActivity(c_note);
//						break;
//					case R.id.nav_weather:
//						Intent browserIntentW = new Intent(Intent.ACTION_VIEW);
//						browserIntentW.setData(Uri.parse("https://weather.com/weather/hourbyhour/l/53726:4:US"));
//						startActivity(browserIntentW);
//						break;
//					case R.id.nav_about:
//						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ClinicMainLoginActivity.this);
//						alertDialogBuilder.setMessage("This app provides initial diagnosis about your pets' health." +
//								" Nurse and veterinarian can use Triage for doctor's visit." +
//								" Notes taking, weather forecast, clinic search, event management and phone call functions are provided too. ");
//						alertDialogBuilder.setPositiveButton("OK",
//								new DialogInterface.OnClickListener() {
//									@Override
//									public void onClick(DialogInterface arg0, int arg1) {
//											}
//								});
//						AlertDialog alertDialog = alertDialogBuilder.create();
//						alertDialog.show();
//						break;
//				}
//				drawer.closeDrawer(GravityCompat.START);
//				return true;
//			}
//		});
			}

//	public void loginUser(View view) {
//		Intent intent = new Intent(this, MainActivityClinic.class);
//        // Obtain username entered
//		EditText usernameText = (EditText) findViewById(R.id.username_field);
//		String username = usernameText.getText().toString().trim();
//		// Obtain password entered
//		EditText passwordText = (EditText) findViewById(R.id.password_field);
//		String password = passwordText.getText().toString().trim();
//		if ((password.trim().length() == 0) || (username.trim().length() == 0))
//		{
//			// Displays error message: incorrect username/password message - empty
//			Toast toast = Toast.makeText(this, "Please enter user name and password", Toast.LENGTH_SHORT);
//			toast.setGravity(Gravity.BOTTOM, 0, 0);
//			toast.show();
//		}
//		/*
//		else {
//			// Check if username and password are correct.
//			UserClient user = userManagerObj.getUser(username, password, mDbHelper);
//			if (user != null && !username.matches("") && !password.matches("") ) { //
//				intent.putExtra("user", user);
//				intent.putExtra("eradmin", erAdmin);
//				startActivityForResult(intent, 0);
//			} else {
//				// Displays error message: incorrect username/password.
//				Toast toast = Toast.makeText(this, getString(R.string.incorrect_login), Toast.LENGTH_SHORT);
//				toast.setGravity(Gravity.BOTTOM, 0, 0);
//				toast.show();
//			}
//		}*/
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login_activity, menu);
		return true;
	}

	/** Handle events generated from menu */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_about: // go to About page of this App.
				Intent i = new Intent(this, AboutERVisitActivity.class);
				startActivity(i);
				return true;
			case R.id.action_settings:
				Intent intent = new Intent(this, app_settings_activity.class);
				startActivity(intent);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Obtain updated ERAdmin object .	 */
    @Override
    public void onActivityResult(int requestCode, int resultCode,
    		Intent intent) {
    	super.onActivityResult(requestCode, resultCode, intent);
    	//erAdmin = (DocVisitAdmin) intent.getSerializableExtra("eradmin");
    }

	/**
	 * Catches the event after the user returns to this Activity	 */
    @Override
    protected void onResume() {
    	//if (!mDbHelper.isOpen())
    	//	mDbHelper.open();
    	super.onResume();
    }

	/**
	 * Handle the event when user leaves LoginActivity	 */
    @Override
    protected void onPause() {
    	//mDbHelper.close();
    	super.onPause();
    }

	/**
	 * User presses the back button to go back the previous Activity	 */
	@Override
	public void onBackPressed() {
		Intent patientIntent = new Intent(MainActivityClinicLogin.this, MainActivitySickChecker.class);
		startActivity(patientIntent);
		finish();
	}
}
