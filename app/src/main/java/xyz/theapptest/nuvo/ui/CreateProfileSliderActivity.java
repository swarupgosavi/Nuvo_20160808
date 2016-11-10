package xyz.theapptest.nuvo.ui;

/**
 * Created by trtcpu007 on 7/7/16.
 */

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.adapter.MyPagerAdapter;
import xyz.theapptest.nuvo.fragment.Fragment_CreateProfile;
import xyz.theapptest.nuvo.utils.ConstantData;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateProfileSliderActivity extends FragmentActivity implements Fragment_CreateProfile.onSomeEventListener {
    ImageView img_item;
    MyPagerAdapter pageAdapter;
    ViewPager pager;
    TextView tv_title;
    String firstname, lastname;
    ConstantData constData;
    ArrayList<HashMap<String, String>> agelist;
    ArrayList<HashMap<String, String>> charlist;
    ArrayList<HashMap<String, String>> joblist;
    ArrayList<HashMap<String, String>> langaugelist;
    ArrayList<HashMap<String, String>> recordingmth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
       // gettingfirstnamelastname();
        init();
        typeInterface();
        pagetadapterlogic();


    }

    private void gettingfirstnamelastname() {

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            firstname = extras.getString("firstname");
            lastname = extras.getString("lastname");
          Log.e("firstname",firstname);
            Log.e("lastname",lastname);
            constData.setFirstname(firstname);
           /* agelist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("age");
            charlist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("characteristics");
            joblist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("job_category");
            langaugelist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("langauge");
            recordingmth = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("recordingmethod");
            constData.setAgelist(agelist);
*/

        }

    }

    private void typeInterface() {
        Typeface facetxttitle = Typeface.createFromAsset(getAssets(),
                "font/Vonique 64.ttf");
        tv_title.setTypeface(facetxttitle);


    }

    @Override
    public void someEvent(int s) {
        Log.e("position", Integer.toString(s));
        if (s == 1) {
            pager.setCurrentItem(getItem(+1), true);
        } else {
            if (s == 2) {
                pager.setCurrentItem(getItem(+2), true);
            } else {
                if (s == 0) {

                    pager.setCurrentItem(getItem(-1), true);
                } else {
                    if (s == 3) {
                        pager.setCurrentItem(getItem(+3), true);
                    }
                }
            }
        }


    }


    @Override
    public void onBackPressed() {

    }

    private int getItem(int i) {
        return pager.getCurrentItem() + i;
    }

    private void pagetadapterlogic() {
        pageAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);

        img_item.setImageResource(R.drawable.create_profile);
        android.view.ViewGroup.LayoutParams layoutParams = img_item.getLayoutParams();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int pxwidth = Math.round(320 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        int pxheight = Math.round(80 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        layoutParams.width = pxwidth;
        layoutParams.height = pxheight;
        img_item.setLayoutParams(layoutParams);


        pager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });


        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                Log.e("i2", Integer.toString(i2));
                Log.e("i", Integer.toString(i));

            }

            @Override
            public void onPageSelected(int i) {

                Log.e("position-selected", Integer.toString(i));
                Log.e("position", Integer.toString(i));
                if (i == 0) {
                    img_item.setImageResource(R.drawable.create_profile);
                    android.view.ViewGroup.LayoutParams layoutParams = img_item.getLayoutParams();
                    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    int pxwidth = Math.round(350 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    int pxheight = Math.round(100 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    layoutParams.width = pxwidth;
                    layoutParams.height = pxheight;
                    img_item.setLayoutParams(layoutParams);
                } else if (i == 1) {

                    img_item.setImageResource(R.drawable.createprofile2);
                    android.view.ViewGroup.LayoutParams layoutParams = img_item.getLayoutParams();
                    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    int pxwidth = Math.round(350 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    int pxheight = Math.round(100 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    layoutParams.width = pxwidth;
                    layoutParams.height = pxheight;
                    img_item.setLayoutParams(layoutParams);
                } else {
                    if (i == 2) {
                        img_item.setImageResource(R.drawable.createprofile3);
                        android.view.ViewGroup.LayoutParams layoutParams = img_item.getLayoutParams();
                        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                        int pxwidth = Math.round(350 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                        int pxheight = Math.round(100 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                        layoutParams.width = pxwidth;
                        layoutParams.height = pxheight;
                        img_item.setLayoutParams(layoutParams);
                    } else {
                        if (i == 3) {
                            img_item.setImageResource(R.drawable.createprofile4);
                            android.view.ViewGroup.LayoutParams layoutParams = img_item.getLayoutParams();
                            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                            int pxwidth = Math.round(350 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                            int pxheight = Math.round(100 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                            layoutParams.width = pxwidth;
                            layoutParams.height = pxheight;
                            img_item.setLayoutParams(layoutParams);
                        }
                    }
                }
            }


            @Override
            public void onPageScrollStateChanged(int i) {
                Log.e("position-scroll", Integer.toString(i));
            }
        });


    }

    private void init() {
        tv_title = (TextView) findViewById(R.id.toolbar_title);
        img_item = (ImageView) findViewById(R.id.img_item);




    }

}