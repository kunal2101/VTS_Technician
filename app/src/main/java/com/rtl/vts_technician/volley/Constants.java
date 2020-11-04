package com.rtl.vts_technician.Volley;

/**
 * Created by sontbv on 12/4/17.
 */

public class Constants {

   public final static String BASE_API_URL = "http://103.197.121.83:8010/";
   //public final static String BASE_API_URL = "http://192.168.1.156:8010/";
    public final static String BASE_API_URL1 = "http://103.197.121.83:8010/api/tech/";


    public static final String GETALLIMEINO 		  	        = "http://103.197.121.83:8010/api/data/getDevicImei/lfksjdfu04/";
    public static final String GETVEHICLEDETAIL 		  	    = "http://103.197.121.83:8010/api/tech/vehicle/lkdsjf98943wur4/";
    public static final String unmappedvehicledetails 		  	= "http://103.197.121.83:8010/api/tech/unmappedvehicledetails/2/";
    public static final String GETDOWNVEHICLE 		  	        = "http://103.197.121.83:8010/api/tech/downVehicle/lkdfjoieur094/?mobile=";
    public static final String GET_REGION_DOWNVEHICLE 		  	= "http://103.197.121.83:8010/api/admin/getDownvehicleByRegion/";

    public static final String GETLOGIN	  	                    = BASE_API_URL1 + "login/lksjf0984w/";
    public static final String ADMINLOGIN	  	                = BASE_API_URL  + "api/admin/adminLogin/lkjhiuy98/";
    public static final String ALLTECHNICIAN	  	            = BASE_API_URL  + "api/tech/getTechniciansOfRegion/";
    public static final String GETREGISTRATION	  	            = BASE_API_URL1 + "registration/lkdsjafou40983w/";
    public static final String GETDIVISION	  	                = BASE_API_URL1 + "division/lksdjfoiew/";
    public static final String GETDEPO  	  	                = BASE_API_URL1 + "getDepot/";
    public static final String GETOTP  	  	                    = BASE_API_URL1 + "otpverification/ksjdf843r3/?mobile=";
    public static final String GETINSTALLVEHICLE  	  	        = BASE_API_URL1 + "installDevice/jhcuro8ew/";

    public static final String GETTECH_ACTIVITY 	  	        = BASE_API_URL + "api/admin/getTechnicianActivityByprocessId/";
   // public static final String GETIMEINODETAIL_NEW 	 		= BASE_API_URL + "/api/data/getRawData/jdhiusyr98w/";

    // public static final String GETVEHICLEDETAIL 		  	    = "http://103.197.121.83:8010/api/tech/vehicle/lkdsjf98943wur4/";

    public static final String GETIMEINODETAIL	  	            = "http://103.197.121.83:8010/api/data/lastPing/";
    public static final String GETDE_INSTALLVEHICLE  	  	    = BASE_API_URL1 + "unInstallDevice/jhcuro8ew/";

    public static final String GETDE_INSTALLVEHICLE_HISTORY  	= BASE_API_URL1 + "/historyDevice/u/";

    public static final String GETINSTALLVEHICLE_HISTORY  	    = BASE_API_URL1 + "historyDevice/i/";
    public static final String GETMAINTAUNCEVEHICLE_HISTORY  	= BASE_API_URL1 + "historyDevice/m/";
    public static final String CHANGE_PASSWORD                	= BASE_API_URL1 + "changePassword/lkfsjf984w/";
    public  static  final  String MAINTENANCE                   =  BASE_API_URL1 + "maintenanceDevice/jhcuro8ew/";
    public  static  final  String REPLACE                       =  BASE_API_URL1 + "replaceDevice/jhcuro8ew/";
    public static final String GETREPLACE_HISTORY  	            = BASE_API_URL1 + "historyDevice/r/";
    public static final String GET_REMATRKS_INSTALL_MAINTENAM   = BASE_API_URL1 + "getRemarksForDropDownList2/jhkhiuy89/";
    public static final String GET_REMATRKS                 	= BASE_API_URL1 + "getRemarksForDropDownList/jhkhiuy89/";
    public static final String My_PROFILE                    	= BASE_API_URL1 + "myprofile/";
    public static final String FORGET_PASSWORD                  = BASE_API_URL1 + "forgotPassword/";
    public static final String PROBLEMS                         = BASE_API_URL1 + "getProblemList/dkjsfjiu9u409/";
    public static final String ACTIONLIST  	  	                = BASE_API_URL1 + "getActionByproblemId/";
    public static final String unmappedImei  	  	            = BASE_API_URL1 + "getunmappedImei/njku9879/";
    public static final String unmapedvehicle  	  	            = "http://103.197.121.83:8010/api/alldata/unmapedvehicle/1/";
    public static final String PERFORMANCE_REPORT  	  	        = BASE_API_URL + "api/admin/countTechnicianActivity/";
    public static final String Technician_Status 	  	        = BASE_API_URL + "api/admin/getTechnician/3/0,1000/";
    public static final String Technician_Activate 	  	        = BASE_API_URL + "api/admin/ActiveInactiveMobile/";
    public static final String UPDATE_DIVISION 	  	            = BASE_API_URL + "api/admin/updateTechnicianData/kjgggg9989/";
    public static final String ACTIVITY_REPORT  	  	        = BASE_API_URL + "api/admin/activitySearch/daywaise/";
    public static final String GET_TECHNI_BY_DIV  	  	        = BASE_API_URL + "api/admin/getTechnicianBydivisionCode/";
    public static final String DAYWISE_ACTIVITY_REPORT  	  	= BASE_API_URL + "api/tech/daywisereportSearch/";

}
