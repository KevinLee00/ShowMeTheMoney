package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
    private FuturePaymentAdapter futurePaymentAdapter;
    private static CustomCalendarView customCalendarView;
    public static Date currentDate;

    public FuturePaymentsFragment() {
        // Required empty public constructor
    }

    public void setFuturePaymentAdapter(FuturePaymentAdapter futurePaymentAdapter) {
        this.futurePaymentAdapter = futurePaymentAdapter;
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

    public static void addCalendarEvent(Event e) { customCalendarView.addEvent(e, true); }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_future_payments, container, false);
        ListView listView = (ListView) view.findViewById(R.id.future_list);
        listView.setAdapter(futurePaymentAdapter);

        customCalendarView = (CustomCalendarView) view.findViewById(R.id.calendar);
        customCalendarView.shouldScrollMonth(false);
        customCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        PaymentManager.Payment rentPayment = new PaymentManager.Payment("Rent", 800, "Living " +
                "Expenses", new Date(1491030000000L));
        PaymentManager.Payment bribePayment = new PaymentManager.Payment("Hollerer Bribe", 1,
                "Other Costs", new Date(1490079600000L));
        PaymentManager.Payment alaskaPayment = new PaymentManager.Payment("Alaska Trip", 1800,
                "Entertainment", new Date(1490425200000L));

        Event rent = new Event(ContextCompat.getColor(getActivity(), R.color.material_light_blue)
                , 1491030000000L, rentPayment);
        Event bribe = new Event(ContextCompat.getColor(getActivity(), R.color.primaryPurple),
                1490079600000L, bribePayment);
        Event alaska = new Event(ContextCompat.getColor(getActivity(), R.color.primaryPink),
                1490425200000L, alaskaPayment);

        customCalendarView.addEvent(rent, false);
        customCalendarView.addEvent(bribe, false);
        customCalendarView.addEvent(alaska, true);

        final TextView monthTitle = (TextView) view.findViewById(month);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
        Date date = customCalendarView.getFirstDayOfCurrentMonth();
        monthTitle.setText(dateFormat.format(date));

        Calendar calendar = Calendar.getInstance(Locale.US);
        currentDate = calendar.getTime();

        customCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                List<Event> events = customCalendarView.getEvents(dateClicked);
                PaymentManager.updateEvents(events);
                currentDate = dateClicked;
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
