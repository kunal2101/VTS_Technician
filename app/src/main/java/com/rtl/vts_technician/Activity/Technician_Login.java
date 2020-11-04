
package com.rtl.vts_technician.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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

import static android.content.ContentValues.TAG;

public class Technician_Login extends AppCompatActivity {
    Button btnLogin, btnForget, btnReg;
    EditText tv_mob, tv_pass;
    PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kk_activity_login);

        btnLogin        = (Button) findViewById(R.id.btnLogin);
        btnForget       = (Button) findViewById(R.id.btnForget);
        btnReg          = (Button) findViewById(R.id.btnReg);
        tv_mob          = (EditText) findViewById(R.id.tv_mob);
        tv_pass         = (EditText) findViewById(R.id.tv_pass);

        preferenceHelper = new PreferenceHelper(Technician_Login.this);

    /*    if(!preferenceHelper.getMobile ().equalsIgnoreCase ( "" )) {
            if (preferenceHelper.getMobile ( ).equalsIgnoreCase ( "0000000001" )) {
                btnForget.setVisibility ( View.GONE );
            }
        }*/

        try {
            if(preferenceHelper.getMobile () == ""){
                Log.d ( "Login Clear" ,"Click on LOGOUT" );
                // getExitTransition ();
                tv_pass.setText ( "");
                tv_mob.setText ( "" );
            }else {
                tv_pass.setText (preferenceHelper.getPassword());
                tv_mob.setText (preferenceHelper.getMobile());
            }
        }catch (Exception e){
            e.getMessage ();
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNo = tv_mob.getText().toString().trim();
                String password = tv_pass.getText().toString().trim();

                if (TextUtils.isEmpty(tv_mob.getText().toString())){
                    Toast.makeText(Technician_Login.this, "Enter mobile no.", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(tv_mob.getText().toString())){
                    Toast.makeText(Technician_Login.this, "Enter Password", Toast.LENGTH_LONG).show();
                }else{
                    if (isConnection()){
                        getLogin(mobileNo, password);
                    }
                }
            }
        });


        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forget_password();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Technician_Login.this, Registration_Activity.class);
                startActivity(intent);
                finish();
                // getActivity().finish();
            }
        });
    }

    private void getLogin( String mobile, String passowrd){
        String  tag_string_req = "string_req";
        String Inpassword ="";
        CustomProgressBar.showCustomProgressDialog(Technician_Login.this,"Please Wait...");
        JSONObject requestJson = new JSONObject();

        try{
            requestJson.put("userLogin", mobile);
            requestJson.put("userPassword", passowrd);
            Inpassword= passowrd;

            System.out.println("Diwas " +requestJson);

        }catch (Exception ev){
            System.out.print(ev.getMessage());
        }

        final String finalInpassword = Inpassword;
        JsonObjectRequest req = new JsonObjectRequest(Constants.GETLOGIN, requestJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                           // Toast.makeText(getContext (), "Your query submitted sucessfully"+response, Toast.LENGTH_LONG).show();

                            if (response.getString("status").equals("true")) {

                                JSONObject jsonObject = response.getJSONObject("data");
                                String name = jsonObject.getString("name");
                                String userLogin = jsonObject.getString("userLogin");
//                                String userEmail = jsonObject.getString("userEmail");
//                                String empId = jsonObject.getString("empId");
//                                String divisionCode = jsonObject.getString("divisionCode");
//                                String depoCode = jsonObject.getString("depotCodes");
//                                String userId = jsonObject.getString("userId");

                                preferenceHelper.putMobileNo(jsonObject.getString("mobile"));
                                preferenceHelper.putUser_name(name);
                                preferenceHelper.putUserLogin(userLogin);
                                preferenceHelper.putPassword ( finalInpassword );

                                Intent intent = new Intent(Technician_Login.this, TechnicianDashboard.class);
                               // intent.putExtra("KEY_NAME", name);
                                startActivity(intent);
                               finish();
                            }else{
                                Toast.makeText(Technician_Login.this, response.getString("message"), Toast.LENGTH_LONG).show();
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
            Toast.makeText(Technician_Login.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    private void forgetPassword(String mobile){
        String  tag_string_req = "string_req";

        CustomProgressBar.showCustomProgressDialog(Technician_Login.this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.FORGET_PASSWORD + mobile + "/skjf98e987943/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {

                        Toast.makeText (Technician_Login.this , "New password has been sent to your Registered Mobile No." , Toast.LENGTH_SHORT ).show ( );

                    }else{
                        Toast.makeText(Technician_Login.this, "Sorry, Mobile no. is not register", Toast.LENGTH_LONG).show();
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

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "Onstart Call ");
        try {
            if(preferenceHelper.getMobile ()==""){
                Log.d ( "Login Clear" ,"Click on LOGOUT" );
                // getExitTransition ();
                tv_mob.setText ("");
                tv_pass.setText ("");
            }else {
                tv_mob.setText (preferenceHelper.getUserLogin());
                tv_pass.setText (preferenceHelper.getPassword());
            }
        }catch (Exception e){
            e.getMessage ();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "OnResume Call login ");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "OnStop Call ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "OnPause Call");
    }

    private void forget_password() {

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(Technician_Login.this);
        //alertDialogBuilder.setTitle("Reset Password");
        alertDialogBuilder.setTitle(Html.fromHtml("<font color='#18B086'>Reset Password</font>"));

        final View itemView = LayoutInflater.from(Technician_Login.this)
                .inflate(R.layout.activity_forgot__password, null, false);
        alertDialogBuilder.setView(itemView);
        alertDialogBuilder.setCancelable(false);

        final EditText et_mob = (EditText) itemView.findViewById(R.id.et_mob);

        alertDialogBuilder.setPositiveButton(getResources().getText(R.string.reset),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                         String regMob = et_mob.getText().toString().trim();

                        if (TextUtils.isEmpty(et_mob.getText().toString().trim())){
                            Toast.makeText (Technician_Login.this , "Enter your register Mobile no." , Toast.LENGTH_SHORT ).show ( );
                        }else if (regMob.length() != 10){
                            Toast.makeText (Technician_Login.this , "Enter your valid Mobile no." , Toast.LENGTH_SHORT ).show ( );
                        }else{
                            if (isConnection()){
                                forgetPassword(regMob);
                            }
                        }
                       // Toast.makeText ( getActivity () , "Password has been send to your Registered Mobile No " , Toast.LENGTH_SHORT ).show ( );
                    }
                });

        alertDialogBuilder.setNegativeButton(getResources().getText(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        final android.app.AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

}



