package com.rtl.vts_technician.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rtl.vts_technician.R;
import com.rtl.vts_technician.model.ReplaceDeviceModel;

import java.util.List;

/**
 * Created by DKC on 04-01-2018.
 */

public class New_ReplaceHistoryListAdapter extends RecyclerView.Adapter<New_ReplaceHistoryListAdapter.MyViewHolder>{
    private List<ReplaceDeviceModel> historyList;

    public New_ReplaceHistoryListAdapter(List<ReplaceDeviceModel> historyList) {
        this.historyList = historyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_row_repalce_history_listview,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReplaceDeviceModel deviceModel = historyList.get(position);

        holder.txt_vehNo.setText(deviceModel.getVeh_no());
        holder.txt_instalDate.setText(deviceModel.getInstal_date());
        holder.txt_installTo.setText(deviceModel.getInstal_time());

        holder.txt_depo.setText(deviceModel.getDepo());
        holder.txt_division.setText(deviceModel.getDivision());
        holder.txt_imeino.setText(deviceModel.getNew_imieno());
        holder.txt_old_imeino.setText(deviceModel.getOld_imie());
        holder.txt_currenT_add.setText(deviceModel.getAddress());
        holder.countMe.setText(""+(position+1));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_vehNo, txt_instalDate, txt_installTo, txt_status, countMe, txt_depo, txt_imeino, txt_old_imeino, txt_division, txt_currenT_add;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_vehNo        = (TextView) itemView.findViewById(R.id.txt_vehNo);
            txt_instalDate      = (TextView) itemView.findViewById(R.id.txt_instalDate);
            txt_installTo       = (TextView) itemView.findViewById(R.id.txt_installTo);
            txt_status          = (TextView) itemView.findViewById(R.id.txt_status);
            countMe             = (TextView) itemView.findViewById(R.id.countMe);
            txt_depo         = (TextView) itemView.findViewById(R.id.txt_depo);
            txt_imeino         = (TextView) itemView.findViewById(R.id.txt_imeino);
            txt_old_imeino         = (TextView) itemView.findViewById(R.id.txt_old_imeino);
            txt_division         = (TextView) itemView.findViewById(R.id.txt_division);
            txt_currenT_add         = (TextView) itemView.findViewById(R.id.txt_currenT_add);
        }
    }
}
