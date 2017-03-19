package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.NumberFormat;


public class SetBudgetDialog extends DialogFragment {

    private String category;

    public SetBudgetDialog(){ }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.set_budget_dialog, null);
        builder.setView(view);

        final ImageView image = (ImageView) view.findViewById(R.id.image);
        final MaterialEditText editText = (MaterialEditText) view.findViewById(R.id.budget_edit_text);
        final LinearLayout colorBar = (LinearLayout) view.findViewById(R.id.color_bar);
        final TextView title = (TextView) view.findViewById(R.id.dialog_title);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            category = bundle.getString("category");
            switch (category) {
                case "Food":
                    image.setImageDrawable(getResources().getDrawable(R.drawable.food_icon));
                    colorBar.setBackgroundColor(getResources().getColor(R.color.primaryOrange));
                    title.setText(R.string.food_budget);
                    break;
                case "Entertainment":
                    image.setImageDrawable(getResources().getDrawable(R.drawable.entertainment_icon));
                    colorBar.setBackgroundColor(getResources().getColor(R.color.primaryPink));
                    title.setText(R.string.entertainment_budget);
                    break;
                case "Living":
                    image.setImageDrawable(getResources().getDrawable(R.drawable.house_icon));
                    colorBar.setBackgroundColor(getResources().getColor(R.color.material_light_blue));
                    title.setText(R.string.living_budget);
                    break;
                case "Other":
                    image.setImageDrawable(getResources().getDrawable(R.drawable.other_icon));
                    colorBar.setBackgroundColor(getResources().getColor(R.color.primaryPurple));
                    title.setText(R.string.other_budget);
                    break;
            }
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String maxBudget = editText.getText().toString();
                final Float budget = Float.parseFloat(maxBudget);

                switch (category) {
                    case "Food":
                        BudgetManager.setFoodBudget(budget);
                        break;
                    case "Entertainment":
                        BudgetManager.setEntertainmentBudget(budget);
                        break;
                    case "Living":
                        BudgetManager.setLivingBudget(budget);
                        break;
                    case "Other":
                        BudgetManager.setOtherBudget(budget);
                        break;
                }
                MainActivity.progressBarFragment.updateFragment();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        Dialog dialog = builder.create();
        return dialog;
    }
}
