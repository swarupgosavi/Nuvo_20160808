package xyz.theapptest.nuvo.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.adapter.NavigationAdapter;
import xyz.theapptest.nuvo.adapter.NavigationDraweritemselection;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.fragment.TabFragment;
import xyz.theapptest.nuvo.fragment.TabFragmentArtist;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Constants;

/**
 * Created by trtcpu007 on 14/7/16.
 */

public class ProducerHomeScreen extends AppCompatActivity implements NavigationDraweritemselection {
    TextView tv_title;
    DrawerLayout myDrawerLayout;
    NavigationView myNavigationView;
    FragmentManager myFragmentManager;
    FragmentTransaction myFragmentTransaction;
    TextView prod_name, prod_email, prod_phone, prod_companyname, txt_welcome;
    ConstantData constantData;
    String prod_id, firstname, lastname, email, status, role, online, createddate, lastlogout, userid, companyname, phoneNo;
    View header;
    ImageView img_edit, img_done;
    EditText ed_name, ed_companyname, ed_phonenumber;
    LinearLayout lv_nav_header;
    Button img_left;

    ListView lv;
    Context context;

    ArrayList prgmName;
    public static int[] prgmImages = {R.drawable.logout_icon_menu};
    public static String[] prgmNameList = {"Logout"};

    SharedPreferences nuvoPreferences;
    SharedPreferences.Editor nuvoEditor;
    String loginEmail = "";
    String loginPassword = "";
    String phone = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producerscreen);
        init();
        setNuvoSharedPref();
        drawerlayoutlogic();
        typeInterface();
        GetUserInfoClass getUserInfoClass = new GetUserInfoClass();
        getUserInfoClass.execute();

        customListView();

    }


    private void customListView() {

        context = this;

        lv = (ListView) findViewById(R.id.lst_menu_items);
        lv.setAdapter(new NavigationAdapter(this, prgmNameList, prgmImages, this));

    }

    private void setNuvoSharedPref() {

        nuvoPreferences = getSharedPreferences(Constants.NUVOPREF, Context.MODE_PRIVATE);
        nuvoEditor = nuvoPreferences.edit();
        loginEmail = nuvoPreferences.getString(Constants.Key_email, "");
        loginPassword = nuvoPreferences.getString(Constants.Key_Password, "");
    }

    public void onArtistSelected(View v, int positions) {
      /*  if (positions == 0) {
            Log.e("about us", "about us");
            myDrawerLayout.closeDrawer(Gravity.LEFT);
            Intent i=new Intent(ProducerHomeScreen.this,AboutUsActivity.class);
            startActivity(i);


        } else {
*/
            if (positions == 0) {
                myDrawerLayout.closeDrawer(Gravity.LEFT);
                try {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);

                    // Setting Dialog Title
                    alertDialog.setTitle("nuvo");

                    // Setting Dialog Message
                    alertDialog.setMessage("Do you really want to logout ? ");

                    // Setting Icon to Dialog


                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            nuvoEditor.clear().commit();
                            SharedPreferences shf = context.getSharedPreferences("UserType", Context.MODE_PRIVATE);
                            shf.edit().clear().commit();
                            Intent intent = new Intent(context, SignUpActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
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


           // }
        }


    }

    public void UpdateUserInfo(String firstname, String lastname, String company, String phone) {
        String text = "";
        BufferedReader reader = null;
        try {
            constantData = ConstantData.getInstance();
            constantData.getUserid();
            constantData.getTokenid();
//            String url = "http://nuvo.theapptest.xyz/v1/api/user/" + constantData.getUserid();
            String url = WebApiCall.baseURl + "user/" + constantData.getUserid();

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            String encoded = Base64.encodeToString((constantData.getUserid() + ":" + constantData.getTokenid()).getBytes("UTF-8"), Base64.NO_WRAP);
            //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
            con.setRequestProperty("Authorization", "Basic " + encoded);

            String data = URLEncoder.encode(WebApiCall.KEY_FIRSTNAME, "UTF-8")
                    + "=" + URLEncoder.encode(firstname, "UTF-8");
            data += "&" + URLEncoder.encode(WebApiCall.KEY_LASTNAME, "UTF-8") + "="
                    + URLEncoder.encode(lastname, "UTF-8");
            data += "&" + URLEncoder.encode("company_name", "UTF-8")
                    + "=" + URLEncoder.encode(company, "UTF-8");
            data += "&" + URLEncoder.encode("phone", "UTF-8")
                    + "=" + URLEncoder.encode(phone, "UTF-8");
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
            Log.e("text", text);
            JSONObject userObject = new JSONObject(text);
            String message_code = userObject.getString("message_code");
            if (message_code.equalsIgnoreCase("151")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final Dialog dialog = new Dialog(ProducerHomeScreen.this);
                            dialog.setContentView(R.layout.iphone_alertdialog);
                            //  dialog.setTitle("Success");
                            dialog.setCancelable(false);
                            // set the custom dialog components - text, image and button
                            TextView text1 = (TextView) dialog.findViewById(R.id.dialogTitle);
                            text1.setText("Success");


                            TextView textmessage = (TextView) dialog.findViewById(R.id.dialogMessage);
                            textmessage.setText("Your profile updated.");


                            Button dialogButton = (Button) dialog.findViewById(R.id.OkButton);
                            // if button is clicked, close the custom dialog
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        dialog.dismiss();
                                        getUserInfo();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                            dialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void getUserInfo() {
        try {
            constantData = ConstantData.getInstance();
            constantData.getUserid();
            constantData.getTokenid();


            // String url = "http://nuvo.theapptest.xyz/v1/api/user/" + constantData.getUserid();
            String url = WebApiCall.baseURl + "user/" + constantData.getUserid();

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            String encoded = Base64.encodeToString((constantData.getUserid() + ":" + constantData.getTokenid()).getBytes("UTF-8"), Base64.NO_WRAP);
            //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
            con.setRequestProperty("Authorization", "Basic " + encoded);
            // optional default is GET
            con.setRequestMethod("GET");

            //add request header


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
            Log.e("GETresonse-homescreen", response.toString());
            Log.e("response-homescreen", response.toString());
            JSONObject userObject = new JSONObject(response.toString());
            if (userObject != null && !userObject.equals("")) {
                prod_id = userObject.getString("id");
                firstname = userObject.getString("first_name");
                lastname = userObject.getString("last_name");
                email = userObject.getString("email");
                status = userObject.getString("status");
                role = userObject.getString("role");
                online = userObject.getString("online");
                createddate = userObject.getString("created_on");
                lastlogout = userObject.getString("last_log_out");
                userid = userObject.getString("user_id");
                companyname = userObject.getString("company_name");
                phoneNo = userObject.getString("phone");
                if (phoneNo.equalsIgnoreCase("0")) {
                    phoneNo = "";
                }
                prod_name.setText(firstname + " " + lastname);
                prod_email.setText(email);
                if (companyname != null && !companyname.isEmpty()) {
                    prod_companyname.setText(companyname);
                }
                if (phoneNo != null && !phoneNo.isEmpty()) {

                    prod_phone.setText(phoneNo);

                }


            }
            img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("img_edit", "imgedit");
                    ed_name.setVisibility(View.VISIBLE);
                    ed_name.setText(firstname + " " + lastname);
                    prod_name.setVisibility(View.GONE);
                    ed_companyname.setVisibility(View.VISIBLE);
                    ed_phonenumber.setVisibility(View.VISIBLE);
                    ed_phonenumber.setText(phoneNo);
                    ed_companyname.setText(companyname);
                    prod_companyname.setVisibility(View.GONE);
                    prod_phone.setVisibility(View.GONE);
                    img_edit.setVisibility(View.GONE);
                    img_done.setVisibility(View.VISIBLE);


                }
            });

            img_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("img_doneclicked", "imgdone");
                    img_done.setVisibility(View.GONE);
                    img_edit.setVisibility(View.VISIBLE);
                    String name = ed_name.getText().toString();
                    ed_name.setVisibility(View.GONE);
                    prod_name.setText(name);
                    prod_name.setVisibility(View.VISIBLE);
                    String companyname = ed_companyname.getText().toString();
                    ed_companyname.setVisibility(View.GONE);
                    phone = ed_phonenumber.getText().toString();
                    prod_phone.setVisibility(View.VISIBLE);
                    prod_phone.setText(phone);
                    ed_phonenumber.setVisibility(View.GONE);
                    prod_companyname.setVisibility(View.VISIBLE);
                    prod_companyname.setText(companyname);


                    String[] splited = name.split("\\s+");
                    String firstname = splited[0];
                    String lastname = splited[1];
                    Log.e("firstname", firstname);
                    Log.e("lastname", lastname);
                    Log.e("companyname", companyname);
                    if (phone.equals("")) {

                    } else {
                        UpdateUserInfo(firstname, lastname, companyname, phone);

                    }


                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void drawerlayoutlogic() {
        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        myNavigationView = (NavigationView) findViewById(R.id.nav_drawer);
        //  header = myNavigationView.getHeaderView(0);

        // prod_name.setText(constantData.getFirstname() + "   " + constantData.getLastname());
        // prod_email.setText(constantData.getEmail());
        lv_nav_header = (LinearLayout) findViewById(R.id.lv_nav_header);
        prod_name = (TextView) lv_nav_header.findViewById(R.id.tv_name);
        prod_email = (TextView) lv_nav_header.findViewById(R.id.tv_email);
        prod_phone = (TextView) lv_nav_header.findViewById(R.id.tv_phonenumber);
        prod_companyname = (TextView) lv_nav_header.findViewById(R.id.tv_companyname);
        img_edit = (ImageView) lv_nav_header.findViewById(R.id.ed_icon);
        img_done = (ImageView) lv_nav_header.findViewById(R.id.ed_save);
        ed_name = (EditText) lv_nav_header.findViewById(R.id.ed_name);
        ed_companyname = (EditText) lv_nav_header.findViewById(R.id.ed_companyname);
        ed_phonenumber = (EditText) lv_nav_header.findViewById(R.id.ed_phoennumber);
        txt_welcome = (TextView) lv_nav_header.findViewById(R.id.textView2);

        myNavigationView.setItemIconTintList(null);
        myFragmentManager = getSupportFragmentManager();
        myFragmentTransaction = myFragmentManager.beginTransaction();
        myFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();


        myNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem selectedMenuItem) {
                myDrawerLayout.closeDrawers();


                if (selectedMenuItem.getItemId() == R.id.nav_item_bookmark) {


                }

                return false;
            }

        });


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_top);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        img_left = (Button) toolbar.findViewById(R.id.btnleft);
        img_left.setVisibility(View.GONE);
        toolbar.setNavigationIcon(R.drawable.menu_icon);
        myDrawerLayout.setDrawerListener(mDrawerToggle);


        mDrawerToggle.syncState();


    }

/*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        */
/*Intent intent = new Intent(ProducerHomeScreen.this, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();*//*


        //finish();;
    }
*/


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            // super.onBackPressed();
            constantData = null;
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void typeInterface() {
        Typeface facetxttitle = Typeface.createFromAsset(getAssets(),
                "font/Vonique 64.ttf");
        tv_title.setTypeface(facetxttitle);

        Typeface facetxtchoose = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Light.ttf");
        txt_welcome.setTypeface(facetxtchoose);


        Typeface facetxtchoosebold = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Bold.ttf");
        prod_name.setTypeface(facetxtchoosebold);
        ed_name.setTypeface(facetxtchoosebold);

        Typeface facetxtchoosesemibold = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Semibold.ttf");
        prod_companyname.setTypeface(facetxtchoosesemibold);
        ed_companyname.setTypeface(facetxtchoosesemibold);

        Typeface facetxtchooseregular = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Regular.ttf");
        prod_email.setTypeface(facetxtchooseregular);
        prod_phone.setTypeface(facetxtchooseregular);
        ed_phonenumber.setTypeface(facetxtchooseregular);

    }

    private void init() {
        constantData = ConstantData.getInstance();
        tv_title = (TextView) findViewById(R.id.toolbar_title);

        // Log.e("firstname", constantData.getFirstname());
        //  Log.e("lastname", constantData.getLastname());
        //    prod_name.setText(constantData.getFirstname() + "/t" + constantData.getLastname());
        //  prod_name.setText(constantData.getFirstname()+"/t"+constantData.getLastname());
        //  prod_email.setText(constantData.getEmail());
        //prod_email.setText(constantData.getEmail());


    }

    public class GetUserInfoClass extends AsyncTask<String, Void, String> {

        public GetUserInfoClass() {

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
            getUserInfo();
            return null;
        }
    }
}
