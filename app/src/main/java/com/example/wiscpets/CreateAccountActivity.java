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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_createaccount);
        //get user input username and password
        final EditText email = findViewById(R.id.username_field);
        final EditText password = findViewById(R.id.input_password);
        Button signup = (Button) findViewById(R.id.signup_Button);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send user info to database
                //todo
                String username_input = email.getText().toString();
                String password_input = password.getText().toString();
                Toast.makeText(getBaseContext(), "Created Account Successfully!", Toast.LENGTH_SHORT).show();

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
