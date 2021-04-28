package com.example.wiscpets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        String patient_number = fromHome.getStringExtra("patient");
        String health_record_index = fromHome.getStringExtra("healthrecordNumber");


        //retrieve patient input
        final EditText pet_name = findViewById(R.id.patient_name);
        final EditText species = findViewById(R.id.species);
        final EditText dob = findViewById(R.id.dob);
        final EditText temperature = findViewById(R.id.temp);
        final EditText blood_pressure = findViewById(R.id.blood_pressure);
        final EditText allergies = findViewById(R.id.allergy);
        final EditText medication = findViewById(R.id.medications);
        final EditText add_info = findViewById(R.id.info);
        Button save = (Button) findViewById(R.id.save_Button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send user info to database
                String pet_name_input = pet_name.getText().toString();
                String species_input = species.getText().toString();
                String dob_input = dob.getText().toString();
                String temperature_input = temperature.getText().toString();
                String blood_pressure_input = blood_pressure.getText().toString();
                String allergies_input = allergies.getText().toString();
                String medication_input = medication.getText().toString();
                String add_info_input = add_info.getText().toString();

                if (pet_name_input.length() < 1) {
                    Toast.makeText(getBaseContext(), "Invalid Pet Name: Please enter name", Toast.LENGTH_SHORT).show();
                    return;
                }



             // To be updated, need to have a database to hold the data
             //   boolean success = db.addAccount(
             //           pet_name_input,species_input,dob_input,temperature_input,blood_pressure_input,
             //           allergies_input,medication_input,add_info_input
            //    );
                boolean success = true;
                if (success) {
                    Toast.makeText(getBaseContext(), "Patient Record Saved Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Failed To Save Patient Record", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });






    }
}
