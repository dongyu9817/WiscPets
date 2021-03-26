package com.example.wiscpets;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DatabaseManagerTest {
    @Test
    public void testAccountCreate() {
        DatabaseManager db = new DatabaseManager();
        boolean result = db.addAccount("jake@email", "mypass", "Jake", "6081237654", "123 Main St");
        System.out.println(result);
    }
}