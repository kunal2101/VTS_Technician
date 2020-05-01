package com.rtl.vts_technician.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rtl.vts_technician.R;
import com.rtl.vts_technician.model.DeviceModel;
import com.rtl.vts_technician.model.ModelDownVehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DKC on 04-01-2018.
 */

public class DownVehicleListAdapter extends RecyclerView.Adapter<DownVehicleListAdapter.MyViewHolder> implements Filterable {
    private List<ModelDownVehicle> historyList;
    private List<ModelDownVehicle> exampleListFull;

    public DownVehicleListAdapter( Context mContext, List<ModelDownVehicle> exampleList) {
        this.historyList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_down_vehicel,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ModelDownVehicle deviceModel = historyList.get(position);

        holder.txtVehNo.setText ( deviceModel.getVehicleNo () );
        holder.txtAddress.setText ( deviceModel.getComAddess () );
        holder.txtDatetime.setText ( deviceModel.getDateTime () );
        holder.txtDepo.setText ( deviceModel.getDepoName () );
        holder.txtImeiNo.setText ( deviceModel.getImeiNo () );
//        holder.txt_date.setText(deviceModel.getIssuedDate());
//        holder.txt_deviceId.setText(deviceModel.getDeviceId());
//        holder.txt_instalDate.setText(deviceModel.getInstallationDate());
//        holder.txt_installTo.setText(deviceModel.getInstalledTo());
//        final String status = deviceModel.getStatus();
//        if (status.equalsIgnoreCase("Inactive"))
//        {
//            holder.txt_status.setTextColor(Color.parseColor("#b20e0f"));
//            holder.txt_status.setText(deviceModel.getStatus());
//        }else{
//            holder.txt_status.setTextColor(Color.parseColor("#ff169c1f"));
//            holder.txt_status.setText(deviceModel.getStatus());
//        }
//       // holder.txt_status.setText(deviceModel.getStatus());
//        holder.txt_manager.setText(deviceModel.getManagerName());
//        holder.countMe.setText(""+(position+1));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    @Override
    public Filter getFilter () {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelDownVehicle> filteredList = new ArrayList<> ();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ModelDownVehicle item : exampleListFull) {
                    if (item.getVehicleNo ().toLowerCase ().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            historyList.clear();
            historyList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtVehNo, txtAddress, txtDatetime, txtImeiNo, txtDepo;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtVehNo        = (TextView) itemView.findViewById(R.id.txtVehNo);
            txtAddress            = (TextView) itemView.findViewById(R.id.txtAddress);
            txtDatetime      = (TextView) itemView.findViewById(R.id.txtDatetime);
            txtImeiNo       = (TextView) itemView.findViewById(R.id.txtImeiNo);
            txtDepo          = (TextView) itemView.findViewById(R.id.txtDepo);
        }
    }
}
