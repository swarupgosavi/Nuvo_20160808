package xyz.theapptest.nuvo.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.adapter.JobRequestAdaper;
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

/**
 * Created by trtcpu007 on 3/8/16.
 */
public class Fragment_jobs_producer extends Fragment implements View.OnClickListener {
    android.support.v7.widget.Toolbar toolbar1;
    TextView txt1, tv_title, tv_right;
    Button bntgbell;
    TextView tv_fragone, tv_fragtwo, tv_fragthree;
    LinearLayout lv_fragone, lv_fragtwo, lv_fragthree;
    ListView lv;
    String userid, token;
    String urlrequest = "http://nuvo.theapptest.xyz/v2/api/job/jobs/request";
    String urlongoings = "http://nuvo.theapptest.xyz/v2/api/job/jobs/ongoing";
    String urlpasts = "http://nuvo.theapptest.xyz/v2/api/job/jobs/past";
    ArrayList<ProducerJob> producerjoblist = null;
    ProducerJobRequest adapter;
    LinearLayout lv_fragment_producer;
    ConstantData con;
    Button btnleft, btnright;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_producer_jobs, null);
      /*  toolbar1 = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar_top);
        txt1 = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        Typeface face1 = Typeface.createFromAsset(getActivity().getAssets(),
                "font/HelveticaNeue.ttf");
        txt1.setText("Jobs");
        txt1.setTypeface(face1);
        bntgbell= (Button) toolbar1.findViewById(R.id.btn);
        bntgbell.setBackgroundResource(R.drawable.bell_icon_toolicon);
        bntgbell.setVisibility(View.VISIBLE);*/
        con = ConstantData.getInstance();
        init(rootView);
        typeofuser();
      //  firstfragload();

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        firstfragload();
    }

    private void typeofuser() {

        Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Regular.ttf");

        tv_fragone.setText("Requests");
        tv_fragone.setTypeface(facetxtsigintextbox);
        tv_fragtwo.setText("Ongoing");
        tv_fragtwo.setTypeface(facetxtsigintextbox);
        tv_fragthree.setText("Past");
        tv_fragthree.setTypeface(facetxtsigintextbox);
        ProducerHomeScreen act = (ProducerHomeScreen) getActivity();
// act.getActionBar().setDisplayShowHomeEnabled(true);
        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
        toolbar1.setNavigationIcon(R.drawable.menu_icon);
        tv_title = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        tv_right = (TextView) toolbar1.findViewById(R.id.rightlabel);
        tv_right.setVisibility(View.GONE);
        tv_title.setText("Jobs");
        tv_title.setTextSize(19);
        btnright = (Button) toolbar1.findViewById(R.id.btn);
        btnright.setVisibility(View.VISIBLE);
        btnright.setBackgroundResource(R.drawable.bell_icon_toolicon);
        btnleft = (Button) toolbar1.findViewById(R.id.btnleft);
        btnleft.setVisibility(View.GONE);


    }


    private void displayListview(final String type) {
        try {
            if (producerjoblist != null) {
                adapter = new ProducerJobRequest(getActivity().getApplicationContext(), R.layout.custom_producer_job, producerjoblist);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (type.equalsIgnoreCase("Requests")) {

                            ProducerJob findJobArtistPojo = producerjoblist.get(position);
                            Gson gson = new Gson();
                            String gsonString = gson.toJson(findJobArtistPojo);
                            con.setRequestproducer(gsonString);

                            con.setSourceflagjobs("jobs");
                            con.setRequestproducerflag("jobs");
                            lv_fragment_producer.removeAllViews();
                            Fragment newFragment = new Fragment_Request_Details();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();


                            transaction.replace(R.id.lv_fragment_producer, newFragment);
                            transaction.addToBackStack(null);


                            transaction.commit();


                        } else {

                            if (type.equalsIgnoreCase("Ongoing")) {
                                ProducerJob findJobArtistPojo = producerjoblist.get(position);
                                Gson gson = new Gson();
                                String gsonString = gson.toJson(findJobArtistPojo);
                                con.setOngoingproducer(gsonString);
                                con.setSourceflagjobs("jobs");
                                con.setRequestproducerflag("jobs");
                                lv_fragment_producer.removeAllViews();
                                Fragment newFragment = new Fragment_producer_ongoing();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();


                                transaction.replace(R.id.lv_fragment_producer, newFragment);
                                transaction.addToBackStack(null);


                                transaction.commit();

                            } else {
                                if (type.equalsIgnoreCase("Past")) {
                                    ProducerJob findJobArtistPojo = producerjoblist.get(position);
                                    Gson gson = new Gson();
                                    String gsonString = gson.toJson(findJobArtistPojo);
                                    con.setPastwebresponse(gsonString);

                                    con.setSourceflagjobs("jobs");
                                    con.setRequestproducerflag("jobs");
                                    lv_fragment_producer.removeAllViews();
                                    Fragment newFragment = new Fragment_producer_past();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();


                                    transaction.replace(R.id.lv_fragment_producer, newFragment);
                                    transaction.addToBackStack(null);


                                    transaction.commit();

                                }
                            }
                        }
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void webServiceCall(String userid, String token, String url, final String type) {
        if (CheckInternetConnection.isConnected(getActivity())) {
            try {

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userid + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
                con.setRequestProperty("Authorization", "Basic " + encoded);
                // optional default is GET
                con.setRequestMethod("GET");


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
                //System.out.println(response.toString());
                // Log.e("producerrequestresponse", response.toString());
                if (response.toString() != null) {
                    {
                        if (response.toString().startsWith("{")) {
                            JSONObject userObject = new JSONObject(response.toString());
                            String message_code = userObject.getString("message_code");

                            if (message_code != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                 /*       CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                                        customizeDialog.setTitle("nuvo");
                                        customizeDialog.setCancelable(false);
                                        customizeDialog.setMessage("Data not found.");
                                        customizeDialog.show();*/
                                        if (producerjoblist != null && !producerjoblist.isEmpty()) {


                                            producerjoblist.clear();
                                        }
                                    }
                                });


                            }


                        } else {

                            //parsing
                            JSONArray json = new JSONArray(response.toString());
                            producerjoblist = new ArrayList<ProducerJob>();
                            for (int i = 0; i < json.length(); i++) {
                                //  HashMap<String, String> map = new HashMap<String, String>();
                                JSONObject e = json.getJSONObject(i);
                                ProducerJob job = new ProducerJob();
                                job.setJobid(e.getString("job_id"));


                                if (e.getString("job_title") != null) {
                                    job.setTitle(e.getString("job_title"));
                                }


                                if (e.getString("created_on") != null) {
                                    job.setCreatedon(e.getString("created_on"));
                                }
                                if (e.getString("job_status") != null) {
                                    job.setStatus(e.getString("job_status"));
                                }
                                if (e.getString("first_name") != null) {
                                    job.setFirstname(e.getString("first_name"));
                                    Log.e("firstname", e.getString("first_name"));
                                }
                                if (e.getString("last_name") != null) {
                                    job.setLastname(e.getString("last_name"));
                                }


                                producerjoblist.add(job);
                            }
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (producerjoblist != null) {
                                    displayListview(type);
                                }
                            }
                        });
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
            if (producerjoblist != null && !producerjoblist.isEmpty()) {


                producerjoblist.clear();
            }

        }


    }


    public void init(View rootView) {
        tv_fragone = (TextView) rootView.findViewById(R.id.frag_one);
        tv_fragtwo = (TextView) rootView.findViewById(R.id.frag_two);
        tv_fragthree = (TextView) rootView.findViewById(R.id.frag_three);
        tv_fragone.setOnClickListener(this);
        tv_fragtwo.setOnClickListener(this);
        tv_fragthree.setOnClickListener(this);
        lv_fragone = (LinearLayout) rootView.findViewById(R.id.lv_fragone);
        lv_fragtwo = (LinearLayout) rootView.findViewById(R.id.lv_fragone);
        lv_fragthree = (LinearLayout) rootView.findViewById(R.id.lv_fragtwo);
        lv = (ListView) rootView.findViewById(R.id.list_result);
        lv_fragment_producer = (LinearLayout) rootView.findViewById(R.id.lv_fragment_producer);


    }

    private void firstfragload() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
                    String usertype = shf.getString("usertype", null);
                    String userid = shf.getString("userid", null);
                    String token = shf.getString("token", null);

                    Log.e("userid", userid);
                    Log.e("token", token);

                    tv_fragone.setTextColor(Color.parseColor("#af304c"));
                    tv_fragtwo.setTextColor(Color.parseColor("#afa9f0"));
                    tv_fragthree.setTextColor(Color.parseColor("#afa9f0"));
                    String url = "http://nuvo.theapptest.xyz/v2/api/job/jobs/request";
                    webServiceCall(userid, token, url, "Requests");

                } catch (Exception e) {

                }
            }
        });
        thread.start();


    }

    @Override
    public void onClick(View v) {
        SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
        String usertype = shf.getString("usertype", null);
        userid = shf.getString("userid", null);
        token = shf.getString("token", null);

        Log.e("userid", userid);
        Log.e("token", token);
        switch (v.getId()) {
            case R.id.frag_one:
                tv_fragone.setTextColor(Color.parseColor("#af304c"));
                tv_fragtwo.setTextColor(Color.parseColor("#afa9f0"));
                tv_fragthree.setTextColor(Color.parseColor("#afa9f0"));

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            webServiceCall(userid, token, urlrequest, "Requests");
                          /*  getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    displayListview("Requests");
                                }
                            });*/
                        } catch (Exception e) {

                        }
                    }
                });
                thread.start();

                //  displayListview();

                break;

            case R.id.frag_two:
                tv_fragone.setTextColor(Color.parseColor("#afa9f0"));
                tv_fragtwo.setTextColor(Color.parseColor("#af304c"));
                tv_fragthree.setTextColor(Color.parseColor("#afa9f0"));
                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            webServiceCall(userid, token, urlongoings, "Ongoing");
                           /* getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    displayListview("Ongoing");
                                }
                            });*/
                        } catch (Exception e) {

                        }
                    }
                });
                thread1.start();

                //    displayListview();
                break;

            case R.id.frag_three:
                tv_fragone.setTextColor(Color.parseColor("#afa9f0"));
                tv_fragtwo.setTextColor(Color.parseColor("#afa9f0"));
                tv_fragthree.setTextColor(Color.parseColor("#af304c"));
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            webServiceCall(userid, token, urlpasts, "Past");
                           /* getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    displayListview("Past");
                                }
                            });*/
                        } catch (Exception e) {

                        }
                    }
                });
                thread2.start();


                break;

        }


    }
}