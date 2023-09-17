package com.example.deneme13;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class customadapter extends RecyclerView.Adapter<customadapter.MyViewHolder>{
     Context context;
     ArrayList<String>hisse,sayi,fiyat,güncelfiyat,tekilkar;
     customadapter(Context context,ArrayList hisse,ArrayList sayi,ArrayList fiyat,ArrayList güncelfiyat,ArrayList tekilkar){
         this.context=context;
         this.hisse=hisse;
         this.sayi=sayi;
         this.fiyat=fiyat;
         this.güncelfiyat=güncelfiyat;
         this.tekilkar=tekilkar;
     }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        holder.hissetxt.setText(String.valueOf(hisse.get(position)));
         holder.lottxt.setText(String.valueOf(sayi.get(position)));
         holder.fiyattxt.setText(String.valueOf(fiyat.get(position)));
         holder.gunceltxt.setText(String.valueOf(güncelfiyat.get(position)));
         holder.instantbalancetxt.setText(String.valueOf(tekilkar.get(position)));

    }

    @Override
    public int getItemCount() {
        return sayi.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         TextView hissetxt,lottxt,fiyattxt,gunceltxt,instantbalancetxt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hissetxt=itemView.findViewById(R.id.stocksymbol);
            lottxt=itemView.findViewById(R.id.lotsayisi);
            fiyattxt=itemView.findViewById(R.id.alisfiyati);
            gunceltxt=itemView.findViewById(R.id.guncelfiyat);
            instantbalancetxt=itemView.findViewById(R.id.anlikkar);

        }
    }
}

