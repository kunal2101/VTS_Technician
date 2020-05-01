package com.rtl.vts_technician;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rtl.vts_technician.Database.DatabaseHelper;
import com.rtl.vts_technician.model.ReplaceDeviceModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NewActivityReplaceDevice extends AppCompatActivity {
    ImageView pimage;
    TextView pName,txtDepo,txtDivision,txtImeiNo,txtInstaDate,txtRemarks,tv_latlong,txtInstaFromTime,txtVehicleNo,txtOldImeiNo;
    EditText txtDeviceId, txtDate, txtLocation, txtHod;
    Button btnSubmit,btn_getlatlong;

    DatePickerDialog picker;
    GPSTracker gps;
    ProgressDialog pdialog;

    double latitude,longitude;
    String response = null;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity__replace_device);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        pName           = (TextView) findViewById(R.id.pname);
        txtDepo         = (TextView) findViewById(R.id.txtDepo);
        txtDivision     =  (TextView) findViewById(R.id.txtDivision);
        txtImeiNo       =   (TextView) findViewById(R.id.txtImeiNo);
        txtInstaDate    =   (TextView) findViewById(R.id.txtInstaDate);
        txtRemarks      =    (TextView) findViewById(R.id.txtRemarks);
        tv_latlong      =  (TextView)  findViewById(R.id.tv_latlong);
        txtInstaFromTime=  (TextView)  findViewById(R.id.txtInstaFromTime);
        txtVehicleNo    =   (TextView) findViewById(R.id.txtVehicleNo);
        txtOldImeiNo    =   (TextView) findViewById(R.id.txtOldImeiNo);
        pimage          = (ImageView) findViewById(R.id.pimage);
       // txtVehicleNo    = (EditText) findViewById(R.id.txtVehicleNo);
        txtHod          = (EditText) findViewById(R.id.txtHod);
        txtLocation     = (EditText) findViewById(R.id.txtLocation);
        txtDate         = (EditText) findViewById(R.id.txtDate);
        txtDeviceId     = (EditText) findViewById(R.id.txtDeviceId);
        btnSubmit       = (Button) findViewById(R.id.btnSubmit);
        btn_getlatlong  = (Button) findViewById(R.id.btn_getlatlong);

        pName.setText("Replace Device");
        dbHelper = new DatabaseHelper(this);
       // dialogOpenForHODList();

        txtDepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpenForDEPOList();
            }
        });
        txtRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpenForRemarksist();
            }
        });

        txtImeiNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpenForIMEIist();
            }
        });
        txtOldImeiNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpenForOLDIMEIist();
            }
        });
        txtInstaDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(NewActivityReplaceDevice.this,
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
                mTimePicker = new TimePickerDialog(NewActivityReplaceDevice.this, new TimePickerDialog.OnTimeSetListener() {
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
        txtVehicleNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOpenSearchVehicleList();

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String depo = txtDepo.getText().toString().trim();
                String division = txtDivision.getText().toString().trim();
                String new_imieno = txtImeiNo.getText().toString().trim();
                String old_imieno = txtOldImeiNo.getText().toString().trim();
                String instantDate = txtInstaDate.getText().toString().trim();
                String instantTime = txtInstaFromTime.getText().toString().trim();
                String remarks = txtRemarks.getText().toString().trim();
                String vehNo = txtVehicleNo.getText().toString().trim();
                String address = tv_latlong.getText().toString().trim();

                if (vehNo.equals("Vehicle No")){
                    Toast.makeText(NewActivityReplaceDevice.this, "Select vehicle no.", Toast.LENGTH_LONG).show();
                }else if (depo.equals("Select Depo")){
                    Toast.makeText(NewActivityReplaceDevice.this, "Select Depo Name.", Toast.LENGTH_LONG).show();
                }else if (old_imieno.equals("Old IMEI No")){
                    Toast.makeText(NewActivityReplaceDevice.this, "Select old IMIE no.", Toast.LENGTH_LONG).show();
                }else if (new_imieno.equals("New IMEI No")){
                    Toast.makeText(NewActivityReplaceDevice.this, "Select new IMIE no.", Toast.LENGTH_LONG).show();
                }else if (remarks.equals("Choose Remarks")){
                    Toast.makeText(NewActivityReplaceDevice.this, "Select remarks", Toast.LENGTH_LONG).show();
                }else if (instantTime.equals("Installation Time")){
                    Toast.makeText(NewActivityReplaceDevice.this, "Set Installation Time", Toast.LENGTH_LONG).show();
                }else if (instantDate.equals("Installation Date")){
                    Toast.makeText(NewActivityReplaceDevice.this, "Set Installation Date", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(tv_latlong.getText().toString().trim())){
                    Toast.makeText(NewActivityReplaceDevice.this, "Address should not be empty", Toast.LENGTH_LONG).show();
                }else{
                    dbHelper.insertReplaceData(new ReplaceDeviceModel(vehNo, depo, division, new_imieno,old_imieno, remarks, instantTime, instantDate, String.valueOf(latitude), String.valueOf(longitude), address));
                    Toast.makeText(NewActivityReplaceDevice.this, "Data saved sucesfully", Toast.LENGTH_LONG).show();

                    txtDepo.setText("Depo Name");
                    txtDivision.setText("");
                    txtImeiNo.setText("New IMEI No");
                    txtOldImeiNo.setText("Old IMEI No");
                    txtVehicleNo.setText("Vehicle No");
                    txtInstaDate.setText("Installation Date");
                    txtInstaFromTime.setText("Installation Time");
                    txtRemarks.setText("Remarks");
                    tv_latlong.setText("Current Location");
                }
            }
        });
    }

    @SuppressLint("NewApi")
    public void getAddress() {
        // create class object

        gps = new GPSTracker(NewActivityReplaceDevice.this);

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
                pdialog = new ProgressDialog(NewActivityReplaceDevice.this);
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
            tv_latlong.setTextColor(getResources().getColor(R.color.black));

        }
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
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(NewActivityReplaceDevice.this);
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
                        Toast.makeText(NewActivityReplaceDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(NewActivityReplaceDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
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


    private void dialogOpenForDEPOList() {

        final ArrayList<String> hod_str = new ArrayList<String>();
        final ArrayList<String> hod_id = new ArrayList<String>();

        hod_id.add("Division 1");hod_id.add("Division 2");hod_id.add("Division 3");hod_id.add("Division 4");hod_id.add("Division 5");
        hod_str.add("DEPO 1");hod_str.add("DEPO 2");hod_str.add("DEPO 3");hod_str.add("DEPO 4");hod_str.add("DEPO 5");

        /*for(int ind = 0 ; ind< dataMechanic.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = dataMechanic.get(ind);
            mechanic_str.add(map.get("KEY_NAME"));
            mechanic_id.add(map.get("KEY_ID"));
        }*/

        final CharSequence[] dialogList = hod_str.toArray(new CharSequence[hod_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(NewActivityReplaceDevice.this);
        builderDialog.setTitle("Select Depo Name");
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
                        Toast.makeText(NewActivityReplaceDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
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
                            txtDepo.setText(stringBuilder );
                            txtDivision.setText(gethod_id[0]+"");
                            Toast.makeText(NewActivityReplaceDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                            // mechType = String.valueOf(stringBuilder);


                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtDepo.setText("Depo Name");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

    }
    private void dialogOpenForOLDIMEIist() {

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
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(NewActivityReplaceDevice.this);
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
                        Toast.makeText(NewActivityReplaceDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
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
                            txtOldImeiNo.setText(stringBuilder);
                            // Toast.makeText(NewActivity_AddDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
                            // mechType = String.valueOf(stringBuilder);


                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtOldImeiNo.setText("Old IMEI No");
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
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(NewActivityReplaceDevice.this);
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
                        Toast.makeText(NewActivityReplaceDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
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
                        txtImeiNo.setText("New IMEI No");
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
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(NewActivityReplaceDevice.this);
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
                        Toast.makeText(NewActivityReplaceDevice.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
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
                        txtRemarks.setText("Remarks");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

    }
}
