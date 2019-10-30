package com.example.aptec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    EditText editTextEmail, passwordEdittext;
    String emailStr, passwordStr;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextEmail = findViewById(R.id.editTextEmail);
        passwordEdittext = findViewById(R.id.editTextPassword);


    }

    public void onAlreadyUserClick(View view) {
    }

    public void onSignupClick(View view) {

        emailStr  = editTextEmail.getText().toString().trim();
        passwordStr = passwordEdittext.getText().toString();

        Intent signupIntent = new Intent(this, MainActivity.class);
        signupIntent.putExtra("email",emailStr);
        signupIntent.putExtra("password", passwordStr);
        startActivity(signupIntent);



    }
}
