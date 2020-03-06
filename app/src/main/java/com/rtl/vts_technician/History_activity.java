package com.rtl.vts_technician;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtl.vts_technician.adapter.HistoryListAdapter;
import com.rtl.vts_technician.model.DeviceModel;

import java.util.ArrayList;
import java.util.List;

public class History_activity extends AppCompatActivity {
    ImageView pimage;
    TextView pName;
    RecyclerView historyRecyclerView;
    private HistoryListAdapter historyListAdapter;
    private List<DeviceModel> historyList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        pName                   = (TextView) findViewById(R.id.pname);
        pimage                  = (ImageView) findViewById(R.id.pimage);
        historyRecyclerView     = (RecyclerView) findViewById(R.id.historyRecyclerView);

        pName.setText("Device History");

        historyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager  mLayoutManager = new LinearLayoutManager(History_activity.this);
        historyRecyclerView.setLayoutManager(mLayoutManager);

        historyListAdapter = new HistoryListAdapter(historyList);
        historyRecyclerView.setAdapter(historyListAdapter);

        prepareHistoryData();
    }

    private void prepareHistoryData() {

        DeviceModel deviceModel = new DeviceModel("RTL1XX1234", "DL1RT1234", "01-01-2018", "Active", "12-12-2017", "Kunal Pathak");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1734", "DL1RT1239", "01-01-2018", "Inactive", "12-12-2017", "Kunal Pathak");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1984", "DL1RT1004", "01-01-2018", "Inactive", "12-12-2017", "Kunal Pathak");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1291", "DL1RT1784", "01-01-2018", "Active", "12-12-2017", "Kunal Pathak");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1004", "DL1RT1334", "01-01-2018", "Active", "12-12-2017", "Kunal Pathak");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1114", "DL1RT1574", "01-01-2018", "Inactive", "12-12-2017", "Kunal Pathak");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1237", "DL1RT0034", "01-01-2018", "Active", "12-12-2017", "Kunal Pathak");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1200", "DL1RT9994", "01-01-2018", "Active", "12-12-2017", "Kunal Pathak");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1134", "DL1RT1884", "01-01-2018", "Inactive", "12-12-2017", "Kunal Pathak");
        historyList.add(deviceModel);

        historyListAdapter.notifyDataSetChanged();

    }
}
