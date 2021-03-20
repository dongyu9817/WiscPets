package com.example.wiscpets;

import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DatabaseManager {

    public String createToken(String username, String password) {
        String toEncode = username + ":" + password;
        return Base64.encodeToString(toEncode.getBytes(), Base64.DEFAULT);
    }

    public boolean validate(String token) {
        String requestType = "GET";
        String operation = "validate";

        final String urlInput = "https://oc0oygi074.execute-api.us-east-2.amazonaws.com/dev/wiscpets?operation=" + operation + "&token=" + token;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(urlInput);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // type of request
            connection.setRequestMethod(requestType);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            // authorize, without this we got 403
            //connection.setRequestProperty("x-api-key", "CEimoZkwJ26pfNfvwiXBia08JGoDVrx1aOyz5HHg");
            connection.setDoOutput(false);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                JSONObject result = new JSONObject(response.toString());
                if (result.getString("status").compareTo("success") == 0) {
                    Log.i("account", "db success ver");
                    int returnedCustomerID = Integer.parseInt(result.getString("id"));
                    System.out.println(returnedCustomerID);
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public JSONObject getSicknesses(String species, String[] symptoms) {
        String requestType = "GET";
        String operation = "getSicknesses";
        return null;
    }

    public JSONObject getPrescriptions(String token, int petId) {
        String requestType = "GET";
        String operation = "getPrescriptions";
        return null;
    }

    public JSONObject getAppointments(String token) {
        String requestType = "GET";
        String operation = "getAppointments";
        return null;
    }

    public JSONObject getPetTable() {
        String requestType = "GET";
        String operation = "getPetTable";
        return null;
    }

    public JSONObject getPetData(String token, int petId) {
        String requestType = "GET";
        String operation = "getPetData";
        return null;
    }

    public boolean modifyAccount(String username, String password, long phoneNumber) {
        String requestType = "PUT";
        String operation = "modifyAccount";
        return false;
    }

    public boolean addPrescription(String medName, String dosage, String timePeriod) {
        String requestType = "POST";
        String operation = "addPrescription";
        return false;
    }

    public boolean addAppointment(String token, int petId, String vetToken) {
        String requestType = "POST";
        String operation = "addApointment";
        return false;
    }

    public boolean addPet(String token, String species, String breed, String birthday) {
        String requestType = "POST";
        String operation = "addPet";
        return false;
    }

    public boolean modifyPrescription(String token, int petId, String prescription, String dosage) {
        String requestType = "PUT";
        String operation = "modifyPrescription";
        return false;
    }

    public boolean modifyAppointment(String token, String oldDate, String newDate) {
        String requestType = "PUT";
        String operation = "modifyAppointment";
        return false;
    }

    public boolean modifyPet(String token, int petId, String name, String breed, String birthday) {
        String requestType = "PUT";
        String operation = "modifyPet";
        return false;
    }

    public boolean deletePet(String token, int petId) {
        String requestType = "DELETE";
        String operation = "deletePet";
        return false;
    }

    public boolean deletePrescription(String token, int petId, String prescription) {
        String requestType = "DELETE";
        String operation = "deletePrescription";
        return false;
    }

    public boolean deleteAccount(String token) {
        String requestType = "DELETE";
        String operation = "deleteAccount";
        return false;
    }

    public boolean deleteAppointment(String token, String apptDate) {
        String requestType = "DELETE";
        String operation = "deleteAppointment";
        return false;
    }

    public boolean addAccount(String email, String password, String name, String phoneNumber, String address) {
        String requestType = "POST";
        String operation = "addAccount";
        String operationStr = "{\"operation\":\"" + operation + "\", ";
        String emailStr = "\"email\":\"" + email + "\", ";
        String passStr = "\"password\":\"" + password + "\", ";
        String nameStr = "\"name\":\"" + name + "\", ";
        String phoneStr = "\"phone\":\"" + phoneNumber + "\", ";
        String addrStr = "\"address\":\"" + address + "\", ";
        String roleStr = "\"role\":\"some role\"}";

        String jsoninput = operationStr + emailStr + passStr + nameStr + phoneStr + addrStr + roleStr;

        final String urlInput = "https://oc0oygi074.execute-api.us-east-2.amazonaws.com/dev/wiscpets";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(urlInput);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // type of request
            connection.setRequestMethod(requestType);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            // authorize, without this we got 403
            //connection.setRequestProperty("x-api-key", "CEimoZkwJ26pfNfvwiXBia08JGoDVrx1aOyz5HHg");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsoninput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                JSONObject result = new JSONObject(response.toString());
                if (result.getString("status").compareTo("success") == 0) {
                    Log.i("Account", "Account Created Successfully");
                    int returnedCustomerID = Integer.parseInt(result.getString("id"));
                    System.out.println(returnedCustomerID);
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
