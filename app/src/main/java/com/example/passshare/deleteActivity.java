package com.example.passshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class deleteActivity extends AppCompatActivity {

    TextView tv_sname_da;
    String sname;
    DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete);
        tv_sname_da=findViewById(R.id.tv_sname_da);
        db=new DBHandler(this);

        sname=getIntent().getStringExtra("gname");

        tv_sname_da.setText(sname);
    }

    public void delFun(View view){
        Toast.makeText(this, sname+"deleted", Toast.LENGTH_SHORT).show();
        int sid=db.findSidFromSname(sname);
//        Toast.makeText(this, "sid : "+sid, Toast.LENGTH_SHORT).show();
        Boolean del = db.delSer(sid);
        if (del){
            Toast.makeText(this, "Service deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(deleteActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            finish();

        }
        else {
            Toast.makeText(this, "Error at deletion", Toast.LENGTH_SHORT).show();
        }
    }
}