package xyz.theapptest.nuvo.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.utils.ConstantData;

import java.util.ArrayList;
import java.util.List;

import static xyz.theapptest.nuvo.R.id.viewpager;

/**
 * Created by trtcpu007 on 14/7/16.
 */

public class TabFragment extends Fragment {
    public static TabLayout tabLayout;
    ViewPager viewPager;
    TextView txtfind, txtmyjobs, txtaudions, txtsaved;
    Button btn, btnleftimg;

    private int[] tabIconsselected = {

            R.mipmap.ic_find_icon_activexxhdpi,
            R.mipmap.ic_jobs_icon_activexxhdpi,
            R.mipmap.ic_audition_icon_activexxhdpi,
            R.mipmap.ic_saved_icon_activexxhdpi,
    };
    android.support.v7.widget.Toolbar toolbar1;
    TextView txt1, tx2;
    ConstantData con;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_tab, null);
        con = ConstantData.getInstance();
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
        btnleftimg = (Button) toolbar1.findViewById(R.id.btnleft);
        Typeface face1 = Typeface.createFromAsset(getActivity().getAssets(),
                "font/HelveticaNeue.ttf");

        Typeface face2 = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Light.ttf");
        tx2.setTypeface(face2);
        tx2.setTextSize(17);
        txt1.setTextSize(25);
        //  txt1.setText("Find");
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
                txtsaved.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_saved_icon_inactivexxhdpi, 0, 0);
                tabLayout.getTabAt(3).setCustomView(txtsaved);


                txt1 = (TextView) toolbar1.findViewById(R.id.toolbar_title);

                switch (tab.getPosition()) {
                    case 0:
                        // tabLayout.getTabAt(0).setIcon()
                        // tabLayout.getTabAt(0).select();
                        con.setFindArtist(false);
                        txt1.setText("Find");

                        //     tx2.setVisibility(View.GONE);
                        //      btn.setVisibility(View.VISIBLE);
                        //     btn.setBackgroundResource(R.drawable.bell_icon_toolicon);

                        txt1.setTextSize(19);
                        Typeface face2 = Typeface.createFromAsset(getActivity().getAssets(),
                                "font/HelveticaNeue.ttf");
                        txt1.setTypeface(face2);
                        tx2.setVisibility(View.GONE);
                        btn.setVisibility(View.VISIBLE);
                        btn.setBackgroundResource(R.drawable.bell_icon_toolicon);
                        btnleftimg.setVisibility(View.GONE);
                        toolbar1.setNavigationIcon(R.drawable.menu_icon);
                        txtfind.setTextColor(Color.parseColor("#ac2f4b"));
                        txtfind.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_find_icon_activexxhdpi, 0, 0);
                        tabLayout.getTabAt(0).setCustomView(txtfind);
                        tabLayout.getTabAt(0).setIcon(tabIconsselected[0]);
                        //  Toast.makeText(getActivity(),"0",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        txt1.setText("Jobs");
                        Typeface face3 = Typeface.createFromAsset(getActivity().getAssets(),
                                "font/HelveticaNeue.ttf");
                        txt1.setTypeface(face3);
                        tx2.setVisibility(View.GONE);
                        btn.setVisibility(View.VISIBLE);
                        btnleftimg.setVisibility(View.GONE);
                        toolbar1.setNavigationIcon(R.drawable.menu_icon);
                        //   btn.setBackgroundResource(R.drawable.bell_icon_toolicon);
                        txtmyjobs.setTextColor(Color.parseColor("#ac2f4b"));
                        txtmyjobs.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_jobs_icon_activexxhdpi, 0, 0);
                        tabLayout.getTabAt(1).setCustomView(txtmyjobs);
                        tabLayout.getTabAt(1).setIcon(tabIconsselected[1]);



                        if (con.getRequestproducerflag() != null) {
                            if (con.getRequestproducerflag().equalsIgnoreCase("jobs")) {
                                con.setSourceflagjobs("jobs");
                                con.setRequestproducerflag("jobs");
                                //lvroot.removeAllViewsInLayout();

                                Fragment newFragment = new Fragment_jobs_producer();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();


                                transaction.replace(R.id.lv_fragment_producer, newFragment);
                                transaction.addToBackStack(null);


                                transaction.commitAllowingStateLoss();
                            }
                        }


                        //   Toast.makeText(getActivity(),"1",Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        txt1.setText("Auditions");
                        Typeface face4 = Typeface.createFromAsset(getActivity().getAssets(),
                                "font/HelveticaNeue.ttf");
                        txt1.setTypeface(face4);
                        tx2.setVisibility(View.GONE);
                        //    tx2.setVisibility(View.GONE);
                        toolbar1.setNavigationIcon(R.drawable.menu_icon);
                        btn.setVisibility(View.GONE);
                        txtaudions.setTextColor(Color.parseColor("#ac2f4b"));
                        txtaudions.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_audition_icon_activexxhdpi, 0, 0);
                        tabLayout.getTabAt(2).setCustomView(txtaudions);
                        tabLayout.getTabAt(2).setIcon(tabIconsselected[2]);
                        con = ConstantData.getInstance();
                        if (con.getAudition_flag().equalsIgnoreCase("audition")) {
                            con.setSource_flag("audition");
                            con.setAudition_flag("audition");
                            //lvroot.removeAllViewsInLayout();

                            Fragment newFragment = new Fragment_audion_producer();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();

                            transaction.replace(R.id.lvauditionproducer, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commitAllowingStateLoss();
                        }

                        //    Toast.makeText(getActivity(),"2",Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        txt1.setText("Saved");
                        Typeface face5 = Typeface.createFromAsset(getActivity().getAssets(),
                                "font/HelveticaNeue.ttf");
                        txt1.setTypeface(face5);
                        tx2.setVisibility(View.GONE);
                        btn.setVisibility(View.GONE);
                        btnleftimg.setVisibility(View.GONE);
                        toolbar1.setNavigationIcon(R.drawable.menu_icon);
                        //    tx2.setVisibility(View.VISIBLE);
                        //   tx2.setText("Send Job");
                        txtsaved.setTextColor(Color.parseColor("#ac2f4b"));
                        txtsaved.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_saved_icon_activexxhdpi, 0, 0);
                        tabLayout.getTabAt(3).setCustomView(txtsaved);
                        tabLayout.getTabAt(3).setIcon(tabIconsselected[3]);
                        try {
                            if (con.getRequestproducerflag() != null) {

                                con.setRequestproducerflag("no");


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
        txtmyjobs.setText("Jobs");
        txtmyjobs.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_jobs_icon_inactivexxhdpi, 0, 0);
        tabLayout.getTabAt(1).setCustomView(txtmyjobs);

        txtaudions = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        txtaudions.setText("Auditions");
        txtaudions.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_audition_icon_inactivexxhdpi, 0, 0);
        tabLayout.getTabAt(2).setCustomView(txtaudions);

        txtsaved = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        txtsaved.setText("Saved");
        txtsaved.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_saved_icon_inactivexxhdpi, 0, 0);
        tabLayout.getTabAt(3).setCustomView(txtsaved);


        txtfind.setTextColor(Color.parseColor("#ac2f4b"));
        txtfind.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_find_icon_activexxhdpi, 0, 0);
        tabLayout.getTabAt(0).setIcon(tabIconsselected[0]);


    }

    public void SetAuditiontab() {
        tabLayout.getTabAt(2).setIcon(tabIconsselected[2]);
    }

    private void setupViewPager(final ViewPager viewPager) {
        Tabadapter adapter = new Tabadapter(getActivity().getSupportFragmentManager());
        // getActivity().getSupportFragmentManager().popBackStackImmediate();


        //make this to update the pager
        viewPager.setAdapter(null);
        adapter.addFrag(new Fragment_Criteria(), "Find");
        adapter.addFrag(new Fragment_jobs_producer(), "Jobs");
        adapter.addFrag(new Fragment_audion_producer(), "Auditions");
        adapter.addFrag(new Fragment_saved_producer(), "Saved");
        viewPager.setAdapter(adapter);


    }

    public void init(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(viewpager);
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });

    }
}


class Tabadapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public Tabadapter(FragmentManager manager) {
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