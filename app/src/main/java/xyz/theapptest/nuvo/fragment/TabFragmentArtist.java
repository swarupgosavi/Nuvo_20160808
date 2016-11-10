package xyz.theapptest.nuvo.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.utils.ConstantData;

/**
 * Created by trtcpu007 on 1/8/16.
 */

public class TabFragmentArtist extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    TextView txtfind, txtmyjobs, txtaudions, txtsaved;
    Button btn, btnleft;

    private int[] tabIconsselected = {

            R.mipmap.ic_find_icon_activexxhdpi,
            R.mipmap.ic_jobs_icon_activexxhdpi,
            R.mipmap.ic_audition_icon_activexxhdpi,
            R.mipmap.ic_action_profile_active,
    };
    android.support.v7.widget.Toolbar toolbar1;
    TextView txt1, tx2;
    ConstantData con;
    ImageView img_play;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_tab, null);
        init(x);
        tabLogic();
        typeInterface();

        return x;


    }

    private void typeInterface() {

        Typeface facetxtsigin = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto-Regular.ttf");

        txtfind.setTypeface(facetxtsigin);
        txtaudions.setTypeface(facetxtsigin);
        txtmyjobs.setTypeface(facetxtsigin);
        txtsaved.setTypeface(facetxtsigin);


    }


    private void tabLogic() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        toolbar1 = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar_top);
        txt1 = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        tx2 = (TextView) toolbar1.findViewById(R.id.rightlabel);
        btn = (Button) toolbar1.findViewById(R.id.btn);
        btnleft = (Button) toolbar1.findViewById(R.id.btnleft);
        Typeface face1 = Typeface.createFromAsset(getActivity().getAssets(),
                "font/HelveticaNeue.ttf");

        Typeface face2 = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Light.ttf");
        tx2.setTypeface(face2);
        tx2.setTextSize(14);
        txt1.setTextSize(19);
        txt1.setText("Jobs");
        txt1.setTypeface(face1);
        setupTabIcons();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());


                txtfind.setTextColor(Color.parseColor("#f5f5f6"));
                txtfind.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_find_icon_inactivexxhdpi, 0, 0);
                tabLayout.getTabAt(0).setCustomView(txtfind);
                txtmyjobs.setTextColor(Color.parseColor("#f5f5f6"));
                txtmyjobs.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_jobs_icon_inactivexxhdpi, 0, 0);
                tabLayout.getTabAt(1).setCustomView(txtmyjobs);
                txtaudions.setTextColor(Color.parseColor("#f5f5f6"));
                txtaudions.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_audition_icon_inactivexxhdpi, 0, 0);
                tabLayout.getTabAt(2).setCustomView(txtaudions);
                txtsaved.setTextColor(Color.parseColor("#f5f5f6"));
                txtsaved.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_action_profile_inactive, 0, 0);
                tabLayout.getTabAt(3).setCustomView(txtsaved);


                txt1 = (TextView) toolbar1.findViewById(R.id.toolbar_title);

                switch (tab.getPosition()) {
                    case 0:

                        txt1.setText("Find");
                        btn.setVisibility(View.VISIBLE);

                     /*   tx2.setVisibility(View.GONE);
                        btn.setVisibility(View.VISIBLE);*/
                        //     btn.setBackgroundResource(R.drawable.bell_icon_toolicon);


                        txtfind.setTextColor(Color.parseColor("#ac2f4b"));
                        txtfind.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_find_icon_activexxhdpi, 0, 0);
                        tabLayout.getTabAt(0).setCustomView(txtfind);
                        tabLayout.getTabAt(0).setIcon(tabIconsselected[0]);

                        con = ConstantData.getInstance();
                        if (con.getFindartistdetails() != null) {
                            if (con.getFindartistdetails().equalsIgnoreCase("artistfind")) {
                                con.setSourceflagartist("artistfind");
                                con.setFindartistdetails("artistfind");
                                //lvroot.removeAllViewsInLayout();

                                Fragment newFragment = new Fragment_CriteriaArtist();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();


                                transaction.replace(R.id.rootfragment, newFragment);
                                transaction.addToBackStack(null);


                                transaction.commitAllowingStateLoss();
                            }
                        }


                        //  Toast.makeText(getActivity(),"0",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        txt1.setText("Jobs");
                        btn.setVisibility(View.GONE);
                        btnleft.setVisibility(View.GONE);
                        tx2.setVisibility(View.GONE);
                        //   btn.setBackgroundResource(R.drawable.bell_icon_toolicon);
                        txtmyjobs.setTextColor(Color.parseColor("#ac2f4b"));
                        txtmyjobs.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_jobs_icon_activexxhdpi, 0, 0);
                        tabLayout.getTabAt(1).setCustomView(txtmyjobs);
                        tabLayout.getTabAt(1).setIcon(tabIconsselected[1]);

                        con = ConstantData.getInstance();
                        if (con.getJobdetailsartist() != null) {
                            if (con.getJobdetailsartist().equalsIgnoreCase("artistjobs")) {
                                con.setSourcejobartist("artistjobs");
                                con.setJobdetailsartist("artistjobs");
                                //lvroot.removeAllViewsInLayout();

                                Fragment newFragment = new Fragment_jobs_UI();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();


                                transaction.replace(R.id.fragment_jobs_ui_main_LL_id, newFragment);
                                transaction.addToBackStack(null);


                                transaction.commitAllowingStateLoss();
                            }
                        }

                        //   Toast.makeText(getActivity(),"1",Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        txt1.setText("Auditions");
                        tx2.setVisibility(View.GONE);
                        btnleft.setVisibility(View.GONE);
                        btn.setVisibility(View.GONE);
                        txtaudions.setTextColor(Color.parseColor("#ac2f4b"));
                        txtaudions.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_audition_icon_activexxhdpi, 0, 0);
                        tabLayout.getTabAt(2).setCustomView(txtaudions);
                        tabLayout.getTabAt(2).setIcon(tabIconsselected[2]);
                        con = ConstantData.getInstance();
                        if (con.getAuditionartistdetails() != null) {
                            if (con.getAuditionartistdetails().equalsIgnoreCase("artistaudition")) {
                                con.setSourceauditionartist("artistaudition");
                                con.setAuditionartistdetails("artistaudition");
                                //lvroot.removeAllViewsInLayout();

                                Fragment newFragment = new Fragment_Auditions();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();


                                transaction.replace(R.id.fragment_audion_layout, newFragment);
                                transaction.addToBackStack(null);


                                transaction.commitAllowingStateLoss();
                            }
                        }

                        //    Toast.makeText(getActivity(),"2",Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        txt1.setText("My Profile");

                        btn.setVisibility(View.GONE);
                        btnleft.setVisibility(View.GONE);
                        tx2.setVisibility(View.VISIBLE);
                        //     tx2.setText("Send Job");
                        txtsaved.setTextColor(Color.parseColor("#ac2f4b"));
                        txtsaved.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_action_profile_active, 0, 0);
                        tabLayout.getTabAt(3).setCustomView(txtsaved);
                        tabLayout.getTabAt(3).setIcon(tabIconsselected[3]);


                        //    Toast.makeText(getActivity(),"3",Toast.LENGTH_LONG).show();
                        break;
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

    private void setupTabIcons() {
        txtfind = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        txtfind.setText("Find");

        txtfind.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_find_icon_inactivexxhdpi, 0, 0);
        tabLayout.getTabAt(0).setCustomView(txtfind);
        // btn.setBackgroundResource(R.drawable.bell_icon_toolicon);


        txtmyjobs = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        txtmyjobs.setText("My Jobs");
        txtmyjobs.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_jobs_icon_inactivexxhdpi, 0, 0);
        tabLayout.getTabAt(1).setCustomView(txtmyjobs);

        txtaudions = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        txtaudions.setText("Auditions");
        txtaudions.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_audition_icon_inactivexxhdpi, 0, 0);
        tabLayout.getTabAt(2).setCustomView(txtaudions);

        txtsaved = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        txtsaved.setText("My Profile");
        txtsaved.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_action_profile_inactive, 0, 0);
        tabLayout.getTabAt(3).setCustomView(txtsaved);


        txtmyjobs.setTextColor(Color.parseColor("#ac2f4b"));
        txtmyjobs.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_jobs_icon_activexxhdpi, 0, 0);

        tabLayout.getTabAt(1).setIcon(tabIconsselected[1]);

        viewPager.setCurrentItem(1);

    }


    private void setupViewPager(ViewPager viewPager) {
        TabadapterArtist adapter = new TabadapterArtist(getActivity().getSupportFragmentManager());
        adapter.addFrag(new Fragment_CriteriaArtist(), "Find");
        adapter.addFrag(new Fragment_jobs_UI(), "Jobs");
        adapter.addFrag(new Fragment_Auditions(), "Auditions");
        adapter.addFrag(new Fragment_Saved(), "My Profile");
        viewPager.setAdapter(adapter);


    }

    public void init(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.beginFakeDrag();
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });
    }


    public class TabadapterArtist extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public TabadapterArtist(FragmentManager manager) {
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

        public void replaceFrag(Fragment fragment, int index) {
            mFragmentList.remove(index);
            mFragmentList.add(index, fragment);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            //Log.e("position",""+position);


            return "";
        }
    }
}
