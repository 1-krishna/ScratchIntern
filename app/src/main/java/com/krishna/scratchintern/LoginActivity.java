package com.krishna.scratchintern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText emailEditText, passwordInputEditText;
    Button loginButton;
    TextView forgotPasswordTextView, signUpLinkTextView;
    RelativeLayout activity_main;
    ProgressDialog progressDialog;

    SqliteHelper sqliteHelper;
    SharedPreferences sharedPreferences;

    public void continueSession(final String savedEmail, final String savedPassword){

      User currentUser = sqliteHelper.Authenticate(new User(null, null, savedEmail, savedPassword, null));
      Intent dashboard = new Intent(getBaseContext(), MainActivity.class);

      dashboard.putExtra("name", currentUser.name);
      dashboard.putExtra("email", currentUser.email);
      dashboard.putExtra("mobile", currentUser.mobile);
      dashboard.putExtra("password", currentUser.password);
      dashboard.putExtra("id", currentUser.id);

      Toast.makeText(this, "session Continued", Toast.LENGTH_LONG).show();

      finish();
      startActivity(dashboard);
    }

    public void loginButtonHandler(View view){
        String email = emailEditText.getText().toString();
        String password = passwordInputEditText.getText().toString();

        //Checking for null values and other validations then making login request
        if(email.equals("") || password.equals("")){
            Snackbar.make(activity_main, "Please Enter Credentials!", Snackbar.LENGTH_SHORT).show();

        }else {
            //Database matching

          User currentUser = sqliteHelper.Authenticate(new User(null, null, email, password, null));

          //Check Authentication is successful or not
          if (currentUser != null) {
            Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
            sharedPreferences.edit().putString("email", email).apply();
            sharedPreferences.edit().putString("password", password).apply();
            //User Logged in Successfully Launching Main activity
            Intent dashboard = new Intent(getBaseContext(), MainActivity.class);

            dashboard.putExtra("name", currentUser.name);
            dashboard.putExtra("email", currentUser.email);
            dashboard.putExtra("mobile", currentUser.mobile);
            dashboard.putExtra("password", currentUser.password);
            dashboard.putExtra("id", currentUser.id);

            finish();
            startActivity(dashboard);
          } else {

            //User Logged in Failed
            Toast.makeText(this, "Wrong Credentials", Toast.LENGTH_SHORT).show();

          }

        }
    }

    public void signUpHandler(View view){
        Intent registrationIntent = new Intent(this, RegistrationActivity.class);
        startActivity(registrationIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText =  findViewById(R.id.emailEditText);
        passwordInputEditText =  findViewById(R.id.passwordInputEditText);
        loginButton =  findViewById(R.id.loginButton);
        forgotPasswordTextView =  findViewById(R.id.forgotPasswordTextView);
        signUpLinkTextView = findViewById(R.id.signUpLinkTextView);
        activity_main =  findViewById(R.id.activity_main);
        progressDialog = new ProgressDialog(this);

        sqliteHelper = new SqliteHelper(this);

        //Checking Shared Preference to resume session

          sharedPreferences = this.getSharedPreferences(getPackageName(),MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email",null);
        String savedPassword = sharedPreferences.getString("password", null);
        if (savedEmail != null) {
            Log.i("Session_Continued","Login");
            continueSession(savedEmail, savedPassword);
        }




    }
}
