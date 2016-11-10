package xyz.theapptest.nuvo.agent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.pojo.CheckArtistPojo;
import xyz.theapptest.nuvo.pojo.ServerResponsePojo;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Util;

public class AgentsAddArtistActivity extends AppCompatActivity implements View.OnClickListener {

    Button nextBtn;
    ImageView backBtn;
    TextView addArtistHdr,tv_titletoolbar;
    EditText addArtistEdOne, addArtistEdTwo, addArtistEdThree;
    String emailOne, emailTwo, emailThree;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Context mContext = AgentsAddArtistActivity.this;
    ProgressDialog pd;
    ConstantData constantData;
    ArrayList<String> emailArrayList;

    CustomizeDialog customizeDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agents_add_artist);
        constantData = ConstantData.getInstance();
        init();
    }

    private void init() {
        Typeface facetxttitle = Typeface.createFromAsset(getAssets(),
                "font/Vonique 64.ttf");
        tv_titletoolbar=(TextView)findViewById(R.id.aaaa_toolbar_title);
        tv_titletoolbar.setTypeface(facetxttitle);

        backBtn = (ImageView) findViewById(R.id.aaaa_back);
        backBtn.setOnClickListener(this);
        nextBtn = (Button) findViewById(R.id.agent_add_artist_next);
        nextBtn.setOnClickListener(this);

        addArtistHdr = (TextView) findViewById(R.id.aaaa_tv_add_artist);
        addArtistEdOne = (EditText) findViewById(R.id.agent_add_artist_add_artist_one);
        addArtistEdTwo = (EditText) findViewById(R.id.agent_add_artist_add_artist_two);
        addArtistEdThree = (EditText) findViewById(R.id.agent_add_artist_add_artist_three);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmail();


            }
        });

    }

    private void validateEmail() {
        emailOne = addArtistEdOne.getText().toString();
        emailTwo = addArtistEdTwo.getText().toString();
        emailThree = addArtistEdThree.getText().toString();

        if (emailOne == null) {
            emailOne = "";
        } else {

        }
        if (emailTwo == null) {
            emailTwo = "";
        }else
        {

        }
        if (emailThree == null) {
            emailThree = "";
        }else
        {

        }
        emailArrayList = new ArrayList<>();
        emailArrayList.add(0, emailOne);
        emailArrayList.add(1, emailTwo);
        emailArrayList.add(2, emailThree);

        for (int i = 0; i < emailArrayList.size(); i++) {
            String email = emailArrayList.get(i);

            DoValidateEmailWebServiceCall doValidateEmailWebServiceCall = new DoValidateEmailWebServiceCall(email, i);
            doValidateEmailWebServiceCall.execute();


        }

    }

    private boolean isEmailValid(CharSequence emailId) {
        boolean isEmailValid = false;
        String email = emailId.toString();
        if (email.matches(emailPattern)) {
            isEmailValid = true;
        } else {
            isEmailValid = false;
        }
        return isEmailValid;
    }

    @Override
    public void onClick(View view) {

        if (view == backBtn) {

            Intent intent = new Intent(AgentsAddArtistActivity.this, AgentContainerActivity.class);
            startActivity(intent);
        } else if (view == nextBtn) {
//            Intent intent = new Intent(AgentsAddArtistActivity.this, AgentContainerActivity.class);
//            startActivity(intent);

        }
        /*if (view == nextBtn){

            Intent intent = new Intent(AgentsAddArtistActivity.this,MyProfileActivity.class);
            startActivity(intent);
        }*/
    }

    private class DoValidateEmailWebServiceCall extends AsyncTask<String, Void, String> {

        String emailId = "";
        String msgCode;
        int position = 0;

        public DoValidateEmailWebServiceCall(String emailId, int position) {
            this.emailId = emailId;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pd = new ProgressDialog(mContext,ProgressDialog.THEME_HOLO_LIGHT);
                pd.setTitle("Add Artist");
                pd.setMessage("Please wait.");
                pd.setCancelable(false);
                pd.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (msgCode == null) {
                if (pd == null) {

                } else {
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                }
                startActivity(new Intent(mContext, AgentContainerActivity.class));
                finish();
            } else {
                if (msgCode.equals("0")) {
                    if (position == 0) {
                        addArtistEdOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cancel, 0);
                    } else if (position == 1) {
                        addArtistEdTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cancel, 0);
                    } else if (position == 2) {
                        addArtistEdThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cancel, 0);
                        doAddArtistWebserviceCall();
                    }

                    // Util.showToast(mContext, "Artist not present in db");
                } else if (msgCode.equals("1")) {
                    if (position == 0) {
                        addArtistEdOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_correct, 0);
                    } else if (position == 1) {
                        addArtistEdTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_correct, 0);
                    } else if (position == 2) {
                        addArtistEdThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_correct, 0);
                        doAddArtistWebserviceCall();
                    }


                }

            }


        }

        @Override
        protected String doInBackground(String... strings) {
            String url = WebApiCall.baseURl + "user/artist_check";
            String text = "";
            BufferedReader reader = null;
            try {
                String userId = constantData.getUserid();
                String token = constantData.getTokenid();

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userId + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                con.setRequestProperty("Authorization", "Basic " + encoded);


                String data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(emailId, "UTF-8");

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
                CheckArtistPojo checkArtistPojo = gson.fromJson(text, CheckArtistPojo.class);
                msgCode = checkArtistPojo.getIsexist();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void doAddArtistWebserviceCall() {
        for (int i = 0; i < emailArrayList.size(); i++) {
            String email = emailArrayList.get(i);
            //if(isEmailValid(email)){
            DoAddEmailWebServiceCall doAddEmailWebServiceCall = new DoAddEmailWebServiceCall(email, i);
            doAddEmailWebServiceCall.execute();
            //}
        }
    }



    private class DoAddEmailWebServiceCall extends AsyncTask<String, Void, String> {

        String emailId = "";
        String msgCode = "";
        int position = 0;

        public DoAddEmailWebServiceCall(String emailId, int position) {

            this.emailId = emailId;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pd.setTitle("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd == null) {

            } else {
                if (pd.isShowing()) {
                    pd.dismiss();

                }
            }

            if (msgCode.equals("167")) {
                if (position == 2) {
                    //Util.showToast(mContext, "Artist added in AgentDb");
                    startActivity(new Intent(mContext, AgentContainerActivity.class));
                    finish();
                }
            } else if (msgCode.equals("100")) {
                if (position == 2) {
                    //Util.showToast(mContext, "Artist not present in Database");
                    startActivity(new Intent(mContext, AgentContainerActivity.class));
                    finish();
                }
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            String url = WebApiCall.baseURl + "user/artist_add";
            BufferedReader reader = null;
            String text = "";

            try {
                String userId = constantData.getUserid();
                String token = constantData.getTokenid();

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userId + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                con.setRequestProperty("Authorization", "Basic " + encoded);


                String data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(emailId, "UTF-8");

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
                ServerResponsePojo checkArtistPojo = gson.fromJson(text, ServerResponsePojo.class);
                msgCode = checkArtistPojo.getMessage_code();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}