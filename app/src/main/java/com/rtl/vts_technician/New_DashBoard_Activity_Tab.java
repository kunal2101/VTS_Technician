package com.rtl.vts_technician;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtl.vts_technician.adapter.TabAdapter;

public class New_DashBoard_Activity_Tab extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView pimage;
    TextView pName;
    private ImageView img_back;
    private TextView txt_title;
    String  stopName;
    int stopId;
    private int[] tabIcons = {
            R.drawable.ic_action_raw_data,
            R.drawable.ic_action_dashboard
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new___raw_dash__tab);
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
        adapter.addFragment(new New_fragment_RawData(), "Raw Data Check");
        adapter.addFragment(new New_fragment_Login(), "Technician Dashboard");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);

    }

}
