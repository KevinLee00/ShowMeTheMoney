package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

public class TransactionAdapter extends BaseAdapter {
    private Context context;

    public TransactionAdapter(Context context) {
        super();
        this.context = context;
        TransactionManager.setOnAddTransactionListener(new TransactionManager.OnAddTransactionListener() {
            @Override
            public void onAddTransaction() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        return TransactionManager.ITEMS.size();
    }

    @Override
    public Object getItem(int i) {
        return TransactionManager.ITEMS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        TransactionManager.Transaction transaction = TransactionManager.ITEMS.get(i);
        if (view == null) {
            v = View.inflate(context, R.layout.fragment_transaction, null);
        } else {
            v = view;
        }

        TextView title = (TextView) v.findViewById(R.id.title);
        TextView amount = (TextView) v.findViewById(R.id.amount);
        TextView date = (TextView) v.findViewById(R.id.date);

        title.setText(transaction.getTitle());
        amount.setText(String.format(Locale.US, "$%.1f", transaction.getAmount()));
        date.setText(transaction.getDate());

        ImageView icon = (ImageView) v.findViewById(R.id.circle);
        if (transaction.getColor() == 0)
            icon.setColorFilter(ContextCompat.getColor(context, R.color.primaryOrange));
        else if (transaction.getColor() == 1)
            icon.setColorFilter(ContextCompat.getColor(context, R.color.primaryPink));
        else if (transaction.getColor() == 2)
            icon.setColorFilter(ContextCompat.getColor(context, R.color.material_light_blue));
        else
            icon.setColorFilter(ContextCompat.getColor(context, R.color.primaryPurple));

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) icon
                .getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        icon.setLayoutParams(layoutParams);

        return v;
    }
}
