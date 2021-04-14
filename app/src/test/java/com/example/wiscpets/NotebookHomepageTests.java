package com.example.wiscpets;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class NotebookHomepageTests {

    private MainActivityNotebookLanding activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivityNotebookLanding.class).create().resume().get();
    }

    @Test
    public void pageOpened() {
        assertNotNull(activity);
    }


}
