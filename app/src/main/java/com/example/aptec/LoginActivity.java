package com.example.aptec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    final String EMAIL = "johndoe@gmail.com";
    final String PASSWORD =  "123456";

    // email and password input

    EditText emailEditText, passwordEdittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       emailEditText = findViewById(R.id.editTextEmail);
       passwordEdittext = findViewById(R.id.editTextPassword);

    }

    public void onLoginClick(View view) {

        // check if is empty
        if(emailEditText.getText().toString().trim().equals("") ||
                emailEditText.getText().toString().length() < 5){
            emailEditText.setError("Your email is empty or less than 5 chars");
//            Toast.makeText(this, "Your email is empty or less than 5 chars", Toast.LENGTH_LONG).show();
        }

        if(passwordEdittext.getText().toString().trim().equals("") ||
                passwordEdittext.getText().toString().length() < 5){
            passwordEdittext.setError("Your password is empty or less than 5 chars");
            //Toast.makeText(this, "Your password is empty or less than 5 chars", Toast.LENGTH_LONG).show();
        }

        // validate against our correct email and password
        if(emailEditText.getText().toString().trim().equals(EMAIL)
                && passwordEdittext.getText().toString().trim().equals(PASSWORD)){
            //take him inside the app
            startActivity(new Intent(this, MainActivity.class));
        }else{
            // tell him his credentials are wrong
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG).show();
        }



    }

    public void onNewUserClick(View view) {

    }
}
