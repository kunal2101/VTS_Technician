package com.rtl.vts_technician.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rtl.vts_technician.R;
import com.rtl.vts_technician.model.MaintainanceDeviceModel;

import java.util.List;

/**
 * Created by DKC on 04-01-2018.
 */

public class New_MainteHistoryListAdapter extends RecyclerView.Adapter<New_MainteHistoryListAdapter.MyViewHolder>{
    private List<MaintainanceDeviceModel> historyList;

    public New_MainteHistoryListAdapter(List<MaintainanceDeviceModel> historyList) {
        this.historyList = historyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_row_repalce_maintenance,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MaintainanceDeviceModel deviceModel = historyList.get(position);

       // holder.txt_date.setText(deviceModel.getIssuedDate());
        holder.txt_vehno.setText(deviceModel.getVeh_no());
        holder.txt_instalDate.setText(deviceModel.getInstal_date());
        holder.txt_installTo.setText(deviceModel.getInstal_time());

        holder.txt_depo.setText(deviceModel.getDepo());
        holder.txt_division.setText(deviceModel.getDivision());
        holder.txt_imeino.setText(deviceModel.getImieno());
        holder.txt_problem.setText(deviceModel.getProblem_type());
        holder.txt_lat_loc.setText(deviceModel.getLast_location());
        holder.txt_action.setText(deviceModel.getActon_taken());
        holder.txt_status_new.setText(deviceModel.getStatus());
        holder.txt_currenT_add.setText(deviceModel.getAddress());
        holder.countMe.setText(""+(position+1));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_vehno, txt_date, txt_instalDate, txt_installTo, txt_status, countMe, txt_depo, txt_imeino, txt_division,
                txt_currenT_add, txt_lat_loc, txt_status_new, txt_problem, txt_action;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_vehno        = (TextView) itemView.findViewById(R.id.txt_vehno);
            txt_date            = (TextView) itemView.findViewById(R.id.txt_date);
            txt_instalDate      = (TextView) itemView.findViewById(R.id.txt_instalDate);
            txt_installTo       = (TextView) itemView.findViewById(R.id.txt_installTo);
            txt_status          = (TextView) itemView.findViewById(R.id.txt_status);
            countMe             = (TextView) itemView.findViewById(R.id.countMe);
            txt_depo         = (TextView) itemView.findViewById(R.id.txt_depo);

            txt_imeino         = (TextView) itemView.findViewById(R.id.txt_imeino);
            txt_division         = (TextView) itemView.findViewById(R.id.txt_depo);
            txt_currenT_add         = (TextView) itemView.findViewById(R.id.txt_currenT_add);
            txt_lat_loc         = (TextView) itemView.findViewById(R.id.txt_lat_loc);
            txt_status_new         = (TextView) itemView.findViewById(R.id.txt_status_new);
            txt_problem         = (TextView) itemView.findViewById(R.id.txt_problem);
            txt_action         = (TextView) itemView.findViewById(R.id.txt_action);

        }
    }
}
