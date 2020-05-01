package com.rtl.vts_technician.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rtl.vts_technician.model.De_installDeviceModel;
import com.rtl.vts_technician.model.MaintainanceDeviceModel;
import com.rtl.vts_technician.model.NewInstallDeviceModel;
import com.rtl.vts_technician.model.ReplaceDeviceModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE = "mstrc_tech.db";
    public static String INSTALLATION_TABLE = "new_installation";
    public static String REPLACE_TABLE = "replace_table";
    public static String MAINTAINCE_TABLE = "maintaince_table";

    private static final String KEY_ID = "row_id";
    private static final String KEY_VEH_No = "veh_no";
    private static final String KEY_DEPO = "depo";
    private static final String KEY_DIVISION = "division";
    private static final String KEY_IMIE ="imieno";
    private static final String KEY_REMARKS = "remarks";
    private static final String KEY_INSTAL_TIME = "instal_time";
    private static final String KEY_INSTAL_DATE = "instal_date";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_ADDRESS = "address";

    private static final String KEY_LAST_LOCATION = "last_location";
    private static final String KEY_STATUS = "status";
    private static final String KEY_PROBLEM_TYPS = "problem_type";
    private static final String KEY_ACTION_TAKEN = "action_taken";
    private static final String KEY_OLD_IMIE = "old_imie";
    private static final String KEY_NEW_IMIE = "new_imie";

    String installTable, replaceTable, maintainceTable;
    Context mContex;


    public DatabaseHelper ( Context context) {
        super(context, DATABASE, null, 2);
        this.mContex = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        installTable = "CREATE TABLE " + INSTALLATION_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_VEH_No + " TEXT," + KEY_DEPO + " TEXT," + KEY_DIVISION + "  TEXT," + KEY_IMIE + "  TEXT,"  + KEY_REMARKS + "  TEXT," + KEY_INSTAL_TIME + "  TEXT," + KEY_INSTAL_DATE + "  TEXT," + KEY_LAT + "  TEXT," + KEY_LNG + "  TEXT," + KEY_ADDRESS + " TEXT" + ")";

        maintainceTable = "CREATE TABLE " + MAINTAINCE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_VEH_No + " TEXT," + KEY_DEPO + " TEXT," + KEY_DIVISION + "  TEXT," + KEY_IMIE + "  TEXT," + KEY_LAST_LOCATION + "  TEXT," + KEY_STATUS + "  TEXT," + KEY_PROBLEM_TYPS + "  TEXT," + KEY_ACTION_TAKEN + "  TEXT," + KEY_REMARKS + "  TEXT," + KEY_INSTAL_TIME + "  TEXT," + KEY_INSTAL_DATE + "  TEXT," + KEY_LAT + "  TEXT," + KEY_LNG + "  TEXT," + KEY_ADDRESS + " TEXT" + ")";

        replaceTable = "CREATE TABLE " + REPLACE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_VEH_No + " TEXT," + KEY_DEPO + " TEXT," + KEY_DIVISION + "  TEXT," + KEY_OLD_IMIE + "  TEXT," + KEY_NEW_IMIE + "  TEXT,"  + KEY_REMARKS + "  TEXT," + KEY_INSTAL_TIME + "  TEXT," + KEY_INSTAL_DATE + "  TEXT," + KEY_LAT + "  TEXT," + KEY_LNG + "  TEXT," + KEY_ADDRESS + " TEXT" + ")";

        db.execSQL(installTable);
        db.execSQL(maintainceTable);
        db.execSQL(replaceTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + INSTALLATION_TABLE + " ;");

        db.execSQL("DROP TABLE IF EXISTS " + MAINTAINCE_TABLE + " ;");

        db.execSQL("DROP TABLE IF EXISTS " + REPLACE_TABLE + " ;");
        onCreate(db);
    }


    public void insertNewData( NewInstallDeviceModel modelDatabase) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_VEH_No, modelDatabase.getVeh_no());
        cv.put(KEY_DEPO, modelDatabase.getDepo());
        cv.put(KEY_DIVISION, modelDatabase.getDivision());
        cv.put(KEY_IMIE, modelDatabase.getImieno());
        cv.put(KEY_REMARKS, modelDatabase.getRemarks());
        cv.put(KEY_INSTAL_TIME, modelDatabase.getInstal_time());
        cv.put(KEY_INSTAL_DATE, modelDatabase.getInstal_date());
        cv.put(KEY_LAT, modelDatabase.getLat());
        cv.put(KEY_LNG, modelDatabase.getLng());
        cv.put(KEY_ADDRESS, modelDatabase.getAddress());

        db.insert(INSTALLATION_TABLE, null, cv);
       // Toast.makeText(mContex, "Data saved sucessfully", Toast.LENGTH_LONG).show();
    }

    public void insertMaintainceeData( MaintainanceDeviceModel modelDatabase) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_VEH_No, modelDatabase.getVeh_no());
        cv.put(KEY_DEPO, modelDatabase.getDepo());
        cv.put(KEY_DIVISION, modelDatabase.getDivision());
        cv.put(KEY_IMIE, modelDatabase.getImieno());
        cv.put(KEY_LAST_LOCATION, modelDatabase.getLast_location());
        cv.put(KEY_STATUS, modelDatabase.getStatus());
        cv.put(KEY_PROBLEM_TYPS, modelDatabase.getProblem_type());
        cv.put(KEY_ACTION_TAKEN, modelDatabase.getActon_taken());
        cv.put(KEY_REMARKS, modelDatabase.getRemarks());
        cv.put(KEY_INSTAL_TIME, modelDatabase.getInstal_time());
        cv.put(KEY_INSTAL_DATE, modelDatabase.getInstal_date());
        cv.put(KEY_LAT, modelDatabase.getLat());
        cv.put(KEY_LNG, modelDatabase.getLng());
        cv.put(KEY_ADDRESS, modelDatabase.getAddress());

        db.insert(MAINTAINCE_TABLE, null, cv);
        // Toast.makeText(mContex, "Data saved sucessfully", Toast.LENGTH_LONG).show();
    }

    public void insertReplaceData( ReplaceDeviceModel modelDatabase) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_VEH_No, modelDatabase.getVeh_no());
        cv.put(KEY_DEPO, modelDatabase.getDepo());
        cv.put(KEY_DIVISION, modelDatabase.getDivision());
        cv.put(KEY_OLD_IMIE, modelDatabase.getOld_imie());
        cv.put(KEY_NEW_IMIE, modelDatabase.getNew_imieno());
        cv.put(KEY_REMARKS, modelDatabase.getRemarks());
        cv.put(KEY_INSTAL_TIME, modelDatabase.getInstal_time());
        cv.put(KEY_INSTAL_DATE, modelDatabase.getInstal_date());
        cv.put(KEY_LAT, modelDatabase.getLat());
        cv.put(KEY_LNG, modelDatabase.getLng());
        cv.put(KEY_ADDRESS, modelDatabase.getAddress());

        db.insert(REPLACE_TABLE, null, cv);
        // Toast.makeText(mContex, "Data saved sucessfully", Toast.LENGTH_LONG).show();
    }

    public Boolean searchVehicle(String vehno)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + INSTALLATION_TABLE +" Where veh_no ='" + vehno + "' ", null);
        Boolean rowExists;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                rowExists = true;
            } while (cursor.moveToNext());
        }else{
            rowExists = false;
        }
        // return contact list
        return rowExists;
    }

    public void deleteAllVehicle() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(INSTALLATION_TABLE, null, null);
        db.delete(REPLACE_TABLE, null, null);
        db.close();
    }


    public ArrayList<NewInstallDeviceModel> getInstall_Arry_list() {
        ArrayList<NewInstallDeviceModel> data = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + INSTALLATION_TABLE + " ;", null);

        NewInstallDeviceModel dataModel = null;
        while (cursor.moveToNext()) {

            dataModel = new NewInstallDeviceModel ();
            String ids = cursor.getString(cursor.getColumnIndexOrThrow("row_id"));

            String veh_no = cursor.getString(cursor.getColumnIndexOrThrow("veh_no"));
            String depo = cursor.getString(cursor.getColumnIndexOrThrow("depo"));
            String division = cursor.getString(cursor.getColumnIndexOrThrow("division"));
            String imieno = cursor.getString(cursor.getColumnIndexOrThrow("imieno"));
            String remarks = cursor.getString(cursor.getColumnIndexOrThrow("remarks"));
            String instal_time = cursor.getString(cursor.getColumnIndexOrThrow("instal_time"));
            String instal_date = cursor.getString(cursor.getColumnIndexOrThrow("instal_date"));
            String lat = cursor.getString(cursor.getColumnIndexOrThrow("lat"));
            String lng = cursor.getString(cursor.getColumnIndexOrThrow("lng"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));


            dataModel.setId(ids);
            dataModel.setVeh_no(veh_no);
            dataModel.setDepo(depo);
            dataModel.setDivision(division);
            dataModel.setImieno(imieno);
            dataModel.setRemarks(remarks);
            dataModel.setInstal_time(instal_time);
            dataModel.setInstal_date(instal_date);
            dataModel.setLat(lat);
            dataModel.setLng(lng);
            dataModel.setAddress(address);

            data.add(dataModel);

        }
        return data;
    }

    public ArrayList<De_installDeviceModel> getDe_Install_Arry_list() {
        ArrayList<De_installDeviceModel> data = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MAINTAINCE_TABLE + " ;", null);

        De_installDeviceModel dataModel = null;
        while (cursor.moveToNext()) {

            dataModel = new De_installDeviceModel ();
            String ids = cursor.getString(cursor.getColumnIndexOrThrow("row_id"));

            String veh_no = cursor.getString(cursor.getColumnIndexOrThrow("veh_no"));
            String depo = cursor.getString(cursor.getColumnIndexOrThrow("depo"));
            String division = cursor.getString(cursor.getColumnIndexOrThrow("division"));
            String imieno = cursor.getString(cursor.getColumnIndexOrThrow("imieno"));
            String remarks = cursor.getString(cursor.getColumnIndexOrThrow("remarks"));
            String instal_time = cursor.getString(cursor.getColumnIndexOrThrow("instal_time"));
            String instal_date = cursor.getString(cursor.getColumnIndexOrThrow("instal_date"));
            String lat = cursor.getString(cursor.getColumnIndexOrThrow("lat"));
            String lng = cursor.getString(cursor.getColumnIndexOrThrow("lng"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));


            dataModel.setId(ids);
            dataModel.setVeh_no(veh_no);
            dataModel.setDepo(depo);
            dataModel.setDivision(division);
            dataModel.setImieno(imieno);
            dataModel.setRemarks(remarks);
            dataModel.setInstal_time(instal_time);
            dataModel.setInstal_date(instal_date);
            dataModel.setLat(lat);
            dataModel.setLng(lng);
            dataModel.setAddress(address);

            data.add(dataModel);

        }
        return data;
    }
    public ArrayList<MaintainanceDeviceModel> getMaintaince_Arry_list() {
        ArrayList<MaintainanceDeviceModel> data = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MAINTAINCE_TABLE + " ;", null);

        MaintainanceDeviceModel dataModel = null;
        while (cursor.moveToNext()) {

            dataModel = new MaintainanceDeviceModel ();
            String ids = cursor.getString(cursor.getColumnIndexOrThrow("row_id"));

            String veh_no = cursor.getString(cursor.getColumnIndexOrThrow("veh_no"));
            String depo = cursor.getString(cursor.getColumnIndexOrThrow("depo"));
            String division = cursor.getString(cursor.getColumnIndexOrThrow("division"));
            String imieno = cursor.getString(cursor.getColumnIndexOrThrow("imieno"));
            String last_location = cursor.getString(cursor.getColumnIndexOrThrow("last_location"));
            String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
            String problem_type = cursor.getString(cursor.getColumnIndexOrThrow("problem_type"));
            String action_type = cursor.getString(cursor.getColumnIndexOrThrow("action_taken"));
            String remarks = cursor.getString(cursor.getColumnIndexOrThrow("remarks"));
            String instal_time = cursor.getString(cursor.getColumnIndexOrThrow("instal_time"));
            String instal_date = cursor.getString(cursor.getColumnIndexOrThrow("instal_date"));
            String lat = cursor.getString(cursor.getColumnIndexOrThrow("lat"));
            String lng = cursor.getString(cursor.getColumnIndexOrThrow("lng"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));

            dataModel.setId(ids);
            dataModel.setVeh_no(veh_no);
            dataModel.setDepo(depo);
            dataModel.setDivision(division);
            dataModel.setImieno(imieno);
            dataModel.setLast_location(last_location);
            dataModel.setStatus(status);
            dataModel.setProblem_type(problem_type);
            dataModel.setActon_taken(action_type);
            dataModel.setRemarks(remarks);
            dataModel.setInstal_time(instal_time);
            dataModel.setInstal_date(instal_date);
            dataModel.setLat(lat);
            dataModel.setLng(lng);
            dataModel.setAddress(address);

            data.add(dataModel);

        }
        return data;
    }


    public ArrayList<ReplaceDeviceModel> getReplace_Arry_list() {
        ArrayList<ReplaceDeviceModel> data = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + REPLACE_TABLE + " ;", null);

        ReplaceDeviceModel dataModel = null;
        while (cursor.moveToNext()) {

            dataModel = new ReplaceDeviceModel ();
            String ids = cursor.getString(cursor.getColumnIndexOrThrow("row_id"));

            String veh_no = cursor.getString(cursor.getColumnIndexOrThrow("veh_no"));
            String depo = cursor.getString(cursor.getColumnIndexOrThrow("depo"));
            String division = cursor.getString(cursor.getColumnIndexOrThrow("division"));
            String old_imieno = cursor.getString(cursor.getColumnIndexOrThrow("old_imie"));
            String new_imieno = cursor.getString(cursor.getColumnIndexOrThrow("new_imie"));
            String remarks = cursor.getString(cursor.getColumnIndexOrThrow("remarks"));
            String instal_time = cursor.getString(cursor.getColumnIndexOrThrow("instal_time"));
            String instal_date = cursor.getString(cursor.getColumnIndexOrThrow("instal_date"));
            String lat = cursor.getString(cursor.getColumnIndexOrThrow("lat"));
            String lng = cursor.getString(cursor.getColumnIndexOrThrow("lng"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));

            dataModel.setId(ids);
            dataModel.setVeh_no(veh_no);
            dataModel.setDepo(depo);
            dataModel.setDivision(division);
            dataModel.setOld_imie(old_imieno);
            dataModel.setNew_imieno(new_imieno);
            dataModel.setRemarks(remarks);
            dataModel.setInstal_time(instal_time);
            dataModel.setInstal_date(instal_date);
            dataModel.setLat(lat);
            dataModel.setLng(lng);
            dataModel.setAddress(address);

            data.add(dataModel);

        }
        return data;
    }

}
