package com.example.deneme13;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
public class MainActivity extends AppCompatActivity {
    private static final int ARKA_PLAN_GECIKMESI =0;
    Handler handler = new Handler(Looper.getMainLooper());
    ImageButton plus;
    Dialog ekleme;
    Button addbutton;
    RecyclerView recyclerView;
    RecyclerView kargunceltablo;
    EditText enstruman;
    EditText lotnum;
    EditText purchaseprice;
    Double sum = 0.0;
    Double sumv2 = 0.0;
    TextView totalbalancetxt;
    TextView totalInvestmenttxt;
    public Integer count=0;
    public boolean checkadd=false;
    public static SqlLiteHelper myDB;
    public static ArrayList<String> hisse;
    public static ArrayList<String> sayi;
    public static ArrayList<String> fiyat;
    public static ArrayList<String> karyazdir;
    public static ArrayList<Double> totalİnvestment;
    public static ArrayList<String> güncelkur;
    customadapter customadapter;
    HashMap<String, String> gelenveri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList deneme = new ArrayList<>();
        deneme.add(1);
        deneme.add(2);
        hisse = new ArrayList<>();
        sayi = new ArrayList<>();
        fiyat = new ArrayList<>();
        karyazdir = new ArrayList<>();
        totalİnvestment = new ArrayList<>();
        güncelkur = new ArrayList<>();
        myDB = new SqlLiteHelper(MainActivity.this);
        Thread veriCekmeThread = new Thread(new Runnable() {
            @Override
            public void run(){
                while (true){
                    try {
                        takeDataArrays();
                        HashMap<String, String> data = borsaEndeks.borsaveri();
                        for (String i : hisse){
                            if (data.containsKey(i)){
                                güncelkur.add(data.get(i));
                                totalİnvestment.add(Double.valueOf(data.get(i)) * Double.valueOf(sayi.get(hisse.indexOf(i))));
                                Double fark = (Double.valueOf(data.get(i)) - Double.valueOf(fiyat.get(hisse.indexOf(i)))) * Double.valueOf(sayi.get(hisse.indexOf(i)));

                                DecimalFormat df= new DecimalFormat("#.###");
                                String tempfark=df.format(fark);
                                karyazdir.add(tempfark);
                            }
                        }
                        sum = 0.0;
                        for (int i=1;i<karyazdir.size();i++){
                            sum += Double.valueOf(karyazdir.get(i).trim());
                        }
                        sumv2 = 0.0;
                        for (Double j : totalİnvestment) {
                            sumv2 += j;
                        }
                        ArrayList karclone = (ArrayList) karyazdir.clone();
                        ArrayList güncelkurv2 = (ArrayList) güncelkur.clone();
                        handler.post(new Runnable(){
                            @Override
                            public void run(){;
                                recyclerView = findViewById(R.id.recview);
                                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                customadapter = new customadapter(MainActivity.this, hisse, sayi, fiyat,güncelkurv2,karclone);
                                customadapter.notifyDataSetChanged();
                                recyclerView.setAdapter(customadapter);
                                System.out.println("karlist" + karclone);
                                totalbalancetxt = findViewById(R.id.toplamkarzararsonuc);
                                totalbalancetxt.setText(sum.toString());
                                totalInvestmenttxt = findViewById(R.id.toplampara);
                                totalInvestmenttxt.setText(sumv2.toString());
                            }
                        });
                        güncelkur.clear();
                        totalİnvestment.clear();
                        karyazdir.clear();

                        Thread.sleep(ARKA_PLAN_GECIKMESI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        veriCekmeThread.start();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plus = findViewById(R.id.imageButton);
        ekleme = new Dialog(MainActivity.this);
        ekleme.setContentView(R.layout.eklemepage);
        addbutton = ekleme.findViewById(R.id.buttonadd);
        enstruman = ekleme.findViewById(R.id.enstruman);
        lotnum = ekleme.findViewById(R.id.lotNUM);
        purchaseprice = ekleme.findViewById(R.id.price);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ekleme.show();
            }
        });
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BorsaVerisiTask().execute();
            }
        });
    }
    void takeDataArrays() {
        hisse.clear();
        sayi.clear();
        fiyat.clear();
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                hisse.add(cursor.getString(0));
                sayi.add(cursor.getString(1));
                fiyat.add(cursor.getString(2));
            }
        }
        cursor.close();
    }
    private class BorsaVerisiTask extends AsyncTask<Void, Void, HashMap<String, String>> {
        @Override
        protected HashMap<String, String> doInBackground(Void... params) {
            return borsaEndeks.borsaveri();
        }
        @Override
        protected void onPostExecute(HashMap<String, String> result) {
            // İşlem tamamlandığında sonuçları işleme
            if (result != null) {
                gelenveri = result;
                if (gelenveri.containsKey(enstruman.getText().toString().trim())) {
                    myDB.addStock(enstruman.getText().toString().trim(), Integer.valueOf(lotnum.getText().toString().trim()), Float.valueOf(purchaseprice.getText().toString().trim()));
                    takeDataArrays();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    customadapter.notifyDataSetChanged();
                    ekleme.dismiss();
                    enstruman.setText("");
                    lotnum.setText("");
                    purchaseprice.setText("");
                } else {
                    System.out.println("yanlis");
                    ekleme.dismiss();
                    enstruman.setText("");
                    lotnum.setText("");
                    purchaseprice.setText("");
                }
            }
        }
    }
}

