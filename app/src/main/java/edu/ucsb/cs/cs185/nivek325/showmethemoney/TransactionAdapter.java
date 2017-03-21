package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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
        amount.setText(String.format(Locale.US, "$%.2f", transaction.getAmount()));

        final SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM d, yyyy", Locale.US);

        date.setText(dateFormat.format(transaction.getDate()));

        ImageView iconFrame = (ImageView) v.findViewById(R.id.circle);
        ImageView icon = (ImageView) v.findViewById(R.id.icon);
        if (transaction.getColor() == 0) {
            iconFrame.setColorFilter(ContextCompat.getColor(context, R.color.primaryOrange));
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.food_icon));
        }
        else if (transaction.getColor() == 1) {
            iconFrame.setColorFilter(ContextCompat.getColor(context, R.color.primaryPink));
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.entertainment_icon));
        }
        else if (transaction.getColor() == 2) {
            iconFrame.setColorFilter(ContextCompat.getColor(context, R.color.material_light_blue));
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.house_icon));
        }
        else {
            iconFrame.setColorFilter(ContextCompat.getColor(context, R.color.primaryPurple));
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.other_icon));
        }

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) icon
                .getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        icon.setLayoutParams(layoutParams);

        layoutParams = (RelativeLayout.LayoutParams) iconFrame
                .getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        iconFrame.setLayoutParams(layoutParams);

        return v;
    }
}
