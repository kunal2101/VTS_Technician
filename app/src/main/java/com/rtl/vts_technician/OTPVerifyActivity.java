package com.rtl.vts_technician;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OTPVerifyActivity extends AppCompatActivity {
    EditText otpView;
    String otpValue;
    Button txt_verify, resendotp;
    TextView timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_otp_verify);

        otpView = (EditText) findViewById( R.id.otpView);
        txt_verify = (Button) findViewById( R.id.txt_verify);
        resendotp = (Button) findViewById( R.id.resendotp);
        timeText = (TextView) findViewById( R.id.timeText);


        resendotp.setBackgroundResource( R.drawable.bg_disabled_button);
        resendotp.setEnabled(false);
        txt_verify.setEnabled(false);

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeText.setText("Time remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                timeText.setText("Click on Resend button to re-generate the OTP !");
                resendotp.setBackgroundResource( R.drawable.rect_blue_cornor);

                resendotp.setEnabled(true);
            }

        }.start();


    final TextWatcher mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() < 4) {
                txt_verify.setBackgroundResource( R.drawable.bg_disabled_button);
                txt_verify.setEnabled(false);


            } else if (s.length() == 4) {
                txt_verify.setBackgroundResource( R.drawable.rect_blue_cornor);
                txt_verify.setEnabled(true);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

        otpView.addTextChangedListener(mTextEditorWatcher);

        txt_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent( OTPVerifyActivity.this, LoginActivity.class);
                startActivity(in);
                finish();
            }
        });

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
