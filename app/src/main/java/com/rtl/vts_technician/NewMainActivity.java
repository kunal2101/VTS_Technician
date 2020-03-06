package com.rtl.vts_technician;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewMainActivity extends AppCompatActivity {
    ImageView pimage;
    TextView pName,txt_Logout;
    LinearLayout ly_addDevice, ly_history, ly_replace, ly_maintenance, ly_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);
        ly_addDevice    = (LinearLayout) findViewById(R.id.ly_addDevice);
        ly_history      = (LinearLayout) findViewById(R.id.ly_history);
        ly_replace      = (LinearLayout) findViewById(R.id.ly_replace);
        ly_maintenance      = (LinearLayout) findViewById(R.id.ly_maintenance);
        ly_profile      = (LinearLayout) findViewById(R.id.ly_profile);

        txt_Logout      =  (TextView)     findViewById(R.id.txt_Logout);
        ly_addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iin = new Intent(NewMainActivity.this, NewActivity_AddDevice.class);
                startActivity(iin);
            }
        });
        ly_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ani = new Intent(NewMainActivity.this, New_Activity_History_Tab.class);
                startActivity(ani);

            }
        });

        ly_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ani = new Intent(NewMainActivity.this, NewActivityReplaceDevice.class);
                startActivity(ani);

            }
        });
        ly_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ani = new Intent(NewMainActivity.this, NewActivityMaintenaceDevice.class);
                startActivity(ani);

            }
        });
        txt_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        ly_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ani = new Intent(NewMainActivity.this, ProfileActivity.class);
                startActivity(ani);

            }
        });




     /*   pName           = (TextView) findViewById(R.id.pname);
        pimage          = (ImageView) findViewById(R.id.pimage);

        ly_reject       = (LinearLayout) findViewById(R.id.ly_reject);
        ly_accept       = (LinearLayout) findViewById(R.id.ly_accept);
        ly_repair       = (LinearLayout) findViewById(R.id.ly_repair);
        ly_history      = (LinearLayout) findViewById(R.id.ly_history);


        pName.setText("Dashboard");


        ly_repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent and = new Intent(NewMainActivity.this, Repair_Device.class);
                startActivity(and);
            }
        });

        ly_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ani = new Intent(NewMainActivity.this, History_activity.class);
                startActivity(ani);

            }
        });

        ly_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ant = new Intent(NewMainActivity.this, ApprovedDevice.class);
                startActivity(ant);
            }
        });*/

    }
}
