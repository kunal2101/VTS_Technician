package com.rtl.vts_technician;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rtl.vts_technician.Database.DatabaseHelper;
import com.rtl.vts_technician.adapter.New_HistoryListAdapter;
import com.rtl.vts_technician.model.NewInstallDeviceModel;

import java.util.List;
import static android.content.ContentValues.TAG;

public class New_fragment_History_New extends Fragment {
    RecyclerView historyRecyclerView;
    private New_HistoryListAdapter historyListAdapter;
    private List<NewInstallDeviceModel> historyList;
    DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_activity_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        historyRecyclerView     = (RecyclerView)view. findViewById(R.id.historyRecyclerView);

        historyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        historyRecyclerView.setLayoutManager(mLayoutManager);

        dbHelper = new DatabaseHelper(getActivity());

        historyList = dbHelper.getInstall_Arry_list();

        historyListAdapter = new New_HistoryListAdapter(historyList);
        historyRecyclerView.setAdapter(historyListAdapter);
        //prepareHistoryData();

    }
   /* private void prepareHistoryData() {

        DeviceModel deviceModel = new DeviceModel("RTL1XX1234", "11:20", "01-01-2018", "12:25", "12-12-2017", "Depo 2");
        historyList.add(deviceModel);

        deviceModel = new DeviceModel("RTL1XX1734", "13:47", "01-01-2018", "15:00", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

        deviceModel = new DeviceModel("RTL1XX1984", "09:12", "01-01-2018", "10:58", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

        deviceModel = new DeviceModel("RTL1XX1291", "15:00", "01-01-2018", "14:16", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

        deviceModel = new DeviceModel("RTL1XX1004", "14:41", "01-01-2018", "14:59", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

        deviceModel = new DeviceModel("RTL1XX1114", "11:25", "01-01-2018", "12:50", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

        deviceModel = new DeviceModel("RTL1XX1237", "16:12", "01-01-2018", "18:18", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

        deviceModel = new DeviceModel("RTL1XX1200", "12:00", "01-01-2018", "14:05", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

        deviceModel = new DeviceModel("RTL1XX1134", "18:14", "01-01-2018", "18:55", "12-12-2017", "Depo 3");
        historyList.add(deviceModel);

        historyListAdapter.notifyDataSetChanged();

    }*/

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

}
