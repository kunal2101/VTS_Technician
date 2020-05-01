package com.rtl.vts_technician;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin, btnForget, btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kk_activity_login);

        btnLogin        = (Button)findViewById(R.id.btnLogin);
        btnForget       = (Button)findViewById(R.id.btnForget);
        btnReg          = (Button) findViewById(R.id.btnReg);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, NewMainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forget_password();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Registration_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void forget_password() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(LoginActivity.this);
        //alertDialogBuilder.setTitle("Reset Password");
        alertDialogBuilder.setTitle(Html.fromHtml("<font color='#18B086'>Reset Password</font>"));

        final View itemView = LayoutInflater.from(LoginActivity.this)
                .inflate(R.layout.activity_forgot__password, null, false);
        alertDialogBuilder.setView(itemView);

        alertDialogBuilder.setPositiveButton(getResources().getText(R.string.reset),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing it will be overridden
                    }
                });

        alertDialogBuilder.setNegativeButton(getResources().getText(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        final android.app.AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });

        alertDialog.show();

    }
}
