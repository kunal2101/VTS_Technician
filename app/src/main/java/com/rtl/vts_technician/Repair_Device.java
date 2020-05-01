package com.rtl.vts_technician;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class Repair_Device extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView pimage;
    TextView pName;
    Spinner problemType;
    LinearLayout upper_row_three;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_device);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        pName           = (TextView) findViewById(R.id.pname);
        pimage          = (ImageView) findViewById(R.id.pimage);
        problemType     = (Spinner) findViewById(R.id.spi_problem);
        upper_row_three = (LinearLayout) findViewById(R.id.upper_row_three);
        btnSubmit       = (Button) findViewById(R.id.btnSubmit);

        pName.setText("Repair Device");

        // Spinner click listener
        problemType.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Sim is not working");
        categories.add("Loose Connection");
        categories.add("Damage Device");
        categories.add("Other");

        // Creating adapter for problemType
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to problemType
        problemType.setAdapter(dataAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Repair_Device.this, MainActivity.class);
                startActivity(in);
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String item = adapterView.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        if (item.equalsIgnoreCase("Other")){
            upper_row_three.setVisibility(View.VISIBLE);
        }else {
            upper_row_three.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
