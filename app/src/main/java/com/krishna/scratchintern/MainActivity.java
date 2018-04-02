package com.krishna.scratchintern;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  private DrawerLayout drawer;
  public static String name, email, mobile, password;
  TextView navHeaderNameTextView;
  NavigationView navigationView;
  String id;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //Getting Intent data
    Intent intent = getIntent();
    name = intent.getStringExtra("name");
    email = intent.getStringExtra("email");
    mobile = intent.getStringExtra("mobile");
    password = intent.getStringExtra("password");
    id = intent.getStringExtra("id");


    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    drawer = findViewById(R.id.drawer_layout);

    navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    //Setting header name
    View header=navigationView.getHeaderView(0);
    navHeaderNameTextView = (TextView)header.findViewById(R.id.navHeaderNameTextView);
    navHeaderNameTextView.setText(name);

    //for menu button hamburger
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
      R.string.navigation_drawer_open, R.string.navigation_drawer_close);

    drawer.addDrawerListener(toggle);
    toggle.syncState();
    //Default fragment
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
      new ViewProfileFragment()).commit();
    navigationView.setCheckedItem(R.id.nav_profile);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()){
      case R.id.nav_profile:
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
          new ViewProfileFragment()).commit();
        break;

      case R.id.nav_another:
        AnotherFragment nextFrag = new AnotherFragment();
        //Adding dummy data for another fragment
        Bundle args = new Bundle();
        args.putString("Dummy1", "Value1");
        args.putString("Dummy2", "Value2");
        nextFrag.setArguments(args);
        //Starting new Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, nextFrag).commit();
        break;
    }

    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  //Handling backpress
  @Override
  public void onBackPressed() {
    if(drawer.isDrawerOpen(GravityCompat.START)){
      drawer.closeDrawer(GravityCompat.START);
    }else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.overflow_menu, menu);
    return true;
  }

  public void logoutHandler(){
    SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName(),MODE_PRIVATE);
    sharedPreferences.edit().clear().commit();
    Toast.makeText(this, "Successfully Logged out", Toast.LENGTH_SHORT).show();
    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
    finish();
    startActivity(intent);

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.overflow_logout:
        logoutHandler();
        break;
      case R.id.overflow_edit_profile:
        editProfileHandler();
        break;
    }
    return true;
  }

  public void editProfileHandler(){
    Intent i = new Intent(this, UpdateProfile.class);
    i.putExtra("name",name);
    i.putExtra("email", email);
    i.putExtra("mobile", mobile);
    i.putExtra("password", password);
    i.putExtra("id", id);
    Log.i("id_main", id);
    startActivityForResult(i, 1);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if (requestCode == 1) {
      if(resultCode == Activity.RESULT_OK){
        name = data.getStringExtra("name");
        email = data.getStringExtra("email");
        mobile = data.getStringExtra("mobile");
        password = data.getStringExtra("password");

        //Setting header name
        View header=navigationView.getHeaderView(0);
        navHeaderNameTextView = (TextView)header.findViewById(R.id.navHeaderNameTextView);
        navHeaderNameTextView.setText(name);

      }
    }
  }
}
