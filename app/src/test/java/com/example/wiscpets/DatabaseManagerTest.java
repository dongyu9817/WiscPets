package com.example.wiscpets;

import android.os.Build;

import org.bouncycastle.pqc.crypto.ExchangePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Random;

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
    private Random rand;

    @Before
    public void setUp(){
        db = new DatabaseManager();
        rand = new Random();
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
    public void accountCreateTest() {
        int userAppend = rand.nextInt(100000);
        boolean result = db.addAccount("new" + userAppend + "@email", "mypass", "Test", "6081237654", "123 Msdfaasdfain St");
        assertTrue(result);
    }

    @Test
    public void userIdGetPassTest() {
        String ownerid = db.getUserId("thomas@hotmail.com", "guessablePassword123");
        assertEquals("1", ownerid);
    }

    @Test
    public void userIdGetFailTest() {
        String ownerid = db.getUserId("not a username", "not a password");
        assertEquals("-1", ownerid);
    }

    @Test
    public void getSicknessesPassTest() {
        String[] syms = new String[1];
        syms[0] = "vomiting";

        try {
            JSONObject result = db.getSicknesses("dog", syms);

            assertEquals("Vomiting should be in the database", "success", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void getSicknessesBadSymptomFailTest() {
        String[] syms = new String[1];
        syms[0] = "screaming";

        try {
            JSONObject result = db.getSicknesses("dog", syms);

            System.out.println(result.getString("message"));

            assertEquals("Screaming should not be in the database", "failure", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void getSicknessesBadSpeciesFailTest() {
        String[] syms = new String[1];
        syms[0] = "screaming";

        try {
            JSONObject result = db.getSicknesses("ostrich", syms);

            System.out.println(result.getString("message"));

            assertEquals("Ostrich should not be in the database", "failure", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void getPrescriptionsPassTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String petId = "14";

        try {
            JSONObject result = db.getPrescriptions(token, petId);
            assertEquals( "success", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void getPrescriptionsBadTokenFailTest() {
        String token = "tokenisbad";
        String petId = "14";

        try {
            JSONObject result = db.getPrescriptions(token, petId);
            assertEquals( "failure", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void getPrescriptionsBadPetIdFailTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String petId = "-3";

        try {
            JSONObject result = db.getPrescriptions(token, petId);
            assertEquals( "failure", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void getApptsPassTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";

        try {
            JSONObject result = db.getAppointments(token);

            assertEquals("success", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void getApptsBadTokenFailTest() {
        String token = "tokenisbad";

        try {
            JSONObject result = db.getAppointments(token);

            assertEquals("failure", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void getPetTableTest() {
        //TODO: Test contents of table when request is built in DB
        try {
            JSONObject result = db.getPetTable();

            assertEquals("success", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void getPetDataPassTest() {
        //String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String petId = "14";

        try {
            //JSONObject result = db.getPetData(token, petId);
            JSONObject result = db.getPetData(petId);

            assertEquals("success", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    /*
    @Test
    public void getPetDataBadTokenFailTest() {
        String token = "tokenisbad";
        String petId = "14";

        try {
            JSONObject result = db.getPetData(token, petId);

            assertEquals("failure", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

     */

    @Test
    public void getPetDataBadPetIdFailTest() {
        //String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String petId = "-3";

        try {
            //JSONObject result = db.getPetData(token, petId);
            JSONObject result = db.getPetData(petId);

            assertEquals("failure", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception thrown.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void getWeatherDefaultPassTest() {
        try {
            JSONObject result = db.getWeather();
            assertEquals("success", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception Thrown");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void getWeatherSpecPassTest() {
        try {
            JSONObject result = db.getWeather("Eau Claire", "Wisconsin", "USA");
            assertEquals("success", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception Thrown");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void getWeatherSpecNumbersFailTest() {
        try {
            JSONObject result = db.getWeather("123", "123", "123");
            assertEquals("failure", result.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
            fail("Exception Thrown");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    /////////////////////////////////////////
    // MODIFY/ADD/DELETE REQUESTS ARE LAST //
    /////////////////////////////////////////

    @Test
    public void addPrescriptionPassTest() {
        try {
            boolean result = db.addPrescription("1", "14", "Test", "Test", "Jan 1", "Dec 31");

            assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void addPrescriptionMismatchFailTest() {
        try {
            boolean result = db.addPrescription("1", "22", "Test", "Test", "Jan 1", "Dec 31");

            assertFalse("The ownerid and petid should not correspond", result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void addPrescriptionBadOwnerFailTest() {
        try {
            boolean result = db.addPrescription("-1", "14", "Test", "Test", "Jan 1", "Dec 31");

            assertFalse("The ownerid doesn't exist", result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void addPrescriptionBadPetFailTest() {
        try {
            boolean result = db.addPrescription("1", "-1", "Test", "Test", "Jan 1", "Dec 31");

            assertFalse("The petid doesn't exist", result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void addApptPassTest() {
        try {
            boolean result = db.addAppointment("1", "14", "3", "Jan 31", "Test", "0800");
            assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void addApptBadOwnerFailTest() {
        try {
            boolean result = db.addAppointment("-1", "14", "3", "Jan 31", "Test", "0800");
            assertFalse("OwnerId doesn't exist", result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void addApptBadPetFailTest() {
        try {
            boolean result = db.addAppointment("1", "-1", "3", "Jan 31", "Test", "0800");
            assertFalse("PetId doesn't exist", result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void addApptBadVetFailTest() {
        try {
            boolean result = db.addAppointment("1", "14", "-3", "Jan 31", "Test", "0800");
            assertFalse("VetId doesn't exist", result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void addApptBadMismatchTest() {
        try {
            boolean result = db.addAppointment("1", "22", "3", "Jan 31", "Test", "0800");
            assertFalse("OwnerId and petId do not correspond", result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void addPetPassTest() {
        try {
            boolean result = db.addPet("1", "dog", "Test", "Poodle", "Jan 1");
            assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void addPetBadOwnerTest() {
        try {
            boolean result = db.addPet("-1", "dog", "Test", "Poodle", "Jan 1");
            assertFalse("OwnerId doesn't exist", result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void modifyApptPassTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        try {
            db.modifyAppointment(token, "", "");

            fail("THIS TEST IS NOT FINISHED BECAUSE THE BACKEND REQUEST IS NOT FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void modifyApptBadTokenFailTest() {
        String token = "tokenisbad";

        try {
            db.modifyAppointment(token, "", "");

            fail("THIS TEST IS NOT FINISHED BECAUSE THE BACKEND REQUEST IS NOT FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void modifyApptSameDateFailTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        try {
            db.modifyAppointment(token, "Same date", "Same date");

            fail("THIS TEST IS NOT FINISHED BECAUSE THE BACKEND REQUEST IS NOT FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void modifyPrescriptionPassTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String petId = "14";
        try {
            db.modifyPrescription(token, petId, "", "");

            fail("THIS TEST IS NOT FINISHED BECAUSE THE BACKEND REQUEST IS NOT FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown");
        }
    }

    @Test
    public void modifyPrescriptionBadTokenFailTest() {
        String token = "tokenisbad";
        String petId = "14";

        try {
            db.modifyPrescription(token, petId, "", "");

            fail("THIS TEST IS NOT FINISHED BECAUSE THE BACKEND REQUEST IS NOT FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void modifyApptBadPetIdFailTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String petId = "-2";

        try {
            db.modifyPrescription(token, petId, "", "");

            fail("THIS TEST IS NOT FINISHED BECAUSE THE BACKEND REQUEST IS NOT FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void modifyApptMismatchFailTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String petId = "22";
        try {
            db.modifyPrescription(token, petId, "", "");

            fail("THIS TEST IS NOT FINISHED BECAUSE THE BACKEND REQUEST IS NOT FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void modifyPetPassTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String petId = "14";
        int nameAppend = rand.nextInt(100000);

        try {
            db.modifyPet(token, petId, "Test" + nameAppend, "", "");

            fail("THIS TEST IS NOT FINISHED BECAUSE THE BACKEND REQUEST IS NOT FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void modifyPetBadTokenFailTest() {
        String token = "tokenisbad";
        String petId = "14";
        int nameAppend = rand.nextInt(100000);

        try {
            db.modifyPet(token, petId, "Test" + nameAppend, "", "");

            fail("THIS TEST IS NOT FINISHED BECAUSE THE BACKEND REQUEST IS NOT FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void modifyPetBadPetIdFailTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String petId = "-1";
        int nameAppend = rand.nextInt(100000);

        try {
            db.modifyPet(token, petId, "Test" + nameAppend, "", "");

            fail("THIS TEST IS NOT FINISHED BECAUSE THE BACKEND REQUEST IS NOT FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void modifyPetMismatchFailTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String petId = "22";
        int nameAppend = rand.nextInt(100000);

        try {
            db.modifyPet(token, petId, "Test" + nameAppend, "", "");

            fail("THIS TEST IS NOT FINISHED BECAUSE THE BACKEND REQUEST IS NOT FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void deletePetPassTest() {
        //TODO: Implement when test pet is added to DB

        fail("AS OF RIGHT NOW, IT IS UNSAFE TO DELETE ENTRIES IN DB");
    }

    @Test
    public void deletePetBadPetIdFailTest() {
        String petId = "-1";

        try {
            boolean result = db.deletePet(petId);
            assertFalse("PetId doesn't exist", result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void deletePrescriptionPassTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String petId = "14";

        try {
            boolean result = db.deletePrescription(token, petId, "");
            assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void deletePrescriptionBadTokenFailTest() {
        String token = "tokenisbad";
        String petId = "14";

        try {
            boolean result = db.deletePrescription(token, petId, "");
            assertFalse(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void deletePrescriptionBadPetIdTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String petId = "-1";

        try {
            boolean result = db.deletePrescription(token, petId, "");
            assertFalse(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void deletePrescriptionBadPrescriptionTest() {
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String petId = "14";

        try {
            boolean result = db.deletePrescription(token, petId, "bad presc");
            assertFalse(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void deleteAccountPassTest() {
        //TODO
        fail("AS OF RIGHT NOW, IT IS UNSAFE TO DELETE ENTRIES IN DB");
    }

    @Test
    public void deleteAccountBadTokenFailTest() {
        String token = "tokenisbad";

        try {
            boolean result = db.deleteAccount(token);
            assertFalse(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void deleteApptPassTest() {
        //TODO
        fail("AS OF RIGHT NOW, IT IS UNSAFE TO DELETE ENTRIES IN DB");
    }

    @Test
    public void deleteApptBadTokenFailTest() {
        String token = "tokenisbad";

        try {
            boolean result = db.deleteAppointment(token, "date");
            assertFalse(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception Thrown");
        }
    }

    @Test
    public void modifyAccountPassTest() {
        int userAppend = rand.nextInt(100000);
        String token = "dGhvbWFzQGhvdG1haWwuY29tOmd1ZXNzYWJsZVBhc3N3b3JkMTIz";
        String newName = "Thomas" + userAppend;

        try {
            boolean result = db.modifyAccount(token, "", "", newName, "", "", "");

            assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }

    @Test
    public void modifyAccountBadTokenFailTest() {
        String token = "tokenisbad";
        String newName = "Billy";

        try {
            boolean result = db.modifyAccount(token, "", "", newName, "", "", "");

            assertFalse(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown.");
        }
    }


}