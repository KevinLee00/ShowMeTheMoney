package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {

    public static final List<Transaction> ITEMS = new ArrayList<>();

    public static void addTransaction(Transaction transaction) {
        ITEMS.add(transaction);
    }

    static {
        for (int i = 1; i <= 5; i++) {
            ITEMS.add(new Transaction("Transaction " + i, 500*i));
        }
    }

    public static class Transaction {
        public final String name;
        public final double amount;

        public Transaction(String name, double amount) {
            this.name = name;
            this.amount = amount;
        }

    }
}