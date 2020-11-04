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

    public static final String USER_ID          = "user_id";
    private final String USERNAME               = "name";
    private final String MOBILE                 = "mobile";
    private final String USERLOGIN              = "userlogin";
    private final String ADMINLOGIN             = "adminlogin";
    private final String PASSWORD               = "password";
    private final String ADMINPASSWORD          = "adminpassword";
    private final String TOKEN                 = "token";
    private final String REGION_CODE           = "region_code";
    private final String REGION_NAME           = "region_name";
     
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

    public String getRegion_Name() {
        return pref.getString(REGION_NAME, null);

    }

    public String putRegion_name(String uname) {
        Editor edit = pref.edit();
        edit.putString(REGION_NAME, uname);
        edit.commit();
        return uname;
    }

    public String getRegion_code() {
        return pref.getString(REGION_CODE, null);

    }

    public String putRegion_code(String uname) {
        Editor edit = pref.edit();
        edit.putString(REGION_CODE, uname);
        edit.commit();
        return uname;
    }

    public String getUser_Name() {
        return pref.getString(USERNAME, null);

    }


    public String putMobileNo(String mobile) {
        Editor edit = pref.edit();
        edit.putString(MOBILE, mobile);
        edit.commit();
        return mobile;
    }

    public String getMobile() {
        return pref.getString(MOBILE, null);

    }

    public String putToken(String token) {
        Editor edit = pref.edit();
        edit.putString(TOKEN, token);
        edit.commit();
        return token;
    }

    public String getToken() {
        return pref.getString(TOKEN, null);

    }

    public String putPassword(String mobile) {
        Editor edit = pref.edit();
        edit.putString(PASSWORD, mobile);
        edit.commit();
        return mobile;
    }

    public String getPassword() {
        return pref.getString(PASSWORD, null);
    }

    public String putAdminPassword(String adminPass) {
        Editor edit = pref.edit();
        edit.putString(ADMINPASSWORD, adminPass);
        edit.commit();
        return adminPass;
    }

    public String getAdminPassword() {
        return pref.getString(ADMINPASSWORD, null);
    }

    public String putUserLogin(String userlogin) {
        Editor edit = pref.edit();
        edit.putString(USERLOGIN, userlogin);
        edit.commit();
        return userlogin;
    }

    public String getUserLogin() {
        return pref.getString(USERLOGIN, null);
    }

    public String putAdminLogin(String adminlogin) {
        Editor edit = pref.edit();
        edit.putString(ADMINLOGIN, adminlogin);
        edit.commit();
        return adminlogin;
    }

    public String getAdminLogin() {
        return pref.getString(ADMINLOGIN, null);
    }

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

    public boolean isCreated(){
        if(this.iscreated()){
            return true;
        }
		return false;
    }

    public boolean iscreated(){
        return pref.getBoolean(IS_CREATED, false);
    }


}
