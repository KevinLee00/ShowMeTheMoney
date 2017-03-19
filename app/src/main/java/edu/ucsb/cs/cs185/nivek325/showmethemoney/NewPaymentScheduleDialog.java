package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Wesley on 3/18/2017.
 */

public class NewPaymentScheduleDialog extends DialogFragment {
    private String selectedTitle;
    private float selectedAmount;
    private String selectedCategory;
    private Date selectedDate;
    private String selectedDateString;
    private boolean wantToClose;

    public NewPaymentScheduleDialog() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.new_payment_dialog, null);
        builder.setView(view);

        final MaterialEditText titleEditText = (MaterialEditText) view.findViewById(R.id
                .title_edit_text_pay);
        final MaterialEditText amountEditText = (MaterialEditText) view.findViewById(R.id
                .amount_edit_text_pay);

        final MaterialBetterSpinner spinner = (MaterialBetterSpinner) view.findViewById(R.id
                .category_spinner_pay);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout
                .simple_spinner_dropdown_item, TransactionManager.categories);
        spinner.setAdapter(adapter);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Need this code to add the positive button
                // Actual code is below
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTitle = titleEditText.getText().toString();
                selectedCategory = spinner.getText().toString();
                wantToClose = (!selectedTitle.trim().equalsIgnoreCase("") && !amountEditText
                        .getText().toString().trim().equalsIgnoreCase("") && !selectedCategory
                        .equalsIgnoreCase(""));

                if (selectedTitle.trim().equalsIgnoreCase("")) {
                    Log.i("info", "title empty");
                    titleEditText.setError("Title cannot be blank");
                }
                if (amountEditText.getText().toString().trim().equalsIgnoreCase("")) {
                    amountEditText.setError("Amount cannot be blank");
                } else {
                    selectedAmount = Float.parseFloat(amountEditText.getText().toString());
                }
                if (selectedCategory.equalsIgnoreCase("")) {
                    spinner.setError("Category cannot be blank");
                }

                if (wantToClose) {
                    TransactionManager.Transaction newTransaction = new TransactionManager
                            .Transaction(selectedTitle, selectedAmount, selectedCategory,
                            selectedDate);
                    TransactionManager.addTransaction(newTransaction);
                    MainActivity.progressBarFragment.updateFragment();

                    long ldate = selectedDate.getTime();
                    Event event = new Event(getResources().getColor(R.color.primaryPink), ldate);
                    FuturePaymentsFragment.addCalendarEvent(event);

                    MediaPlayer chaching = MediaPlayer.create(getContext(), R.raw.chaching);
                    chaching.start();

                    NotificationCompat.Builder notif = new NotificationCompat.Builder(view.getContext()).setSmallIcon(R.drawable.ic_money).setContentTitle("SHOW ME");
                    Intent res = new Intent(view.getContext(),Notifier.class);
                    TaskStackBuilder builder = TaskStackBuilder.create(view.getContext());
                    builder.addParentStack(MainActivity.class);
                    builder.addNextIntent(res);
                    PendingIntent resPen = builder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                    notif.setContentIntent(resPen);
                    NotificationManager manageNotif = (NotificationManager) view.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    manageNotif.notify(0,notif.build());
                    dialog.dismiss();

                }
            }
        });


        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        final MaterialEditText dateEditText = (MaterialEditText) view.findViewById(R.id
                .date_edit_text_pay);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM d, yyyy", Locale.US);

        // If user doesn't choose a date, default to current date
        selectedDate = calendar.getTime();
        selectedDateString = dateFormat.format(selectedDate);
        dateEditText.setText(selectedDateString);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                selectedDate = calendar.getTime();
                selectedDateString = dateFormat.format(selectedDate);
                dateEditText.setText(selectedDateString);
            }
        };

        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), listener, year,
                month, day);

        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(calendar.getTimeInMillis());

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        return dialog;

    }
}
