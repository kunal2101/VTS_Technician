package com.rtl.vts_technician;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.rtl.vts_technician.volley.AppController;
import com.rtl.vts_technician.volley.Constants;
import com.rtl.vts_technician.volley.CustomProgressBar;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class New_fragment_RawData extends Fragment implements
        DatePickerDialog.OnDateSetListener {
    ArrayList<HashMap<String,String>> vehicleList;
    ArrayList<String> vList;
    SearchableSpinner spinner_vehicle;
    LinearLayout lin_sea_;
    private ArrayAdapter<String> adapters;
    TextView date_select,search_box,txtAddImeiNO;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonths;
    Calendar calendar;
    String  s_name="";
    String current_date;
    PreferenceHelper preferenceHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_frag_rawdata, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        spinner_vehicle     = (SearchableSpinner)view.  findViewById(R.id.spinner_vehicle);
        lin_sea_=(LinearLayout) view. findViewById(R.id.lin_sea_);
        date_select=(TextView)view. findViewById(R.id.date_select) ;
        search_box=(TextView)view. findViewById(R.id.search_box) ;
        txtAddImeiNO=(TextView)view. findViewById(R.id.txtAddImeiNO);
        vehicleList = new ArrayList<>();
        vList = new ArrayList<>();
        preferenceHelper = new PreferenceHelper(getActivity());
        //
        //  Toast.makeText(this, "------"+ preferenceHelper.getUser_id(), Toast.LENGTH_SHORT).show();


       /* ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/




        Calendar now = Calendar.getInstance();
        int monthOfYear = now.get(Calendar.MONTH);
        int dayOfMonth = now.get(Calendar.DAY_OF_MONTH);
        String monthString = (++monthOfYear) < 10 ? "0"+(monthOfYear) : ""+(monthOfYear);
        String dayString = dayOfMonth < 10 ? "0"+dayOfMonth : ""+dayOfMonth;
        current_date = now.get(Calendar.YEAR)+"-"+monthString+"-"+dayString;
        date_select.setText("Date :- " +current_date);




        spinner_vehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_name = spinner_vehicle.getItemAtPosition(position).toString();

                // Toast.makeText(getActivity(), s_name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        search_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s_name.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select IMEINO .....", Toast.LENGTH_LONG).show();
                } else {
                    // Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
                    Intent inty = new Intent(getActivity(), DetailActivity.class);
                    inty.putExtra("imeino", s_name);
                    inty.putExtra("date", current_date);
                    getActivity().startActivity(inty);
                }
            }
        });

        if(preferenceHelper.getUser_id()==null){

            GetAllImeiNo();
        }else {
            try {
                JSONObject jsonObject = new JSONObject(preferenceHelper.getUser_id());
                if (jsonObject.getString("status").equals("success")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobject = jsonArray.getJSONObject(i);
                        HashMap<String, String> maps = new HashMap<>();
                        maps.put("KEY_IMEI_NO",  jobject.getString("deviceImei")+"/"+jobject.getString("deviceId") );
                        vehicleList.add(maps);
                        vList.add(jobject.getString("deviceImei")+" / "+jobject.getString("deviceId"));
                    }
                    adapters = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, vList);
                    spinner_vehicle.setAdapter(adapters);
                    spinner_vehicle.setTitle("Select ImeiNo/DeviceId ");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        txtAddImeiNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               preferenceHelper.editor.clear();
                preferenceHelper.editor.apply();
                preferenceHelper.editor.remove("user_id");
                vehicleList.clear();
                vList.clear();
                GetAllImeiNo();


            }
        });



    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


    }




    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "Onstart Call ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "OnResume Call ");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "OnStop Call ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "OnPause Call ");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        String monthString = (++monthOfYear) < 10 ? "0"+(monthOfYear) : ""+(monthOfYear);
        String dayString = dayOfMonth < 10 ? "0"+dayOfMonth : ""+dayOfMonth;
        String date = ""+year+"-"+monthString+ "-" + dayString;
        date_select.setText(date);
    }

    private void GetAllImeiNo(){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(getContext(),"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.GETALLIMEINO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();
                try {
                    preferenceHelper.putUser_id(response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            HashMap<String, String> maps = new HashMap<>();
                            maps.put("KEY_IMEI_NO",  jobject.getString("deviceImei")+"/"+jobject.getString("deviceId") );
                            vehicleList.add(maps);
                            vList.add(jobject.getString("deviceImei")+" / "+jobject.getString("deviceId"));
                        }
                        adapters = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, vList);
                        spinner_vehicle.setAdapter(adapters);
                        spinner_vehicle.setTitle("Select ImeiNo/DeviceId ");

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


