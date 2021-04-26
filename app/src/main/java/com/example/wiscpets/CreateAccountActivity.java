package com.example.wiscpets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.app.PendingIntent.getActivity;

public class CreateAccountActivity extends AppCompatActivity {

    private DatabaseManager db = new DatabaseManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_createaccount);
        //get user input username and password
        final EditText email = findViewById(R.id.username_field);
        final EditText password = findViewById(R.id.input_password);
        final EditText address = findViewById(R.id.input_address);
        final EditText phone = findViewById(R.id.input_phone);
        final EditText name = findViewById(R.id.input_name);
        Button signup = (Button) findViewById(R.id.signup_Button);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send user info to database
                String username_input = email.getText().toString();
                String password_input = password.getText().toString();
                String name_input = name.getText().toString();
                String address_input = address.getText().toString();
                String phone_input = phone.getText().toString();

                if (phone_input.length() < 10 || Long.parseLong(phone_input) < 0) {
                    Toast.makeText(getBaseContext(), "Invalid phone number: Please include Area Code", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success = db.addAccount(username_input, password_input, name_input, phone_input, address_input);

                if (success) {
                    Toast.makeText(getBaseContext(), "Created Account Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Failed To Create Account", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getBaseContext(), WiscPetLoginActivity.class);
                startActivity(intent);
            }
        });





        String usernameKey = "username";
//        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5" , Context.MODE_PRIVATE);
//
//        if (!sharedPreferences.getString(usernameKey, "").equals("")){
//
//            String login = sharedPreferences.getString(usernameKey, "");
//            Intent intent = new Intent(this, Main2Activity.class);
//            intent.putExtra("message", login);
//            startActivity(intent);
//
//        }
//        else {
//            setContentView(R.layout.activity_createaccount);
//        }
    }
}
