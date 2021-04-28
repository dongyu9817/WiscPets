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
import java.nio.charset.StandardCharsets;

public class DatabaseManager {

    public String createToken(String username, String password) {
        String toEncode = username + ":" + password;
        return Base64.encodeToString(toEncode.getBytes(), Base64.DEFAULT);
    }

    public String getUserId(String username, String password) {
        String token = createToken(username, password);
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
            connection.setRequestProperty("x-api-key", "SeVNjubzye8jacTBCdhH64qg58NG05sQ2Z8GDDgc");
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
                    Log.i("Request Status", "validate Success");
                    int returnedCustomerID = Integer.parseInt(result.getString("id"));
                    System.out.println(returnedCustomerID);
                    return result.getString("id");
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
        // Failure
        Log.i("Request Status", "validate Failure");
        return "-1";
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
            connection.setRequestProperty("x-api-key", "SeVNjubzye8jacTBCdhH64qg58NG05sQ2Z8GDDgc");
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
                    Log.i("Request Status", "validate Success");
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
        Log.i("Request Status", "validate Failure");
        return false;
    }


    public JSONObject getSicknesses(String species, String[] symptoms) {
        String requestType = "GET";
        String operation = "getSicknesses";
        StringBuilder jsonInputBuilder = new StringBuilder("{\"symptoms\":[");
        // Build symptoms entry in JSON Body
        for (int sym = 0; sym < symptoms.length; sym++) {
            if (sym != symptoms.length - 1) {
                jsonInputBuilder.append("\"").append(symptoms[sym]).append("\",");
                continue;
            }
            jsonInputBuilder.append("\"").append(symptoms[sym]).append("\"], ");
        }
        jsonInputBuilder.append("\"species\":\"").append(species).append("\"}");
        String jsonInput = jsonInputBuilder.toString();
        Log.i("Input", jsonInput);

        final String urlInput = "https://oc0oygi074.execute-api.us-east-2.amazonaws.com/dev/wiscpets?operation=" + operation;
        final JSONObject[] result = new JSONObject[1];

        // Prevents Main thread use errors
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(urlInput);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //type of request
            connection.setRequestMethod(requestType);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            // authorize, without this we got 403
            connection.setRequestProperty("x-api-key", "SeVNjubzye8jacTBCdhH64qg58NG05sQ2Z8GDDgc");
            connection.setDoOutput(true);

            // Output stream for request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result[0] = new JSONObject(response.toString());
                if (result[0].getString("status").equals("success")) {
                    Log.i("Request Status", "getSicknesses Success");
                } else {
                    Log.i("Request Status", "getSicknesses Failure");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result[0];
    }


    public JSONObject getPrescriptions(String token, String petId) {
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
        final String urlInput = "https://oc0oygi074.execute-api.us-east-2.amazonaws.com/dev/wiscpets?operation=" + operation;
        final JSONObject[] result = new JSONObject[1];

        // Prevents Main thread use errors
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(urlInput);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //type of request
            connection.setRequestMethod(requestType);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            // authorize, without this we got 403
            connection.setRequestProperty("x-api-key", "SeVNjubzye8jacTBCdhH64qg58NG05sQ2Z8GDDgc");
            connection.setDoOutput(true);

            // Output stream for request body
            /*
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

             */

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result[0] = new JSONObject(response.toString());
                if (result[0].getString("status").equals("success")) {
                    Log.i("Request Status", "getPetTable Success");
                } else {
                    Log.i("Request Status", "getPetTable Failure");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result[0];
    }

    // THIS IS THE SAME AS getPetInfo
    public JSONObject getPetData(String petId) {
        String requestType = "GET";
        String operation = "getPetInfo";
        final String urlInput = "https://oc0oygi074.execute-api.us-east-2.amazonaws.com/dev/wiscpets?operation=" + operation + "&petid" + petId;
        final JSONObject[] result = new JSONObject[1];

        // Prevents Main thread use errors
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(urlInput);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //type of request
            connection.setRequestMethod(requestType);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            // authorize, without this we got 403
            connection.setRequestProperty("x-api-key", "SeVNjubzye8jacTBCdhH64qg58NG05sQ2Z8GDDgc");
            connection.setDoOutput(true);

            // Output stream for request body
            /*
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

             */

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result[0] = new JSONObject(response.toString());
                if (result[0].getString("status").equals("success")) {
                    Log.i("Request Status", "getPetInfo/getPetData Success");
                } else {
                    Log.i("Request Status", "getPetInfo/getPetData Failure");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result[0];
    }


    public JSONObject getVitals(String petId){
        String requestType = "GET";
        String operation = "getPetVitals";
        return null;
    }


    public boolean modifyAccount(String token, String email, String pass, String name, String phone, String address, String role) {
        //String requestType = "PUT";
        //String operation = "modifyAccount";
        String operationString = "{\"operation\":\"modifyAccount\", ";
        String tokenString = "\"token\":\"" + token + "\", ";
        String emailString = "\"email\":\"" + email + "\", ";
        String passString = "\"password\":\"" + pass + "\", ";
        String roleString = "\"role\":\"" + role + "\", ";
        String phoneString = "\"phone\":\"" + phone + "\", ";
        String addressString = "\"address\":\"" + address + "\", ";
        String nameString = "\"name\":\"" + name + "\"}";

        final boolean[] valid = {false};
        String jsonInputString = operationString + tokenString + emailString + passString + roleString + phoneString + addressString + nameString;
        Log.i("Input", jsonInputString);
        try {
            URL url = new URL("https://oc0oygi074.execute-api.us-east-2.amazonaws.com/dev/wiscpets");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // type of request
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            // authorize, without this we got 403
            connection.setRequestProperty("x-api-key", "SeVNjubzye8jacTBCdhH64qg58NG05sQ2Z8GDDgc");
            connection.setDoOutput(true);
            // Output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                JSONObject result = new JSONObject(response.toString());
                if (result.getString("status").compareTo("success") == 0) {
                    Log.i("user", "modifyAccount Success");
                    valid[0] = true;
                } else {
                    Log.i("Request Status", "modifyAccount Failure");
                }
                System.out.println(response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException | ProtocolException e) {
            System.out.println("URL ISSUE\n");
            //textView2.setText( "Malformed url or protocol exception" );
            e.printStackTrace();
        } catch (IOException ec) {
            //textView2.setText( "IO exception" );
            ec.printStackTrace();
        }
        return valid[0];
    }

    public boolean addPrescription(String ownerId, String petId, String prescriptionName, String dosage, String startDate, String endDate){
        String requestType = "POST";
        String operation = "addPrescription";
        return false;
    }


    public boolean addAppointment(String ownerId, String petId, String vetId, String date, String reason, String time) {
        String requestType = "POST";
        String operation = "addAppointment";
        String opStr = "{\"operation\":\"" + operation + "\", ";
        String ownerStr = "\"ownerid\":\"" + ownerId + "\", ";
        String petStr = "\"petid\":\"" + petId + "\", ";
        String vetStr = "\"vetid\":\"" + vetId + "\", ";
        String dateStr = "\"dt\":\"" + date + "\", ";
        String reasonStr = "\"reason\":\"" + reason + "\", ";
        String timeStr = "\"time\":\"" + time + "\"}";
        String jsonInput = opStr + ownerStr + petStr + vetStr + dateStr + reasonStr + timeStr;

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
            connection.setRequestProperty("x-api-key", "SeVNjubzye8jacTBCdhH64qg58NG05sQ2Z8GDDgc");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
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
                    Log.i("Request Status", "addAppointment Success");
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
        Log.i("Request Status", "addAppointment Failure");
        return false;
    }


    public boolean addPet(String ownerid, String species, String name, String breed, String birthday) {
        String requestType = "POST";
        String operation = "addPet";
        String opStr = "{\"operation\":\"" + operation + "\", ";
        String idStr = "\"ownerid\":" + ownerid + "\", ";
        String speciesStr = "\"species\":" + species + "\", ";
        String nameStr = "\"name\":" + name + "\", ";
        String breedStr = "\"breed\":" + breed + "\", ";
        String bdayStr = "\"birthday\":" + birthday + "\"}";
        String jsonInput = opStr + idStr + speciesStr + nameStr + breedStr + bdayStr;

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
            connection.setRequestProperty("x-api-key", "SeVNjubzye8jacTBCdhH64qg58NG05sQ2Z8GDDgc");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
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
                    Log.i("Request Status", "addPet Success");
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
        Log.i("Request Status", "addPet Failure");
        return false;
    }


    public boolean modifyPrescription(String token, String petId, String prescription, String dosage) {
        String requestType = "PUT";
        String operation = "modifyPrescription";
        return false;
    }


    public boolean modifyAppointment(String token, String oldDate, String newDate) {
        String requestType = "PUT";
        String operation = "modifyAppointment";
        return false;
    }


    public boolean modifyPet(String token, String petId, String name, String breed, String birthday) {
        String requestType = "PUT";
        String operation = "modifyPet";
        return false;
    }


    public boolean deletePet(String petId) {
        String requestType = "POST";
        String operation = "deletePet";
        String opStr = "{\"operation\":\"" + operation + "\", ";
        String idStr = "\"petid\":\"" + petId + "\"}";
        String jsonInput = opStr + idStr;
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
            connection.setRequestProperty("x-api-key", "SeVNjubzye8jacTBCdhH64qg58NG05sQ2Z8GDDgc");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
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
                    Log.i("Request Status", "deletePet Success");
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
        Log.i("Request Status", "deletePet Failure");
        return false;
    }

    public boolean deletePrescription(String token, String petId, String prescription) {
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
            connection.setRequestProperty("x-api-key", "SeVNjubzye8jacTBCdhH64qg58NG05sQ2Z8GDDgc");
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
                    Log.i("Account", "addAccount Success");
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
        Log.i("Request Status", "addAccount Failure");
        return false;
    }

    public JSONObject getWeather() {
        String requestType = "GET";
        String operation = "getWeather";

        final String urlInput = "https://oc0oygi074.execute-api.us-east-2.amazonaws.com/dev/wiscpets?operation=" + operation;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(urlInput);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // type of request
            connection.setRequestMethod(requestType);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            // authorize, without this we got 403
            connection.setRequestProperty("x-api-key", "SeVNjubzye8jacTBCdhH64qg58NG05sQ2Z8GDDgc");
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
                    Log.i("Request Status", "getWeather Success");
                    return result;
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
        // Failure
        Log.i("Request Status", "getWeather Failure");
        return null;
    }

    public JSONObject getWeather(String city, String state, String country) {
        String requestType = "GET";
        String operation = "getWeather";

        final String urlInput = "https://oc0oygi074.execute-api.us-east-2.amazonaws.com/dev/wiscpets?operation=" + operation + "&city=" + city + "&state=" + state + "&country=" + country;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(urlInput);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // type of request
            connection.setRequestMethod(requestType);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            // authorize, without this we got 403
            connection.setRequestProperty("x-api-key", "SeVNjubzye8jacTBCdhH64qg58NG05sQ2Z8GDDgc");
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
                    Log.i("Request Status", "getWeather Success");
                    return result;
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
        // Failure
        Log.i("Request Status", "getWeather Failure");
        return null;
    }
}
