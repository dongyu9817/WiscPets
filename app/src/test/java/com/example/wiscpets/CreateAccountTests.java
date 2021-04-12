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

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class CreateAccountTests {

    private CreateAccountActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(CreateAccountActivity.class).create().resume().get();
    }

    @Test
    public void createAccountRealInfoTest() {
        EditText user = (EditText) activity.findViewById(R.id.username_field);
        EditText pass = (EditText) activity.findViewById(R.id.input_password);
        EditText phone = (EditText) activity.findViewById(R.id.input_phone);
        EditText name = (EditText) activity.findViewById(R.id.input_name);
        EditText addr = (EditText) activity.findViewById(R.id.input_address);

        Button create = (Button) activity.findViewById(R.id.signup_Button);

        user.setText("test@email");
        pass.setText("APassword");
        phone.setText("1234567890");
        name.setText("Test");
        addr.setText("My house");

        create.performClick();
        Intent expected = new Intent(activity, WiscPetLoginActivity.class);
        Intent actual = Shadows.shadowOf(activity).getNextStartedActivity();

        assertEquals(expected.getComponent().getClassName(), actual.getComponent().getClassName());
    }
}
