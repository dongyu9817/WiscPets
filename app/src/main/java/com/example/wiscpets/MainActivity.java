package com.example.wiscpets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    public void clickFunction (View view){
        Log.i("Info", "button pressed" );
        EditText myTextField = (EditText) findViewById (R.id.editText2);
        Toast.makeText(this, myTextField.getText().toString(), Toast.LENGTH_LONG).show();
        String str = myTextField.getText().toString();
        gotoActivity2(str);
    }

    public void gotoActivity2 (String s){
        Log.i("username mainactivity ", s);
        Intent intent = new Intent(this,Main2Activity.class);
        intent.putExtra("message", s);
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", s).apply();
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Intent intent = getIntent();

        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5" , Context.MODE_PRIVATE);

        if (!sharedPreferences.getString(usernameKey, "").equals("")){

            String login = sharedPreferences.getString(usernameKey, "");
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("message", login);
            startActivity(intent);

        }
        else {
            setContentView(R.layout.activity_main);
        }
    }
}
