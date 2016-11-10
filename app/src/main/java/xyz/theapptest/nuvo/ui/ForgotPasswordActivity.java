package xyz.theapptest.nuvo.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.services.VolleyWebserviceCallBack;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by trtcpu007 on 7/7/16.
 */

public class ForgotPasswordActivity extends Activity implements View.OnClickListener, VolleyWebserviceCallBack {
    TextView tv_forgotpwd, tv_enteryourmail, tv_passwordreset, tv_title;
    EditText ed_email;
    Button bt_send;
    String regEx;
    String strEmailAddress;
    ImageView img_back;
    HashMap<String, String> parameter;
    CustomizeDialog customizeDialog = null;
    String usertype;
    int roleuser;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_forgot_password);
        init();
        typeofuser();
        emailidexpression();
        checkEdittextPassword();
        typeInterface();

    }

    private void emailidexpression() {
        regEx =
                "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";


    }

    private void typeofuser() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            usertype = extras.getString("Screentype");
        }
    }

    private void checkEdittextPassword() {
    }

    private void init() {

        tv_title = (TextView) findViewById(R.id.toolbar_title);
        tv_forgotpwd = (TextView) findViewById(R.id.tv_forgotpassword);
        tv_enteryourmail = (TextView) findViewById(R.id.tv_enteremail);
        tv_passwordreset = (TextView) findViewById(R.id.tv_passwordreset);
        ed_email = (EditText) findViewById(R.id.ed_email);
        bt_send = (Button) findViewById(R.id.send);
        bt_send.setOnClickListener(this);
        img_back = (ImageView) findViewById(R.id.back);
        img_back.setOnClickListener(this);

    }

    private void typeInterface() {
        Typeface facetxttitle = Typeface.createFromAsset(getAssets(),
                "font/Vonique 64.ttf");
        tv_title.setTypeface(facetxttitle);

        Typeface facetxtsigin = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Semibold.ttf");
        tv_forgotpwd.setTypeface(facetxtsigin);
        bt_send.setTypeface(facetxtsigin);

        Typeface facetxtsiginbelow = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Light.ttf");
        tv_enteryourmail.setTypeface(facetxtsiginbelow);
        tv_passwordreset.setTypeface(facetxtsiginbelow);

        Typeface facetxtsigintextbox = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Regular.ttf");
        ed_email.setTypeface(facetxtsigintextbox);


    }

    private void GetLoginDetail(String email, int role) {
        try {

            String message_code = "";
            String data = URLEncoder.encode(WebApiCall.KEY_EMAIL, "UTF-8")
                    + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("role")
                    + "=" + URLEncoder.encode(Integer.toString(role), "UTF-8");


            String text = "";
            BufferedReader reader = null;

            // Send data
            try {
                String Urls = WebApiCall.baseURl + "user/forgotpassword";

                // Defined URL  where to send data
                URL url = new URL(Urls);

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
                if (text.contains("user_id")) {

                } else {
                    try {
                        dialog.dismiss();
                        JSONObject userObject = new JSONObject(text);
                        message_code = userObject.getString("message_code");
                        Log.e("message code", message_code);
                        if (message_code.equalsIgnoreCase("153")) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent sendemail = new Intent(ForgotPasswordActivity.this, SendingEmailActivity.class);
                                    sendemail.putExtra("typeofuser", usertype);
                                    startActivity(sendemail);
                                }
                            });


                        } else {
                            if (message_code.equalsIgnoreCase("106")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        customizeDialog = new CustomizeDialog(ForgotPasswordActivity.this);
                                        customizeDialog.setTitle("nuvo");
                                        customizeDialog.setCancelable(false);
                                        customizeDialog.setMessage("Email id does not exits.");
                                        customizeDialog.show();
                                    }
                                });

                            } else {
                                if (message_code.equalsIgnoreCase("108")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            customizeDialog = new CustomizeDialog(ForgotPasswordActivity.this);
                                            customizeDialog.setTitle("nuvo");
                                            customizeDialog.setCancelable(false);
                                            customizeDialog.setMessage("Your role credentials are mismatched. Please try with another role.");
                                            customizeDialog.show();
                                        }
                                    });

                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {

            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.send:

                strEmailAddress = ed_email.getText().toString().trim();

                if (strEmailAddress.matches("")) {
                    customizeDialog = new CustomizeDialog(ForgotPasswordActivity.this);
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Please enter valid email.");
                    customizeDialog.show();

                    // DialogShowMethods.showDialog(ForgotPasswordActivity.this, "Error", "Please enter email.");
                } else {

                    Pattern pattern;
                    pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(strEmailAddress);
                    if (!matcher.find()) {
                        Log.e("Invalid email", "Invalid email");
                        customizeDialog = new CustomizeDialog(ForgotPasswordActivity.this);
                        customizeDialog.setTitle("nuvo");
                        customizeDialog.setCancelable(false);
                        customizeDialog.setMessage("Please enter valid email.");
                        customizeDialog.show();


                        //DialogShowMethods.showDialog(ForgotPasswordActivity.this, "Error", "Email address is invalid");

                    } else {


                        Log.e("email", strEmailAddress.trim());
                        if (CheckInternetConnection.isConnected(ForgotPasswordActivity.this)) {

                            if (usertype.equalsIgnoreCase("Producer")) {
                                roleuser = 6;
                            } else {
                                if (usertype.equalsIgnoreCase("Agent")) {
                                    roleuser = 5;
                                } else {
                                    if (usertype.equalsIgnoreCase("Artist")) {
                                        roleuser = 4;
                                    }
                                }
                            }

                            Log.e("email", ed_email.getText().toString().trim());
                            Log.e("roleuser", Integer.toString(roleuser));
                            dialog = new ProgressDialog(this);
                            dialog.setMessage("Please Wait!!");
                            dialog.setCancelable(false);
                            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            dialog.show();
                            Thread mThread = new Thread() {
                                @Override
                                public void run() {
                                    String imgupl = "";
                                    try {
                                        GetLoginDetail(ed_email.getText().toString().trim(), roleuser);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            };
                            mThread.start();


                            // Toast.makeText(ForgotPasswordActivity.this, "Validation success", Toast.LENGTH_LONG).show();
                        } else {


                            customizeDialog = new CustomizeDialog(ForgotPasswordActivity.this);
                            customizeDialog.setTitle("nuvo");
                            customizeDialog.setCancelable(false);
                            customizeDialog.setMessage("Internet not available, Please check your Internet connectivity and try again.");
                            customizeDialog.show();

                            //   Dialogs.showNoConnectionDialog(ForgotPasswordActivity.this);
                        }

                    }

                }


                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    public void onSuccess(String serverResult, String requestTag, int statusCode) {
        Log.e("result", serverResult);
        //   Intent sendemail=new Intent(ForgotPasswordActivity.this,SendingEmailActivity.class);
        //  startActivity(sendemail);
    }

    @Override
    public void onError(String serverResult, String requestTag, int statusCode) {

    }
}
