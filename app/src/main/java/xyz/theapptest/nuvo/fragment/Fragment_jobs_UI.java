package xyz.theapptest.nuvo.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
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
import xyz.theapptest.nuvo.adapter.JobArtistOngoing;
import xyz.theapptest.nuvo.adapter.JobArtistSaved;
import xyz.theapptest.nuvo.adapter.JobRequestAdaper;
import xyz.theapptest.nuvo.adapter.UnsaveJobListner;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.pojo.NewAuditionsPojo;
import xyz.theapptest.nuvo.ui.ArtistHomescreen;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.ui.SignInActivity;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.OngoingArtistJob;

/**
 * Created by trtcpu007 on 1/8/16.
 */

public class Fragment_jobs_UI extends Fragment implements View.OnClickListener, UnsaveJobListner {
    TextView tv_fragone, tv_fragtwo, tv_fragthree;
    LinearLayout lv_fragone, lv_fragtwo, lv_fragthree;
    String usertype;
    ArrayList<OngoingArtistJob> ongoingjoblist = null;
    JobArtistOngoing adapter;
    JobArtistSaved adaptersaved;
    ListView lv;
    String userid, token;
    ProgressDialog pd;


    String urlongoing = WebApiCall.baseURl + "job/jobs/ongoing";
    String urlpast = WebApiCall.baseURl + "job/jobs/past";
    String urlsaved = WebApiCall.baseURl + "job/jobs/saved";
    String jobType = "";


    android.support.v7.widget.Toolbar toolbar1;
    Button btn, btnleft;
    TextView tx2;
    ConstantData constantData;
    LinearLayout mainLL;
    TextView toolbartv_title, tv_right;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jobs_ui, null);
        init(rootView);
        constantData = ConstantData.getInstance();
        typeofuser();
        firstfragload();
        setUpListner();
        //webServiceCall(userid, token, urlongoing, "ongoing-artist");
        //  displayListview();
        return rootView;
    }

    private void
    displayListview(String typeofuser) {
        try {
            if (ongoingjoblist != null) {
                Log.e("typeofuser", typeofuser);
                if (typeofuser.equalsIgnoreCase("saved-artist")) {
                    adaptersaved = new JobArtistSaved(getActivity().getApplicationContext(), R.layout.job_artist_saved, ongoingjoblist, this);
                    Log.e("Adapter:", Integer.toString(ongoingjoblist.size()));
                    lv.setAdapter(adaptersaved);
                    adaptersaved.notifyDataSetChanged();

                } else {
                    adapter = new JobArtistOngoing(getActivity().getApplicationContext(), R.layout.job_artist_ongoing, ongoingjoblist);
                    Log.e("Adapter:", Integer.toString(ongoingjoblist.size()));
                    lv.setAdapter(adapter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pd != null)
            pd.dismiss();
    }


    private void webServicecallUnSave(final String ids, final String operation) {
        SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
        String usertype = shf.getString("usertype", null);
        userid = shf.getString("userid", null);
        token = shf.getString("token", null);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    webServiceCallUnSaveData(userid, token, ids, operation);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


    }

    private void webServiceCallUnSaveData(final String userid, final String token, String id, String operation) {
        if (CheckInternetConnection.isConnected(getActivity())) {

            String text = "";
            BufferedReader reader = null;


            try {


                String url = WebApiCall.baseURl + "/job/" + id + "/" + operation;

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
                        if (message_code.equalsIgnoreCase("167")) {
                            Log.e("saved-artist", "saved-artist");
                            Log.e("userid", userid);
                            Log.e("token", token);

                            webServiceCall(userid, token, urlsaved, "saved-artist");
                          /*  getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });*/


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


    private void setUpListner() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                OngoingArtistJob ongoingArtistJobPojo = ongoingjoblist.get(position);

                try {
                    Gson gson = new Gson();
                    String gsonString = gson.toJson(ongoingArtistJobPojo);
                    constantData.setOnGoingJobsJsonString(gsonString);
                    constantData.setJobType(jobType);
                    constantData.setSourcejobartist("artistjobs");
                    constantData.setJobdetailsartist("artistjobs");
                    mainLL.removeAllViewsInLayout();
                    Fragment newFragment = new FragmentJobDetails();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragment_jobs_ui_main_LL_id, newFragment);
                    transaction.addToBackStack(null);


                    transaction.commit();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void firstfragload() {
        try {
            pd = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);

            pd.setMessage("Please wait.");
            pd.setCancelable(false);
            pd.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                    //String url = "http://nuvo.theapptest.xyz/v2/api/job/jobs/ongoing";
                    String url = WebApiCall.baseURl + "job/jobs/ongoing";

                    webServiceCall(userid, token, url, "ongoing-artist");

                } catch (Exception e) {

                }
            }
        });
        thread.start();


    }


    private void init(View rootView) {
        ArtistHomescreen act = (ArtistHomescreen) getActivity();
        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
        toolbartv_title = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        tv_right = (TextView) toolbar1.findViewById(R.id.rightlabel);
        tv_right.setVisibility(View.GONE);
        toolbartv_title.setText("My Jobs");
        btnleft = (Button) toolbar1.findViewById(R.id.btnleft);
        btnleft.setVisibility(View.GONE);
        btn = (Button) toolbar1.findViewById(R.id.btn);
        btn.setVisibility(View.GONE);

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
        mainLL = (LinearLayout) rootView.findViewById(R.id.fragment_jobs_ui_main_LL_id);
        // mainLL.removeAllViews();

    }

    private void typeofuser() {
        Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Regular.ttf");

        tv_fragone.setText("Ongoing");
        tv_fragone.setTypeface(facetxtsigintextbox);
        tv_fragtwo.setText("Past");
        tv_fragtwo.setTypeface(facetxtsigintextbox);
        tv_fragthree.setText("Saved");
        tv_fragthree.setTypeface(facetxtsigintextbox);


    }

    private void webServiceCall(String userid, String token, String url, final String typeofrequest) {
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
                System.out.println(response.toString());
                Log.e("artistresponse", response.toString());
                if (response.toString() != null) {
                    if (response.toString().startsWith("{")) {
                        JSONObject userObject = new JSONObject(response.toString());
                        String message_code = userObject.getString("message_code");
                        if (message_code.equalsIgnoreCase("1116")) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("clearlistview", "clearlistview");
                                    if (pd != null)
                                        pd.dismiss();
                                    if (ongoingjoblist != null) {
                                        ongoingjoblist.clear();
                                        if (typeofrequest.equalsIgnoreCase("saved-artist")) {
                                            if(adaptersaved!=null)
                                            adaptersaved.notifyDataSetChanged();
                                        }
                                        if(adapter!=null) {
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            });

                        }
                    } else {

                        //parsing
                        JSONArray json = new JSONArray(response.toString());
                        ongoingjoblist = new ArrayList<OngoingArtistJob>();

                        for (int i = 0; i < json.length(); i++) {
                            JSONObject e = json.getJSONObject(i);
                            OngoingArtistJob job = new OngoingArtistJob();
                            job.setJobid(e.getString("job_id"));


                            if (e.getString("job_title") != null) {
                                job.setTitle(e.getString("job_title"));
                            }
                            if (e.getString("created_on") != null) {
                                job.setCreatedon(e.getString("created_on"));
                            }

                            if (e.getString("first_name") != null) {
                                job.setFirstname(e.getString("first_name"));
                                Log.e("firstname", e.getString("first_name"));
                            }
                            if (e.getString("last_name") != null) {
                                job.setLastname(e.getString("last_name"));
                            }
                            if (e.getString("company_name") != null) {
                                job.setCompanyname(e.getString("company_name"));
                            }
                            if (e.getString("description") != null) {
                                job.setDescription(e.getString("description"));
                            }
                            if (e.getString("attachments") != null) {
                                job.setAttachments(e.getString("attachments"));
                            }

                            if (jobType.equalsIgnoreCase("Past"))
                                job.setJobstatus("13");
                            else if (jobType.equalsIgnoreCase("Ongoing"))
                                job.setJobstatus("12");
                            else
                                job.setJobstatus("11");


                            ongoingjoblist.add(job);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (pd != null)
                                    pd.dismiss();
                                try {
                                    displayListview(typeofrequest);
                                } catch (Exception e) {
                                    e.printStackTrace();
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

                    if (pd != null)
                        pd.dismiss();
                    if (ongoingjoblist != null) {
                        ongoingjoblist.clear();
                        adapter.notifyDataSetChanged();
                    }
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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        // firstfragload();
    }

    @Override
    public void onClick(View v) {
        SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
        String usertype = shf.getString("usertype", null);
        userid = shf.getString("userid", null);
        token = shf.getString("token", null);

        Log.e("userid", userid);
        Log.e("token", token);

        try {
            pd = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);

            pd.setMessage("Please wait.");
            pd.setCancelable(false);
            pd.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (v.getId()) {
            case R.id.frag_one:
                jobType = "Ongoing";
                tv_fragone.setTextColor(Color.parseColor("#af304c"));
                tv_fragtwo.setTextColor(Color.parseColor("#afa9f0"));
                tv_fragthree.setTextColor(Color.parseColor("#afa9f0"));
                // String url = "http://nuvo.theapptest.xyz/v1/api/job/ongoing";
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            webServiceCall(userid, token, urlongoing, "ongoing-artist");
                          /*  getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    displayListview();
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
                jobType = "Past";
                tv_fragone.setTextColor(Color.parseColor("#afa9f0"));
                tv_fragtwo.setTextColor(Color.parseColor("#af304c"));
                tv_fragthree.setTextColor(Color.parseColor("#afa9f0"));


                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {


                            webServiceCall(userid, token, urlpast, "past-artist");
                         /*   getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    displayListview();
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
                jobType = "Saved";
                tv_fragone.setTextColor(Color.parseColor("#afa9f0"));
                tv_fragtwo.setTextColor(Color.parseColor("#afa9f0"));
                tv_fragthree.setTextColor(Color.parseColor("#af304c"));
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            webServiceCall(userid, token, urlsaved, "saved-artist");

                        } catch (Exception e) {

                        }
                    }
                });
                thread2.start();


                break;

        }

    }

    @Override
    public void onUnsavedata(View v, int position, String jobid) {
        Log.e("jobid", jobid);
        webServicecallUnSave(jobid, "unsave");
    }
}