package xyz.theapptest.nuvo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.theapptest.nuvo.R;

/**
 * Created by trtcpu007 on 1/8/16.
 */

public class FragmentSavedArtist extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmentsavedartist, null);
        return rootView;
    }
}
