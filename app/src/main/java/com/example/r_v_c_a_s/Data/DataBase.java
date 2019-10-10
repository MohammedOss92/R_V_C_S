package com.example.r_v_c_a_s.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.r_v_c_a_s.Sport;

import java.util.ArrayList;

/**
 * Created by abdallah on 8/25/2017.
 */

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ssqwq";
    private static final int DATABASE_VERSION = 1;
    public static Context context;
    private static DataBase sInstance;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBase getInstance(Context contexts) {
        context = contexts;
        if (sInstance == null) {
            sInstance = new DataBase(contexts.getApplicationContext());
        }
        return sInstance;
    }
////////////////////
public boolean checkIsDataAlreadyInDBorNot(SQLiteDatabase sqldb, String TableName, String dbfield, String fieldValue) {
    String Query = "SELECT " + dbfield + " FROM " + TableName + " WHERE " + dbfield + " =?";
    Cursor cursor = sqldb.rawQuery(Query, new String[]{fieldValue});
    if (cursor.getCount() <= 0) {
        cursor.close();
        return false;
    }
    cursor.close();
    return true;
}

    @Override
    public void onCreate(SQLiteDatabase db) {

        //*******
        String CREATE_Section_inf = "CREATE TABLE  section_info(name TEXT)";
        db.execSQL(CREATE_Section_inf);

        String Create_Content="CREATE TABLE Content (Content_ID INTEGER NOT NULL PRIMARY KEY," +
                "Con_id INTEGER," +
                "Con_Name TEXT," +
                "Fav INTEGER DEFAULT 0," +
                "ID_Categry INTEGER)";
        db.execSQL(Create_Content);

    }

    public void addNewRow(String name) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        if (!checkIsDataAlreadyInDBorNot(db, "section_info", "name", name)) {


            values.put("name", name);

            // Inserting Row
            db.insert("section_info", null, values);

        }
        db.close();
        // Closing database connection
    }



    ///////////////////



    public ArrayList<Sport> getAllPrayer() {

        ArrayList<Sport> contactList = new ArrayList<Sport>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + "section_info";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.e("cursor", cursor.toString());
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Sport row = new Sport();
             /*   Log.e (" cursor.getString (1)", cursor.getString (1) + "");// send
                Log.e (" cursor.getString type", cursor.getString (2) + "");// type
                Log.e (" cursor.getString date", cursor.getString (3) + "");// date
                Log.e (" cursor.getString (4)", cursor.getString (4) + "");// time
*/
//                row.setSubTitle((cursor.getString(1)));
                row.setInfo(cursor.getString(0));
//                row.setTitle(cursor.getString(3));


                // Adding contact to list
                contactList.add(row);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return contactList;
    }


    /////////////////////////////






    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
