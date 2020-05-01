package com.rtl.vts_technician;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rtl.vts_technician.Constants.Utility;
import com.rtl.vts_technician.Database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class De_installationActivity extends AppCompatActivity {
    ImageView pimage;
    TextView pName,txtDepo,txtDivision,txtImeiNo,txtInstaDate,txtRemarks,tv_latlong,txtInstaFromTime, txtVehicleNo;
    EditText txtDeviceId, txtDate, txtLocation, txtVehicle;
    Button btnSubmit,btn_getlatlong;
    DatePickerDialog picker;
    GPSTracker gps;
    ProgressDialog pdialog;
    DatabaseHelper dbHelper;
    double latitude,longitude;
    String response = null;
    ImageView form_image;
    Button btn_uploadPhoto;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String encoded;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_de_installation);


        Toolbar toolbar = (Toolbar) findViewById( R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable( R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor( R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        pName           = (TextView) findViewById( R.id.pname);
        pName.setText("De-Installation Form");

        dbHelper = new DatabaseHelper (this);

        txtDepo         = (TextView) findViewById( R.id.txtDepo);
        txtDivision     =  (TextView) findViewById( R.id.txtDivision);
        txtImeiNo       =   (TextView) findViewById( R.id.txtImeiNo);
        txtInstaDate    =   (TextView) findViewById( R.id.txtInstaDate);
        txtRemarks      =    (TextView) findViewById( R.id.txtRemarks);
        tv_latlong      =  (TextView)  findViewById( R.id.tv_latlong);
        txtInstaFromTime=  (TextView)  findViewById( R.id.txtInstaFromTime);
        pimage          = (ImageView) findViewById( R.id.pimage);
        txtVehicleNo    = (TextView) findViewById( R.id.txtVehicleNo);
        //txtHod          = (EditText) findViewById(R.id.txtHod);
        txtLocation     = (EditText) findViewById( R.id.txtLocation);
        txtDate         = (EditText) findViewById( R.id.txtDate);
        txtDeviceId     = (EditText) findViewById( R.id.txtDeviceId);
        btnSubmit       = (Button) findViewById( R.id.btnSubmit);
        btn_getlatlong  = (Button) findViewById( R.id.btn_getlatlong);
        txtVehicle      = (EditText) findViewById( R.id.txtVehicle);

        form_image      =   (ImageView) findViewById( R.id.form_image);
        btn_uploadPhoto =   (Button) findViewById( R.id.btn_uploadPhoto);

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

        txtDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpenForDivisionList();
            }
        });

        txtRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpenForRemarksist();
            }
        });

        txtVehicleNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOpenSearchVehicleList();

            }
        });

        txtImeiNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpenForIMEIist();
            }
        });

        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

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
                picker = new DatePickerDialog( De_installationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtInstaDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog( De_installationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtInstaFromTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        btn_getlatlong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddress();

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( De_installationActivity.this, "Data saved sucesfully", Toast.LENGTH_LONG).show();

                txtDepo.setText("Select Depo");
                txtDivision.setText("");
                txtImeiNo.setText("Select IMEI No");
                txtVehicleNo.setText("Vehicle No");
                txtInstaDate.setText("Installation Date");
                txtInstaFromTime.setText("Installation Time");
                txtRemarks.setText("Choose Remarks");
                tv_latlong.setText("Current Location");
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Cancel"};
        // final CharSequence[] items = { "Take Photo", "Take From Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission( De_installationActivity.this);

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                // onSelectFromGalleryResult(data);
            }else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
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
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);

    }

    private void dialogOpenSearchVehicleList() {

        final ArrayList<String> hod_str = new ArrayList<String>();
        final ArrayList<String> hod_id = new ArrayList<String>();

        hod_id.add("Division 1");hod_id.add("Division 2");hod_id.add("Division 3");hod_id.add("Division 4");hod_id.add("Division 5");
        hod_str.add("Vehicle 1");hod_str.add("Vehicle 2");hod_str.add("Vehicle 3");hod_str.add("Vehicle 4");hod_str.add("Vehicle 5");

        /*for(int ind = 0 ; ind< dataMechanic.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = dataMechanic.get(ind);
            mechanic_str.add(map.get("KEY_NAME"));
            mechanic_id.add(map.get("KEY_ID"));
        }*/

        final CharSequence[] dialogList = hod_str.toArray(new CharSequence[hod_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( De_installationActivity.this);
        builderDialog.setTitle("Select Vehicle No.");
        int count = dialogList.length;
        boolean[] is_checked = new boolean[count];
        final String[] getMechanic_str = new String[1];
        final String[] gethod_id = new String[1];

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
                        Toast.makeText( De_installationActivity.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        ArrayList yourlist = new ArrayList();
                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {
                                getMechanic_str[0] = list.getItemAtPosition(i).toString();
                                int hod_index = hod_str.indexOf(getMechanic_str[0]);
                                gethod_id[0] = hod_id.get(hod_index);



                                if (stringBuilder.length() > 0) stringBuilder.append(",");
                                stringBuilder.append(getMechanic_str[0]);
                                //dialogOpenForProjectList();
                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder.toString().trim().equals("")) {

                            txtDepo.setText("");
                            stringBuilder.setLength(0);


                        } else {
                            txtVehicleNo.setText(stringBuilder );
                            // txtDivision.setText(gethod_id[0]+"");
                            //Toast.makeText(NewActivityMaintenaceDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                            // mechType = String.valueOf(stringBuilder);


                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtVehicleNo.setText("Vehicle No");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

    }


    @SuppressLint("NewApi")
    public void getAddress() {
        // create class object

        gps = new GPSTracker ( De_installationActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            new locationupdate().execute();


        } else {

            gps.showSettingsAlert();
        }
    }
    private class locationupdate extends AsyncTask<String, String, String> {
        String myaddress;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (pdialog == null) {
                pdialog = new ProgressDialog( De_installationActivity.this);
                pdialog.setCancelable(false);
                pdialog.setMessage("Please Wait...");
                pdialog.show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
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

    private void dialogOpenForDivisionList() {

        final ArrayList<String> hod_str = new ArrayList<String>();
        final ArrayList<String> hod_id = new ArrayList<String>();

        hod_id.add("Division 1");hod_id.add("Division 2");hod_id.add("Division 3");hod_id.add("Division 4");hod_id.add("Division 5");
        hod_str.add("DEPO d");hod_str.add("DEPO 2");hod_str.add("DEPO 3");hod_str.add("DEPO 4");hod_str.add("DEPO 5");

        /*for(int ind = 0 ; ind< dataMechanic.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = dataMechanic.get(ind);
            mechanic_str.add(map.get("KEY_NAME"));
            mechanic_id.add(map.get("KEY_ID"));
        }*/

        final CharSequence[] dialogList = hod_id.toArray(new CharSequence[hod_id.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( De_installationActivity.this);
        builderDialog.setTitle("Select Division Name");
        int count = dialogList.length;
        boolean[] is_checked = new boolean[count];
        final String[] getMechanic_str = new String[1];
        final String[] gethod_id = new String[1];

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
                        Toast.makeText( De_installationActivity.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        ArrayList yourlist = new ArrayList();
                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {
                                getMechanic_str[0] = list.getItemAtPosition(i).toString();
                                int hod_index = hod_id.indexOf(getMechanic_str[0]);
                                gethod_id[0] = hod_str.get(hod_index);

                                if (stringBuilder.length() > 0) stringBuilder.append(",");
                                stringBuilder.append(getMechanic_str[0]);
                                //dialogOpenForProjectList();
                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder.toString().trim().equals("")) {

                            txtDivision.setText("");
                            stringBuilder.setLength(0);
                        } else {
                            txtDivision.setText(stringBuilder );
                            txtDepo.setText(gethod_id[0]+"");
                            Toast.makeText( De_installationActivity.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                            // mechType = String.valueOf(stringBuilder);

                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        txtDivision.setText("Select Depo");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

    }

    private void dialogOpenForIMEIist() {

        final ArrayList<String> hod_str = new ArrayList<String>();
        final ArrayList<String> hod_id = new ArrayList<String>();

        hod_id.add("Division 1");hod_id.add("Division 2");hod_id.add("Division 3");hod_id.add("Division 4");hod_id.add("Division 5");
        hod_str.add("IMEI 1");hod_str.add("IMEI 2");hod_str.add("IMEI 3");hod_str.add("IMEI 4");hod_str.add("IMEI 5");

        /*for(int ind = 0 ; ind< dataMechanic.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = dataMechanic.get(ind);
            mechanic_str.add(map.get("KEY_NAME"));
            mechanic_id.add(map.get("KEY_ID"));
        }*/

        final CharSequence[] dialogList = hod_str.toArray(new CharSequence[hod_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( De_installationActivity.this);
        builderDialog.setTitle("Select Imei No");
        int count = dialogList.length;
        boolean[] is_checked = new boolean[count];
        final String[] getMechanic_str = new String[1];
        final String[] gethod_id = new String[1];

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
                        Toast.makeText( De_installationActivity.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        ArrayList yourlist = new ArrayList();
                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {
                                getMechanic_str[0] = list.getItemAtPosition(i).toString();
                                int hod_index = hod_str.indexOf(getMechanic_str[0]);
                                gethod_id[0] = hod_id.get(hod_index);



                                if (stringBuilder.length() > 0) stringBuilder.append(",");
                                stringBuilder.append(getMechanic_str[0]);
                                //dialogOpenForProjectList();
                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder.toString().trim().equals("")) {

                            txtDepo.setText("");
                            stringBuilder.setLength(0);


                        } else {
                            txtImeiNo.setText(stringBuilder);
                            // Toast.makeText(NewActivity_AddDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                            // mechType = String.valueOf(stringBuilder);


                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtImeiNo.setText("Select IMEI No");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

    }
    private void dialogOpenForRemarksist() {

        final ArrayList<String> hod_str = new ArrayList<String>();
        final ArrayList<String> hod_id = new ArrayList<String>();

        hod_id.add("Division 1");hod_id.add("Division 2");hod_id.add("Division 3");hod_id.add("Division 4");hod_id.add("Division 5");
        hod_str.add("Remarks 1");hod_str.add("Remarks 2");hod_str.add("Remarks 3");hod_str.add("Remarks 4");hod_str.add("Remarks 5");

        /*for(int ind = 0 ; ind< dataMechanic.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = dataMechanic.get(ind);
            mechanic_str.add(map.get("KEY_NAME"));
            mechanic_id.add(map.get("KEY_ID"));
        }*/

        final CharSequence[] dialogList = hod_str.toArray(new CharSequence[hod_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( De_installationActivity.this);
        builderDialog.setTitle("Select Remarks");
        int count = dialogList.length;
        boolean[] is_checked = new boolean[count];
        final String[] getMechanic_str = new String[1];
        final String[] gethod_id = new String[1];

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
                        Toast.makeText( De_installationActivity.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        ArrayList yourlist = new ArrayList();
                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {
                                getMechanic_str[0] = list.getItemAtPosition(i).toString();
                                int hod_index = hod_str.indexOf(getMechanic_str[0]);
                                gethod_id[0] = hod_id.get(hod_index);



                                if (stringBuilder.length() > 0) stringBuilder.append(",");
                                stringBuilder.append(getMechanic_str[0]);
                                //dialogOpenForProjectList();
                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder.toString().trim().equals("")) {

                            txtDepo.setText("");
                            stringBuilder.setLength(0);


                        } else {
                            txtRemarks.setText(stringBuilder);
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
}
