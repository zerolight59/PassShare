package com.example.passshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    EditText et_uname_reg,et_password_reg,et_repassword_reg;
    Button btn_reg;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        dbHandler=new DBHandler(this);

        et_uname_reg=findViewById(R.id.et_uname_reg);
        et_password_reg=findViewById(R.id.et_password_reg);
        et_repassword_reg=findViewById(R.id.et_repassword_reg);
    }

    //some problem here
    public void RegFunction(View view){
        String uname = et_uname_reg.getText().toString();
        String pass = et_password_reg.getText().toString();
        String repass = et_repassword_reg.getText().toString();

        if (uname.isEmpty() || pass.isEmpty() || repass.isEmpty()){
            Toast.makeText(this,"please fill all the boxes",Toast.LENGTH_SHORT).show();
        } else if (!pass.equals(repass)) {
            Toast.makeText(this,"password and repassword must be same",Toast.LENGTH_SHORT).show();
        }
        else{
            Boolean checkuser = dbHandler.checkUserName(uname);
            if (checkuser){
                Toast.makeText(this,"Usern name Already Taken",Toast.LENGTH_SHORT).show();
            }
            else {
                Boolean insert = dbHandler.addUser(uname,pass);
                if (insert) {
                    Toast.makeText(this,"User is Registered",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else Toast.makeText(this,"Error occured during Registeration",Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void ToMainActivity(View view){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }
}