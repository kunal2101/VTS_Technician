package com.rtl.vts_technician.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.rtl.vts_technician.Constants.Utility;
import com.rtl.vts_technician.Database.DatabaseHelper;
import com.rtl.vts_technician.PreferenceHelper;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Utils.GPSTracker;
import com.rtl.vts_technician.Utils.LocationProvider;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;
import com.rtl.vts_technician.Volley.CustomProgressBar;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MaintenaceDevice_Activity extends AppCompatActivity implements LocationProvider.LocationCallback {
    ImageView pimage;
    TextView pName,txtDepo,txtDivision,txtImeiNo,txtInstaDate,tv_latlong,txtInstaFromTime,txtVehicleNo,txtLastLocation,txtProbleIden,txtActionTaken,txtDivi_ent,
            txtDepo_ent, txtSatus, txtLasttracking, txtRemarks, spinner_imie_replace;
    EditText txtDeviceId, txtDate, txtLocation, et_Remarks, et_ProbleIden, et_ActionTaken;
    Button btnSubmit,btn_getlatlong,btn_search;
    LinearLayout ly_veh_ent, ly_div_srch, ly_div_ent, ly_depo_srch, ly_depo_ent, ly_imie_srch, ly_imie_ent,
            ly_txt_remarks, ly_et_remarks, ly_txt_prob, ly_et_prob, ly_actioc_txt, ly_actioc_et;
    DatePickerDialog picker;
    GPSTracker gps;
    ProgressDialog pdialog;
    Button btn_uploadPhoto;
    double latitude,longitude;
    String response = null;
    DatabaseHelper dbHelper;
    ImageView form_image;
    public LocationProvider mLocationProvider;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 1, SELECT_FILE = 2;
    private String encoded;
    PreferenceHelper preferenceHelper;
    ArrayList<HashMap<String, String>> allRemarks;
    String st_newImeino ="";
    double currentLatitude;
    double currentLongitude;
    ArrayList<HashMap<String, String>> alldivision;
    ArrayList<HashMap<String, String>> alldepo;
    ArrayList<HashMap<String, String>> allProblems;
    ArrayList<HashMap<String, String>> allActions;

    String div_id = "";
    String divi_code = "", depo_code="", pr_ID = "";
    String depo ="", division = "", imieno = "", instantDate = "", instantTime ="", remarks ="", vehNo = "", address = "";

    SearchableSpinner spinner_imie;
    ArrayList<String> imieList;
    LinearLayout ly_replace_imie;
    String selected_date = "";
    boolean isMockAddress;
    TextView  tv_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity__maintenance_device);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        preferenceHelper = new PreferenceHelper(this);
        imieList        = new ArrayList<>();

        pName           = (TextView) findViewById(R.id.pname);
        txtDepo         = (TextView) findViewById(R.id.txtDepo);
        txtDivision     =  (TextView) findViewById(R.id.txtDivision);
        txtImeiNo       =   (TextView) findViewById(R.id.txtImeiNo);
        txtInstaDate    =   (TextView) findViewById(R.id.txtInstaDate);
        txtRemarks      =    (TextView) findViewById(R.id.txtRemarks);
        tv_latlong      =  (TextView)  findViewById(R.id.tv_latlong);
        txtInstaFromTime=  (TextView)  findViewById(R.id.txtInstaFromTime);
        txtVehicleNo    =   (TextView) findViewById(R.id.txtVehicleNo);
        txtLastLocation =    (TextView) findViewById(R.id.txtLastLocation);
        txtSatus        =  (TextView)  findViewById(R.id.txtSatus);
        txtProbleIden   =   (TextView) findViewById(R.id.txtProbleIden);
        txtActionTaken  =  (TextView)  findViewById(R.id.txtActionTaken);
        pimage          = (ImageView) findViewById(R.id.pimage);
        spinner_imie_replace = (TextView) findViewById(R.id.spinner_imie_replace);
        btnSubmit       = (Button) findViewById(R.id.btnSubmit);
        btn_getlatlong  = (Button) findViewById(R.id.btn_getlatlong);
        btn_search      =   (Button) findViewById(R.id.btn_search);
        form_image      =   (ImageView) findViewById( R.id.form_image);
        btn_uploadPhoto =   (Button) findViewById( R.id.btn_uploadPhoto);

        ly_veh_ent      = (LinearLayout) findViewById(R.id.ly_veh_ent);
        ly_depo_srch    = (LinearLayout) findViewById(R.id.ly_depo_srch);
        ly_depo_ent     = (LinearLayout) findViewById(R.id.ly_depo_ent);
        ly_div_srch     =   (LinearLayout)  findViewById(R.id.ly_div_srch);
        ly_div_ent      =   (LinearLayout) findViewById(R.id.ly_div_ent);
        ly_imie_srch    = (LinearLayout)  findViewById(R.id.ly_imie_srch);
        ly_imie_ent     =   (LinearLayout)  findViewById(R.id.ly_imie_ent);
        ly_replace_imie =   (LinearLayout) findViewById(R.id.ly_replace_imie);
        txtDivi_ent     =   (TextView)  findViewById(R.id.txtDivi_ent);
        txtDepo_ent     = (TextView)  findViewById(R.id.txtDepo_ent);
        txtLasttracking = (TextView) findViewById(R.id.txtLasttracking);
        et_Remarks      =  (EditText) findViewById(R.id.et_Remarks);
        ly_txt_remarks  = (LinearLayout) findViewById(R.id.ly_txt_remarks);
        ly_et_remarks   = (LinearLayout) findViewById(R.id.ly_et_remarks);

        et_ProbleIden   = (EditText) findViewById(R.id.et_ProbleIden);
        et_ActionTaken  = (EditText) findViewById(R.id.et_ActionTaken) ;
        ly_txt_prob     = (LinearLayout) findViewById(R.id.ly_txt_prob);
        ly_et_prob      = (LinearLayout) findViewById(R.id.ly_et_prob);
        ly_actioc_txt   = (LinearLayout) findViewById(R.id.ly_actioc_txt);
        ly_actioc_et   = (LinearLayout) findViewById(R.id.ly_actioc_et);
        spinner_imie    = (SearchableSpinner) findViewById(R.id.spinner_imie);
        tv_msg          = (TextView) findViewById(R.id.tv_msg);

        pName.setText("Device Manitenance");

        dbHelper = new DatabaseHelper(this);

        txtDivi_ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnection()){
                    GetAllDivision();
                }
            }
        });

        txtDepo_ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (div_id.length() == 0){
                    Toast.makeText(MaintenaceDevice_Activity.this, "Select your division first", Toast.LENGTH_SHORT).show();
                }else {
                    if (isConnection()) {
                        GetSelectedDepo(div_id);
                    }
                }
            }
        });

        txtRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnection()){
                    GetAllRemarks();
                }
            }
        });

     if (isConnection()){
            GetAllImeiNo();
        }

        spinner_imie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imieno = spinner_imie.getItemAtPosition(position).toString();

                Toast.makeText(MaintenaceDevice_Activity.this, imieno, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      /*  txtImeiNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpenForIMEIist();
            }
        });*/

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentDate_temp = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        selected_date = currentDate_temp;
        txtInstaDate.setText(currentDate);
        txtInstaFromTime.setText(currentTime);

        txtInstaDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MaintenaceDevice_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtInstaDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                selected_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                //txtInstaDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        txtInstaFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MaintenaceDevice_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtInstaFromTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        btn_getlatlong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMockAddress) {
                    getAddress();
                    tv_msg.setVisibility(View.GONE);
                }else{
                    tv_msg.setVisibility(View.VISIBLE);
                }

            }
        });

        txtVehicleNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MaintenaceDevice_Activity.this, SearchViewActivity.class);
                startActivityForResult(intent, 3);


            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehNo = txtVehicleNo.getText().toString().trim();
                ly_veh_ent.setVisibility(View.GONE);
                ly_div_ent.setVisibility(View.GONE);
                ly_depo_ent.setVisibility(View.GONE);
                ly_imie_ent.setVisibility(View.GONE);
                //ly_imie_ent.setVisibility(View.VISIBLE);
                ly_div_srch.setVisibility(View.VISIBLE);
                ly_depo_srch.setVisibility(View.VISIBLE);
                ly_imie_srch.setVisibility(View.VISIBLE);
                // ly_imie_srch.setVisibility(View.GONE);

                if (isConnection()){
                    GetVehicleDetail(vehNo);
                }
            }
        });

        txtProbleIden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnection()){
                    GetAllProblems();
                }

            }
        });

        txtActionTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtActionTaken.getText().toString().equals("OTHER")) {
                }else {
                    if (pr_ID.length() == 0){
                        Toast.makeText(MaintenaceDevice_Activity.this, "Select Problem type first.", Toast.LENGTH_SHORT).show();
                    }else {
                        if (isConnection()) {
                            GetSelectedAction(pr_ID);
                        }
                    }
                }

            }
        });

        //txtLastLocation.setText("Pune Railaway Station");
       // txtSatus.setText("Status :- Off Road");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String depo = txtDepo.getText().toString().trim();
                String division = txtDivision.getText().toString().trim();
                String imienos =  txtImeiNo.getText().toString().trim();
                String instantDate = selected_date;//txtInstaDate.getText().toString().trim();
                String instantTime = txtInstaFromTime.getText().toString().trim();
                String remarks = txtRemarks.getText().toString().trim();
                String vehNo = txtVehicleNo.getText().toString().trim();
                String address = tv_latlong.getText().toString().trim();
                String last_loc = txtLastLocation.getText().toString().trim();
                String status = txtSatus.getText().toString().trim();
                String problem_type = txtProbleIden.getText().toString().trim();
                String acton_taken = txtActionTaken.getText().toString().trim();

                if (vehNo.equals("Vehicle No")){
                    Toast.makeText(MaintenaceDevice_Activity.this, "Select vehicle no.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (depo.equals("Depot")){
                    Toast.makeText(MaintenaceDevice_Activity.this, "Select Depot Name.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (imienos.equals("Select IMEI No")){
                    Toast.makeText(MaintenaceDevice_Activity.this, "Select IMIE no.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(tv_latlong.getText().toString().trim())){
                    Toast.makeText(MaintenaceDevice_Activity.this, "Address should not be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txtLastLocation.getText().toString().trim())){
                    Toast.makeText(MaintenaceDevice_Activity.this, "Last Location should not be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(txtSatus.getText().toString().trim())){
                    Toast.makeText(MaintenaceDevice_Activity.this, "Status should not be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (problem_type.equals("Problem Identified")){
                    Toast.makeText(MaintenaceDevice_Activity.this, "Select Problem Type", Toast.LENGTH_LONG).show();
                    return;
                }
                if (txtProbleIden.getText().toString().equals("OTHER")){
                    if (TextUtils.isEmpty(et_ProbleIden.getText().toString())){
                        Toast.makeText(MaintenaceDevice_Activity.this, "Enter Problem Type", Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        problem_type = et_ProbleIden.getText().toString().trim();
                    }
                }
                if (txtActionTaken.equals("Action Taken")){
                    Toast.makeText(MaintenaceDevice_Activity.this, "Choose Action type", Toast.LENGTH_LONG).show();
                    return;
                }
                if (txtActionTaken.getText().toString().equals("OTHER")){
                    if (TextUtils.isEmpty(et_ActionTaken.getText().toString())) {
                        Toast.makeText(MaintenaceDevice_Activity.this, "Enter Action Type", Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        acton_taken = et_ActionTaken.getText().toString().trim();
                    }
                }

                if (remarks.equals("Choose Remarks")){
                    Toast.makeText(MaintenaceDevice_Activity.this, "Select remarks", Toast.LENGTH_LONG).show();
                    return;
                }
                if (txtRemarks.getText().toString().equals("OTHERS")){
                    if (TextUtils.isEmpty(et_Remarks.getText().toString().trim())) {
                        Toast.makeText(MaintenaceDevice_Activity.this, "Enter remarks", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        remarks = et_Remarks.getText().toString().trim();
                    }
                }

                if (tv_latlong.getText().toString().trim().equalsIgnoreCase("Current Location") || TextUtils.isEmpty(tv_latlong.getText().toString().trim())){
                    Toast.makeText( MaintenaceDevice_Activity.this, "Location Should not be empty", Toast.LENGTH_LONG).show();
                    return;
                }

                if (isConnection()) {
                       saveDeviceInfo();
                    }
            }
        });

        btn_uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission( Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                    }else{
                        selectImage();
                    }
                }

            }
        });

        mLocationProvider = new LocationProvider(this, this);
    }
    private void GetAllRemarks(){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.GET_REMATRKS_INSTALL_MAINTENAM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    allRemarks = new ArrayList<>();
                    allRemarks.clear();
                    if (jsonObject.getInt("code") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            HashMap<String, String> maps = new HashMap<>();
                            maps.put("KEY_REMARKS",  jobject.getString("remarks"));
                            allRemarks.add(maps);

                        }
                        dialogOpenForRemarksist();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());

            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void saveDeviceInfo(){
        String  tag_string_req = "string_req";
        String imienos = "";

        String instantDate = selected_date;//txtInstaDate.getText().toString().trim();
        String instantTime = txtInstaFromTime.getText().toString().trim();
        String depo = txtDepo.getText().toString().trim();
        String division = txtDivision.getText().toString().trim();
        String vehNo = txtVehicleNo.getText().toString().trim();
        String address = tv_latlong.getText().toString().trim();
        String problem_type = txtProbleIden.getText().toString().trim();
        String acton_taken = txtActionTaken.getText().toString().trim();
        String txtremarks  = txtRemarks.getText ().toString ().trim ();

        if (TextUtils.isEmpty(txtImeiNo.getText().toString().trim())){
            if (imieno.length() == 0){
                Toast.makeText(MaintenaceDevice_Activity.this, "Select IMIE No.", Toast.LENGTH_LONG).show();
                return;
            }else {
                imienos = imieno;
            }
        }else{
            imienos  = txtImeiNo.getText().toString().trim();
        }

        if (txtremarks.equals("Choose Remarks")){
            Toast.makeText(MaintenaceDevice_Activity.this, "Select remarks", Toast.LENGTH_LONG).show();
            return;
        }
        if (txtRemarks.getText().toString().equals("OTHERS")){
            if (TextUtils.isEmpty(et_Remarks.getText().toString().trim())){
                Toast.makeText( MaintenaceDevice_Activity.this, "Enter remarks", Toast.LENGTH_LONG).show();
                return;
            }else{
                txtremarks = et_Remarks.getText().toString().trim();
            }
        }

        String txtLastLocation_  = txtLastLocation.getText ().toString ().trim ();
        String txtSatus_ = txtSatus.getText ().toString ().trim ();

        CustomProgressBar.showCustomProgressDialog(MaintenaceDevice_Activity.this,"Please Wait...");
        JSONObject requestJson = new JSONObject();
        //{"deviceImei":"874355443","installationDate":"2020-04-28","installationTime":"21:45:23","depot_code":"KRD","division_code":"nasik","vehicle_reg_no":"MH235679","userLogin":"980098709"}

        if(acton_taken.equalsIgnoreCase ( "DEVICE CHANGED BY SERVICE UNIT" )){
            if(st_newImeino.equals("Choose IMIE NO")){
                Toast.makeText ( MaintenaceDevice_Activity.this , "Select New ImeiNo" , Toast.LENGTH_SHORT ).show ( );
                return;
            }else{
            try{
                requestJson.put("deviceImei", imienos);
                requestJson.put("installationDate", instantDate);
                requestJson.put("installationTime", instantTime);
                requestJson.put("depot_code", depo_code);
                requestJson.put("division_code", divi_code);
                requestJson.put("vehicle_reg_no", vehNo);
                requestJson.put("problemType", problem_type);
                requestJson.put("actionTaken", acton_taken);
                requestJson.put ( "remarks",txtremarks );
                requestJson.put("latitude", currentLatitude);
                requestJson.put("longitude", currentLongitude);
                requestJson.put("address", address);
                requestJson.put("lastLocation", txtLastLocation_);
                requestJson.put("status", txtSatus_);
                requestJson.put("userLogin", preferenceHelper.getUserLogin());
                requestJson.put("newImei", st_newImeino);
                requestJson.put("imageString", encoded);

                System.out.println("Diwas " +requestJson);

            }catch (Exception ev){
                System.out.print(ev.getMessage());
            }
                JsonObjectRequest req = new JsonObjectRequest(Constants.REPLACE, requestJson,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                   // Toast.makeText(NewActivityMaintenaceDevice.this, "SSSS"+response, Toast.LENGTH_LONG).show();
                                    if (response.getString("status").equals("true")) {
                                        Toast.makeText(MaintenaceDevice_Activity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                                    finish();
                                    }else{
                                        Toast.makeText(MaintenaceDevice_Activity.this, response.getString("message"), Toast.LENGTH_LONG).show();

                                    }

                                    CustomProgressBar.removeCustomProgressDialog();
                                } catch (Exception e) {
                                    CustomProgressBar.removeCustomProgressDialog();
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CustomProgressBar.removeCustomProgressDialog();
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }){
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                };

                AppController.getInstance().addToRequestQueue(req, tag_string_req);
            }

        }else {

            try{
                requestJson.put("deviceImei", imienos);
                requestJson.put("installationDate", instantDate);
                requestJson.put("installationTime", instantTime);
                requestJson.put("depot_code", depo_code);
                requestJson.put("division_code", divi_code);
                requestJson.put("vehicle_reg_no", vehNo);
                requestJson.put("problemType", problem_type);
                requestJson.put("actionTaken", acton_taken);
                requestJson.put( "remarks",txtremarks );
                requestJson.put("latitude", currentLatitude);
                requestJson.put("longitude", currentLongitude);
                requestJson.put("address", address);
                requestJson.put("lastLocation", txtLastLocation_);
                requestJson.put("status", txtSatus_);
                requestJson.put("userLogin", preferenceHelper.getUserLogin());
                requestJson.put("imageString", encoded);

                System.out.println("Diwas " +requestJson);

            }catch (Exception ev){
                System.out.print(ev.getMessage());
            }
            JsonObjectRequest req = new JsonObjectRequest(Constants.MAINTENANCE, requestJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                               // Toast.makeText(NewActivityMaintenaceDevice.this, "SSSS"+response, Toast.LENGTH_LONG).show();
                                if (response.getString("status").equals("true")) {
                                    Toast.makeText(MaintenaceDevice_Activity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                                    finish();

                                }else{
                                    Toast.makeText(MaintenaceDevice_Activity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                                }

                                CustomProgressBar.removeCustomProgressDialog();
                            } catch (Exception e) {
                                CustomProgressBar.removeCustomProgressDialog();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    CustomProgressBar.removeCustomProgressDialog();
                    VolleyLog.e("Error: ", error.getMessage());
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };

            AppController.getInstance().addToRequestQueue(req, tag_string_req);

        }

    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Cancel"};
        // final CharSequence[] items = { "Take Photo", "Take From Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission( MaintenaceDevice_Activity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                }

                // As per Need need to be uncomment for take image from gallery
                /* else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } */
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    protected boolean isConnection() {
        ConnectivityManager manage = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            Toast.makeText(MaintenaceDevice_Activity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void GetVehicleDetail(String vehicleNo){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(MaintenaceDevice_Activity.this,"Please Wait...");

        StringRequest strReq = new StringRequest( Request.Method.GET,
                Constants.GETVEHICLEDETAIL+vehicleNo+"/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        JSONObject jObj = jsonObject.getJSONObject ("data");

                        txtDepo.setText(jObj.getString ( "depot" ));
                        txtDivision.setText(jObj.getString ( "division" ));
                        txtLastLocation.setText ( jObj.getString ( "location" ));
                        txtImeiNo.setText ( jObj.getString ( "imei" ));
                        txtSatus.setText(jObj.getString("status"));
                        txtLasttracking.setText(jObj.getString("eventDate"));

                        divi_code = jObj.getString("divisionCode");
                        depo_code = jObj.getString("depotCode");

                    }else{
                        Toast.makeText(MaintenaceDevice_Activity.this, jsonObject.getString ( "message" ), Toast.LENGTH_LONG).show();
                        depo_code = "";
                        divi_code = "";
                        txtVehicleNo.setText("Select Vehicle No.");
                        //txtImeiNo.setText ("IMEI No");
                        txtDepo.setText("Depo");
                        txtDivision.setText("Division");
                        txtLastLocation.setText ( "Last Location" );
                        txtImeiNo.setText ( "IMEI No" );

                        ly_veh_ent.setVisibility(View.VISIBLE);
                        ly_div_ent.setVisibility(View.VISIBLE);
                        ly_depo_ent.setVisibility(View.VISIBLE);
                        ly_imie_ent.setVisibility(View.VISIBLE);
                        ly_div_srch.setVisibility(View.GONE);
                        ly_depo_srch.setVisibility(View.GONE);
                        ly_imie_srch.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse( VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed
        if (resultCode == RESULT_CANCELED){
            Toast.makeText(MaintenaceDevice_Activity.this, "User Cancel request", Toast.LENGTH_SHORT).show();
        } else if (requestCode == SELECT_FILE) {
            // onSelectFromGalleryResult(data);
        }else if (requestCode == REQUEST_CAMERA) {
            onCaptureImageResult(data);
            }else if (requestCode == 3) {
                String message_st=data.getStringExtra("VEH_REG_NO");

                txtVehicleNo.setText(message_st);
            }else if (resultCode == 9) {
            String message_st=data.getStringExtra("VEH_REG_NO");
            st_newImeino = message_st;
            spinner_imie_replace.setText(st_newImeino);

        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

        File destination = new File( Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        form_image.setImageBitmap(thumbnail);

        BitmapDrawable drawable = (BitmapDrawable) form_image.getDrawable();
        Bitmap imageBitmap = drawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);

    }


    @SuppressLint("NewApi")
    public void getAddress() {
        // create class object

        if (currentLatitude != 0.0 || currentLongitude != 0.0){
            new locationupdate().execute();
        }
/*
        gps = new GPSTracker(NewActivityMaintenaceDevice.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            new locationupdate().execute();


        } else {

            gps.showSettingsAlert();
        }*/
    }


    @Override
    public void handleNewLocation(Location location) {
        Log.d("Location", location.toString());
            isMockAddress = location.isFromMockProvider();

        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        if (currentLatitude != 0.0 && currentLongitude != 0.0){
            mLocationProvider.disconnect();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationProvider.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationProvider.disconnect();
    }

    private class locationupdate extends AsyncTask<String, String, String> {
        String myaddress;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (pdialog == null) {
                pdialog = new ProgressDialog(MaintenaceDevice_Activity.this);
                pdialog.setCancelable(false);
                pdialog.setMessage("Please Wait...");
                pdialog.show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
                StringBuilder sb = new StringBuilder();
                if (addresses.size() > 0) {

                    myaddress = addresses.get(0).getAddressLine(0);
                    response = myaddress;


                } else {
                    Address address = addresses.get(0);
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
                        sb.append(addresses.get(0).getAddressLine(0)).append(", ");

                    sb.append(address.getFeatureName()).append(", ");

                    sb.append(address.getThoroughfare()).append(", ");
                    sb.append(address.getLocality()).append(", ");
                    sb.append(address.getAdminArea()).append(", ");
                    sb.append(address.getPostalCode()).append(", ");
                    sb.append(address.getCountryName());
                    response = sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pdialog != null) {
                pdialog.dismiss();
                pdialog = null;
            }


            tv_latlong.setText(response);
            tv_latlong.setTextColor(getResources().getColor(R.color.black));

        }
    }


    private void dialogOpenForDivisionList() {

        final ArrayList<String> division_str = new ArrayList<String>();
        final ArrayList<String> division_id = new ArrayList<String>();
        final ArrayList<String> division_code = new ArrayList<String>();


        for(int ind = 0 ; ind< alldivision.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = alldivision.get(ind);
            division_id.add(map.get("KEY_DIV_ID"));
            division_str.add(map.get("KEY_DIV_NAME"));
            division_code.add(map.get("KEY_DIV_CODE"));
        }


        final CharSequence[] dialogList = division_str.toArray(new CharSequence[division_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( MaintenaceDevice_Activity.this);
        builderDialog.setTitle("Select Division Name");

        int count = dialogList.length;


        // Creating multiple selection by using setMutliChoiceItem method
        builderDialog.setSingleChoiceItems(dialogList, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        StringBuilder stringBuilder_name = new StringBuilder();
                        StringBuilder stringBuilder_code = new StringBuilder();

                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {

                                String getDiv_str = list.getItemAtPosition(i).toString();
                                int div_index = division_str.indexOf(getDiv_str);
                                String getDiv_id = division_id.get(div_index);
                                String getDiv_Code = division_code.get(div_index);

                                if (stringBuilder_name.length() > 0)
                                    stringBuilder_name.append(",");
                                    stringBuilder_name.append(getDiv_str);

                                if (stringBuilder.length() > 0)
                                    stringBuilder.append(",");
                                    stringBuilder.append(getDiv_id);

                                if (stringBuilder_code.length() > 0)
                                    stringBuilder_code.append(",");
                                    stringBuilder_code.append(getDiv_Code);
                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */

                        if (stringBuilder_name.toString().trim().equals("")) {

                            txtDivi_ent.setText("");
                            stringBuilder_name.setLength(0);
                        } else {
                            txtDivi_ent.setText(stringBuilder_name );
                            div_id = stringBuilder.toString();
                            divi_code = stringBuilder_code.toString();
                            //txtDepo.setText(getDivision_id[0]+"");
                            Toast.makeText( MaintenaceDevice_Activity.this, divi_code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        txtDivi_ent.setText("Select Division");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

    }

    private void dialogOpenForDepoList() {

        final ArrayList<String> depo_str = new ArrayList<String>();;
        final ArrayList<String> depo_id = new ArrayList<String>();;
        final ArrayList<String> depo_codes = new ArrayList<String>();

        for(int ind = 0 ; ind< alldepo.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = alldepo.get(ind);
            depo_id.add(map.get("KEY_DEPO_ID"));
            depo_str.add(map.get("KEY_DEPO_NAME"));
            depo_codes.add(map.get("KEY_DEPO_CODE"));
        }

        final CharSequence[] dialogList = depo_str.toArray(new CharSequence[depo_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( MaintenaceDevice_Activity.this);
        builderDialog.setTitle("Select Depot Name");

        // Creating multiple selection by using setMutliChoiceItem method
        builderDialog.setSingleChoiceItems(dialogList, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText( Registration_Activity.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        StringBuilder stringBuilder_name = new StringBuilder();
                        StringBuilder stringBuilder_code = new StringBuilder();

                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {
                                String getDepo_str = list.getItemAtPosition(i).toString();
                                int depo_index = depo_str.indexOf(getDepo_str);
                                String getDepo_id = depo_id.get(depo_index);
                                String getDepo_Code = depo_codes.get(depo_index);

                                if (stringBuilder_name.length() > 0)
                                    stringBuilder_name.append(",");
                                stringBuilder_name.append(getDepo_str);

                                if (stringBuilder.length() > 0)
                                    stringBuilder.append(",");
                                stringBuilder.append(getDepo_id);

                                if (stringBuilder_code.length() > 0)
                                    stringBuilder_code.append(",");
                                stringBuilder_code.append(getDepo_Code);

                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder_name.toString().trim().equals("")) {

                            txtDepo_ent.setText("");
                            stringBuilder_name.setLength(0);
                        } else {
                            txtDepo_ent.setText(stringBuilder_name );
                            depo_code = stringBuilder_code.toString();
                            //txtDepo.setText(getDivision_id[0]+"");
                            Toast.makeText( MaintenaceDevice_Activity.this, depo_code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        txtDepo_ent.setText("Select Depo");

                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

    }

    private void dialogActionTakenList() {
        final ArrayList<String> action_str = new ArrayList<String>();;

        for(int ind = 0 ; ind< allActions.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = allActions.get(ind);

            action_str.add(map.get("KEY_ACT_NAME"));
        }

        final CharSequence[] dialogList = action_str.toArray(new CharSequence[action_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(MaintenaceDevice_Activity.this);
        builderDialog.setTitle("Action Taken");
        int count = dialogList.length;

        // Creating multiple selection by using setMutliChoiceItem method
        builderDialog.setSingleChoiceItems(dialogList, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder_name = new StringBuilder();

                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);
                            if (checked) {

                            String getAction_str = list.getItemAtPosition(i).toString();
                           // int action_index = action_str.indexOf(getAction_str);

                            if (stringBuilder_name.length() > 0) stringBuilder_name.append(",");

                            stringBuilder_name.append(getAction_str);
                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder_name.toString().trim().equals("")) {

                        txtActionTaken.setText("");
                        stringBuilder_name.setLength(0);


                        } else {
                            txtActionTaken.setText(stringBuilder_name );
                            if(txtActionTaken.getText().toString().equalsIgnoreCase("DEVICE CHANGED BY SERVICE UNIT")){

                                ly_replace_imie.setVisibility(View.VISIBLE);

                                spinner_imie_replace.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(MaintenaceDevice_Activity.this, SearchViewImeiNoActivity.class);
                                        startActivityForResult(intent, 9);
                                    }
                                });

                               // final SearchableSpinner spinner_imie_dialog;

                               // android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(NewActivityMaintenaceDevice.this);
                                //alertDialogBuilder.setTitle("Reset Password");
                               // alertDialogBuilder.setTitle(Html.fromHtml("<font color='#18B086'>New IMEINO</font>"));

                               // final View itemView = LayoutInflater.from(NewActivityMaintenaceDevice.this)
                                        //.inflate(R.layout.new_dialog_mainte__imei_change, null, false);
                               // alertDialogBuilder.setView(itemView);

                              //  spinner_imie_dialog = (SearchableSpinner ) itemView.findViewById (R.id.spinner_imie_dialog ) ;

                               // ArrayAdapter<String> adapterss = new ArrayAdapter<String>(NewActivityMaintenaceDevice.this, R.layout.spinner_drop_down, imieList);
                               //// spinner_imie_dialog.setAdapter(adapterss);
                                //spinner_imie_dialog.setTitle("Choose IMIE NO.");

                              /* spinner_imie_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                   @Override
                                   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                       st_newImeino = spinner_imie_dialog.getItemAtPosition(position).toString();

                                   }

                                   @Override
                                   public void onNothingSelected(AdapterView<?> parent) {

                                   }
                               });
*/

                           /*     alertDialogBuilder.setPositiveButton ("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //do nothing it will be overridden


                                            }
                                        });

                                alertDialogBuilder.setNegativeButton(getResources().getText(R.string.cancel),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });


                                final android.app.AlertDialog alertDialog = alertDialogBuilder.create();

                                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(final DialogInterface dialog) {
                                        Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                        button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText ( NewActivityMaintenaceDevice.this , st_newImeino , Toast.LENGTH_SHORT ).show ( );
                                                //Log.d ( "nnnnnnnn","nnnnnn" );
                                                //st_newImeino = editText.getText ().toString ();
                                                dialog.cancel();
                                            }
                                        });
                                    }
                                });

                                alertDialog.show();*/
                            }else{
                                ly_replace_imie.setVisibility(View.GONE);
                            }
                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtActionTaken.setText("Action Taken");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

    }


    private void dialogProbleIdentifyList() {

        final ArrayList<String> pr_str = new ArrayList<String>();;
        final ArrayList<String> pr_id = new ArrayList<String>();;

        for(int ind = 0 ; ind< allProblems.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = allProblems.get(ind);
            pr_id.add(map.get("KEY_PR_ID"));
            pr_str.add(map.get("KEY_PR_NAME"));
        }


        final CharSequence[] dialogList = pr_str.toArray(new CharSequence[pr_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(MaintenaceDevice_Activity.this);
        builderDialog.setTitle("Problem Identified");
        int count = dialogList.length;
        boolean[] is_checked = new boolean[count];

        // Creating multiple selection by using setMutliChoiceItem method
        builderDialog.setSingleChoiceItems(dialogList, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        StringBuilder stringBuilder_name = new StringBuilder();

                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {
                                String getPr_str = list.getItemAtPosition(i).toString();
                                int depo_index = pr_str.indexOf(getPr_str);
                                String getPr_id = pr_id.get(depo_index);

                                if (stringBuilder_name.length() > 0) stringBuilder_name.append(",");
                                //if (stringBuilder.length() > 0) stringBuilder_name.append(",");
                                stringBuilder.append(getPr_id);
                                stringBuilder_name.append(getPr_str);
                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder_name.toString().trim().equals("")) {

                            txtProbleIden.setText("");
                            stringBuilder_name.setLength(0);
                        } else {
                            txtProbleIden.setText(stringBuilder_name);
                            pr_ID = stringBuilder.toString();
                            if (txtProbleIden.getText().toString().equals("OTHER")){
                                ly_et_prob.setVisibility(View.VISIBLE);
                                ly_actioc_et.setVisibility(View.VISIBLE);
                                txtActionTaken.setText("OTHER");


                            }else{
                                ly_et_prob.setVisibility(View.GONE);
                                ly_actioc_et.setVisibility(View.GONE);
                                txtActionTaken.setText("Action Taken");
                                et_ActionTaken.setText("");
                                et_ProbleIden.setText("");
                            }
                            //txtDepo.setText(getDivision_id[0]+"");
                            Toast.makeText( MaintenaceDevice_Activity.this, stringBuilder_name +"----"+ pr_ID, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtProbleIden.setText("Problem Identified");
                        pr_ID = "";
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

    }

    private void dialogOpenForRemarksist() {
        final ArrayList<String> remarks_str = new ArrayList<String>();

        for(int ind = 0 ; ind< allRemarks.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = allRemarks.get(ind);
            remarks_str.add(map.get("KEY_REMARKS"));
        }

        final CharSequence[] dialogList = remarks_str.toArray(new CharSequence[remarks_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( MaintenaceDevice_Activity.this);
        builderDialog.setTitle("Select Remarks");

        // Creating multiple selection by using setMutliChoiceItem method
        builderDialog.setSingleChoiceItems(dialogList, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText( NewActivity_AddDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {
                                String getRemarks_str = list.getItemAtPosition(i).toString();
                                int remarks_index = remarks_str.indexOf(getRemarks_str);
                                String getRemarks = remarks_str.get(remarks_index);

                                if (stringBuilder.length() > 0) stringBuilder.append(",");
                                stringBuilder.append(getRemarks);

                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder.toString().trim().equals("")) {

                            txtRemarks.setText("");
                            stringBuilder.setLength(0);


                        } else {
                            txtRemarks.setText(stringBuilder);
                            if (txtRemarks.getText().toString().equals("OTHERS")){
                                ly_et_remarks.setVisibility(View.VISIBLE);
                                ly_txt_remarks.setVisibility(View.VISIBLE);
                            }else{
                                ly_et_remarks.setVisibility(View.GONE);
                                ly_txt_remarks.setVisibility(View.VISIBLE);
                            }
                            // Toast.makeText(NewActivity_AddDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                            // mechType = String.valueOf(stringBuilder);
                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtRemarks.setText("Choose Remarks");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

    }


    private void GetAllDivision(){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.GETDIVISION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    alldivision = new ArrayList<>();
                    alldivision.clear();
                    if (jsonObject.getInt("code") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            HashMap<String, String> maps = new HashMap<>();
                            maps.put("KEY_DIV_ID",  jobject.getString("divisionId"));
                            maps.put("KEY_DIV_NAME",jobject.optString("divisionName"));
                            maps.put("KEY_DIV_CODE",jobject.optString("divisionCode"));
                            alldivision.add(maps);

                        }
                        dialogOpenForDivisionList();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());

            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

       private void GetSelectedDepo(String div_id){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.GETDEPO + div_id + "/lksdjf984ew/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    alldepo = new ArrayList<>();
                    alldepo.clear();
                    if (jsonObject.getInt("code") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            HashMap<String, String> maps = new HashMap<>();
                            maps.put("KEY_DEPO_ID",  jobject.getString("depot_id"));
                            maps.put("KEY_DEPO_NAME",jobject.optString("depot_name"));
                            maps.put("KEY_DEPO_CODE",jobject.optString("depotCode"));
                            alldepo.add(maps);

                        }
                        dialogOpenForDepoList();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());

            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void GetAllProblems(){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.PROBLEMS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    allProblems = new ArrayList<>();
                    allProblems.clear();
                    if (jsonObject.getInt("code") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            HashMap<String, String> maps = new HashMap<>();
                            maps.put("KEY_PR_ID",  jobject.getString("problemId"));
                            maps.put("KEY_PR_NAME",jobject.optString("description"));

                            allProblems.add(maps);

                        }
                        dialogProbleIdentifyList();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());

            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void GetSelectedAction(String problem_id){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.ACTIONLIST + problem_id + "/lksdjf984ew/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    allActions = new ArrayList<>();
                    allActions.clear();
                   // HashMap<String, String> mapss = new HashMap<>();
                   // mapss.put("KEY_ACT_NAME",  "Device Replace");
                   // allActions.add(mapss);
                    //allActions.add(0,  mapss);
                    if (jsonObject.getInt("code") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            String name = jsonArray.get(i).toString();
                            HashMap<String, String> maps = new HashMap<>();
                            //maps.put("KEY_DEPO_ID",  jsonArray.getJSONObject(i));
                            maps.put("KEY_ACT_NAME", name);
                            allActions.add(maps);
                        }
                        dialogActionTakenList();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());

            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void GetAllImeiNo(){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(MaintenaceDevice_Activity.this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.unmappedImei, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        imieList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            HashMap<String, String> maps = new HashMap<>();
                            //maps.put("KEY_IMEI_NO",  jobject.getString("deviceImei"));
                            // vehicleList.add(maps);
                            imieList.add(jobject.getString("device_imei"));
                        }
                        ArrayAdapter<String> adapters = new ArrayAdapter<String>(MaintenaceDevice_Activity.this, R.layout.spinner_drop_down, imieList);
                        spinner_imie.setAdapter(adapters);
                        spinner_imie.setTitle("Choose IMIE NO.");



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());

            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
