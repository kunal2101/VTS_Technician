package com.rtl.vts_technician;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtl.vts_technician.adapter.ApprovedDeviceListAdapter;
import com.rtl.vts_technician.model.ApprovedDeviceModel;

import java.util.ArrayList;
import java.util.List;

public class ApprovedDevice extends AppCompatActivity {
    ImageView pimage;
    TextView pName;
    RecyclerView approvedRecyclerView;
    private ApprovedDeviceListAdapter approvedDeviceListAdapter;
    private List<ApprovedDeviceModel> approvedList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_device);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        pName                    = (TextView) findViewById(R.id.pname);
        pimage                   = (ImageView) findViewById(R.id.pimage);
        approvedRecyclerView     = (RecyclerView) findViewById(R.id.approvedRecyclerView);

        pName.setText("Approved Device");

        approvedRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ApprovedDevice.this);
        approvedRecyclerView.setLayoutManager(mLayoutManager);

        approvedDeviceListAdapter = new ApprovedDeviceListAdapter(approvedList);
        approvedRecyclerView.setAdapter(approvedDeviceListAdapter);

        prepareApprovedData();
    }

    private void prepareApprovedData() {
        ApprovedDeviceModel approvedDeviceModel = new ApprovedDeviceModel("RTL1XX123", "Accepted", "11-12-2017");
        approvedList.add(approvedDeviceModel);

        approvedDeviceModel = new ApprovedDeviceModel("RTL1XX163", "Accepted", "10-12-2017");
        approvedList.add(approvedDeviceModel);
        approvedDeviceModel = new ApprovedDeviceModel("RTL1XX183", "Accepted", "12-12-2017");
        approvedList.add(approvedDeviceModel);
        approvedDeviceModel = new ApprovedDeviceModel("RTL1XX120", "Accepted", "17-12-2017");
        approvedList.add(approvedDeviceModel);
        approvedDeviceModel = new ApprovedDeviceModel("RTL1XX122", "Accepted", "13-12-2017");
        approvedList.add(approvedDeviceModel);
        approvedDeviceModel = new ApprovedDeviceModel("RTL1XX1270", "Accepted", "04-12-2017");
        approvedList.add(approvedDeviceModel);
        approvedDeviceModel = new ApprovedDeviceModel("RTL1XX133", "Accepted", "08-12-2017");
        approvedList.add(approvedDeviceModel);
        approvedDeviceModel = new ApprovedDeviceModel("RTL1XX003", "Accepted", "13-12-2017");
        approvedList.add(approvedDeviceModel);
        approvedDeviceModel = new ApprovedDeviceModel("RTL1XX113", "Accepted", "01-12-2017");
        approvedList.add(approvedDeviceModel);
        approvedDeviceModel = new ApprovedDeviceModel("RTL1XX133", "Accepted", "22-12-2017");
        approvedList.add(approvedDeviceModel);
        approvedDeviceModel = new ApprovedDeviceModel("RTL1XX853", "Accepted", "24-12-2017");
        approvedList.add(approvedDeviceModel);

    }
}
