package com.example.wiscpets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static android.app.PendingIntent.getActivity;

//This is the activity for vet to review specific patient record

public class CreateConsultPatientActivity extends AppCompatActivity {

    private DatabaseManager db = new DatabaseManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_consultation_patient);

        //get which patient, health record
        Intent fromHome = getIntent();
        String petNameAndId = fromHome.getStringExtra("petName");
        String petId = petNameAndId.split(" ")[0];
        String petName = petNameAndId.split(" ")[1];
        String visitDate = fromHome.getStringExtra("visitVitals");


        //retrieve patient input
        final TextView pet_name = findViewById(R.id.patient_name);
        final TextView species = findViewById(R.id.species);
        final TextView dov = findViewById(R.id.dov);
        final TextView temperature = findViewById(R.id.temp);
        final TextView heartRate = findViewById(R.id.heartRate);
        final TextView weight = findViewById(R.id.weight);

        pet_name.setText(petName);
        JSONObject vitals =  db.getVitals(petId);
        try {
            JSONArray visitVitals = vitals.getJSONArray("response");
            for (int i = 0; i < visitVitals.length(); i++) {
                JSONObject specVisit = visitVitals.getJSONObject(i);
                if (specVisit.getString("Visit_Date").equals(visitDate)) {
                    species.setText("Dog");
                    dov.setText(specVisit.getString("Visit_Date"));
                    temperature.setText(specVisit.getString("Temperature"));
                    heartRate.setText(specVisit.getString("Heart_Rate"));
                    weight.setText(specVisit.getString("Weight"));
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
