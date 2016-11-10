package xyz.theapptest.nuvo.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import xyz.theapptest.nuvo.adapter.Producer_audition_Object;
import xyz.theapptest.nuvo.adapter.Producerjobdetails;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.ui.ProducerHomeScreen;
import xyz.theapptest.nuvo.ui.SignUpActivity;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.FinfjobArtist;
import xyz.theapptest.nuvo.utils.ProducerJob;

/**
 * Created by ADMIN on 8/6/2016.
 */
public class Fragment_Request_Details extends Fragment {
    LinearLayout lvroot;
    ConstantData con;
    String jobid, audioname, firstname, lastname, createdon, status;
    TextView tv_audname, tv_firstname, tv_lastname, tv_createdon, tv_description;
    String userid, token;
    Button btn, btnleft;
    android.support.v7.widget.Toolbar toolbar1;
    TextView tv_title;
    String userstatus;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_request_details, null);
        con = ConstantData.getInstance();
        init(x);
        typeofuser();
        return x;
    }

    /*private void init(View x) {
        lvroot = (LinearLayout) x.findViewById(R.id.rootfragment);

        tv_audname = (TextView) x.findViewById(R.id.audition_details_title_tv_id);
        //  tv_firstname=(TextView)x.findViewById(R.id.audition_firstname);
        //   tv_lastname=(TextView)x.findViewById(R.id.audition_lastname);
        tv_createdon = (TextView) x.findViewById(R.id.audition_details_date_tv_id);
        tv_description= (TextView) x.findViewById(R.id.audition_details_layout_description_tv_id);
        Typeface facetxtsigin = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Semibold.ttf");
        tv_audname.setTypeface(facetxtsigin);


        Typeface facetxtsiginbelow = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Light.ttf");
        tv_createdon.setTypeface(facetxtsiginbelow);
        tv_description.setTypeface(facetxtsiginbelow);





    }
*/

    private void init(View x) {
        //toolbar design changes
        ProducerHomeScreen act = (ProducerHomeScreen) getActivity();
        // act.getActionBar().setDisplayShowHomeEnabled(true);
        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
        toolbar1.setNavigationIcon(null);
        btn = (Button) toolbar1.findViewById(R.id.btn);
        btnleft = (Button) toolbar1.findViewById(R.id.btnleft);
        tv_title = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        tv_title.setTextSize(15);
        btn.setVisibility(View.VISIBLE);
        btn.setBackgroundResource(R.drawable.trash);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeleteConfirmationDialog();

            }
        });
        // ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        //  context.getActionBar().setDisplayShowHomeEnabled(false);

        btnleft.setVisibility(View.VISIBLE);
        btnleft.setBackgroundResource(R.drawable.backimg);

        lvroot = (LinearLayout) x.findViewById(R.id.rootfragment);
        btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();


                lvroot.removeAllViews();
                Fragment newFragment = new Fragment_jobs_producer();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();


                transaction.replace(R.id.rootfragment, newFragment);
                transaction.addToBackStack(null);


                transaction.commit();


            }
        });


        tv_audname = (TextView) x.findViewById(R.id.audition_details_title_tv_id);
        //  tv_firstname=(TextView)x.findViewById(R.id.audition_firstname);
        //   tv_lastname=(TextView)x.findViewById(R.id.audition_lastname);
        tv_createdon = (TextView) x.findViewById(R.id.audition_details_date_tv_id);
        tv_description = (TextView) x.findViewById(R.id.audition_details_layout_description_tv_id);
        Typeface facetxtsigin = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Semibold.ttf");
        tv_audname.setTypeface(facetxtsigin);


        Typeface facetxtsiginbelow = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Light.ttf");
        tv_createdon.setTypeface(facetxtsiginbelow);
        tv_description.setTypeface(facetxtsiginbelow);


    }

    private void typeofuser() {


        String usertype = con.getRequestproducer();
        if (usertype != null) {
            Gson gson = new Gson();
            ProducerJob findJobArtistPojo = gson.fromJson(usertype, ProducerJob.class);
            jobid = findJobArtistPojo.getJobid();
            audioname = findJobArtistPojo.getTitle();

            firstname = findJobArtistPojo.getFirstname();
            //  tv_firstname.setText(firstname);
            lastname = findJobArtistPojo.getLastname();
            //   tv_lastname.setText(lastname);
            createdon = findJobArtistPojo.getCreatedon();
            status = findJobArtistPojo.getStatus();
            tv_createdon.setText(createdon);
            if (status.equalsIgnoreCase("11")) {
                userstatus = "Pending";
            } else {
                if (status.equalsIgnoreCase("12")) {
                    userstatus = "Ongoing";
                } else {
                    if (status.equalsIgnoreCase("13")) {
                        userstatus = "Completed";
                    } else {
                        if (status.equalsIgnoreCase("14")) {
                            userstatus = "Rejected";
                        } else {
                            if (status.equalsIgnoreCase("15")) {
                                userstatus = "Accepted";
                            }else
                            {
                                if(status.equalsIgnoreCase("16"))
                                {
                                    userstatus = "Applied";
                                }else
                                {
                                    if(status.equalsIgnoreCase("17"))
                                    {
                                        userstatus = "Expired";
                                    }
                                }
                            }
                        }
                    }
                }
            }

            tv_title.setText(firstname + "\t" + lastname + "\n" + userstatus);
            Log.e("jobsid", jobid);
            webServicecallAudition(jobid);

        }


    }

    private void openDeleteConfirmationDialog() {
        try {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT);

            // Setting Dialog Title
            alertDialog.setTitle("nuvo");

            // Setting Dialog Message
            alertDialog.setMessage("Are you confirm to delete a job ? ");

            // Setting Icon to Dialog


            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    doDeleteWebServiceCall();
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event

                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void webServiceCallAuditionData(String userid, String token, String jobid) {
        if (CheckInternetConnection.isConnected(getActivity())) {

            String text = "";
            BufferedReader reader = null;


            try {
                //String url = "http://nuvo.theapptest.xyz/v2/api/job/" + jobid + "/jobdetails";
                String url = WebApiCall.baseURl + "job/" + jobid + "/jobdetails";

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
                        try {
                            //parsing
                            JSONArray json = new JSONArray(text);

                            for (int i = 0; i < json.length(); i++) {
                                //  HashMap<String, String> map = new HashMap<String, String>();
                                final JSONObject e = json.getJSONObject(i);

                                Producer_audition_Object job = new Producer_audition_Object();
                                job.setJobid(e.getString("job_id"));


                                if (e.getString("job_title") != null) {
                                    job.setTitle(e.getString("job_title"));
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                tv_audname.setText(e.getString("job_title"));
                                            } catch (Exception e1) {
                                                e1.printStackTrace();
                                            }

                                        }
                                    });


                                }


                                if (e.getString("created_on") != null) {
                                    job.setCreated_on(e.getString("created_on"));
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String[] dateArray = e.getString("created_on").split(" ");

                                                String createddate = dateArray[0];
                                                tv_createdon.setText(createddate);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });


                                }


                                if (e.getString("description") != null) {


                                    job.setDescription(e.getString("description"));

                                    getActivity().runOnUiThread(new Runnable() {
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
                                if (e.getString("views") != null) {
                                    job.setViews(e.getString("views"));
                                }
                                if (e.getString("expiry_date") != null) {
                                    job.setExpiry_date(e.getString("expiry_date"));
                                }
                                if (e.getString("attachments") != null) {
                                    job.setAttachments(e.getString("attachments"));
                                }

                                if (e.getString("duration") != null) {
                                    // job.setAttachments(e.getString("attachments"));
                                }


                                JSONArray st = e.getJSONArray("user_info");
                                for (int i1 = 0; i1 < st.length(); i1++) {
                                    JSONObject innerElem = st.getJSONObject(i1);
                                    if (innerElem.getString("user_id") != null) {
                                        job.setAudid(innerElem.getString("user_id"));
                                        Log.e("user_id", innerElem.getString("user_id"));
                                    }
                                    if (innerElem.getString("job_status") != null) {
                                        job.setJobstatus(innerElem.getString("job_status"));
                                        Log.e("job_status", innerElem.getString("job_status"));
                                    }

                                    if (innerElem.getString("first_name") != null) {
                                        job.setFirstname(innerElem.getString("first_name"));
                                        Log.e("firstname", innerElem.getString("first_name"));
                                    }
                                    if (innerElem.getString("last_name") != null) {
                                        job.setLastname(innerElem.getString("last_name"));
                                        Log.e("lastname", innerElem.getString("last_name"));
                                    }
                                    if (innerElem.getString("rating") != null) {
                                        job.setRating(innerElem.getString("rating"));
                                        Log.e("rating", innerElem.getString("rating"));
                                    }

                                    if (innerElem.getString("demo") != null) {
                                        job.setDemo(innerElem.getString("demo"));
                                        Log.e("demo", innerElem.getString("demo"));
                                    }
                                    if (innerElem.getString("status") != null) {
                                        job.setOnlinestatus(innerElem.getString("status"));
                                        Log.e("status", innerElem.getString("status"));
                                    }


                                }


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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

    private void doDeleteWebServiceCall() {

        DeleteJobWebServiceCall deleteJobWebServiceCall = new DeleteJobWebServiceCall();
        deleteJobWebServiceCall.execute();

    }

    private class DeleteJobWebServiceCall extends AsyncTask<String, Void, String> {

        public DeleteJobWebServiceCall() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            navigateBack();

        }

        @Override
        protected String doInBackground(String... strings) {
            String text = "";
            BufferedReader reader = null;
            try {
                if (CheckInternetConnection.isConnected(getActivity())) {
                    String url = WebApiCall.baseURl + "job/" + jobid + "/delete";
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
                        }

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

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        private void navigateBack() {
            lvroot.removeAllViews();
            Fragment newFragment = new Fragment_jobs_producer();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();


            transaction.replace(R.id.rootfragment, newFragment);
            transaction.addToBackStack(null);


            transaction.commit();

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}









