package com.rtl.vts_technician;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rtl.vts_technician.Database.DatabaseHelper;
import com.rtl.vts_technician.adapter.De_InstallationListAdapter;
import com.rtl.vts_technician.model.De_installDeviceModel;

import java.util.ArrayList;
import java.util.List;


public class De_InstallationFragment extends Fragment {
    ImageView pimage;
    TextView pName;
    RecyclerView historyRecyclerView;
    private De_InstallationListAdapter historyListAdapter;
    private List<De_installDeviceModel> historyList = new ArrayList<>();
    DatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_de__installation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        historyRecyclerView     = (RecyclerView)view. findViewById( R.id.de_installRecyclerView);
        dbHelper = new DatabaseHelper (getActivity());

        historyList = dbHelper.getDe_Install_Arry_list();

        historyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        historyRecyclerView.setLayoutManager(mLayoutManager);

        historyListAdapter = new De_InstallationListAdapter(historyList);
        historyRecyclerView.setAdapter(historyListAdapter);
        // prepareHistoryData();

    }
}
