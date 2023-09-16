package com.example.deneme13;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class uyumm extends RecyclerView.Adapter<uyumm.MyViewHolder>{
    Context context;
    ArrayList karlist,fiyatlist;
    uyumm(Context context,ArrayList karlist,ArrayList fiyatlist){
        this.context=context;
        this.karlist=karlist;
        this.fiyatlist=fiyatlist;

    }
    @NonNull
    @Override
    public uyumm.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.karzararrow,parent,false);
        return new uyumm.MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull uyumm.MyViewHolder holder, int position){
       holder.currentpricetxt.setText(String.valueOf(fiyatlist.get(position)));
        holder.kartxt.setText(String.valueOf(karlist.get(position)));
    }
    @Override
    public int getItemCount() {
        return karlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView currentpricetxt,kartxt;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            currentpricetxt=itemView.findViewById(R.id.currentprice);
            kartxt=itemView.findViewById(R.id.balance);
        }
    }
}
