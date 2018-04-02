package com.krishna.scratchintern;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProfile extends AppCompatActivity {

  EditText nameUpdateProfile, emailUpdateProfile, mobileUpdateProfile, passwordUpdateProfile;
  Button updateButton;
  String name, email, mobile, password;
  int id;
  SqliteHelper sqliteHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_profile);

    Intent intent = getIntent();
    name = intent.getStringExtra("name");
    email = intent.getStringExtra("email");
    mobile = intent.getStringExtra("mobile");
    password = intent.getStringExtra("password");
    id = Integer.parseInt(intent.getStringExtra("id"));

    sqliteHelper = new SqliteHelper(this);


    nameUpdateProfile = findViewById(R.id.nameUpdateProfile);
    nameUpdateProfile.setText(name);
    emailUpdateProfile = findViewById(R.id.emailUpdateProfile);
    emailUpdateProfile.setText(email);
    mobileUpdateProfile = findViewById(R.id.mobileUpdateProfile);
    mobileUpdateProfile.setText(mobile);
    passwordUpdateProfile = findViewById(R.id.passwordUpdateProfile);
    passwordUpdateProfile.setText(password);


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

  public void updateButtonHandler(View view){
    String namee = nameUpdateProfile.getText().toString();
    String passwordd = passwordUpdateProfile.getText().toString();
    String emaill = emailUpdateProfile.getText().toString();
    String phonenoo = mobileUpdateProfile.getText().toString();

    if(namee.equals("") || passwordd.equals("") || emaill.equals("") || phonenoo.equals("")){
      Toast.makeText(this, "Can't set empty values", Toast.LENGTH_SHORT).show();
    }else if(!isValidMobile(phonenoo)){
      Toast.makeText(this, "Invalid Mobile", Toast.LENGTH_SHORT).show();
    }else if(!isValidEmail(emaill)){
      Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
    }else if(sqliteHelper.isEmailExists(emaill) && !(emaill.equals(email))){
      Toast.makeText(this, "Email already Registered", Toast.LENGTH_SHORT).show();
    }else{
      int c;
      c = sqliteHelper.updateProfile(id, namee, email,  emaill, passwordd, phonenoo);
      if(c>0){Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();}
      else {Toast.makeText(this, "Failed To update data", Toast.LENGTH_SHORT).show();}
      Intent returnIntent = new Intent();
      returnIntent.putExtra("name",namee);
      returnIntent.putExtra("email", emaill);
      returnIntent.putExtra("mobile", phonenoo);
      returnIntent.putExtra("password", passwordd);
      setResult(Activity.RESULT_OK, returnIntent);
      finish();
    }

  }

}
