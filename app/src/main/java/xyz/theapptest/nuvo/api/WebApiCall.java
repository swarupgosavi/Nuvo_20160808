package xyz.theapptest.nuvo.api;

/**
 * Created by trtcpu007 on 11/7/16.
 */

public class WebApiCall {

    public static String baseURl="http://nuvo.theapptest.xyz/v2/api/";

    //first login and register
    public static String default_user_register=baseURl+"user/register";

    public static String default_user_login= baseURl+"user/login";

    public static String default_forgotpassword= baseURl+"user/forgotpassword";


    public static String getAuditionsUrl = baseURl + "job/inreview";

    public static String getUserProfileUrl = baseURl + "user/";

    public static String updateProfile = baseURl + "user/";


    //registeration parameter
    public static String KEY_FIRSTNAME="first_name";
    public static String KEY_LASTNAME="last_name";
    public static String KEY_EMAIL="email";
    public static String KEY_PASSWORD="password";
    public static String KEY_ROLE="role";
    public static String KEY_COMPANY="company";
    public static String KEY_PHONE="phone";
    public static String KEY_ARGENCY_LOGO = "agency_logo";











}



