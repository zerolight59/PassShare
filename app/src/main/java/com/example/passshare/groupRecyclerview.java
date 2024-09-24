package com.example.passshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class groupRecyclerview extends RecyclerView.Adapter<groupRecyclerview.MyViewHoldedr> {

    private final groupRecyclerviewInterface recyclerviewInterface;

    Context context;
    ArrayList<DBGroup> dbGroups;
    public groupRecyclerview(Context context, ArrayList<DBGroup> dbGroups,groupRecyclerviewInterface recyclerviewInterface) {
        this.context=context;
        this.dbGroups=dbGroups;
        this.recyclerviewInterface=recyclerviewInterface;
    }

    @NonNull
    @Override
    public groupRecyclerview.MyViewHoldedr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_row,parent,false);
        return new groupRecyclerview.MyViewHoldedr(view,recyclerviewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull groupRecyclerview.MyViewHoldedr holder, int position) {
        holder.tv_rv_gname.setText(dbGroups.get(position).getGroupName());
    }

    @Override
    public int getItemCount() {

        return dbGroups.size();
    }

    public static class MyViewHoldedr extends RecyclerView.ViewHolder{

        TextView tv_rv_gname;
        public MyViewHoldedr(@NonNull View itemView,groupRecyclerviewInterface recyclerviewInterface) {
            super(itemView);
            tv_rv_gname=itemView.findViewById(R.id.tv_gnmae_rr);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerviewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerviewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
