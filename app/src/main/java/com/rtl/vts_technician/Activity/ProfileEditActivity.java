package com.rtl.vts_technician.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

public class ProfileEditActivity extends AppCompatActivity {
    EditText input_name,input_email,input_mobile, input_oldpass, input_newpass;
    String encodedImage="", division= "", depot = "";
    Button btnSave, btnSave_div;
    TextView pName, input_division, input_depo;
    PreferenceHelper preferenceHelper;
    ArrayList<HashMap<String, String>> alldivision;
    ArrayList<HashMap<String, String>> alldepo;
    String div_id = "";
    String divi_code = "", depo_code="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        preferenceHelper = new PreferenceHelper(this);

        input_name= (EditText)findViewById(R.id.input_name);
        input_email= (EditText)findViewById(R.id.input_email);
        input_mobile= (EditText)findViewById(R.id.input_mobile);
        btnSave        = (Button)findViewById(R.id.btnSave);
        input_newpass  = (EditText) findViewById(R.id.input_newpass);
        input_oldpass  = (EditText)  findViewById(R.id.input_oldpass);
        input_depo      = findViewById(R.id.input_depo);
        input_division  = findViewById(R.id.input_division);
        btnSave_div     = findViewById(R.id.btnSave_div);

        pName = (TextView) findViewById ( R.id.pname );
        pName.setText ("Edit Profile");

        try {
            input_name.setText(getIntent().getStringExtra("name"));
            input_email.setText(getIntent().getStringExtra("email"));
            input_mobile.setText(getIntent().getStringExtra("mobile"));

            input_depo.setText(getIntent().getStringExtra("depo"));
            division = getIntent().getStringExtra("div");

            input_division.setText(division);
            depot = getIntent().getStringExtra("depo");

        }catch (Exception e){
            e.getMessage();
        }

        input_division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnection()){
                    GetAllDivision();
                }
            }
        });

        input_depo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (div_id.length() == 0){
                    Toast.makeText(ProfileEditActivity.this, "Select your division first", Toast.LENGTH_SHORT).show();
                }else {
                    if (isConnection()) {
                        GetSelectedDepo(div_id);
                    }
                }
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldPassword  = input_oldpass.getText().toString().trim();
                String newPassword  = input_newpass.getText().toString().trim();

                if (TextUtils.isEmpty(input_oldpass.getText().toString())){
                    Toast.makeText(ProfileEditActivity.this, "Enter old password", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(input_newpass.getText().toString())){
                    Toast.makeText(ProfileEditActivity.this, "Enter new password", Toast.LENGTH_LONG).show();
                }else if (newPassword.length() < 6){
                    Toast.makeText(ProfileEditActivity.this, "Password must be atleast 6 characters", Toast.LENGTH_LONG).show();
                }else{
                    if (isConnection()){
                        changePassword(oldPassword, newPassword);
                    }
                }

            }
        });

        btnSave_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(divi_code.length() == 0){
                    Toast.makeText(ProfileEditActivity.this, "Select Division", Toast.LENGTH_SHORT).show();
                }else if(depo_code.length() == 0){
                    Toast.makeText(ProfileEditActivity.this, "Select Depo", Toast.LENGTH_SHORT).show();
                }else{
                    if (isConnection()){
                        updateDivision(divi_code, depo_code);
                    }
                }
            }
        });

    }

    private void updateDivision(String div, String depo){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(ProfileEditActivity.this,"Please Wait...");
        JSONObject requestJson = new JSONObject();
        // {"divisionCode":"","depotCodes":"","isActive":"1","userLogin":"8745028441"}
        try{
            requestJson.put("divisionCode", div);
            requestJson.put("depotCodes", depo);
            requestJson.put("isActive", "1");
            requestJson.put("userLogin", preferenceHelper.getUserLogin());

            System.out.println("Diwas " +requestJson);

        }catch (Exception ev){
            System.out.print(ev.getMessage());
        }

        JsonObjectRequest req = new JsonObjectRequest(Constants.UPDATE_DIVISION, requestJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getString("status").equals("true")) {
                                Toast.makeText(ProfileEditActivity.this, "Updated Sucessfully", Toast.LENGTH_LONG).show();
                                //finish();
                            }else{
                                Toast.makeText(ProfileEditActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
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


    private void changePassword(String oldpass, String newpassowrd){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(ProfileEditActivity.this,"Please Wait...");
        JSONObject requestJson = new JSONObject();
        // {"userLogin":"msrtc_7294","oldPassword":"jfhwlkdw","userPassword":"98098"}
        try{
            requestJson.put("userLogin", preferenceHelper.getUserLogin());
            requestJson.put("oldPassword", oldpass);
            requestJson.put("userPassword", newpassowrd);

            System.out.println("Diwas " +requestJson);

        }catch (Exception ev){
            System.out.print(ev.getMessage());
        }

        JsonObjectRequest req = new JsonObjectRequest(Constants.CHANGE_PASSWORD, requestJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Toast.makeText(LoginActivity.this, "Your query submitted sucessfully", Toast.LENGTH_LONG).show();

                            if (response.getString("status").equals("true")) {
                                Toast.makeText(ProfileEditActivity.this, "Updated Sucessfully", Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(ProfileEditActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
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
            Toast.makeText(ProfileEditActivity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
   /* private String encodedImagegallery(Bitmap imImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imImage.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        encodedImage = encodedImage(thumbnail);
    }
    public String encodedImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;

    }*/

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

        final String[] selected_division = division.split(",");

        final CharSequence[] dialogList = division_str.toArray(new CharSequence[division_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( ProfileEditActivity.this);
        builderDialog.setTitle("Select Division Name");

        int count = dialogList.length;
        boolean[] is_checked = new boolean[count];

        for(int i=0 ; i<dialogList.length ; i++){

            for(int j=0 ; j<selected_division.length ; j++){
                if(dialogList[i] .equals(selected_division[j]) ){

                    is_checked[i] = true;
                }
            }
        }

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

                            input_division.setText("");
                            stringBuilder_name.setLength(0);
                        } else {
                            input_division.setText(stringBuilder_name );
                            div_id = stringBuilder.toString();
                            divi_code = stringBuilder_code.toString();
                            //txtDepo.setText(getDivision_id[0]+"");
                            Toast.makeText( ProfileEditActivity.this, divi_code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        input_division.setText("Select Division");
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

        final String[] selected_depo = depot.split(",");

        final CharSequence[] dialogList = depo_str.toArray(new CharSequence[depo_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( ProfileEditActivity.this);
        builderDialog.setTitle("Select Depo Name");

        // Creating multiple selection by using setMutliChoiceItem method

        int count = dialogList.length;
        boolean[] is_checked = new boolean[count];

      /*  for(int i=0 ; i<count ; i++){
            is_checked[i] = true;
        }*/


        for(int i=0 ; i<dialogList.length ; i++){

            for(int j=0 ; j<selected_depo.length ; j++){
                if(dialogList[i] .equals(selected_depo[j]) ){

                    is_checked[i] = true;
                }
            }
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

                            input_depo.setText("");
                            stringBuilder_name.setLength(0);
                        } else {
                            input_depo.setText(stringBuilder_name );
                            depo_code = stringBuilder_code.toString();

                            Toast.makeText( ProfileEditActivity.this, depo_code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        input_depo.setText("Select Depot");

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


}
