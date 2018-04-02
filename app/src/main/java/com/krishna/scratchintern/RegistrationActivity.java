package com.krishna.scratchintern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

      ProgressDialog progressDialog;
      RelativeLayout activity_registration;
      TextInputEditText regNameEditText, regEmailEditText, regPasswordEditText, regMobileEditText;

      SqliteHelper sqliteHelper;

      // Handles Login Text
      public void loginHandler(View view){
          Intent loginIntent = new Intent(this, LoginActivity.class);
          startActivity(loginIntent);
          finish();
      }

      public static boolean isValidMobile(String s) {
      Pattern p = Pattern.compile("[6-9][0-9]{9}");
      Matcher m = p.matcher(s);
      return (m.find() && m.group().equals(s));
    }

      public static boolean isValidEmail(String email) {
      String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
        "[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
        "A-Z]{2,7}$";

      Pattern pat = Pattern.compile(emailRegex);
      if (email == null)
        return false;
      return pat.matcher(email).matches();
    }

      // Handles register Button
      public void registerHandler(View view){
          String name = regNameEditText.getText().toString();
          String password = regPasswordEditText.getText().toString();
          String email = regEmailEditText.getText().toString();
          String phoneno = regMobileEditText.getText().toString();

          if(name.equals("") || password.equals("") || email.equals("") || phoneno.equals("")){
              Snackbar.make(activity_registration, "All fields are required", Snackbar.LENGTH_SHORT).show();
          }else if(!isValidMobile(phoneno)){
            Snackbar.make(activity_registration, "Invalid Mobile Number", Snackbar.LENGTH_SHORT).show();
          }else if(!isValidEmail(email)){
            Snackbar.make(activity_registration, "Invalid Email", Snackbar.LENGTH_SHORT).show();
          }else{

            //Check in the database is there any user associated with  this email
            if (!sqliteHelper.isEmailExists(email)) {

              //Email does not exist now add new user to database
              sqliteHelper.addUser(new User(null, name, email, password, phoneno));
              new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                  finish();
                }
              }, Snackbar.LENGTH_LONG);

              Intent loginIntent = new Intent(this, LoginActivity.class);
              startActivity(loginIntent);
              finish();

            }else {
              //Email exists with email input provided so show error user already exist
              Snackbar.make(activity_registration, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
            }
          }

      }

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_registration);

          progressDialog = new ProgressDialog(this);
          activity_registration = findViewById(R.id.activity_registration);
          regNameEditText = findViewById(R.id.regNameEditText);
          regEmailEditText = findViewById(R.id.regEmailEditText);
          regPasswordEditText = findViewById(R.id.regPasswordEditText);
          regMobileEditText = findViewById(R.id.regMobileEditText);
          sqliteHelper = new SqliteHelper(this);
      }
}
