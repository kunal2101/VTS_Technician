package com.rtl.vts_technician.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.rtl.vts_technician.PreferenceHelper;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;
import com.rtl.vts_technician.Volley.CustomProgressBar;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AdminDashBoard extends AppCompatActivity {

    ImageView imgSearch;
    String  division_code="";
    TextView editVehicleNo;
    Button btnShow, btnDownVeh, btnTechList, btnReport;
    TextView txt_user, txtToDate, txtFromDate;
    PreferenceHelper preferenceHelper;
    String selected_to_date, selected_from_date;
    DatePickerDialog picker;
    SearchableSpinner spinner_division, spinner_Tech;
    ArrayList<HashMap<String, String>> alldivision;
    ArrayList<HashMap<String, String>> allTechni;
    ArrayList<String> div_name;
    ArrayList<String>tech_name;
    String techName = "", userName = "";
    LinearLayout ly_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);

        preferenceHelper = new PreferenceHelper(AdminDashBoard.this);

        txt_user        =   (TextView) findViewById (R.id.txt_user);
        btnShow         =   (Button)    findViewById (R.id.btnShow) ;
        imgSearch       =   (ImageView)  findViewById (R.id.imgSearch);
        editVehicleNo   =   (TextView) findViewById (R.id.editVehicleNo);
        txtFromDate     =   (TextView) findViewById(R.id.txtFromDate);
        txtToDate       =   (TextView) findViewById(R.id.txtToDate);
        spinner_Tech    =   (SearchableSpinner) findViewById(R.id.spinner_Tech);
        spinner_division =   (SearchableSpinner) findViewById(R.id.spinner_division);
        btnDownVeh      =    (Button) findViewById(R.id.btnDownVeh);
        ly_logout       =   (LinearLayout) findViewById(R.id.ly_logout);
        btnTechList     =   (Button) findViewById(R.id.btnTechList);
        btnReport       =   (Button) findViewById(R.id.btnReport);

        txt_user.setText ("Welcome " + preferenceHelper.getAdminLogin());
        userName = preferenceHelper.getUser_Name();

        if (userName.equalsIgnoreCase("super@admin")){
            btnTechList.setVisibility(View.VISIBLE);
        }else{
            btnTechList.setVisibility(View.GONE);
        }

        try {
           alldivision = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("KEY_ARRAY");
           div_name = new ArrayList<>();
           div_name.clear();
           for (int i = 0; i < alldivision.size(); i++){
               HashMap<String,String> map = alldivision.get(i);
            div_name.add(map.get("KEY_DIV_NAME"));
           }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        /*if (isConnection()){
            GetAllTechnician();
        }*/

        editVehicleNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashBoard.this, SearchViewActivity.class);
                startActivityForResult(intent, 3);
            }
        });

        imgSearch.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                String vehNo = editVehicleNo.getText().toString().trim();

                if (!TextUtils.isEmpty(vehNo)) {

                            Intent inty = new Intent ( AdminDashBoard.this , ActivityVehicleDetail.class );
                            inty.putExtra ("vehicelNo" , vehNo);
                            startActivity (inty);

                    } else {
                        Toast.makeText ( AdminDashBoard.this , "Plaese enter the Valid vehicle number" , Toast.LENGTH_SHORT ).show ( );
                    }
            }
        } );

        btnTechList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(AdminDashBoard.this, TechnicianList.class));
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this, DaywiseActivityReport.class)
                        .putExtra("KEY_ARRAY", alldivision));
            }
        });

        final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentDate_temp = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        selected_from_date = currentDate_temp;
        selected_to_date = currentDate_temp;
        txtFromDate.setText(currentDate);
        txtToDate.setText(currentDate);

        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog( AdminDashBoard.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String monthString = (monthOfYear + 1) < 10 ? "0"+(monthOfYear + 1) : ""+(monthOfYear + 1);
                                String dayString = dayOfMonth < 10 ? "0"+dayOfMonth : ""+dayOfMonth;
                                //txtFromDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                txtFromDate.setText(dayString + "-" + monthString + "-" + year);
                                selected_from_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                // Toast.makeText(NewActivity_AddDevice.this, selected_date, Toast.LENGTH_LONG).show();
                                //txtInstaDate.setText(year + "-" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();

                //Not allow user to select previous date
                Date newDate = cldr.getTime();
                picker.getDatePicker().setMaxDate(newDate.getTime());
            }

        });

        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog( AdminDashBoard.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String monthString = (monthOfYear + 1) < 10 ? "0"+(monthOfYear + 1) : ""+(monthOfYear + 1);
                                String dayString = dayOfMonth < 10 ? "0"+dayOfMonth : ""+dayOfMonth;
                                txtToDate.setText(dayString + "-" + monthString + "-" + year);

                                selected_to_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                // Toast.makeText(NewActivity_AddDevice.this, selected_date, Toast.LENGTH_LONG).show();
                                //txtInstaDate.setText(year + "-" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();

                //Not allow user to select previous date
                Date newDate = cldr.getTime();
                picker.getDatePicker().setMaxDate(newDate.getTime());
            }
        });

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, div_name);
        spinner_division.setAdapter(adapter);
        spinner_division.setTitle("Select Division");
        spinner_Tech.setTitle("Select Technician");

        spinner_division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map = alldivision.get(position);
                 division_code = map.get("KEY_DIV_CODE");
                 if (isConnection()){
                     if (division_code.equalsIgnoreCase("All")){
                         GetAllTechnician();
                     }else{
                         GetTechnicianByDivision(division_code);
                     }

                 }
                //Toast.makeText(AdminDashBoard.this, "code: " + division_code, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_Tech.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map = allTechni.get(position);
                techName = map.get("Key_userlogin");
                Toast.makeText(AdminDashBoard.this, "code: " + techName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (division_code.equals("All")){
                    division_code = "";
                }
                if (techName.equals("All")){
                    techName = "all";
                }
                Intent in = new Intent(AdminDashBoard.this, PerformanceReport.class);
                in.putExtra("KEY_DIVI_CODE", division_code);
                in.putExtra("KEY_TO_DATE", selected_to_date);
                in.putExtra("KEY_FROM_DATE", selected_from_date);
                in.putExtra("KEY_TECH_NAME", techName);
                in.putExtra("KEY_TECH_ARRY", tech_name);
                startActivity(in);

            }
        });

        btnDownVeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashBoard.this, RegionWiseDownVehicle.class));
            }
        });

        ly_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });
    }

    public void showLogoutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("")
                .setTitle("Logout");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to logout from this application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        preferenceHelper.putAdminPassword ( "" );
                        preferenceHelper.putAdminLogin ( "" );
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Logout");
        alert.show();
    }

    private void GetAllTechnician(){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(AdminDashBoard.this,"Please Wait...");

        StringRequest strReq = new StringRequest( Request.Method.GET,
                Constants.ALLTECHNICIAN + preferenceHelper.getToken()+"/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        allTechni = new ArrayList<>();
                        allTechni.clear();

                        tech_name = new ArrayList<>();
                        tech_name.clear();

                        tech_name.add(0, "All");

                        HashMap<String, String> maps1 = new HashMap<>();
                        maps1.put("Key_name",  "All");
                        maps1.put("Key_userlogin","All");
                        allTechni.add(maps1);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("Key_name", jobject.getString("name"));
                            map.put("Key_userlogin", jobject.getString("userLogin"));
                            allTechni.add(map);
                            tech_name.add(jobject.getString("name"));
                        }
                        CustomProgressBar.removeCustomProgressDialog();
                        ArrayAdapter adapter = new ArrayAdapter(AdminDashBoard.this, android.R.layout.simple_spinner_dropdown_item, tech_name);
                        spinner_Tech.setAdapter(adapter);
                    }else{
                        Toast.makeText(AdminDashBoard.this,"No result found", Toast.LENGTH_LONG).show();
                        CustomProgressBar.removeCustomProgressDialog();
                    }
                } catch (JSONException e) {
                    CustomProgressBar.removeCustomProgressDialog();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse( VolleyError error) {
                CustomProgressBar.removeCustomProgressDialog();
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void GetTechnicianByDivision(String div_code){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(AdminDashBoard.this,"Please Wait...");

        StringRequest strReq = new StringRequest( Request.Method.GET,
                Constants.GET_TECHNI_BY_DIV + div_code +"/"+ preferenceHelper.getToken()+"/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equals("success")) {
                        allTechni = new ArrayList<>();
                        allTechni.clear();

                        tech_name = new ArrayList<>();
                        tech_name.clear();

                        //tech_name.add(0, "All");

                       /* HashMap<String, String> maps1 = new HashMap<>();
                        maps1.put("Key_name",  "All");
                        maps1.put("Key_userlogin","All");
                        allTechni.add(maps1);*/
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("Key_name", jobject.getString("name"));
                            map.put("Key_userlogin", jobject.getString("userLogin"));
                            allTechni.add(map);
                            tech_name.add(jobject.getString("name"));
                        }
                        CustomProgressBar.removeCustomProgressDialog();
                        ArrayAdapter adapter = new ArrayAdapter(AdminDashBoard.this, android.R.layout.simple_spinner_dropdown_item, tech_name);
                        spinner_Tech.setAdapter(adapter);
                    }else{
                        Toast.makeText(AdminDashBoard.this,"No result found", Toast.LENGTH_LONG).show();
                        CustomProgressBar.removeCustomProgressDialog();
                    }
                } catch (JSONException e) {
                    CustomProgressBar.removeCustomProgressDialog();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse( VolleyError error) {
                CustomProgressBar.removeCustomProgressDialog();
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    protected boolean isConnection() {
        ConnectivityManager manage = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            Toast.makeText(AdminDashBoard.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed
        if (resultCode == RESULT_CANCELED){
            Toast.makeText(AdminDashBoard.this, "User Cancel request", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 3) {
            String message_st=data.getStringExtra("VEH_REG_NO");

            editVehicleNo.setText(message_st);
        }
    }
}