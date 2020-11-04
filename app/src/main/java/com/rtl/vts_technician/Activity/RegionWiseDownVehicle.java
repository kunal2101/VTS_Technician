package com.rtl.vts_technician.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.rtl.vts_technician.PreferenceHelper;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Adapter.DownVehicleListAdapter;
import com.rtl.vts_technician.Pojo.ModelDownVehicle;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;
import com.rtl.vts_technician.Volley.CustomProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegionWiseDownVehicle extends AppCompatActivity {
    TextView pName,txtDepoName,txtTotDown;
    private DownVehicleListAdapter historyListAdapter;
    RecyclerView downRecyclerView;
    private List<ModelDownVehicle> historyList = new ArrayList<>();
    PreferenceHelper pHelper;

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_wise_down_vehicle);

        downRecyclerView     = (RecyclerView) findViewById (R.id.downRecyclerView ) ;
        searchView              = (SearchView) findViewById(R.id.action_search);
        txtTotDown              = (TextView) findViewById (R.id.txtTotDown );
        txtDepoName             = (TextView) findViewById (R.id.txtDepoName );

        Toolbar toolbar = (Toolbar) findViewById(R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor( Color.WHITE);

        pName           = (TextView) findViewById(R.id.pname);
        pName.setText("Down Vehicles");

        downRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(RegionWiseDownVehicle.this);
        downRecyclerView.setLayoutManager(mLayoutManager);

        pHelper = new PreferenceHelper(RegionWiseDownVehicle.this);

        String getToken  = pHelper.getToken();

        if (isConnection()){
            GetAllDownVehicle(getToken);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // txtTotDown.setText ("Total Down Vehicles :- "+historyListAdapter.getItemCount());
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                try {
                    historyListAdapter.getFilter().filter(query);
                   // txtTotDown.setText ("Total Down Vehicles :- "+historyListAdapter.getItemCount());
                }catch (Exception e){
                    e.getMessage();
                }

                return false;
            }
        });

      /*  searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                txtTotDown.setText ("Total Down Vehicles :- "+historyListAdapter.getItemCount());
                return false;
            }
        });

       searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTotDown.setText ("Total Down Vehicles :- "+historyListAdapter.getItemCount());
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
      /*  MenuInflater inflater = getMenuInflater();
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
        });*/
        return true;
    }

    private void GetAllDownVehicle(String token){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(RegionWiseDownVehicle.this,"Please Wait...");

        StringRequest strReq = new StringRequest( Request.Method.GET,
                Constants.GET_REGION_DOWNVEHICLE + token + "/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        JSONObject jObj = jsonObject.getJSONObject ("data");
                        //regNo
                        JSONArray jsonArray = jObj.getJSONArray ( "downData" );
                        for(int i = 0 ; i< jsonArray.length ();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject ( i );

                            ModelDownVehicle deviceModel = new ModelDownVehicle ();
                            //yyyy-MM-dd hh:mm:ss
                            SimpleDateFormat HHmmFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

                            SimpleDateFormat hhmmampmFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);

                            String arrTime  = parseDate(jsonObject1.getString( "dateTime" ), HHmmFormat, hhmmampmFormat);

                            deviceModel.setDateTime(arrTime);
                            deviceModel.setComAddess(jsonObject1.getString ("location" ));
                            deviceModel.setDepoName(jsonObject1.getString ("depot" ));
                            deviceModel.setImeiNo(jsonObject1.getString ("imei" ));
                            deviceModel.setVehicleNo(jsonObject1.getString ("vehicle" ));
                            deviceModel.setStatus(jsonObject1.getString ("status" ));
                            //txtDepoName.setText ("Depo Name :- "+jsonObject1.getString ("depot" ));
                           // txtTotDown.setText ("Total Down Vehicle :- "+jObj.getString ("downCount" ));
                            historyList.add (deviceModel);
                        }
                        historyListAdapter = new DownVehicleListAdapter (RegionWiseDownVehicle.this,historyList);
                        downRecyclerView.setAdapter(historyListAdapter);
                        historyListAdapter.notifyDataSetChanged();
                        CustomProgressBar.removeCustomProgressDialog();
                        txtTotDown.setText ("Total Down Vehicles :- "+historyListAdapter.getItemCount());
                    }else{
                        Toast.makeText(RegionWiseDownVehicle.this,"No result found", Toast.LENGTH_LONG).show();
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
        ConnectivityManager manage = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            Toast.makeText(RegionWiseDownVehicle.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static String parseDate(String inputDateString, SimpleDateFormat inputDateFormat, SimpleDateFormat outputDateFormat) {
        Date date = null;
        String outputDateString = null;
        try {
            date = inputDateFormat.parse(inputDateString);
            outputDateString = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }
}