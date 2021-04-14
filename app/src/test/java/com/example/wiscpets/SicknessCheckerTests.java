package com.example.wiscpets;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.wiscpets.app.app_activity_contact_us;
import com.example.wiscpets.app.app_event_add_to_calendar;
import com.example.wiscpets.app.app_home;
import com.example.wiscpets.app.app_make_phone_call_activity;
import com.example.wiscpets.app.app_settings_activity;
import com.example.wiscpets.sickcheckmodule.activity_symptoms;
import com.google.android.material.navigation.NavigationView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowToast;


import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class SicknessCheckerTests {

    MainActivitySickChecker activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivitySickChecker.class).create().resume().get();
    }

    @Test
    public void pageLoaded() {
        assertNotNull(activity);
    }

    @Test
    public void emptyFieldsTest() {
        ImageView enter = activity.findViewById(R.id.imageView7);
        enter.performClick();

        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        assertNull("Should not leave page", actual);
    }

    @Test
    public void emptyNameTest() {
        EditText name = activity.findViewById(R.id.nametext);
        EditText age = activity.findViewById(R.id.agetext);
        RadioButton dogType = activity.dog;

        age.setText("12");
        dogType.toggle();
        ImageView enter = activity.findViewById(R.id.imageView7);
        enter.performClick();

        String message = ShadowToast.getTextOfLatestToast();

        assertEquals("enter pet's name", message.toLowerCase());
    }

    @Test
    public void emptyAgeTest() {
        EditText name = activity.findViewById(R.id.nametext);
        EditText age = activity.findViewById(R.id.agetext);
        RadioButton dogType = activity.dog;

        name.setText("Spot");
        dogType.toggle();
        ImageView enter = activity.findViewById(R.id.imageView7);
        enter.performClick();

        String message = ShadowToast.getTextOfLatestToast();

        assertEquals("enter pet's age", message.toLowerCase());
    }

    @Test
    public void emptyTypeTest() {
        EditText name = activity.findViewById(R.id.nametext);
        EditText age = activity.findViewById(R.id.agetext);

        name.setText("Spot");
        age.setText("12");
        ImageView enter = activity.findViewById(R.id.imageView7);
        enter.performClick();

        String message = ShadowToast.getTextOfLatestToast();

        assertEquals("please select pet type", message.toLowerCase());
    }

    @Test
    public void sickCheckDogPassTest() {
        EditText name = activity.findViewById(R.id.nametext);
        EditText age = activity.findViewById(R.id.agetext);
        RadioButton dogType = activity.dog;

        name.setText("Spot");
        age.setText("12");
        dogType.toggle();
        ImageView enter = activity.findViewById(R.id.imageView7);
        enter.performClick();

        Intent expected = new Intent(activity, activity_symptoms.class);
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }

    @Test
    public void sickCheckCatPassTest() {
        EditText name = activity.findViewById(R.id.nametext);
        EditText age = activity.findViewById(R.id.agetext);
        RadioButton catType = activity.cat;

        name.setText("Spot");
        age.setText("12");
        catType.toggle();
        ImageView enter = activity.findViewById(R.id.imageView7);
        enter.performClick();

        Intent expected = new Intent(activity, activity_symptoms.class);
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }

    ///////////////////////////
    // NAV MENU BUTTON TESTS //
    ///////////////////////////

    @Test
    public void consultantNavButtonHitTest() {
        NavigationView nav = activity.findViewById(R.id.nav_view);
        Menu navMenu = nav.getMenu();

        navMenu.performIdentifierAction(R.id.nav_check_triage, Menu.NONE);

        Intent expected = new Intent(activity, WiscPetLoginActivity.class);
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        if (actual == null) {
            assertNotNull(actual);
        }

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }

    @Test
    public void hospitalSearchNavButtonHitTest() {
        NavigationView nav = activity.findViewById(R.id.nav_view);
        Menu navMenu = nav.getMenu();

        navMenu.performIdentifierAction(R.id.nav_hosp, Menu.NONE);

        Intent expected = new Intent(Intent.ACTION_VIEW);
        expected.setData(Uri.parse("https://www.google.com/maps/search/animal+hospital/@43.0665967,-89.4442024,12z/data=!4m8!2m7!3m6!1sanimal+hospital!2sUniversity+of+Wisconsin+School+of+Medicine+and+Public+Health,+750+Highland+Ave,+Madison,+WI+53726!3s0x8807aced2e217f59:0x40c12eea006ffa4a!4m2!1d-89.430191!2d43.0775032"));
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        if (actual == null) {
            assertNotNull(actual);
        }

        assertEquals(expected.getDataString(), actual.getDataString());
    }

    @Test
    public void emergencyCallNavButtonHitTest() {
        NavigationView nav = activity.findViewById(R.id.nav_view);
        Menu navMenu = nav.getMenu();

        navMenu.performIdentifierAction(R.id.nav_sos, Menu.NONE);

        Intent expected = new Intent(activity, app_make_phone_call_activity.class);
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        if (actual == null) {
            assertNotNull(actual);
        }

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }

    @Test
    public void weatherNavButtonHitTest() {
        NavigationView nav = activity.findViewById(R.id.nav_view);
        Menu navMenu = nav.getMenu();

        navMenu.performIdentifierAction(R.id.nav_weather, Menu.NONE);

        Intent expected = new Intent(Intent.ACTION_VIEW);
        expected.setData(Uri.parse("https://weather.com/weather/hourbyhour/l/53726:4:US"));
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        if (actual == null) {
            assertNotNull(actual);
        }

        assertEquals(expected.getDataString(), actual.getDataString());
    }

    @Test
    public void calendarNavButtonHitTest() {
        NavigationView nav = activity.findViewById(R.id.nav_view);
        Menu navMenu = nav.getMenu();

        navMenu.performIdentifierAction(R.id.nav_events, Menu.NONE);

        Intent expected = new Intent(activity, app_event_add_to_calendar.class);
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        if (actual == null) {
            assertNotNull(actual);
        }

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }

    @Test
    public void notebookNavButtonHitTest() {
        NavigationView nav = activity.findViewById(R.id.nav_view);
        Menu navMenu = nav.getMenu();

        navMenu.performIdentifierAction(R.id.nav_note, Menu.NONE);

        Intent expected = new Intent(activity, MainActivityNotebookLanding.class);
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        if (actual == null) {
            assertNotNull(actual);
        }

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }

    @Test
    public void aboutNavButtonHitTest() {
        /*
        Figure out how to get message from pop up dialog
         */
        NavigationView nav = activity.findViewById(R.id.nav_view);
        Menu navMenu = nav.getMenu();

        navMenu.performIdentifierAction(R.id.nav_about, Menu.NONE);

        ShadowAlertDialog shadowAlert = new ShadowAlertDialog();
        String message;

        if (ShadowAlertDialog.getLatestAlertDialog() != null) {
            message = (String) shadowAlert.getMessage();
        } else {
            message = "";
        }

        assertEquals("This app provides initial diagnosis about your pets' health. Nurse and veterinarian can use Triage for doctor's visit." +
                " Notes taking, weather forecast, clinic search, event management and phone call functions are provided too. ", message);
    }

    @Test
    public void shareNavButtonHitTest() {
        NavigationView nav = activity.findViewById(R.id.nav_view);
        Menu navMenu = nav.getMenu();

        navMenu.performIdentifierAction(R.id.nav_share, Menu.NONE);

        Intent sent = new Intent(Intent.ACTION_SEND);
        sent.setType("text/plain");
        Intent expected = Intent.createChooser(sent, "SHARE USING");
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        if (actual == null) {
            assertNotNull(actual);
        }

        assertEquals(expected.getDataString(), actual.getDataString());
    }

    @Test
    public void contactNavButtonHitTest() {
        NavigationView nav = activity.findViewById(R.id.nav_view);
        Menu navMenu = nav.getMenu();

        navMenu.performIdentifierAction(R.id.nav_cntus, Menu.NONE);

        Intent expected = new Intent(activity, app_activity_contact_us.class);
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        if (actual == null) {
            assertNotNull(actual);
        }

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }

    /////////////////////////////////
    // SETTINGS/OTHER BUTTON TESTS //
    /////////////////////////////////

    @Test
    public void backButtonHitTest() {
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        activity.onBackPressed();
        Intent expected = new Intent(activity, app_home.class);
        Intent actual = shadowActivity.getNextStartedActivity();

        if (actual == null) {
            assertNotNull(actual);
        }

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }

    @Test
    public void menuInflationTest() {
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Menu optionsMenu = shadowActivity.getOptionsMenu();

        assertNotNull(optionsMenu);
    }

    @Test
    public void settingsButtonHitTest() {
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Menu optionsMenu = shadowActivity.getOptionsMenu();

        assertNotNull(optionsMenu);

        optionsMenu.performIdentifierAction(R.id.action_settings, Menu.NONE);

        Intent expected = new Intent(activity, app_settings_activity.class);
        Intent actual = shadowActivity.getNextStartedActivity();

        if (actual == null) {
            assertNotNull(actual);
        }

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }
}
