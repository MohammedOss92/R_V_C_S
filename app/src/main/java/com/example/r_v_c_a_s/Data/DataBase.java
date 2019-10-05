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
    private static final String DATABASE_NAME = "bb";
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
        String CREATE_Section_inf = "CREATE TABLE IF NOT EXISTS section_info(id INTEGER NOT NULL PRIMARY KEY," +
                "name TEXT)";
        db.execSQL(CREATE_Section_inf);

        String Create_Content="CREATE TABLE Content (Content_ID INTEGER NOT NULL PRIMARY KEY," +
                "Con_id INTEGER," +
                "Con_Name TEXT," +
                "Fav INTEGER DEFAULT 0," +
                "ID_Categry INTEGER)";
        db.execSQL(Create_Content);

    }

    public void addNewRow( String name) {
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

    public void add_New_Row(String Con_id, String Con_Name, String Fav, String ID_Categry) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        if (!checkIsDataAlreadyInDBorNot(db, "Content", "Con_Name", Con_Name)) {
            values.put("Con_id", Con_id);
            values.put("Con_Name", Con_Name);
            values.put("Fav",Fav);
            values.put("ID_Categry",ID_Categry);

            // Inserting Row
            db.insert("Content", null, values);

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
                row.setTitle((cursor.getString(1)));
//                row.setSubTitle(cursor.getString(2));
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


    public String getMsgTitleByTitleID(int msgType) {

        String result = "";
        SQLiteDatabase db = getReadableDatabase();

        Cursor countCursor = db.rawQuery("SELECT name from section_info where iid=" + msgType, null);
        if (countCursor.moveToFirst()) {
            result = countCursor.getString(0);
        }
        countCursor.close();
        db.close();

        return result;
    }

    public ArrayList<Sport> getAllPrayer(String text) {
        text = "%" + text + "%";
        ArrayList<Sport> contactList = new ArrayList<Sport>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + "section_info" + " WHERE namese  LIKE '" + text + "'";

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
//                row.setTitle(cursor.getString(3));
//                row.setSubTitle(cursor.getString(2));
                row.setTitle(cursor.getString(1));


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





/****************************///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    /**/
//    public ArrayList<Sport> DataFaVourit() {
//        Sport m;
//        ArrayList<Sport> Data = new ArrayList();
//        SQLiteDatabase db = getReadableDatabase();
////        db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, 0);
//        String SelectQuery = "Select * from section_info where FAV= 1";
//        Cursor cursor = db.rawQuery(SelectQuery, null);
//        Log.e("cursor", cursor.toString());
////        Cursor cursor = db.rawQuery("Select * from vmsg where FAV= 1", null);
//        if (cursor.moveToFirst()) {
//
//            do {
//                Sport row = new Sport();
//                row.setID(Integer.parseInt(cursor.getString(0)));
//                row.setName(cursor.getString(2));
//
//                Data.add(row);
//            }
//            while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return Data;
//    }

    public boolean addMessage(String Message, int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM section_info WHERE name = '" + Message + "'" + "and FavActivity =" + 0, null);
        try {
            boolean x;
            if (data.moveToFirst()) {
                db.execSQL("update section_info set FavActivity =1 where ID= " + id);
//                Toast.makeText(this.ctx, "\u062a\u0645\u062a \u0627\u0644\u0627\u0636\u0627\u0641\u0629 \u0627\u0644\u0649 \u0642\u0627\u0626\u0645\u0629 \u0627\u0644\u0645\u0641\u0636\u0644\u0629", DATABASE_VERSION).show();
                x = false;
            } else {
                db.execSQL("update section_info set FavActivity =0 where ID= " + id);
//                Toast.makeText(this.ctx, "\u062a\u0645 \u0627\u0644\u062d\u0630\u0641 \u0645\u0646 \u0627\u0644\u0645\u0641\u0636\u0644\u0629", DATABASE_VERSION).show();
                x = true;
                data.close();
            }
            data.close();
            db.close();
            return x;
        } finally {
            data.close();
        }
    }


    public void fav(int id, String name) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put("iid", id);
        values.put("name", name);

        // Inserting Row
        db.update("section_info", values, "id=" + id, null);

        db.close();
        // Closing database connection
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNewRow(String[] stringArray) {
    }
}
