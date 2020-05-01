package com.rtl.vts_technician;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rtl.vts_technician.volley.AppController;
import com.rtl.vts_technician.volley.Constants;
import com.rtl.vts_technician.volley.CustomProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class DetailActivity extends AppCompatActivity {
    String intent_date,intent_imenino,intent_deviceid;
    TextView txtImeiNo,txtDeviceId,txtDate;
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
    ArrayList<String> arr_raw_data;
    ArrayList<String> arr_raw_date;
    ArrayList<String> arr_raw_serverdate;
    ArrayList<String> arr_raw_satatus;


    RecyclerView recyclerview;
    private ImageView img_back,img_refresh;
    private  TextView txt_title,more;
    int count_=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        img_back = (ImageView)findViewById(R.id.img_back);
        img_refresh =(ImageView)findViewById(R.id.img_refresh);
        txt_title = (TextView)findViewById(R.id.txt_title);
        more     = (TextView)findViewById(R.id.more);


        txtImeiNo=(TextView)findViewById(R.id.txtImeiNo);
        txtDeviceId=(TextView)findViewById(R.id.txtDeviceId);
        txtDate=(TextView)findViewById(R.id.txtDate);
        recyclerview = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerview.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplication(), R.dimen.item_offset);
        recyclerview.addItemDecoration(itemDecoration);
        arr_raw_data= new ArrayList<>();
        arr_raw_date= new ArrayList<>();
        arr_raw_satatus= new ArrayList<>();
        arr_raw_serverdate= new ArrayList<>();


        try{

            intent_date =getIntent().getExtras().getString("date");
            intent_imenino=getIntent().getExtras().getString("imeino");
            String[] currencies = intent_imenino.split("/");
            intent_imenino = currencies[0];
            intent_deviceid = currencies[1];
            // intent_deviceid =  intent_imenino.substring( intent_imenino.indexOf("/")+1, intent_imenino.length());
            Log.d("allllll" ,intent_date +intent_imenino+"-------"+ intent_deviceid );
            txtDate.setText(intent_date);
            txtDeviceId.setText(intent_deviceid);
            txtImeiNo.setText(intent_imenino);

        }catch (Exception e){
            e.getMessage();
        }
        GetImeinoDetail(intent_imenino,"20");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arr_raw_data.clear();
                arr_raw_date.clear();
                arr_raw_satatus.clear();
                GetImeinoDetail(intent_imenino,count_*20+"");
                count_ = count_ + 1 ;
            }
        });
        img_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arr_raw_data.clear();
                arr_raw_date.clear();
                arr_raw_satatus.clear();
                GetImeinoDetail(intent_imenino,"20");
            }
        });
        txt_title.setText("Raw Data Detail");

    }
    private void GetImeinoDetail(final String deviceImei,final String count) {

        String  tag_string_req = "string_req";

        CustomProgressBar.showCustomProgressDialog(this,"Please Wait...");

        final JSONObject requestJson = new JSONObject();
        try{

            requestJson.put("deviceImei",deviceImei);
            requestJson.put("count",count);

            System.out.println("Diwas "+requestJson);

        }catch (Exception ev){
            System.out.print(ev.getMessage());
        }

        JsonObjectRequest req = new JsonObjectRequest(Constants.GETIMEINODETAIL, requestJson,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        CustomProgressBar.removeCustomProgressDialog();
                        try {

                            if (response.getInt("code") == 1) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                System.out.println("Diwas Response"+response);

                                if (jsonArray.length() >0) {


                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jobject = jsonArray.getJSONObject(i);
                                        String message = jobject.getString("rawData");
                                        arr_raw_data.add(message);
                                        arr_raw_date.add(jobject.getString("messageTime"));
                                        arr_raw_serverdate.add(jobject.getString("receivedTime"));
                                        if(jobject.getString("msgStatus").equalsIgnoreCase("L")){
                                            arr_raw_satatus.add("Message Status - L (live)");
                                        }else if(jobject.getString("msgStatus").equalsIgnoreCase("H")){
                                            arr_raw_satatus.add("Message Status - H (History)");
                                        }

                                    }
                                    Myadapter mAdapter = new Myadapter();
                                    recyclerview.setAdapter(mAdapter);

                                }else{
                                    CustomProgressBar.removeCustomProgressDialog();

                                    Toast.makeText(DetailActivity.this, "No Record found",Toast.LENGTH_LONG).show();
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            CustomProgressBar.removeCustomProgressDialog();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());
                CustomProgressBar.removeCustomProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_string_req);

    }

    public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {
        String routeId;


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_page, null);
            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final String raw_data = arr_raw_data.get(position);
            final String messagestatus_data = arr_raw_satatus.get(position);
            final String date_data = arr_raw_date.get(position);
            final String severdate_data = arr_raw_date.get(position);

            holder.txtETM.setText(raw_data);
            holder.messageStatus.setText(messagestatus_data);
            holder.messageTime.setText("Server Date/Time  :- "+date_data);

            holder.serverTime.setText("Recevied Date/Time  :- "+severdate_data);
            /*long miliSec = Long.parseLong(date_data);
            long miliSec_server = Long.parseLong(severdate_data);


            DateFormat simple = new SimpleDateFormat("dd MMM yy HH:mm:ss");

            Date result = new Date(miliSec);
            Date result_server = new Date(miliSec_server);

            holder.messageTime.setText("Server Date/Time  :- "+simple.format(result));

            holder.serverTime.setText("Recevied Date/Time  :- "+simple.format(result_server));
*/

            // holder.messageTime.setText(date_data);

            holder.txtid.setText(position+1+"");
            Random random = new Random(); // or create a static random field...
            int colorString = colorArr[random.nextInt(colorArr.length)];
            holder.viewLine.setBackgroundColor(colorString);

        }


        @Override
        public int getItemCount() {
            return arr_raw_data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            protected TextView txtid;
            protected TextView txtETM;
            protected  TextView messageStatus;
            protected  TextView messageTime;
            protected  TextView serverTime;

            View viewLine;


            public ViewHolder(final View itemLayputView) {
                super(itemLayputView);
                txtid = (TextView) itemLayputView.findViewById(R.id.txtid);
                txtETM = (TextView) itemLayputView.findViewById(R.id.txtETM);
                viewLine = (View)itemLayputView.findViewById(R.id.viewLine);
                messageStatus= (TextView) itemLayputView.findViewById(R.id.messageStatus);
                messageTime= (TextView) itemLayputView.findViewById(R.id.messageTime);
                serverTime= (TextView) itemLayputView.findViewById(R.id.serverTime);

            }
        }
    }

}
