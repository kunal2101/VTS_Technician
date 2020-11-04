package com.rtl.vts_technician.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.rtl.vts_technician.Database.DatabaseHelper;
import com.rtl.vts_technician.PreferenceHelper;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Adapter.De_InstallationListAdapter;
import com.rtl.vts_technician.Pojo.De_installDeviceModel;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class De_InstallationHistoryFragment extends Fragment {
    ImageView pimage;
    TextView pName;
    RecyclerView historyRecyclerView;
    private De_InstallationListAdapter historyListAdapter;
    private List<De_installDeviceModel> historyList;
    DatabaseHelper dbHelper;
    PreferenceHelper preferenceHelper;
    String UserLogin = "";
    private boolean _hasLoadedOnce= false; // your boolean field

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);
        if (this.isVisible()) {
// we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                if (isConnection()){

                        GetUnInstallHistory(UserLogin);

                   // _hasLoadedOnce = true;
                }


            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_de__installation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        historyRecyclerView     = (RecyclerView)view. findViewById( R.id.de_installRecyclerView);
       // dbHelper = new DatabaseHelper (getActivity());
        preferenceHelper = new PreferenceHelper(getContext());
        UserLogin = preferenceHelper.getUserLogin();

        //historyList = dbHelper.getDe_Install_Arry_list();

        historyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        historyRecyclerView.setLayoutManager(mLayoutManager);

        // prepareHistoryData();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDetach () {
        super.onDetach ( );
    }

    private void GetUnInstallHistory(String userLogin){
        String  tag_string_req = "string_req";

       // CustomProgressBar.showCustomProgressDialog(getContext(),"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.GETDE_INSTALLVEHICLE_HISTORY + userLogin + "/fkdsjflksj/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
              // CustomProgressBar.removeCustomProgressDialog();
                try {
                    //CustomProgressBar.removeCustomProgressDialog();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        De_installDeviceModel dataModel = null;
                        historyList= new ArrayList<>();
                        historyList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            dataModel = new De_installDeviceModel ();

                            JSONObject jobject = jsonArray.getJSONObject(i);
                            //dataModel.setId(ids);
                            dataModel.setVeh_no(jobject.getString("vehicle_reg_no"));
                            dataModel.setDepo(jobject.getString("depotName"));
                            dataModel.setDivision(jobject.getString("divisionName"));
                            dataModel.setImieno(jobject.getString("deviceImei"));
                            dataModel.setRemarks("");
                            dataModel.setInstal_time(jobject.getString("uninstallationTime"));
                            dataModel.setInstal_date(jobject.getString("uninstallationDate"));
                            dataModel.setLat(jobject.getString("latitude"));
                            dataModel.setLng(jobject.getString("longitude"));
                            dataModel.setAddress(jobject.getString("address"));
                            //dataModel.setImageString("http://103.197.121.83:8010/Image/"+jobject.getString("imageString"));
                            dataModel.setImageString(jobject.getString("imageString"));


                            historyList.add(dataModel);
                        }
                        historyListAdapter = new De_InstallationListAdapter(getContext(),historyList);
                        historyRecyclerView.setAdapter(historyListAdapter);
                    }else{
                        Toast.makeText(getContext(), "No records found", Toast.LENGTH_LONG).show();
                        //CustomProgressBar.removeCustomProgressDialog();
                    }
                } catch (JSONException e) {
                   System.out.println(e.getMessage());
                    //CustomProgressBar.removeCustomProgressDialog();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());
               // CustomProgressBar.removeCustomProgressDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    protected boolean isConnection() {
        ConnectivityManager manage = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
