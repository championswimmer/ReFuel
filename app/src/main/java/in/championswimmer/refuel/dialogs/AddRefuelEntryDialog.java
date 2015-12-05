package in.championswimmer.refuel.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import in.championswimmer.refuel.R;
import in.championswimmer.refuel.dbhelper.RefuelDbHelper;
import in.championswimmer.refuel.uihelper.ExtendedFuelEntryWatchers;

/**
 * Created by championswimmer on 30/11/15.
 */
public class AddRefuelEntryDialog {

    public interface RefuelUpdateListener {
        public void onRefuelUpdate();
    }


    public static AlertDialog newInstance (final Context ctx, final RefuelUpdateListener rul) {
        LayoutInflater li = LayoutInflater.from(ctx);
        View dialogView = li.inflate(R.layout.dialog_add_refuel_entry, null);

        final AppCompatEditText etFuelAmt = (AppCompatEditText) dialogView.findViewById(R.id.edittext_fuel);
        final AppCompatEditText etRate = (AppCompatEditText) dialogView.findViewById(R.id.edittext_rate);
        final AppCompatEditText etMoneyAmt = (AppCompatEditText) dialogView.findViewById(R.id.edittext_money);
        final AppCompatEditText etOdometer = (AppCompatEditText) dialogView.findViewById(R.id.edittext_odometer);
        final SwitchCompat swEnterRate = (SwitchCompat) dialogView.findViewById(R.id.switch_enter_rate);

        etFuelAmt.addTextChangedListener(new ExtendedFuelEntryWatchers.FuelTextWatcher(etRate, etMoneyAmt));
        etRate.addTextChangedListener(new ExtendedFuelEntryWatchers.RateTextWatcher(etFuelAmt, etMoneyAmt));
        etMoneyAmt.addTextChangedListener(new ExtendedFuelEntryWatchers.MoneyTextWatcher(etFuelAmt, etRate));
        swEnterRate.setOnCheckedChangeListener(new ExtendedFuelEntryWatchers.RateEnterCheckedChanceListener(etRate, etFuelAmt));

        AlertDialog.Builder adBuilder = new AlertDialog.Builder(ctx, R.style.AppTheme_AlertDialog);
        adBuilder.setView(dialogView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Update", null);
        final AlertDialog areDialog = adBuilder.create();
        areDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button b = areDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Handle submit
                        if (RefuelDbHelper.getLastRefuel(ctx.getApplicationContext()).getOdometer() > Integer.valueOf(etOdometer.getText().toString())) {
                            Toast.makeText(ctx, "You can't have odometer reading less than last time!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        RefuelDbHelper.addNewRefuelEntry(
                                ctx.getApplicationContext(),
                                Float.valueOf(etFuelAmt.getText().toString()),
                                Float.valueOf(etRate.getText().toString()),
                                Float.valueOf(etMoneyAmt.getText().toString()),
                                Integer.valueOf(etOdometer.getText().toString())
                        );
                        areDialog.dismiss();
                        rul.onRefuelUpdate();
                    }
                });
            }
        });

        return areDialog;

    }

}
