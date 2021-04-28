package com.example.wiscpets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> Patient1 = new ArrayList<String>();
        Patient1.add("Health Record 1");
        Patient1.add("Health Record 2");
        Patient1.add("Health Record 3");
        Patient1.add("Health Record 4");


        List<String> Patient2 = new ArrayList<String>();
        Patient2.add("Health Record 1");
        Patient2.add("Health Record 2");


        List<String> Patient3 = new ArrayList<String>();
        Patient3.add("Health Record 1");
        Patient3.add("Health Record 2");
        Patient3.add("Health Record 3");


        expandableListDetail.put("Patient 1", Patient1);
        expandableListDetail.put("Patient 2", Patient2);
        expandableListDetail.put("Patient 3", Patient3);
        return expandableListDetail;
    }
}
