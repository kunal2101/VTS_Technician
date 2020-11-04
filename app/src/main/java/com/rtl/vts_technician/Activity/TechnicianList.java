package com.rtl.vts_technician.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.rtl.vts_technician.PreferenceHelper;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Adapter.TechnicianListAdapter;
import com.rtl.vts_technician.Pojo.TechnicianListModel;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;
import com.rtl.vts_technician.Volley.CustomProgressBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class TechnicianList extends AppCompatActivity implements TechnicianListAdapter.SelectedTechnician {
    ImageView pimage;
    TextView pName, txt_active, txt_inactive;
    RecyclerView recyle_tech;
    TechnicianListAdapter technicianListAdapter;
    List<TechnicianListModel> technicianListModelList;
    PreferenceHelper preferenceHelper;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_list);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbar = (Toolbar) findViewById( R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable( R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor( R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        pName           = (TextView) findViewById( R.id.pname);
        searchView      = (SearchView) findViewById(R.id.action_search);
        txt_inactive    = findViewById(R.id.txt_inactive);
        txt_active      = findViewById(R.id.txt_active);

        pName.setText("Technician List");

        preferenceHelper = new PreferenceHelper(this);


        if (isConnection()){
            GetTechnicianList();
        }


        recyle_tech = (RecyclerView) findViewById(R.id.recyle_tech);
        recyle_tech.setLayoutManager(new LinearLayoutManager(this));
        recyle_tech.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                try {
                    technicianListAdapter.getFilter().filter(newText);
                }catch (Exception e){
                    e.getMessage();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions( EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                technicianListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void GetTechnicianList(){

        String  tag_string_req = "string_req";

         CustomProgressBar.showCustomProgressDialog(TechnicianList.this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.Technician_Status + preferenceHelper.getToken() + "/", new Response.Listener<String>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                 CustomProgressBar.removeCustomProgressDialog();
                try {
                    technicianListModelList= new ArrayList<>();
                    technicianListModelList.clear();

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        JSONObject object = jsonObject.getJSONObject("dataCount");
                        String tot_active = object.getString("Active");
                        String tot_inactive = object.getString("Inactive");
                        txt_active.setText("Total Active : " +tot_active);
                        txt_inactive.setText("Total In-active : " +tot_inactive);

                        TechnicianListModel dataModel = null;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            dataModel = new TechnicianListModel ();

                            JSONObject jobject = jsonArray.getJSONObject(i);
                           dataModel.setEmpid(jobject.getString("empId"));
                           dataModel.setMobileNo(jobject.getString("mobile"));
                           dataModel.setDivision(jobject.getString("divisionName"));
                           dataModel.setName(jobject.getString("name"));
                           dataModel.setStatus(jobject.getInt("isActive"));

                            technicianListModelList.add(dataModel);
                        }

                        technicianListModelList.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                        technicianListAdapter = new TechnicianListAdapter(TechnicianList.this, technicianListModelList,TechnicianList.this);
                        recyle_tech.setAdapter(technicianListAdapter);
                    }else{
                        Toast.makeText(TechnicianList.this, "No records found", Toast.LENGTH_LONG).show();
                         CustomProgressBar.removeCustomProgressDialog();
                    }
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
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
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void ChangeTechnicianStatus(int stats, String ph, String tokens){

        String  tag_string_req = "string_req";

        CustomProgressBar.showCustomProgressDialog(TechnicianList.this,"Please Wait...");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.Technician_Activate + stats + "/" + ph + "/" + preferenceHelper.getToken() + "/", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());
                CustomProgressBar.removeCustomProgressDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        Toast.makeText(TechnicianList.this, "Status Changed", Toast.LENGTH_LONG).show();
                        GetTechnicianList();

                    }else{
                        Toast.makeText(TechnicianList.this, "No records found", Toast.LENGTH_LONG).show();
                        CustomProgressBar.removeCustomProgressDialog();
                    }
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
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
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    protected boolean isConnection() {
        ConnectivityManager manage = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            Toast.makeText(TechnicianList.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void selectedTechnician(TechnicianListModel technicianListModel) {
        int i = technicianListModel.getStatus();
        String mob = technicianListModel.getMobileNo();
        String token = preferenceHelper.getToken();
        int change_status =0;
        if (i == 0){
            change_status = 1;
        }else if (i == 1){
            change_status = 0;
        }

        showTechDialog(change_status, mob, token);

        //Toast.makeText(TechnicianList.this, "Name : " +technicianListModel.getName()+"\nEmp ID : " + technicianListModel.getEmpid()+ "\nPh : " +technicianListModel.getMobileNo(), Toast.LENGTH_LONG).show();
    }

    public void showTechDialog(int stat, String ph, String token){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("")
                .setTitle("Technician Activation");

        String msg = "";
        if (stat == 1){
            msg = "Are you sure to activate this technician ?";
        }else{
            msg = "Are you sure to de-activate this technician ?";
        }

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (isConnection()){
                            ChangeTechnicianStatus(stat, ph, token);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Technician Activation");
        alert.show();
    }

}