package com.rtl.vts_technician.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.rtl.vts_technician.Activity.RawDataResultActivity;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Pojo.ModelDownVehicle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by DKC on 04-01-2018.
 */

public class DownVehicleListAdapter extends RecyclerView.Adapter<DownVehicleListAdapter.MyViewHolder> implements Filterable {
    private List<ModelDownVehicle> historyList;
    private List<ModelDownVehicle> exampleListFull;
    private  Context    mContexts ;

    public DownVehicleListAdapter( Context mContext, List<ModelDownVehicle> exampleList) {
        this.historyList = exampleList;
        mContexts       = mContext;
        exampleListFull = new ArrayList<>(exampleList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_down_vehicel,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ModelDownVehicle deviceModel = historyList.get(position);

        holder.txtVehNo.setText (deviceModel.getVehicleNo ());
        holder.txtAddress.setText (deviceModel.getComAddess ());
        holder.txtDatetime.setText (deviceModel.getDateTime ());
        //holder.txtDepo.setText (deviceModel.getDepoName ());
        holder.txtImeiNo.setText (deviceModel.getImeiNo ());
        holder.txtstatus.setText("Status: "+ deviceModel.getStatus());
        holder.txtsr.setText(""+(position+1));
        holder.txtdepno.setText("Depo: " + deviceModel.getDepoName());

        holder.item_lin_top.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {

                Calendar now = Calendar.getInstance();
                int monthOfYear = now.get(Calendar.MONTH);
                int dayOfMonth = now.get(Calendar.DAY_OF_MONTH);
                String monthString = (++monthOfYear) < 10 ? "0"+(monthOfYear) : ""+(monthOfYear);
                String dayString = dayOfMonth < 10 ? "0"+dayOfMonth : ""+dayOfMonth;
                String  current_date = dayString + "-" + monthString + "-" + now.get(Calendar.YEAR);

                Intent inty = new Intent(mContexts, RawDataResultActivity.class);
                inty.putExtra("imeino", deviceModel.getImeiNo()+"/********");
                inty.putExtra("date", current_date);
                mContexts.startActivity(inty);
            }
        });

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
                    if (item.getDepoName().toLowerCase ().contains(filterPattern) || item.getVehicleNo().toLowerCase().contains(filterPattern)) {
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
        private TextView txtVehNo, txtAddress, txtDatetime, txtImeiNo, txtsr, txtstatus, txtdepno;
       private LinearLayout item_lin_top;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtVehNo        = (TextView) itemView.findViewById(R.id.txtVehNo);
            txtAddress      = (TextView) itemView.findViewById(R.id.txtAddress);
            txtDatetime     = (TextView) itemView.findViewById(R.id.txtDatetime);
            txtImeiNo       = (TextView) itemView.findViewById(R.id.txtImeiNo);

            txtsr           = (TextView) itemView.findViewById(R.id.txtsr);
            txtstatus       = (TextView) itemView.findViewById(R.id.txtstatus);
            txtdepno        = (TextView) itemView.findViewById(R.id.txtdepno);

            item_lin_top    = (LinearLayout) itemView.findViewById ( R.id.item_lin_top );
        }
    }
}
