package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

public class NewTransactionDialog extends DialogFragment {

    public NewTransactionDialog() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.new_transaction_dialog, null);

        LovelyCustomDialog dialog = new LovelyCustomDialog(getActivity());
        dialog.setView(view);
        dialog.setTitle("Add Transaction");
        dialog.setIcon(R.drawable.ic_money);
        dialog.setTopColor(ContextCompat.getColor(getActivity(), R.color.material_green));

        MaterialBetterSpinner spinner = (MaterialBetterSpinner) view.findViewById(R.id
                .category_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout
                .simple_spinner_dropdown_item, TransactionManager.categories);
        spinner.setAdapter(adapter);


        return dialog.show();

    }
}
