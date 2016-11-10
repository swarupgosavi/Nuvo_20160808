package xyz.theapptest.nuvo.utils;

/**
 * Created by user on 05/10/2015.
 */
public class Constants {


    public static final String NUVOPREF = "NuvoPref";
    public static final String Key_Login_Email = "keyLoginEmail";
    public static final String Key_Login_Password = "keyLoginPassword";

    //public static final String Base_Url = "http://mawsoftwares.in/spex/api/index.php/";
    //**************Prod url*******************
     public static final String Base_Url = "http://mawsoftwares.in/spex/api/index.php/v11/";
    //****************************************
    //public static final String Base_Url = "http://192.168.1.108/spex/api/index.php/v5/";

 //   public static final String Base_Url = "http://mawsoftwares.in/spex_staging/api/index.php/v10/";

    public static final String User = "User/";
    public static String Login_Url = Base_Url + User + "login";
    public static String Forgot_Password_Url = Base_Url + User + "forgotPassword";
    public static final String Timesheet = "Timesheet/";
    public static String CheckIn_Url = Base_Url + Timesheet + "checkIn";
    //    public static String timeSheetUrl = Base_Url + Timesheet + "get/1/2015-10-08/2015-10-07";
    public static String timeSheetUrl = Base_Url + Timesheet + "get/";
    public static String getSPEUrl = Base_Url + User + "getSpe";
    public static final String Location = "Location/";
    public static String getZonesUrl = Base_Url + Location + "getZone";

    public static String Vendor = "Vendor/";
    public static String getVendorsUrl = Base_Url + Vendor + "getVendor";
    public static String getVendorReviewsUrl = Base_Url + Vendor + "getReviews";
    public static String schools = "Schools/";
    public static String getSchoolsUrl = Base_Url + schools + "getSchools";
    public static String addVendorUrl = Base_Url + Vendor + "add";
    public static String getProductsUrl = Base_Url + Vendor + "autoCompleteItem/";
    public static String getProfileUrl = Base_Url + User + "getProfile/";
    public static String editProfileUrl = Base_Url + User + "editProfile";
    public static String getSpePhotoUrl = Base_Url + User + "getThumbs/";
    public static String redeemExcessHoursUrl = Base_Url + Timesheet + "redeemExcessHours";
    public static String applyExcessHoursUrl = Base_Url + Timesheet + "applyExcessHours";
    public static String Leaves = "Leaves/";
    public static String applyLeaveUrl = Base_Url + Leaves + "applyLeave";
    public static String speLeavesStatusUrl = Base_Url + Leaves + "getSpeLeavesStatus/";
    public static String addRemarksUrl = Base_Url + Timesheet + "addRemark";
    public static String awayUrl = Base_Url + Timesheet + "away";
    public static String changePasswordUrl = Base_Url + User + "changePassword";
    public static String logoutUrl = Base_Url + User + "logout/";
    public static String getThumbsUrl = Base_Url + User + "getThumbs/";
    public static String getSpeLeavesUrl = Base_Url + Leaves + "getSpeLEaves/";
    public static String submitTimesheetUrl = Base_Url + Timesheet + "submitTimesheet/";
    public static String Notifications = "Notification/";
    public static String notificationsUrl = Base_Url + Notifications + "getNotifications/";
    public static String Event = "Events/";
    public static String getEventsUrl = Base_Url + Event + "get/";
    public static String Vendor_Review_Url = Base_Url + Vendor + "review";
    public static String cancelLeaveUrl = Base_Url + Leaves + "cancelLeave/";
    public static String searchSpeUrl = Base_Url + User + "searchSpe/";
    public static String searchVendorUrl = Base_Url + Vendor + "searchVendor/";
    public static String deleteNotificationUrl = Base_Url + Notifications + "delete/";
    public static String getHolidaysUrl = Base_Url + "Holiday/get/";
    public static String getClinicsUrl = Base_Url + "clinic/get/";

    public static String Key_Login_id = "um_email";
    public static String Key_Password = "um_password";
    public static String Key_email = "um_email";
    public static String Key_nric = "um_NRIC";
    public static String Key_isCheckIn = "isChkIn";
    public static String Key_speId = "speId";
    public static String Key_um_device_type = "um_device_type";
    public static String Key_um_device_id = "um_device_id";

    //*******************chekIn/Out**********************
    public static String Key_tmId = "tm_id";
    public static String Key_tm_spe = "tm_spe";
    public static String Key_location = "location";
    public static String Key_Remark = "remark";
    public static String Key_timeStamp = "timestamp";
    public static String Key_tm_checkIn_isAway = "is_Away";
    public static String Key_Remarks_isCheckIn = "is_checkin";

    //**************Timesheet data***************
    public static String Key_timesheetDataList = "timesheetDataList";

    public static String Key_Position = "position";

    //*******************leave status*******************
    public static int Key_Applied_Leave_Fragment = 1;
    public static int Key_Approved_Leave_Fragment = 2;
    public static int Key_Declined_Leave_Fragment = 3;

    //******************camera********************
    public static final int PICK_FROM_CAMERA = 1001;
    public static final int CROP_FROM_CAMERA = 1002;
    public static final int PICK_FROM_FILE = 1003;
    public static final String SAVED_IMAGE_FOLDER_NAME = "NUVO";

    //******************SPE*************************
    public static String Key_SpeDetails_Json_String = "SPEDetailsJsonString";
    //******************Vendors*********************
    public static String Key_VendorsData_Json_String = "VendorDetailsJsonString";
    public static String Key_Vendor_id = "VendorId";
    public static String Key_VendorUserReview_Json_String = "VendorUserReviewJsonString";

    //******************Add Vendor*******************
    public static String Key_Vendor_add_name = "vm_name";
    public static String Key_Vendor_add_school = "vm_schools";
    public static String Key_Vendor_add_location = "vm_address";
    public static String Key_Vendor_add_postal_code = "VendorDetailsJsonString";
    public static String Key_Vendor_add_contact_person = "VendorDetailsJsonString";
    public static String Key_Vendor_add_office_no = "vm_phone1";
    public static String Key_Vendor_add_fax = "vm_fax";
    public static String Key_Vendor_add_email = "vm_email";
    public static String Key_Vendor_add_website = "vm_website";
    public static String Key_Vendor_add_prodct_name = "item";
    public static String Key_Vendor_add_price = "price";
    public static String Key_Spe = "spe";


    //*********************zones********************

    //*********************Select School************
    public static String Key_Select_School = "Select School";

    //*********************Select Product************
    public static String Key_Product_Name = "Product Name";
    public static String Key_Product_Price = "Product Price";

    //*****************Edit Profile********************
    public static String Key_User_Id = "user_id";
    public static String Key_User_Name = "um_name";
    public static String Key_Secondary_Email = "um_secondary_email";
    public static String Key_Photo = "um_photo";
    public static String Key_Phone = "um_phone";
    public static String Key_Primary_Email = "um_email";
    public static String Key_Remove_Photo = "remove_photo";

    //****************Redeem Excess Hours***************
    public static String Key_redeem_hours = "redeem_hours";
    public static String Key_excess_hours = "excess_hours";

    //***************Apply Leave************************
    public static String Key_spe = "spe";
    public static String Key_leave_from = "leave_from";
    public static String Key_leave_to = "leave_to";
    public static String Key_leave_type = "leave_type";
    public static String Key_is_halfday = "is_halfday";
    public static String Key_halfday_type = "halfday_type";
    public static String Key_leave_document = "leave_document";
    public static String leave_description = "leave_description";
    public static String Key_Balance_Leaves = "Key_Balance_Leaves";

    //**************Away********************
    public static String Key_tm_away = "tm_away";
    public static String Key_tm_away_type = "tm_away_type";
    public static String Key_tm_half_day_type = "tm_halfday_type";
    public static String Key_tm_away_remark = "tm_away_remark";
    public static String Key_tm_away_hours = "tm_away_hours";
    public static String Key_tm_away_date = "tm_away_date";

    //************change password**************
    public static String Key_OldPassword = "old_password";
    public static String Key_NewPassword = "new_password";
    public static String Key_CPSpeId = "spe_id";

    //************Notifications****************
    public static String isNotification = "isNotification";
    public static String leaveId = "leaveId";
    public static String Key_isNotificationsEnabled = "isNotificationsEnabled";
    //************Auth Err**********************
    public static String Key_Auth_Err = "Auth_Err";

    //*************Leave Type******************
    public static String Key_LeaveType_Str = "Key_LeaveType_Str";

    //****************isLogOut******************
    public static String Key_isLogOut = "Key_isLogOut";

    //***************Vendor Review**************
    public static String Key_Vendor_Review_Title = "vr_review";
    public static String Key_Vendor_Review_Rating = "vr_rating";
    public static String Key_Vr_Vendor = "vr_vendor";
    public static String Key_Vr_Spe = "vr_spe";

    //**************Clinic Details**************
    public static String Key_Clinic_Details_Json_String = "clinicDetailsJsonString";

}
