package com.example.wiscpets;

import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.Random;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class CreateAccountTests {

    private CreateAccountActivity activity;
    private Random rand;
    private int userAppend;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(CreateAccountActivity.class).create().resume().get();
        rand = new Random();
    }

    @Test
    public void createAccountRealInfoTest() {
        userAppend = rand.nextInt(100000);
        EditText user = (EditText) activity.findViewById(R.id.username_field);
        EditText pass = (EditText) activity.findViewById(R.id.input_password);
        EditText phone = (EditText) activity.findViewById(R.id.input_phone);
        EditText name = (EditText) activity.findViewById(R.id.input_name);
        EditText addr = (EditText) activity.findViewById(R.id.input_address);

        Button create = (Button) activity.findViewById(R.id.signup_Button);

        user.setText("test" + userAppend + "@email");
        pass.setText("APassword");
        phone.setText("1234567890");
        name.setText("Test");
        addr.setText("My house");

        create.performClick();
        Intent expected = new Intent(activity, WiscPetLoginActivity.class);
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }

    @Test
    public void createAccountShortPhoneTest() {
        userAppend = rand.nextInt(100000);
        EditText user = (EditText) activity.findViewById(R.id.username_field);
        EditText pass = (EditText) activity.findViewById(R.id.input_password);
        EditText phone = (EditText) activity.findViewById(R.id.input_phone);
        EditText name = (EditText) activity.findViewById(R.id.input_name);
        EditText addr = (EditText) activity.findViewById(R.id.input_address);

        Button create = (Button) activity.findViewById(R.id.signup_Button);

        user.setText("test" + userAppend + "@email");
        pass.setText("APassword");
        phone.setText("1234567");
        name.setText("Test");
        addr.setText("My house");

        create.performClick();

        assertEquals("Invalid phone number: Please include Area Code", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void createAccountNegPhoneTest() {
        userAppend = rand.nextInt(100000);
        EditText user = (EditText) activity.findViewById(R.id.username_field);
        EditText pass = (EditText) activity.findViewById(R.id.input_password);
        EditText phone = (EditText) activity.findViewById(R.id.input_phone);
        EditText name = (EditText) activity.findViewById(R.id.input_name);
        EditText addr = (EditText) activity.findViewById(R.id.input_address);

        Button create = (Button) activity.findViewById(R.id.signup_Button);

        user.setText("test" + userAppend + "@email");
        pass.setText("APassword");
        phone.setText("-1234567123");
        name.setText("Test");
        addr.setText("My house");

        create.performClick();

        assertEquals("Invalid phone number: Please include Area Code", ShadowToast.getTextOfLatestToast());
    }
}
