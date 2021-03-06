package com.rtl.vts_technician.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.rtl.vts_technician.Constants.Utility;
import com.rtl.vts_technician.Database.DatabaseHelper;
import com.rtl.vts_technician.PreferenceHelper;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Utils.LocationProvider;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;
import com.rtl.vts_technician.Volley.CustomProgressBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddDevice_Activity extends AppCompatActivity implements LocationProvider.LocationCallback{
    ImageView pimage;
    TextView pName,txtDepo,txtDivision,txtImeiNo,txtInstaDate,txtRemarks,tv_latlong,txtInstaFromTime, txtVehicleNo,txtDivi_ent, txtDepo_ent;
    EditText  txtVehicle, txtImeiNo_ent, et_Remarks;
    Button btnSubmit,btn_getlatlong, btn_search;
    DatePickerDialog picker;
   // GPSTracker gps;
    ProgressDialog pdialog;
    DatabaseHelper dbHelper;
    double latitude,longitude;
    String response = null;
    public LocationProvider mLocationProvider;
    ImageView form_image;
    Button btn_uploadPhoto;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 1, SELECT_FILE = 2;
    private String encoded;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    LinearLayout ly_veh_ent, ly_div_srch, ly_div_ent, ly_depo_srch, ly_depo_ent, ly_imie_srch, ly_imie_ent, ly_txt_remarks, ly_et_remarks;
    ArrayList<HashMap<String, String>> alldivision;
    ArrayList<HashMap<String, String>> alldepo;
    String div_id = "";
    String divi_code = "", depo_code="";
    String depo ="", division = "", imieno = "", instantDate = "", instantTime ="", remarks ="", vehNo = "", address = "";
    PreferenceHelper preferenceHelper;
    ArrayList<HashMap<String, String>> allRemarks;
    double currentLatitude;
    double currentLongitude;
    TextView spinner_imie, tv_msg;
    String selected_date = "";
    boolean isMockAddress;
    //ArrayList<String> imieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.new_activity__add_device);

        Toolbar toolbar = (Toolbar) findViewById( R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable( R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor( R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        pName           = (TextView) findViewById( R.id.pname);
        pName.setText("New Device Installation");

        dbHelper = new DatabaseHelper (this);
        preferenceHelper = new PreferenceHelper(this);

        txtDepo         =   (TextView) findViewById(R.id.txtDepo);
        txtDivision     =   (TextView) findViewById(R.id.txtDivision);
        txtImeiNo       =   (TextView) findViewById(R.id.txtImeiNo);
        txtInstaDate    =   (TextView) findViewById(R.id.txtInstaDate);
        txtRemarks      =   (TextView) findViewById(R.id.txtRemarks);
        tv_latlong      =   (TextView) findViewById(R.id.tv_latlong);
        txtInstaFromTime=   (TextView) findViewById(R.id.txtInstaFromTime);
        pimage          =   (ImageView) findViewById(R.id.pimage);
        txtVehicleNo    =   (TextView) findViewById(R.id.txtVehicleNo);
        btnSubmit       =   (Button) findViewById(R.id.btnSubmit);
        btn_getlatlong  =   (Button) findViewById(R.id.btn_getlatlong);
        txtVehicle      =   (EditText) findViewById(R.id.txtVehicle);
        ly_veh_ent      =   (LinearLayout) findViewById(R.id.ly_veh_ent);
        ly_depo_srch    =   (LinearLayout) findViewById(R.id.ly_depo_srch);
        ly_depo_ent     =   (LinearLayout) findViewById(R.id.ly_depo_ent);
        ly_div_srch     =   (LinearLayout) findViewById(R.id.ly_div_srch);
        ly_div_ent      =   (LinearLayout) findViewById(R.id.ly_div_ent);
        ly_imie_srch    =   (LinearLayout) findViewById(R.id.ly_imie_srch);
        ly_imie_ent     =   (LinearLayout) findViewById(R.id.ly_imie_ent);

        form_image      =   (ImageView) findViewById(R.id.form_image);
        btn_uploadPhoto =   (Button) findViewById(R.id.btn_uploadPhoto);
        btn_search      =   (Button) findViewById(R.id.btn_search);
        txtDivi_ent     =   (TextView)  findViewById(R.id.txtDivi_ent);
        txtDepo_ent     =   (TextView)  findViewById(R.id.txtDepo_ent);
        txtImeiNo_ent   =   (EditText) findViewById(R.id.txtImeiNo_ent);

        et_Remarks      =  (EditText) findViewById(R.id.et_Remarks);
        ly_txt_remarks  = (LinearLayout) findViewById(R.id.ly_txt_remarks);
        ly_et_remarks   = (LinearLayout) findViewById(R.id.ly_et_remarks);
        spinner_imie    = (TextView ) findViewById(R.id.spinner_imie);
        tv_msg          = (TextView) findViewById(R.id.tv_msg);

        txtVehicleNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddDevice_Activity.this, SearchUnmapedVehicle.class);
                startActivityForResult(intent, 3);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehNo = txtVehicleNo.getText().toString().trim();

                ly_veh_ent.setVisibility(View.GONE);
                ly_div_ent.setVisibility(View.GONE);
                ly_depo_ent.setVisibility(View.GONE);
                //ly_imie_ent.setVisibility(View.GONE);
                ly_imie_ent.setVisibility(View.GONE);
                ly_div_srch.setVisibility(View.VISIBLE);
                ly_depo_srch.setVisibility(View.VISIBLE);
                ly_imie_srch.setVisibility(View.VISIBLE);
                //ly_imie_srch.setVisibility(View.GONE);

                if (isConnection()){
                    GetVehicleDetail(vehNo);
                }
            }
        });

        btn_uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                    }else{
                        selectImage();
                    }
                }
            }
        });
        spinner_imie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddDevice_Activity.this, SearchViewImeiNoActivity.class);
                startActivityForResult(intent, 3);
            }
        });

       // dialogOpenForHODList();

        txtDivi_ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnection()){
                    GetAllDivision();
                }
            }
        });

        txtDepo_ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (div_id.length() == 0){
                    Toast.makeText(AddDevice_Activity.this, "Select your division first", Toast.LENGTH_SHORT).show();
                }else {
                    if (isConnection()) {
                        GetSelectedDepo(div_id);
                    }
                }
            }
        });

        txtRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnection()){
                    GetAllRemarks();
                }
            }
        });

        final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentDate_temp = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        selected_date = currentDate_temp;
        txtInstaDate.setText(currentDate);
        txtInstaFromTime.setText(currentTime);

        txtInstaDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog( AddDevice_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtInstaDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                selected_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                               // Toast.makeText(NewActivity_AddDevice.this, selected_date, Toast.LENGTH_LONG).show();
                                //txtInstaDate.setText(year + "-" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        txtInstaFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                int sec = mcurrentTime.get(Calendar.SECOND);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog( AddDevice_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtInstaFromTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute,true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        btn_getlatlong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMockAddress) {
                    getAddress();
                    tv_msg.setVisibility(View.GONE);
                }else{
                    tv_msg.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vehNo = txtVehicleNo.getText().toString().trim();
                depo = txtDepo.getText().toString().trim();
                division = txtDivision.getText().toString().trim();
               // imieno = txtImeiNo_ent.getText().toString().trim();
                instantDate =  selected_date;//txtInstaDate.getText().toString().trim();
                instantTime = txtInstaFromTime.getText().toString().trim();
                remarks = txtRemarks.getText().toString().trim();
                address = tv_latlong.getText().toString().trim();

                if (vehNo.equals("Select Vehicle No.")){
                    //Toast.makeText( NewActivity_AddDevice.this, "Enter vehicle no.", Toast.LENGTH_LONG).show();
                    if (TextUtils.isEmpty(txtVehicle.getText().toString().trim())){
                        Toast.makeText( AddDevice_Activity.this, "Enter vehicle no.", Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        vehNo = txtVehicle.getText().toString().trim();
                    }
                } if (division.equals("Division")){

                    if (txtDivi_ent.getText().toString().trim().equals("Select Division")){
                        Toast.makeText( AddDevice_Activity.this, "Select Division Name.", Toast.LENGTH_LONG).show();
                        return;
                    }else{
                        division = txtDivi_ent.getText().toString().trim();
                    }
                }
                if (depo.equals("Depot")){

                    if (txtDepo_ent.getText().toString().trim().equals("Select Depot")){
                        Toast.makeText( AddDevice_Activity.this, "Select Depot", Toast.LENGTH_LONG).show();
                        return;
                    }else{
                        depo = txtDepo_ent.getText().toString().trim();
                    }
                }
                if (imieno.length() == 0){
                    Toast.makeText( AddDevice_Activity.this, "Select IMEI No", Toast.LENGTH_LONG).show();
                    if (imieno.equals("Choose IMIE NO")){
                        Toast.makeText( AddDevice_Activity.this, "Select IMEI No", Toast.LENGTH_LONG).show();
                        return;
                    }

                }
                if (TextUtils.isEmpty(tv_latlong.getText().toString().trim())){
                    Toast.makeText( AddDevice_Activity.this, "Address should not be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (remarks.equals("Choose Remarks")){
                    Toast.makeText( AddDevice_Activity.this, "Select remarks", Toast.LENGTH_LONG).show();
                    return;
                }
                if (txtRemarks.getText().toString().equals("OTHERS")){
                    if (TextUtils.isEmpty(et_Remarks.getText().toString().trim())) {
                        Toast.makeText(AddDevice_Activity.this, "Enter remarks", Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        remarks = et_Remarks.getText().toString().trim();
                    }
                }

                if (tv_latlong.getText().toString().trim().equalsIgnoreCase("Current Location") || TextUtils.isEmpty(tv_latlong.getText().toString().trim())){
                    Toast.makeText( AddDevice_Activity.this, "Location should not be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                    if (isConnection()) {
                        saveDeviceInfo();
                    }


            }
        });

        mLocationProvider = new LocationProvider(this, this);
    }

    private void GetAllRemarks(){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.GET_REMATRKS_INSTALL_MAINTENAM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    allRemarks = new ArrayList<>();
                    allRemarks.clear();
                    if (jsonObject.getInt("code") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            HashMap<String, String> maps = new HashMap<>();
                            maps.put("KEY_REMARKS",  jobject.getString("remarks"));
                            allRemarks.add(maps);

                        }
                        dialogOpenForRemarksist();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Cancel"};
        // final CharSequence[] items = { "Take Photo", "Take From Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission( AddDevice_Activity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                }

                // As per Need need to be uncomment for take image from gallery
                /* else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } */
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void dialogOpenForRemarksist() {
        final ArrayList<String> remarks_str = new ArrayList<String>();

        for(int ind = 0 ; ind< allRemarks.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = allRemarks.get(ind);
            remarks_str.add(map.get("KEY_REMARKS"));
        }

        final CharSequence[] dialogList = remarks_str.toArray(new CharSequence[remarks_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( AddDevice_Activity.this);
        builderDialog.setTitle("Select Remarks");

        // Creating multiple selection by using setMutliChoiceItem method
        builderDialog.setSingleChoiceItems(dialogList, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText( NewActivity_AddDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {
                                String getRemarks_str = list.getItemAtPosition(i).toString();
                                int remarks_index = remarks_str.indexOf(getRemarks_str);
                                String getRemarks = remarks_str.get(remarks_index);

                                if (stringBuilder.length() > 0) stringBuilder.append(",");
                                stringBuilder.append(getRemarks);
                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder.toString().trim().equals("")) {

                            txtRemarks.setText("");
                            stringBuilder.setLength(0);

                        } else {
                            txtRemarks.setText(stringBuilder);
                            if (txtRemarks.getText().toString().equals("OTHERS")){
                                ly_et_remarks.setVisibility(View.VISIBLE);
                                ly_txt_remarks.setVisibility(View.VISIBLE);
                            }else{
                                ly_et_remarks.setVisibility(View.GONE);
                                ly_txt_remarks.setVisibility(View.VISIBLE);
                            }
                            // Toast.makeText(NewActivity_AddDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                            // mechType = String.valueOf(stringBuilder);
                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtRemarks.setText("Choose Remarks");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed
        if (resultCode == RESULT_CANCELED){
            Toast.makeText(AddDevice_Activity.this, "User Cancel request", Toast.LENGTH_SHORT).show();
        } else if (requestCode == SELECT_FILE) {
                // onSelectFromGalleryResult(data);
            }else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }else if (resultCode == 3) {
                String message_st=data.getStringExtra("VEH_REG_NO");

                txtVehicleNo.setText(message_st);
            }else if (resultCode == 9) {
            String message_st=data.getStringExtra("VEH_REG_NO");
            imieno = message_st;
            spinner_imie.setText(message_st);
        }else if (resultCode == 4) {
            String message_st=data.getStringExtra("VEH_REG");

            showInstallVehicleAlertDialog(message_st);

        }else if (resultCode == 5) {
           // String message_st=data.getStringExtra("VEH_REG_NO");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("")
                    .setTitle("No Vehicle Found ");

            //Setting message manually and performing action on button click
            builder.setMessage("Invalid Vehicle No !!!!!!!!!!")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });

            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Info...");
            alert.show();

        }
    }

    public void showInstallVehicleAlertDialog(String vehicle_no){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("")
                .setTitle("Vehicle Already Install ");

        //Setting message manually and performing action on button click
        builder.setMessage("Vehicle No " + vehicle_no +"  is already install with another device.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Install Alert !");
        alert.show();
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        form_image.setImageBitmap(thumbnail);

        BitmapDrawable drawable = (BitmapDrawable) form_image.getDrawable();
        Bitmap imageBitmap = drawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);

    }

    @SuppressLint("NewApi")
    public void getAddress() {
        // create class object

        if (currentLatitude != 0.0 || currentLongitude != 0.0){
            new locationupdate().execute();
        }

       /* gps = new GPSTracker ( NewActivity_AddDevice.this);
          if (gps.canGetLocation()) {
                if (currentLatitude != 0.0 || currentLongitude != 0.0){
                    new locationupdate().execute();
                }
          } else {

              gps.showSettingsAlert();
          } */
    }

    @Override
    public void handleNewLocation(Location location) {
        Log.d("Location", location.toString());
        isMockAddress = location.isFromMockProvider();

        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        if (currentLatitude != 0.0 && currentLongitude != 0.0){
            mLocationProvider.disconnect();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationProvider.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationProvider.disconnect();
    }

    private class locationupdate extends AsyncTask<String, String, String> {
        String myaddress;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (pdialog == null) {
                pdialog = new ProgressDialog( AddDevice_Activity.this);
                pdialog.setCancelable(false);
                pdialog.setMessage("Please Wait...");
                pdialog.show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
                StringBuilder sb = new StringBuilder();
                if (addresses.size() > 0) {

                    myaddress = addresses.get(0).getAddressLine(0);
                    response = myaddress;
                } else {
                    Address address = addresses.get(0);
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
                        sb.append(addresses.get(0).getAddressLine(0)).append(", ");

                    sb.append(address.getFeatureName()).append(", ");

                    sb.append(address.getThoroughfare()).append(", ");
                    sb.append(address.getLocality()).append(", ");
                    sb.append(address.getAdminArea()).append(", ");
                    sb.append(address.getPostalCode()).append(", ");
                    sb.append(address.getCountryName());
                    response = sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pdialog != null) {
                pdialog.dismiss();
                pdialog = null;
            }
            tv_latlong.setText(response);
            tv_latlong.setTextColor(getResources().getColor( R.color.black));
        }
    }

    private void GetVehicleDetail(String vehicleNo){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(AddDevice_Activity.this,"Please Wait...");

        StringRequest strReq = new StringRequest( Request.Method.GET,
                Constants.unmappedvehicledetails+vehicleNo+"/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        JSONObject jObj = jsonObject.getJSONObject ("data");

                        //txtImeiNo.setText (jObj.getString ( "imei" ));
                        txtDepo.setText(jObj.getString ( "depot" ));
                        txtDivision.setText(jObj.getString ( "division" ));
                        divi_code = jObj.getString("divisionCode");
                        depo_code = jObj.getString("depotCode");

                    }else{
                        Toast.makeText(AddDevice_Activity.this, jsonObject.getString ( "message" ), Toast.LENGTH_LONG).show();
                        depo_code = "";
                        divi_code = "";

                        txtVehicleNo.setText("Select Vehicle No.");
                        //txtImeiNo.setText ("IMEI No");
                        txtDepo.setText("Depot");
                        txtDivision.setText("Division");


                        ly_veh_ent.setVisibility(View.VISIBLE);
                        ly_div_ent.setVisibility(View.VISIBLE);
                        ly_depo_ent.setVisibility(View.VISIBLE);
                        ly_imie_ent.setVisibility(View.GONE);
                        ly_div_srch.setVisibility(View.GONE);
                        ly_depo_srch.setVisibility(View.GONE);
                        ly_imie_srch.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse( VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    protected boolean isConnection() {
        ConnectivityManager manage = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {
            return true;
        } else {
            Toast.makeText(AddDevice_Activity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void dialogOpenForDivisionList() {

        final ArrayList<String> division_str = new ArrayList<String>();
        final ArrayList<String> division_id = new ArrayList<String>();
        final ArrayList<String> division_code = new ArrayList<String>();

        for(int ind = 0 ; ind< alldivision.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = alldivision.get(ind);
            division_id.add(map.get("KEY_DIV_ID"));
            division_str.add(map.get("KEY_DIV_NAME"));
            division_code.add(map.get("KEY_DIV_CODE"));
        }

        final CharSequence[] dialogList = division_str.toArray(new CharSequence[division_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( AddDevice_Activity.this);
        builderDialog.setTitle("Select Division Name");

        int count = dialogList.length;
        //boolean[] is_checked = new boolean[count];

        // Creating multiple selection by using setMutliChoiceItem method
       /* builderDialog.setMultiChoiceItems(dialogList, is_checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
                if (whichButton == 0) {

                }
            }
        });*/

        // Creating single  selection by using setSingleChoiceItems method
        builderDialog.setSingleChoiceItems(dialogList, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        StringBuilder stringBuilder_name = new StringBuilder();
                        StringBuilder stringBuilder_code = new StringBuilder();

                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {

                                String getDiv_str = list.getItemAtPosition(i).toString();
                                int div_index = division_str.indexOf(getDiv_str);
                                String getDiv_id = division_id.get(div_index);
                                String getDiv_Code = division_code.get(div_index);

                                if (stringBuilder_name.length() > 0)
                                    stringBuilder_name.append(",");
                                stringBuilder_name.append(getDiv_str);

                                if (stringBuilder.length() > 0)
                                    stringBuilder.append(",");
                                stringBuilder.append(getDiv_id);

                                if (stringBuilder_code.length() > 0)
                                    stringBuilder_code.append(",");
                                stringBuilder_code.append(getDiv_Code);
                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */

                        if (stringBuilder_name.toString().trim().equals("")) {

                            txtDivi_ent.setText("");
                            stringBuilder_name.setLength(0);
                        } else {
                            txtDivi_ent.setText(stringBuilder_name );
                            div_id = stringBuilder.toString();
                            divi_code = stringBuilder_code.toString();
                            //txtDepo.setText(getDivision_id[0]+"");
                            Toast.makeText( AddDevice_Activity.this, divi_code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        txtDivi_ent.setText("Select Division");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();
    }

    private void dialogOpenForDepoList() {

        final ArrayList<String> depo_str    = new ArrayList<String>();
        final ArrayList<String> depo_id     = new ArrayList<String>();
        final ArrayList<String> depo_codes  = new ArrayList<String>();

        for(int ind = 0 ; ind< alldepo.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = alldepo.get(ind);
            depo_id.add(map.get("KEY_DEPO_ID"));
            depo_str.add(map.get("KEY_DEPO_NAME"));
            depo_codes.add(map.get("KEY_DEPO_CODE"));
        }

        final CharSequence[] dialogList = depo_str.toArray(new CharSequence[depo_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( AddDevice_Activity.this);
        builderDialog.setTitle("Select Depo Name");

       int count = dialogList.length;
        boolean[] checkedItems  = new boolean[count];

        for(int i=0 ; i<count ; i++){
            checkedItems [i] = true;
        }


        // Creating multiple selection by using setMutliChoiceItem method
       /* builderDialog.setMultiChoiceItems(dialogList, checkedItems , new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {

            }
        });*/

        // Creating single  selection by using setSingleChoiceItems method
        builderDialog.setSingleChoiceItems(dialogList, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText( Registration_Activity.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        StringBuilder stringBuilder_name = new StringBuilder();
                        StringBuilder stringBuilder_code = new StringBuilder();

                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {
                                String getDepo_str = list.getItemAtPosition(i).toString();
                                int depo_index = depo_str.indexOf(getDepo_str);
                                String getDepo_id = depo_id.get(depo_index);
                                String getDepo_Code = depo_codes.get(depo_index);

                                if (stringBuilder_name.length() > 0)
                                    stringBuilder_name.append(",");
                                stringBuilder_name.append(getDepo_str);

                                if (stringBuilder.length() > 0)
                                    stringBuilder.append(",");
                                stringBuilder.append(getDepo_id);

                                if (stringBuilder_code.length() > 0)
                                    stringBuilder_code.append(",");
                                stringBuilder_code.append(getDepo_Code);
                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder_name.toString().trim().equals("")) {

                            txtDepo_ent.setText("");
                            stringBuilder_name.setLength(0);
                        } else {
                            txtDepo_ent.setText(stringBuilder_name );
                            depo_code = stringBuilder_code.toString();
                            //txtDepo.setText(getDivision_id[0]+"");
                            Toast.makeText( AddDevice_Activity.this, depo_code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        txtDepo_ent.setText("Select Depo");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();
    }

    private void GetAllDivision(){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.GETDIVISION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    alldivision = new ArrayList<>();
                    alldivision.clear();
                    if (jsonObject.getInt("code") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            HashMap<String, String> maps = new HashMap<>();
                            maps.put("KEY_DIV_ID",  jobject.getString("divisionId"));
                            maps.put("KEY_DIV_NAME",jobject.optString("divisionName"));
                            maps.put("KEY_DIV_CODE",jobject.optString("divisionCode"));
                            alldivision.add(maps);
                        }
                        dialogOpenForDivisionList();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void GetSelectedDepo(String div_id){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.GETDEPO + div_id + "/lksdjf984ew/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    alldepo = new ArrayList<>();
                    alldepo.clear();
                    if (jsonObject.getInt("code") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            HashMap<String, String> maps = new HashMap<>();
                            maps.put("KEY_DEPO_ID",  jobject.getString("depot_id"));
                            maps.put("KEY_DEPO_NAME",jobject.optString("depot_name"));
                            maps.put("KEY_DEPO_CODE",jobject.optString("depotCode"));
                            alldepo.add(maps);
                        }
                        dialogOpenForDepoList();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());

            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void saveDeviceInfo(){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(AddDevice_Activity.this,"Please Wait...");
        JSONObject requestJson = new JSONObject();
        //{"deviceImei":"874355443","installationDate":"2020-04-28","installationTime":"21:45:23","depot_code":"KRD","division_code":"nasik","vehicle_reg_no":"MH235679","userLogin":"980098709"}

        try{
            requestJson.put("deviceImei", imieno);
            requestJson.put("installationDate", instantDate);
            requestJson.put("installationTime", instantTime);
            requestJson.put("depot_code", depo_code);
            requestJson.put("division_code", divi_code);
            requestJson.put("vehicle_reg_no", vehNo);
            requestJson.put("userLogin", preferenceHelper.getUserLogin());
            requestJson.put("latitude", currentLatitude);
            requestJson.put("longitude", currentLongitude);
            requestJson.put("address", address);
            requestJson.put("imageString", encoded);
            System.out.println("Diwas " +requestJson);

        }catch (Exception ev){
            System.out.print(ev.getMessage());
        }
        //Constants.GETINSTALLVEHICLE

        JsonObjectRequest req = new JsonObjectRequest(Constants.GETINSTALLVEHICLE, requestJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //{"status":"true","message":"success","code":1}
                            if (response.getString("status").equals("true")) {
                                Toast.makeText(AddDevice_Activity.this, "Saved Sucessfully", Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(AddDevice_Activity.this, response.getString("message"), Toast.LENGTH_LONG).show();

                            }

                            CustomProgressBar.removeCustomProgressDialog();
                        } catch (Exception e) {
                            CustomProgressBar.removeCustomProgressDialog();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CustomProgressBar.removeCustomProgressDialog();
                VolleyLog.e("Error: ", error.getMessage());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        AppController.getInstance().addToRequestQueue(req, tag_string_req);
    }
}
