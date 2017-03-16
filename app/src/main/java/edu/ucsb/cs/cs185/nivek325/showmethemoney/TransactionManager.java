package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {

    public static final List<Transaction> ITEMS = new ArrayList<>(10);
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
            ITEMS.add(new Transaction("Transaction " + i, 500*i));
        }
    }

    public static class Transaction {
        private final String title;
        private final double amount;

        public Transaction(String title, double amount) {
            this.title = title;
            this.amount = amount;
        }

        public String getTitle() {
            return title;
        }

        public double getAmount() {
            return amount;
        }
    }

    public interface OnAddTransactionListener {
        void onAddTransaction();
    }
}