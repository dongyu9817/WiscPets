package com.example.wiscpets;

import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class LoginTest {

    private WiscPetLoginActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(WiscPetLoginActivity.class).create().resume().get();
    }

    @Test
    public void loginSuccessTest() {
        EditText user = activity.findViewById(R.id.username_field);
        EditText pass = activity.findViewById(R.id.password_field);
        ImageView loginButton = activity.findViewById(R.id.imageView7);

        user.setText("thomas@hotmail.com");
        pass.setText("guessablePassword123");

        loginButton.performClick();
        Intent expected = new Intent(activity, MainActivityConsultationOwners.class);
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }

    @Test
    public void loginFailedTest() {
        EditText user = activity.findViewById(R.id.username_field);
        EditText pass = activity.findViewById(R.id.password_field);
        ImageView loginButton = activity.findViewById(R.id.imageView7);

        user.setText("not a real username");
        pass.setText("not a real password");

        loginButton.performClick();
        assertEquals("Username or Password is incorrect. Try again!", ShadowToast.getTextOfLatestToast());
    }
}
