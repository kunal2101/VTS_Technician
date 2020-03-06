package com.rtl.vts_technician;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtl.vts_technician.adapter.HistoryListAdapter;
import com.rtl.vts_technician.adapter.New_HistoryListAdapter;
import com.rtl.vts_technician.model.DeviceModel;

import java.util.ArrayList;
import java.util.List;

public class New_History_activity extends AppCompatActivity {
    ImageView pimage;
    TextView pName;
    RecyclerView historyRecyclerView;
    private New_HistoryListAdapter historyListAdapter;
    private List<DeviceModel> historyList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_history);

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
        LinearLayoutManager  mLayoutManager = new LinearLayoutManager(New_History_activity.this);
        historyRecyclerView.setLayoutManager(mLayoutManager);

        historyListAdapter = new New_HistoryListAdapter(historyList);
        historyRecyclerView.setAdapter(historyListAdapter);

        prepareHistoryData();
    }

    private void prepareHistoryData() {

        DeviceModel deviceModel = new DeviceModel("RTL1XX1234", "11:20", "01-01-2018", "12:25", "12-12-2017", "Depo 2");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1734", "13:47", "01-01-2018", "15:00", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1984", "09:12", "01-01-2018", "10:58", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1291", "15:00", "01-01-2018", "14:16", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1004", "14:41", "01-01-2018", "14:59", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1114", "11:25", "01-01-2018", "12:50", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1237", "16:12", "01-01-2018", "18:18", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1200", "12:00", "01-01-2018", "14:05", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

         deviceModel = new DeviceModel("RTL1XX1134", "18:14", "01-01-2018", "18:55", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

        historyListAdapter.notifyDataSetChanged();

    }
}
