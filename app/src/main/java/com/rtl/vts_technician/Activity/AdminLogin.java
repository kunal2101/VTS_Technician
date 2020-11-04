package com.rtl.vts_technician.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class AdminLogin extends AppCompatActivity {

    Button btnLogin, btnForget, btnReg;
    EditText tv_mob, tv_pass;
    PreferenceHelper preferenceHelper;
    ArrayList<HashMap<String, String>> alldivision;
   /* String jasondata = "{\n" +
            "    \"data\": {\n" +
            "        \"2020-08-04\": {\n" +
            "            \"install\": 3,\n" +
            "            \"uninstall\": 1,\n" +
            "            \"maintenance\": 2,\n" +
            "            \"replace\": 1,\n" +
            "            \"total\": 7\n" +
            "        },\n" +
            "        \"2020-07-23\": {\n" +
            "            \"install\": 1,\n" +
            "            \"uninstall\": 1,\n" +
            "            \"maintenance\": 2,\n" +
            "            \"replace\": 0,\n" +
            "            \"total\": 4\n" +
            "        }\n" +
            "    },\n" +
            "    \"count\": 11,\n" +
            "    \"status\": \"true\",\n" +
            "    \"message\": \"success\",\n" +
            "    \"code\": 1,\n" +
            "    \"dataCount\": null\n" +
            "}";*/

    ArrayList<HashMap<String, String>> mylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        btnLogin        = (Button) findViewById(R.id.btnLogin);
        btnForget       = (Button) findViewById(R.id.btnForget);
        btnReg          = (Button) findViewById(R.id.btnReg);
        tv_mob          = (EditText) findViewById(R.id.tv_mob);
        tv_pass         = (EditText) findViewById(R.id.tv_pass);

        preferenceHelper = new PreferenceHelper(AdminLogin.this);

        try {
            if(preferenceHelper.getAdminLogin() == ""){
                Log.d ( "Login Clear" ,"Click on LOGOUT" );

                // getExitTransition ();
                tv_pass.setText ("");
                tv_mob.setText ("");
            }else {
                tv_pass.setText (preferenceHelper.getAdminPassword());
                tv_mob.setText (preferenceHelper.getAdminLogin());
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
                    Toast.makeText(AdminLogin.this, "Enter user name", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(tv_mob.getText().toString())){
                    Toast.makeText(AdminLogin.this, "Enter Password", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(AdminLogin.this, Registration_Activity.class);
                startActivity(intent);
                finish();
            }
        });

       /* try {
            JSONObject jobj = new JSONObject(jasondata);
            parseJson(jobj);

            //System.out.println("My List-->" +mylist);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    private void getLogin( String mobile, String passowrd){
        String  tag_string_req = "string_req";
        String Inpassword ="";

        CustomProgressBar.showCustomProgressDialog(AdminLogin.this,"Please Wait...");
        JSONObject requestJson = new JSONObject();
        //{"userLogin":"Admin","userPassword":"Admin@123"}
        try{
            requestJson.put("userLogin", mobile);
            requestJson.put("userPassword", passowrd);
            Inpassword= passowrd;

            System.out.println("Diwas " +requestJson);

        }catch (Exception ev){
            System.out.print(ev.getMessage());
        }

        final String finalInpassword = Inpassword;
        JsonObjectRequest req = new JsonObjectRequest(Constants.ADMINLOGIN, requestJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getString("status").equals("true")) {

                                JSONObject jsonObject = response.getJSONObject("data");
                                String name = jsonObject.getString("name");
                                String userLogin = jsonObject.getString("userLogin");
                                String token = jsonObject.getString("token");
                                String regionCode = jsonObject.getString("regionCode");
                                String regionName = jsonObject.getString("regionName");

                                preferenceHelper.putRegion_name(regionName);
                                preferenceHelper.putRegion_code(regionCode);
                                preferenceHelper.putMobileNo(jsonObject.getString("mobile"));
                                preferenceHelper.putUser_name(name);
                                preferenceHelper.putAdminLogin(userLogin);
                                preferenceHelper.putAdminPassword (finalInpassword);
                                preferenceHelper.putToken(token);

                                alldivision = new ArrayList<>();
                                alldivision.clear();

                                HashMap<String, String> maps1 = new HashMap<>();
                                maps1.put("KEY_DIV_ID",  "All");
                                maps1.put("KEY_DIV_NAME","All");
                                maps1.put("KEY_DIV_CODE","All");
                                alldivision.add(maps1);

                                JSONArray jsonArray = jsonObject.getJSONArray("divisionList");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jobject = jsonArray.getJSONObject(i);
                                    HashMap<String, String> maps = new HashMap<>();
                                    maps.put("KEY_DIV_ID",  jobject.getString("divisionId"));
                                    maps.put("KEY_DIV_NAME",jobject.optString("divisionName"));
                                    maps.put("KEY_DIV_CODE",jobject.optString("divisionCode"));
                                    alldivision.add(maps);

                                }

                                Intent intent = new Intent(AdminLogin.this, AdminDashBoard.class);
                                 intent.putExtra("KEY_ARRAY", alldivision);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(AdminLogin.this, response.getString("message"), Toast.LENGTH_LONG).show();
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
            Toast.makeText(AdminLogin.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    private void forgetPassword(String mobile){
        String  tag_string_req = "string_req";

        CustomProgressBar.showCustomProgressDialog(AdminLogin.this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.FORGET_PASSWORD + mobile + "/skjf98e987943/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {

                        Toast.makeText (AdminLogin.this , "New password has been sent to your Registered Mobile No." , Toast.LENGTH_SHORT ).show ( );

                    }else{
                        Toast.makeText(AdminLogin.this, "Sorry, Mobile no. is not register", Toast.LENGTH_LONG).show();
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
            if(preferenceHelper.getAdminLogin()==""){
                Log.d ( "Login Clear" ,"Click on LOGOUT" );

                tv_mob.setText ("");
                tv_pass.setText ("");
            }else {

                tv_mob.setText (preferenceHelper.getAdminLogin());
                tv_pass.setText (preferenceHelper.getAdminPassword());
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

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(AdminLogin.this);
        //alertDialogBuilder.setTitle("Reset Password");
        alertDialogBuilder.setTitle(Html.fromHtml("<font color='#18B086'>Reset Password</font>"));

        final View itemView = LayoutInflater.from(AdminLogin.this)
                .inflate(R.layout.activity_forgot__password, null, false);
        alertDialogBuilder.setView(itemView);
        alertDialogBuilder.setCancelable(false);

        final EditText et_mob = (EditText) itemView.findViewById(R.id.et_mob);

        alertDialogBuilder.setPositiveButton(getResources().getText(R.string.reset),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String regMob = et_mob.getText().toString().trim();

                        if (TextUtils.isEmpty(et_mob.getText().toString().trim())){
                            Toast.makeText (AdminLogin.this , "Enter your register Mobile no." , Toast.LENGTH_SHORT ).show ( );
                        }else if (regMob.length() != 10){
                            Toast.makeText (AdminLogin.this , "Enter your valid Mobile no." , Toast.LENGTH_SHORT ).show ( );
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

  /*  private void parseJson(JSONObject data) {
        mylist = new ArrayList<>();
        mylist.clear();
        if (data != null) {
            Iterator<String> it = data.keys();
            while (it.hasNext()) {
                String key = it.next();
                try {
                    if (data.get(key) instanceof JSONArray) {
                        JSONArray arry = data.getJSONArray(key);
                        int size = arry.length();
                        for (int i = 0; i < size; i++) {
                            parseJson(arry.getJSONObject(i));
                        }
                    } else if (data.get(key) instanceof JSONObject) {
                        parseJson(data.getJSONObject(key));
                    } else {
                        System.out.println("Diwash " +key + ":" + data.getString(key));
                       HashMap<String, String> map = new HashMap<>();
                       map.put(key, data.getString(key));
                       mylist.add(map);
                    }
                   // System.out.println("My List " +mylist);
                } catch (Throwable e) {
                    try {
                        System.out.println(key + ":" + data.getString(key));
                    } catch (Exception ee) {
                    }
                    e.printStackTrace();

                }
            }
        }
    }*/
}