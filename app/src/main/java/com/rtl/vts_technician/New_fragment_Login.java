package com.rtl.vts_technician;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.content.ContentValues.TAG;

public class New_fragment_Login extends Fragment {
    Button btnLogin, btnForget, btnReg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.kk_activity_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        btnLogin        = (Button)view.findViewById(R.id.btnLogin);
        btnForget       = (Button)view.findViewById(R.id.btnForget);
        btnReg          = (Button)view. findViewById(R.id.btnReg);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityTest.class);
                startActivity(intent);
                //getActivity().finish();
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
                Intent intent = new Intent(getActivity(), Registration_Activity.class);
                startActivity(intent);
               // getActivity().finish();
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


    }




    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "Onstart Call ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "OnResume Call ");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "OnStop Call ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "OnPause Call ");
    }
    private void forget_password() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(getActivity());
        //alertDialogBuilder.setTitle("Reset Password");
        alertDialogBuilder.setTitle(Html.fromHtml("<font color='#18B086'>Reset Password</font>"));

        final View itemView = LayoutInflater.from(getActivity())
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


