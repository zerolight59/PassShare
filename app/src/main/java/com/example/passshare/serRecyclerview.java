package com.example.passshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class serRecyclerview  extends RecyclerView.Adapter<serRecyclerview.MyViewHoldedr> {

    private final groupRecyclerviewInterface recyclerviewInterface;

    Context context;
    ArrayList<DBService> dbServices;
    public serRecyclerview(Context context, ArrayList<DBService> dbServices,groupRecyclerviewInterface recyclerviewInterface) {
        this.context=context;
        this.dbServices=dbServices;
        this.recyclerviewInterface=recyclerviewInterface;
    }


    @NonNull
    @Override
    public serRecyclerview.MyViewHoldedr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycleview_row_1,parent,false);
        return new serRecyclerview.MyViewHoldedr(view,recyclerviewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull serRecyclerview.MyViewHoldedr holder, int position) {
        holder.tv_gnmae_rr_vg.setText(dbServices.get(position).getName());
        holder.tv_uname_vg.setText(dbServices.get(position).getUname());
        holder.tv_upass_vg.setText(dbServices.get(position).getPassword());
    }

    @Override
    public int getItemCount() {
        return dbServices.size();
    }

    public static class MyViewHoldedr extends RecyclerView.ViewHolder{

        TextView tv_gnmae_rr_vg,tv_uname_vg,tv_upass_vg;
        public MyViewHoldedr(@NonNull View itemView ,groupRecyclerviewInterface recyclerviewInterface) {
            super(itemView);
            tv_gnmae_rr_vg=itemView.findViewById(R.id.tv_gnmae_rr_vg);
            tv_uname_vg=itemView.findViewById(R.id.tv_uname_vg);
            tv_upass_vg=itemView.findViewById(R.id.tv_upass_vg);

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

