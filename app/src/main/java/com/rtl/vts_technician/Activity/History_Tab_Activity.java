package com.rtl.vts_technician.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtl.vts_technician.Fragment.De_InstallationHistoryFragment;
import com.rtl.vts_technician.Fragment.InstalledHistoryFragment;
import com.rtl.vts_technician.Fragment.MaintenanceHistoryFragment;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Fragment.ReplaceHistoryFragment;
import com.rtl.vts_technician.Adapter.TabAdapter;

public class History_Tab_Activity extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView pimage;
    TextView pName;
    private ImageView img_back;
    private TextView txt_title;
    String  stopName;
    int stopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new___history__tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        pName                   = (TextView) findViewById(R.id.pname);
        pimage                  = (ImageView) findViewById(R.id.pimage);

        pName.setText("Device History");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new InstalledHistoryFragment(), "New Device Installation");
        adapter.addFragment(new De_InstallationHistoryFragment(), "Device De-Installation");

        adapter.addFragment(new ReplaceHistoryFragment(), "Replace Device");

        adapter.addFragment(new MaintenanceHistoryFragment(), "Maintenance Device");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
