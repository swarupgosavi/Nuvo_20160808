package xyz.theapptest.nuvo.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import xyz.theapptest.nuvo.pojo.AuditionsPojo;
import xyz.theapptest.nuvo.pojo.NewAuditionsPojo;
import xyz.theapptest.nuvo.ui.Activity_Notify;
import xyz.theapptest.nuvo.ui.ArtistHomescreen;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.ui.ProducerHomeScreen;
import xyz.theapptest.nuvo.utils.ConstantData;

/**
 * Created by trtcpu007 on 2/8/16.
 */

public class FragmentAudionDetails extends Fragment {
    Context mContext;
    ConstantData constantData;
    NewAuditionsPojo auditionsPojo;
    TextView titleTv, descriptionTv, dateTv;
    LinearLayout lyAudiotiondetails;
    android.support.v7.widget.Toolbar toolbar1;
    Button btnleft, btn,btnnotify;
    String jobId = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_audition_details_layout, null);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        init(rootView);
        constantData = ConstantData.getInstance();
        getValuesFromIntent();
        String userId = constantData.getUserid();
        String token = constantData.getTokenid();
        webServiceCallAuditionData(userId, token, jobId);
        setValues();

        return rootView;
    }


    private void init(View rootView) {
        btnnotify=(Button)rootView.findViewById(R.id.bt_notify);
        btnnotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), Activity_Notify.class);
                i.putExtra("jobid",jobId);
                startActivity(i);



            }
        });
        titleTv = (TextView) rootView.findViewById(R.id.audition_details_title_tv_id);
        descriptionTv = (TextView) rootView.findViewById(R.id.audition_details_layout_description_tv_id);
        dateTv = (TextView) rootView.findViewById(R.id.audition_details_date_tv_id);
        lyAudiotiondetails = (LinearLayout) rootView.findViewById(R.id.fragmentaudiondetails);
        ArtistHomescreen act = (ArtistHomescreen) getActivity();
        // act.getActionBar().setDisplayShowHomeEnabled(true);
        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
     //   toolbar1.setNavigationIcon(null);
        btn = (Button) toolbar1.findViewById(R.id.btn);
        btnleft = (Button) toolbar1.findViewById(R.id.btnleft);
        btn.setVisibility(View.GONE);
        btnleft.setVisibility(View.VISIBLE);
        btnleft.setBackgroundResource(R.drawable.backimg);
        btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();


                lyAudiotiondetails.removeAllViews();
                Fragment newFragment = new Fragment_Auditions();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();


                transaction.replace(R.id.fragmentaudiondetails, newFragment);
                transaction.addToBackStack(null);


                transaction.commit();


            }
        });



    }

    private void getValuesFromIntent() {
        try {
            String gsonString = constantData.getAuditionresponse();
            Gson gson = new Gson();
            auditionsPojo = gson.fromJson(gsonString, NewAuditionsPojo.class);
            jobId = auditionsPojo.getJob_id();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setValues() {
        String title = auditionsPojo.getJob_title();
        String description = auditionsPojo.getDescription();
        String expiryDate = auditionsPojo.getCreated_on();
        String[] dateArray = expiryDate.split(" ");
        expiryDate = dateArray[0];

        titleTv.setText(title);
        descriptionTv.setText(description);
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

    public void webServiceCallAuditionData(String userid, String token, String jobid) {
        if (CheckInternetConnection.isConnected(getActivity())) {

            String text = "";
            BufferedReader reader = null;


            try {

               /* getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog = new ProgressDialog(mContext);
                        dialog.setMessage("Please Wait!!");
                        dialog.setCancelable(false);
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.show();

                    }
                });*/
                //  String url = WebApiCall.baseURl + "auditions/" + jobid + "/apply";
                String url = WebApiCall.baseURl + "job/" + jobid + "/viewed";

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
                        /*if (message_code.equalsIgnoreCase("167")) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    dialog.dismiss();
                                    tv_progress.setText("Completed");
                                    //  completedTv.setBackgroundDrawable(mContext.getResources().getDrawable().drawable.rounded_corner_red_background);
                                    completedTv.setVisibility(View.INVISIBLE);
                                    progressBarImv.setBackgroundResource(R.drawable.progress_bar_full);
                                    //   Toast.makeText(getActivity(), "Successfully applied for Audition", Toast.LENGTH_LONG).show();
                                    //Dialogs.showAlertcommon(mContext, "Audition", "Successfully applied for Audition");
                                    CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                                    customizeDialog.setTitle("Audition");
                                    customizeDialog.setCancelable(false);
                                    customizeDialog.setMessage("Audition Successfully Completed.");
                                    customizeDialog.show();
                                }
                            });


                        }*/
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

}
