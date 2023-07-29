package com.example.classification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.classification.gpdao.Patientclass;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    final static String DBName ="doctordatabase.db";
    final static int DBVERSION =4;

    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table patients(id integer primary key autoincrement, patientname TEXT, patientphone TEXT, patientage INTEGER, patientstate TEXT, doctorcomm TEXT)");
        sqLiteDatabase.execSQL("create Table doctors(username TEXT primary key, password TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP table if exists doctors");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  patients");
           onCreate(sqLiteDatabase);
    }

    public boolean insertpatient(String name, String phone, String state, String docomm,int age){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("patientname", name);
        values.put("patientphone", phone);
        values.put("patientstate", state);
        values.put("doctorcomm", docomm);
        values.put("patientage", age);
        long id = db.insert("patients",null,values);
        return id > 0;
    }

    public Boolean insertdoctorData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = db.insert("doctors", null, contentValues);
        return result != -1;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from doctors where username = ?", new String[]{username});
        return cursor.getCount() > 0;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from doctors where username = ? and password = ?", new String[] {username,password});
        return cursor.getCount() > 0;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from patients",null);
        //Log.e("cusrordata",res.getString(2).toString());
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "patients");
        return numRows;
    }

//    public ArrayList<Patientclass> readdata(){
//        {
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery("Select * from patients", new String[]{"patientname", "patientphone", "patientstate", "doctorcomm", "patientage"});
//            Log.e("cursor data ", cursor.getString(2).toString());
//            ArrayList<Patientclass> patientModalArrayList = new ArrayList<>();
//            // moving our cursor to first position.
//            if  (cursor != null &&cursor.moveToFirst()) {
//                do {
//                    // on below line we are adding the data from cursor to our array list.
//                    patientModalArrayList.add(new Patientclass(cursor.getInt(4), //age1
//                            cursor.getString(2), // paientstate //name2
//                            cursor.getString(3), //paientphone //phone3
//                            cursor.getString(5), //patientage //state4
//                            cursor.getString(6) //docname //comm5
//                            )); //paientage
//                } while (cursor.moveToNext());
//                // moving our cursor to next.
//            }
//            else
//            {
//                Log.e("cursor data ", cursor.toString());
//            };
//            // at last closing our cursor
//            // and returning our array list.
//            cursor.close();
//            return patientModalArrayList;
//        }
//    }






}
