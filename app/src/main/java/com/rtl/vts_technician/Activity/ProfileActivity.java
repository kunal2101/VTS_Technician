package com.rtl.vts_technician.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    Button btnEditProfile, btnChangePassword;
    PreferenceHelper preferenceHelper;
    TextView name_txt, email_txt, mobile_txt, address_txt,pName, depo_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bars);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                finish ( );
            }
        } );

        pName = ( TextView ) findViewById ( R.id.pname );
        pName.setText ( "Profile" );

        preferenceHelper = new PreferenceHelper(ProfileActivity.this);
        String userLogin = preferenceHelper.getUserLogin();

        btnEditProfile = ( Button ) findViewById ( R.id.btnEditProfile );
        btnChangePassword = ( Button ) findViewById ( R.id.btnChangePassword );
        name_txt = ( TextView ) findViewById ( R.id.name_txt );
        email_txt = ( TextView ) findViewById ( R.id.emp_txt );
        mobile_txt = ( TextView ) findViewById ( R.id.mobile_txt );
        address_txt = ( TextView ) findViewById ( R.id.address_txt );
        depo_txt    =   (TextView) findViewById(R.id.depo_txt);

        btnEditProfile.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent intent = new Intent ( ProfileActivity.this , ProfileEditActivity.class );
                intent.putExtra ( "name" , name_txt.getText ().toString ());
                intent.putExtra ( "mobile" , mobile_txt.getText ().toString());
                intent.putExtra ( "email" , email_txt.getText ().toString ());
                intent.putExtra ( "div" , address_txt.getText ().toString ());
                intent.putExtra ( "depo" , depo_txt.getText ().toString ());
                startActivity ( intent );
                //finish ( );
            }
        } );

        if (isConnection()){
            GetMyProfile(userLogin);
        }

    }

    private void GetMyProfile(String userLogin){
        String  tag_string_req = "string_req";

        CustomProgressBar.showCustomProgressDialog(ProfileActivity.this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.My_PROFILE + userLogin + "/kjhoiu978/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {

                        JSONObject jObject = jsonObject.getJSONObject("data");

                        name_txt.setText(jObject.getString("name"));
                        mobile_txt.setText(jObject.getString("mobile"));
                        email_txt.setText(jObject.getString("empId"));
                        String subString_div = jObject.getString("divisionName");
                        address_txt.setText(subString_div.substring(0, subString_div.length()-1));
                        String subString_depo = jObject.getString("depotName");
                        depo_txt.setText(subString_depo.substring(0, subString_depo.length()-1));

                    }else{
                        Toast.makeText(ProfileActivity.this, "No records found", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
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

    protected boolean isConnection() {
        ConnectivityManager manage = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            Toast.makeText(ProfileActivity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }



}
