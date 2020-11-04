package com.rtl.vts_technician.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.rtl.vts_technician.R;

public class NewMainActivity extends AppCompatActivity {
    ImageView pimage;
    TextView pName,txt_Logout;
    LinearLayout ly_addDevice, ly_history, ly_replace, ly_maintenance, ly_profile;
    RelativeLayout rlTop;
    AppBarLayout Appbar;
    CollapsingToolbarLayout CoolToolbar;
    Button btn_installation;
    Toolbar toolbar;
    boolean ExpandedActionBar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        rlTop=(RelativeLayout)findViewById(R.id.rltop);
        Appbar = (AppBarLayout)findViewById(R.id.appbar);
        btn_installation = (Button)findViewById(R.id.btn_installation);
        CoolToolbar = (CollapsingToolbarLayout)findViewById(R.id.ctolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CoolToolbar.setTitle("");

        Appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset) > 200){
                    ExpandedActionBar = false;
                    CoolToolbar.setTitle("MSRTC Technician");
                    rlTop.setVisibility(View.GONE);
                    invalidateOptionsMenu();
                } else {
                    ExpandedActionBar = true;
                    CoolToolbar.setTitle("");
                    rlTop.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                }
            }
        });

        ly_addDevice    = (LinearLayout) findViewById(R.id.ly_addDevice);
        ly_history      = (LinearLayout) findViewById(R.id.ly_history);
        ly_replace      = (LinearLayout) findViewById(R.id.ly_replace);
        ly_maintenance      = (LinearLayout) findViewById(R.id.ly_maintenance);
        ly_profile      = (LinearLayout) findViewById(R.id.ly_profile);

        txt_Logout      =  (TextView)     findViewById(R.id.txt_Logout);
        btn_installation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iin = new Intent(NewMainActivity.this, AddDevice_Activity.class);
                startActivity(iin);
            }
        });
        ly_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ani = new Intent(NewMainActivity.this, History_Tab_Activity.class);
                startActivity(ani);

            }
        });

        ly_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent ani = new Intent(NewMainActivity.this, ReplaceDevice_Activity.class);
                startActivity(ani);*/

            }
        });

        ly_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ani = new Intent(NewMainActivity.this, MaintenaceDevice_Activity.class);
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
