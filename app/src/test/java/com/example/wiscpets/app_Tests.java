package com.example.wiscpets;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wiscpets.app.app_activity_contact_us;
import com.example.wiscpets.app.app_event_add_to_calendar;
import com.example.wiscpets.app.app_home;
import com.example.wiscpets.app.app_make_phone_call_activity;
import com.example.wiscpets.app.app_preference_activity;
import com.example.wiscpets.app.app_settings_activity;

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


import java.util.Calendar;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class app_Tests {

    private app_activity_contact_us contactUs;
    private app_event_add_to_calendar calendar;
    private app_home home;
    private app_make_phone_call_activity phoneCall;

    @Before
    public void setup() {
        contactUs = Robolectric.buildActivity(app_activity_contact_us.class).create().resume().get();
        calendar = Robolectric.buildActivity(app_event_add_to_calendar.class).create().resume().get();
        phoneCall = Robolectric.buildActivity(app_make_phone_call_activity.class).create().resume().get();
    }

    ////////////////
    // CONTACT US //
    ////////////////

    @Test
    public void contactPageLoads() {
        assertNotNull(contactUs);
    }

    @Test
    public void textPhoneLinkTest() {
        TextView phoneLink = contactUs.findViewById(R.id.call_cs);
        phoneLink.performClick();

        String num = "tel:1234567890";
        Intent expected = new Intent(Intent.ACTION_CALL, Uri.parse(num));
        Intent actual = Shadows.shadowOf(contactUs).getNextStartedActivity();

        assertEquals(expected.getDataString(), actual.getDataString());
    }

    @Test
    public void imagePhoneLinkTest() {
        ImageView phoneLink = contactUs.findViewById(R.id.final_call);
        phoneLink.performClick();

        String num = "tel:1234567890";
        Intent expected = new Intent(Intent.ACTION_CALL, Uri.parse(num));
        Intent actual = Shadows.shadowOf(contactUs).getNextStartedActivity();

        assertEquals(expected.getDataString(), actual.getDataString());
    }

    @Test
    public void textMailLinkTest() {
        TextView mailLink = contactUs.findViewById(R.id.mail_cs);
        mailLink.performClick();

        Intent expected = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "hotline@petscenter.wisc.edu"));
        Intent actual = Shadows.shadowOf(contactUs).getNextStartedActivity();

        assertEquals(expected.getDataString(), actual.getDataString());
    }

    @Test
    public void imageMailLinkTest() {
        ImageView mailLink = contactUs.findViewById(R.id.final_mail);
        mailLink.performClick();

        Intent expected = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "hotline@petscenter.wisc.edu"));
        Intent actual = Shadows.shadowOf(contactUs).getNextStartedActivity();

        assertEquals(expected.getDataString(), actual.getDataString());
    }

    @Test
    public void textMapLinkTest() {
        TextView mailLink = contactUs.findViewById(R.id.map_cs);
        mailLink.performClick();

        Intent expected = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/University+of+Wisconsin+School+of+Medicine+and+Public+Health/@43.0775032,-89.430191,15z/data=!4m5!3m4!1s0x0:0x40c12eea006ffa4a!8m2!3d43.0775032!4d-89.430191"));
        Intent actual = Shadows.shadowOf(contactUs).getNextStartedActivity();

        assertEquals(expected.getDataString(), actual.getDataString());
    }

    @Test
    public void imageMapLinkTest() {
        ImageView mailLink = contactUs.findViewById(R.id.final_map);
        mailLink.performClick();

        Intent expected = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/University+of+Wisconsin+School+of+Medicine+and+Public+Health/@43.0775032,-89.430191,15z/data=!4m5!3m4!1s0x0:0x40c12eea006ffa4a!8m2!3d43.0775032!4d-89.430191"));
        Intent actual = Shadows.shadowOf(contactUs).getNextStartedActivity();

        assertEquals(expected.getDataString(), actual.getDataString());
    }

    //////////////
    // CALENDAR //
    //////////////

    @Test
    public void calendarPageLoads() {
        assertNotNull(calendar);
    }

    @Test
    public void setEventTest() {
        EditText eventName = calendar.findViewById(R.id.event_edit_name);
        DatePicker eventDate = calendar.findViewById(R.id.event_pick_date);

        eventName.setText("Test Event");
        eventDate.updateDate(2020, 12, 25);
        calendar.setevent(calendar.findViewById(R.id.event_edit_done));

        Calendar cal = Calendar.getInstance();
        Intent expected = new Intent(Intent.ACTION_EDIT);
        expected.setType("vnd.android.cursor.item/event");
        expected.putExtra("beginTime", cal.getTimeInMillis());
        expected.putExtra("allDay", false);
        expected.putExtra("rrule", "FREQ=DAILY");
        expected.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        expected.putExtra("title", "Test Event");

        Intent actual = Shadows.shadowOf(calendar).getNextStartedActivity();

        assertEquals(expected.getExtras(), actual.getExtras());
    }

    //////////
    // HOME //
    //////////

    @Test
    public void homePageLoads() {
        home = Robolectric.buildActivity(app_home.class).create().resume().get();
        assertNotNull(home);
    }

    @Test
    public void waitAndGoTest() {
        Intent expected = new Intent(home, WiscPetLoginActivity.class);

        home = Robolectric.buildActivity(app_home.class).create().resume().get();
        SystemClock.sleep(3000);
        Intent actual = Shadows.shadowOf(home).getNextStartedActivity();
    }

    /////////////////////
    // MAKE PHONE CALL //
    /////////////////////

    @Test
    public void phonePageLoads() {
        assertNotNull(phoneCall);
    }

    @Test
    public void noNumberCallTest() {
        ImageView call = phoneCall.findViewById(R.id.imageView3);
        call.performClick();

        String message = ShadowToast.getTextOfLatestToast();

        assertEquals("enter valid phone number", message.toLowerCase());
    }

    @Test
    public void CallPassTest() {
        EditText phoneNum = phoneCall.findViewById(R.id.editText2);
        ImageView call = phoneCall.findViewById(R.id.imageView3);

        phoneNum.setText("1234561234");
        call.performClick();

        Intent expected = new Intent(Intent.ACTION_CALL, Uri.parse("tel:1234561234"));
        Intent actual = Shadows.shadowOf(phoneCall).getNextStartedActivity();

        assertEquals(expected.getDataString(), actual.getDataString());
    }
}
