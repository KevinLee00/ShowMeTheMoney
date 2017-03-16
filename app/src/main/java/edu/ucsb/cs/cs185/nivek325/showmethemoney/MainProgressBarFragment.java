package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainProgressBarFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public MainProgressBarFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
//    public static MainProgressBarFragment newInstance(int sectionNumber) {
//        MainProgressBarFragment fragment = new MainProgressBarFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.progress_bar_detail, container, false);
    }

}
