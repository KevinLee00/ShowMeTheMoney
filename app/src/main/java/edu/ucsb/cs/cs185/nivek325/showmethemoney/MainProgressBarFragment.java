package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainProgressBarFragment extends Fragment {

    public MainProgressBarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_up, container, false);

        String HTMLOpenTag = "<b><font color='#20CD5F'>";
        String HTMLEndTag = "</font></b>";

        TextView amtLeft = (TextView) view.findViewById(R.id.amtSpent);
        TextView perLeft = (TextView) view.findViewById(R.id.perSpent);

        amtLeft.setText(Html.fromHtml("You have" + HTMLOpenTag + " $750 " + HTMLEndTag+ "left to spend."));
        perLeft.setText(Html.fromHtml("You've used" + HTMLOpenTag + " 73% " + HTMLEndTag+ "of your budget."));

        return view;
    }

}
