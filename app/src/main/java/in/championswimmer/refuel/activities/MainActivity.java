package in.championswimmer.refuel.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import in.championswimmer.refuel.R;
import in.championswimmer.refuel.dbhelper.RefuelDbHelper;
import in.championswimmer.refuel.uihelper.ExtendedFuelEntryWatchers;
import in.championswimmer.refuel.uihelper.RefuelEntryCardAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvHistoryContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvHistoryContainer = (RecyclerView) findViewById(R.id.refuel_history_container);
        rvHistoryContainer.setLayoutManager(new LinearLayoutManager(this));
        final RefuelEntryCardAdapter rfEntryCardAdapter = new RefuelEntryCardAdapter(RefuelDbHelper.getRefuelHistory(getApplicationContext()));
        rvHistoryContainer.setAdapter(rfEntryCardAdapter);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get LayoutInflater and inflate the dialog view.
                LayoutInflater li = LayoutInflater.from(getBaseContext());
                View dialogView = li.inflate(R.layout.dialog_add_refuel_entry, null);

                final AppCompatEditText etFuelAmt = (AppCompatEditText) dialogView.findViewById(R.id.edittext_fuel);
                final AppCompatEditText etRate = (AppCompatEditText) dialogView.findViewById(R.id.edittext_rate);
                final AppCompatEditText etMoneyAmt = (AppCompatEditText) dialogView.findViewById(R.id.edittext_money);
                final AppCompatEditText etOdometer = (AppCompatEditText) dialogView.findViewById(R.id.edittext_odometer);
                SwitchCompat swEnterRate = (SwitchCompat) dialogView.findViewById(R.id.switch_enter_rate);

                etFuelAmt.addTextChangedListener(new ExtendedFuelEntryWatchers.FuelTextWatcher(etRate, etMoneyAmt));
                etRate.addTextChangedListener(new ExtendedFuelEntryWatchers.RateTextWatcher(etFuelAmt, etMoneyAmt));
                etMoneyAmt.addTextChangedListener(new ExtendedFuelEntryWatchers.MoneyTextWatcher(etFuelAmt, etRate));
                swEnterRate.setOnCheckedChangeListener(new ExtendedFuelEntryWatchers.RateEnterCheckedChanceListener(etRate, etFuelAmt));

                //Set up the dialog using an AlertDialogBuilder
                AlertDialog.Builder adBuilder = new AlertDialog.Builder(MainActivity.this, R.style.AppTheme_AlertDialog);
                adBuilder.setView(dialogView)
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Update", null);

                final AlertDialog refuelDialog = adBuilder.create();
                refuelDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button b = refuelDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Handle submit
                                if (RefuelDbHelper.getLastRefuel(getApplicationContext()).getOdometer() > Integer.valueOf(etOdometer.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "You can't have odometer reading less than last time!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                RefuelDbHelper.addNewRefuelEntry(
                                        getApplicationContext(),
                                        Float.valueOf(etFuelAmt.getText().toString()),
                                        Float.valueOf(etRate.getText().toString()),
                                        Float.valueOf(etMoneyAmt.getText().toString()),
                                        Integer.valueOf(etOdometer.getText().toString())
                                );
                                refuelDialog.dismiss();
                                rfEntryCardAdapter.updateRefuelHistory(RefuelDbHelper.getRefuelHistory(getApplicationContext()));

                            }
                        });
                    }
                });
                refuelDialog.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
