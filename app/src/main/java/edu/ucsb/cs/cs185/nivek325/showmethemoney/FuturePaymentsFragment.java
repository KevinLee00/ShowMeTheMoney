package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static edu.ucsb.cs.cs185.nivek325.showmethemoney.R.id.month;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FuturePaymentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FuturePaymentsFragment extends Fragment {

    private OnFragmentInteractionListener dListener;
    private TransactionAdapter transactionAdapter;
    private static CustomCalendarView customCalendarView;

    public FuturePaymentsFragment() {
        // Required empty public constructor
    }

    public void setTransactionAdapter(TransactionAdapter transactionAdapter) {
        this.transactionAdapter = transactionAdapter;
    }

    public static FuturePaymentsFragment newInstance() {
        FuturePaymentsFragment fragment = new FuturePaymentsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    public static void addCalendarEvent(Event e)
    {
        customCalendarView.addEvent(e, true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_future_payments, container, false);
        ListView listView = (ListView) view.findViewById(R.id.future_list);
        listView.setAdapter(transactionAdapter);

        customCalendarView = (CustomCalendarView) view.findViewById(R.id.calendar);
        customCalendarView.shouldScrollMonth(false);
        customCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        Event ev1 = new Event(getResources().getColor(R.color.primaryPink), 1489363200050L, "Some extra data that I want to store.");
        customCalendarView.addEvent(ev1);

        Event ev2= new Event(R.color.primaryPink, 1490040000000L, "Some extra data that I want to store.");
        customCalendarView.addEvent(ev2);

        Event ev3= new Event(R.color.primaryPink, 1490035000000L, "Some extra data that I want to store.");
        customCalendarView.addEvent(ev3);

        Event ev4= new Event(R.color.primaryPink, 1490034000000L, "Some extra data that I want to store.");
        customCalendarView.addEvent(ev4);

        Event ev5 = new Event(R.color.primaryPink, 1490033000000L, "Some extra data that I want to store.");
        customCalendarView.addEvent(ev5);

        List<Event> events = customCalendarView.getEvents(1489363200000L);
        Log.d(TAG, "Events: " + events);

        final TextView monthTitle = (TextView) view.findViewById(month);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
        Date date = customCalendarView.getFirstDayOfCurrentMonth();
        monthTitle.setText(dateFormat.format(date));

        customCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                List<Event> events = customCalendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthTitle.setText(dateFormat.format(firstDayOfNewMonth));
            }
        });

        Button prevButton = (Button) view.findViewById(R.id.prev_month);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customCalendarView.showPreviousMonth();
            }
        });

        Button nextButton = (Button) view.findViewById(R.id.next_month);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customCalendarView.showNextMonth();
            }
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (dListener != null) {
            dListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            dListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
