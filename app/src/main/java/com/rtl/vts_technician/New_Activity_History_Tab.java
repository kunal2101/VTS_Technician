package com.rtl.vts_technician;

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

import com.rtl.vts_technician.adapter.TabAdapter;

public class New_Activity_History_Tab extends AppCompatActivity {
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
        adapter.addFragment(new New_fragment_History_New(), "New Device History");
        adapter.addFragment(new New_fragment_Replace_dev(), "Replace Device History");

        adapter.addFragment(new New_fragment_Maintenance(), "Maintenance Device History");
        adapter.addFragment(new De_InstallationFragment(), "Device De-Installation History");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
