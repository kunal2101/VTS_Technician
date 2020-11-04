package com.rtl.vts_technician.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Pojo.PerformanceDetailModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diwash Choudhary on 14-08-2020.
 */
public class PerformanceDetailAdapter extends RecyclerView.Adapter<PerformanceDetailAdapter.MyviewHolder> implements Filterable {
    List<PerformanceDetailModel> performanceList;
    List<PerformanceDetailModel> temp_performanceList;
    Context context;

    public PerformanceDetailAdapter(Context context, List<PerformanceDetailModel> performanceList) {
        this.context = context;
        this.performanceList = performanceList;
        temp_performanceList = new ArrayList<>(performanceList);
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.performance_detail_list_items,parent, false);
        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
       PerformanceDetailModel performanceDetailModel = performanceList.get(position);

        final String deinstall = performanceDetailModel.getUninstall();
        final String install = performanceDetailModel.getInstall();
        final String maintance = performanceDetailModel.getMaintaince();
        final String replace = performanceDetailModel.getReplace();
        final String techname = performanceDetailModel.getTech_name();
        final String total = performanceDetailModel.getTotal();

        holder.txt_deinstall.setText(deinstall);
        holder.txt_install.setText(install);
        holder.txt_maintain.setText(maintance);
        holder.txt_replace.setText(replace);
        holder.txt_sr.setText(""+(position+1));
        holder.txt_tech_name.setText(techname);
        holder.txt_total.setText(total);
    }

    @Override
    public int getItemCount() {
        return performanceList.size();
    }

    @Override
    public Filter getFilter() {
        return TempFilter;
    }

    private Filter TempFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PerformanceDetailModel> filteredList = new ArrayList<> ();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(temp_performanceList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (PerformanceDetailModel items : temp_performanceList) {
                    if (items.getTech_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(items);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            performanceList.clear();
            performanceList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView txt_sr, txt_tech_name, txt_install, txt_deinstall, txt_maintain, txt_replace, txt_total;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            txt_total   = itemView.findViewById(R.id.txt_total);
            txt_sr   = itemView.findViewById(R.id.txt_sr);
            txt_tech_name   = itemView.findViewById(R.id.txt_tech_name);
            txt_install   = itemView.findViewById(R.id.txt_install);
            txt_deinstall   = itemView.findViewById(R.id.txt_deinstall);
            txt_maintain   = itemView.findViewById(R.id.txt_maintain);
            txt_replace   = itemView.findViewById(R.id.txt_replace);
        }
    }
}
