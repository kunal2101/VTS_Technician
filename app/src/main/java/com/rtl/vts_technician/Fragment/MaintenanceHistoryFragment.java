package com.rtl.vts_technician.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.rtl.vts_technician.Database.DatabaseHelper;
import com.rtl.vts_technician.PreferenceHelper;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Adapter.MainteListAdapter;
import com.rtl.vts_technician.Pojo.MaintainanceDeviceModel;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MaintenanceHistoryFragment extends Fragment {

    ImageView pimage;
    TextView pName;
    RecyclerView historyRecyclerView;
    private MainteListAdapter historyListAdapter;
    private List<MaintainanceDeviceModel> historyList = new ArrayList<>();
    DatabaseHelper dbHelper;
    PreferenceHelper preferenceHelper;
    String UserLogin = "";
    private boolean _hasLoadedOnce= false; // your boolean field

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_activity_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        historyRecyclerView     = (RecyclerView)view. findViewById(R.id.historyRecyclerView);
        //dbHelper = new DatabaseHelper(getActivity());

        preferenceHelper = new PreferenceHelper(getContext());
        UserLogin = preferenceHelper.getUserLogin();

        // historyList = dbHelper.getMaintaince_Arry_list();

        historyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        historyRecyclerView.setLayoutManager(mLayoutManager);

        if (isConnection()){
            GetMaintainceHistory(UserLogin);
        }

    }
    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);
        if (this.isVisible()) {
// we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                if (isConnection()){
                    GetMaintainceHistory(UserLogin);
                }


                _hasLoadedOnce = true;
            }
        }
    }



    private void GetMaintainceHistory(String userLogin){
        String  tag_string_req = "string_req";

       //CustomProgressBar.showCustomProgressDialog(getContext(),"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.GETMAINTAUNCEVEHICLE_HISTORY + userLogin + "/fkdsjflksj/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                //CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        MaintainanceDeviceModel dataModel = null;
                        historyList= new ArrayList<>();
                        historyList.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            dataModel = new MaintainanceDeviceModel ();

                            JSONObject jobject = jsonArray.getJSONObject(i);
                            //dataModel.setId(ids);

                            dataModel.setVeh_no(jobject.getString("vehicle_reg_no"));
                            dataModel.setDepo(jobject.getString("depotName"));
                            dataModel.setDivision(jobject.getString("divisionName"));
                            dataModel.setImieno(jobject.getString("deviceImei"));
                            dataModel.setRemarks("");
                            dataModel.setInstal_time(jobject.getString("maintenanceTime"));
                            dataModel.setInstal_date(jobject.getString("maintenanceDate"));
                            dataModel.setLat(jobject.getString("latitude"));
                            dataModel.setLng(jobject.getString("longitude"));
                            dataModel.setAddress(jobject.getString("address"));
                            dataModel.setImageString(jobject.getString("imageString"));
                            dataModel.setActon_taken(jobject.getString("actionTaken"));
                            dataModel.setProblem_type(jobject.getString("problemType"));
                            dataModel.setStatus(jobject.getString("status"));
                            dataModel.setLast_location(jobject.getString("lastLocation"));

                            historyList.add(dataModel);
                        }
                        historyListAdapter = new MainteListAdapter(getContext(),historyList);
                        historyRecyclerView.setAdapter(historyListAdapter);
                    }else{
                        Toast.makeText(getContext(), "No records found", Toast.LENGTH_LONG).show();
                       // CustomProgressBar.removeCustomProgressDialog();
                    }
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                    //CustomProgressBar.removeCustomProgressDialog();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());
               // CustomProgressBar.removeCustomProgressDialog();
            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
           // CustomProgressBar.removeCustomProgressDialog ();

        }catch (Exception e){
            e.getMessage ();
        }
    }

    @Override
    public void onDetach () {
        super.onDetach ( );
        try{
           // CustomProgressBar.removeCustomProgressDialog ();

        }catch (Exception e){
            e.getMessage ();
        }
    }
    protected boolean isConnection() {
        ConnectivityManager manage = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
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
}
