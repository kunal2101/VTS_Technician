package com.rtl.vts_technician.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rtl.vts_technician.R;
import com.rtl.vts_technician.model.ApprovedDeviceModel;
import java.util.List;

/**
 * Created by DKC on 04-01-2018.
 */

public class ApprovedDeviceListAdapter extends RecyclerView.Adapter<ApprovedDeviceListAdapter.MyDeviceHolder> {
    private List<ApprovedDeviceModel> approvedList;

    public ApprovedDeviceListAdapter(List<ApprovedDeviceModel> approvedList) {
        this.approvedList = approvedList;
    }

    @Override
    public MyDeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_approved_list,parent, false);
        return new MyDeviceHolder(v);
    }

    @Override
    public void onBindViewHolder(MyDeviceHolder holder, int position) {
        ApprovedDeviceModel approvedDeviceModel = approvedList.get(position);

        holder.txt_date.setText(approvedDeviceModel.getSubmitionDate());
        holder.txt_deviceId.setText(approvedDeviceModel.getDeviceId());
        holder.txt_status.setText(approvedDeviceModel.getStatus());
        holder.countMe.setText(""+(position+1));

    }

    @Override
    public int getItemCount() {
        return approvedList.size();
    }

    public class MyDeviceHolder extends RecyclerView.ViewHolder {
        private TextView txt_deviceId, txt_date, txt_status, countMe;
        public MyDeviceHolder(View itemView) {
            super(itemView);

            txt_deviceId        = (TextView) itemView.findViewById(R.id.txt_deviceId);
            txt_date            = (TextView) itemView.findViewById(R.id.txt_date);
            txt_status          = (TextView) itemView.findViewById(R.id.txt_status);
            countMe             = (TextView) itemView.findViewById(R.id.countMe);
        }
    }
}
