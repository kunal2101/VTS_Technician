package com.rtl.vts_technician;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

@SuppressLint("CommitPrefEdits") 
public class PreferenceHelper {

    // Shared Preferences
    SharedPreferences pref;
     
    // Editor for Shared preferences
    Editor editor;
     
    // Context
    Context _context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
     
    // Sharedpref file name
    private static final String PREF_NAME       = "msrtcrawdata";

    // All Shared Preferences Keys
    private static final String IS_CREATED      = "isCreateIn";

    private final String DEVICE_TOKEN           = "device_token";
    private final String SESSION_TOKEN          = "session_token";
    public static final String USER_ID          = "user_id";
    public static final String COMPANY_NAME     = "CompanyName";
    public static final String COMPANY_ID       = "companyid";
    public static final String COMPANY_INFO     = "CompanyInfo";
    public static final String COMPANY_LOGO     = "Imagepath1";
    public static final String COMPANY_ICON     = "Imagepath2";
    private final String USERNAME               = "name";
     
    // Constructor
    public PreferenceHelper(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String putUser_name(String uname) {
        Editor edit = pref.edit();
        edit.putString(USERNAME, uname);
        edit.commit();
        return uname;
    }

    public String getUser_Name() {
        return pref.getString(USERNAME, null);

    }

    public void putDeviceToken(String deviceToken) {
        editor.putBoolean(IS_CREATED, true);
        editor.putString(DEVICE_TOKEN, deviceToken);
        editor.commit();
    }

    public String getDeviceToken() {
        return pref.getString(DEVICE_TOKEN, null);
    }

    public void putSessionToken(String sessionToken) {
        editor.putBoolean(IS_CREATED, true);
        editor.putString(SESSION_TOKEN, sessionToken);
        editor.commit();
    }

    public String getSessionToken() {
        return pref.getString(SESSION_TOKEN, null);

    }
    //2.
    //store user_id
    public void putUser_id(String user_id){
        editor.putBoolean(IS_CREATED, true);
        editor.putString(USER_ID, user_id);
        editor.commit();
    }

    //get user_id
    public String getUser_id(){

        return pref.getString(USER_ID, null);
    }

    //store user_id
    public void putCompany_id(String cpmpany_id){
        editor.putBoolean(IS_CREATED, true);
        editor.putString(COMPANY_ID, cpmpany_id);
        editor.commit();
    }
    public String getCompany_id(){
        return pref.getString(COMPANY_ID, null);
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public boolean isCreated(){
        if(this.iscreated()){
            return true;
        }
		return false;
    }

    public boolean iscreated(){
        return pref.getBoolean(IS_CREATED, false);
    }

    public void putCompanyNAme(String companyName){
        editor.putBoolean(IS_CREATED, true);
        editor.putString(COMPANY_NAME, companyName);
        editor.commit();
    }

    //get user_id
    public String getputCompanyNAme(){
        return pref.getString(COMPANY_NAME, null);
    }

    public void putCompanyInfo(String companyInfo){
        editor.putBoolean(IS_CREATED, true);
        editor.putString(COMPANY_INFO, companyInfo);
        editor.commit();
    }

    //get user_id
    public String getputCompanyInfo(){
        return pref.getString(COMPANY_INFO, null);
    }

    public void putCompanyImage(String companyImage){
        editor.putBoolean(IS_CREATED, true);
        editor.putString(COMPANY_LOGO, companyImage);
        editor.commit();
    }

    //get user_id
    public String getputCompanyImage(){
        return pref.getString(COMPANY_LOGO, null);
    }

    public void putCompanyicon(String companyIcon){
        editor.putBoolean(IS_CREATED, true);
        editor.putString(COMPANY_ICON, companyIcon);
        editor.commit();
    }

    //get user_id
    public String getputCompanyIcon(){
        return pref.getString(COMPANY_ICON, null);
    }

}
