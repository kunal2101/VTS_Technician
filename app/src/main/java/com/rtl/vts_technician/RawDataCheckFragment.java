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
import com.rtl.vts_technician.Activity.AutoSearchViewImeiNoActivity;
import com.rtl.vts_technician.Activity.RawDataResultActivity;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;
import com.rtl.vts_technician.Volley.CustomProgressBar;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class RawDataCheckFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    ArrayList<HashMap<String,String>> vehicleList;
    ArrayList<String> vList;
    SearchableSpinner spinner_vehicle;
    LinearLayout lin_sea_;
    private ArrayAdapter<String> adapters;
    TextView date_select,search_box,txtAddImeiNO,txtImeiNo;
    String  s_name="";
    String current_date;
    PreferenceHelper preferenceHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d ( "New_fragment_RawData", "onCreateView" );
        return inflater.inflate(R.layout.new_frag_rawdata, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d ( "New_fragment_RawData", "onViewCreated" );
        lin_sea_=(LinearLayout) view. findViewById(R.id.lin_sea_);
        date_select=(TextView)view. findViewById(R.id.date_select) ;
        search_box=(TextView)view. findViewById(R.id.search_box) ;
        txtAddImeiNO=(TextView)view. findViewById(R.id.txtAddImeiNO);
        txtImeiNo    =   (TextView)view. findViewById(R.id.txtVehicleNo);
        vehicleList = new ArrayList<>();
        vList = new ArrayList<>();
        preferenceHelper = new PreferenceHelper(getActivity());

        //  Toast.makeText(this, "------"+ preferenceHelper.getUser_id(), Toast.LENGTH_SHORT).show();

       /* ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        Calendar now = Calendar.getInstance();
        int monthOfYear = now.get(Calendar.MONTH);
        int dayOfMonth = now.get(Calendar.DAY_OF_MONTH);
        String monthString = (++monthOfYear) < 10 ? "0"+(monthOfYear) : ""+(monthOfYear);
        String dayString = dayOfMonth < 10 ? "0"+dayOfMonth : ""+dayOfMonth;
        current_date = dayString + "-" + monthString + "-" + now.get(Calendar.YEAR);
        date_select.setText("Date :- " +current_date);

        search_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s_name.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select IMEINO .....", Toast.LENGTH_LONG).show();
                } else {
                    // Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
                    Intent inty = new Intent(getActivity(), RawDataResultActivity.class);
                    inty.putExtra("imeino", s_name);
                    inty.putExtra("date", current_date);
                    getActivity().startActivity(inty);
                }
            }
        });

        txtImeiNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity (), AutoSearchViewImeiNoActivity.class);
                startActivityForResult(intent, 9);
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed
        if (resultCode == 0){
            Toast.makeText(getActivity (), "User Cancel request", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 9) {
            String message_st=data.getStringExtra("VEH_REG_NO");
            s_name = message_st;
            txtImeiNo.setText(message_st.trim());
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "Onstart Call ");
    }
/*
    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);
        if (this.isVisible()) {
// we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
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
                _hasLoadedOnce = true;
            }
        }
    }
*/

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

                        Toast.makeText(getContext(), "IMIE data sync sucessfully ", Toast.LENGTH_SHORT).show();

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


