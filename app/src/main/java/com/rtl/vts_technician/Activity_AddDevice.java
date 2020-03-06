package com.rtl.vts_technician;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Activity_AddDevice extends AppCompatActivity {
    ImageView pimage;
    TextView pName;
    EditText txtDeviceId, txtDate, txtLocation, txtHod, txtVehicleNo;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add_device);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        pName           = (TextView) findViewById(R.id.pname);
        pimage          = (ImageView) findViewById(R.id.pimage);
        txtVehicleNo    = (EditText) findViewById(R.id.txtVehicleNo);
        txtHod          = (EditText) findViewById(R.id.txtHod);
        txtLocation     = (EditText) findViewById(R.id.txtLocation);
        txtDate         = (EditText) findViewById(R.id.txtDate);
        txtDeviceId     = (EditText) findViewById(R.id.txtDeviceId);
        btnSubmit       = (Button) findViewById(R.id.btnSubmit);

        pName.setText("Entry Form");
        dialogOpenForHODList();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void dialogOpenForHODList() {

        final ArrayList<String> hod_str = new ArrayList<String>();
        final ArrayList<String> hod_id = new ArrayList<String>();

        hod_id.add("H1");hod_id.add("H2");hod_id.add("H3");hod_id.add("H4");hod_id.add("H5");
        hod_str.add("HOD Name1");hod_str.add("HOD Name2");hod_str.add("HOD Name3");hod_str.add("HOD Name4");hod_str.add("HOD Name5");

        /*for(int ind = 0 ; ind< dataMechanic.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = dataMechanic.get(ind);
            mechanic_str.add(map.get("KEY_NAME"));
            mechanic_id.add(map.get("KEY_ID"));
        }*/

        final CharSequence[] dialogList = hod_str.toArray(new CharSequence[hod_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Activity_AddDevice.this);
        builderDialog.setTitle("Select HOD Name");
        int count = dialogList.length;
        boolean[] is_checked = new boolean[count];

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

                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        ArrayList yourlist = new ArrayList();
                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {
                                String getMechanic_str = list.getItemAtPosition(i).toString();
                                int hod_index = hod_str.indexOf(getMechanic_str);
                                String gethod_id = hod_id.get(hod_index);

                                if (stringBuilder.length() > 0) stringBuilder.append(",");
                                stringBuilder.append(getMechanic_str);
                                //dialogOpenForProjectList();
                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder.toString().trim().equals("")) {

                            txtHod.setText("");
                            stringBuilder.setLength(0);

                        } else {
                            txtHod.setText(stringBuilder);
                            // mechType = String.valueOf(stringBuilder);


                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtHod.setText("");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

    }
}
