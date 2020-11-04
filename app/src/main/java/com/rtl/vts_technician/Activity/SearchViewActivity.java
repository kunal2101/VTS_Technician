package com.rtl.vts_technician.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.rtl.vts_technician.PreferenceHelper;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Utils.ItemOffsetDecoration;
import com.rtl.vts_technician.Pojo.ExampleItem;
import com.rtl.vts_technician.Volley.AppController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SearchViewActivity extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerview;
    ProgressBar simpleProgressBar;
    public static final String TAG = SearchViewActivity.class.getSimpleName();
    String fliet_st = "";
    Myadapter mAdapter;
    private List<ExampleItem> exampleList;
    private List<ExampleItem> exampleList_duplicate;
    ArrayList<String> arr_raw_data , arr_vehicle_type;
    PreferenceHelper preferenceHelper;
    //Myadapter_new myadapter_new;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        simpleProgressBar = findViewById(R.id.simpleProgress);
        searchView = findViewById(R.id.action_search);

        recyclerview = (RecyclerView) findViewById(R.id.recycle_search);
        recyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerview.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplication(), R.dimen.item_offset);
        recyclerview.addItemDecoration(itemDecoration);

        preferenceHelper = new PreferenceHelper(SearchViewActivity.this);

        exampleList = new ArrayList<>();
        exampleList_duplicate  = new ArrayList<>();
        arr_raw_data= new ArrayList<>();
        arr_vehicle_type  = new ArrayList<>();

        searchView = findViewById(R.id.action_search);

        mAdapter = new Myadapter();
        recyclerview.setAdapter(mAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.length() >= 4  ) {

                    if(fliet_st.equalsIgnoreCase( newText.substring(0, 4))){
                        mAdapter.getFilter().filter(newText.toUpperCase());
                    }else {
                        fliet_st = newText;
                        exampleList.clear();
                        if (isConnection()) {
                            updateArrayList(newText);
                        }
                    }
                }

                return false;
            }
        });

       // myadapter_new = new Myadapter_new();

       // recyclerview.setAdapter(myadapter_new);
    }

    private void updateArrayList(String match_vehicle_no) {
        //String URL = "https://msrtcvtspis.com/app/mobile/vehicles/getMatchingVehicles/*/*/";
        //"http://103.197.121.83:8010/api/tech/autocompleteVehicle/f76hkjds6767jhdufk/?searchData=MH20"
        String URL =  "http://103.197.121.83:8010/api/tech/autocompleteVehicle/";
        String  tag_string_req = "string_req";

        simpleProgressBar.setVisibility(View.VISIBLE);

        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL + preferenceHelper.getToken() + "/?searchData=" + match_vehicle_no , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt("code") == 1) {

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        simpleProgressBar.setVisibility(View.GONE);

                        for (int i = 0; i < jsonArray.length(); i++) {
                           //JSONObject jobject = jsonArray.getJSONObject(i);
                            String Veh_reg_no = jsonArray.get(i).toString();
                            //String veh_type = jobject.getString("vehicleType");

                            arr_raw_data.add(Veh_reg_no);
                            //arr_vehicle_type.add(veh_type);
                            exampleList.add(new ExampleItem(Veh_reg_no));
                        }
                        exampleList_duplicate.addAll(exampleList) ;

                        recyclerview.setAdapter(mAdapter);

                    } else{
                        String msg = jsonObject.getString("message");
                        Toast.makeText(SearchViewActivity.this ,msg , Toast.LENGTH_LONG).show();
                        simpleProgressBar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleProgressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                simpleProgressBar.setVisibility(View.GONE);
            }
        })/*{
        //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("X-Requested-With", "com.app.commuter");
                 headers.put("content-type", "application/json");
                  headers.put("Authorization", "2e96e0a4ff05ba86dc8f778ac49a8dc0");

                    // add headers <key,value>
        String credentials = USERNAME+":"+PASSWORD;
        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headers.put("Authorization", auth);

                return headers;
            }
        }*/
       /* {@Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
              List<Header> token = response.allHeaders;
            String token = response.headers.get("token");
        }}*/;
        strReq.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>  implements Filterable {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_track_bus, null);
            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final ExampleItem currentItem = exampleList.get(position);

            final String vehNo = currentItem.getText1();

            holder.item_vehicle_no.setText(currentItem.getText1());

            Random random = new Random(); // or create a static random field...
            int colorString = colorArr[random.nextInt(colorArr.length)];
            holder.viewLine.setBackgroundColor(colorString);


            holder.ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.putExtra("VEH_REG_NO", vehNo);
                    setResult(3,intent);
                    finish();

                }
            });

        }


        @Override
        public int getItemCount() {
            return exampleList.size();
        }

        @Override
        public Filter getFilter() {
            return exampleFilter;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            protected TextView item_vehicle_no;
            View viewLine;
            LinearLayout ly;

            public ViewHolder(final View itemLayputView) {
                super(itemLayputView);
                item_vehicle_no = (TextView) itemLayputView.findViewById(R.id.item_vehicle_no);
                viewLine = (View)itemLayputView.findViewById(R.id.viewLine);
                ly = (LinearLayout) itemLayputView.findViewById(R.id.ly);

            }
        }
        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ExampleItem> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(exampleList_duplicate);
                } else {
                    String filterPattern = constraint.toString().trim();

                    for (ExampleItem item : exampleList_duplicate) {
                        if (item.getText1().contains(filterPattern)) {
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
                exampleList.clear();
                exampleList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };

    }


    protected boolean isConnection() {
        ConnectivityManager manage = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            Toast.makeText(SearchViewActivity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
