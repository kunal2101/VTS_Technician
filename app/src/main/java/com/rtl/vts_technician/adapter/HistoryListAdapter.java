package com.rtl.vts_technician.adapter;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rtl.vts_technician.R;
import com.rtl.vts_technician.model.DeviceModel;

import java.util.List;

/**
 * Created by DKC on 04-01-2018.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.MyViewHolder>{
    private List<DeviceModel> historyList;

    public HistoryListAdapter(List<DeviceModel> historyList) {
        this.historyList = historyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history_listview,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DeviceModel deviceModel = historyList.get(position);

        holder.txt_date.setText(deviceModel.getIssuedDate());
        holder.txt_deviceId.setText(deviceModel.getDeviceId());
        holder.txt_instalDate.setText(deviceModel.getInstallationDate());
        holder.txt_installTo.setText(deviceModel.getInstalledTo());
        final String status = deviceModel.getStatus();
        if (status.equalsIgnoreCase("Inactive"))
        {
            holder.txt_status.setTextColor(Color.parseColor("#b20e0f"));
            holder.txt_status.setText(deviceModel.getStatus());
        }else{
            holder.txt_status.setTextColor(Color.parseColor("#ff169c1f"));
            holder.txt_status.setText(deviceModel.getStatus());
        }
       // holder.txt_status.setText(deviceModel.getStatus());
        holder.txt_manager.setText(deviceModel.getManagerName());
        holder.countMe.setText(""+(position+1));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_deviceId, txt_date, txt_instalDate, txt_installTo, txt_status, countMe, txt_manager;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_deviceId        = (TextView) itemView.findViewById(R.id.txt_deviceId);
            txt_date            = (TextView) itemView.findViewById(R.id.txt_date);
            txt_instalDate      = (TextView) itemView.findViewById(R.id.txt_instalDate);
            txt_installTo       = (TextView) itemView.findViewById(R.id.txt_installTo);
            txt_status          = (TextView) itemView.findViewById(R.id.txt_status);
            countMe             = (TextView) itemView.findViewById(R.id.countMe);
            txt_manager         = (TextView) itemView.findViewById(R.id.txt_manager);
        }
    }
}
