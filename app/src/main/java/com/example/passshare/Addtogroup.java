package com.example.passshare;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Addtogroup extends AppCompatActivity implements groupRecyclerviewInterface{
    ArrayList<DBGroup> dbGroups = new ArrayList<>();
    DBHandler db;
    int aid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addtogroup);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        db=new DBHandler(this);

        setDbGroups();

        groupRecyclerview groupRecyclerview =new groupRecyclerview(this,dbGroups,this);
        recyclerView.setAdapter(groupRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setDbGroups(){
        SessionManagement sessionManagement = new SessionManagement(Addtogroup.this);
        String uname = sessionManagement.getSession();
        aid = db.findUidUsingUname(uname);
        List<String> gname= db.getGroupNames(aid);
        for (int i = 0; i < gname.toArray().length; i++) {
            dbGroups.add(new DBGroup(gname.get(i)));
        }
    }


    @Override
    public void onItemClick(int position) {
        String gnmae = dbGroups.get(position).getGroupName();
        Intent intent = new Intent(Addtogroup.this, Addservices.class);
        intent.putExtra("gname",dbGroups.get(position).getGroupName());
        startActivity(intent);
    }
}