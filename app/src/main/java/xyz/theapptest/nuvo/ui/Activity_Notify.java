package xyz.theapptest.nuvo.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.adapter.Producerjobdetails;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;

/**
 * Created by trtcpu007 on 11/8/16.
 */

public class Activity_Notify extends Activity {
    Button btnaccepted, btnrejected;
    String userid, token;
    String jobid;
    TextView tv_audtitle, tv_date, tv_description, tv_attachment, tv_companyname, tv_firstname, tv_lastname, tv_duartion;
    // ImageView imgback;
    // String urlaccepted= WebApiCall.baseURl + "job/" + jobid + "/accepted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistnotify);
        init();
        getjobid();


    }

    private void getjobid() {
        Intent intent = getIntent();
        jobid = intent.getStringExtra("jobid");
        SharedPreferences shf = getSharedPreferences("UserType", Context.MODE_PRIVATE);
        String usertype = shf.getString("usertype", null);
        userid = shf.getString("userid", null);
        token = shf.getString("token", null);
        webServiceCalldetails(userid, token, jobid);
    }

    public void init() {
       /* imgback = (ImageView) findViewById(R.id.back);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        tv_audtitle = (TextView) findViewById(R.id.audition_details_title_tv_id);
        Typeface type = Typeface.createFromAsset(getAssets(), "font/OpenSans-Bold.ttf");
        tv_audtitle.setTypeface(type);
        tv_duartion = (TextView) findViewById(R.id.duration);

        tv_firstname = (TextView) findViewById(R.id.audition_firstname);
        tv_lastname = (TextView) findViewById(R.id.audition_lastname);
        tv_attachment = (TextView) findViewById(R.id.tvattachments);
        tv_companyname = (TextView) findViewById(R.id.companyname);


        tv_description = (TextView) findViewById(R.id.audition_details_layout_description_tv_id);
        tv_date = (TextView) findViewById(R.id.audition_details_date_tv_id);
        Typeface facetxtsiginbelow = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Light.ttf");
        tv_date.setTypeface(facetxtsiginbelow);
        tv_description.setTypeface(facetxtsiginbelow);
        tv_firstname.setTypeface(facetxtsiginbelow);
        tv_lastname.setTypeface(facetxtsiginbelow);


        btnaccepted = (Button) findViewById(R.id.btnaccepted);
        btnrejected = (Button) findViewById(R.id.btnrejected);
        btnaccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("accepted", "accepted");
                Log.e("jobid", jobid);
                String urlaccepted = WebApiCall.baseURl + "job/" + jobid + "/accepted";
                webServicecallAudition(urlaccepted, "accepted");
                finish();
            }
        });
        btnrejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("rejected", "rejected");
                Log.e("jobid", jobid);
                String urlrejected = WebApiCall.baseURl + "job/" + jobid + "/rejected";
                webServicecallAudition(urlrejected, "rejected");
                finish();
            }
        });

    }

    public void webServiceCalldetails(String userid, String token, String jobid) {
        if (CheckInternetConnection.isConnected(Activity_Notify.this)) {

            String text = "";
            BufferedReader reader = null;


            try {
                String url = "http://nuvo.theapptest.xyz/v2/api/job/" + jobid + "/jobdetails";
                URL obj = new URL(url);


                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userid + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
                con.setRequestProperty("Authorization", "Basic " + encoded);
                con.setRequestMethod("POST");
                String data = "";

                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(data);
                wr.flush();
                // Get the server response
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }
                text = sb.toString();
                Log.e("job details response", text);
                if (text != null) {
                    if (text.startsWith("{")) {
                        JSONObject userObject = new JSONObject(text);
                        String message_code = userObject.getString("message_code");
                        if (message_code != null) {

                        }


                    } else {

                        JSONArray json = new JSONArray(text);
                        for (int i = 0; i < json.length(); i++) {

                            final JSONObject e = json.getJSONObject(i);


                            if (e.getString("job_title") != null) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            tv_audtitle.setText(e.getString("job_title"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                            }

                            if (e.getString("description") != null) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            tv_description.setText(e.getString("description"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                            }
                            if (e.getString("created_on") != null) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            tv_date.setText(e.getString("created_on"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                            }
                            if (e.getString("company_name") != null) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            tv_companyname.setText(e.getString("company_name"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                            }
                            if (e.getString("first_name") != null) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            tv_firstname.setText(e.getString("first_name"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                            }
                            if (e.getString("last_name") != null) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            tv_lastname.setText(e.getString("last_name"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                            }
                            if (e.getString("job_status") != null) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            Log.e("jobstatus", e.getString("job_status"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                            }


                            if (e.getString("user_id") != null) {

                            }


                            if (e.getString("attachments") != null) {

                            }
                            if (e.getString("duration") != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            tv_duartion.setText(e.getString("duration"));
                                        }catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }

                                    }
                                });


                            }

                        }
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    CustomizeDialog customizeDialog = new CustomizeDialog(Activity_Notify.this);
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Internet not available, Please check your Internet connectivity and try again.");
                    customizeDialog.show();

                }
            });

        }
    }


    public void webServicecallAudition(final String url, final String action) {
        SharedPreferences shf = getSharedPreferences("UserType", Context.MODE_PRIVATE);
        String usertype = shf.getString("usertype", null);
        userid = shf.getString("userid", null);
        token = shf.getString("token", null);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    webServiceCallAuditionData(url, userid, token, action);

                } catch (Exception e) {

                }
            }
        });
        thread.start();

    }

    public void webServiceCallAuditionData(String url, String userid, String token, final String action) {
        if (CheckInternetConnection.isConnected(this)) {

            String text = "";
            BufferedReader reader = null;


            try {


                //  String url = WebApiCall.baseURl + "auditions/" + jobid + "/apply";
                // String url = WebApiCall.baseURl + "job/" + jobid + "/accepted";

                URL obj = new URL(url);


                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userid + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
                con.setRequestProperty("Authorization", "Basic " + encoded);
                con.setRequestMethod("PUT");
                String data = "";

                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(data);
                wr.flush();
                // Get the server response
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }
                text = sb.toString();
                Log.e("notification response", text);
                if (text != null) {
                    if (text.startsWith("{")) {
                        JSONObject userObject = new JSONObject(text);
                        String message_code = userObject.getString("message_code");
                        if (message_code.equalsIgnoreCase("167")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (action.equalsIgnoreCase("accepted")) {
                                        try {
                                            CustomizeDialog customizeDialog = new CustomizeDialog(Activity_Notify.this);
                                            customizeDialog.setTitle("Audition");
                                            customizeDialog.setCancelable(false);
                                            customizeDialog.setMessage("Successfully applied for Audition");
                                            customizeDialog.show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    } else {
                                        try {
                                            CustomizeDialog customizeDialog = new CustomizeDialog(Activity_Notify.this);
                                            customizeDialog.setTitle("Audition");
                                            customizeDialog.setCancelable(false);
                                            customizeDialog.setMessage("Successfully rejected for Audition");
                                            customizeDialog.show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    //   Toast.makeText(getActivity(), "Successfully applied for Audition", Toast.LENGTH_LONG).show();
                                    //Dialogs.showAlertcommon(mContext, "Audition", "Successfully applied for Audition");

                                }
                            });


                        }
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    CustomizeDialog customizeDialog = new CustomizeDialog(Activity_Notify.this);
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Internet not available, Please check your Internet connectivity and try again.");
                    customizeDialog.show();

                }
            });

        }
    }


}
