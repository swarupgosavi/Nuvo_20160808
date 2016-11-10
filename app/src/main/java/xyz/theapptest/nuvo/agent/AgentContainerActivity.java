package xyz.theapptest.nuvo.agent;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.adapter.Tabadapter;
import xyz.theapptest.nuvo.fragment.Fragment_Criteria;
import xyz.theapptest.nuvo.fragment.Fragment_audion_producer;
import xyz.theapptest.nuvo.fragment.Fragment_jobs_producer;
import xyz.theapptest.nuvo.fragment.Fragment_jobs_profile;
import xyz.theapptest.nuvo.fragment.Fragment_saved_producer;
import xyz.theapptest.nuvo.utils.Fonts;

public class AgentContainerActivity extends AppCompatActivity {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    TextView txtArtistFooter, txtJobsFooter, txtMyProfile;
    Button btn;
    Fonts fonts;
    private int[] tabIconsselected = {

            R.mipmap.ic_jobs_icon_inactivexxhdpi,
            R.mipmap.ic_jobs_icon_inactivexxhdpi,
            R.mipmap.ic_action_profile_inactive,
    };
    android.support.v7.widget.Toolbar toolbar1;
    TextView txt1, tx2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_container);

        init();
        tabLogic();
        typeInterface();
    }

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


    private void tabLogic() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        /*toolbar1 = (android.support.v7.widget.Toolbar)this.findViewById(R.id.toolbar_top);
        txt1 =(TextView)toolbar1.findViewById(R.id.toolbar_title) ;
        tx2=(TextView)toolbar1.findViewById(R.id.rightlabel) ;
        btn=(Button)toolbar1.findViewById(R.id.btn) ;
        Typeface face1 = Typeface.createFromAsset(this.getAssets(),
                "font/HelveticaNeue.ttf");

        Typeface face2 = Typeface.createFromAsset(this.getAssets(),
                "font/OpenSans-Light.ttf");
        tx2.setTypeface(face2);
        tx2.setTextSize(17);
        txt1.setTextSize(19);
        //  txt1.setText("Find");
        //  txt1.setTypeface(face1);*/
        setupTabIcons();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                viewPager.setCurrentItem(tab.getPosition());


                txtArtistFooter.setTextColor(Color.parseColor("#f5f5f6"));
                txtArtistFooter.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_jobs_icon_inactivexxhdpi, 0, 0);
                tabLayout.getTabAt(0).setCustomView(txtArtistFooter);
                txtJobsFooter.setTextColor(Color.parseColor("#f5f5f6"));
                txtJobsFooter.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_jobs_icon_inactivexxhdpi, 0, 0);
                tabLayout.getTabAt(1).setCustomView(txtJobsFooter);
                txtMyProfile.setTextColor(Color.parseColor("#f5f5f6"));
                txtMyProfile.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_action_profile_inactive, 0, 0);
                tabLayout.getTabAt(2).setCustomView(txtMyProfile);


                // txt1 =(TextView)toolbar1.findViewById(R.id.toolbar_title) ;

                switch (tab.getPosition()) {
                    case 0:
                        // tabLayout.getTabAt(0).setIcon()
                        // tabLayout.getTabAt(0).select();

                        //       txt1.setText("Find");

                        //     tx2.setVisibility(View.GONE);
                        //      btn.setVisibility(View.VISIBLE);
                        //     btn.setBackgroundResource(R.drawable.bell_icon_toolicon);

                       /* txt1.setText("Find");
                        Typeface face2 = Typeface.createFromAsset(getAssets(),
                                "font/OpenSans-Light.ttf");
                        txt1.setTypeface(face2);
                        tx2.setVisibility(View.GONE);
                        btn.setVisibility(View.VISIBLE);
                        btn.setBackgroundResource(R.drawable.bell_icon_toolicon);*/


                        txtArtistFooter.setTextColor(Color.parseColor("#ac2f4b"));
                        txtArtistFooter.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_jobs_icon_activexxhdpi, 0, 0);
                        tabLayout.getTabAt(0).setCustomView(txtArtistFooter);
                        tabLayout.getTabAt(0).setIcon(tabIconsselected[0]);
                        //  Toast.makeText(this,"0",Toast.LENGTH_LONG).show();
                        break;

                    case 1:
                        /*txt1.setText("Auditions");
                        Typeface face4 = Typeface.createFromAsset(getAssets(),
                                "font/OpenSans-Light.ttf");
                        txt1.setTypeface(face4);
                        //    tx2.setVisibility(View.GONE);
                        //    btn.setVisibility(View.GONE);*/
                        txtJobsFooter.setTextColor(Color.parseColor("#ac2f4b"));
                        txtJobsFooter.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_jobs_icon_activexxhdpi, 0, 0);
                        tabLayout.getTabAt(1).setCustomView(txtJobsFooter);
                        tabLayout.getTabAt(1).setIcon(tabIconsselected[1]);





                        //    Toast.makeText(this,"2",Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                       /* txt1.setText("Jobs");
                        Typeface face3 = Typeface.createFromAsset(getAssets(),
                                "font/OpenSans-Light.ttf");
                        txt1.setTypeface(face3);*/
                        //   btn.setVisibility(View.VISIBLE);
                        //   btn.setBackgroundResource(R.drawable.bell_icon_toolicon);

                        txtMyProfile.setTextColor(Color.parseColor("#ac2f4b"));
                        txtMyProfile.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_action_profile_active, 0, 0);
                        tabLayout.getTabAt(2).setCustomView(txtMyProfile);
                        tabLayout.getTabAt(2).setIcon(tabIconsselected[2]);


                        //   Toast.makeText(this,"1",Toast.LENGTH_LONG).show();
                        break;

                    /*case 3:
                        txt1.setText("Saved");
                        Typeface face5 = Typeface.createFromAsset(getActivity().getAssets(),
                                "font/OpenSans-Light.ttf");
                        txt1.setTypeface(face5);
                        //    btn.setVisibility(View.GONE);
                        //    tx2.setVisibility(View.VISIBLE);
                        //   tx2.setText("Send Job");
                        txtsaved.setTextColor(Color.parseColor("#ac2f4b"));
                        txtsaved.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_saved_icon_activexxhdpi, 0, 0);
                        tabLayout.getTabAt(3).setCustomView(txtsaved);
                        tabLayout.getTabAt(3).setIcon(tabIconsselected[3]);


                        //    Toast.makeText(getActivity(),"3",Toast.LENGTH_LONG).show();
                        break;*/
                }


            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    private void typeInterface() {

        /*Typeface facetxtsigin = Typeface.createFromAsset(getAssets(),
                "font/Roboto-Regular.ttf");

        txtArtistFooter.setTypeface(facetxtsigin);
        txtMyProfile.setTypeface(facetxtsigin);
        txtJobsFooter.setTypeface(facetxtsigin);*/

    }


    private void setupTabIcons() {
        txtArtistFooter = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        txtArtistFooter.setText("Artists");
        txtArtistFooter.setTypeface(fonts.robotoRegular);
        txtArtistFooter.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_jobs_icon_inactivexxhdpi, 0, 0);
        tabLayout.getTabAt(0).setCustomView(txtArtistFooter);
        // btn.setBackgroundResource(R.drawable.bell_icon_toolicon);


        txtJobsFooter = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        txtJobsFooter.setText("Jobs");
        txtJobsFooter.setTypeface(fonts.robotoRegular);
        txtJobsFooter.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_jobs_icon_inactivexxhdpi, 0, 0);
        tabLayout.getTabAt(1).setCustomView(txtJobsFooter);

        txtMyProfile = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        txtMyProfile.setText("My Profile");
        txtMyProfile.setTypeface(fonts.robotoRegular);
        txtMyProfile.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_action_profile_inactive, 0, 0);
        tabLayout.getTabAt(2).setCustomView(txtMyProfile);

        /*txtsaved = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        txtsaved.setText("Saved");
        txtsaved.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_saved_icon_inactivexxhdpi, 0, 0);
        tabLayout.getTabAt(3).setCustomView(txtsaved);*/


        txtArtistFooter.setTextColor(Color.parseColor("#ac2f4b"));
        txtArtistFooter.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_jobs_icon_activexxhdpi, 0, 0);
        tabLayout.getTabAt(0).setIcon(tabIconsselected[0]);


    }


    private void setupViewPager(ViewPager viewPager) {
        TabadapterAgent adapter = new TabadapterAgent(this.getSupportFragmentManager());
        adapter.addFrag(new ArtistsListFragment(), "Artists");
        adapter.addFrag(new Fragment_jobs_profile(), "Jobs");
        adapter.addFrag(new MyProfileFragment(), "My Profile");


        viewPager.setAdapter(adapter);
    }

    public void init() {

        fonts = new Fonts(this);
        tabLayout = (TabLayout) findViewById(R.id.agent_tabs);
        viewPager = (ViewPager) findViewById(R.id.agent_viewpager);
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });
    }

}


class TabadapterAgent extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public TabadapterAgent(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //Log.e("position",""+position);


        return "";
    }
}
