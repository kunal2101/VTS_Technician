package com.rtl.vts_technician;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rtl.vts_technician.volley.AppController;
import com.rtl.vts_technician.volley.Constants;
import com.rtl.vts_technician.volley.CustomProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

public class ActivityVehicleDetail extends AppCompatActivity implements OnMapReadyCallback {
    TextView pName,txtViewVehicleNO,txtImeiNo,txtDivision,txtMainBattery,txtDepo,txtSpeed,txtIgn,txtStatus,txtDateTime,txtLstLoc,txtGoToVehLoc;
    GoogleMap googleMap;
    String vehicleNo , laty , longy;



    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_vehicle_detail );

        txtViewVehicleNO = (TextView) findViewById ( R.id.txtViewVehicleNO );
        txtImeiNo       =  (TextView) findViewById ( R.id.txtImeiNo );
        txtMainBattery       =  (TextView) findViewById ( R.id.txtMainBattery );
        txtDivision       =  (TextView) findViewById ( R.id.txtDivision );
        txtDepo       =  (TextView) findViewById ( R.id.txtDepo );
        txtSpeed       =  (TextView) findViewById ( R.id.txtSpeed );
        txtIgn       =  (TextView) findViewById ( R.id.txtIgn );
        txtStatus       =  (TextView) findViewById ( R.id.txtStatus );
        txtDateTime       =  (TextView) findViewById ( R.id.txtDateTime );
        txtLstLoc       =  (TextView) findViewById ( R.id.txtLstLoc );
        txtGoToVehLoc  = (TextView)   findViewById ( R.id.txtGoToVehLoc );


        try {
            Intent inty = getIntent();
            vehicleNo = inty.getStringExtra("vehicelNo");

            GetAllImeiNo();
        }catch (Exception e){
            e.getMessage ();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor( Color.WHITE);
        pName           = (TextView) findViewById( R.id.pname);
        pName.setText("Vehicle Detail");
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Call your Alert message
            //progressDialog = ProgressDialog.show(AllVehiclemapActivity.this, "", "Please Wait...", true);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        (( SupportMapFragment ) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        //http://103.197.121.83:8010/api/tech/vehicle/lkdsjf98943wur4/MH14BT4895/
        txtGoToVehLoc.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                String strUri = "http://maps.google.com/maps?q=loc:" + laty + "," + longy + " (" + "Vehicle Loction" + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                startActivity(intent);
            }
        } );


    }

    @Override
    public void onMapReady ( GoogleMap mMap ) {
        googleMap =mMap;

        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setTrafficEnabled(true);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
        // permissions
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);


    }
    private void GetAllImeiNo(){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(ActivityVehicleDetail.this,"Please Wait...");

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
                        //regNo
                        txtImeiNo.setText ( jObj.getString ( "imei" ) );
                        txtViewVehicleNO.setText (  jObj.getString ( "regNo" ));
                        txtDivision.setText (  jObj.getString ( "division" ));
                        txtDateTime.setText (  jObj.getString ( "eventDate" ));
                        txtDepo.setText (  jObj.getString ( "depot" ));
                        if(jObj.getString ( "ignition" ).equalsIgnoreCase ( "0" )){
                            txtIgn.setText ( "0 (OFF)");
                        }else {
                               txtIgn.setText ( "1 (ON)" );
                        }

                        txtLstLoc.setText (  jObj.getString ( "location" ));

                        if(jObj.getString ( "main_battery_status" ).equalsIgnoreCase ( "1" )){
                            txtMainBattery.setText ( "1 (Working)");
                        }else {
                            txtMainBattery.setText ( "0 (Not Working)" );
                        }
                        txtSpeed.setText (  jObj.getString ( "speed" ));
                        txtStatus.setText (  jObj.getString ( "status" ));
                        laty =   jObj.getString ( "lat" );
                        longy =  jObj.getString ( "lon" );

                        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble ( jObj.getString ( "lat" ) ),  Double.parseDouble (jObj.getString ( "lon" ))))
                                .title("Marker in Sydney"));
                        LatLng coordinate = new LatLng(Double.parseDouble ( jObj.getString ( "lat" ) ),  Double.parseDouble (jObj.getString ( "lon" ))); //Store these lat lng values somewhere. These should be constant.
                        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                                coordinate, 15);
                        googleMap.animateCamera(location);

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

}
