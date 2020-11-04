package com.rtl.vts_technician.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Pojo.TechnicianListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diwash Choudhary on 13-08-2020.
 */
public class TechnicianListAdapter extends RecyclerView.Adapter<TechnicianListAdapter.TechnicianAdapterVh> implements Filterable {
    private List<TechnicianListModel> technicianStatusList;
    private List<TechnicianListModel> temp_technicianStatusList;
    private Context context;
    private SelectedTechnician selectedTech;

    public TechnicianListAdapter(Context context, List<TechnicianListModel> technicianStatusList, SelectedTechnician selectedTech) {
        this.context = context;
        this.technicianStatusList = technicianStatusList;
        this.selectedTech = selectedTech;
        temp_technicianStatusList = new ArrayList<>(technicianStatusList);
    }

    @NonNull
    @Override
    public TechnicianAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new TechnicianAdapterVh(LayoutInflater.from(context).inflate(R.layout.tech_list_items,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TechnicianAdapterVh holder, int position) {
        TechnicianListModel technicianListModel = technicianStatusList.get(position);

        holder.txt_sr.setText(""+(position+1));
        holder.txt_empid.setText(technicianListModel.getEmpid());
        holder.txt_name.setText(technicianListModel.getName());
        holder.txt_div.setText(technicianListModel.getDivision());

        int i = technicianListModel.getStatus();

        if (i == 0){
            holder.txt_status.setText("In-active");
            holder.txt_status.setTextColor(Color.parseColor("#FFFFFF"));
            holder.txt_status.setBackgroundColor(Color.parseColor("#ED262A"));

        }else if (i == 1){
            holder.txt_status.setText("Active");
            holder.txt_status.setTextColor(Color.parseColor("#145795"));
            holder.txt_status.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return technicianStatusList.size();
    }

    @Override
    public Filter getFilter() {
        return TempFilter;
    }

    public interface SelectedTechnician{
        void selectedTechnician(TechnicianListModel technicianListModel);
    }

    private Filter TempFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TechnicianListModel> filteredList = new ArrayList<> ();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(temp_technicianStatusList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (TechnicianListModel items : temp_technicianStatusList) {
                    if (items.getName().toLowerCase().contains(filterPattern) || items.getEmpid().toLowerCase().contains(filterPattern)) {
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
            technicianStatusList.clear();
            technicianStatusList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

        public class TechnicianAdapterVh extends RecyclerView.ViewHolder {
        TextView txt_sr, txt_empid, txt_name, txt_status, txt_div;

        public TechnicianAdapterVh(@NonNull View itemView) {
            super(itemView);

            txt_div    = itemView.findViewById(R.id.txt_div);
            txt_sr    = itemView.findViewById(R.id.txt_sr);
            txt_empid   = itemView.findViewById(R.id.txt_empid);
            txt_name   = itemView.findViewById(R.id.txt_name);
            txt_status   = itemView.findViewById(R.id.txt_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedTech.selectedTechnician(technicianStatusList.get(getAdapterPosition()));
                }
            });
        }
    }
}
