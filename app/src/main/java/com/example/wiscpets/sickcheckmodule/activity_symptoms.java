package app.www.wiscpets.sickcheckmodule;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import app.www.wiscpets.R;
import app.www.wiscpets.app.app_home;

public class activity_symptoms extends AppCompatActivity {
    Button dis;
    Spinner s1Spinner,s2Spinner,s3Spinner,s4Spinner,s5Spinner,s6Spinner,s7Spinner;
    String description[] = new String[7];
    int max =0;

    // Dog symptoms
    String Skin_Allergies [] = { "Itchiness", "Hives", "Swelling", "Red, inflamed skin",  "Diarrhea", "Vomiting", "Sneezing" };
    String Ear_Infection []  = { "Scratching of the ear", "Brown, yellow, or bloody discharge",  "Odor in the ear", "Redness Swelling Crusts or scabs", "Hair loss around the ear", "Rubbing of the ear", "Head shaking or head tilt" };
    String Arthritis []  = { "Reluctance to walk", "Limping", "Lagging behind on walks", "Pain or stiffness", "Yelping when touched", "Licking of affected joints"    };
    String Bladder_Infection [] = { "Increased thirst", "Fatigue or sluggishness", "Urgency to urinate",
            "Licking at urinary opening", "Whining", "Loss of appetite",  "Fever", "Blood in urine" };
    String Dental_Disease [] = { "Problems picking up food", "Bleeding or red gums", "Loose teeth", "Bad breath", "Bumps or lumps",
            "Bloody or ropey saliva"  } ;
    String Upset_Stomach [] = {"Dehydration", "Painful, distended, or hard belly", "Retching, trying to vomit", "Blood in vomit, urine, or feces", "Lethargy", "Suspected ingestion"};

    // Cat symptoms
    String Kidney_DiseaseCat [] = {"Frequent urinating", "Drinking a lot of water", "Bacterial infections" ,
            "Weight loss", "Decreased appetite",
            "Vomiting",  "Bloody or cloudy urine" };
    String ThyroidCat[] = {"Weight loss", "Decreased appetite", "Excessive thirst", "Increased urination",
            "Hyperactivity" , "Unkempt appearance", "Panting"    };
    String Bladder_InfectionCat [] = {"Excessive grooming",  "Frequent attempts to urinate",
            "Squatting to urinate", "Urinating in unusual places", "Vomiting","Lethargy"   };
    String Dental_DiseaseCat [] = {"Drooling", "Decreased appetite",  "Vomiting", "Swollen and bleeding gums",
            "Bad breath" };
    String Upset_StomachCat []  = {"Diarrhea", "Weight loss", "Loss of appetite", "Chronic soft stool",
            "Lethargy", "Discomfort" };
    String DiabetesCat[] =  {"Excessive Urination and Thirst", "Increased Weight Loss",
            "Inability to Jump and Loss of Interest",
            "Change in Gait", "Lack of Appetite", "Vomiting", "Lethargy"};

    // Covid symptoms
    String Covid [] = {"Coughing", "Fever", "Unusual laziness or sluggish" ,
            "Shortness of breath", "Loss of appetite",
            "Vomiting",  "Congestion or runny nose" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String name = getIntent().getStringExtra("name");
        final String selectedType = getIntent().getStringExtra("selectedType");
        if (selectedType.equals("Dog")) {
            setContentView(R.layout.activity_symptoms);
        }
        else  {
            if (selectedType.equals("Cat"))
                setContentView(R.layout.activity_symptoms_cat);
            else
                setContentView(R.layout.activity_symptoms_covid);
        }

        dis= findViewById(R.id.disease);
        s1Spinner = findViewById(R.id.syp1);
        s2Spinner = findViewById(R.id.syp2);
        s3Spinner = findViewById(R.id.syp3);
        s4Spinner = findViewById(R.id.syp4);
        s5Spinner = findViewById(R.id.syp5);
        s6Spinner = findViewById(R.id.syp6);
        s7Spinner = findViewById(R.id.syp7);

        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int countMatched[] = new int[6];
                description[0] = s1Spinner.getSelectedItem().toString();
                description[1] = s2Spinner.getSelectedItem().toString();
                description[2] = s3Spinner.getSelectedItem().toString();
                description[3] = s4Spinner.getSelectedItem().toString();
                description[4] = s5Spinner.getSelectedItem().toString();
                description[5] = s6Spinner.getSelectedItem().toString();
                description[6] = s7Spinner.getSelectedItem().toString();
                max = 0;
                if (selectedType.equals("Covid")) {
                    countMatched[0] = 0;
                    int leng = Covid.length;
                    boolean matched [] = new boolean[leng];
                    for (int i = 0; i < 7; i++) {
                        int l = Covid.length;
                        for (int k = 0; k < l; k++) {
                            if (description[i].equals(Covid[k]) && !matched[k]) {
                                matched[k] = true;
                                countMatched[0]++;
                            }
                        }
                    }
                    if (countMatched[0] > 0) {
                        if (countMatched[0] >= (Covid.length - 3)) // matching at least 5 criteria
                            max = countMatched[0]; // Likely Covid
                        Intent dis_page = new Intent(activity_symptoms.this, activity_disease.class);
                        dis_page.putExtra("name", name);
                        dis_page.putExtra("selectedType", selectedType);
                        dis_page.putExtra("max", max);
                        dis_page.putExtra("c", countMatched);
                        startActivity(dis_page);
                    }
                    else {
                        Toast.makeText(activity_symptoms.this, "Please select symptom(s)", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    // non-convid
                    for (int i = 0; i < 7; i++) {
                        for (int j = 1; j <= 6; j++) {
                            switch (j) {
                                case 1: {
                                    int l = 0;
                                    // l = diarrhoea.length;
                                    if (selectedType.equals("Dog")) {
                                        l = Skin_Allergies.length;  //dog
                                        for (int k = 0; k < l; k++) {
                                            if (description[i].equals(Skin_Allergies[k])) {
                                                countMatched[0]++;
                                            }
                                        }
                                    } else {
                                        l = Kidney_DiseaseCat.length;  //cat
                                        for (int k = 0; k < l; k++) {
                                            if (description[i].equals(Kidney_DiseaseCat[k])) {
                                                countMatched[0]++;
                                            }
                                        }
                                    }
                                    break;
                                }
                                case 2: {
                                    int l = 0;
                                    if (selectedType.equals("Dog")) {
                                        l = Ear_Infection.length;  //Dog
                                        for (int k = 0; k < l; k++) {
                                            if (description[i].equals(Ear_Infection[k])) {
                                                countMatched[1]++;
                                            }
                                        }
                                    } else {
                                        l = ThyroidCat.length;  //cat
                                        for (int k = 0; k < l; k++) {
                                            if (description[i].equals(ThyroidCat[k])) {
                                                countMatched[1]++;
                                            }
                                        }
                                    }
                                    break;
                                }

                                case 3: {
                                    int l = 0;
                                    if (selectedType.equals("Dog")) {
                                        l = Arthritis.length; //Dog
                                        for (int k = 0; k < l; k++) {
                                            if (description[i].equals(Arthritis[k])) {
                                                countMatched[2]++;
                                            }
                                        }
                                    } else {
                                        l = Bladder_InfectionCat.length; //Cat
                                        for (int k = 0; k < l; k++) {
                                            if (description[i].equals(Bladder_InfectionCat[k])) {
                                                countMatched[2]++;
                                            }
                                        }
                                    }
                                    break;
                                }

                                case 4: {
                                    int l = 0;
                                    if (selectedType.equals("Dog")) {
                                        l = Bladder_Infection.length; //Dog
                                        for (int k = 0; k < l; k++) {
                                            if (description[i].equals(Bladder_Infection[k])) {
                                                countMatched[3]++;
                                            }
                                        }
                                    } else {
                                        l = Dental_DiseaseCat.length; //Cat
                                        for (int k = 0; k < l; k++) {
                                            if (description[i].equals(Dental_DiseaseCat[k])) {
                                                countMatched[3]++;
                                            }
                                        }
                                    }
                                    break;
                                }

                                case 5: {
                                    int l = 0;
                                    if (selectedType.equals("Dog")) {
                                        l = Dental_Disease.length; //Dog
                                        for (int k = 0; k < l; k++) {
                                            if (description[i].equals(Dental_Disease[k])) { //blood_pressure[k]
                                                countMatched[4]++;
                                            }
                                        }
                                    } else {
                                        l = Upset_StomachCat.length; //Cat
                                        for (int k = 0; k < l; k++) {
                                            if (description[i].equals(Upset_StomachCat[k])) {
                                                countMatched[4]++;
                                            }
                                        }
                                    }
                                    break;
                                }

                                case 6: {
                                    int l = 0;
                                    if (selectedType.equals("Dog")) {
                                        l = Upset_Stomach.length;  //Dog
                                        for (int k = 0; k < l; k++) {
                                            if (description[i].equals(Upset_Stomach[k])) {
                                                countMatched[5]++;
                                            }
                                        }
                                    } else {
                                        l = DiabetesCat.length;  //Cat
                                        for (int k = 0; k < l; k++) {
                                            if (description[i].equals(DiabetesCat[k])) {
                                                countMatched[5]++;
                                            }
                                        }
                                    }
                                    break;
                                }
                            } // switch
                        }  //for
                    } //for

                    int itemCount = 0;
                    for (int m = 0; m < 6; m++) {
                        if (countMatched[m] >= max)
                            max = countMatched[m];
                        if (countMatched[m] > 0)
                            itemCount++;
                    }

                    if (itemCount > 0) {
                        Intent dis_page = new Intent(activity_symptoms.this, activity_disease.class);
                        dis_page.putExtra("name", name);
                        dis_page.putExtra("selectedType", selectedType);
                        dis_page.putExtra("max", max);
                        dis_page.putExtra("c", countMatched);
                        startActivity(dis_page);
                        max =0;
                    } else
                        Toast.makeText(activity_symptoms.this, "Please select symptom(s)", Toast.LENGTH_LONG).show();
                }  // if  covid
                //   max = 0;
            }



        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent tIntent = new Intent(this, app_home.class);
        startActivity(tIntent);
    }
}
