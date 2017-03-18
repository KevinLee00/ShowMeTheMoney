package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DetailedBarsFragment extends Fragment {

    public DetailedBarsFragment() {

    }

//    public static DetailedBarsFragment newInstance() {
//        DetailedBarsFragment fragment = new DetailedBarsFragment();
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.progress_bar_main, container, false);
    }
}
