package com.rtl.vts_technician.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rtl.vts_technician.Activity.AdminLogin;
import com.rtl.vts_technician.Activity.Technician_Login;
import com.rtl.vts_technician.R;

import static android.content.ContentValues.TAG;

public class LoginType_fragment extends Fragment {

    private Button btnTech, btnAdmin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_type_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated Call");

        btnAdmin = view.findViewById(R.id.btnAdmin);
        btnTech  = view.findViewById(R.id.btnTech);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminLogin.class);
                startActivity(intent);
            }
        });

        btnTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Technician_Login.class);
                startActivity(intent);
            }
        });
    }
}