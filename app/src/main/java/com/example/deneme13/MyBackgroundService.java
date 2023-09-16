package com.example.deneme13;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

public class MyBackgroundService extends Service{
    private Handler handler = new Handler(Looper.getMainLooper());
    public HashMap<String, String> gelenveri;
    public  static ArrayList<String> karyazdiran;
    public static Integer count=0;
    public SqlLiteHelper myDB;
    Double sumforbackground;
    TextView tablodakitotal;
    private static final int ARKA_PLAN_GECIKMESI = 2000;
    private Thread veriCekmeThread;
    @Override
    public void onCreate(){
        super.onCreate();
        myDB=MainActivity.myDB;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        karyazdiran=MainActivity.karyazdir;
        veriCekmeThread = new Thread(new Runnable(){
            @Override
            public void run(){
                while (true){
                    try {
                        count+=1;
                        takeDataArrays();
                        HashMap<String, String> data = borsaEndeks.borsaveri();
                        for (String i : MainActivity.hisse){
                            System.out.println("hisse listesi"+MainActivity.hisse);
                            System.out.println("i yazdir"+i);
                            if (data.containsKey(i)) {
                                Double fark = Double.valueOf(data.get(i)) - Double.valueOf(MainActivity.sayi.get(MainActivity.hisse.indexOf(i)));
                                System.out.println("fark:" + " " + fark);
                                System.out.println("kar yazdir"+karyazdiran);
                                karyazdiran.add(fark.toString());
                            }
                        }
                        System.out.println("KARYAZDİRAN" + karyazdiran);
                        karyazdiran.clear();
                        System.out.println("hisse" + MainActivity.hisse);
                        Thread.sleep(ARKA_PLAN_GECIKMESI);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        veriCekmeThread.start();
        return START_STICKY;
    }
    void takeDataArrays(){
        MainActivity.hisse.clear();
        MainActivity.sayi.clear();
        MainActivity.fiyat.clear();
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            System.out.println("bos");
        } else {
            while (cursor.moveToNext()) {
                MainActivity.hisse.add(cursor.getString(0));
                MainActivity.sayi.add(cursor.getString(1));
                MainActivity.fiyat.add(cursor.getString(2));
            }
        }
        cursor.close();
    }
}
