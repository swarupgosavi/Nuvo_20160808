package xyz.theapptest.nuvo.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.services.VolleyWebserviceCallBack;
import xyz.theapptest.nuvo.utils.CameraHelperLib;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Constants;
import xyz.theapptest.nuvo.utils.Dialogs;
import xyz.theapptest.nuvo.utils.Utility;
import xyz.theapptest.nuvo.widget.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by trtcpu007 on 7/7/16.
 */
public class RegistrationActivity extends Activity implements View.OnClickListener, VolleyWebserviceCallBack {
    TextView tv_register, tv_title;
    EditText ed_firstname, ed_lastname, ed_email, ed_password, ed_companyname;
    Button bt_signup;
    ImageView img_back;
    String strRole;
    String regEx;
    String usertype;
    ImageView img_Camera;
    HashMap<String, String> parameter;
    Context context = RegistrationActivity.this;
    File imageFile;
    private ProgressDialog dialog = null;
    private int serverResponseCode = 0;
    private String upLoadServerUri = null;
    private String imagepath = null;
    String strrollmsg;
    static RoundedImageView userImv;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    CameraHelperLib camerahelp;
    Uri picUri;
    String userImagePath;
    private int mPreviousInputType;
    ConstantData constantData;
    private boolean mIsShowingPassword;
    ProgressDialog asyncDialog;
    private TextView messageText;
    Context con = RegistrationActivity.this;
    JSONArray age = null;
    JSONArray characteristics = null;
    JSONArray job_category = null;
    JSONArray langaugesd = null;
    JSONArray recording_methods = null;
    CustomizeDialog customizeDialog = null;
    private static final String TAG_AGE = "age";
    private static final String TAG_CHARACTERISTICS = "characteristics";
    private static final String TAG_JOBCATEGORY = "job_category";
    private static final String TAG_LANGAUGE = "language";
    private static final String TAG_RECORDINGMEHOD = "recording_methods";
    private static final int REQUEST_CAMERA_RESULT = 1;

    ArrayList<HashMap<String, String>> agelist;
    ArrayList<HashMap<String, String>> characterlistvalue;
    ArrayList<HashMap<String, String>> jobtypevalue;
    ArrayList<HashMap<String, String>> langaugevalues;
    ArrayList<HashMap<String, String>> recordingmethodvaluesd;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String userChoosenTask;
    Bitmap thePic;
    ProgressDialog pd;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(xyz.theapptest.nuvo.R.layout.activity_registration);
        constantData = ConstantData.getInstance();
        typeofuser();
        init();
        emailidexpression();
        typeInterface();
        checkEdittextPassword();
        onClickevent();
        if (usertype.equalsIgnoreCase("Agent")) {
            openCamera();
        }
        pd = new ProgressDialog(RegistrationActivity.this);


        agelist = new ArrayList<HashMap<String, String>>();
        characterlistvalue = new ArrayList<HashMap<String, String>>();
        jobtypevalue = new ArrayList<HashMap<String, String>>();
        langaugevalues = new ArrayList<HashMap<String, String>>();
        recordingmethodvaluesd = new ArrayList<HashMap<String, String>>();
    }

    private void openCamera() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {

                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        Toast.makeText(this, "No Permission to use the Camera services", Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_RESULT);
                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_RESULT:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Cannot run application because camera service permission have not been granted", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void typeofuser() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            usertype = extras.getString("Screentype");
        }
    }

    private void checkEdittextPassword() {
        ed_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (ed_password.getRight() - ed_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (mIsShowingPassword) {
                            showPassword();
                        } else {
                            hidePassword();
                        }
                    } else {
                    }
                }
                return false;
            }
        });
    }

    private void setInputType(int inputType, boolean keepState) {
        int selectionStart = -1;
        int selectionEnd = -1;
        if (keepState) {
            selectionStart = ed_password.getSelectionStart();
            selectionEnd = ed_password.getSelectionEnd();
        }
        ed_password.setInputType(inputType);
        if (keepState) {
            ed_password.setSelection(selectionStart, selectionEnd);
        }
    }

    public void showPassword() {
        mIsShowingPassword = false;
        setInputType(mPreviousInputType, true);
        mPreviousInputType = -1;
        if (null != mOnPasswordDisplayListener) {
            mOnPasswordDisplayListener.onPasswordShow();
        }
    }

    public void hidePassword() {
        mPreviousInputType = ed_password.getInputType();
        mIsShowingPassword = true;
        setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD, true);
        if (null != mOnPasswordDisplayListener) {
            mOnPasswordDisplayListener.onPasswordHide();
        }
    }

    public interface OnPasswordDisplayListener {
        public void onPasswordShow();

        public void onPasswordHide();
    }

    SignInActivity.OnPasswordDisplayListener mOnPasswordDisplayListener;

    public void setOnPasswordDisplayListener(SignInActivity.OnPasswordDisplayListener listener) {
        mOnPasswordDisplayListener = listener;
    }

    private void onClickevent() {
        bt_signup.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    public void init() {
        tv=(TextView)findViewById(xyz.theapptest.nuvo.R.id.aad_artist_your_logo);
        tv_title = (TextView) findViewById(xyz.theapptest.nuvo.R.id.toolbar_title);
        tv_register = (TextView) findViewById(xyz.theapptest.nuvo.R.id.tv_register);
        ed_firstname = (EditText) findViewById(xyz.theapptest.nuvo.R.id.ed_firstname);
        ed_lastname = (EditText) findViewById(xyz.theapptest.nuvo.R.id.ed_lastname);
        ed_email = (EditText) findViewById(xyz.theapptest.nuvo.R.id.ed_email);
        tv=(TextView)findViewById(xyz.theapptest.nuvo.R.id.aad_artist_your_logo);
        if (usertype.equalsIgnoreCase("Artist")) {
            img_Camera = (ImageView) findViewById(xyz.theapptest.nuvo.R.id.edit_profile_user_imv_id);
            img_Camera.setVisibility(View.GONE);
            tv.setVisibility(View.GONE);
            ed_companyname = (EditText) findViewById(xyz.theapptest.nuvo.R.id.ed_companyname);
            ed_companyname.setVisibility(View.GONE);
        }
        if (usertype.equalsIgnoreCase("Producer")) {
            img_Camera = (ImageView) findViewById(xyz.theapptest.nuvo.R.id.edit_profile_user_imv_id);
            img_Camera.setVisibility(View.GONE);
            tv.setVisibility(View.GONE);
            ed_companyname = (EditText) findViewById(xyz.theapptest.nuvo.R.id.ed_companyname);
            ed_companyname.setVisibility(View.VISIBLE);
        }
        if (usertype.equalsIgnoreCase("Agent")) {
            img_Camera = (ImageView) findViewById(xyz.theapptest.nuvo.R.id.edit_profile_user_imv_id);
            img_Camera.setVisibility(View.VISIBLE);
            tv_register.setVisibility(View.GONE);
            ed_companyname = (EditText) findViewById(xyz.theapptest.nuvo.R.id.ed_companyname_agent);
            ed_companyname.setVisibility(View.VISIBLE);
            img_Camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();
                /*    checkPermissionForCamera(RegistrationActivity.this);
                    camerahelp = new CameraHelperLib(context);
                    //camerahelp.editProfileImageChooser();
                    camerahelp.imageChooser();*/
                }
            });
        }
        ed_password = (EditText) findViewById(xyz.theapptest.nuvo.R.id.ed_password);
        bt_signup = (Button) findViewById(xyz.theapptest.nuvo.R.id.signup);
        img_back = (ImageView) findViewById(xyz.theapptest.nuvo.R.id.back);
    }

    public void typeInterface() {
        Typeface facetxttitle = Typeface.createFromAsset(getAssets(),
                "font/Vonique 64.ttf");
        tv_title.setTypeface(facetxttitle);
        Typeface facetxtsigin = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Semibold.ttf");
        tv_register.setTypeface(facetxtsigin);
        bt_signup.setTypeface(facetxtsigin);
        Typeface facetxtsigintextbox = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Regular.ttf");
        ed_firstname.setTypeface(facetxtsigintextbox);
        ed_lastname.setTypeface(facetxtsigintextbox);
        ed_email.setTypeface(facetxtsigintextbox);
        ed_password.setTypeface(facetxtsigintextbox);
        if (usertype.equalsIgnoreCase("Producer")) {
            ed_companyname = (EditText) findViewById(xyz.theapptest.nuvo.R.id.ed_companyname);
            ed_companyname.setVisibility(View.VISIBLE);
            ed_companyname.setTypeface(facetxtsigintextbox);
        }
        if (usertype.equalsIgnoreCase("Artist")) {
            img_Camera = (ImageView) findViewById(xyz.theapptest.nuvo.R.id.edit_profile_user_imv_id);
            img_Camera.setVisibility(View.GONE);
            ed_companyname = (EditText) findViewById(xyz.theapptest.nuvo.R.id.ed_companyname);
            ed_companyname.setVisibility(View.GONE);
        }
        if (usertype.equalsIgnoreCase("Agent")) {
            img_Camera = (ImageView) findViewById(xyz.theapptest.nuvo.R.id.edit_profile_user_imv_id);
            img_Camera.setVisibility(View.VISIBLE);
            ed_companyname = (EditText) findViewById(xyz.theapptest.nuvo.R.id.ed_companyname_agent);
            ed_companyname.setVisibility(View.VISIBLE);
            ed_companyname.setTypeface(facetxtsigintextbox);
            img_Camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();
                   /* camerahelp = new CameraHelperLib(context);
                    //camerahelp.editProfileImageChooser();
                    camerahelp.imageChooser();*/
                }
            });
        }
    }

    private void emailidexpression() {
        regEx =
                "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
    }

    private void registerUser(final String firstnames, final String lastname, final String email, final String password, final String role, final String comapny, final String phone, final String argency_logo) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://nuvo.theapptest.xyz/v2/api/user/register",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(WebApiCall.KEY_FIRSTNAME, firstnames);
                params.put(WebApiCall.KEY_LASTNAME, lastname);
                params.put(WebApiCall.KEY_EMAIL, email);
                params.put(WebApiCall.KEY_PASSWORD, password);
                params.put(WebApiCall.KEY_ROLE, role);
                params.put(WebApiCall.KEY_COMPANY, comapny);
                params.put(WebApiCall.KEY_PHONE, phone);
                params.put(WebApiCall.KEY_ARGENCY_LOGO, argency_logo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case xyz.theapptest.nuvo.R.id.signup:
                String strfirstname, strlastname, strcompanyname, strEmailAddress, strPassword, strRole = null;
                //   String imgupl = "";
                strfirstname = ed_firstname.getText().toString().trim();
                strlastname = ed_lastname.getText().toString().trim();
                if (usertype.equalsIgnoreCase("Producer")) {
                    ed_companyname.setVisibility(View.VISIBLE);
                    strcompanyname = ed_companyname.getText().toString().trim();
                }
                if (usertype.equalsIgnoreCase("Agent")) {
                    img_Camera = (ImageView) findViewById(xyz.theapptest.nuvo.R.id.edit_profile_user_imv_id);
                    img_Camera.setVisibility(View.VISIBLE);
                    ed_companyname = (EditText) findViewById(xyz.theapptest.nuvo.R.id.ed_companyname_agent);
                    ed_companyname.setVisibility(View.VISIBLE);
                    strcompanyname = ed_companyname.getText().toString().trim();
                    // openCamera();


                    img_Camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            selectImage();

                           /* camerahelp = new CameraHelperLib(context);
                            //camerahelp.editProfileImageChooser();
                            camerahelp.imageChooser();*/
                        }
                    });
                }
                if (usertype.equalsIgnoreCase("Artist")) {
                    img_Camera = (ImageView) findViewById(xyz.theapptest.nuvo.R.id.edit_profile_user_imv_id);
                    img_Camera.setVisibility(View.GONE);
                    ed_companyname.setVisibility(View.GONE);
                }
                strEmailAddress = ed_email.getText().toString().trim();
                strPassword = ed_password.getText().toString().trim();
                constantData.setEmail(strEmailAddress);
                constantData.setPassword(strPassword);

                if ((usertype.equalsIgnoreCase("Agent") && (ed_companyname.getText().toString().matches("")))) {


                    customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Please enter company name.");
                    customizeDialog.show();
                } else if (strfirstname.matches("")) {
                    customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Please enter first name.");
                    customizeDialog.show();


                    //  DialogShowMethods.showDialog(RegistrationActivity.this, "Error", "Please enter first name.");
                } else {
                    if (strlastname.matches("")) {

                        customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                        customizeDialog.setTitle("nuvo");
                        customizeDialog.setCancelable(false);
                        customizeDialog.setMessage("Please enter last name.");
                        customizeDialog.show();


                        //   DialogShowMethods.showDialog(RegistrationActivity.this, "Error", "Please enter last name.");
                    } else {
                        if (strEmailAddress.matches("")) {

                            customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                            customizeDialog.setTitle("nuvo");
                            customizeDialog.setCancelable(false);
                            customizeDialog.setMessage("Please enter valid email.");
                            customizeDialog.show();


                            //  DialogShowMethods.showDialog(RegistrationActivity.this, "Error", "Please enter valid email.");
                        } else {
                            Pattern pattern;
                            pattern = Pattern.compile(regEx);
                            Matcher matcher = pattern.matcher(strEmailAddress);
                            if (!matcher.find()) {
                                Log.e("Invalid email", "Invalid email");

                                customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                                customizeDialog.setTitle("nuvo");
                                customizeDialog.setCancelable(false);
                                customizeDialog.setMessage("Please enter valid email.");
                                customizeDialog.show();


                                //  DialogShowMethods.showDialog(RegistrationActivity.this, "Error", "Please enter valid email.");
                            } else {
                                if (strPassword.matches("")) {

                                    customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                                    customizeDialog.setTitle("nuvo");
                                    customizeDialog.setCancelable(false);
                                    customizeDialog.setMessage("Please enter password.");
                                    customizeDialog.show();


                                    //  DialogShowMethods.showDialog(RegistrationActivity.this, "Error", "  Please enter password.");
                                } else {
                                 /*   if ((usertype.equalsIgnoreCase("Agent") && (ed_companyname.getText().toString().matches("")))) {


                                        customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                                        customizeDialog.setTitle("Error");
                                        customizeDialog.setCancelable(false);
                                        customizeDialog.setMessage("Please enter company name.");
                                        customizeDialog.show();


                                        //  DialogShowMethods.showDialog(RegistrationActivity.this, "Error", "Please enter company name.");
                                    } else {
*/

                                    Log.e("parameter call", "calling parameter");
                                    parameter = new HashMap<String, String>();
                                    parameter.put(WebApiCall.KEY_EMAIL, ed_email.getText().toString().trim());
                                    parameter.put(WebApiCall.KEY_PASSWORD, ed_password.getText().toString().trim());
                                    if (usertype.equalsIgnoreCase("Producer")) {
                                        strRole = "6";
                                        parameter.put(WebApiCall.KEY_FIRSTNAME, ed_firstname.getText().toString().trim());
                                        parameter.put(WebApiCall.KEY_LASTNAME, ed_lastname.getText().toString().trim());
                                        parameter.put(WebApiCall.KEY_COMPANY, ed_companyname.getText().toString().trim());
                                    } else {
                                        if (usertype.equalsIgnoreCase("Agent")) {
                                            strRole = "5";
                                            parameter.put(WebApiCall.KEY_FIRSTNAME, ed_firstname.getText().toString().trim());
                                            parameter.put(WebApiCall.KEY_LASTNAME, ed_lastname.getText().toString().trim());
                                            parameter.put(WebApiCall.KEY_COMPANY, ed_companyname.getText().toString().trim());
                                            parameter.put(WebApiCall.KEY_ARGENCY_LOGO, "");
                                        } else {
                                            if (usertype.equalsIgnoreCase("Artist")) {
                                                strRole = "4";
                                                parameter.put(WebApiCall.KEY_FIRSTNAME, ed_firstname.getText().toString().trim());
                                                parameter.put(WebApiCall.KEY_LASTNAME, ed_lastname.getText().toString().trim());
                                            }
                                        }
                                    }
                                    parameter.put(WebApiCall.KEY_ROLE, strRole);
                                    strrollmsg = strRole;
                                    if (CheckInternetConnection.isConnected(RegistrationActivity.this)) {


                                        Log.e("parameter call", "calling parameter-webservice");
                                        Log.e("firstname", ed_firstname.getText().toString().trim());
                                        Log.e("lastname", ed_lastname.getText().toString().trim());
                                        Log.e("email", ed_email.getText().toString().trim());
                                        Log.e("password", ed_password.getText().toString().trim());
                                        Log.e("strrole", strRole);
                                        Log.e("companyname", ed_companyname.getText().toString().trim());
                                        if (usertype.equalsIgnoreCase("Agent")) {

                                            imageUploadForAgent();

                                        } else {
                                            dialog = new ProgressDialog(this,ProgressDialog.THEME_HOLO_LIGHT);
                                            dialog.setMessage("Please Wait!!");
                                            dialog.setCancelable(false);
                                            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                            dialog.show();
                                            Thread mThread = new Thread() {
                                                @Override
                                                public void run() {
                                                    String imgupl = "";
                                                    try {

                                                        GetText(ed_firstname.getText().toString().trim(), ed_lastname.getText().toString().trim(), ed_email.getText().toString().trim(), ed_password.getText().toString().trim(), strrollmsg, ed_companyname.getText().toString().trim(), "", imgupl);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            };
                                            mThread.start();




                                             /*   new Thread() {

                                                    public void run() {

                                                        runOnUiThread(new Runnable() {

                                                            public void run() {

                                                                String imgupl = "";
                                                                try {

                                                                   GetText(ed_firstname.getText().toString().trim(), ed_lastname.getText().toString().trim(), ed_email.getText().toString().trim(), ed_password.getText().toString().trim(), strrollmsg, ed_companyname.getText().toString().trim(), "", imgupl);
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }


                                                            }
                                                        });

                                                    }
                                                }.start();*/

                                                /*try {

                                                    GetText(ed_firstname.getText().toString().trim(), ed_lastname.getText().toString().trim(), ed_email.getText().toString().trim(), ed_password.getText().toString().trim(), strrollmsg, ed_companyname.getText().toString().trim(), "", imgupl);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }*/
                                        }
                                    } else {

                                        customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                                        customizeDialog.setTitle("nuvo");
                                        customizeDialog.setCancelable(false);
                                        customizeDialog.setMessage("Internet not available, Please check your Internet connectivity and try again.");
                                        customizeDialog.show();

                                        // Dialogs.showNoConnectionDialog(RegistrationActivity.this);
                                    }

                                }
                                // }
                            }
                        }
                    }
                }
                break;
            case xyz.theapptest.nuvo.R.id.back:
                Intent i=new Intent(RegistrationActivity.this,SignUpActivity.class);
                startActivity(i);
                finish();
                break;
            default:
                break;
        }
    }


    public void imageUploadForAgent() {

        String url = WebApiCall.baseURl + "user/register";

        checkPermissionForCamera(RegistrationActivity.this);
        SendHttpRequestTask t = new SendHttpRequestTask();

        String[] params = new String[]{url, ed_firstname.getText().toString().trim(), ed_lastname.getText().toString().trim(), ed_email.getText().toString().trim(), ed_password.getText().toString().trim(), strrollmsg, ed_companyname.getText().toString().trim(), "0"};
        t.execute(params);

    }


    public void checkPermissionForCamera(RegistrationActivity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }


    }


    private class SendHttpRequestTask extends AsyncTask<String, Void, String> {
        ByteArrayOutputStream baos;
        String response;
        ProgressDialog imgdialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgdialog = new ProgressDialog(RegistrationActivity.this,ProgressDialog.THEME_HOLO_LIGHT);
            imgdialog.setMessage("Please Wait!!");
            imgdialog.setCancelable(false);
            imgdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            imgdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String firtsname = params[1];
            String lastname = params[2];
            String email = params[3];
            String password = params[4];
            String strrolemsg = params[5];
            String company = params[6];
            String phone = params[7];

            // Bitmap b = BitmapFactory.decodeResource(ImageUploadActivity.this.getResources(), userImagePath);
            try {
                baos = new ByteArrayOutputStream();
                HttpClient client = new HttpClient(url);
                client.connectForMultipart();
                client.addFormPart("first_name", firtsname);
                client.addFormPart("last_name", lastname);
                client.addFormPart("email", email);
                client.addFormPart("password", password);
                client.addFormPart("role", strrolemsg);
                client.addFormPart("company", company);
                client.addFormPart("phone", phone);

                //  InputStream inStream = context.getResources().openRawResource(R.raw.cheerapp);
                //  byte[] music = new byte[inStream.available()];


                if (thePic == null) {

                } else {
                    thePic.compress(Bitmap.CompressFormat.PNG, 0, baos);
                    String out = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date());

                    client.addFilePart("agency_logo", out + "." + "png", baos.toByteArray());


                    client.finishMultipart();
                }
                /*if (!baos.equals("")) {

                    client.addFilePart("agency_logo", "agent.png", baos.toByteArray());
                    client.finishMultipart();
                    response = client.getResponse();
                } else {

                    response = client.getResponse();
                }
*/

                response = client.getResponse();
            } catch (Throwable t) {
                imgdialog.dismiss();
                t.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            if (response != null && !response.isEmpty()) {
                imgdialog.dismiss();
                String message_code = "";
                String user_id = "";
                if (response.contains("user_id")) {
                    try {
                        JSONObject userObject = new JSONObject(response);
                        message_code = userObject.getString("message_code");
                        user_id = userObject.getString("user_id");
                        String token = userObject.getString("token");
                        if (user_id != null && !user_id.equals("")) {
                            Log.e("user_id", user_id);
                            constantData.setUserid(user_id);
                            constantData.setTokenid(token);
                        }
                        Log.e("message_code", message_code);
                    } catch (Exception ex) {
                        //don't forget this
                        ex.printStackTrace();
                    }
                    if (message_code.equalsIgnoreCase("150")) {

                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(xyz.theapptest.nuvo.R.layout.iphone_alertdialog);
                        //  dialog.setTitle("Success");
                        dialog.setCancelable(false);
                        // set the custom dialog components - text, image and button
                        TextView text1 = (TextView) dialog.findViewById(xyz.theapptest.nuvo.R.id.dialogTitle);
                        text1.setText("Success");


                        TextView textmessage = (TextView) dialog.findViewById(xyz.theapptest.nuvo.R.id.dialogMessage);
                        textmessage.setText("Your registration is successful. Registration email is sent to your email address.");


                        Button dialogButton = (Button) dialog.findViewById(xyz.theapptest.nuvo.R.id.OkButton);
                        // if button is clicked, close the custom dialog
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RegistrationActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        Intent sendingemail = new Intent(RegistrationActivity.this, SendingEmailActivity.class);
                                        sendingemail.putExtra("typeofuser", usertype);
                                        sendingemail.putExtra("action", "registration");
                                        sendingemail.putExtra("firstname", ed_firstname.getText().toString());
                                        sendingemail.putExtra("lastname", ed_lastname.getText().toString());
                                        sendingemail.putExtra("email", ed_email.getText().toString());
                                        sendingemail.putExtra("password", ed_password.getText().toString());
                                        startActivity(sendingemail);
                                        finish();
                                    }
                                });

                            }
                        });

                        dialog.show();





                    /*    new AlertDialog.Builder(context)
                                .setTitle("Success")
                                .setCancelable(false)
                                .setMessage("Your registration is successful. Registration email is sent to your email address.")
                                .setPositiveButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                RegistrationActivity.this.runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        Intent sendingemail = new Intent(RegistrationActivity.this, SendingEmailActivity.class);
                                                        sendingemail.putExtra("typeofuser", usertype);
                                                        sendingemail.putExtra("firstname", ed_firstname.getText().toString());
                                                        sendingemail.putExtra("lastname", ed_lastname.getText().toString());
                                                        sendingemail.putExtra("email", ed_email.getText().toString());
                                                        sendingemail.putExtra("password", ed_password.getText().toString());
                                                        startActivity(sendingemail);
                                                    }
                                                });
                                                dialog.dismiss();
                                            }
                                        }).show();*/
                    }
                } else {
                    try {
                        JSONObject userObject = new JSONObject(response);
                        message_code = userObject.getString("message_code");
                        Log.e("messagecode", message_code);
                        if (message_code.equalsIgnoreCase("104")) {
                            customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                            customizeDialog.setTitle("nuvo");
                            customizeDialog.setCancelable(false);
                            customizeDialog.setMessage("Unable to do the registration. This email is already registered.");
                            customizeDialog.show();


                        } else {
                            if (message_code.equalsIgnoreCase("100")) {
                                customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                                customizeDialog.setTitle("nuvo");
                                customizeDialog.setCancelable(false);
                                customizeDialog.setMessage("Unable to register. Please check your data and try again.");
                                customizeDialog.show();


                                // Dialogs.showAlertcommon(RegistrationActivity.this, "Error", "Unable to register. Please check your data and try again.");
                            } else {
                                if (message_code.equalsIgnoreCase("105")) {
                                    customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                                    customizeDialog.setTitle("nuvo");
                                    customizeDialog.setCancelable(false);
                                    customizeDialog.setMessage("Unable to registration. Invalid user ID.");
                                    customizeDialog.show();


                                    // Dialogs.showAlertcommon(RegistrationActivity.this, "Error", "Unable to registration. Invalid user ID.");
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }


        }


    }


    public void GetText(final String firstnames, final String lastname, final String email, final String password, final String role, final String comapny, final String phone, final String argency_logo) throws UnsupportedEncodingException {
        //    ProgressDialog pd = new ProgressDialog(RegistrationActivity.this);
        //pd.setMessage("loading");
        //  pd.show();

        String data = URLEncoder.encode(WebApiCall.KEY_FIRSTNAME, "UTF-8")
                + "=" + URLEncoder.encode(firstnames, "UTF-8");
        data += "&" + URLEncoder.encode(WebApiCall.KEY_LASTNAME, "UTF-8") + "="
                + URLEncoder.encode(lastname, "UTF-8");
        data += "&" + URLEncoder.encode(WebApiCall.KEY_EMAIL, "UTF-8")
                + "=" + URLEncoder.encode(email, "UTF-8");
        data += "&" + URLEncoder.encode(WebApiCall.KEY_PASSWORD, "UTF-8")
                + "=" + URLEncoder.encode(password, "UTF-8");
        data += "&" + URLEncoder.encode(WebApiCall.KEY_ROLE, "UTF-8")
                + "=" + URLEncoder.encode(role, "UTF-8");
        data += "&" + URLEncoder.encode(WebApiCall.KEY_COMPANY, "UTF-8")
                + "=" + URLEncoder.encode(comapny, "UTF-8");
        data += "&" + URLEncoder.encode(WebApiCall.KEY_PHONE, "UTF-8")
                + "=" + URLEncoder.encode(phone, "UTF-8");
        data += "&" + URLEncoder.encode(WebApiCall.KEY_ARGENCY_LOGO, "UTF-8")
                + "=" + URLEncoder.encode(argency_logo, "UTF-8");
        Log.e("data", data);
        String text = "";
        BufferedReader reader = null;
        // Send data
        try {
            // Defined URL  where to send data
            String registerUrl = WebApiCall.baseURl + "user/register";
            URL url = new URL(registerUrl);
            // Send POST data request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }
            text = sb.toString();
            Log.e("text", text);
        } catch (Exception ex) {
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
            }
        }
        if (text != null && !text.isEmpty()) {
            //  pd.dismiss();
            dialog.dismiss();
            String message_code = "";
            String user_id = "";
            if (text.contains("user_id")) {
                try {
                    JSONObject userObject = new JSONObject(text);
                    message_code = userObject.getString("message_code");
                    user_id = userObject.getString("user_id");
                    if (user_id != null && !user_id.equals("")) {
                        Log.e("user_id", user_id);
                        constantData.setUserid(user_id);
                    }
                    Log.e("message_code", message_code);
                } catch (Exception ex) {
                    //don't forget this
                    ex.printStackTrace();
                }
                if (message_code.equalsIgnoreCase("150")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                final Dialog dialog = new Dialog(context);
                                dialog.setContentView(xyz.theapptest.nuvo.R.layout.iphone_alertdialog);
                                //  dialog.setTitle("Success");
                                dialog.setCancelable(false);
                                // set the custom dialog components - text, image and button
                                TextView text1 = (TextView) dialog.findViewById(xyz.theapptest.nuvo.R.id.dialogTitle);
                                text1.setText("Success");


                                TextView textmessage = (TextView) dialog.findViewById(xyz.theapptest.nuvo.R.id.dialogMessage);
                                textmessage.setText("Your registration is successful. Registration email is sent to your email address.");


                                Button dialogButton = (Button) dialog.findViewById(xyz.theapptest.nuvo.R.id.OkButton);
                                // if button is clicked, close the custom dialog
                                dialogButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            dialog.dismiss();
                                            GetLoginDetail(ed_email.getText().toString().trim(), ed_password.getText().toString().trim());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                                dialog.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });






                /*    new AlertDialog.Builder(context)
                            .setTitle("Success")
                            .setCancelable(false)
                            .setMessage("Your registration is successful. Registration email is sent to your email address.")
                            .setPositiveButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            RegistrationActivity.this.runOnUiThread(new Runnable() {
                                                public void run() {

                                                    try {
                                                        GetLoginDetail(ed_email.getText().toString().trim(), ed_password.getText().toString().trim());
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }


                                                }
                                            });
                                            dialog.dismiss();
                                        }
                                    }).show();*/
                }
            } else {
                try {
                    JSONObject userObject = new JSONObject(text);
                    String message_code_error = userObject.getString("message_code").trim();
                    Log.e("msg-registration", message_code);
                    if (message_code_error.equalsIgnoreCase("104")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("email-alrreg", "email-alrreg");
                                customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                                customizeDialog.setTitle("nuvo");
                                customizeDialog.setCancelable(false);
                                customizeDialog.setMessage("User email id already exists.");
                                customizeDialog.show();
                            }
                        });


                        Dialogs.showAlertcommon(RegistrationActivity.this, "Error", "Unable to do the registration. This email is already registered.");
                    } else {
                        if (message_code_error.equalsIgnoreCase("100")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                                    customizeDialog.setTitle("nuvo");
                                    customizeDialog.setCancelable(false);
                                    customizeDialog.setMessage("Unable to register. Please check your data and try again.");
                                    customizeDialog.show();
                                }
                            });
                            //   Dialogs.showAlertcommon(RegistrationActivity.this, "Error", "Unable to register. Please check your data and try again.");
                        } else {
                            if (message_code_error.equalsIgnoreCase("105")) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        customizeDialog = new CustomizeDialog(RegistrationActivity.this);
                                        customizeDialog.setCancelable(false);
                                        customizeDialog.setTitle("nuvo");
                                        customizeDialog.setMessage("Unable to registration. Invalid user ID.");
                                        customizeDialog.show();
                                    }
                                });
                                //  Dialogs.showAlertcommon(RegistrationActivity.this, "Error", "Unable to registration. Invalid user ID.");
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    private void GetLoginDetail(String email, String password) {
        try {


            String data = URLEncoder.encode(WebApiCall.KEY_EMAIL, "UTF-8")
                    + "=" + URLEncoder.encode(email, "UTF-8");

            data += "&" + URLEncoder.encode(WebApiCall.KEY_PASSWORD, "UTF-8") + "="
                    + URLEncoder.encode(password, "UTF-8");

            String text = "";
            BufferedReader reader = null;

            // Send data
            try {

                // Defined URL  where to send data
                String loginUrl = WebApiCall.baseURl + "user/login";
                URL url = new URL(loginUrl);

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }


                text = sb.toString();
                Log.e("text", text);
                if (text == null && text.equals("")) {
                    Dialogs.showAlertcommon(RegistrationActivity.this, "Error", "Please enter valid username and password to login.");
                }
            } catch (Exception ex) {

            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                }
            }
            String message_code = "";
            String user_id = "";
            String token = "";

            if (text != null && !text.equals("")) {
                if (text.contains("user_id")) {
                    try {
                        JSONObject userObject = new JSONObject(text);
                        message_code = userObject.getString("message_code");
                        user_id = userObject.getString("user_id");
                        token = userObject.getString("token");
                        if (user_id != null && !user_id.equals("")) {
                            Log.e("user_id", user_id);
                            constantData.setTokenid(token);
                            constantData.setUserid(user_id);
                        }
                        Log.e("message_code", message_code);
                    } catch (Exception ex) {
                        //don't forget this
                        ex.printStackTrace();
                    }

                    if (message_code.equalsIgnoreCase("152")) {
                        constantData.setTokenid(token);
                        constantData.setUserid(user_id);

                        basicgetauthentication(user_id, token);
                        if (usertype.equalsIgnoreCase("Producer")) {

                            SharedPreferences preferences = getSharedPreferences("UserType", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("usertype", "Producer");
                            editor.putString("userid", user_id);
                            editor.putString("token", token);
                            editor.commit();

                            Intent sendingemail = new Intent(RegistrationActivity.this, SendingEmailActivity.class);
                            sendingemail.putExtra("typeofuser", usertype);
                            sendingemail.putExtra("action", "registration");
                            sendingemail.putExtra("firstname", ed_firstname.getText().toString());
                            sendingemail.putExtra("lastname", ed_lastname.getText().toString());
                            sendingemail.putExtra("email", ed_email.getText().toString());
                            sendingemail.putExtra("password", ed_password.getText().toString());

                        /*sendingemail.putExtra("age", agelist);
                        sendingemail.putExtra("characteristics", characterlistvalue);
                        sendingemail.putExtra("job_category", jobtypevalue);
                        sendingemail.putExtra("langauge", langaugevalues);
                        sendingemail.putExtra("recordingmethod", recordingmethodvaluesd);*/
                            startActivity(sendingemail);
                            finish();
                        } else {
                            if (usertype.equalsIgnoreCase("Artist")) {
                                SharedPreferences preferences = getSharedPreferences("UserType", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("usertype", "Artist");
                                editor.putString("userid", user_id);
                                editor.putString("token", token);
                                editor.commit();
                                constantData.setUserid(user_id);
                                constantData.setPassword(password);

                                Intent sendingemail = new Intent(RegistrationActivity.this, SendingEmailActivity.class);
                                sendingemail.putExtra("typeofuser", usertype);
                                sendingemail.putExtra("action", "registration");
                                sendingemail.putExtra("firstname", ed_firstname.getText().toString());
                                sendingemail.putExtra("lastname", ed_lastname.getText().toString());
                                sendingemail.putExtra("email", ed_email.getText().toString());
                                sendingemail.putExtra("password", ed_password.getText().toString());
                                startActivity(sendingemail);
                                finish();

                            } else {

                                SharedPreferences preferences = getSharedPreferences("UserType", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("usertype", "Agent");
                                editor.putString("userid", user_id);
                                editor.putString("token", token);
                                editor.commit();

                                constantData.setUserid(user_id);
                                constantData.setTokenid(token);


                                Intent sendingemail = new Intent(RegistrationActivity.this, SendingEmailActivity.class);
                                sendingemail.putExtra("typeofuser", usertype);
                                sendingemail.putExtra("action", "registration");
                                sendingemail.putExtra("firstname", ed_firstname.getText().toString());
                                sendingemail.putExtra("lastname", ed_lastname.getText().toString());
                                sendingemail.putExtra("email", ed_email.getText().toString());
                                sendingemail.putExtra("password", ed_password.getText().toString());
                                startActivity(sendingemail);

                            }

                        /*sendingemail.putExtra("age", agelist);
                        sendingemail.putExtra("characteristics", characterlistvalue);
                        sendingemail.putExtra("job_category", jobtypevalue);
                        sendingemail.putExtra("langauge", langaugevalues);
                        sendingemail.putExtra("recordingmethod", recordingmethodvaluesd);*/


                        }
                    }
                } else {
                    try {
                        JSONObject userObject = new JSONObject(text);
                        message_code = userObject.getString("message_code");
                        Log.e("messagecode", message_code);

                        if (message_code.equalsIgnoreCase("103")) {
                            //   Dialogs.showAlertcommon(RegistrationActivity.this, "Error", "Login fails. Please enter valid password.");
                        } else {
                            if (message_code.equalsIgnoreCase("102")) {
                                //    Dialogs.showAlertcommon(RegistrationActivity.this, "Error", "Unable to login. Your login is inactive. Please check the verification mail or contact administrator.");
                            } else {
                                if (message_code.equalsIgnoreCase("105")) {
                                    //     Dialogs.showAlertcommon(RegistrationActivity.this, "Error", "Unable to login. Invalid user ID.");
                                } else {
                                    if (message_code.equalsIgnoreCase("101")) {
                                        //     Dialogs.showAlertcommon(RegistrationActivity.this, "Error", "Unable to update at this movement. Please check your details and try again.");
                                    }
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        // Dialogs.showAlertcommon(RegistrationActivity.this, "Error", "Please enter valid username and password to login.");
                    }
                }
            } else {
                // Dialogs.showAlertcommon(RegistrationActivity.this, "Error", "Please enter valid username and password to login.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void basicgetauthentication(String userid, String tokens) {
        try {



            String defaultUrl = WebApiCall.baseURl + "default";

            URL obj = new URL(defaultUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            String encoded = Base64.encodeToString((userid + ":" + tokens).getBytes("UTF-8"), Base64.NO_WRAP);
            //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
            con.setRequestProperty("Authorization", "Basic " + encoded);
            // optional default is GET
            con.setRequestMethod("GET");

            //add request header


            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + defaultUrl);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
            Log.e("GETresonse", response.toString());
            JSONObject jsonObj = new JSONObject(response.toString());
            Button myButton = new Button(this);
            LinearLayout layout = (LinearLayout) findViewById(xyz.theapptest.nuvo.R.id.toplv);
            Button[] buttons = new Button[agelist.size()];

            //gender logic


            age = jsonObj.getJSONArray(TAG_AGE);
            for (int i = 0; i < age.length(); i++) {
                JSONObject c = age.getJSONObject(i);
                String id = c.getString("id");

                String agetype = c.getString("age_type");
                String displayorder = c.getString("display_order");
                String agevale = c.getString("age_value");
                Log.e("id", id);
                Log.e("agetype", agetype);
                Log.e("displayorder", displayorder);
                Log.e("agevale", agevale);
                HashMap<String, String> ages = new HashMap<String, String>();
                ages.put("id", id);
                ages.put("age_type", agetype);
                ages.put("display_order", displayorder);
                ages.put("age_value", agevale);
                ages.put("age_selected", "0");
                agelist.add(ages);


            }

            constantData.setAgelist(agelist);






           /* for (int i = 0; i < agelist.size(); i++) {

                Button btn = new Button(RegistrationActivity.this);
                btn.setBackgroundResource(R.drawable.rounded_edittext3);
                btn.setTextSize(13);

                btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                btn.setTextColor(getApplication().getResources().getColor(R.color.white));
                btn.setText(agelist.get(i).get("age_type"));

                layout.addView(btn);
            }*/


         /*   for (int i = 0; i < agelist.size(); i++) {
                buttons[i] = new Button(this);
                buttons[i].setBackgroundResource(R.drawable.rounded_edittext3);
                LinearLayout ll = (LinearLayout) findViewById(R.id.toplv);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.addView(buttons[i], lp);
            }
*/

            characteristics = jsonObj.getJSONArray(TAG_CHARACTERISTICS);
            for (int i = 0; i < characteristics.length(); i++) {
                JSONObject c = characteristics.getJSONObject(i);
                String id = c.getString("id");
                String characteristicname = c.getString("characteristic_name");
                String displayorder = c.getString("display_order");
                String charcteristicvalue = c.getString("characteristics_value");
                HashMap<String, String> charcter = new HashMap<String, String>();
                charcter.put("id", id);
                charcter.put("characteristic_name", characteristicname);
                charcter.put("display_order", displayorder);
                charcter.put("characteristics_value", charcteristicvalue);
                charcter.put("characteristics_selected", "0");
                Log.e("characteristic_name", characteristicname);
                Log.e("characterics_value_json", charcteristicvalue);

                characterlistvalue.add(charcter);
                //  Log.e("id", id);
                //  Log.e("characteristicname", characteristicname);
                // Log.e("displayorder", displayorder);
                //  Log.e("charcteristicvalue", charcteristicvalue);


            }
            constantData.setCharacteristics(characterlistvalue);


            job_category = jsonObj.getJSONArray(TAG_JOBCATEGORY);
            for (int i = 0; i < job_category.length(); i++) {
                JSONObject c = job_category.getJSONObject(i);
                String id = c.getString("id");
                String categoryname = c.getString("category_name");
                String displayorder = c.getString("display_order");
                String categoryvalue = c.getString("category_value");
                HashMap<String, String> job = new HashMap<String, String>();
                job.put("id", id);
                job.put("category_name", categoryname);
                job.put("display_order", displayorder);
                job.put("category_value", categoryvalue);
                Log.e("category_value", categoryvalue);
                job.put("jobcategory_selected", "0");
                jobtypevalue.add(job);
                Log.e("id", id);
                Log.e("categoryname", categoryname);
                Log.e("displayorder", displayorder);
                Log.e("categoryvalue", categoryvalue);


            }
            constantData.setJob_category(jobtypevalue);


            langaugesd = jsonObj.getJSONArray(TAG_LANGAUGE);
            for (int i = 0; i < langaugesd.length(); i++) {
                JSONObject c = langaugesd.getJSONObject(i);
                String id = c.getString("id");
                String langaugename = c.getString("language_name");
                String displayorder = c.getString("display_order");
                String langaugevalue = c.getString("language_value");
                HashMap<String, String> lan = new HashMap<String, String>();
                lan.put("id", id);
                lan.put("language_name", langaugename);
                lan.put("display_order", displayorder);
                lan.put("language_value", langaugevalue);
                lan.put("language_selected", "0");
                Log.e("language_value", langaugevalue);
                langaugevalues.add(lan);


                Log.e("id", id);
                Log.e("langaugename", langaugename);
                Log.e("displayorder", displayorder);
                Log.e("langaugevalue", langaugevalue);

            }
            constantData.setLanguage(langaugevalues);


            recording_methods = jsonObj.getJSONArray(TAG_RECORDINGMEHOD);
            for (int i = 0; i < recording_methods.length(); i++) {
                JSONObject c = recording_methods.getJSONObject(i);
                String id = c.getString("id");
                String recordingmethods = c.getString("recording_method");
                String displayorder = c.getString("display_method");
                String recordingmethodvalue = c.getString("recording_method_value");
                HashMap<String, String> recor = new HashMap<String, String>();
                recor.put("id", id);
                recor.put("recording_method", recordingmethods);
                recor.put("display_order", displayorder);
                recor.put("recording_method_value", recordingmethodvalue);
                Log.e("recording_method_value", recordingmethodvalue);
                recordingmethodvaluesd.add(recor);


                Log.e("id", id);
                Log.e("recordingmethods", recordingmethods);
                Log.e("displayorder", displayorder);
                Log.e("recordingmethods", recordingmethods);

            }
            constantData.setRecording_methods(recordingmethodvaluesd);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
/*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int result1 = 0;
        result1 = resultCode;
        switch (requestCode) {
            case Constants.PICK_FROM_CAMERA:
                // chooserDialog.dismiss();
                try {
                    checkPermissionForCamera(RegistrationActivity.this);
                    // camerahelp = ManageFragment.getCameraHelpObj();
                    imageFile = camerahelp.getmImageCaptureUri();
                    if (imageFile != null && imageFile.exists()) {
                        //    camerahelp.startCropImage(camerahelp.getmImageCaptureUri());
                        String fileName = imageFile.getName();
                        userImagePath = imageFile.getPath();
                        constantData.setUserImagePath(userImagePath);
                        picUri = Uri.fromFile(imageFile); // convert path to
                        performCrop();
//                        attachmentNameTv.setVisibility(View.VISIBLE);
//                        attachmentNameTv.setText(fileName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Utils.showToast(getResources().getString(R.string.somethingWrong),
                    // mContext);
                }
                break;
            case Constants.CROP_FROM_CAMERA:
                try {
                    checkPermissionForCamera(RegistrationActivity.this);


                    data.getData();

                   // Bundle extras = data.getExtras();
                  // Log.e("extras",extras.toString());
                    // get the cropped bitmap
               //     thePic= extras.getp
                    //thePic = extras.getParcelable("data");

                    userImagePath = camerahelp.saveToInternalSorage(thePic);
                    constantData.setUserImagePath(userImagePath);
                    if (imageFile == null) {

                    } else {
                        img_Camera.setImageBitmap(thePic);
                        boolean isUpdatePhoto = true;
                        // updateProfile(isUpdatePhoto);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case Constants.PICK_FROM_FILE:

                try {
                    checkPermissionForCamera(RegistrationActivity.this);
                    InputStream inputStream = getContentResolver().openInputStream(
                            data.getData());
//                    File uploadPhotoFile = camerahelp
//                            .getNewFileForCamera(Constants.SAVED_IMAGE_FOLDER_NAME);
                    imageFile = camerahelp
                            .getNewFileForCamera(Constants.SAVED_IMAGE_FOLDER_NAME);
                    userImagePath = imageFile.getAbsolutePath();
                    constantData.setUserImagePath(userImagePath);
                    camerahelp.setmImageCaptureUri(imageFile);
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            imageFile);
                    camerahelp.copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    if (imageFile.exists()) {
                        picUri = Uri.fromFile(imageFile); // convert path to
                        performCrop();
//                        camerahelp.setmImageCaptureUri(uploadPhotoFile);
//                        camerahelp.startCropImage(camerahelp.getmImageCaptureUri());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    Log.e("onActivityResult:Gallery",
//                            "Error while creating temp file", e);
                }
                break;
        }
    }
*/

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri

            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, Constants.CROP_FROM_CAMERA);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onSuccess(String serverResult, String requestTag, int statusCode) {
        Log.e("result", serverResult);
        String message_code = "";
        String user_id = "";
        if (serverResult.contains("user_id")) {
            try {
                JSONObject userObject = new JSONObject(serverResult);
                message_code = userObject.getString("message_code");
                user_id = userObject.getString("user_id");
                if (user_id != null && !user_id.equals("")) {
                    Log.e("user_id", user_id);
                    constantData.setUserid(user_id);
                }
                Log.e("message_code", message_code);
            } catch (Exception ex) {
                //don't forget this
                ex.printStackTrace();
            }
            if (message_code.equalsIgnoreCase("150")) {
                Intent sendingemail = new Intent(RegistrationActivity.this, SendingEmailActivity.class);
                sendingemail.putExtra("typeofuser", usertype);
                sendingemail.putExtra("action", "registration");
                sendingemail.putExtra("firstname", ed_firstname.getText().toString());
                sendingemail.putExtra("lastname", ed_lastname.getText().toString());
                sendingemail.putExtra("email", ed_email.getText().toString());
                sendingemail.putExtra("password", ed_password.getText().toString());
                startActivity(sendingemail);
            }
        } else {
            try {
                JSONObject userObject = new JSONObject(serverResult);
                message_code = userObject.getString("message_code");
                if (message_code.equalsIgnoreCase("104")) {
                    Intent sendingemail = new Intent(RegistrationActivity.this, SendingEmailActivity.class);
                    sendingemail.putExtra("typeofuser", usertype);
                    sendingemail.putExtra("action", "registration");
                    sendingemail.putExtra("firstname", ed_firstname.getText().toString());
                    sendingemail.putExtra("lastname", ed_lastname.getText().toString());
                    sendingemail.putExtra("email", ed_email.getText().toString());
                    sendingemail.putExtra("password", ed_password.getText().toString());
                    startActivity(sendingemail);
                }
            } catch (Exception ex) {
                //don't forget this
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onError(String serverResult, String requestTag, int statusCode) {
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(RegistrationActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        thePic = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thePic.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        img_Camera.setImageBitmap(thePic);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {


        if (data != null) {
            try {
                thePic = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        tv.setText("");
        img_Camera.setImageBitmap(thePic);
    }


}