package xyz.theapptest.nuvo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.agent.AgentContainerActivity;
import xyz.theapptest.nuvo.agent.AgentsAddArtistActivity;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.pojo.CheckArtistPojo;
import xyz.theapptest.nuvo.pojo.LoginPojo;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Constants;
import xyz.theapptest.nuvo.utils.Dialogs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by trtcpu007 on 7/7/16.
 */

public class SendingEmailActivity extends Activity {
    TextView tv_thankyou, tv_checkmail, tv_title;
    ImageView imgProgress;
    String usertype, firstname, lastname, email, password,action;
    ConstantData constData;
    ArrayList<HashMap<String, String>> agelist;
    ArrayList<HashMap<String, String>> charlist;
    ArrayList<HashMap<String, String>> joblist;
    ArrayList<HashMap<String, String>> langaugelist;
    ArrayList<HashMap<String, String>> recordingmth;
String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_send);
        typeofuser();
        init();
        typeInterface();
        onProgressLogic();

    }

    private void typeofuser() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            usertype = extras.getString("typeofuser");
            if(extras.getString("firstname")!=null) {
                firstname = extras.getString("firstname");
            }
            if(extras.getString("lastname")!=null) {
                lastname = extras.getString("lastname");
            }
            if(extras.getString("email")!=null) {
                email = extras.getString("email");
                Log.e("email-email", email);
            }
            if(extras.getString("email")!=null) {
                password = extras.getString("password");
            }
            if(extras.getString("action")!=null) {
                action = extras.getString("action");
            }





          /*  agelist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("age");
            charlist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("characteristics");
            joblist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("job_category");
            langaugelist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("langauge");
            recordingmth = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("recordingmethod");*/

        }


    }

    @Override
    public void onBackPressed() {
    }

    private void onProgressLogic() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                imgProgress.setImageResource(R.drawable.sendemail);
                tv_checkmail.setText("Email sent to email address.");
                android.view.ViewGroup.LayoutParams layoutParams = imgProgress.getLayoutParams();
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int px = Math.round(100 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                layoutParams.width = px;
                layoutParams.height = px;
                imgProgress.setLayoutParams(layoutParams);
                if (usertype != null && !usertype.isEmpty() && action !=null && !action.isEmpty()) {
                    if (usertype.equalsIgnoreCase("Artist")) {
                        Intent artistdetail = new Intent(SendingEmailActivity.this, CreateProfileSliderActivity.class);
                        constData.setFirstname(firstname);
                        constData.setLastname(lastname);
                        constData.setEmail(email);
                        constData.setPassword(password);

                        startActivity(artistdetail);
                        finish();
                    } else {
                        if (usertype.equalsIgnoreCase("Producer")) {
                            Intent home = new Intent(SendingEmailActivity.this, ProducerHomeScreen.class);
                            constData.setFirstname(firstname);
                            constData.setLastname(lastname);
                            constData.setEmail(email);
                            constData.setPassword(password);
                            home.putExtra("usertype", "Producer");
                            startActivity(home);
                            finish();

                            // Intent home = new Intent(SendingEmailActivity.this, HomeActivity.class);
                            // startActivity(home);


                        } else {
                            if (usertype.equalsIgnoreCase("Agent")) {
                                doAgentLoginWebServiceCall();
//                                Intent home = new Intent(SendingEmailActivity.this, AgentsAddArtistActivity.class);
//                                startActivity(home);
                            }
                        }
                    }

                } else {

                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Screentype", usertype);
                    startActivity(intent);
                }

            }
        }, 2000);


    }

    private void typeInterface() {
        Typeface facetxttitle = Typeface.createFromAsset(getAssets(),
                "font/Vonique 64.ttf");
        tv_title.setTypeface(facetxttitle);

        Typeface facebtn = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Semibold.ttf");
        tv_thankyou.setTypeface(facebtn);

        Typeface facetxtsigintextbox = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Regular.ttf");
        tv_checkmail.setTypeface(facetxtsigintextbox);

    }

    private void init() {
        tv_title = (TextView) findViewById(R.id.toolbar_title);
        tv_thankyou = (TextView) findViewById(R.id.tv_thankyou);
        tv_checkmail = (TextView) findViewById(R.id.tv_checkmail);
        imgProgress = (ImageView) findViewById(R.id.imgprogrss);
        //    img_back = (ImageView) findViewById(R.id.back);
        //    img_back.setOnClickListener(this);
        constData = ConstantData.getInstance();

    }

    private void doAgentLoginWebServiceCall() {
        String email = constData.getEmail();
        String password = constData.getPassword();
        DoLoginWebserviceCall doLoginWebserviceCall = new DoLoginWebserviceCall();
        doLoginWebserviceCall.execute();


    }

    private class DoLoginWebserviceCall extends AsyncTask<String, Void, String> {

        public DoLoginWebserviceCall() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent home = new Intent(SendingEmailActivity.this, AgentsAddArtistActivity.class);
            startActivity(home);
            finish();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = WebApiCall.baseURl + "user/login";
            String text = "";
            BufferedReader reader = null;
            try {
                String emailId = constData.getEmail();
                String password = constData.getPassword();


                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // String encoded = Base64.encodeToString((userId + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                // con.setRequestProperty("Authorization", "Basic " + encoded);


                String data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(emailId, "UTF-8");
                data += "&" + URLEncoder.encode(WebApiCall.KEY_PASSWORD, "UTF-8") + "="
                        + URLEncoder.encode(password, "UTF-8");

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
                Gson gson = new Gson();
                LoginPojo loginPojo = gson.fromJson(text, LoginPojo.class);
                String user_id = loginPojo.getUser_id();
                String token = loginPojo.getToken();
                constData.setUserid(user_id);
                constData.setTokenid(token);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}