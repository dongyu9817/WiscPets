package com.example.wiscpets;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class DatabaseManagerTest {

    private DatabaseManager db;

    @Before
    public void setUp(){
        db = new DatabaseManager();
    }

    @Test
    public void createTokenTest(){
        //DatabaseManager db = new DatabaseManager();
        String token = db.createToken("thomas@hotmail.com", "guessablePassword123");
        assertEquals("dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz\n", token);
    }


    @Test
    public void validateTest(){
        //DatabaseManager db = new DatabaseManager();
        String token = db.createToken("thomas@hotmail.com", "guessablePassword123");
        boolean result = db.validate(token);
        assertTrue(result);
    }


    @Test
    public void testAccountCreate() {
        DatabaseManager db = new DatabaseManager();
        boolean result = db.addAccount("newe123@email", "mypass", "Jakesafasdf", "6081237654", "123 Msdfaasdfain St");
        //System.out.println(result);
        assertTrue(result);
    }


}