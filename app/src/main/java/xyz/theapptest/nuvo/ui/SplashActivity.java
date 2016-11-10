package xyz.theapptest.nuvo.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

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

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.agent.AgentContainerActivity;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Constants;
import xyz.theapptest.nuvo.utils.Dialogs;


/**
 * Created by trtcpu007 on 5/7/16.
 */

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    ConstantData constantData;
    Context mContext = SplashActivity.this;

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
    ProgressDialog dialog;
    CustomizeDialog customizeDialog = null;
    String email = "";
    String password = "";
    SharedPreferences nuvoPreferences;
    SharedPreferences.Editor nuvoEditor;
    String usertype = "";
    String userid = "";
    String role = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setValues();
        setNuvoSharedPref();
        SharedPreferences shf = getSharedPreferences("UserType", Context.MODE_PRIVATE);
        usertype = shf.getString("usertype", "");
        userid = shf.getString("userid", "");

        if (email.equals("") || password.equals("")) {
            splashScreenLogic();
        } else {
            Thread mThread = new Thread() {
                @Override
                public void run() {

                    try {
                        GetLoginDetail(email, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            mThread.start();

        }

        /*if (usertype != null && userid != null) {
            if (usertype.equalsIgnoreCase("Producer")) {

                Intent i = new Intent(SplashActivity.this, ProducerHomeScreen.class);
                startActivity(i);
                finish();
            }else
            {
                if(usertype.equalsIgnoreCase("Artist"))
                {
                    Intent i = new Intent(SplashActivity.this, ArtistHomescreen.class);
                    startActivity(i);
                    finish();
                }
            }

        } else {
            //splashScreenLogic();
        }

*/


    }


    private void setValues() {
        constantData = ConstantData.getInstance();
        agelist = new ArrayList<HashMap<String, String>>();
        characterlistvalue = new ArrayList<HashMap<String, String>>();
        jobtypevalue = new ArrayList<HashMap<String, String>>();
        langaugevalues = new ArrayList<HashMap<String, String>>();
        recordingmethodvaluesd = new ArrayList<HashMap<String, String>>();

    }

    private void setNuvoSharedPref() {

        nuvoPreferences = getSharedPreferences(Constants.NUVOPREF, Context.MODE_PRIVATE);
        nuvoEditor = nuvoPreferences.edit();
        email = nuvoPreferences.getString(Constants.Key_email, "");
        password = nuvoPreferences.getString(Constants.Key_Password, "");
    }

    private void splashScreenLogic() {

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent isplash = new Intent(SplashActivity.this, SignUpActivity.class);
                startActivity(isplash);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

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
                    Dialogs.showAlertcommon(mContext, "Error", "Please enter valid username and password to login.");
                }
            } catch (Exception ex) {

                ex.printStackTrace();
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
                //dialog.dismiss();
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
                        if (usertype.equalsIgnoreCase("Producer") && role.equalsIgnoreCase("6")) {
                            constantData.setUserid(user_id);
                            constantData.setTokenid(token);
                            SharedPreferences preferences = getSharedPreferences("UserType", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("usertype", "Producer");
                            editor.putString("userid", user_id);
                            editor.putString("token", token);
                            editor.commit();


                            basicgetauthentication(constantData.getUserid(), constantData.getTokenid());
                            Intent i = new Intent(mContext, ProducerHomeScreen.class);

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
                                editor.commit();


                                basicgetauthentication(constantData.getUserid(), constantData.getTokenid());
                                Intent i = new Intent(mContext, ArtistHomescreen.class);

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
                                    editor.commit();


                                    basicgetauthentication(constantData.getUserid(), constantData.getTokenid());
                                    Intent i = new Intent(mContext, AgentContainerActivity.class);

                                    startActivity(i);
                                    finish();

                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e("email-alrreg", "email-alrreg");
                                            customizeDialog = new CustomizeDialog(mContext);
                                            customizeDialog.setTitle("nuvo");
                                            customizeDialog.setCancelable(false);
                                            customizeDialog.setMessage("Your role credentials are mismatched. Please try with another role.");
                                            customizeDialog.show();
                                        }
                                    });
                                }
                            }


                        }


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
                                    customizeDialog = new CustomizeDialog(mContext);
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
                                        customizeDialog = new CustomizeDialog(mContext);
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
                                            customizeDialog = new CustomizeDialog(mContext);
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
                                                customizeDialog = new CustomizeDialog(mContext);
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
                                customizeDialog = new CustomizeDialog(mContext);
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
                        customizeDialog = new CustomizeDialog(mContext);
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

}