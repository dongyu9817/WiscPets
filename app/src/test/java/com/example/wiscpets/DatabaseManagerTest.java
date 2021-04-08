package com.example.wiscpets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DatabaseManagerTest {

    @Mock
    private DatabaseManager db;

    @Before
    public void setUp(){
        db = new DatabaseManager();

    }

    @Test
    public void createTokenTest(){
        //DatabaseManager db = new DatabaseManager();
        String token = db.createToken("thomas@hotmail.com", "guessablePassword123");
        assertEquals("dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz", token);
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
        boolean result = db.addAccount("newe@email", "mypass", "Jakesafasdf", "6081237654", "123 Msdfaasdfain St");
        //System.out.println(result);
        assertTrue(result);
    }


}