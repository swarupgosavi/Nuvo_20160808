package xyz.theapptest.nuvo.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.fragment.Fragment_CriteriaArtist;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Dialogs;
import xyz.theapptest.nuvo.utils.FinfjobArtist;

/**
 * Created by trtcpu007 on 1/8/16.
 */

public class Activity_Criteradetails extends Fragment {
    String usertype;
    TextView tv_audioname, tv_auditioncompany, tv_firstname, tv_lastname, tv_description, tv_date;
    ConstantData con;
    android.support.v7.widget.Toolbar toolbar1;
    Button btn, btnleft;
    TextView tx2;
    LinearLayout lvroot;
    String userid, token;
    String jobid, audioname, audioncompany, firstname, lastname, description, date;
    Context mContext;
    ProgressDialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.activity_artistdetails, null);
        init(x);
        typeofuser();
        typeface();
        return x;
    }

    private void init(View x) {
        lvroot = (LinearLayout) x.findViewById(R.id.rootfragment);
        ArtistHomescreen act = (ArtistHomescreen) getActivity();

        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);

        btn = (Button) toolbar1.findViewById(R.id.btn);
        btnleft = (Button) toolbar1.findViewById(R.id.btnleft);
        btn.setVisibility(View.GONE);

        btnleft.setVisibility(View.VISIBLE);
        btnleft.setBackgroundResource(R.drawable.backimg);


        btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lvroot.removeAllViewsInLayout();
                Fragment newFragment = new Fragment_CriteriaArtist();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();


                transaction.replace(R.id.rootfragment, newFragment);
                transaction.addToBackStack(null);


                transaction.commit();

            }
        });

        tx2 = (TextView) toolbar1.findViewById(R.id.rightlabel);
        tx2.setVisibility(View.VISIBLE);
        Typeface face2 = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Light.ttf");
        tx2.setTypeface(face2);
        tx2.setTextSize(17);
        tx2.setText("Audition");
        tx2.setClickable(true);
        tx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServicecallAudition(jobid);
            }
        });


        con = ConstantData.getInstance();
        tv_audioname = (TextView) x.findViewById(R.id.audition_details_title_tv_id);
        tv_auditioncompany = (TextView) x.findViewById(R.id.audition_companyname);
        tv_firstname = (TextView) x.findViewById(R.id.audition_firstname);
        tv_lastname = (TextView) x.findViewById(R.id.audition_lastname);
        tv_description = (TextView) x.findViewById(R.id.audition_details_layout_description_tv_id);
        tv_date = (TextView) x.findViewById(R.id.audition_details_date_tv_id);


    }

    private void typeface() {
        Typeface facetxtsigin = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Semibold.ttf");
        tv_audioname.setTypeface(facetxtsigin);


        Typeface facetxtsiginbelow = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Light.ttf");
        tv_date.setTypeface(facetxtsiginbelow);
        tv_description.setTypeface(facetxtsiginbelow);

        Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Regular.ttf");
        tv_firstname.setTypeface(facetxtsigintextbox);
        tv_lastname.setTypeface(facetxtsigintextbox);
        tv_auditioncompany.setTypeface(facetxtsigintextbox);


    }

   /* private void init() {
        tv_audioname=(TextView)findViewById(R.id.audition_details_title_tv_id);
        tv_auditioncompany=(TextView)findViewById(R.id.audition_companyname);
        tv_firstname=(TextView)findViewById(R.id.audition_firstname);
        tv_lastname=(TextView)findViewById(R.id.audition_lastname);
        tv_description=(TextView)findViewById(R.id.audition_details_layout_description_tv_id);
        tv_date=(TextView)findViewById(R.id.audition_details_date_tv_id);
    }*/


    private void typeofuser() {
        /*Intent intent = getActivity().getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            usertype = extras.getString("arrayvalue");
            Gson gson = new Gson();
            FinfjobArtist findJobArtistPojo = gson.fromJson(usertype, FinfjobArtist.class);
            String jobid = findJobArtistPojo.getJobid();
            String audioname = findJobArtistPojo.getTitle();
            String audioncompany = findJobArtistPojo.getCompanyname();
            String firstname = findJobArtistPojo.getFirstname();
            String lastname = findJobArtistPojo.getLastname();
            String description = findJobArtistPojo.getDescription();
            String date = findJobArtistPojo.getCreatedon();
            tv_audioname.setText(audioname);
            tv_auditioncompany.setText(audioncompany);
            tv_firstname.setText(firstname);
            tv_lastname.setText(lastname);
            tv_description.setText(description);
            tv_date.setText(date);
            Log.e("jobid", jobid);


        }*/

        String usertype = con.getFindjobresponse();
        if (usertype != null) {
            Gson gson = new Gson();
            FinfjobArtist findJobArtistPojo = gson.fromJson(usertype, FinfjobArtist.class);
            jobid = findJobArtistPojo.getJobid();
            audioname = findJobArtistPojo.getTitle();
            audioncompany = findJobArtistPojo.getCompanyname();
            firstname = findJobArtistPojo.getFirstname();
            lastname = findJobArtistPojo.getLastname();
            description = findJobArtistPojo.getDescription();
            date = findJobArtistPojo.getCreatedon();
            tv_audioname.setText(audioname);
            tv_auditioncompany.setText(audioncompany);
            tv_firstname.setText(firstname);
            tv_lastname.setText(lastname);
            tv_description.setText(description);
            if(date == null){

            }else{
                if(date.contains( " ")){
                    String[] dateArray = date.split(" ");
                    date = dateArray[0];

                }
                tv_date.setText(date);
            }

            webServicecallView(jobid);
            Log.e("jobid", jobid);
        }


    }

    public void webServicecallAudition(final String jobid) {
        SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
        String usertype = shf.getString("usertype", null);
        userid = shf.getString("userid", null);
        token = shf.getString("token", null);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    webServiceCallAuditionData(userid, token, jobid);

                } catch (Exception e) {

                }
            }
        });
        thread.start();

    }

    public void webServiceCallAuditionData(String userid, String token, String jobid) {
        if (CheckInternetConnection.isConnected(getActivity())) {

            String text = "";
            BufferedReader reader = null;


            try {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog = new ProgressDialog(mContext);
                        dialog.setMessage("Please Wait!!");
                        dialog.setCancelable(false);
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.show();

                    }
                });
              //  String url = WebApiCall.baseURl + "auditions/" + jobid + "/apply";
                String url = WebApiCall.baseURl + "job/" + jobid + "/applied";

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
                Log.e("audition response", text);
                if (text != null) {
                    if (text.startsWith("{")) {
                        JSONObject userObject = new JSONObject(text);
                        String message_code = userObject.getString("message_code");
                        if (message_code.equalsIgnoreCase("167")) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    //   Toast.makeText(getActivity(), "Successfully applied for Audition", Toast.LENGTH_LONG).show();
                                    //Dialogs.showAlertcommon(mContext, "Audition", "Successfully applied for Audition");
                                    CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                                    customizeDialog.setTitle("Audition");
                                    customizeDialog.setCancelable(false);
                                    customizeDialog.setMessage("Successfully applied for Audition");
                                    customizeDialog.show();
                                }
                            });


                        }
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Internet not available, Please check your Internet connectivity and try again.");
                    customizeDialog.show();

                }
            });

        }
    }


    public void webServicecallView(final String jobid) {
        SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
        String usertype = shf.getString("usertype", null);
        userid = shf.getString("userid", null);
        token = shf.getString("token", null);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    webServiceCallViewData(userid, token, jobid);

                } catch (Exception e) {

                }
            }
        });
        thread.start();

    }

    public void webServiceCallViewData(String userid, String token, String jobid) {
        if (CheckInternetConnection.isConnected(getActivity())) {

            String text = "";
            BufferedReader reader = null;


            try {
                String url = WebApiCall.baseURl + "job/" + jobid + "/views";
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
                Log.e("save response", text);
                if (text != null) {
                    if (text.startsWith("{")) {
                        JSONObject userObject = new JSONObject(text);
                        String message_code = userObject.getString("message_code");
                        if (message_code.equalsIgnoreCase("156")) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //   Toast.makeText(getActivity(), "Audition view updated", Toast.LENGTH_LONG).show();
                                }
                            });


                        }
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Internet not available, Please check your Internet connectivity and try again.");
                    customizeDialog.show();

                }
            });

        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}