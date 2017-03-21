package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.util.Log;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PaymentManager {
    public static List<Payment> payments = new ArrayList<>();
    public static List<Event> events = new ArrayList<>();
    private static OnAddPaymentListener listener;


    public static void setListener(OnAddPaymentListener listener) {
        PaymentManager.listener = listener;
    }

    public static void addPayment(Payment payment) {
        PaymentManager.payments.add(payment);
    }

    public static void addEvent(Event event, Date date) {
        PaymentManager.events.add(event);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d yyyy", Locale.US);
        String currentDate = dateFormat.format(FuturePaymentsFragment.currentDate);
        String passedDate = dateFormat.format(date);
        Log.i("info", "currentDate = " + currentDate);
        Log.i("info", "passedDate = " + passedDate);
        if (currentDate.equals(passedDate)) {
            listener.onAddPayment();
        }
    }

    public static void updateEvents(List<Event> events) {
        PaymentManager.events.clear();
        PaymentManager.events.addAll(events);
        Collections.sort(PaymentManager.events, new Comparator<Event>() {
            @Override
            public int compare(Event event1, Event event2) {
                Payment payment1 = (Payment) event1.getData();
                Payment payment2 = (Payment) event2.getData();
                return Float.compare(payment1.getAmount(), payment2.getAmount()) * -1;
            }
        });
        listener.onAddPayment();
    }

    public static class Payment {
        private final String title;
        private final float amount;
        private final String category;
        private final int color;
        private final Date date;
        private static int count = 0;
        private final int id;

        public Payment(String title, float amount, String category, Date date) {
            count = count +1;
            this.id=count;
            this.title = title;
            this.amount = amount;
            this.category = category;
            if (category.equals("Food"))
                this.color = 0;
            else if (category.equals("Entertainment"))
                this.color = 1;
            else if (category.equals("Living Expenses"))
                this.color = 2;
            else
                this.color = 3;
            this.date = date;
        }
        public static int getId(Payment p){return p.id;}

        public String getTitle() {
            return title;
        }

        public float getAmount() {
            return amount;
        }

        public String getCategory() {
            return category;
        }

        public int getColor() {
            return color;
        }

        public Date getDate() {
            return date;
        }
    }

    public interface OnAddPaymentListener {
        void onAddPayment();
    }
}
