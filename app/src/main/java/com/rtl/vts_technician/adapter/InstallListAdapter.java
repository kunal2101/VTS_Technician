package com.rtl.vts_technician.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rtl.vts_technician.Constants.Utility;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Pojo.NewInstallDeviceModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by DKC on 04-01-2018.
 */

public class InstallListAdapter extends RecyclerView.Adapter<InstallListAdapter.MyViewHolder>{
    private List<NewInstallDeviceModel> historyList;
    Context context;

    public InstallListAdapter(Context context, List<NewInstallDeviceModel> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_row_history_listview,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewInstallDeviceModel deviceModel = historyList.get(position);


        holder.txt_installTo.setText(deviceModel.getInstal_time());
        holder.txt_vehno.setText(deviceModel.getVeh_no());
        holder.txt_imeino.setText(deviceModel.getImieno());
        holder.txt_division.setText(deviceModel.getDivision());
        holder.txt_currenT_add.setText(deviceModel.getAddress());
        holder.countMe.setText(""+(position+1));
        holder.txt_depo.setText(deviceModel.getDepo());

       SimpleDateFormat HHmmFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        SimpleDateFormat hhmmampmFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        String arrTime  = Utility.parseDate(deviceModel.getInstal_date(), HHmmFormat, hhmmampmFormat);

        holder.txt_instalDate.setText(arrTime);

        Glide.with(context)
                .load(deviceModel.getImageString())
                .error(R.drawable.no_privew)
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_vehno, txt_date, txt_instalDate, txt_installTo, txt_status, countMe, txt_depo, txt_imeino, txt_division, txt_currenT_add;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_vehno        = (TextView) itemView.findViewById(R.id.txt_vehno);
            txt_date            = (TextView) itemView.findViewById(R.id.txt_date);
            txt_instalDate      = (TextView) itemView.findViewById(R.id.txt_instalDate);
            txt_installTo       = (TextView) itemView.findViewById(R.id.txt_installTo);
            txt_status          = (TextView) itemView.findViewById(R.id.txt_status);
            countMe             = (TextView) itemView.findViewById(R.id.countMe);
            txt_depo            = (TextView) itemView.findViewById(R.id.txt_depo);
            txt_imeino          = (TextView) itemView.findViewById(R.id.txt_imeino);
            txt_division        = (TextView) itemView.findViewById(R.id.txt_division);
            txt_currenT_add     = (TextView) itemView.findViewById(R.id.txt_currenT_add);
            img                 = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
