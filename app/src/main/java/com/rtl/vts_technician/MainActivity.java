package com.rtl.vts_technician;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView pimage;
    TextView pName;
    LinearLayout ly_addDevice, ly_history, ly_repair, ly_accept, ly_reject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        pName           = (TextView) findViewById(R.id.pname);
        pimage          = (ImageView) findViewById(R.id.pimage);

        ly_reject       = (LinearLayout) findViewById(R.id.ly_reject);
        ly_accept       = (LinearLayout) findViewById(R.id.ly_accept);
        ly_repair       = (LinearLayout) findViewById(R.id.ly_repair);
        ly_history      = (LinearLayout) findViewById(R.id.ly_history);
        ly_addDevice    = (LinearLayout) findViewById(R.id.ly_addDevice);

        pName.setText("Dashboard");

        ly_addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iin = new Intent(MainActivity.this, Activity_AddDevice.class);
                startActivity(iin);
            }
        });

        ly_repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent and = new Intent(MainActivity.this, Repair_Device.class);
                startActivity(and);
            }
        });

        ly_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ani = new Intent(MainActivity.this, History_activity.class);
                startActivity(ani);

            }
        });

        ly_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ant = new Intent(MainActivity.this, ApprovedDevice.class);
                startActivity(ant);
            }
        });

    }
}
