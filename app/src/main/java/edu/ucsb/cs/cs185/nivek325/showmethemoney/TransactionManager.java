package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {

    public static final List<Transaction> ITEMS = new ArrayList<>();
    public static final List<String> categories = new ArrayList<>();
    public static final List<Integer> colors = new ArrayList<>();
    private static OnAddTransactionListener listener;

    public static void addTransaction(Transaction transaction) {
        ITEMS.add(0, transaction);
        listener.onAddTransaction();
    }

    public static void setOnAddTransactionListener(OnAddTransactionListener listener) {
        TransactionManager.listener = listener;
    }

    static {
        for (int i = 1; i <= 5; i++) {
            switch (i) {
                case 1: case 2:
                    ITEMS.add(new Transaction("Food", 500 * i, "Food", 0));
                    break;
                case 3: case 4:
                    ITEMS.add(new Transaction("Entertainment", 500 * i, "Entertainment", 1));
                    break;
                default:
                    ITEMS.add(new Transaction("Living Expenses", 500 * i, "Living Expenses", 2));
            }

        }
        categories.add("Food");
        categories.add("Entertainment");
        categories.add("Living Expenses");

        colors.add(0);
        colors.add(1);
        colors.add(2);
    }

    public static class Transaction {
        private final String title;
        private final double amount;
        private final String category;
        private final int color;

        public Transaction(String title, double amount, String category, int color) {
            this.title = title;
            this.amount = amount;
            this.category = category;
            this.color = color;
        }

        public String getTitle() {
            return title;
        }

        public double getAmount() {
            return amount;
        }

        public String getCategory() { return category; }

        public int getColor() { return color; }
    }

    public interface OnAddTransactionListener {
        void onAddTransaction();
    }
}