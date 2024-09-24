package com.example.passshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    TextView tv_uname_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout first
        setContentView(R.layout.activity_home);

        // Now initialize the TextView and other views
        tv_uname_home = findViewById(R.id.tv_uname_home);

        // Retrieve username from session
        SessionManagement sessionManagement = new SessionManagement(HomeActivity.this);
        String uname = sessionManagement.getSession();

        // Set the username to the TextView
        tv_uname_home.setText("Welcome "+uname+" !!");

    }

    public void crtGroupFunction(View view){
        Intent intent = new Intent(HomeActivity.this, Creategroup.class);
        startActivity(intent);
    }

    public void addToGroupFn(View view){
        Intent intent = new Intent(HomeActivity.this, Addtogroup.class);
        startActivity(intent);
    }

    public void viewGroupFunction(View view){
        Intent intent = new Intent(HomeActivity.this, Viewgroup.class);
        startActivity(intent);
    }

    public void logoutFunction(View view){
        SessionManagement sessionManagement = new SessionManagement(HomeActivity.this);
        sessionManagement.removeSession();

        moveToMainActivity();
    }

    public void moveToMainActivity(){
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}