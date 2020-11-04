package com.rtl.vts_technician.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Adapter.RawDataDetailAdapter;
import com.rtl.vts_technician.Pojo.RawDataModel;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;
import com.rtl.vts_technician.Volley.CustomProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RawDataResultActivity extends AppCompatActivity {
    String intent_date, intent_imenino, intent_deviceid;
    TextView txtImeiNo, txtDeviceId, txtDate;
    ArrayList<RawDataModel> arr_raw_data;
    boolean isLoading = false;
    RecyclerView recyclerview;
    private ImageView img_back, img_refresh;
    private TextView txt_title, more;
    int count_;

    RawDataDetailAdapter rawDataDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_refresh = (ImageView) findViewById(R.id.img_refresh);
        txt_title = (TextView) findViewById(R.id.txt_title);
        more = (TextView) findViewById(R.id.more);
        txtImeiNo = (TextView) findViewById(R.id.txtImeiNo);
        txtDeviceId = (TextView) findViewById(R.id.txtDeviceId);
        txtDate = (TextView) findViewById(R.id.txtDate);
        recyclerview = (RecyclerView) findViewById(R.id.my_recycler_view);

        img_refresh.setVisibility(View.GONE);

        recyclerview.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(RawDataResultActivity.this);
        recyclerview.setLayoutManager(mLayoutManager);

        arr_raw_data = new ArrayList<>();

        try {
            intent_date = getIntent().getExtras().getString("date");
            intent_imenino = getIntent().getExtras().getString("imeino");
            String[] currencies = intent_imenino.split("/");
            intent_imenino = currencies[0];
            intent_deviceid = currencies[1];
            // intent_deviceid =  intent_imenino.substring( intent_imenino.indexOf("/")+1, intent_imenino.length());
            Log.d("allllll", intent_date + intent_imenino + "-------" + intent_deviceid);
            txtDate.setText(intent_date);
            txtDeviceId.setText(intent_deviceid);
            txtImeiNo.setText(intent_imenino);

        } catch (Exception e) {
            e.getMessage();
        }

        if (isConnection()) {
            GetImeinoDetail(intent_imenino, 10);
        }

        initScrollListener();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

       /* more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arr_raw_data.clear();
                arr_raw_date.clear();
                arr_raw_satatus.clear();
                GetImeinoDetail(intent_imenino,count_*20+"");
                count_ = count_ + 1 ;
            }
        });*/

       /* img_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arr_raw_data.clear();
                arr_raw_date.clear();
                arr_raw_satatus.clear();
                GetImeinoDetail(intent_imenino,"20");
            }
        });
*/
        txt_title.setText("Raw Data Detail");

    }

    private void GetImeinoDetail(final String deviceImei, final int count) {

        String tag_string_req = "string_req";

        CustomProgressBar.showCustomProgressDialog(this, "Please Wait...");

        final JSONObject requestJson = new JSONObject();
        try {

            requestJson.put("deviceImei", deviceImei);
            requestJson.put("count", count);

            System.out.println("Diwas " + requestJson);

        } catch (Exception ev) {
            System.out.print(ev.getMessage());
        }

        JsonObjectRequest req = new JsonObjectRequest(Constants.GETIMEINODETAIL, requestJson,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        CustomProgressBar.removeCustomProgressDialog();
                        try {

                            if (response.getInt("code") == 1) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                System.out.println("Diwas Response" + response);
                                    RawDataModel rawDataModel = null;
                                if (jsonArray.length() > 0) {

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        rawDataModel = new RawDataModel();

                                        JSONObject jobject = jsonArray.getJSONObject(i);
                                        rawDataModel.setMessageTime(jobject.getString("messageTime"));
                                        rawDataModel.setRawData(jobject.getString("rawData"));
                                        rawDataModel.setReceivedTime(jobject.getString("receivedTime"));
                                        rawDataModel.setImei(jobject.getString("imei"));
                                        rawDataModel.setLogId(jobject.getString("logId"));

                                        if (jobject.getString("msgStatus").equalsIgnoreCase("L")) {
                                            rawDataModel.setMsgStatus("Message Status - L (live)");
                                           // arr_raw_satatus.add("Message Status - L (live)");
                                        } else if (jobject.getString("msgStatus").equalsIgnoreCase("H")) {
                                            rawDataModel.setMsgStatus("Message Status - H (History)");
                                           // arr_raw_satatus.add("Message Status - H (History)");
                                        }
                                    arr_raw_data.add(rawDataModel);
                                    }

                                    rawDataDetailAdapter = new RawDataDetailAdapter(arr_raw_data);

                                    recyclerview.setAdapter(rawDataDetailAdapter);

                                } else {
                                    CustomProgressBar.removeCustomProgressDialog();
                                    Toast.makeText(RawDataResultActivity.this, "No Record found", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            CustomProgressBar.removeCustomProgressDialog();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());
                CustomProgressBar.removeCustomProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_string_req);

    }

    private void GetImeinoDetailMore(final String deviceImei, final int count) {

        String tag_string_req = "string_req";

      //  CustomProgressBar.showCustomProgressDialog(this, "Please Wait...");

        final JSONObject requestJson = new JSONObject();
        try {

            requestJson.put("deviceImei", deviceImei);
            requestJson.put("count", count);

            System.out.println("Diwas " + requestJson);

        } catch (Exception ev) {
            System.out.print(ev.getMessage());
        }

        JsonObjectRequest req = new JsonObjectRequest(Constants.GETIMEINODETAIL, requestJson,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                       CustomProgressBar.removeCustomProgressDialog();
                        try {

                            if (response.getInt("code") == 1) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                System.out.println("Diwas Response" + response);

                                if (jsonArray.length() > 0) {
                                    RawDataModel rawDataModel = null;
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        rawDataModel = new RawDataModel();

                                        JSONObject jobject = jsonArray.getJSONObject(i);
                                        rawDataModel.setMessageTime(jobject.getString("messageTime"));
                                        rawDataModel.setRawData(jobject.getString("rawData"));
                                        rawDataModel.setReceivedTime(jobject.getString("receivedTime"));
                                        rawDataModel.setImei(jobject.getString("imei"));
                                        rawDataModel.setLogId(jobject.getString("logId"));

                                        if (jobject.getString("msgStatus").equalsIgnoreCase("L")) {
                                            rawDataModel.setMsgStatus("Message Status - L (live)");

                                        } else if (jobject.getString("msgStatus").equalsIgnoreCase("H")) {
                                            rawDataModel.setMsgStatus("Message Status - H (History)");

                                        }

                                        arr_raw_data.add(null);
                                        rawDataDetailAdapter.notifyItemInserted(arr_raw_data.size() - 1);

                                       arr_raw_data.remove(arr_raw_data.size() - 1);
                                        int scrollPosition = arr_raw_data.size();
                                        rawDataDetailAdapter.notifyItemRemoved(scrollPosition);
                                        //int currentSize = scrollPosition;
                                       // int nextLimit = currentSize + 20;

                                       // while (currentSize - 1 < nextLimit) {
                                            arr_raw_data.add(rawDataModel);
                                            //currentSize++;
                                       // }

                                        rawDataDetailAdapter.notifyDataSetChanged();
                                        isLoading = false;

                                    }

                                } else {
                                    CustomProgressBar.removeCustomProgressDialog();
                                    Toast.makeText(RawDataResultActivity.this, "No Record found", Toast.LENGTH_LONG).show();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                           CustomProgressBar.removeCustomProgressDialog();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());
                CustomProgressBar.removeCustomProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_string_req);

    }

    private void initScrollListener() {
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == arr_raw_data.size() - 1) {
                        //bottom of list!
                        count_ = count_ + 10;
                        if(isConnection()){
                            GetImeinoDetailMore(intent_imenino, count_);
                        }
                        //loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }


    protected boolean isConnection() {
        ConnectivityManager manage = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            Toast.makeText(RawDataResultActivity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
