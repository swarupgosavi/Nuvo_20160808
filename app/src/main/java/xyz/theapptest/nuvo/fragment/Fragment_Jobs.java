package xyz.theapptest.nuvo.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.ui.ProducerHomeScreen;
import xyz.theapptest.nuvo.ui.SplashActivity;

/**
 * Created by trtcpu007 on 15/7/16.
 */

public class Fragment_Jobs extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jobs, null);
        init(rootView);
        typeface();
        typeofuser();


        return rootView;
    }


    private void typeofuser() {

        SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
        String usertype = shf.getString("usertype", null);
        String userid = shf.getString("userid", null);

        if (usertype != null && userid != null) {

            if (usertype.equalsIgnoreCase("Producer")) {
                setupViewPagerProducer(viewPager);
                tabLayout.setupWithViewPager(viewPager);
                setupTabIconsProducer();

            } else {
                if (usertype.equalsIgnoreCase("Artist")) {
                    setupViewPagerArtist(viewPager);
                    tabLayout.setupWithViewPager(viewPager);
                    setupTabIconsArtist();

                } else {
                    if (usertype.equalsIgnoreCase("Agent")) {


                    }
                }
            }


        }


    }

    private void init(View rootView) {
        viewPager = (ViewPager)rootView.findViewById(R.id.viewpager);


        tabLayout = (TabLayout)rootView.findViewById(R.id.tabs);


    }

    private void typeface() {


    }

    private void setupTabIconsArtist() {
        Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Regular.ttf");
        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOne.setTypeface(facetxtsigintextbox);
        tabOne.setText("Ongoing");

        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Past");
        tabTwo.setTypeface(facetxtsigintextbox);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabThree.setText("Saved");
        tabThree.setTypeface(facetxtsigintextbox);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupTabIconsProducer() {
        Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Regular.ttf");
        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOne.setTypeface(facetxtsigintextbox);
        tabOne.setText("Request");

        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Ongoing");
        tabTwo.setTypeface(facetxtsigintextbox);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabThree.setText("Past");
        tabThree.setTypeface(facetxtsigintextbox);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }





    /**
     * Adding fragments to ViewPager
     * @param viewPager
     */

    private void setupViewPagerProducer(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new FragmentRequestProducer(), "Request");
        adapter.addFrag(new Fragment_OngoingProducer(), "Ongoing");
        adapter.addFrag(new FragmentPastProducer(), "Past");
        viewPager.setAdapter(adapter);
    }


    private void setupViewPagerArtist(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new FragmentOngoingArtist(), "Ongoing");
        adapter.addFrag(new FragmentPastArtist(), "Past");
        adapter.addFrag(new FragmentSavedArtist(), "Saved");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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
            return mFragmentTitleList.get(position);
        }
    }

}
