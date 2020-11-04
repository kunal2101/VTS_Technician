package com.rtl.vts_technician.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Pojo.RawDataModel;

import java.util.ArrayList;
import java.util.Random;

public class RawDataDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    ArrayList<RawDataModel> arr_raw_data;


    int[] colorArr = {Color.parseColor("#01b1af"), Color.parseColor("#ffff908b"),
            Color.parseColor("#ff169c1f"), Color.parseColor("#2196F3"),
            Color.parseColor("#523921"), Color.parseColor("#FF9D3D"),
            Color.parseColor("#73b6fd"), Color.parseColor("#3B5998"),
            Color.parseColor("#ffff00"), Color.parseColor("#ff4986e7"),
            Color.parseColor("#ff9d3d"), Color.parseColor("#b20e0f"),
            Color.parseColor("#c000ff00"), Color.parseColor("#84e5de"),
            Color.parseColor("#00a5a9"), Color.parseColor("#479c9d"),
            Color.parseColor("#ff6700"), Color.parseColor("#ff4500"),
            Color.parseColor("#2ac6b1"), Color.parseColor("#edcc4d"),
            Color.parseColor("#eb7e62")};


    public RawDataDetailAdapter(ArrayList<RawDataModel> arr_raw_data) {
        this.arr_raw_data = arr_raw_data;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_page, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {

            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return arr_raw_data == null ? 0 : arr_raw_data.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return arr_raw_data.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView  txtid, txtETM,  messageStatus, messageTime,serverTime;
        View viewLine;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            txtid = (TextView) itemView.findViewById(R.id.txtid);
            txtETM = (TextView) itemView.findViewById(R.id.txtETM);
            viewLine = (View)itemView.findViewById(R.id.viewLine);
            messageStatus= (TextView) itemView.findViewById(R.id.messageStatus);
            messageTime= (TextView) itemView.findViewById(R.id.messageTime);
            serverTime= (TextView) itemView.findViewById(R.id.serverTime);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(ItemViewHolder holder, int position) {

        RawDataModel rawDataModel = arr_raw_data.get(position);

        final String raw_data = rawDataModel.getRawData();
        final String messagestatus_data =rawDataModel.getMsgStatus();
        final String date_data = rawDataModel.getMessageTime();
        final String severdate_data = rawDataModel.getReceivedTime();
        //viewHolder.tvItem.setText(item);

        holder.txtETM.setText(raw_data);
        holder.messageStatus.setText(messagestatus_data);
        holder.messageTime.setText("Tracking Date/Time  :- "+date_data);

        holder.serverTime.setText("Recevied Date/Time  :- "+severdate_data);

        holder.txtid.setText(position+1+"");
        Random random = new Random(); // or create a static random field...
        int colorString = colorArr[random.nextInt(colorArr.length)];
        holder.viewLine.setBackgroundColor(colorString);


    }


}
