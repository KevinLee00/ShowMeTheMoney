package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.content.Context;
import android.util.AttributeSet;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

public class CustomCalendarView extends CompactCalendarView {

    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return false;
    }
}
