package com.example.passshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Addservices extends AppCompatActivity {
    TextView tv_gname;
    EditText et_sname,et_suname,et_spassword;
    DBHandler dbHandler;
    String gn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addservices);

        tv_gname=findViewById(R.id.tv_gname);
        et_sname=findViewById(R.id.et_sname);
        et_suname=findViewById(R.id.et_suname);
        et_spassword=findViewById(R.id.et_spassword);

        dbHandler=new DBHandler(this);

        String gname= getIntent().getStringExtra("gname");
        gn=gname;
        tv_gname.setText(gname);
        Toast.makeText(this, "gname"+gn, Toast.LENGTH_SHORT).show();
    }

    public void AddService(View view){
        String sname=et_sname.getText().toString();
        String suname=et_suname.getText().toString();
        String spassword=et_spassword.getText().toString();
        int gid=dbHandler.getGroupIdFromName(gn);
        if (sname.isEmpty() || suname.isEmpty() || spassword.isEmpty()){
            Toast.makeText(this,"please fill all the boxes",Toast.LENGTH_SHORT).show();
        }
        else{
            Boolean insert = dbHandler.addService(sname,suname,spassword,gid);
            if (insert) {
                Toast.makeText(this,"Service is added",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Addservices.this,HomeActivity.class);
                startActivity(intent);
            }
            else Toast.makeText(this,"Error occured during ADDing",Toast.LENGTH_SHORT).show();
        }
    }


}