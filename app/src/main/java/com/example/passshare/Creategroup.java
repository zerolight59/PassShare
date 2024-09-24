package com.example.passshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Creategroup extends AppCompatActivity {
    int aid;
    DBHandler dbHandler;
    EditText et_gname_crt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_creategroup);
        et_gname_crt=findViewById(R.id.et_gname_crt);
        dbHandler = new DBHandler(this);
    }

    public void crtGpfun(View view){
        String gname=et_gname_crt.getText().toString();
        SessionManagement sessionManagement = new SessionManagement(Creategroup.this);
        String uname = sessionManagement.getSession();
        aid=dbHandler.findUidUsingUname(uname);
//        Toast.makeText(this, "button clicked"+aid, Toast.LENGTH_SHORT).show();
        if (aid!=-1){
            if (uname.isEmpty()){
                Toast.makeText(this,"please fill all the boxes",Toast.LENGTH_SHORT).show();
            }
            else{
//            Toast.makeText(this, "button clicked "+gname, Toast.LENGTH_SHORT).show();
                Boolean insert = dbHandler.addGroup(gname,aid);
//                Toast.makeText(this, "button clicked "+insert, Toast.LENGTH_SHORT).show();
                if (insert) {
                    Toast.makeText(this,"Group Created is Registered",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Creategroup.this, HomeActivity.class);
                    startActivity(intent);
                }
                else Toast.makeText(this,"Error occured during Registeration",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(Creategroup.this,"Invalid admin",Toast.LENGTH_SHORT).show();
        }

    }
}