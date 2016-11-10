package xyz.theapptest.nuvo.adapter;

import android.support.v4.app.FragmentPagerAdapter;

import xyz.theapptest.nuvo.fragment.Fragment_CreateProfile;
import xyz.theapptest.nuvo.fragment.Fragment_Details;
import xyz.theapptest.nuvo.fragment.Fragment_Profile_Recorded;
import xyz.theapptest.nuvo.fragment.Fragment_contact_info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trtcpu007 on 7/7/16.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<android.support.v4.app.Fragment> fragments;

    public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<android.support.v4.app.Fragment>();
        fragments.add(new Fragment_CreateProfile());
        fragments.add(new Fragment_contact_info());
        fragments.add(new Fragment_Details());
        fragments.add(new Fragment_Profile_Recorded());
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}