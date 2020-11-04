package com.rtl.vts_technician.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rtl.vts_technician.PreferenceHelper;
import com.rtl.vts_technician.R;

public class TechnicianDashboard extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    LinearLayout ly_addDevice, ly_history, addDevice, ly_maintenance, removeDevice;
    ImageView imgSearch;
    String vehicelNo;
    TextView editVehicleNo;
    Button btnDownVeh;
    TextView txt_user;
    PreferenceHelper preferenceHelper;
    private int REQUEST_CHECK_SETTINGS = 100;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_dashborad_search);

        bottomNavigation = findViewById(R.id.nav_view);
        preferenceHelper = new PreferenceHelper(TechnicianDashboard.this);
        initViewValues();

        ly_history      =   (LinearLayout) findViewById(R.id.ly_history);
        ly_maintenance  =   (LinearLayout) findViewById(R.id.ly_maintenance);
        addDevice       =   (LinearLayout) findViewById(R.id.addDevice);
        removeDevice    =   (LinearLayout) findViewById(R.id.removeDevice);
        imgSearch       =   (ImageView)  findViewById (R.id.imgSearch);
        editVehicleNo   =   (TextView) findViewById (R.id.editVehicleNo);
        txt_user        =   (TextView) findViewById (R.id.txt_user);
        btnDownVeh      =   (Button)    findViewById (R.id.btnDownVeh) ;

        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigation.setSelectedItemId(R.id.navigation_home);

        ly_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ani = new Intent(TechnicianDashboard.this, History_Tab_Activity.class);
                startActivity(ani);
            }
        });

        ly_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationSettingsRequest = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest).build();

                Task<LocationSettingsResponse> task = mSettingsClient.checkLocationSettings(mLocationSettingsRequest);

                task.addOnFailureListener(TechnicianDashboard.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                            // Location settings are not satisfied, but this can
                            // be fixed by showing the user a dialog
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(), and check the
                                // result in onActivityResult()
                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                resolvable.startResolutionForResult(TechnicianDashboard.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sendEx) {
                                // Ignore the error
                            }
                        }
                    }
                });

                task.addOnSuccessListener(TechnicianDashboard.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                        Intent ani = new Intent(TechnicianDashboard.this, MaintenaceDevice_Activity.class);
                        startActivity(ani);
                    }
                });

            }
        });

        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationSettingsRequest = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest).build();

                Task<LocationSettingsResponse> task = mSettingsClient.checkLocationSettings(mLocationSettingsRequest);

                task.addOnFailureListener(TechnicianDashboard.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                            // Location settings are not satisfied, but this can
                            // be fixed by showing the user a dialog
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(), and check the
                                // result in onActivityResult()
                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                resolvable.startResolutionForResult(TechnicianDashboard.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sendEx) {
                                // Ignore the error
                            }
                        }
                    }
                });

                task.addOnSuccessListener(TechnicianDashboard.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                        Intent iin = new Intent(TechnicianDashboard.this, AddDevice_Activity.class);
                        startActivity(iin);
                    }
                });

            }
        });

        removeDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationSettingsRequest = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest).build();

                Task<LocationSettingsResponse> task = mSettingsClient.checkLocationSettings(mLocationSettingsRequest);

                task.addOnFailureListener(TechnicianDashboard.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                            // Location settings are not satisfied, but this can
                            // be fixed by showing the user a dialog
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(), and check the
                                // result in onActivityResult()
                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                resolvable.startResolutionForResult(TechnicianDashboard.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sendEx) {
                                // Ignore the error
                            }
                        }
                    }
                });

                task.addOnSuccessListener(TechnicianDashboard.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                        Intent iin_ = new Intent(TechnicianDashboard.this, De_installationActivity.class);
                        startActivity(iin_);
                    }
                });
            }
        });

        btnDownVeh.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                Intent iin_ = new Intent(TechnicianDashboard.this, ActivityDownVehicleList.class);
                startActivity(iin_);
            }
        });

        editVehicleNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TechnicianDashboard.this, SearchViewActivity.class);
                startActivityForResult(intent, 3);
            }
        });

        imgSearch.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                vehicelNo = editVehicleNo.getText().toString().trim();

                if (!TextUtils.isEmpty(vehicelNo)) {

                    Intent inty = new Intent ( TechnicianDashboard.this , ActivityVehicleDetail.class );
                    inty.putExtra ("vehicelNo" , vehicelNo);
                    startActivity (inty);

                } else {
                    Toast.makeText ( TechnicianDashboard.this , "Plaese enter the Valid vehicle number" , Toast.LENGTH_SHORT ).show ( );
                }
            }
        } );


        txt_user.setText ( "Welcome " + preferenceHelper.getUser_Name () );
    }

    private void initViewValues(){

        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mSettingsClient = LocationServices.getSettingsClient(TechnicianDashboard.this);
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

                            Intent ani_ = new Intent(TechnicianDashboard.this, TechnicianDashboard.class);
                            startActivity(ani_);
                           // finish();
                            return true;
                        case R.id.navigation_dashboard:
                            Intent ani = new Intent(TechnicianDashboard.this, ProfileActivity.class);
                            startActivity(ani);
                            return true;
                        case R.id.navigation_notifications:
                            showLogoutDialog();
                           /* finish();
                            System.exit(0);*/
                            return true;
                    }
                    return false;
                }
            };

    public void showLogoutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("")
                .setTitle("Logout");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to logout from this application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        preferenceHelper.putMobileNo ( "" );
                        preferenceHelper.putPassword ( "" );
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Logout");
        alert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed
        if (resultCode == RESULT_CANCELED){
            Toast.makeText(TechnicianDashboard.this, "User Cancel request", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 3) {
            String message_st=data.getStringExtra("VEH_REG_NO");

            editVehicleNo.setText(message_st);
        }
    }
}
