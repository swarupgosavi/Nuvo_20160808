package xyz.theapptest.nuvo.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import android.widget.RatingBar;
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
import xyz.theapptest.nuvo.adapter.ProducerJobRequest;
import xyz.theapptest.nuvo.adapter.Producer_Ongoing;
import xyz.theapptest.nuvo.adapter.Producer_OngoingAdapter;
import xyz.theapptest.nuvo.adapter.Producer_audition_Object;
import xyz.theapptest.nuvo.adapter.Producerjobdetails;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.pojo.UserInformationJobs;
import xyz.theapptest.nuvo.pojo.Userinformation;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.ui.ProducerHomeScreen;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.ProducerJob;

/**
 * Created by trtcpu007 on 8/8/16.
 */

public class Fragment_producer_ongoing extends Fragment implements View.OnClickListener {
    ListView lv;
    LinearLayout lv_decription;
    Button btn, btnleft;
    android.support.v7.widget.Toolbar toolbar1;
    LinearLayout baselv;
    TextView tv_title;
    String jobid, audioname, firstname, lastname, createdon;
    String userid, token;
    ArrayList<Producer_Ongoing> producerjoblistong = null;
    Producer_OngoingAdapter adapter;
    TextView tv_userdecription, tv_userdate, tvdesclabel, tvattachment;
    String description, userdate;
    ArrayList<Userinformation> userlist = null;
    LinearLayout Lvprojobong, artistprofiles;
    TextView name, tvgender, tvjobcategory;
    ImageView isonline, phonecall, sendemail, sendmsg;
    RatingBar tb;
    String email, phone;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ongoing_btn_hired:
                hired.setTextColor(getResources().getColor(R.color.inactivetab));
                Descriptition.setTextColor(getResources().getColor(R.color.activetab));
                webServicecallAudition(jobid);

               /* getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayListview();
                    }
                });*/


                lv.setVisibility(View.VISIBLE);
                lv_decription.setVisibility(View.GONE);
                break;
            case R.id.ongoing_btn_description:
                hired.setTextColor(getResources().getColor(R.color.activetab));
                Descriptition.setTextColor(getResources().getColor(R.color.inactivetab));
                lv.setVisibility(View.GONE);
                lv_decription.setVisibility(View.VISIBLE);

                tv_userdate.setText(userdate);
                tv_userdecription.setText(description);
                break;

        }


    }

    public Typeface fOpenSansCondBold, fOpenSansCondLight, fOpenSansCondLightItalic;

    TextView headingEventName, labelJobDescription, labelProgress, headingForActivity;
    Button hired, Descriptition;
    private Typeface fOpenSansCondRegular;
    private Typeface fOpenSansSemibold, fHelveticaNeue;
    ConstantData con;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_producer_jobs_on_going, null);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        con = ConstantData.getInstance();
        init(rootView);
        typeface();
        return rootView;
    }

    private void typeface() {
        final String sOpenSansCondBold = "font/OpenSans-Bold.ttf";
        final String sOpenSansCondLight = "font/OpenSans-Light.ttf";
        final String sOpenSansCondLightItalic = "font/OpenSans-LightItalic.ttf";
        final String sOpenSansCondRegular = "font/OpenSans-Regular.ttf";
        final String sOpenSansSemibold = "font/OpenSans-Semibold.ttf";
        final String sHelveticaNeue = "font/HelveticaNeue.ttf";

        fOpenSansCondBold = Typeface.createFromAsset(getActivity().getAssets(),
                sOpenSansCondBold);
        fOpenSansCondLight = Typeface.createFromAsset(getActivity().getAssets(),
                sOpenSansCondLight);
        fOpenSansCondLightItalic = Typeface.createFromAsset(getActivity().getAssets(),
                sOpenSansCondLightItalic);
        fOpenSansCondRegular = Typeface.createFromAsset(getActivity().getAssets(),
                sOpenSansCondRegular);
        fOpenSansSemibold = Typeface.createFromAsset(getActivity().getAssets(),
                sOpenSansSemibold);
        fHelveticaNeue = Typeface.createFromAsset(getActivity().getAssets(),
                sHelveticaNeue);
        headingEventName.setTypeface(fOpenSansCondBold);
        labelJobDescription.setTypeface(fOpenSansCondRegular);
        labelProgress.setTypeface(fOpenSansCondLight);
        hired.setTypeface(fOpenSansSemibold);
        Descriptition.setTypeface(fOpenSansSemibold);
        tv_userdecription.setTypeface(fOpenSansSemibold);
        tv_userdate.setTypeface(fOpenSansCondLight);
        tvdesclabel.setTypeface(fOpenSansCondRegular);
        tvattachment.setTypeface(fOpenSansSemibold);
        Descriptition.setOnClickListener(this);
        hired.setOnClickListener(this);
        String usertype = con.getOngoingproducer();
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

            Log.e("jobsid", jobid);
            webServicecallAudition(jobid);
            hired.setTextColor(getResources().getColor(R.color.inactivetab));
            Descriptition.setTextColor(getResources().getColor(R.color.activetab));


        }


    }


    public void displayListview() {
        if (producerjoblistong != null) {

            adapter = new Producer_OngoingAdapter(getActivity().getApplicationContext(), R.layout.producer_ongoing_items, producerjoblistong.get(0).getUserinfo());
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Userinformation user = producerjoblistong.get(0).getUserinfo().get(position);
                    Log.e("userid", user.getUser_id());
                    getProfile(user.getUser_id());
                    Lvprojobong.setVisibility(View.GONE);
                    artistprofiles.setVisibility(View.VISIBLE);
                    tv_title.setText("Artist");
                    btn.setBackgroundResource(R.drawable.bell_icon_toolicon);
                    btn.setVisibility(View.VISIBLE);

                }
            });

        }

    }

    private void getProfile(String useridaudition) {
        if (CheckInternetConnection.isConnected(getActivity())) {
            try {
                //   String userId = constantData.getUserid();
                //   String token = constantData.getTokenid();

                SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
                String usertype = shf.getString("usertype", null);
                userid = shf.getString("userid", null);
                token = shf.getString("token", null);


                String url = WebApiCall.getUserProfileUrl + "" + useridaudition;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userid + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
                con.setRequestProperty("Authorization", "Basic " + encoded);
                // optional default is GET
                con.setRequestMethod("GET");

                //add request header


                int responseCode = con.getResponseCode();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                String serverResult = response.toString();
                Log.e("serverResult", serverResult);

                if (serverResult.startsWith("{")) {
                    JSONObject userObject = new JSONObject(response.toString());
                    if (userObject.getString("id") != null) {
                        String id = userObject.getString("id");
                    }
                    if (userObject.getString("first_name") != null && userObject.getString("last_name") != null) {
                        final String first_name = userObject.getString("first_name");
                        final String last_name = userObject.getString("last_name");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                name.setText(first_name + "  " + last_name);
                            }
                        });
                    }

                    if (userObject.getString("email") != null) {
                        if (userObject.getString("email").equalsIgnoreCase("0")) {

                        } else {
                            email = userObject.getString("email");
                        }


                    }
                    if (userObject.getString("status") != null) {
                        String status = userObject.getString("status");
                    }
                    if (userObject.getString("role") != null) {
                        String role = userObject.getString("role");
                    }
                    if (userObject.getString("online") != null) {


                        String online = userObject.getString("online");
                        if (online.equals("7")) {

                            isonline.setImageDrawable(getResources().getDrawable(R.drawable.green_circle));
                        } else {

                            isonline.setImageDrawable(getResources().getDrawable(R.drawable.red_dot));
                        }
                    }
                    if (userObject.getString("created_on") != null) {
                        String created_on = userObject.getString("created_on");
                    }
                    if (userObject.getString("last_log_out") != null) {
                        String last_log_out = userObject.getString("last_log_out");
                    }
                    if (userObject.getString("user_id") != null) {
                        String user_id = userObject.getString("user_id");
                    }
                    if (userObject.getString("gender") != null) {
                        final String gender = userObject.getString("gender");
                        if (gender.equalsIgnoreCase("0")) {

                        } else {
                            final String gendervalue;
                            if (gender.equals("14")) {
                                gendervalue = "Male";
                            } else {
                                gendervalue = "Female";
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvgender.setText(gendervalue);
                                }
                            });
                        }

                    }
                    if (userObject.getString("age") != null) {
                        String age = userObject.getString("age");
                        if (age.equalsIgnoreCase("0")) {

                        } else {
                            if (age.equals("1")) {
                                age = "Gen X";
                                tvjobcategory.setText(age);
                            } else if (age.equals("2")) {
                                age = "Gen Y";
                                tvjobcategory.setText(age);
                            } else {
                                age = "Millenials";
                                tvjobcategory.setText(age);
                            }
                        }


                    }


                    if (userObject.getString("phone") != null) {
                        phone = userObject.getString("phone");
                        if (phone.equalsIgnoreCase("0")) {

                        } else {

                        }
                    }
                    if (userObject.getString("language") != null) {
                        String language = userObject.getString("language");
                    }
                    if (userObject.getString("description") != null) {
                        String description = userObject.getString("description");
                    }
                    if (userObject.getString("characteristics") != null) {
                        String characteristics = userObject.getString("characteristics");
                    }
                    if (userObject.getString("job_category") != null) {
                        String jobcategory = userObject.getString("job_category");

                    }
                    if (userObject.getString("recording_method") != null) {
                        String recording_method = userObject.getString("recording_method");
                    }
                    if (userObject.getString("demo") != null) {
                        String demo = userObject.getString("demo");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {


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
                        try {

                            JSONObject userObject = new JSONObject(text);
                            String message_code = userObject.getString("message_code");
                            if (message_code != null) {

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        try {
                            //parsing
                            JSONArray json = new JSONArray(text);
                            producerjoblistong = new ArrayList<Producer_Ongoing>();
                            for (int i = 0; i < json.length(); i++) {
                                //  HashMap<String, String> map = new HashMap<String, String>();
                                final JSONObject e = json.getJSONObject(i);

                                Producer_Ongoing job = new Producer_Ongoing();
                                job.setJob_id(e.getString("job_id"));


                                if (e.getString("job_title") != null) {
                                    job.setJob_title(e.getString("job_title"));
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                headingEventName.setText(e.getString("job_title"));
                                            } catch (Exception e) {
                                                e.printStackTrace();
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
                                                userdate = createddate;
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
                                                description = e.getString("description");
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
                                userlist = new ArrayList<Userinformation>();
                                Log.e("User Info Index:", Integer.toString(st.length()));

                                for (int i1 = 0; i1 < st.length(); i1++) {
                                    JSONObject innerElem = st.getJSONObject(i1);
                                    Userinformation user = new Userinformation();
                                    //    job = new Producer_audition_Object();
                                    if (innerElem.getString("user_id") != null) {
                                        user.setUser_id(innerElem.getString("user_id"));
                                        Log.e("user_id", innerElem.getString("user_id"));
                                    }
                                    if (innerElem.getString("job_status") != null) {
                                        user.setJob_status(innerElem.getString("job_status"));
                                        Log.e("job_status", innerElem.getString("job_status"));
                                    }

                                    if (innerElem.getString("first_name") != null) {
                                        user.setFirst_name(innerElem.getString("first_name"));
                                        Log.e("firstname", innerElem.getString("first_name"));
                                    }
                                    if (innerElem.getString("last_name") != null) {
                                        user.setLast_name(innerElem.getString("last_name"));
                                        Log.e("lastname", innerElem.getString("last_name"));
                                    }
                                    if (innerElem.getString("rating") != null) {
                                        user.setRating(innerElem.getString("rating"));
                                        Log.e("rating", innerElem.getString("rating"));
                                    }

                                    if (innerElem.getString("demo") != null) {
                                        user.setDemo(innerElem.getString("demo"));
                                        Log.e("demo", innerElem.getString("demo"));
                                    }
                                    if (innerElem.getString("status") != null) {
                                        user.setStatus(innerElem.getString("status"));
                                        Log.e("status", innerElem.getString("status"));
                                    }

                                    userlist.add(user);
                                }


                                job.setUserinfo(userlist);

                                producerjoblistong.add(job);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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
                        if (producerjoblistong != null) {
                            displayListview();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });


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
        baselv = (LinearLayout) rootView.findViewById(R.id.rootonproducer);
        Lvprojobong = (LinearLayout) rootView.findViewById(R.id.rootproducerjobsongng);
        artistprofiles = (LinearLayout) rootView.findViewById(R.id.agent_add_artist_toplv);
        //  headingForActivity = (TextView)rootView.findViewById(R.id.abn_header_text);
        ProducerHomeScreen act = (ProducerHomeScreen) getActivity();
        // act.getActionBar().setDisplayShowHomeEnabled(true);
        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
        toolbar1.setNavigationIcon(null);
        btn = (Button) toolbar1.findViewById(R.id.btn);
        btnleft = (Button) toolbar1.findViewById(R.id.btnleft);
        tv_title = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        tv_title.setText("Ongoing");
        btn.setVisibility(View.GONE);

        // ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        //  context.getActionBar().setDisplayShowHomeEnabled(false);

        btnleft.setVisibility(View.VISIBLE);
        btnleft.setBackgroundResource(R.drawable.backimg);
        btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();

                if (artistprofiles.getVisibility() == View.VISIBLE) {
                    Log.e("artistprofile", "artistprofile");
                    Lvprojobong.setVisibility(View.VISIBLE);
                    artistprofiles.setVisibility(View.GONE);
                    tv_title.setText("Ongoing");
                    btn.setVisibility(View.GONE);
                } else {
                    // Either gone or invisible
                    Log.e("listview", "listview");
                    baselv.removeAllViews();
                    Fragment newFragment = new Fragment_jobs_producer();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();


                    transaction.replace(R.id.rootonproducer, newFragment);
                    transaction.addToBackStack(null);


                    transaction.commit();
                }


            }
        });
        headingEventName = (TextView) rootView.findViewById(R.id.ongoing_heading_event_name);
        labelJobDescription = (TextView) rootView.findViewById(R.id.ongoing_label_job_status);
        labelProgress = (TextView) rootView.findViewById(R.id.ongoing_label_in_progress);
        hired = (Button) rootView.findViewById(R.id.ongoing_btn_hired);
        Descriptition = (Button) rootView.findViewById(R.id.ongoing_btn_description);
        lv = (ListView) rootView.findViewById(R.id.list_result);
        lv_decription = (LinearLayout) rootView.findViewById(R.id.lv_rooot);

        tv_userdecription = (TextView) rootView.findViewById(R.id.tv_description);
        tv_userdate = (TextView) rootView.findViewById(R.id.tvdate);
        tvdesclabel = (TextView) rootView.findViewById(R.id.descriptiolabel);
        tvattachment = (TextView) rootView.findViewById(R.id.txt_attachment);

        try {
            name = (TextView) rootView.findViewById(R.id.aad_artist_profile_user_name_tv_id);
            tvgender = (TextView) rootView.findViewById(R.id.aad_artist_profile_gender_tv_id);
            tvjobcategory = (TextView) rootView.findViewById(R.id.aad_artist_profile_age_tv_id);
            isonline = (ImageView) rootView.findViewById(R.id.aad_artist_profile_user_status_imv_id);
            tb = (RatingBar) rootView.findViewById(R.id.aad_artist_profile_rating_bar_id);
            phonecall = (ImageView) rootView.findViewById(R.id.aad_artist_profile_call_imv_id);
            sendemail = (ImageView) rootView.findViewById(R.id.aad_artist_profile_mail_imv_id);
            sendmsg = (ImageView) rootView.findViewById(R.id.aad_artist_profile_msg_imv_id);
            sendmsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.SEND_SMS);
                        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                            sendIntent.setType("vnd.android-dir/mms-sms");
                            startActivity(sendIntent);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            phonecall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (phone != null) {

                        try {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            intent.setData(Uri.parse("tel:" + phone));
                            getActivity().startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    /*    int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CALL_PHONE);
                        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent(Intent.ACTION_CALL);

                            intent.setData(Uri.parse("tel:" + phone));
                            getActivity().startActivity(intent);
                        }
*/

                    }
                }
            });
            sendemail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (email != null) {
                        Intent emailsend = new Intent(Intent.ACTION_SEND);
                        emailsend.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                        emailsend.putExtra(Intent.EXTRA_SUBJECT, "producer wants to talk.");


                        //need this to prompts email client only
                        emailsend.setType("message/rfc822");

                        startActivity(Intent.createChooser(emailsend, "Choose an Email client :"));
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            ;
        }


    }
}
