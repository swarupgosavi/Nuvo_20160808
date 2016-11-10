package xyz.theapptest.nuvo.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.agent.ArtistsListFragment;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.pojo.AgentArtistListItemPojo;
import xyz.theapptest.nuvo.ui.ProducerHomeScreen;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.FindJobs;
import xyz.theapptest.nuvo.utils.Fonts;

/**
 * Created by ADMIN on 8/19/2016.
 */
public class Fragment_Artist_Details extends Fragment implements View.OnClickListener {

    ImageView bckBtnImg, centerPlayImg, artistAvilabitlityStatusImg, callArtist, msgArtist, emailArtist, agentLogoImg, callAgentImg, msgAgentImg, emailAgentImg;
    TextView nuvoHdrTv, artistUserName, artistGender, artistProfileAge, yourLogoTxt;
    Button notificationBtn;
    RatingBar artistRatings;
    Fonts fonts;
    LinearLayout mainLL;
    Context mContext;
    ConstantData constantData;
    // AgentArtistListItemPojo agentArtistListItemPojo;
    FindJobs findJobsPojo;
    String phone = "";
    String email = "";
    String rating, imgurl;
    ImageView img_icon;
    String id = "";
    String firstName, lastName, age, gender, online;
    android.support.v7.widget.Toolbar toolbar1;
    String fragmentType = "";
    String gsonString = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            fragmentType = bundle.getString("fragmentType");
            if (fragmentType.equalsIgnoreCase("Fragment_Saved")) {
                gsonString = bundle.getString("GsonString");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_artist_details_for_agent, null);
        constantData = ConstantData.getInstance();

        initViews(rootView);
        //setUpListner();
        //doGetArtistDetailsWebServiceCall();
        try {

            if (fragmentType.equalsIgnoreCase("Fragment_Saved")) {
                setValuesThroughGson();
            } else {
                getValuesFromIntent();
                doGetArtistDetailsWebServiceCall();

            }
            //setValues();
            setUpListner();
        } catch (Exception e) {
            e.printStackTrace();

        }

        return rootView;
    }

    private void initViews(View v) {

        fonts = new Fonts(getActivity());
        img_icon = (ImageView) v.findViewById(R.id.aad_artist_profile_imv_id_agency_logo);
        nuvoHdrTv = (TextView) v.findViewById(R.id.aad_toolbar_title);
        nuvoHdrTv.setTypeface(fonts.halveticaNeue);
        artistUserName = (TextView) v.findViewById(R.id.aad_artist_profile_user_name_tv_id);
        artistUserName.setTypeface(fonts.openSansSemiBold);
        artistGender = (TextView) v.findViewById(R.id.aad_artist_profile_gender_tv_id);
        artistGender.setTypeface(fonts.openSansRegular);
        artistProfileAge = (TextView) v.findViewById(R.id.aad_artist_profile_age_tv_id);
        artistProfileAge.setTypeface(fonts.openSansRegular);
        yourLogoTxt = (TextView) v.findViewById(R.id.aad_artist_your_logo);
        yourLogoTxt.setTypeface(fonts.openSansRegular);

        bckBtnImg = (ImageView) v.findViewById(R.id.aad_back);
        bckBtnImg.setOnClickListener(this);
        centerPlayImg = (ImageView) v.findViewById(R.id.aad_center_play_img);
        artistAvilabitlityStatusImg = (ImageView) v.findViewById(R.id.aad_artist_profile_user_status_imv_id);
        callArtist = (ImageView) v.findViewById(R.id.aad_artist_profile_call_imv_id);
        msgArtist = (ImageView) v.findViewById(R.id.aad_artist_profile_msg_imv_id);
        emailArtist = (ImageView) v.findViewById(R.id.aad_artist_profile_mail_imv_id);
        agentLogoImg = (ImageView) v.findViewById(R.id.aad_artist_profile_imv_id_agency_logo);
        callAgentImg = (ImageView) v.findViewById(R.id.aad_agent_call_imv_id_agent);
        msgAgentImg = (ImageView) v.findViewById(R.id.aad_agent_msg_imv_id_agent);
        emailAgentImg = (ImageView) v.findViewById(R.id.aad_agent_mail_imv_id_agent);

        notificationBtn = (Button) v.findViewById(R.id.ada_bell_btn);

        artistRatings = (RatingBar) v.findViewById(R.id.aad_artist_profile_rating_bar_id);

        mainLL = (LinearLayout) v.findViewById(R.id.activity_artist_details_for_agent_main_ll_id);

        ProducerHomeScreen act = (ProducerHomeScreen) getActivity();
        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
    }

    private void getValuesFromIntent() {
        findJobsPojo = constantData.getFindJobsPojo();
        id = findJobsPojo.getUserid();

    }


    private void setValues() {
        try {


            String name = firstName + " " + lastName;
            artistUserName.setText(name);
            if (gender != null) {
                if (gender.equals("14")) {
                    artistGender.setText("Male");
                } else {
                    artistGender.setText("Female");
                }
            }
            if (online != null) {
                if (online.equals("7")) {
                    artistAvilabitlityStatusImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_circle));
                } else {
                    artistAvilabitlityStatusImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_dot));
                }
            }
            if (age != null) {
                if (age.equals("1")) {
                    age = "Gen X";
                } else if (age.equals("2")) {
                    age = "Gen Y";
                } else {
                    age = "Millenials";
                }
                artistProfileAge.setText(age);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setValuesThroughGson() {
        try {
            Gson gson = new Gson();
            AgentArtistListItemPojo pojo = gson.fromJson(gsonString, AgentArtistListItemPojo.class);
            String firstName = pojo.getFirst_name();
            String lastName = pojo.getLast_name();
            String gender = pojo.getGender();
            phone = pojo.getPhone();
            email = pojo.getEmail();
            online = pojo.getOnline();
            String[] ageArray = pojo.getAge();
            if (ageArray == null) {

            } else {
                if (ageArray.length == 0) {

                } else {
                    age = ageArray[0];
                }
            }

            String name = firstName + " " + lastName;
            if (name != null) {
                artistUserName.setText(name);
            }
            if (gender != null) {
                if (gender.equals("14")) {
                    artistGender.setText("Male");
                } else {
                    artistGender.setText("Female");
                }
            }
            if (online != null) {
                if (online.equals("7")) {
                    artistAvilabitlityStatusImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_circle));
                } else {
                    artistAvilabitlityStatusImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_dot));
                }

            }
            if (age != null) {
                if (age.equals("1")) {
                    age = "Gen X";
                } else if (age.equals("2")) {
                    age = "Gen Y";
                } else {
                    age = "Millenials";
                }

                artistProfileAge.setText(age);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpListner() {
        callArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);

                    intent.setData(Uri.parse("tel:" + phone));
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        msgArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int permissionCheck = ContextCompat.checkSelfPermission(mContext,
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

        emailArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "" + email);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Nuvo");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");


                emailIntent.setType("message/rfc822");

                try {
                    startActivity(Intent.createChooser(emailIntent,
                            "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(),
                            "No email clients installed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void doGetArtistDetailsWebServiceCall() {
        GetArtistDetails getArtistDetails = new GetArtistDetails();
        getArtistDetails.execute();
    }

    @Override
    public void onClick(View view) {
        if (view == bckBtnImg) {
            mainLL.removeAllViews();
            Fragment newFragment = null;
            if (fragmentType.equals("Fragment_Criteria")) {
                newFragment = new Fragment_Criteria();

            } else if (fragmentType.equals("Fragment_Saved")) {
                newFragment = new Fragment_saved_producer();
            }
            FragmentTransaction transaction = getFragmentManager().beginTransaction();


            transaction.replace(R.id.activity_artist_details_for_agent_main_ll_id, newFragment);
            transaction.addToBackStack(null);


            transaction.commit();

        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    private class GetArtistDetails extends AsyncTask<String, Void, String> {
        public GetArtistDetails() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setValues();
        }

        @Override
        protected String doInBackground(String... strings) {
            getProfile(id);
            return null;
        }
    }

    private void getProfile(String useridaudition) {
        if (CheckInternetConnection.isConnected(getActivity())) {
            try {
                String userId = constantData.getUserid();
                String token = constantData.getTokenid();

                SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
                String usertype = shf.getString("usertype", null);


                String url = WebApiCall.getUserProfileUrl + "" + useridaudition;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userId + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
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
                        firstName = userObject.getString("first_name");
                        lastName = userObject.getString("last_name");

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
                        online = userObject.getString("online");

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
                        gender = userObject.getString("gender");


                    }
                    if (userObject.getString("age") != null) {
                        age = userObject.getString("age");
                    }


                    if (userObject.getString("phone") != null) {
                        phone = userObject.getString("phone");
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

    @Override
    public void onResume() {
        super.onResume();
        toolbar1.setVisibility(View.GONE);
    }
}
