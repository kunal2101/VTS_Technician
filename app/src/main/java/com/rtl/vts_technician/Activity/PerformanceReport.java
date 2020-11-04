package com.rtl.vts_technician.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rtl.vts_technician.PreferenceHelper;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Adapter.PerformanceCountAdapter;
import com.rtl.vts_technician.Pojo.PerformanceCountModel;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;
import com.rtl.vts_technician.Volley.CustomProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerformanceReport extends AppCompatActivity implements PerformanceCountAdapter.SelectedDate{
    ImageView pimage;
    TextView pName;
    RecyclerView recyle_performance;
    String div_code = "", tech_name = "", from_date = "", to_date = "";
    List<PerformanceCountModel> performanceCount;
    String processId = "", total_count = "";
    PerformanceCountAdapter performanceCountAdapter;
    PreferenceHelper preferenceHelper;
    ArrayList<String> tech_array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_report);

        Toolbar toolbar = (Toolbar) findViewById( R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable( R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor( R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        pName           = (TextView) findViewById( R.id.pname);
        pName.setText("Performance Report");

        preferenceHelper = new PreferenceHelper(PerformanceReport.this);

        try {
            div_code = getIntent().getStringExtra("KEY_DIVI_CODE");
            tech_name = getIntent().getStringExtra("KEY_TECH_NAME");
            from_date = getIntent().getStringExtra("KEY_FROM_DATE");
            to_date = getIntent().getStringExtra("KEY_TO_DATE");
            tech_array = getIntent().getStringArrayListExtra("KEY_TECH_ARRY");

            if (isConnection()){
                findInfo();
            }

        }catch (Exception ev){
            System.out.println(ev.getMessage());
        }

        recyle_performance = (RecyclerView) findViewById(R.id.recyle_performance);
        recyle_performance.setLayoutManager(new LinearLayoutManager(this));
        recyle_performance.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }


private void findInfo(){
    String  tag_string_req = "string_req";
    CustomProgressBar.showCustomProgressDialog(PerformanceReport.this,"Please Wait...");
    JSONObject requestJson = new JSONObject();
    // {"name":"all","startDt":"2020-07-23","endDt":"2020-08-24","divisionCode":"","userLogin":"all"}
    try{
        requestJson.put("name", "all");
        requestJson.put("startDt", from_date);
        requestJson.put("endDt", to_date);
        requestJson.put("divisionCode", div_code);
        requestJson.put("userLogin", tech_name);

        System.out.println("Diwas " +requestJson);

    }catch (Exception ev){
        System.out.print(ev.getMessage());
    }

    JsonObjectRequest req = new JsonObjectRequest(Constants.PERFORMANCE_REPORT + preferenceHelper.getToken() + "/", requestJson,
            new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //Toast.makeText(NewActivity_AddDevice.this, "SSSS"+response, Toast.LENGTH_LONG).show();
                        if (response.getString("status").equals("true")) {

                            JSONArray jsonArray = response.getJSONArray("data");
                            PerformanceCountModel performanceCountModel = null;
                            performanceCount = new ArrayList<>();
                            performanceCount.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                performanceCountModel = new PerformanceCountModel();

                                JSONObject jobject = jsonArray.getJSONObject(i);
                                performanceCountModel.setActivityDate(jobject.getString("activityDate"));
                                performanceCountModel.setInstall(jobject.getInt("install"));
                                performanceCountModel.setMaintenance(jobject.getInt("maintenance"));
                                performanceCountModel.setReplace(jobject.getInt("replace"));
                                performanceCountModel.setTotal(jobject.getInt("total"));
                                performanceCountModel.setUninstall(jobject.getInt("uninstall"));

                                performanceCount.add(performanceCountModel);
                            }

                            total_count = response.getString("count");
                            processId = response.getString("processId");
                            //------- Sort the list based on date   Start

                           /* Collections.sort(performanceCount, new Comparator<PerformanceCountModel>() {
                                @Override
                                public int compare(PerformanceCountModel o1, PerformanceCountModel o2) {
                                    return o2.getActivityDate().compareTo(o1.getActivityDate());
                                }
                            });*/
                            //  --------------------------OR-------------------
                            Collections.sort(performanceCount,
                                    (o1, o2) -> o2.getActivityDate().compareTo(o1.getActivityDate()));
                            //  -----------------------OR----------------------------
                            //performanceCount.sort(Comparator.comparing(PerformanceCountModel::getActivityDate).reversed());
                            //---------------------------------  End
                            performanceCountAdapter = new PerformanceCountAdapter(performanceCount, PerformanceReport.this);
                            recyle_performance.setAdapter(performanceCountAdapter);

                        }else{
                            Toast.makeText(PerformanceReport.this, response.getString("message"), Toast.LENGTH_LONG).show();
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
            Toast.makeText(PerformanceReport.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void selectedDate(PerformanceCountModel performanceCountModel) {
        startActivity(new Intent(PerformanceReport.this, PerformanceDetailReport.class)
                .putExtra("data",performanceCountModel)
                .putExtra("processid", processId)
                .putExtra("techLogin", tech_name)
                .putExtra("tech_array", tech_array));
    }
}