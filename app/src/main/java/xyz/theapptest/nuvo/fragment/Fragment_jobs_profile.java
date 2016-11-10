package xyz.theapptest.nuvo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.theapptest.nuvo.R;

/**
 * Created by trtcpu007 on 16/8/16.
 */

public class Fragment_jobs_profile extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jobs_agent, null);
        return  rootView;
    }
}
