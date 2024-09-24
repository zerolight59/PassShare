package com.example.passshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText et_uname,et_password;
    Button btn_login;
    TextView tv_register;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHandler=new DBHandler(this);
        et_uname = findViewById(R.id.et_uname);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tv_register= findViewById(R.id.tv_register);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }

    public void checkSession(){
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        String loggedUname = sessionManagement.getSession();
        if (!loggedUname.isEmpty()){
            moveToHomeActivity();
        }
        else {
            //do nothing
        }
    }

    public void loginFunction(View view){
        String uname = et_uname.getText().toString();
        String pass = et_password.getText().toString();
        if (uname.isEmpty() || pass.isEmpty()){
            Toast.makeText(this,"please fill all the boxes",Toast.LENGTH_SHORT).show();
        }
        else{
            Boolean checkuser = dbHandler.loginFun(uname,pass);
            if (checkuser){
                DBUsers user = new DBUsers(uname,pass);
                SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                sessionManagement.saveSession(user);
                Toast.makeText(this,"Login Successfull",Toast.LENGTH_SHORT).show();
                moveToHomeActivity();
            }
            else {
                Toast.makeText(this,"Invalid UserName OR Password",Toast.LENGTH_SHORT).show();
            }
        }
//        Toast.makeText(this,"login function",Toast.LENGTH_SHORT).show();
    }

    public  void moveToHomeActivity(){
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void ToRegisterActivity(View view){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}