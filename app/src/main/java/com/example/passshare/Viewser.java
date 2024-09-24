package com.example.passshare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Viewser extends AppCompatActivity implements groupRecyclerviewInterface {
    TextView tv_gnmae_vs;
    DBHandler db;
    int aid;
    String gn;
    ArrayList<DBService> dbServices = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewser);

        tv_gnmae_vs=findViewById(R.id.tv_gnmae_vs);
        String gname= getIntent().getStringExtra("gname");
        gn=gname;
        Toast.makeText(this, "Group Name: " + gn, Toast.LENGTH_SHORT).show();
        tv_gnmae_vs.setText(gname);

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        db=new DBHandler(this);

        setDbServices();

        serRecyclerview serRecyclerview =new serRecyclerview(this,dbServices,this);
        recyclerView.setAdapter(serRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }

    private void setDbServices() {
        // Fetch the data from the database
        List<String> sname = db.getSerNames(gn);
        List<String> suname = db.getSerUNames(gn);
        List<String> spass = db.getSerPass(gn);

        // Debugging
//        Toast.makeText(this, "Service Names: " + sname.size(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Service Usernames: " + suname.size(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Service Passwords: " + spass.size(), Toast.LENGTH_SHORT).show();

        for (int i = 0; i < sname.size(); i++) {
            // Ensure all the lists are populated correctly
            dbServices.add(new DBService(sname.get(i), suname.get(i), spass.get(i)));
        }
    }

    @Override
    public void onItemClick(int position) {
        String gnmae = dbServices.get(position).getName();
        Toast.makeText(this, gnmae, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Viewser.this, deleteActivity.class);
        intent.putExtra("gname",dbServices.get(position).getName());
        startActivity(intent);
    }

}