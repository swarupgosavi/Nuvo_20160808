package xyz.theapptest.nuvo.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
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

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.agent.AgentContainerActivity;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.services.VolleyWebserviceCallBack;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Constants;
import xyz.theapptest.nuvo.utils.DialogShowMethods;
import xyz.theapptest.nuvo.utils.Dialogs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by trtcpu007 on 6/7/16.
 */

public class SignInActivity extends Activity implements View.OnClickListener, VolleyWebserviceCallBack {
    TextView tvsign, tvuseemail, tv_createdaccount, tv_forgot, tv_title;
    EditText ed_email, ed_password;
    Button bt_sigin;
    String strEmailAddress, strPassword;
    String regEx;
    String s;
    ImageView img_back;
    boolean isPasswordVisible = false;
    HashMap<String, String> parameter;
    ;
    String role = "";
    private int mPreviousInputType;
    CustomizeDialog customizeDialog = null;
    private boolean mIsShowingPassword;
    ConstantData constantData;
    String usertype;
    ProgressDialog dialog;

    ArrayList<HashMap<String, String>> agelist;
    ArrayList<HashMap<String, String>> characterlistvalue;
    ArrayList<HashMap<String, String>> jobtypevalue;
    ArrayList<HashMap<String, String>> langaugevalues;
    ArrayList<HashMap<String, String>> recordingmethodvaluesd;

    private static final String TAG_AGE = "age";
    private static final String TAG_CHARACTERISTICS = "characteristics";
    private static final String TAG_JOBCATEGORY = "job_category";
    private static final String TAG_LANGAUGE = "language";
    private static final String TAG_RECORDINGMEHOD = "recording_methods";


    JSONArray age = null;
    JSONArray characteristics = null;
    JSONArray job_category = null;
    JSONArray langaugesd = null;
    JSONArray recording_methods = null;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    SharedPreferences nuvoPreferences;
    SharedPreferences.Editor nuvoEditor;
    public static final String MyPREFERENCES = "NuvoPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_login);
        constantData = ConstantData.getInstance();

        setSharedPref();
        init();
        typeofuser();
        emailidexpression();
        typeInterface();
        checkEdittextPassword();
        onClickevent();
        agelist = new ArrayList<HashMap<String, String>>();
        characterlistvalue = new ArrayList<HashMap<String, String>>();
        jobtypevalue = new ArrayList<HashMap<String, String>>();
        langaugevalues = new ArrayList<HashMap<String, String>>();
        recordingmethodvaluesd = new ArrayList<HashMap<String, String>>();

    }

    private void emailidexpression() {
        regEx =
                "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";


    }

    private void setSharedPref() {
        preferences = getSharedPreferences("UserType", Context.MODE_PRIVATE);
        editor = preferences.edit();

        nuvoPreferences = getSharedPreferences(Constants.NUVOPREF, Context.MODE_PRIVATE);
        nuvoEditor = nuvoPreferences.edit();
        //editor.putString("usertype", "Agent");
    }

    private void typeofuser() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            usertype = extras.getString("Screentype");
        }
    }

    private void onClickevent() {

        tv_forgot.setOnClickListener(this);
        bt_sigin.setOnClickListener(this);
        img_back.setOnClickListener(this);

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

    OnPasswordDisplayListener mOnPasswordDisplayListener;

    public void setOnPasswordDisplayListener(OnPasswordDisplayListener listener) {
        mOnPasswordDisplayListener = listener;
    }


    private void typeInterface() {
        Typeface facetxttitle = Typeface.createFromAsset(getAssets(),
                "font/Vonique 64.ttf");
        tv_title.setTypeface(facetxttitle);


        Typeface facetxtsigin = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Semibold.ttf");
        tvsign.setTypeface(facetxtsigin);
        bt_sigin.setTypeface(facetxtsigin);

        Typeface facetxtsiginbelow = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Light.ttf");
        tvuseemail.setTypeface(facetxtsiginbelow);
        tv_createdaccount.setTypeface(facetxtsiginbelow);

        Typeface facetxtsigintextbox = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Regular.ttf");
        ed_email.setTypeface(facetxtsigintextbox);
        ed_password.setTypeface(facetxtsigintextbox);
        tv_forgot.setTypeface(facetxtsigintextbox);


    }

    private void init() {
        String html = "<a href=\"http://google.com\">Forgot Password?</a>";


        tv_title = (TextView) findViewById(R.id.toolbar_title);
        tvsign = (TextView) findViewById(R.id.tv_signin);
        tvuseemail = (TextView) findViewById(R.id.tv_useemail);
        tv_createdaccount = (TextView) findViewById(R.id.tv_whencreated);
        tv_forgot = (TextView) findViewById(R.id.txtLostpassword);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_password = (EditText) findViewById(R.id.ed_password);
        bt_sigin = (Button) findViewById(R.id.signin);
        img_back = (ImageView) findViewById(R.id.back);
        tv_forgot.setText(Html.fromHtml(html));

    }


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txtLostpassword:
                Intent forgot = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                forgot.putExtra("Screentype", usertype);
                startActivity(forgot);
                break;
            case R.id.signin:

                strEmailAddress = ed_email.getText().toString().trim();
                strPassword = ed_password.getText().toString().trim();
                if (strEmailAddress.matches("")) {

                    customizeDialog = new CustomizeDialog(SignInActivity.this);
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Please enter valid email.");
                    customizeDialog.show();


                    // DialogShowMethods.showDialog(SignInActivity.this, "Error", "Please enter valid email.");
                } else {

                    Pattern pattern;
                    pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(strEmailAddress);
                    if (!matcher.find()) {
                        Log.e("Invalid email", "Invalid email");
                        //   DialogShowMethods.showDialog(SignInActivity.this, "nuvo", "Please enter valid email.");
                        customizeDialog = new CustomizeDialog(SignInActivity.this);
                        customizeDialog.setTitle("nuvo");
                        customizeDialog.setCancelable(false);
                        customizeDialog.setMessage("Please enter valid email.");
                        customizeDialog.show();

                    } else {
                        if (strPassword.matches("")) {
                            customizeDialog = new CustomizeDialog(SignInActivity.this);
                            customizeDialog.setTitle("nuvo");
                            customizeDialog.setCancelable(false);
                            customizeDialog.setMessage("Please enter password.");
                            customizeDialog.show();


                            //   DialogShowMethods.showDialog(SignInActivity.this, "Error", "Please enter password.");

                        } else {

                            Log.e("email", strEmailAddress.trim());
                            Log.e("passsword", strPassword.trim());
                            parameter = new HashMap<String, String>();
                            parameter.put("email", ed_email.getText().toString().trim());
                            parameter.put("password", ed_password.getText().toString().trim());
                            if (CheckInternetConnection.isConnected(SignInActivity.this)) {
                                Log.e("strEmailAddress", strEmailAddress.trim());
                                Log.e("strpassword", strPassword.trim());


                                dialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
                                dialog.setMessage("Please Wait!!");
                                dialog.setCancelable(false);
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                dialog.show();
                                Thread mThread = new Thread() {
                                    @Override
                                    public void run() {

                                        try {
                                            GetLoginDetail(ed_email.getText().toString().trim(), ed_password.getText().toString().trim());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                };
                                mThread.start();


                            } else {
                                customizeDialog = new CustomizeDialog(SignInActivity.this);
                                customizeDialog.setTitle("nuvo");
                                customizeDialog.setCancelable(false);
                                customizeDialog.setMessage("Internet not available, Please check your Internet connectivity and try again.");
                                customizeDialog.show();
                            }


                            // Toast.makeText(SignInActivity.this, "Validation success", Toast.LENGTH_LONG).show();
                        }
                    }

                }

                break;
            case R.id.back:
                Intent i=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(i);
                finish();
                break;

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
                    Dialogs.showAlertcommon(SignInActivity.this, "Error", "Please enter valid username and password to login.");
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
                dialog.dismiss();

                if (text.contains("user_id")) {
                    try {
                        JSONObject userObject = new JSONObject(text);
                        message_code = userObject.getString("message_code");
                        user_id = userObject.getString("user_id");
                        token = userObject.getString("token");
                        role = userObject.getString("role");
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

                    if (message_code.equalsIgnoreCase("152")) {
                        Log.e("usertype",usertype);
                        if (usertype.equalsIgnoreCase("Producer") && role.equalsIgnoreCase("6")) {
                            constantData.setUserid(user_id);
                            constantData.setTokenid(token);
                            SharedPreferences preferences = getSharedPreferences("UserType", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("usertype", "Producer");
                            editor.putString("userid", user_id);
                            editor.putString("token", token);
                            nuvoEditor.putString(Constants.Key_email, email);
                            nuvoEditor.putString(Constants.Key_Password, password);

                            nuvoEditor.commit();

                            editor.commit();


                            basicgetauthentication(constantData.getUserid(), constantData.getTokenid());
                            Intent i = new Intent(SignInActivity.this, ProducerHomeScreen.class);

                            startActivity(i);
                            finish();


                        } else {

                            if (usertype.equalsIgnoreCase("Artist") && role.equalsIgnoreCase("4")) {

                                constantData.setUserid(user_id);
                                constantData.setTokenid(token);
                                SharedPreferences preferences = getSharedPreferences("UserType", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("usertype", "Artist");
                                editor.putString("userid", user_id);
                                editor.putString("token", token);
                                nuvoEditor.putString(Constants.Key_email, email);
                                nuvoEditor.putString(Constants.Key_Password, password);
                                nuvoEditor.commit();
                                editor.commit();


                                basicgetauthentication(constantData.getUserid(), constantData.getTokenid());
                                Intent i = new Intent(SignInActivity.this, ArtistHomescreen.class);

                                startActivity(i);
                                finish();


                            } else {
                                if (usertype.equalsIgnoreCase("Agent") && role.equalsIgnoreCase("5")) {
                                    constantData.setUserid(user_id);
                                    constantData.setTokenid(token);
                                    SharedPreferences preferences = getSharedPreferences("UserType", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("usertype", "Agent");
                                    editor.putString("userid", user_id);
                                    editor.putString("token", token);
                                    nuvoEditor.putString(Constants.Key_email, email);
                                    nuvoEditor.putString(Constants.Key_Password, password);
                                    nuvoEditor.commit();
                                    editor.commit();


                                    basicgetauthentication(constantData.getUserid(), constantData.getTokenid());
                                    Intent i = new Intent(SignInActivity.this, AgentContainerActivity.class);

                                    startActivity(i);
                                    finish();

                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e("email-alrreg", "email-alrreg");
                                            customizeDialog = new CustomizeDialog(SignInActivity.this);
                                            customizeDialog.setTitle("nuvo");
                                            customizeDialog.setCancelable(false);
                                            customizeDialog.setMessage("Your role credentials are mismatched. Please try with another role.");
                                            customizeDialog.show();
                                        }
                                    });
                                }
                            }


                        }
















                      /*  new AlertDialog.Builder(SignInActivity.this)
                                .setTitle("Success")
                                .setMessage("Your login is successful")
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                SignInActivity.this.runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        Intent i = new Intent(SignInActivity.this, HomeActivity.class);
                                                        startActivity(i);
                                                      //  Toast.makeText(SignInActivity.this, "Loggin successfully", Toast.LENGTH_LONG).show();

                                                    }
                                                });

                                                dialog.dismiss();


                                            }
                                        }).show();*/


                    }
                } else {
                    try {
                        JSONObject userObject = new JSONObject(text);
                        message_code = userObject.getString("message_code");
                        Log.e("messagecode", message_code);

                        //both invalid username and password-Login failed. Please enter valid credentials.

                        if (message_code.equalsIgnoreCase("103")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    customizeDialog = new CustomizeDialog(SignInActivity.this);
                                    customizeDialog.setTitle("nuvo");
                                    customizeDialog.setCancelable(false);
                                    customizeDialog.setMessage("Login failed. Please enter valid credentials.");
                                    customizeDialog.show();
                                }
                            });


                            // Dialogs.showAlertcommon(SignInActivity.this, "Error", "Login fails. Please enter valid password.");
                        } else {
                            if (message_code.equalsIgnoreCase("102")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        customizeDialog = new CustomizeDialog(SignInActivity.this);
                                        customizeDialog.setTitle("nuvo");
                                        customizeDialog.setCancelable(false);
                                        customizeDialog.setMessage("Unable to login. Your login is inactive. Please check the verification mail or contact administrator.");
                                        customizeDialog.show();

                                    }
                                });


                                //  Dialogs.showAlertcommon(SignInActivity.this, "Error", "Unable to login. Your login is inactive. Please check the verification mail or contact administrator.");
                            } else {
                                if (message_code.equalsIgnoreCase("105")) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            customizeDialog = new CustomizeDialog(SignInActivity.this);
                                            customizeDialog.setTitle("nuvo");
                                            customizeDialog.setCancelable(false);
                                            customizeDialog.setMessage("Unable to login. Invalid user ID.");
                                            customizeDialog.show();
                                        }
                                    });


                                    // Dialogs.showAlertcommon(SignInActivity.this, "Error", "Unable to login. Invalid user ID.");
                                } else {
                                    if (message_code.equalsIgnoreCase("101")) {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                customizeDialog = new CustomizeDialog(SignInActivity.this);
                                                customizeDialog.setTitle("nuvo");
                                                customizeDialog.setCancelable(false);
                                                customizeDialog.setMessage("Unable to update at this movement. Please check your details and try again.");
                                                customizeDialog.show();
                                            }
                                        });


                                        //      Dialogs.showAlertcommon(SignInActivity.this, "Error", "Unable to update at this movement. Please check your details and try again.");
                                    }
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                customizeDialog = new CustomizeDialog(SignInActivity.this);
                                customizeDialog.setTitle("nuvo");
                                customizeDialog.setCancelable(false);
                                customizeDialog.setMessage("Login failed. Please enter valid credentials.");
                                customizeDialog.show();
                            }
                        });


                        //  Dialogs.showAlertcommon(SignInActivity.this, "Error", "Please enter valid username and password to login.");
                    }
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customizeDialog = new CustomizeDialog(SignInActivity.this);
                        customizeDialog.setTitle("nuvo");
                        customizeDialog.setCancelable(false);
                        customizeDialog.setMessage("Login failed. Please enter valid credentials.");
                        customizeDialog.show();
                    }
                });


                // Dialogs.showAlertcommon(SignInActivity.this, "Error", "Please enter valid username and password to login.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void basicgetauthentication(String userid, String tokens) {
        try {

            constantData = ConstantData.getInstance();

            String url = WebApiCall.baseURl + "default";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            String encoded = Base64.encodeToString((userid + ":" + tokens).getBytes("UTF-8"), Base64.NO_WRAP);
            //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
            con.setRequestProperty("Authorization", "Basic " + encoded);
            // optional default is GET
            con.setRequestMethod("GET");

            //add request header


            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
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
            //  Button myButton = new Button(this);
            //LinearLayout layout = (LinearLayout) findViewById(xyz.theapptest.nuvo.R.id.toplv);
            // Button[] buttons = new Button[agelist.size()];
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

  /*          Set<HashMap<String, String>> set = new HashSet<HashMap<String, String>>();
            set.addAll(agelist);
//            SharedPreferences preferences = getSharedPreferences("UserType", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("usertype", "Agent");
            editor.putStringSet("key", set);
            editor.commit();*/

            constantData.setAgelist(agelist);


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
                Log.e("......", "..........................................");
                Log.e("id", id);
                Log.e("characteristic_name", characteristicname);
                Log.e("characterics_value_json", charcteristicvalue);
                Log.e("......", "..........................................");
                characterlistvalue.add(charcter);


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


    @Override
    public void onSuccess(String serverResult, String requestTag, int statusCode) {
        Log.e("result", serverResult);
        String message_code = "";
        String user_id = "";
        String token = "";
        if (serverResult.contains("user_id")) {
            try {
                JSONObject userObject = new JSONObject(serverResult);
                message_code = userObject.getString("message_code");
                user_id = userObject.getString("user_id");
                token = userObject.getString("token");
                if (user_id != null && !user_id.equals("")) {
                    Log.e("user_id", user_id);
                    constantData.setTokenid(token);
                }
                Log.e("message_code", message_code);
            } catch (Exception ex) {
                //don't forget this
                ex.printStackTrace();
            }

            if (message_code.equalsIgnoreCase("152")) {

                new AlertDialog.Builder(SignInActivity.this)
                        .setTitle("Success")
                        .setMessage("User Registreation Successful.")
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        SignInActivity.this.runOnUiThread(new Runnable() {
                                            public void run() {

                                                Toast.makeText(SignInActivity.this, "Loggin successfully", Toast.LENGTH_LONG).show();
                                                Intent i = new Intent(SignInActivity.this, HomeActivity.class);
                                                startActivity(i);

                                            }
                                        });

                                        dialog.dismiss();

                                    }
                                }).show();


            } else {
                if (message_code.equalsIgnoreCase("103")) {
                    customizeDialog = new CustomizeDialog(SignInActivity.this);
                    customizeDialog.setTitle("Error");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("User Login Unsuccessful-Password Incorrect.");
                    customizeDialog.show();

                    // Dialogs.showAlertcommon(SignInActivity.this, "Error", "User Login Unsuccessful-Password Incorrect.");
                } else {
                    if (message_code.equalsIgnoreCase("102")) {
                        customizeDialog = new CustomizeDialog(SignInActivity.this);
                        customizeDialog.setTitle("Error");
                        customizeDialog.setCancelable(false);
                        customizeDialog.setMessage("User Login Unsuccessful-User Inactive.");
                        customizeDialog.show();


                        //  Dialogs.showAlertcommon(SignInActivity.this, "Error", "User Login Unsuccessful-User Inactive.");
                    } else {
                        if (message_code.equalsIgnoreCase("105")) {

                            customizeDialog = new CustomizeDialog(SignInActivity.this);
                            customizeDialog.setTitle("Error");
                            customizeDialog.setCancelable(false);
                            customizeDialog.setMessage("Invalid User ID specified.");
                            customizeDialog.show();

                            // Dialogs.showAlertcommon(SignInActivity.this, "Error", "Invalid User ID specified.");
                        } else {
                            if (message_code.equalsIgnoreCase("101")) {

                                customizeDialog = new CustomizeDialog(SignInActivity.this);
                                customizeDialog.setTitle("Error");
                                customizeDialog.setCancelable(false);
                                customizeDialog.setMessage("Unable to update user information.");
                                customizeDialog.show();


                                //  Dialogs.showAlertcommon(SignInActivity.this, "Error", "Unable to update user information.");
                            }
                        }
                    }
                }

            }
        }
    }

    @Override
    public void onError(String serverResult, String requestTag, int statusCode) {

    }
}
