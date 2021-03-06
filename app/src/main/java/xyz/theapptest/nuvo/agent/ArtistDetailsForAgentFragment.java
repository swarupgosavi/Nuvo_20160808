package xyz.theapptest.nuvo.agent;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.text.Html;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.pojo.AgentArtistListItemPojo;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Fonts;

/**
 * Created by torinit01 on 10/8/16.
 */
public class ArtistDetailsForAgentFragment extends Fragment implements View.OnClickListener {


    ImageView bckBtnImg, centerPlayImg, artistAvilabitlityStatusImg, callArtist, msgArtist, emailArtist, agentLogoImg, callAgentImg, msgAgentImg, emailAgentImg;
    TextView nuvoHdrTv, artistUserName, artistGender, artistProfileAge, yourLogoTxt;
    Button notificationBtn;
    RatingBar artistRatings;
    Fonts fonts;
    LinearLayout mainLL;
    Context mContext;
    ConstantData constantData;
    AgentArtistListItemPojo agentArtistListItemPojo;
    String phone = "";
    String email = "";
    String rating, imgurl;
    ImageView img_icon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_artist_details_for_agent, null);
        constantData = ConstantData.getInstance();
        initViews(rootView);
        //setUpListner();
        //doGetArtistDetailsWebServiceCall();
        try {
            getValuesFromIntent();
            setValues();
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


    }

    private void getValuesFromIntent() {
        agentArtistListItemPojo = constantData.getAgentArtistListItemPojo();
    }


    private void setValues() {

        rating = agentArtistListItemPojo.getRating();
        if (rating != null) {
            Log.e("rating", rating);
            artistRatings.setRating(Float.parseFloat(rating));
        }


        String firstName = agentArtistListItemPojo.getFirst_name();
        String lastName = agentArtistListItemPojo.getLast_name();
        String[] ageArray = agentArtistListItemPojo.getAge();
        String age = "";
        if (ageArray == null) {

        } else {
            if (ageArray.length == 0) {

            } else {
                age = ageArray[0];
            }
        }
        phone = agentArtistListItemPojo.getPhone();
        email = agentArtistListItemPojo.getEmail();
        Log.e("email",email);
        String gender = agentArtistListItemPojo.getGender();
        String online = agentArtistListItemPojo.getOnline();

        String name = firstName + " " + lastName;
        artistUserName.setText(name);
        if (gender.equals("14")) {
            artistGender.setText("Male");
        } else {
            artistGender.setText("Female");
        }
        if (online.equals("7")) {
            artistAvilabitlityStatusImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_circle));
        } else {
            artistAvilabitlityStatusImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_dot));
        }
        if (age.equals("1")) {
            age = "Gen X";
        } else if (age.equals("2")) {
            age = "Gen Y";
        } else {
            age = "Millenials";
        }

        artistProfileAge.setText(age);

    }

    private void setUpListner() {
        callArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* int permissionCheck = ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.CALL_PHONE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);

                    intent.setData(Uri.parse("tel:" + phone));
                    mContext.startActivity(intent);
                }
*/

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

                   /* Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("Hi"));
                    startActivity(Intent.createChooser(sharingIntent,"Nuvo"));
*/
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        emailArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ email});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "an agent wants to talk.");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");


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

    /*  private void doGetArtistDetailsWebServiceCall(){
          GetArtistDetails getArtistDetails = new GetArtistDetails();
          getArtistDetails.execute();
      }*/
    @Override
    public void onClick(View view) {
        if (view == bckBtnImg) {
            mainLL.removeAllViews();
            Fragment newFragment = new ArtistsListFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();


            transaction.replace(R.id.activity_artist_details_for_agent_main_ll_id, newFragment);
            transaction.addToBackStack(null);


            transaction.commit();

        }
    }

    /*private class GetArtistDetails extends AsyncTask<String, Void, String>{

        ProgressDialog pd;
        public GetArtistDetails(){

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            if (CheckInternetConnection.isConnected(getActivity())) {
                try {
                    //   String userId = constantData.getUserid();
                    //   String token = constantData.getTokenid();

                    SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
                    String usertype = shf.getString("usertype", null);
                    String userid = shf.getString("userid", null);
                    String token = shf.getString("token", null);


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
                            String email = userObject.getString("email");
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
                        if (userObject.getString("phone") != null) {
                            String phone = userObject.getString("phone");
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
                            if (jobcategory.equals("1")) {
                                jobcategory = "Gen X";
                                tvjobcategory.setText(jobcategory);
                            } else if (jobcategory.equals("2")) {
                                jobcategory = "Gen Y";
                                tvjobcategory.setText(jobcategory);
                            } else {
                                jobcategory = "Millenials";
                                tvjobcategory.setText(jobcategory);
                            }

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

            return null;
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}