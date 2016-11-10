package xyz.theapptest.nuvo.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import android.widget.Toast;

import com.google.gson.Gson;
//import com.navdrawer.SimpleSideDrawer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.adapter.FindListadapter;
import xyz.theapptest.nuvo.adapter.Findartistadapter;
import xyz.theapptest.nuvo.adapter.JobRequestAdaper;
import xyz.theapptest.nuvo.adapter.Jobwithaudition;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.ui.Activity_Criteradetails;
import xyz.theapptest.nuvo.ui.ArtistHomescreen;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.FindJobs;
import xyz.theapptest.nuvo.utils.FinfjobArtist;
import xyz.theapptest.nuvo.utils.OngoingArtistJob;

/**
 * Created by trtcpu007 on 1/8/16.
 */

public class Fragment_CriteriaArtist extends Fragment implements Jobwithaudition {

    //String urlfind = "http://nuvo.theapptest.xyz/v1/api/auditions";
    String urlfind = WebApiCall.baseURl + "/job/jobs/find";

    String userid, token;
    ArrayList<FinfjobArtist> findjobartist = null;
    Findartistadapter adapter;
    ListView lv;
    ConstantData con;
    private ViewPager pager = null;
    private TabFragmentArtist pagerAdapter = null;
    LinearLayout lvroot;
    android.support.v7.widget.Toolbar toolbar1;
    Button btn, btnleft;
    TextView tx2;
    ProgressDialog dialog;
    //SimpleSideDrawer slide_me;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_criteriaartist, null);

        lv = (ListView) x.findViewById(R.id.list_result);
        lvroot = (LinearLayout) x.findViewById(R.id.rootfragment);
        ArtistHomescreen act = (ArtistHomescreen) getActivity();
        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
        btnleft = (Button) toolbar1.findViewById(R.id.btnleft);
        btnleft.setVisibility(View.GONE);
        btn = (Button) toolbar1.findViewById(R.id.btn);
        btn.setBackgroundResource(R.drawable.filterimg);
        btn.setVisibility(View.VISIBLE);

        tx2 = (TextView) toolbar1.findViewById(R.id.rightlabel);
        tx2.setVisibility(View.GONE);

        webservicecall();

        con = ConstantData.getInstance();
        return x;
    }

    private void webServicecallSave(final String ids, final String operation) {
        SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
        String usertype = shf.getString("usertype", null);
        userid = shf.getString("userid", null);
        token = shf.getString("token", null);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    webServiceCallSaveData(userid, token, ids, operation);

                } catch (Exception e) {

                }
            }
        });
        thread.start();


    }

    private void displayListview() {
        try {
            if (findjobartist != null) {
                adapter = new Findartistadapter(getActivity().getApplicationContext(), R.layout.custon_findartist, findjobartist, this);
                lv.setAdapter(adapter);
            }
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    FinfjobArtist findJobArtistPojo = findjobartist.get(position);
                    Gson gson = new Gson();
                    String gsonString = gson.toJson(findJobArtistPojo);
                    con.setFindjobresponse(gsonString);
                    con.setSourceflagartist("artistfind");
                    con.setFindartistdetails("artistfind");
                    lvroot.removeAllViewsInLayout();
                    Fragment newFragment = new Activity_Criteradetails();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();


                    transaction.replace(R.id.rootfragment, newFragment);
                    transaction.addToBackStack(null);


                    transaction.commit();


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void webservicecall() {
        SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
        String usertype = shf.getString("usertype", null);
        userid = shf.getString("userid", null);
        token = shf.getString("token", null);
        dialog = new ProgressDialog(getActivity(),ProgressDialog.THEME_HOLO_LIGHT);
        dialog.setMessage("Please Wait!!");
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    webServiceCall(userid, token, urlfind);
                    dialog.dismiss();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            displayListview();
                        }
                    });
                } catch (Exception e) {

                }
            }
        });
        thread.start();
    }


    private void webServiceCall(String userid, String token, String url) {
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
                Log.e("criteriaresp[onse", response.toString());
                if (response.toString() != null) {
                    if (response.toString().startsWith("{")) {
                        JSONObject userObject = new JSONObject(response.toString());
                        String message_code = userObject.getString("message_code");
                        if (message_code.equalsIgnoreCase("112")) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                                    customizeDialog.setTitle("nuvo");
                                    customizeDialog.setCancelable(false);
                                    customizeDialog.setMessage("Data not found.");
                                    customizeDialog.show();
                                    if (findjobartist != null) {


                                        findjobartist.clear();
                                    }
                                }
                            });

                        }
                    } else {


                        JSONArray json = new JSONArray(response.toString());
                        findjobartist = new ArrayList<FinfjobArtist>();
                        for (int i = 0; i < json.length(); i++) {
                            //  HashMap<String, String> map = new HashMap<String, String>();
                            JSONObject e = json.getJSONObject(i);
                            FinfjobArtist job = new FinfjobArtist();
                            job.setJobid(e.getString("job_id"));
                            //Log.e("jobid", e.getString("id"));
                            job.setTitle(e.getString("job_title"));
                            if (e.getString("created_on") != null && !e.getString("created_on").isEmpty()) {
                                job.setCreatedon(e.getString("created_on"));
                            }
                            if (e.getString("company_name") != null && !e.getString("company_name").isEmpty()) {
                                job.setCompanyname(e.getString("company_name"));
                            }
                            if (e.getString("first_name") != null && !e.getString("first_name").isEmpty()) {
                                job.setFirstname(e.getString("first_name"));
                            }
                            if (e.getString("last_name") != null && !e.getString("last_name").isEmpty()) {
                                job.setLastname(e.getString("last_name"));
                            }
                            job.setDescription(e.getString("description"));

                            if (e.getString("attachments") != null && !e.getString("attachments").isEmpty()) {
                                job.setAttachments(e.getString("attachments"));
                            }
                            //  job.setExpirydate(e.getString("expiry_date"));
                            /*if (e.getString("attachments") != null && !e.getString("attachments").isEmpty()) {
                                job.setAttachments(e.getString("attachments"));
                            }
                            if (e.getString("views") != null && !e.getString("views").isEmpty()) {
                                job.setView(e.getString("views"));
                            }
                            if (e.getString("status") != null && !e.getString("status").isEmpty()) {
                                job.setStatus(e.getString("status"));
                            }

                            if (e.getString("created_by") != null && !e.getString("created_by").isEmpty()) {
                                job.setCreatedby(e.getString("created_by"));
                            }

                            if (e.getString("last_modified_on") != null && !e.getString("last_modified_on").isEmpty()) {
                                job.setLastmodifiedon(e.getString("last_modified_on"));
                            }

                            if (e.getString("last_modified_by") != null && !e.getString("last_modified_by").isEmpty()) {
                                job.setLastmodifiedby(e.getString("last_modified_by"));
                            }
*/

                            findjobartist.add(job);
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

    private void webServiceCallSaveData(String userid, String token, String id, String operation) {
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
                        if (message_code.equalsIgnoreCase("158")) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Toast.makeText(getActivity(), "Job added to Save List", Toast.LENGTH_LONG).show();
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
    public void onaudionclick(View v, int position, String id) {

        FinfjobArtist fd = findjobartist.get(position);
        if (fd.getSelected() == 0) {
            fd.setSelected(1);
            webServicecallSave(id, "save");

            adapter.notifyDataSetChanged();
        } else {
            fd.setSelected(0);
            webServicecallSave(id, "unsave");
            //webServicecallDelete(id);
            adapter.notifyDataSetChanged();
        }


    }


    public void webServiceCallDeleteData(String userid, String token, String id) {
        if (CheckInternetConnection.isConnected(getActivity())) {

            String text = "";
            BufferedReader reader = null;


            try {

                //String url = "http://nuvo.theapptest.xyz/v1/api/auditions/" + id + "/save";
                String url = WebApiCall.baseURl + "/job/jobs/audition/" + id + "/save";

                URL obj = new URL(url);


                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userid + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
                con.setRequestProperty("Authorization", "Basic " + encoded);
                con.setRequestMethod("DELETE");
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
                Log.e("delete response", text);
                if (text != null) {
                    if (text.startsWith("{")) {
                        JSONObject userObject = new JSONObject(text);
                        String message_code = userObject.getString("message_code");
                        if (message_code.equalsIgnoreCase("159")) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Toast.makeText(getActivity(), "Job removed from Save List", Toast.LENGTH_LONG).show();
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


    private void webServicecallDelete(final String id) {

        SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
        String usertype = shf.getString("usertype", null);
        userid = shf.getString("userid", null);
        token = shf.getString("token", null);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    webServiceCallDeleteData(userid, token, id);

                } catch (Exception e) {

                }
            }
        });
        thread.start();


    }
}
