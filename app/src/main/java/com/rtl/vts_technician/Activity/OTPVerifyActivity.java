package com.rtl.vts_technician.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.rtl.vts_technician.PreferenceHelper;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;
import com.rtl.vts_technician.Volley.CustomProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

public class OTPVerifyActivity extends AppCompatActivity {
    EditText otpView;
    Button txt_verify, resendotp;
    TextView timeText;
    String userName="",  mobile="", password="", empId="", div_code="", dep_code="";
    PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_otp_verify);

        otpView = (EditText) findViewById( R.id.otpView);
        txt_verify = (Button) findViewById( R.id.txt_verify);
        resendotp = (Button) findViewById( R.id.resendotp);
        timeText = (TextView) findViewById( R.id.timeText);

        try {
            mobile = getIntent().getStringExtra("MOBILE");
//            userName = getIntent().getStringExtra("NAME");
//            password = getIntent().getStringExtra("PASSWORD");
//            empId = getIntent().getStringExtra("EMPID");
//            div_code = getIntent().getStringExtra("DIV_CODE");
//            dep_code = getIntent().getStringExtra("DEPT_CODE");
        }catch (Exception ev){
            System.out.println(ev.getMessage());
        }

        preferenceHelper = new PreferenceHelper(this);

        resendotp.setBackgroundResource( R.drawable.bg_disabled_button);
        resendotp.setEnabled(false);
        txt_verify.setEnabled(false);

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeText.setText("Time remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                timeText.setText("Click on Resend button to re-generate the OTP !");
                resendotp.setBackgroundResource( R.drawable.rect_blue_cornor);

                resendotp.setEnabled(true);
            }

        }.start();


    final TextWatcher mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() < 4) {
                txt_verify.setBackgroundResource( R.drawable.bg_disabled_button);
                txt_verify.setEnabled(false);
            } else if (s.length() == 4) {
                txt_verify.setBackgroundResource( R.drawable.rect_blue_cornor);
                txt_verify.setEnabled(true);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

        otpView.addTextChangedListener(mTextEditorWatcher);

        txt_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp_code = otpView.getText().toString().trim();
                if (isConnection()){
                    GetVerifyOtp(mobile, otp_code);
                }
            }
        });

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void GetVerifyOtp(String mobile, String otp_code){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(this,"Please Wait...");
        // http://103.197.121.83:8010/api/tech/otpverification/ksjdf843r3/?mobile=980098709&otp=1234
        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.GETOTP + mobile + "&otp=" + otp_code , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("true")){
                        CustomProgressBar.removeCustomProgressDialog();
                        Toast.makeText(OTPVerifyActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                       // getRegister();
                        Intent i = new Intent(OTPVerifyActivity.this, DashBoard_Activity_Tab.class);
                        startActivity(i);
                        finish();

                    }else{
                        CustomProgressBar.removeCustomProgressDialog();
                        Toast.makeText(OTPVerifyActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    CustomProgressBar.removeCustomProgressDialog();
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

    private void getRegister(){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(OTPVerifyActivity.this,"Please Wait...");
        JSONObject requestJson = new JSONObject();
        // {"mobile":"980098702","divisionCode":"nasik","depotCodes":"nasik1","userPassword":"56707","name":"deepak","userLogin":"980098702"}
        try{
            requestJson.put("mobile", mobile);
            requestJson.put("divisionCode", div_code);
            requestJson.put("depotCodes", dep_code);
            requestJson.put("userPassword", password);
            requestJson.put("name", userName);
            requestJson.put("userLogin", mobile);
            requestJson.put("empId", empId);

            System.out.println("Diwas " +requestJson);

        }catch (Exception ev){
            System.out.print(ev.getMessage());
        }

        JsonObjectRequest req = new JsonObjectRequest(Constants.GETREGISTRATION, requestJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Toast.makeText(LoginActivity.this, "Your query submitted sucessfully", Toast.LENGTH_LONG).show();
                            if (response.getString("status").equals("true")) {

                                JSONObject jsonObject = response.getJSONObject("data");

                                //preferenceHelper.putMobileNo(jsonObject.getString("mobile"));
                                //preferenceHelper.putUser_name(jsonObject.getString("name"));

                                Intent i = new Intent(OTPVerifyActivity.this, DashBoard_Activity_Tab.class);
                                startActivity(i);
                                finish();

                            }else{
                                Toast.makeText(OTPVerifyActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
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



    protected boolean isConnection() {
        ConnectivityManager manage = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            Toast.makeText(OTPVerifyActivity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
