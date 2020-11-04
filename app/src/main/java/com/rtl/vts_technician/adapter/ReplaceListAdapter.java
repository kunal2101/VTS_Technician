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
import com.rtl.vts_technician.Pojo.ReplaceDeviceModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by DKC on 04-01-2018.
 */

public class ReplaceListAdapter extends RecyclerView.Adapter<ReplaceListAdapter.MyViewHolder>{
    private List<ReplaceDeviceModel> historyList;
    Context context;

    public ReplaceListAdapter(Context context, List<ReplaceDeviceModel> historyList) {
        this.context = context;
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
        //holder.txt_instalDate.setText(deviceModel.getInstal_date());
        holder.txt_installTo.setText(deviceModel.getInstal_time());

        holder.txt_depo.setText(deviceModel.getDepo());
        holder.txt_division.setText(deviceModel.getDivision());
        holder.txt_imeino.setText(deviceModel.getNew_imieno());
        holder.txt_old_imeino.setText(deviceModel.getOld_imie());
        holder.txt_currenT_add.setText(deviceModel.getAddress());
        holder.txt_problem.setText(deviceModel.getProblem_type());
        holder.txt_lat_loc.setText(deviceModel.getLast_location());
        holder.txt_action.setText(deviceModel.getActon_taken());
        holder.txt_status_new.setText(deviceModel.getStatus());
        holder.countMe.setText(""+(position+1));

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
        private TextView txt_vehNo, txt_instalDate, txt_installTo, txt_status, countMe, txt_depo, txt_imeino, txt_old_imeino, txt_division, txt_currenT_add,
        txt_lat_loc, txt_status_new, txt_problem, txt_action;
        ImageView img;


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
            img                 = (ImageView) itemView.findViewById(R.id.img);
            txt_lat_loc         = (TextView) itemView.findViewById(R.id.txt_lat_loc);
            txt_status_new         = (TextView) itemView.findViewById(R.id.txt_status_new);
            txt_problem         = (TextView) itemView.findViewById(R.id.txt_problem);
            txt_action         = (TextView) itemView.findViewById(R.id.txt_action);
        }
    }
}
