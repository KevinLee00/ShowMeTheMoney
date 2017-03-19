package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.text.NumberFormat;

public class MainProgressBarFragment extends Fragment {
    // For the sentences
    private TextView amtLeft;
    private TextView perLeft;

    // Main bar labels
    private TextView amountSpentLabel;
    private TextView amountTotalLabel;

    // Detailed bars
    private RoundCornerProgressBar mainBar;
    private RoundCornerProgressBar totalBar;
    private RoundCornerProgressBar foodBar;
    private RoundCornerProgressBar entertainmentBar;
    private RoundCornerProgressBar livingBar;
    private RoundCornerProgressBar otherBar;

    // Detailed bar labels
    private TextView spentTotal;
    private TextView allottedTotal;
    private TextView spentFood;
    private TextView allottedFood;
    private TextView spentEntertainment;
    private TextView allottedEntertainment;
    private TextView spentLiving;
    private TextView allottedLiving;
    private TextView spentOther;
    private TextView allottedOther;

    private float spentOnFood = 0.0f;
    private float spentOnEntertainment = 0.0f;
    private float spentOnLivingExpenses = 0.0f;
    private float spentOnOtherCosts = 0.0f;
    private float totalSpent = 0.0f;

    private float allottedForFood = BudgetManager.getFoodBudget();
    private float allottedForEntertainment = BudgetManager.getEntertainmentBudget();
    private float allottedForLivingExpenses = BudgetManager.getLivingBudget();
    private float allottedForOtherCosts = BudgetManager.getOtherBudget();
    private float totalAllotted;

    private String HTMLOpenGreenTag = "<b><font color='#20CD5F'>";
    private String HTMLOpenYellowTag = "<b><font color='#ffc107'>";
    private String HTMLOpenRedTag = "<b><font color='#d50000'>";
    private String HTMLEndTag = "</font></b>";

    public MainProgressBarFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_up, container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.swipe_layout);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    return true;
                }
                return false;
            }
        });

        totalAllotted = allottedForFood + allottedForEntertainment + allottedForLivingExpenses + allottedForOtherCosts;
        updateAmountSpent();

        // The green text views in the two sentences.
        amtLeft = (TextView) view.findViewById(R.id.amtSpent);
        perLeft = (TextView) view.findViewById(R.id.perSpent);

        // The bar label under the main progress bar
        amountSpentLabel = (TextView) view.findViewById(R.id.totalSpentText);
        amountTotalLabel = (TextView) view.findViewById(R.id.maxBudgetText);
        mainBar = (RoundCornerProgressBar) view.findViewById(R.id.main_progress_bar);

        // Detailed Bars (swipe up)
        totalBar = (RoundCornerProgressBar) view.findViewById(R.id.progress_detail_1);
        foodBar = (RoundCornerProgressBar) view.findViewById(R.id.progress_detail_2);
        entertainmentBar = (RoundCornerProgressBar) view.findViewById(R.id.progress_detail_3);
        livingBar = (RoundCornerProgressBar) view.findViewById(R.id.progress_detail_4);
        otherBar = (RoundCornerProgressBar) view.findViewById(R.id.progress_detail_5);

        spentTotal = (TextView) view.findViewById(R.id.category_spent_0);
        allottedTotal = (TextView) view.findViewById(R.id.category_total_0);

        spentFood = (TextView) view.findViewById(R.id.category_spent_1);
        allottedFood = (TextView) view.findViewById(R.id.category_total_1);

        spentEntertainment = (TextView) view.findViewById(R.id.category_spent_2);
        allottedEntertainment = (TextView) view.findViewById(R.id.category_total_2);

        spentLiving = (TextView) view.findViewById(R.id.category_spent_3);
        allottedLiving = (TextView) view.findViewById(R.id.category_total_3);

        spentOther = (TextView) view.findViewById(R.id.category_spent_4);
        allottedOther = (TextView) view.findViewById(R.id.category_total_4);


        foodBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category", "Food");
                SetBudgetDialog setBudgetDialog = new SetBudgetDialog();
                setBudgetDialog.setArguments(bundle);
                setBudgetDialog.show(getActivity().getFragmentManager(), "food");
            }
        });

        entertainmentBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category", "Entertainment");
                SetBudgetDialog setBudgetDialog = new SetBudgetDialog();
                setBudgetDialog.setArguments(bundle);
                setBudgetDialog.show(getActivity().getFragmentManager(), "entertainment");
            }
        });

        livingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("category", "Living");
                SetBudgetDialog setBudgetDialog = new SetBudgetDialog();
                setBudgetDialog.setArguments(bundle);
                setBudgetDialog.show(getActivity().getFragmentManager(), "living");
            }
        });

        otherBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("category", "Other");
                SetBudgetDialog setBudgetDialog = new SetBudgetDialog();
                setBudgetDialog.setArguments(bundle);
                setBudgetDialog.show(getActivity().getFragmentManager(), "other");
            }
        });

        updateFragment();
        return view;
    }

    void updateBudget() {
        allottedForFood = BudgetManager.getFoodBudget();
        allottedForEntertainment = BudgetManager.getEntertainmentBudget();
        allottedForLivingExpenses = BudgetManager.getLivingBudget();
        allottedForOtherCosts = BudgetManager.getOtherBudget();
        totalAllotted = allottedForFood + allottedForEntertainment + allottedForLivingExpenses + allottedForOtherCosts;
    }

    void updateAmountSpent() {
        spentOnFood = 0.0f;
        spentOnEntertainment = 0.0f;
        spentOnLivingExpenses = 0.0f;
        spentOnOtherCosts = 0.0f;
        totalSpent = 0.0f;

        for (int i=0; i<TransactionManager.ITEMS.size(); i++) {
            String category = TransactionManager.ITEMS.get(i).getCategory();
            float amount = TransactionManager.ITEMS.get(i).getAmount();

            totalSpent += amount;
            switch(category) {
                case ("Food"):
                    spentOnFood += amount;
                    break;
                case ("Entertainment"):
                    spentOnEntertainment += amount;
                    break;

                case ("Living Expenses"):
                    spentOnLivingExpenses += amount;
                    break;

                case ("Other Cost"):
                    spentOnOtherCosts += amount;
                    break;
            }
        }
    }

    void updateFragment() {
        updateAmountSpent();
        updateBudget();

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        NumberFormat pf = NumberFormat.getPercentInstance();

        float percentageSpent = (totalSpent / totalAllotted);

        if (totalSpent > 0.70*totalAllotted && totalSpent < totalAllotted) {
            amtLeft.setText(Html.fromHtml("Careful! You have " + HTMLOpenYellowTag + nf.format(totalAllotted - totalSpent) + HTMLEndTag+ " left to spend."));
            perLeft.setText(Html.fromHtml("You've used " + HTMLOpenYellowTag + pf.format(percentageSpent) + HTMLEndTag+ " of your monthly budget."));
            amountTotalLabel.setTextColor(getResources().getColor(R.color.material_amber));
            mainBar.setProgressColor(getResources().getColor(R.color.material_amber));
            totalBar.setProgressColor(getResources().getColor(R.color.material_amber));
            allottedTotal.setTextColor(getResources().getColor(R.color.material_amber));


//            allottedFood.setTextColor(getResources().getColor(R.color.material_amber));
//            allottedEntertainment.setTextColor(getResources().getColor(R.color.material_amber));
//            allottedLiving.setTextColor(getResources().getColor(R.color.material_amber));
//            allottedOther.setTextColor(getResources().getColor(R.color.material_amber));
        }

        else if (totalSpent > totalAllotted) {
            amtLeft.setText(Html.fromHtml("Oh no! You are " + HTMLOpenRedTag + nf.format(totalSpent - totalAllotted) + HTMLEndTag + " over budget!"));
            perLeft.setText(Html.fromHtml("You've used " + HTMLOpenRedTag + pf.format(percentageSpent) + HTMLEndTag+ " of your monthly budget."));
            amountTotalLabel.setTextColor(getResources().getColor(R.color.material_red));
            mainBar.setProgressColor(getResources().getColor(R.color.material_red));
            totalBar.setProgressColor(getResources().getColor(R.color.material_red));
            allottedTotal.setTextColor(getResources().getColor(R.color.material_red));



        }
        else {
            amtLeft.setText(Html.fromHtml("You have " + HTMLOpenGreenTag + nf.format(totalAllotted - totalSpent) + HTMLEndTag+ " left to spend."));
            perLeft.setText(Html.fromHtml("You've used " + HTMLOpenGreenTag + pf.format(percentageSpent) + HTMLEndTag+ " of your monthly budget."));

            amountTotalLabel.setTextColor(getResources().getColor(R.color.black_text));
            allottedTotal.setTextColor(getResources().getColor(R.color.black_text));
        }

        amountSpentLabel.setText(String.valueOf(nf.format(totalSpent)) + " of ");
        amountSpentLabel.invalidate();

        amountTotalLabel.setText(nf.format(totalAllotted));
        amountTotalLabel.invalidate();

        mainBar.setMax(totalAllotted);
        mainBar.setProgress(totalSpent);
        mainBar.invalidate();

        spentTotal.setText(nf.format(totalSpent) + " of ");
        allottedTotal.setText(nf.format(totalAllotted));
        totalBar.setMax(totalAllotted);
        totalBar.setProgress(totalSpent);
        totalBar.invalidate();

        spentFood.setText(nf.format(spentOnFood) + " of ");
        allottedFood.setText(nf.format(allottedForFood));
        foodBar.setMax(allottedForFood);
        foodBar.setProgress(spentOnFood);
        if (spentOnFood > allottedForFood) {
            foodBar.setProgressColor(Color.parseColor("#d50000"));
            allottedFood.setTextColor(getResources().getColor(R.color.material_red));
        } else {
            foodBar.setProgressColor(getResources().getColor(R.color.primaryOrange));
            allottedFood.setTextColor(getResources().getColor(R.color.black_text));
        }
        foodBar.invalidate();

        spentEntertainment.setText(nf.format(spentOnEntertainment) + " of ");
        allottedEntertainment.setText(nf.format(allottedForEntertainment));
        entertainmentBar.setMax(allottedForEntertainment);
        entertainmentBar.setProgress(spentOnEntertainment);
        if (spentOnEntertainment > allottedForEntertainment) {
            entertainmentBar.setProgressColor(Color.parseColor("#d50000"));
            allottedEntertainment.setTextColor(getResources().getColor(R.color.material_red));

        } else {
            entertainmentBar.setProgressColor(getResources().getColor(R.color.primaryPink));
            allottedEntertainment.setTextColor(getResources().getColor(R.color.black_text));
        }
        entertainmentBar.invalidate();

        spentLiving.setText(nf.format(spentOnLivingExpenses) + " of ");
        allottedLiving.setText(nf.format(allottedForLivingExpenses));
        livingBar.setMax(allottedForLivingExpenses);
        livingBar.setProgress(spentOnLivingExpenses);
        if (spentOnLivingExpenses > allottedForLivingExpenses) {
            livingBar.setProgressColor(Color.parseColor("#d50000"));
            allottedLiving.setTextColor(getResources().getColor(R.color.material_red));
        } else {
            livingBar.setProgressColor(getResources().getColor(R.color.material_light_blue));
            allottedLiving.setTextColor(getResources().getColor(R.color.black_text));
        }
        livingBar.invalidate();


        spentOther.setText(nf.format(spentOnOtherCosts) + " of ");
        allottedOther.setText(nf.format(allottedForOtherCosts));
        otherBar.setMax(allottedForOtherCosts);
        otherBar.setProgress(spentOnOtherCosts);
        if (spentOnOtherCosts > allottedForOtherCosts) {
            otherBar.setProgressColor(Color.parseColor("#d50000"));
            allottedOther.setTextColor(getResources().getColor(R.color.material_red));
        } else {
            otherBar.setProgressColor(getResources().getColor(R.color.primaryPurple));
            allottedOther.setTextColor(getResources().getColor(R.color.black_text));
        }
        otherBar.invalidate();
    }
}
