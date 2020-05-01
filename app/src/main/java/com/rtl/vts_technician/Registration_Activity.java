package com.rtl.vts_technician;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Registration_Activity extends AppCompatActivity {

    private Button btnRegister, btngoback;
    private EditText txtUser, txtMobile, txtPassword, txtEid;
    TextView txtDepo,txtDivision;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_registration);

        btngoback       = (Button) findViewById( R.id.btngoback);
        btnRegister     = (Button) findViewById( R.id.btnRegister);
        txtUser         = (EditText) findViewById( R.id.txtUser);
        txtMobile        = (EditText) findViewById( R.id.txtMobile);
        txtPassword   = (EditText) findViewById( R.id.txtPassword);
        txtEid          = (EditText) findViewById( R.id.txtEid);
        txtDepo         = (TextView) findViewById( R.id.txtDepo);
        txtDivision     =  (TextView) findViewById( R.id.txtDivision);

        txtDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpenForDivisionList();
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent( Registration_Activity.this, OTPVerifyActivity.class);
                startActivity(in);
                finish();

            }
        });

        btngoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent( Registration_Activity.this, LoginActivity.class);
                startActivity(in);
                finish();
            }
        });

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
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder( Registration_Activity.this);
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
                        Toast.makeText( Registration_Activity.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
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
                            Toast.makeText( Registration_Activity.this, getMechanic_str[0] +"----------"+ gethod_id[0], Toast.LENGTH_SHORT).show();
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

}
