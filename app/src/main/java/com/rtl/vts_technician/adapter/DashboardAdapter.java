package com.rtl.vts_technician.adapter;

import android.content.Context;
import android.graphics.Typeface;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.model.dashboard;

import java.util.List;

/**
 * Created byTrending Design on 23/03/19.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    private Context mContext;
    private List<dashboard> dashboardList;

    Typeface tf;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvtitle;
        ImageView imgMenu;
        CardView cdview;

        public MyViewHolder(View view) {
            super(view);

            tvtitle = (TextView) view.findViewById(R.id.tvmenutitle);

            imgMenu=(ImageView)view.findViewById(R.id.imgmenu);

            cdview = (CardView) view.findViewById(R.id.card_view);
        }
    }

    public DashboardAdapter(Context mContext, List<dashboard> dashboardList) {
        this.mContext = mContext;
        this.dashboardList = dashboardList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_menu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //FetchImages();

        //imageserver=imageList;
//        tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/saira_regular.ttf");
//        tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/proximanovaregular.ttf");
//        holder.tvtitle.setTypeface(tf);

        final dashboard dashboard = dashboardList.get(position);
        holder.tvtitle.setText(dashboard.getTitle());

        Glide.with(mContext)
                .load(dashboard.getImg())
                .into(holder.imgMenu);

       if(holder.tvtitle.getText().toString().equalsIgnoreCase("ADD Pole")) {
           holder.cdview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                 /*  Intent inty = new Intent(mContext, ActivityAddPole_New.class);
                   mContext.startActivity(inty);
*/
               }
           });

       }else    if(holder.tvtitle.getText().toString().equalsIgnoreCase("History On List")) {
           holder.cdview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   /*Intent inty = new Intent(mContext, ActivityHistoryList.class);
                   mContext.startActivity(inty);*/

               }
           });

       }else    if(holder.tvtitle.getText().toString().equalsIgnoreCase("History On Map")) {
           holder.cdview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                 /*  Intent inty = new Intent(mContext, RecyclerActivity.class);
                   mContext.startActivity(inty);*/

               }
           });

       }else    if(holder.tvtitle.getText().toString().equalsIgnoreCase("Upload Detail")) {
           holder.cdview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                 /*  Intent inty = new Intent(mContext, ActivityUploadList.class);
                   mContext.startActivity(inty);*/

               }
           });

       }
//
    }

    @Override
    public int getItemCount() {
        return dashboardList.size();
    }

}