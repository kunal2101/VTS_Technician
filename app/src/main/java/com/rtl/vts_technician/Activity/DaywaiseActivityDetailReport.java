package com.rtl.vts_technician.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rtl.vts_technician.PreferenceHelper;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Adapter.ActivityDetailListAdapter;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;
import com.rtl.vts_technician.Volley.CustomProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DaywaiseActivityDetailReport extends AppCompatActivity {
    ImageView pimage;
    TextView pName;
    String div_code = "", tech_name = "", from_date = "", to_date = "";
    PreferenceHelper preferenceHelper;
    List<String> listDataHeader;
    HashMap<String, ArrayList<HashMap<String, String>>> listDataChild;
    ExpandableListView expListView;
    final int[] prevExpandPosition = {-1};
    List<String> listDataeCount;
    ActivityDetailListAdapter activityDetailListAdapter;
    ArrayList<String> tech_array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daywaise_detail_report);

       /* Toolbar toolbar = (Toolbar) findViewById( R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable( R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor( R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE); */

        preferenceHelper = new PreferenceHelper(DaywaiseActivityDetailReport.this);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        pName           = (TextView) findViewById( R.id.pname);
        pName.setText("Performance Summary Report");

        try {
            div_code = getIntent().getStringExtra("KEY_DIVI_CODE");
            tech_name = getIntent().getStringExtra("KEY_TECH_NAME");
            from_date = getIntent().getStringExtra("KEY_FROM_DATE");
            to_date = getIntent().getStringExtra("KEY_TO_DATE");
            tech_array = getIntent().getStringArrayListExtra("KEY_TECH_ARR");

            if (isConnection()){
                findInfo();
            }

        }catch (Exception ev){
            System.out.println(ev.getMessage());
        }

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (prevExpandPosition[0] >= 0 && prevExpandPosition[0] != groupPosition) {
                    expListView.collapseGroup(prevExpandPosition[0]);
                }
                prevExpandPosition[0] = groupPosition;
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                /*Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/
                return false;
            }
        });
    }

  /*  private void findInfo(){
        String  tag_string_req = "string_req";

        CustomProgressBar.showCustomProgressDialog(DaywaiseActivityDetailReport.this,"Please Wait...");
        JSONObject requestJson = new JSONObject();
        // {"name":"all","startDt":"2020-05-01","endDt":"2020-09-24","divisionCode":"all","userLogin":"all","token":"dkjjfjriuferoe"}
        try{
            requestJson.put("name", "all");
            requestJson.put("startDt", from_date);
            requestJson.put("endDt", to_date);
            requestJson.put("divisionCode", div_code);
            requestJson.put("userLogin", tech_name);
            requestJson.put("token", preferenceHelper.getToken());

            System.out.println("Diwas " +requestJson);

        }catch (Exception ev){
            System.out.print(ev.getMessage());
        }

        JsonObjectRequest req = new JsonObjectRequest(Constants.ACTIVITY_REPORT, requestJson,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getString("status").equals("true")) {
                                 String headerTitle = "";
                                listDataHeader = new ArrayList<String>();
                                listDataChild = new HashMap<String, ArrayList<HashMap<String, String>>>();
                                listDataeCount = new ArrayList<>();

                                listDataHeader.clear();
                                listDataChild.clear();
                                listDataeCount.clear();

                                JSONArray jsonArray = response.getJSONArray("dataCount");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    listDataeCount.add(jsonArray.getString(i));
                                    headerTitle = String.valueOf(i+1)+"'"+jsonArray.getString(i);
                                    listDataHeader.add(headerTitle);
                                }
                                JSONObject jobj = response.getJSONObject("data");

                                for (int j = 0; j < listDataeCount.size(); j++) {
                                    ArrayList<HashMap<String, String>> tempChild = new ArrayList<>();
                                    //tempChild.clear();
                                    String selected_date = listDataeCount.get(j);
                                    JSONArray jArray = jobj.getJSONArray(selected_date);
                                    for (int k = 0; k < jArray.length(); k++) {
                                        JSONObject activitypobject = jArray.getJSONObject(k);
                                        HashMap<String, String> maps = new HashMap<>();

                                            maps.put("INSTALL", activitypobject.getString("install"));
                                            maps.put("MAINTAINCE", activitypobject.getString("maintenance"));
                                            maps.put("REPLACE", activitypobject.getString("replace"));
                                            maps.put("TECH_NAME", activitypobject.getString("technicianName"));
                                            maps.put("TOTAL", activitypobject.getString("total"));
                                            maps.put("UNINSTALL", activitypobject.getString("uninstall"));
                                            maps.put("DATE", activitypobject.getString("activityDate"));

                                            tempChild.add(maps);
                                       // listDataChild.put(listDataHeader.get(j),tempChild);
                                    }
                                    listDataChild.put(listDataHeader.get(j),tempChild);
                                }
                                activityDetailListAdapter = new ActivityDetailListAdapter(DaywaiseActivityDetailReport.this, listDataHeader, listDataChild);
                                expListView.setAdapter(activityDetailListAdapter);
                                System.out.println("Header " +listDataHeader.size()+" --- Child " + listDataChild.size());
                            }else{
                                Toast.makeText(DaywaiseActivityDetailReport.this, response.getString("message"), Toast.LENGTH_LONG).show();
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
    }*/

    private void findInfo(){
        String  tag_string_req = "string_req";

        CustomProgressBar.showCustomProgressDialog(DaywaiseActivityDetailReport.this,"Please Wait...");
        JSONObject requestJson = new JSONObject();
        // {"name":"all","startDt":"2020-05-01","endDt":"2020-09-24","divisionCode":"all","userLogin":"all","token":"dkjjfjriuferoe"}
        try{
            requestJson.put("name", "all");
            requestJson.put("startDt", from_date);
            requestJson.put("endDt", to_date);
            requestJson.put("divisionCode", div_code);
            requestJson.put("userLogin", tech_name);
           // requestJson.put("token", preferenceHelper.getToken());

            System.out.println("Diwas " +requestJson);

        }catch (Exception ev){
            System.out.print(ev.getMessage());
        }

        JsonObjectRequest req = new JsonObjectRequest(Constants.DAYWISE_ACTIVITY_REPORT + preferenceHelper.getToken() +"/", requestJson,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getString("status").equals("true")) {
                                String headerTitle = "";
                                listDataHeader = new ArrayList<String>();
                                listDataChild = new HashMap<String, ArrayList<HashMap<String, String>>>();

                                listDataChild.clear();
                                listDataHeader.clear();

                                JSONObject jobj = response.getJSONObject("data");

                                Iterator keys = jobj.keys();
                                  int count =0 ;
                                while (keys.hasNext ()) {
                                    // loop to get the dynamic key
                                    String currentDynamicKey = (String) keys.next();
                                    count++;
                                    headerTitle = String.valueOf(count)+"'"+currentDynamicKey;
                                    listDataHeader.add(headerTitle);

                                    JSONArray currentDynamicValue = jobj.getJSONArray(currentDynamicKey);
                                    ArrayList<HashMap<String,String>> inner_array = new ArrayList<>();
                                    for (int k = 0; k < currentDynamicValue.length(); k++) {

                                        JSONObject activitypobject = currentDynamicValue.getJSONObject(k);
                                        if (tech_name.equals(activitypobject.getString("userLogin"))) {
                                            HashMap<String, String> maps = new HashMap<>();

                                            maps.put("INSTALL", activitypobject.getString("install"));
                                            maps.put("MAINTAINCE", activitypobject.getString("maintenance"));
                                            maps.put("REPLACE", activitypobject.getString("replace"));
                                            maps.put("TECH_NAME", activitypobject.getString("technicianName"));
                                            maps.put("TOTAL", activitypobject.getString("total"));
                                            maps.put("UNINSTALL", activitypobject.getString("uninstall"));
                                            maps.put("DATE", activitypobject.getString("activityDate"));
                                            inner_array.add(maps);
                                        }else if (tech_name.equalsIgnoreCase("All")) {

                                                    HashMap<String, String> maps = new HashMap<>();

                                                    maps.put("INSTALL", activitypobject.getString("install"));
                                                    maps.put("MAINTAINCE", activitypobject.getString("maintenance"));
                                                    maps.put("REPLACE", activitypobject.getString("replace"));
                                                    maps.put("TECH_NAME", activitypobject.getString("technicianName"));
                                                    maps.put("TOTAL", activitypobject.getString("total"));
                                                    maps.put("UNINSTALL", activitypobject.getString("uninstall"));
                                                    maps.put("DATE", activitypobject.getString("activityDate"));
                                                    inner_array.add(maps);

                                            }
                                    }
                                    //Log.d("Array list child " ,childArr.toString());
                                    listDataChild.put(headerTitle,inner_array);
                                }

                                activityDetailListAdapter = new ActivityDetailListAdapter(DaywaiseActivityDetailReport.this, listDataHeader, listDataChild);
                                expListView.setAdapter(activityDetailListAdapter);
                                //System.out.println("Header " +listDataHeader.size()+" --- Child " + listDataChild.size());
                            }else{
                                Toast.makeText(DaywaiseActivityDetailReport.this, response.getString("message"), Toast.LENGTH_LONG).show();
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
            Toast.makeText(DaywaiseActivityDetailReport.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}