package com.rtl.vts_technician.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rtl.vts_technician.Constants.Utility;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Pojo.PerformanceCountModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Diwash Choudhary on 13-08-2020.
 */
public class PerformanceCountAdapter extends RecyclerView.Adapter<PerformanceCountAdapter.PerformanceCountAdapterVh> {
    private List<PerformanceCountModel> performanceCountList;
    private Context context;
    private SelectedDate selectedDate;

   /* public PerformanceCountAdapter(List<PerformanceCountModel> performanceCountList) {
        this.performanceCountList = performanceCountList;

    }
*/

    public PerformanceCountAdapter(List<PerformanceCountModel> performanceCountList, SelectedDate selectedDate) {
        this.performanceCountList = performanceCountList;
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public PerformanceCountAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new PerformanceCountAdapterVh(LayoutInflater.from(context).inflate(R.layout.performance_list_items,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PerformanceCountAdapterVh holder, int position) {
        PerformanceCountModel performanceCountModel = performanceCountList.get(position);

        SimpleDateFormat HHmmFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        SimpleDateFormat hhmmampmFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        String arrTime  = Utility.parseDate(performanceCountModel.getActivityDate(), HHmmFormat, hhmmampmFormat);

        holder.txt_count.setText(""+performanceCountModel.getTotal());
        holder.txt_date.setText(arrTime);
        holder.txt_sr.setText(""+(position+1));

        holder.txt_install.setText("Installed : "+performanceCountModel.getInstall());
        holder.txt_replace.setText("Replaced : "+performanceCountModel.getReplace());
        holder.txt_maintance.setText("Maintainced : "+performanceCountModel.getMaintenance());
        holder.txt_uninstall.setText("Unistalled : "+performanceCountModel.getUninstall());
    }

    @Override
    public int getItemCount() {
        return performanceCountList.size();
    }

    public interface SelectedDate{
        void selectedDate(PerformanceCountModel performanceCountModel);
    }

    public class PerformanceCountAdapterVh extends RecyclerView.ViewHolder {
        TextView txt_sr, txt_date, txt_count, txt_install, txt_uninstall, txt_maintance, txt_replace;
        LinearLayout ly_more;

        public PerformanceCountAdapterVh(@NonNull View itemView) {
            super(itemView);

            txt_count = itemView.findViewById(R.id.txt_count);
            txt_sr    = itemView.findViewById(R.id.txt_sr);
            txt_date  = itemView.findViewById(R.id.txt_date);
            ly_more   = itemView.findViewById(R.id.ly_more);
            txt_install   = itemView.findViewById(R.id.txt_install);
            txt_uninstall   = itemView.findViewById(R.id.txt_uninstall);
            txt_maintance   = itemView.findViewById(R.id.txt_maintance);
            txt_replace   = itemView.findViewById(R.id.txt_replace);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                selectedDate.selectedDate(performanceCountList.get(getAdapterPosition()));
                }
            });
        }
    }
}
