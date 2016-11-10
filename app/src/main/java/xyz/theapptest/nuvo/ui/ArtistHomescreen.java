package xyz.theapptest.nuvo.ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import xyz.theapptest.nuvo.adapter.NavigationDraweritemselection;
/**
 * Created by trtcpu007 on 1/8/16.
 */
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import xyz.theapptest.nuvo.adapter.NavigationadapterArtist;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.fragment.TabFragment;
import xyz.theapptest.nuvo.fragment.TabFragmentArtist;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Constants;

public class ArtistHomescreen extends AppCompatActivity {
    TextView tv_title;
    DrawerLayout myDrawerLayout;
    NavigationView myNavigationView;
    FragmentManager myFragmentManager;
    FragmentTransaction myFragmentTransaction;
    TextView prod_name, prod_email, prod_phone, prod_companyname, txt_welcome;
    ConstantData constantData;
    String prod_id, firstname, lastname, email, status, role, online, createddate, lastlogout, userid, companyname;
    View header;
    ImageView img_edit, img_done;
    EditText ed_name, ed_companyname, ed_phonenumber;
    LinearLayout lv_nav_header;
    ListView lv;
    Context context;
    ArrayList prgmName;
    public static int[] prgmImages = {R.drawable.logout_icon_menu};
    public static String[] prgmNameList = {"Logout"};
    SharedPreferences nuvoPreferences;
    SharedPreferences.Editor nuvoEditor;
    String loginEmail = "";
    String loginPassword = "";
    Button logOutBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistscreen);
         init();
        drawerlayoutlogic();
        setNuvoSharedPref();

        //   typeInterface();

        //  customListView();
    }
  /*  private void customListView() {
        context = this;
        lv = (ListView) findViewById(R.id.lst_menu_items);
        lv.setAdapter(new NavigationadapterArtist(this, prgmNameList, prgmImages, this));
    }*/
    /*public void onArtistSelected(View v, int positions) {
        if (positions == 0) {
            myDrawerLayout.closeDrawer(Gravity.LEFT);
            constantData = ConstantData.getInstance();
            constantData.setUserid("");
            Intent intent = new Intent(ArtistHomescreen.this, SignUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }*/

    private void setNuvoSharedPref() {

        nuvoPreferences = getSharedPreferences(Constants.NUVOPREF, Context.MODE_PRIVATE);
        nuvoEditor = nuvoPreferences.edit();
        loginEmail = nuvoPreferences.getString(Constants.Key_email, "");
        loginPassword = nuvoPreferences.getString(Constants.Key_Password, "");
    }

    private void drawerlayoutlogic() {
      /*  myDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
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
        myNavigationView.setItemIconTintList(null);*/
        myFragmentManager = getSupportFragmentManager();
        myFragmentTransaction = myFragmentManager.beginTransaction();
        myFragmentTransaction.replace(R.id.containerView, new TabFragmentArtist()).commit();
      /*  myNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem selectedMenuItem) {
                myDrawerLayout.closeDrawers();
                if (selectedMenuItem.getItemId() == R.id.nav_item_bookmark) {
                }
                return false;
            }
        });*/
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_top);
      /*  ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        myDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();*/
    }
  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
        *//*Intent intent = new Intent(ProducerHomeScreen.this, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();*//*
        //finish();;


    }*/

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
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
        Typeface facetxtchoosesemibold = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Semibold.ttf");
        prod_companyname.setTypeface(facetxtchoosesemibold);
        Typeface facetxtchooseregular = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Regular.ttf");
        prod_email.setTypeface(facetxtchooseregular);
        prod_phone.setTypeface(facetxtchooseregular);
    }

    private void init() {
        constantData = ConstantData.getInstance();
        tv_title = (TextView) findViewById(R.id.toolbar_title);

    }
}
