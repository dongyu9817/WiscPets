package com.example.wiscpets;

import org.json.JSONObject;

public class DatabaseManager {

    public String createToken(String username, String password) {
        return "";
    }

    public JSONObject validate(String username, String password) {
        String operation = "get";
        return null;
    }

    public JSONObject getSicknesses(String species, String[] symptoms) {
        String operation = "get";
        return null;
    }

    public JSONObject getPrescriptions(String token, int petId) {
        String operation = "get";
        return null;
    }

    public JSONObject getAppointments(String token) {
        String operation = "get";
        return null;
    }

    public JSONObject getPetTable() {
        String operation = "get";
        return null;
    }

    public JSONObject getPetData(String token, int petId) {
        String operation = "get";
        return null;
    }

    public boolean modifyAccount(String username, String password, long phoneNumber) {
        String operation = "put";
        return false;
    }

    public boolean addPrescription(String medName, String dosage, String timePeriod) {
        String operation = "post";
        return false;
    }

    public boolean addAppointment(String token, int petId, String vetToken) {
        String operation = "post";
        return false;
    }

    public boolean addPet(String token, String species, String breed, String birthday) {
        String operation = "post";
        return false;
    }

    public boolean modifyPrescription(String token, int petId, String prescription, String dosage) {
        String operation = "put";
        return false;
    }

    public boolean modifyAppointment(String token, String oldDate, String newDate) {
        String operation = "put";
        return false;
    }

    public boolean modifyPet(String token, int petId, String name, String breed, String birthday) {
        String operation = "put";
        return false;
    }

    public boolean deletePet(String token, int petId) {
        String operation = "delete";
        return false;
    }

    public boolean deletePrescription(String token, int petId, String prescription) {
        String operation = "delete";
        return false;
    }

    public boolean deleteAccount(String token) {
        String operation = "delete";
        return false;
    }

    public boolean deleteAppointment(String token, String apptDate) {
        String operation = "delete";
        return false;
    }

    public boolean addAccount(String email, String password, String name, long phoneNumber, String address) {
        String operation = "post";
        return false;
    }
}
