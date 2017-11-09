package com.hitachi_tstv.mist.it.tiresmanagement;

/**
 * Created by musz on 7/25/2016.
 */
public class ConstantUrl {

    public static String urlServerString = "http://service.eternity.co.th/";
    public static String urlProjectString =  "tires_test";
    public static String urlPathString = "/system/CenterService/";


    private String urlJSONuser = urlServerString + urlProjectString + urlPathString +"getUser.php";
    private String urlJSONLicense = urlServerString + urlProjectString + urlPathString +"getVehicle.php";
    private String urlJSONFormatWhell = urlServerString + urlProjectString + urlPathString +"getFormatVehicle.php";
    private String urlAddCheckList =  urlServerString + urlProjectString + urlPathString +"addCheckList.php";
    private String urlJSONReason = urlServerString + urlProjectString + urlPathString +"getReason.php";

    public String getUrlJSONuser() {
        return urlJSONuser;
    }

    public String getUrlAddCheckList() {
        return urlAddCheckList;
    }

    public String getUrlJSONLicense() {
        return urlJSONLicense;
    }

    public String getUrlJSONFormatWhell() {
        return urlJSONFormatWhell;
    }

    public String getUrlJSONReason() {
        return  urlJSONReason;
    }

}
