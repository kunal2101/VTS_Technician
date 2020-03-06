package com.rtl.vts_technician;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import java.io.ByteArrayOutputStream;

public class ProfileActivity extends AppCompatActivity {

    Button btnEditProfile, btnChangePassword;
   // PreferenceHelper preferenceHelper;

    TextView name_txt, email_txt, mobile_txt, address_txt,pName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bars);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        pName           = (TextView) findViewById(R.id.pname);
        pName.setText("Profile");

        //preferenceHelper = new PreferenceHelper(ProfileActivity.this);

        btnEditProfile    = (Button)findViewById(R.id.btnEditProfile);
        btnChangePassword = (Button)findViewById(R.id.btnChangePassword);
        name_txt          = (TextView)findViewById(R.id.name_txt);
        email_txt         = (TextView)findViewById(R.id.email_txt);
        mobile_txt        = (TextView)findViewById(R.id.mobile_txt);
        address_txt       = (TextView)findViewById(R.id.address_txt);
       // profileuserPic    = (com.rapl.utils.CircleImageview)findViewById(R.id.profileuserPic);

     /*   if(preferenceHelper.getProfileImage() == null){
            Toast.makeText(ProfileActivity.this,"Uplaod Your Image",Toast.LENGTH_LONG).show();
        }else {
            String st_image = preferenceHelper.getProfileImage();
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] imageBytes = baos.toByteArray();
                imageBytes = Base64.decode(st_image, Base64.DEFAULT);
                Bitmap decodedimage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                profileuserPic.setImageBitmap(decodedimage);
            }catch (Exception e){
                e.printStackTrace();
            }*/



        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                intent.putExtra("name",name_txt.getText().toString());
                intent.putExtra("mobile",mobile_txt.getText().toString());
                intent.putExtra("email",email_txt.getText().toString());
                intent.putExtra("address",address_txt.getText().toString());
                startActivity(intent);
                finish();
            }
        });

/*
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChangepasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
*/



    }


}
