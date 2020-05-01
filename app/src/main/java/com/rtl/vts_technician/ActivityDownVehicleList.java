package com.rtl.vts_technician;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rtl.vts_technician.adapter.DownVehicleListAdapter;
import com.rtl.vts_technician.model.ModelDownVehicle;
import com.rtl.vts_technician.volley.AppController;
import com.rtl.vts_technician.volley.Constants;
import com.rtl.vts_technician.volley.CustomProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityDownVehicleList extends AppCompatActivity {
     TextView pName,txtDepoName,txtTotDown;
    private DownVehicleListAdapter historyListAdapter;
    RecyclerView historyRecyclerView;
    private List<ModelDownVehicle> historyList = new ArrayList<> ();

    SearchView searchView;
    String fliet_st = "";
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_down_vehicle_list );
        historyRecyclerView = (RecyclerView )findViewById ( R.id.historyRecyclerView ) ;
        searchView = ( SearchView )findViewById(R.id.action_search);
        txtTotDown = (TextView ) findViewById ( R.id.txtTotDown );
        txtDepoName =(TextView ) findViewById ( R.id.txtDepoName )  ;
        Toolbar toolbar = (Toolbar) findViewById(R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor( Color.WHITE);
        pName           = ( TextView ) findViewById( R.id.pname);
        pName.setText("Down Vehicle");
        historyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ActivityDownVehicleList.this);
        historyRecyclerView.setLayoutManager(mLayoutManager);


        GetAllDownVehicle();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                try {
                    historyListAdapter.getFilter().filter(newText);
                }catch (Exception e){
                    e.getMessage();
                }
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions( EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                historyListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }


    private void GetAllDownVehicle(){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(ActivityDownVehicleList.this,"Please Wait...");

        StringRequest strReq = new StringRequest( Request.Method.GET,
                Constants.GETDOWNVEHICLE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        JSONObject jObj = jsonObject.getJSONObject ("data");
                        //regNo
                        JSONArray jsonArray = jObj.getJSONArray ( "downData" );
                        for(int i = 0 ; i< jsonArray.length ();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject ( i );
                            ModelDownVehicle deviceModel = new ModelDownVehicle ();
                            deviceModel.setDateTime ( jsonObject1.getString ( "dateTime" ) );
                            deviceModel.setComAddess ( jsonObject1.getString ( "location" )  );
                            deviceModel.setDepoName ( jsonObject1.getString ( "depot" ) );
                            deviceModel.setImeiNo ( jsonObject1.getString ( "imei" ) );
                            deviceModel.setVehicleNo ( jsonObject1.getString ( "vehicle" ) );
                            txtDepoName.setText ( "Depo Name :- "+jsonObject1.getString ( "depot" )  );
                            txtTotDown.setText ( "Down Vehicle :- "+jObj.getString ( "downCount" ) );
                            historyList.add ( deviceModel );
                        }
                        historyListAdapter = new DownVehicleListAdapter (ActivityDownVehicleList.this,historyList);
                        historyRecyclerView.setAdapter(historyListAdapter);
                        historyListAdapter.notifyDataSetChanged();

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

    private void prepareHistoryData() {

        ModelDownVehicle deviceModel = new ModelDownVehicle ();
        historyList.add(deviceModel);
         deviceModel = new ModelDownVehicle ();
        historyList.add(deviceModel);
         deviceModel = new ModelDownVehicle ();
        historyList.add(deviceModel);



        historyListAdapter.notifyDataSetChanged();

    }

}
