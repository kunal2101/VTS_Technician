package com.rtl.vts_technician.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rtl.vts_technician.R;
import com.rtl.vts_technician.model.De_installDeviceModel;

import java.util.List;

/**
 * Created by DKC on 04-01-2018.
 */

public class De_InstallationListAdapter extends RecyclerView.Adapter<De_InstallationListAdapter.MyViewHolder>{
    private List<De_installDeviceModel> historyList;

    public De_InstallationListAdapter ( List<De_installDeviceModel> historyList) {
        this.historyList = historyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.row_deinstall_listview,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        De_installDeviceModel deviceModel = historyList.get(position);

        //holder.txt_date.setText(deviceModel.getInstal_date());
        //holder.txt_deviceId.setText(deviceModel.getDeviceId());
        holder.txt_instalDate.setText(deviceModel.getInstal_date());
        holder.txt_installTo.setText(deviceModel.getInstal_time());
      /*  final String status = deviceModel.get;
        if (status.equalsIgnoreCase("Inactive"))
        {
            holder.txt_status.setTextColor(Color.parseColor("#b20e0f"));
            holder.txt_status.setText(deviceModel.getStatus());
        }else{
            holder.txt_status.setTextColor(Color.parseColor("#ff169c1f"));
            holder.txt_status.setText(deviceModel.getStatus());
        }*/
       // holder.txt_status.setText(deviceModel.getStatus());
        holder.txt_vehno.setText(deviceModel.getVeh_no());
        holder.txt_imeino.setText(deviceModel.getImieno());
        holder.txt_division.setText(deviceModel.getDivision());
        holder.txt_currenT_add.setText(deviceModel.getAddress());
        holder.countMe.setText(""+(position+1));
        holder.txt_depo.setText(deviceModel.getDepo());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_vehno, txt_date, txt_instalDate, txt_installTo, txt_status, countMe, txt_depo, txt_imeino, txt_division, txt_currenT_add;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_vehno        = (TextView) itemView.findViewById( R.id.txt_vehno);
            txt_date            = (TextView) itemView.findViewById( R.id.txt_date);
            txt_instalDate      = (TextView) itemView.findViewById( R.id.txt_instalDate);
            txt_installTo       = (TextView) itemView.findViewById( R.id.txt_installTo);
            txt_status          = (TextView) itemView.findViewById( R.id.txt_status);
            countMe             = (TextView) itemView.findViewById( R.id.countMe);
            txt_depo         = (TextView) itemView.findViewById( R.id.txt_depo);
            txt_imeino         = (TextView) itemView.findViewById( R.id.txt_imeino);
            txt_division         = (TextView) itemView.findViewById( R.id.txt_division);
            txt_currenT_add         = (TextView) itemView.findViewById( R.id.txt_currenT_add);
        }
    }
}
