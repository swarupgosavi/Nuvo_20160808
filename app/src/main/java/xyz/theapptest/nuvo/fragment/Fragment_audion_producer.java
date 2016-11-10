package xyz.theapptest.nuvo.fragment;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.adapter.AudiotionDsiplayAdapter;
import xyz.theapptest.nuvo.adapter.AuditionDisplayObjects;
import xyz.theapptest.nuvo.adapter.ProducerJobRequest;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.ui.Activity_Criteradetails;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.ui.ProducerHomeScreen;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.FinfjobArtist;
import xyz.theapptest.nuvo.utils.OngoingArtistJob;
import xyz.theapptest.nuvo.utils.ProducerJob;

import xyz.theapptest.nuvo.R;



public class Fragment_audion_producer extends Fragment implements View.OnClickListener {
    android.support.v7.widget.Toolbar toolbar1;
    TextView txt1;
    Button bntgbell,btnright;
    ImageView imgcreate;
    TextView tv;
    ListView lv;
    String urlgetaudition = "http://nuvo.theapptest.xyz/v2/api/job/jobs/audition";
    String userid, token;
    LinearLayout lvroot;
    ConstantData con;
    ArrayList<AuditionDisplayObjects> audiotionjoblist = null;
    AudiotionDsiplayAdapter adapter;
    Button btnleft;
    TextView tv_title,tv_right;
    ProgressDialog imgdialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_audition_producer, null);
        con = ConstantData.getInstance();
        init(rootView);
        setTypeface();


        webServicecallshowaudion();


        return rootView;
    }


    private void setTypeface() {
        Typeface facetxttitle = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Avenir Medium.otf");
        tv.setTypeface(facetxttitle);


    }

    private void webServicecallshowaudion() {
        SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
        String usertype = shf.getString("usertype", null);
        userid = shf.getString("userid", null);
        token = shf.getString("token", null);

      /*  imgdialog = new ProgressDialog(getActivity());
        imgdialog.setMessage("Please Wait!!");
        imgdialog.setCancelable(false);
        imgdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        imgdialog.show();*/

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    callAudionData(userid, token);
                } catch (Exception e) {

                }
            }
        });
        thread.start();


    }

    private void displayListview() {
        try {
            if (audiotionjoblist != null) {

                Log.e("audiotionjoblist:Length", Integer.toString(audiotionjoblist.size()));
                adapter = null;

                adapter = new AudiotionDsiplayAdapter(getActivity().getApplicationContext(), R.layout.custom_audition_view, audiotionjoblist);
                adapter.notifyDataSetChanged();
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AuditionDisplayObjects findJobArtistPojo = audiotionjoblist.get(position);
                        Gson gson = new Gson();
                        String gsonString = gson.toJson(findJobArtistPojo);
                        con.setAuditionproducerdetails(gsonString);
                        con.setSource_flag("audition");
                        con.setAudition_flag("audition");
                        lvroot.removeAllViewsInLayout();
                        Fragment newFragment = new Fragment_AudionDetailsProducer();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();


                        transaction.replace(R.id.lvauditionproducer, newFragment);
                        transaction.addToBackStack(null);


                        transaction.commit();

//                       lvroot.removeAllViewsInLayout();
//                        Fragment newFragment = new Activity_Criteradetails();
//                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                        transaction.replace(R.id.rootfragment, newFragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  imgdialog.dismiss();
    }

    private void callAudionData(String userid, String token) {
        if (CheckInternetConnection.isConnected(getActivity())) {
            try {

                URL obj = new URL(urlgetaudition);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userid + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
                con.setRequestProperty("Authorization", "Basic " + encoded);
                // optional default is GET
                con.setRequestMethod("GET");


                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + urlgetaudition);
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
                Log.e("getaudition", response.toString());
                if (response.toString() != null) {
                    if (response.toString().startsWith("{")) {
                        JSONObject userObject = new JSONObject(response.toString());
                        String message_code = userObject.getString("message_code");
                        if (message_code.equalsIgnoreCase("1116")) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                   /* CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                                    customizeDialog.setTitle("nuvo");
                                    customizeDialog.setCancelable(false);
                                    customizeDialog.setMessage("Data not found.");
                                    customizeDialog.show();*/

                                }
                            });

                        }
                    } else {
                        //parsing
                        JSONArray json = new JSONArray(response.toString());
                        audiotionjoblist = new ArrayList<AuditionDisplayObjects>();
                        for (int i = 0; i < json.length(); i++) {
                            //  HashMap<String, String> map = new HashMap<String, String>();
                            JSONObject e = json.getJSONObject(i);
                            AuditionDisplayObjects job = new AuditionDisplayObjects();
                            job.setId(e.getString("job_id"));
                            Log.e("jobid", e.getString("job_id"));

                            if (e.getString("job_title") != null) {
                                job.setTitle(e.getString("job_title"));
                            }
                            if (e.getString("created_on") != null) {
                                job.setCreated_on(e.getString("created_on"));
                            }
                            if (e.getString("views") != null) {
                                job.setViews(e.getString("views"));
                            }

                            if (e.getString("expiry") != null) {
                                job.setExpirydate(e.getString("expiry"));
                            }
                            if (e.getString("description") != null) {
                                job.setDescription(e.getString("description"));
                            }
                            if (e.getString("attachments") != null) {
                                job.setAttachment(e.getString("attachments"));
                            }
                            if (e.getString("auditionee_count") != null) {
                                job.setAuditioneers(e.getString("auditionee_count"));
                            }

                            if (!e.getString("auditionee_count").equals("0")) {
                                JSONObject userinfo = e.getJSONObject("auditionees");
                                if ((userinfo.toString() != null) && (userinfo.toString() != "")) {
                                    if (userinfo.getString("id") != null) {
                                        job.setAudionid(userinfo.getString("id"));
                                    }
                                    if (userinfo.getString("first_name") != null) {
                                        job.setFirst_name(userinfo.getString("first_name"));
                                        Log.e("firstname", userinfo.getString("first_name"));
                                    }
                                    if (userinfo.getString("last_name") != null) {
                                        job.setLast_name(userinfo.getString("last_name"));

                                    }

                                    if (userinfo.getString("demo") != null) {
                                        job.setDemo(userinfo.getString("demo"));
                                    }
                                  /*  if (userinfo.getString("job_status") != null) {
                                        job.setJobstatusaudition(userinfo.getString("job_status"));
                                    }*/

                                    if (userinfo.getString("rating") != null) {
                                        job.setRating(userinfo.getString("rating"));
                                    }
                                }

                                //job.setAuditioneers(e.getString("auditionee_count"));
                                if (e.getString("duration") != null) {
                                    job.setDuration(e.getString("duration"));
                                }
                            }

                            audiotionjoblist.add(job);
                        }

                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   try {
                       displayListview();
                   }catch (Exception e)
                   {
                       e.printStackTrace();
                   }

                }
            });

        } else {
           /* getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Internet not available, Please check your Internet connectivity and try again.");
                    customizeDialog.show();

                }
            });*/

        }


    }

    public void init(View v) {
        imgcreate = (ImageView) v.findViewById(R.id.img_create);
        imgcreate.setOnClickListener(this);

        lvroot = (LinearLayout) v.findViewById(R.id.lvauditionproducer);

        tv = (TextView) v.findViewById(R.id.tv_create);
        tv.setOnClickListener(this);

        lv = (ListView) v.findViewById(R.id.list_result_audition);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lv.setLayoutParams(params);

        ProducerHomeScreen act = (ProducerHomeScreen) getActivity();

// act.getActionBar().setDisplayShowHomeEnabled(true);
        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
        toolbar1.setNavigationIcon(R.drawable.menu_icon);
        tv_title = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        tv_right = (TextView) toolbar1.findViewById(R.id.rightlabel);
        tv_right.setVisibility(View.GONE);
        tv_title.setTextSize(19);
        tv.setTextSize(14);
        btnright= (Button) toolbar1.findViewById(R.id.btn);
        btnright.setVisibility(View.GONE);
        btnleft = (Button) toolbar1.findViewById(R.id.btnleft);
        btnleft.setVisibility(View.GONE);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_create:
            case R.id.tv_create:
                con.setSource_flag("audition");
                con.setAudition_flag("audition");
                lvroot.removeAllViewsInLayout();
                Fragment newFragment = new FragmentAuditionCreation();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.lvauditionproducer, newFragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();


                break;

        }


    }
}