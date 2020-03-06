package com.rtl.vts_technician;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.icu.lang.UCharacterEnums;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class Registration_Activity extends AppCompatActivity {

    private Button btnRegister, btngoback;
    private EditText txtUser, txtEmail, txtDegination,txtHod, txtEid, txtProject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btngoback       = (Button) findViewById(R.id.btngoback);
        btnRegister     = (Button) findViewById(R.id.btnRegister);
        txtProject      = (EditText) findViewById(R.id.txtProject);
        txtUser         = (EditText) findViewById(R.id.txtUser);
        txtEmail        = (EditText) findViewById(R.id.txtEmail);
        txtDegination   = (EditText) findViewById(R.id.txtDegination);
        txtHod          = (EditText) findViewById(R.id.txtHod);
        txtEid          = (EditText) findViewById(R.id.txtEid);

        dialogOpenForHODList();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Registration_Activity.this, MainActivity.class);
                startActivity(in);
                finish();

            }
        });

        btngoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Registration_Activity.this, LoginActivity.class);
                startActivity(in);
                finish();
            }
        });

        txtHod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpenForHODList();
            }
        });

        txtProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpenForProjectList();
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
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Registration_Activity.this);
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
                                dialogOpenForProjectList();
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


    private void dialogOpenForProjectList() {

        final ArrayList<String> pro_str = new ArrayList<String>();
        final ArrayList<String> pro_id = new ArrayList<String>();

        pro_id.add("H1");pro_id.add("H2");pro_id.add("H3");pro_id.add("H4");pro_id.add("H5");
        pro_str.add("Depo 1");pro_str.add("Depo 2");pro_str.add("Depo 3");pro_str.add("Depo 4");pro_str.add("Depo 5");

        /*for(int ind = 0 ; ind< dataMechanic.size(); ind++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = dataMechanic.get(ind);
            mechanic_str.add(map.get("KEY_NAME"));
            mechanic_id.add(map.get("KEY_ID"));
        }*/

        final CharSequence[] dialogList = pro_str.toArray(new CharSequence[pro_str.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Registration_Activity.this);
        builderDialog.setTitle("Select Depo Name");
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
                                int hod_index = pro_str.indexOf(getMechanic_str);
                                String gethod_id = pro_id.get(hod_index);

                                if (stringBuilder.length() > 0) stringBuilder.append(",");
                                stringBuilder.append(getMechanic_str);

                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder.toString().trim().equals("")) {

                            txtProject.setText("");
                            stringBuilder.setLength(0);

                        } else {
                            txtProject.setText(stringBuilder);
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
