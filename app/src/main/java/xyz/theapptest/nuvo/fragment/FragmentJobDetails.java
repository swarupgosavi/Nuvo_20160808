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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import xyz.theapptest.nuvo.pojo.NewAuditionsPojo;
import xyz.theapptest.nuvo.ui.ArtistHomescreen;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.OngoingArtistJob;

/**
 * Created by ADMIN on 8/8/2016.
 */
public class FragmentJobDetails extends Fragment {

    Context mContext;
    ConstantData constantData;
    android.support.v7.widget.Toolbar toolbar1;
    Button btnleft, btn;
    LinearLayout mainLL;
    TextView titleTv, dateTv, descriptionTv, completedTv;
    OngoingArtistJob ongoingArtistJobPojo;
    String jobType = "";
    String userid, token;
    ImageView progressBarImv;
    ProgressDialog dialog;
    TextView tv_progress;
    TextView tv_progrssstatus;
    String strresponse;
    String jobstatus;
    LinearLayout lv_saved, lv_on_past;
    TextView tv_companyname, tv_firstname, tv_lastname;
    String companyname, firstnamestr, lastname;
    TextView tv_toolbartitle, tv_right;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_job_details_layout, null);

        init(rootView);
        constantData = ConstantData.getInstance();
        getValuesFromIntent();
        setValues();

        return rootView;
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

    public void webServicecallApply(final String jobid) {
        SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
        String usertype = shf.getString("usertype", null);
        userid = shf.getString("userid", null);
        token = shf.getString("token", null);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    webServiceCallApplyJob(userid, token, jobid);

                } catch (Exception e) {

                }
            }
        });
        thread.start();

    }

    public void webServiceCallApplyJob(String userid, String token, String jobid) {
        if (CheckInternetConnection.isConnected(getActivity())) {

            String text = "";
            BufferedReader reader = null;


            try {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog = new ProgressDialog(mContext,ProgressDialog.THEME_HOLO_LIGHT);
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
                Log.e("apply response response", text);
                if (text != null) {
                    if(dialog!=null)
                    dialog.dismiss();
                    JSONObject userObject = new JSONObject(text);
                    String message_code = userObject.getString("message_code");
                    if (message_code.equalsIgnoreCase("167")) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                                customizeDialog.setTitle("nuvo");
                                customizeDialog.setCancelable(false);
                                customizeDialog.setMessage("Job applied for audition");
                                customizeDialog.show();


                            }
                        });


                    }

                }


            } catch (Exception e) {
                if(dialog!=null)
                    dialog.dismiss();
                e.printStackTrace();
            }


        } else {
            if(dialog!=null)
                dialog.dismiss();
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

                String url = WebApiCall.baseURl + "job/" + jobid + "/";

                Log.e("url", url);

                Log.e("url", url + "-" + jobstatus);

                if (jobstatus.equalsIgnoreCase("12")) {
                    url = url + "completed";
                } else
                    url = url + "notcompleted";


                Log.e("url", url);

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

                                    if (jobstatus.equalsIgnoreCase("13")) {
                                        tv_progress.setText("In Progress");
                                        completedTv.setText("Complete");

                                        //  completedTv.setBackgroundDrawable(mContext.getResources().getDrawable().drawable.rounded_corner_red_background);
                                        //  completedTv.setVisibility(View.INVISIBLE);
                                        completedTv.setBackgroundResource(R.drawable.rounded_corner_white_background);

                                        progressBarImv.setImageResource(R.drawable.progress_bar_half);
                                        //progressBarImv.setBackgroundResource(R.drawable.progress_bar_half);
                                        //   Toast.makeText(getActivity(), "Successfully applied for Audition", Toast.LENGTH_LONG).show();
                                        //Dialogs.showAlertcommon(mContext, "Audition", "Successfully applied for Audition");
                                        jobstatus = "12";
                                        CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                                        customizeDialog.setTitle("Audition");
                                        customizeDialog.setCancelable(false);
                                        customizeDialog.setMessage("Audition is  marked as incomplete.");
                                        customizeDialog.show();
                                    } else {
                                        tv_progress.setText("Completed");
                                        completedTv.setText("Completed");
                                        completedTv.setBackgroundResource(R.drawable.rounded_corner_complete_background);


                                        progressBarImv.setImageResource(R.drawable.progress_bar_full);
                                        jobstatus = "13";
                                        //   Toast.makeText(getActivity(), "Successfully applied for Audition", Toast.LENGTH_LONG).show();
                                        //Dialogs.showAlertcommon(mContext, "Audition", "Successfully applied for Audition");
                                        CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                                        customizeDialog.setTitle("Audition");
                                        customizeDialog.setCancelable(false);
                                        customizeDialog.setMessage("Audition marked as complete.");
                                        customizeDialog.show();
                                    }
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


    private void init(View rootView) {
        tv_companyname = (TextView) rootView.findViewById(R.id.tv_companyname);
        tv_firstname = (TextView) rootView.findViewById(R.id.tv_firstname);
        tv_lastname = (TextView) rootView.findViewById(R.id.tv_lastname);
        lv_saved = (LinearLayout) rootView.findViewById(R.id.lv_saved);
        lv_on_past = (LinearLayout) rootView.findViewById(R.id.lv_on_past);
        tv_progress = (TextView) rootView.findViewById(R.id.fragment_artist_job_details_status_progress_tv_id);
        titleTv = (TextView) rootView.findViewById(R.id.fragment_artist_job_details_title_tv_id);

        descriptionTv = (TextView) rootView.findViewById(R.id.fragment_artist_job_details_description_tv_id);
        dateTv = (TextView) rootView.findViewById(R.id.fragment_artist_job_details_date_tv_id);
        mainLL = (LinearLayout) rootView.findViewById(R.id.fragment_artist_job_details_main_ll_id);
        progressBarImv = (ImageView) rootView.findViewById(R.id.fragment_artist_job_details_progressbar_imv_id);
        completedTv = (TextView) rootView.findViewById(R.id.fragment_artist_job_details_completed_tv_id);
        completedTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                webServicecallAudition(ongoingArtistJobPojo.getJobid());


            }
        });
        ArtistHomescreen act = (ArtistHomescreen) getActivity();
        // act.getActionBar().setDisplayShowHomeEnabled(true);
        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
        //   toolbar1.setNavigationIcon(null);
        tv_toolbartitle = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        tv_right = (TextView) toolbar1.findViewById(R.id.rightlabel);
        btn = (Button) toolbar1.findViewById(R.id.btn);
        btnleft = (Button) toolbar1.findViewById(R.id.btnleft);
        btn.setVisibility(View.GONE);
        btnleft.setVisibility(View.VISIBLE);
        btnleft.setBackgroundResource(R.drawable.backimg);
        btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();


                mainLL.removeAllViews();
                Fragment newFragment = new Fragment_jobs_UI();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();


                transaction.replace(R.id.fragment_artist_job_details_main_ll_id, newFragment);
                transaction.addToBackStack(null);


                transaction.commit();


            }
        });


    }

    private void getValuesFromIntent() {
        try {
            String gsonString = constantData.getOnGoingJobsJsonString();
            Gson gson = new Gson();
            ongoingArtistJobPojo = gson.fromJson(gsonString, OngoingArtistJob.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setValues() {
        String title = ongoingArtistJobPojo.getTitle();

        String description = ongoingArtistJobPojo.getDescription();
        String expiryDate = ongoingArtistJobPojo.getCreatedon();
        companyname = ongoingArtistJobPojo.getCompanyname();
        firstnamestr = ongoingArtistJobPojo.getFirstname();
        lastname = ongoingArtistJobPojo.getLastname();
        jobstatus = ongoingArtistJobPojo.getJobstatus();


        //jobId = ongoingArtistJobPojo.getJobid();
        String jobType = constantData.getJobType();
        if (jobType.equals("Past")) {
            progressBarImv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.progress_bar_full));
            tv_progress.setText("Completed");
            completedTv.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rounded_corner_white_border_red_background));
            jobstatus = "13";
        } else if (jobType.equalsIgnoreCase("Saved")) {
            progressBarImv.setVisibility(View.INVISIBLE);
            completedTv.setVisibility(View.INVISIBLE);
            lv_on_past.setVisibility(View.GONE);
            lv_saved.setVisibility(View.VISIBLE);
            tv_firstname.setText(firstnamestr);
            tv_lastname.setText(lastname);
            tv_companyname.setText(companyname);
            Typeface dateType = Typeface.createFromAsset(mContext.getAssets(), "font/OpenSans-Light.ttf");
            tv_companyname.setTypeface(dateType);
            tv_lastname.setTypeface(dateType);
            tv_firstname.setTypeface(dateType);
            tv_toolbartitle.setText("Saved");
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText("Audition");
            tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webServicecallApply(ongoingArtistJobPojo.getJobid());

                }
            });
        } else {
            progressBarImv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.progress_bar_half));
            jobstatus = "12";

        }
        if (expiryDate == null) {

        } else {
            if (expiryDate.contains(" ")) {
                String[] dateArray = expiryDate.split(" ");
                expiryDate = dateArray[0];
            }

        }

        titleTv.setText(title);
        if (description == null) {
            descriptionTv.setText("NA");
        } else {
            descriptionTv.setText(description);

        }
        dateTv.setText(expiryDate);

        Typeface type = Typeface.createFromAsset(mContext.getAssets(), "font/OpenSans-Bold.ttf");
        titleTv.setTypeface(type);


        Typeface descriptionType = Typeface.createFromAsset(mContext.getAssets(), "font/OpenSans-Regular.ttf");
        descriptionTv.setTypeface(descriptionType);

        Typeface dateType = Typeface.createFromAsset(mContext.getAssets(), "font/OpenSans-Light.ttf");
        descriptionTv.setTypeface(dateType);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
