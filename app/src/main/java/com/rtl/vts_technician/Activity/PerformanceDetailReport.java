package com.rtl.vts_technician.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.rtl.vts_technician.BuildConfig;
import com.rtl.vts_technician.Constants.Utility;
import com.rtl.vts_technician.R;
import com.rtl.vts_technician.Utils.WriteExcel;
import com.rtl.vts_technician.Adapter.PerformanceDetailAdapter;
import com.rtl.vts_technician.Pojo.PerformanceCountModel;
import com.rtl.vts_technician.Pojo.PerformanceDetailModel;
import com.rtl.vts_technician.Volley.AppController;
import com.rtl.vts_technician.Volley.Constants;
import com.rtl.vts_technician.Volley.CustomProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class PerformanceDetailReport extends AppCompatActivity {

    private TextView txt_selectedDate;
    ImageView pimage;
    TextView pName;
    String processId = "";
    String selected_date = "", convert_date = "";
    ArrayList<PerformanceDetailModel> activityList;
    RecyclerView recyle_activity;
    PerformanceDetailAdapter performanceDetailAdapter;
    SearchView searchView;
    String  currentDateandTime,currentDate;
    String techLogin = "";
    List<String> tech_arry = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_detail_report);

        Toolbar toolbar = (Toolbar) findViewById( R.id.tools);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final Drawable upArrow = getResources().getDrawable( R.mipmap.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor( R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);

        pName           = (TextView) findViewById( R.id.pname);
        pName.setText("Performance Report Detail");
        pimage         = findViewById ( R.id.pimage );

        pimage.setBackground( ContextCompat.getDrawable(this, R.drawable.ic_excel));

        txt_selectedDate = findViewById(R.id.txt_selectedDate);
        recyle_activity = findViewById(R.id.recyle_activity);
        searchView      = (SearchView) findViewById(R.id.action_search);

        Intent intent =getIntent();
        if(intent.getExtras() != null){
            PerformanceCountModel performanceCountModel = (PerformanceCountModel) intent.getSerializableExtra("data");

            processId = intent.getStringExtra("processid");
            selected_date = performanceCountModel.getActivityDate();
            techLogin = intent.getStringExtra("techLogin");
            tech_arry = getIntent().getStringArrayListExtra("tech_array");

            SimpleDateFormat HHmmFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            SimpleDateFormat hhmmampmFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

            String arrTime  = Utility.parseDate(selected_date, HHmmFormat, hhmmampmFormat);

            txt_selectedDate.setText("Report on : " +arrTime);
        }

        if (isConnection()){
            GetPerformanceReport();
        }

        recyle_activity.setLayoutManager(new LinearLayoutManager(this));
       // recyle_activity.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                try {
                    performanceDetailAdapter.getFilter().filter(newText);
                }catch (Exception e){
                    e.getMessage();
                }
                return false;
            }
        });

        pimage.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                importExcel(activityList);
            }
        } );
    }

    public  void importExcel(ArrayList<PerformanceDetailModel> arrlist){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        SimpleDateFormat sdf_date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        currentDate = sdf_date.format ( new Date (  ) );
        File sd = Environment.getExternalStorageDirectory();
        String csvFile ="Msrtc_Technician_"+currentDate+".xls";
        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {
            File file = new File ( directory , csvFile );
            WorkbookSettings wbSettings = new WorkbookSettings ( );
            wbSettings.setLocale ( new Locale ( "en" , "EN" ) );
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook ( file , wbSettings );
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet ( "Msrtc_Technician" , 10 );
            WriteExcel writeExcel = new WriteExcel ( );
            writeExcel.createLabel ( sheet );
            //   modelArrayList = databaseHelper.getDeviceData_all ( );
            if (arrlist.size ( ) == 0) {
                Toast.makeText ( PerformanceDetailReport.this , "No Record Found " , Toast.LENGTH_LONG ).show ( );
            } else {
                for (int i = 0; i < arrlist.size ( ); i++) {
                    HashMap<String, String> map = new HashMap<>();
                    PerformanceDetailModel performanceDetailModel = new PerformanceDetailModel();
                    performanceDetailModel = arrlist.get ( i );
                    sheet.addCell ( new Label( 0 , i + 1 , i + 1 + "" ) );
                    sheet.addCell ( new Label ( 1 , i + 1 ,performanceDetailModel.getActivity_date()) );
                    sheet.addCell ( new Label ( 2 , i + 1 ,performanceDetailModel.getTech_name()) );
                    sheet.addCell ( new Label ( 3 , i + 1 , performanceDetailModel.getInstall() ) );
                    sheet.addCell ( new Label ( 4 , i + 1 , performanceDetailModel.getUninstall() ) );
                    sheet.addCell ( new Label ( 5 , i + 1 , performanceDetailModel.getMaintaince() ) );
                    sheet.addCell ( new Label ( 6 , i + 1 , performanceDetailModel.getReplace() ) );
                    int total_day_activity = Integer.parseInt(performanceDetailModel.getInstall()) + Integer.parseInt(performanceDetailModel.getUninstall())+Integer.parseInt(performanceDetailModel.getMaintaince()) +Integer.parseInt(performanceDetailModel.getReplace());
                    sheet.addCell ( new Label ( 7 , i + 1 , total_day_activity+"" ) );
                   /* if( map.get ( "Key_install" ) .equalsIgnoreCase ( "0" ) &&  map.get ( "Key_uninstall" ) .equalsIgnoreCase ( "0" )
                            &&  map.get ( "Key_maintenance" ) .equalsIgnoreCase ( "0" ) &&  map.get ( "Key_replace" ) .equalsIgnoreCase ( "0" )){
                        sheet.addCell ( new Label ( 7 , i + 1 , "ABSENT" ) );
                    }else{
                        sheet.addCell ( new Label ( 7 , i + 1 , "PRESENT" ) );
                    }*/
                }
                workbook.write ( );
                workbook.close ( );
                Toast.makeText ( PerformanceDetailReport.this , "Record saved Sucessfully " , Toast.LENGTH_LONG ).show ( );
                sendmail();
            }
        }catch (Exception e){
            e.getMessage ();
        }
    }

    public  void sendmail(){
        String filename="Msrtc_Technician_"+currentDate+".xls";
        File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
        //Uri path = Uri.fromFile(filelocation);
        Uri path = FileProvider.getUriForFile(PerformanceDetailReport.this, BuildConfig.APPLICATION_ID + ".provider",filelocation);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
        emailIntent .setType("vnd.android.cursor.dir/email");
        String to[] = {"imkunal2101@gmail.com","samiran.de@rosmertatech.com"};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
// the attachment
        emailIntent .putExtra(Intent.EXTRA_STREAM, path);
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
// the mail subject
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, currentDateandTime+" Performance Report");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear sir," + "\nDaily performance report of MSRTC Technician of Date : " + currentDateandTime + "\n Please find the Atttchment for your reference. ");
        startActivity(Intent.createChooser(emailIntent , "Send email..."));
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
                performanceDetailAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void GetPerformanceReport(){
        String  tag_string_req = "string_req";
        CustomProgressBar.showCustomProgressDialog(PerformanceDetailReport.this,"Please Wait...");
        Log.d("URL  ", Constants.GETTECH_ACTIVITY + processId +"/" + selected_date +"/");
       StringRequest strReq = new StringRequest(Request.Method.GET,
                Constants.GETTECH_ACTIVITY + processId +"/" + selected_date +"/", new Response.Listener<String>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                Log.d("ONRESPONCE", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        activityList = new ArrayList<>();
                        activityList.clear();
                        PerformanceDetailModel performanceDetailModel;
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jobject = jsonArray.getJSONObject(i);

                            if (techLogin.equals(jobject.getString("userLogin"))){

                            performanceDetailModel = new PerformanceDetailModel();
                            performanceDetailModel.setTech_name(jobject.getString("technicianName"));
                            performanceDetailModel.setInstall(jobject.getString("install"));
                            performanceDetailModel.setMaintaince(jobject.getString("maintenance"));
                            performanceDetailModel.setReplace(jobject.getString("replace"));
                            performanceDetailModel.setTotal(jobject.getString("total"));
                            performanceDetailModel.setUninstall(jobject.getString("uninstall"));
                            performanceDetailModel.setActivity_date(jobject.getString("activityDate"));

                            activityList.add(performanceDetailModel);
                            }else if (techLogin.equalsIgnoreCase("All")){

                               performanceDetailModel = new PerformanceDetailModel();
                                performanceDetailModel.setTech_name(jobject.getString("technicianName"));
                                performanceDetailModel.setInstall(jobject.getString("install"));
                                performanceDetailModel.setMaintaince(jobject.getString("maintenance"));
                                performanceDetailModel.setReplace(jobject.getString("replace"));
                                performanceDetailModel.setTotal(jobject.getString("total"));
                                performanceDetailModel.setUninstall(jobject.getString("uninstall"));
                                performanceDetailModel.setActivity_date(jobject.getString("activityDate"));

                                activityList.add(performanceDetailModel);
                            }
                        }
                        CustomProgressBar.removeCustomProgressDialog();

                        activityList.sort((o1, o2) -> o1.getTech_name().compareTo(o2.getTech_name()));

                        performanceDetailAdapter = new PerformanceDetailAdapter(PerformanceDetailReport.this, activityList);
                        recyle_activity.setAdapter(performanceDetailAdapter);

                    }else{
                        Toast.makeText(PerformanceDetailReport.this,"No result found", Toast.LENGTH_LONG).show();
                        CustomProgressBar.removeCustomProgressDialog();
                    }
                } catch (JSONException e) {
                    CustomProgressBar.removeCustomProgressDialog();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse( VolleyError error) {
                CustomProgressBar.removeCustomProgressDialog();
                VolleyLog.d("ERROR :-", "Error: " + error.getMessage());
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
            Toast.makeText(PerformanceDetailReport.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}