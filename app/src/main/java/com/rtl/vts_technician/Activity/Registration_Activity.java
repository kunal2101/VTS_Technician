package com.rtl.vts_technician.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;
import com.rtl.vts_technician.Volley.CustomProgressBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class Registration_Activity extends AppCompatActivity {

    private Button btnRegister, btngoback;
    private EditText txtUser, txtMobile, txtPassword, txtEid, txtPasswordCon;
    TextView txtDepo,txtDivision;
    ArrayList<HashMap<String, String>> alldivision;
    ArrayList<HashMap<String, String>> alldepo;
    String div_id = "";
    String divi_code = "", depo_code="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_registration);

        btngoback       = (Button) findViewById( R.id.btngoback);
        btnRegister     = (Button) findViewById( R.id.btnRegister);
        txtUser         = (EditText) findViewById( R.id.txtUser);
        txtMobile       = (EditText) findViewById( R.id.txtMobile);
        txtPassword     = (EditText) findViewById( R.id.txtPassword);
        txtEid          = (EditText) findViewById( R.id.txtEid);
        txtDepo         = (TextView) findViewById( R.id.txtDepo);
        txtDivision     = (TextView) findViewById( R.id.txtDivision);
        txtPasswordCon  = (EditText) findViewById(R.id.txtPasswordCon);

       /* if (isConnection()){
            GetAllDivision();
        }*/

        txtDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnection()){
                    GetAllDivision();
                }
            }
        });

        txtDepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (div_id.length() == 0){
                    Toast.makeText(Registration_Activity.this, "Select your division first", Toast.LENGTH_SHORT).show();
                }else {
                    if (isConnection()) {
                        GetSelectedDepo(div_id);
                    }
                }
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = txtUser.getText().toString().trim();
                String mobile = txtMobile.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String confPass = txtPasswordCon.getText().toString();
                String emp_id = txtEid.getText().toString().trim();

                if (TextUtils.isEmpty(txtUser.getText().toString())){
                    Toast.makeText(Registration_Activity.this, "Enter user Name", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(txtMobile.getText().toString())){
                    Toast.makeText(Registration_Activity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (mobile.length() < 10){
                    Toast.makeText(Registration_Activity.this, "Mobile Number must be 10 digit", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(txtPassword.getText().toString())){
                    Toast.makeText(Registration_Activity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }else if (password.length() < 5){
                    Toast.makeText(Registration_Activity.this, "Password must be 5 character", Toast.LENGTH_SHORT).show();
                }else if (!password.equals(confPass)){
                    Toast.makeText(Registration_Activity.this, "password not matched with confirm passsword", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(txtEid.getText().toString())){
                    Toast.makeText(Registration_Activity.this, "Enter Employee Id", Toast.LENGTH_SHORT).show();
                }else if(divi_code.length() == 0){
                    Toast.makeText(Registration_Activity.this, "Select Division", Toast.LENGTH_SHORT).show();
                }else if(depo_code.length() == 0){
                    Toast.makeText(Registration_Activity.this, "Enter Depo", Toast.LENGTH_SHORT).show();
                }else{

                    if (isConnection()){
                         getRegister(userName, mobile, password, emp_id, divi_code, depo_code);

                       /* Intent in = new Intent( Registration_Activity.this, OTPVerifyActivity.class);
                        in.putExtra("MOBILE", mobile);
                        in.putExtra("DIV_CODE", divi_code);
                        in.putExtra("DEPT_CODE", depo_code);
                        in.putExtra("PASSWORD", password);
                        in.putExtra("NAME", userName);
                        in.putExtra("EMPID", emp_id);
                        startActivity(in);
                        finish();*/
                    }
                }
            }
        });

        btngoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent( Registration_Activity.this, Technician_Login.class);
                startActivity(in);
                finish();
            }
        });
}

    private void dialogOpenForDivisionList() {

        final ArrayList<String> division_str = new ArrayList<String>();
        final ArrayList<String> division_id = new ArrayList<String>();
        final ArrayList<String> division_code = new ArrayList<String>();

        for(int ind = 0 ; ind< alldivision.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = alldivision.get(ind);
            division_id.add(map.get("KEY_DIV_ID"));
            division_str.add(map.get("KEY_DIV_NAME"));
            division_code.add(map.get("KEY_DIV_CODE"));
        }


        final CharSequence[] dialogList = division_str.toArray(new CharSequence[division_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( Registration_Activity.this);
        builderDialog.setTitle("Select Division Name");

        int count = dialogList.length;
        boolean[] is_checked = new boolean[count];

        // Creating multiple selection by using setMutliChoiceItem method
        builderDialog.setMultiChoiceItems(dialogList, is_checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {

            }
        });
        /*builderDialog.setSingleChoiceItems(dialogList, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });*/

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        StringBuilder stringBuilder_name = new StringBuilder();
                        StringBuilder stringBuilder_code = new StringBuilder();

                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);
// All commited
                            if (checked) {

                                String getDiv_str = list.getItemAtPosition(i).toString();
                                int div_index = division_str.indexOf(getDiv_str);
                                String getDiv_id = division_id.get(div_index);
                                String getDiv_Code = division_code.get(div_index);

                                if (stringBuilder_name.length() > 0)
                                    stringBuilder_name.append(",");
                                    stringBuilder_name.append(getDiv_str);

                                    if (stringBuilder.length() > 0)
                                        stringBuilder.append(",");
                                        stringBuilder.append(getDiv_id);

                                if (stringBuilder_code.length() > 0)
                                    stringBuilder_code.append(",");
                                stringBuilder_code.append(getDiv_Code);

                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */

                        if (stringBuilder_name.toString().trim().equals("")) {

                            txtDivision.setText("");
                            stringBuilder_name.setLength(0);
                        } else {
                            txtDivision.setText(stringBuilder_name );
                            div_id = stringBuilder.toString();
                            divi_code = stringBuilder_code.toString();
                            //txtDepo.setText(getDivision_id[0]+"");
                            Toast.makeText( Registration_Activity.this, divi_code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        txtDivision.setText("Select Division");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

    }

    private void dialogOpenForDepoList() {

        final ArrayList<String> depo_str = new ArrayList<String>();;
        final ArrayList<String> depo_id = new ArrayList<String>();;
        final ArrayList<String> depo_codes = new ArrayList<String>();

        for(int ind = 0 ; ind< alldepo.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = alldepo.get(ind);
            depo_id.add(map.get("KEY_DEPO_ID"));
            depo_str.add(map.get("KEY_DEPO_NAME"));
            depo_codes.add(map.get("KEY_DEPO_CODE"));
        }

        final CharSequence[] dialogList = depo_str.toArray(new CharSequence[depo_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( Registration_Activity.this);
        builderDialog.setTitle("Select Depo Name");

        // Creating multiple selection by using setMutliChoiceItem method

        int count = dialogList.length;
        boolean[] is_checked = new boolean[count];

        for(int i=0 ; i<count ; i++){
            is_checked[i] = true;
        }

        // Creating multiple selection by using setMutliChoiceItem method
        builderDialog.setMultiChoiceItems(dialogList, is_checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {

            }
        });
       /* builderDialog.setSingleChoiceItems(dialogList, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });*/

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText( Registration_Activity.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        StringBuilder stringBuilder_name = new StringBuilder();
                        StringBuilder stringBuilder_code = new StringBuilder();

                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {
                                String getDepo_str = list.getItemAtPosition(i).toString();
                                int depo_index = depo_str.indexOf(getDepo_str);
                                String getDepo_id = depo_id.get(depo_index);
                                String getDepo_Code = depo_codes.get(depo_index);

                                if (stringBuilder_name.length() > 0)
                                    stringBuilder_name.append(",");
                                    stringBuilder_name.append(getDepo_str);

                                if (stringBuilder.length() > 0)
                                    stringBuilder.append(",");
                                    stringBuilder.append(getDepo_id);

                                if (stringBuilder_code.length() > 0)
                                    stringBuilder_code.append(",");
                                    stringBuilder_code.append(getDepo_Code);
                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder_name.toString().trim().equals("")) {

                            txtDepo.setText("");
                            stringBuilder_name.setLength(0);
                        } else {
                            txtDepo.setText(stringBuilder_name );
                            depo_code = stringBuilder_code.toString();

                            Toast.makeText( Registration_Activity.this, depo_code, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            txtDepo.setText("Select Depo");

                        }
                    });
                    AlertDialog alert = builderDialog.create();
        alert.show();

                }


        private void GetAllDivision(){
            String  tag_string_req = "string_req";
            CustomProgressBar.showCustomProgressDialog(this,"Please Wait...");

            StringRequest strReq = new StringRequest(Request.Method.GET,
                    Constants.GETDIVISION, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("ONRESPONCE", response.toString());
                    CustomProgressBar.removeCustomProgressDialog();
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        alldivision = new ArrayList<>();
                        alldivision.clear();
                        if (jsonObject.getInt("code") == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobject = jsonArray.getJSONObject(i);
                                HashMap<String, String> maps = new HashMap<>();
                                maps.put("KEY_DIV_ID",  jobject.getString("divisionId"));
                                maps.put("KEY_DIV_NAME",jobject.optString("divisionName"));
                                maps.put("KEY_DIV_CODE",jobject.optString("divisionCode"));
                                alldivision.add(maps);

                            }
                            dialogOpenForDivisionList();

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

        private void GetSelectedDepo(String div_id){
            String  tag_string_req = "string_req";
            CustomProgressBar.showCustomProgressDialog(this,"Please Wait...");

            StringRequest strReq = new StringRequest(Request.Method.GET,
                    Constants.GETDEPO + div_id + "/lksdjf984ew/", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    CustomProgressBar.removeCustomProgressDialog();
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        alldepo = new ArrayList<>();
                        alldepo.clear();
                        if (jsonObject.getInt("code") == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobject = jsonArray.getJSONObject(i);
                                HashMap<String, String> maps = new HashMap<>();
                                maps.put("KEY_DEPO_ID",  jobject.getString("depot_id"));
                                maps.put("KEY_DEPO_NAME",jobject.optString("depot_name"));
                                maps.put("KEY_DEPO_CODE",jobject.optString("depotCode"));
                                alldepo.add(maps);

                            }
                            dialogOpenForDepoList();

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

        private void getRegister(String userName, String mobile, String password, String empId, String div_code, String dep_code){
            String  tag_string_req = "string_req";
            CustomProgressBar.showCustomProgressDialog(Registration_Activity.this,"Please Wait...");
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
                                    String mobile = jsonObject.getString("mobile");

                                    Intent in = new Intent( Registration_Activity.this, OTPVerifyActivity.class);
                                    in.putExtra("MOBILE", mobile);
                                    startActivity(in);
                                    finish();

                                }else{
                                    Toast.makeText(Registration_Activity.this, response.getString("message"), Toast.LENGTH_LONG).show();
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
                Toast.makeText(Registration_Activity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
                return false;
            }
        }

    }
