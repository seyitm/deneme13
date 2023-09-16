package com.example.deneme13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SqlLiteHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME="finance.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="STOCK";
    private static final String COLUMN_ENS="enstruman";
    private static final String COLUMN_LOT="lotnum";
    private static final String COLUMN_PURCHASE="price";

    public SqlLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ENS + " TEXT, " +
                COLUMN_LOT + " INTEGER," + COLUMN_PURCHASE + " FLOAT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    void addStock(String enstruman,int lotnum,float price){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(COLUMN_ENS,enstruman);
        cv.put(COLUMN_LOT,lotnum);
        cv.put(COLUMN_PURCHASE,price);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result==-1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Added Succesfuly",Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData(){
        String query= "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
}
