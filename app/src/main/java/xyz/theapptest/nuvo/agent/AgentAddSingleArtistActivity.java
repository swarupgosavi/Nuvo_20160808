package xyz.theapptest.nuvo.agent;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.pojo.CheckArtistPojo;
import xyz.theapptest.nuvo.pojo.ServerResponsePojo;
import xyz.theapptest.nuvo.pojo.UpdatePojo;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.ui.SignInActivity;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Util;

/**
 * Created by ADMIN on 8/12/2016.
 */
public class AgentAddSingleArtistActivity extends AppCompatActivity {

    EditText addArtistOneEt;
    Button nextBtn;
    ImageView backImv;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Context mContext = AgentAddSingleArtistActivity.this;
    ConstantData constantData;
    String emailId = "";
    ProgressDialog pd;
    TextView tv_title;
    CustomizeDialog customizeDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_add_single_artist_activity_layout);
        constantData = ConstantData.getInstance();
        init();
        setUpListner();
    }

    private void init() {
        tv_title = (TextView) findViewById(R.id.aaaa_toolbar_title);
        Typeface facetxttitle = Typeface.createFromAsset(getAssets(),
                "font/Vonique 64.ttf");
        tv_title.setTypeface(facetxttitle);
        addArtistOneEt = (EditText) findViewById(R.id.agent_add_single_artist_activity_artist_one_et_id);
        nextBtn = (Button) findViewById(R.id.agent_add_single_artist_activity_next_btn_id);
        backImv = (ImageView) findViewById(R.id.agent_add_single_artist_activity_toolbar_back_btn_id);

    }

    private void setUpListner() {
        backImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                finish();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailId = addArtistOneEt.getText().toString();
                if (emailId.isEmpty()) {
                    customizeDialog = new CustomizeDialog(AgentAddSingleArtistActivity.this);
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Please enter  emailId.");
                    customizeDialog.show();
                } else {
                    if (isEmailValid(emailId)) {
                        DoValidateEmailWebServiceCall doValidateEmailWebServiceCall = new DoValidateEmailWebServiceCall(emailId);
                        doValidateEmailWebServiceCall.execute();

//                    DoAddEmailWebServiceCall doAddEmailWebServiceCall = new DoAddEmailWebServiceCall(emailId);
//                    doAddEmailWebServiceCall.execute();
                    } else {

                        customizeDialog = new CustomizeDialog(AgentAddSingleArtistActivity.this);
                        customizeDialog.setTitle("nuvo");
                        customizeDialog.setCancelable(false);
                        customizeDialog.setMessage("Please enter valid emailId.");
                        customizeDialog.show();


                    }

                }
            }
        });
        /*addArtistOneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isEmailValid(charSequence)) {

                    DoValidateEmailWebServiceCall doValidateEmailWebServiceCall = new DoValidateEmailWebServiceCall(charSequence.toString());
                    doValidateEmailWebServiceCall.execute();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/

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
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class DoValidateEmailWebServiceCall extends AsyncTask<String, Void, String> {

        String emailId = "";
        String msgCode;

        public DoValidateEmailWebServiceCall(String emailId) {
            this.emailId = emailId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(mContext,ProgressDialog.THEME_HOLO_LIGHT);
            pd.setTitle("Add Artist");
            pd.setMessage("Please wait.");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("addsingleartsits", msgCode);
            if (msgCode.equals("0")) {
                addArtistOneEt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cancel, 0);
                // Util.showToast(mContext, "Artist not present in db");
            } else if (msgCode.equals("1")) {
                addArtistOneEt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_correct, 0);

                //  Util.showToast(mContext, "Artist Present in db");
            }

            doAddArtistWebserviceCall();
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

                Log.e("msgagentsingle", msgCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void doAddArtistWebserviceCall() {
        try {
            nextBtn.setClickable(false);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(5000);

                            } catch (InterruptedException ie) {
                                ie.printStackTrace();
                            }

                        }
                    }
            );
            DoAddEmailWebServiceCall doAddEmailWebServiceCall = new DoAddEmailWebServiceCall(emailId);
            doAddEmailWebServiceCall.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class DoAddEmailWebServiceCall extends AsyncTask<String, Void, String> {

        String emailId = "";
        String msgCode = "";

        public DoAddEmailWebServiceCall(String emailId) {

            this.emailId = emailId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait");
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

            if (msgCode.equals("101")) {
                Log.e("doaddemail", msgCode);
                //  Util.showToast(mContext, "Artist added in AgentDb");

             /*  customizeDialog = new CustomizeDialog(AgentAddSingleArtistActivity.this);
                customizeDialog.setTitle("nuvo");
                customizeDialog.setCancelable(false);
                customizeDialog.setMessage("Artist already added.");
                customizeDialog.show();*/

                new AlertDialog.Builder(AgentAddSingleArtistActivity.this,AlertDialog.THEME_HOLO_LIGHT)
                        .setTitle("Success")
                        .setMessage("Artist already added")
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        AgentAddSingleArtistActivity.this.runOnUiThread(new Runnable() {
                                            public void run() {
                                                nextBtn.setClickable(false);
                                                addArtistOneEt.setText("");
                                                startActivity(new Intent(mContext, AgentContainerActivity.class));
                                                finish();

                                            }
                                        });

                                        dialog.dismiss();


                                    }
                                }).show();


            } else if (msgCode.equals("100")) {
                // Util.showToast(mContext, "Artist not present in Database");
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


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
