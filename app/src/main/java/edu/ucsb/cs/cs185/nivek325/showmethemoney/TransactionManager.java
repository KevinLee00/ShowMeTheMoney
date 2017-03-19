package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TransactionManager {

    public static final List<Transaction> ITEMS = new ArrayList<>();
    public static final List<String> categories = new ArrayList<>();
    public static final List<Integer> colors = new ArrayList<>();
    private static OnAddTransactionListener listener;

    public static void addTransaction(Transaction transaction) {
        ITEMS.add(0, transaction);
        Collections.sort(ITEMS, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction transaction, Transaction t1) {
                return transaction.getDate().compareTo(t1.getDate()) * -1;
            }
        });
        listener.onAddTransaction();
    }

    public static void setOnAddTransactionListener(OnAddTransactionListener listener) {
        TransactionManager.listener = listener;
    }

    static {
        for (int i = 1; i <= 5; i++) {
            switch (i) {
                case 1:
                case 2:
                    ITEMS.add(new Transaction("Food", 500 * i, "Food", new Date(1489722963000L)));
                    break;
                case 3:
                case 4:
                    ITEMS.add(new Transaction("Entertainment", 500 * i, "Entertainment", new Date
                            (1489636563000L)));
                    break;
                default:
                    ITEMS.add(new Transaction("Living Expenses", 500 * i, "Living Expenses", new
                            Date(1489377363000L)));
            }
        }
        categories.add("Food");
        categories.add("Entertainment");
        categories.add("Living Expenses");
        categories.add("Other Cost");

        colors.add(0);
        colors.add(1);
        colors.add(2);
        colors.add(3);
    }

    public static class Transaction {
        private final String title;
        private final float amount;
        private final String category;
        private final int color;
        private final Date date;

        public Transaction(String title, float amount, String category, Date date) {
            this.title = title;
            this.amount = amount;
            this.category = category;
            this.date = date;
            if (category.equals("Food"))
                this.color = 0;
            else if (category.equals("Entertainment"))
                this.color = 1;
            else if (category.equals("Living Expenses"))
                this.color = 2;
            else
                this.color = 3;
        }

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

    public interface OnAddTransactionListener {
        void onAddTransaction();
    }
}