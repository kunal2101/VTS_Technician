package com.rtl.vts_technician.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rtl.vts_technician.R;

public class Splash_Activity extends AppCompatActivity {

    int windowwidth;
    int windowheight;
    ImageView img;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,

    };
    Thread background;

    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        // img = (ImageView) findViewById(R.id.img1);

        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getWindowManager().getDefaultDisplay().getHeight();

        if (ActivityCompat.checkSelfPermission(Splash_Activity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(Splash_Activity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(Splash_Activity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(Splash_Activity.this, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED

        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Splash_Activity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Splash_Activity.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Splash_Activity.this, permissionsRequired[2]
            ) || ActivityCompat.shouldShowRequestPermissionRationale(Splash_Activity.this, permissionsRequired[3]
            )
            ) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(Splash_Activity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Storage and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(Splash_Activity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(Splash_Activity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }

            // txtPermissions.setText("Permissions Required");

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            //StartAnimations();
            proceedAfterPermission();
        }


    }
    private void proceedAfterPermission() {
       /* LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //  buildAlertMessageNoGps();
            noLocation();
        }else {*/
        background = new Thread() {
            public void run() {
                try {
                    sleep(4 * 1000);
                    Intent inty = new Intent(getApplication(), DashBoard_Activity_Tab.class);
                    startActivity(inty);
                    //overridePendingTransition(R.anim.s, R.anim.slide_to_left);
                    finish();

                } catch (Exception ev) {
                    System.out.print(ev.getMessage());
                }
            }
        };
        background.start();

        //StartAnimations();

    }


    /*private void StartAnimations() {
        RelativeLayout root = (RelativeLayout)findViewById( R.id.parentLay);
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        @SuppressWarnings("unused")
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();

        int originalPos[] = new int[2];
        //  img.getLocationOnScreen( originalPos );

        TranslateAnimation anim = new TranslateAnimation(0, 0, dm.heightPixels, (float) (dm.heightPixels/3.5) );
        anim.setDuration(3000);
        anim.setFillAfter(true);
        // img.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {

                Intent i = new Intent(Splash_Activity.this, DashBoard_Activity_Tab.class);
                startActivity(i);
                finish();

            }

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                //Toast.makeText(getApplicationContext(), "Start", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }
        });
    }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(Splash_Activity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                // StartAnimations();
                proceedAfterPermission();
            }
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(Splash_Activity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
              //  StartAnimations();
                proceedAfterPermission();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT){
            //check if all permissions are granted
            boolean allgranted = false;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if(allgranted){

               // StartAnimations();
                proceedAfterPermission();

            } else if(ActivityCompat.shouldShowRequestPermissionRationale(Splash_Activity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Splash_Activity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Splash_Activity.this,permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Splash_Activity.this,permissionsRequired[3])
            ){
                //txtPermissions.setText("Permissions Required");
                AlertDialog.Builder builder = new AlertDialog.Builder(Splash_Activity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Storage and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(Splash_Activity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                builder.show();

            } else {
                Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        // do something here and don't write super.onBackPressed()
    }
}
