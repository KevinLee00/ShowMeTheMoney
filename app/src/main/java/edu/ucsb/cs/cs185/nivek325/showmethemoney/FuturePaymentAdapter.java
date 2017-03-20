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

public class FuturePaymentAdapter extends BaseAdapter {
    private Context context;

    public FuturePaymentAdapter(Context context) {
        super();
        this.context = context;
        PaymentManager.setListener(new PaymentManager.OnAddPaymentListener() {
            @Override
            public void onAddPayment() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        return PaymentManager.events.size();
    }

    @Override
    public Object getItem(int i) {
        return PaymentManager.events.get(i).getData();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        PaymentManager.Payment payment = (PaymentManager.Payment) PaymentManager.events.get(i).getData();
        if (view == null) {
            v = View.inflate(context, R.layout.fragment_transaction, null);
        } else {
            v = view;
        }

        TextView title = (TextView) v.findViewById(R.id.title);
        TextView amount = (TextView) v.findViewById(R.id.amount);
        TextView date = (TextView) v.findViewById(R.id.date);

        title.setText(payment.getTitle());
        amount.setText(String.format(Locale.US, "$%.2f", payment.getAmount()));

        final SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM d, yyyy", Locale.US);

        date.setText(dateFormat.format(payment.getDate()));

        ImageView iconFrame = (ImageView) v.findViewById(R.id.circle);
        ImageView icon = (ImageView) v.findViewById(R.id.icon);
        if (payment.getColor() == 0) {
            iconFrame.setColorFilter(ContextCompat.getColor(context, R.color.primaryOrange));
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.food_icon));
        }
        else if (payment.getColor() == 1) {
            iconFrame.setColorFilter(ContextCompat.getColor(context, R.color.primaryPink));
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.entertainment_icon));
        }
        else if (payment.getColor() == 2) {
            iconFrame.setColorFilter(ContextCompat.getColor(context, R.color.material_light_blue));
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.house_icon));
        }
        else {
            iconFrame.setColorFilter(ContextCompat.getColor(context, R.color.primaryPurple));
            icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.other_icon));
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
