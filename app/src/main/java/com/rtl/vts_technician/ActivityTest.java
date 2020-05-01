package com.rtl.vts_technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityTest extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    LinearLayout ly_addDevice, ly_history, addDevice, ly_maintenance, removeDevice;
    ImageView imgSearch;
    String vehicelNo;
    EditText editVehicleNo;
    Button btnDownVeh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_dashborad_search);
        bottomNavigation = findViewById(R.id.nav_view);

        ly_history      = (LinearLayout) findViewById(R.id.ly_history);
        ly_maintenance      = (LinearLayout) findViewById(R.id.ly_maintenance);
        addDevice   = (LinearLayout) findViewById(R.id.addDevice);
        removeDevice   = (LinearLayout) findViewById(R.id.removeDevice);
        imgSearch     = (ImageView )  findViewById ( R.id.imgSearch );
        editVehicleNo = (EditText ) findViewById ( R.id.editVehicleNo );
        btnDownVeh    =(Button )    findViewById ( R.id.btnDownVeh ) ;

        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigation.setSelectedItemId(R.id.navigation_home);


        ly_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ani = new Intent(ActivityTest.this, New_Activity_History_Tab.class);
                startActivity(ani);

            }
        });
        ly_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ani = new Intent(ActivityTest.this, NewActivityMaintenaceDevice.class);
                startActivity(ani);

            }
        });
        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iin = new Intent(ActivityTest.this, NewActivity_AddDevice.class);
                startActivity(iin);

            }
        });
        removeDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iin_ = new Intent(ActivityTest.this, De_installationActivity.class);
                startActivity(iin_);
            }
        });
        btnDownVeh.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                Intent iin_ = new Intent(ActivityTest.this, ActivityDownVehicleList.class);
                startActivity(iin_);
            }
        } );
        imgSearch.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                vehicelNo = editVehicleNo.getText ().toString ();
                if (!TextUtils.isEmpty(vehicelNo)) {
                    if(vehicelNo.matches("[0-9]+")) {
                        Intent inty = new Intent(ActivityTest.this, ActivityVehicleDetail.class);
                        inty.putExtra("vehicelNo", vehicelNo);
                        startActivity(inty);
                    }else {
                        Intent inty = new Intent(ActivityTest.this, ActivityVehicleDetail.class);
                        inty.putExtra("vehicelNo", vehicelNo);
                        startActivity(inty);

                    }
                } else {
                    Toast.makeText(ActivityTest.this, "Plaese enter the vehicle number", Toast.LENGTH_SHORT).show();
                }
            }
        } );



    }

    @Override
    protected void onRestart () {
        super.onRestart ( );
        bottomNavigation.setSelectedItemId(R.id.navigation_home);

    }

/*
    @Override
    protected void onResume () {
        super.onResume ( );
        bottomNavigation.setSelectedItemId(R.id.navigation_home);

    }
*/

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:

                            Intent ani_ = new Intent(ActivityTest.this, ActivityTest.class);
                            startActivity(ani_);
                           // finish();
                            return true;
                        case R.id.navigation_dashboard:
                            Intent ani = new Intent(ActivityTest.this, ProfileActivity.class);
                            startActivity(ani);
                            return true;
                        case R.id.navigation_notifications:
                            finish();
                             return true;
                    }
                    return false;
                }
            };

}
