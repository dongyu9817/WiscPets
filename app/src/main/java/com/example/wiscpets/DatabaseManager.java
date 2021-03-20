package com.example.wiscpets;

import android.util.Base64;

import org.json.JSONObject;

public class DatabaseManager {

    public String createToken(String username, String password) {
        String toEncode = username + ":" + password;
        return Base64.encodeToString(toEncode.getBytes(), Base64.DEFAULT);
    }

    public JSONObject validate(String username, String password) {
        String requestType = "GET";
        return null;
    }

    public JSONObject getSicknesses(String species, String[] symptoms) {
        String requestType = "GET";
        return null;
    }

    public JSONObject getPrescriptions(String token, int petId) {
        String requestType = "GET";
        return null;
    }

    public JSONObject getAppointments(String token) {
        String requestType = "GET";
        return null;
    }

    public JSONObject getPetTable() {
        String requestType = "GET";
        return null;
    }

    public JSONObject getPetData(String token, int petId) {
        String requestType = "GET";
        return null;
    }

    public boolean modifyAccount(String username, String password, long phoneNumber) {
        String requestType = "PUT";
        return false;
    }

    public boolean addPrescription(String medName, String dosage, String timePeriod) {
        String requestType = "POST";
        return false;
    }

    public boolean addAppointment(String token, int petId, String vetToken) {
        String requestType = "POST";
        return false;
    }

    public boolean addPet(String token, String species, String breed, String birthday) {
        String requestType = "POST";
        return false;
    }

    public boolean modifyPrescription(String token, int petId, String prescription, String dosage) {
        String requestType = "PUT";
        return false;
    }

    public boolean modifyAppointment(String token, String oldDate, String newDate) {
        String requestType = "PUT";
        return false;
    }

    public boolean modifyPet(String token, int petId, String name, String breed, String birthday) {
        String requestType = "PUT";
        return false;
    }

    public boolean deletePet(String token, int petId) {
        String requestType = "DELETE";
        return false;
    }

    public boolean deletePrescription(String token, int petId, String prescription) {
        String requestType = "DELETE";
        return false;
    }

    public boolean deleteAccount(String token) {
        String requestType = "DELETE";
        return false;
    }

    public boolean deleteAppointment(String token, String apptDate) {
        String requestType = "DELETE";
        return false;
    }

    public boolean addAccount(String email, String password, String name, long phoneNumber, String address) {
        String requestType = "POST";
        return false;
    }
}
