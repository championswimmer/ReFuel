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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

import in.championswimmer.refuel.R;
import in.championswimmer.refuel.dbhelper.RefuelDbHelper;
import in.championswimmer.refuel.uihelper.ExtendedFuelEntryWatchers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
//            getSupportActionBar().setTitle(null);
//            getSupportActionBar().setCustomView(R.layout.titlebar_main_collapsible);
//            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        } catch (NullPointerException e) {
            //Do something with the exception
        }

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
                Switch swEnterRate = (Switch) dialogView.findViewById(R.id.switch_enter_rate);

                etFuelAmt.addTextChangedListener(new ExtendedFuelEntryWatchers.FuelTextWatcher(etRate, etMoneyAmt));
                etRate.addTextChangedListener(new ExtendedFuelEntryWatchers.RateTextWatcher(etFuelAmt, etMoneyAmt));
                etMoneyAmt.addTextChangedListener(new ExtendedFuelEntryWatchers.MoneyTextWatcher(etFuelAmt, etRate));
                swEnterRate.setOnCheckedChangeListener(new ExtendedFuelEntryWatchers.RateEnterCheckedChanceListener(etRate, etFuelAmt));

                //Set up the dialog using an AlertDialogBuilder
                AlertDialog.Builder adBuilder = new AlertDialog.Builder(MainActivity.this, R.style.AppTheme_AlertDialog);
                adBuilder.setView(dialogView);


                adBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                adBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle submit
                        RefuelDbHelper.addNewRefuelEntry(
                                getApplicationContext(),
                                Float.valueOf(etFuelAmt.getText().toString()),
                                Float.valueOf(etRate.getText().toString()),
                                Float.valueOf(etMoneyAmt.getText().toString()),
                                Integer.valueOf(etOdometer.getText().toString())
                                );
                    }
                });

                AlertDialog refuelDialog = adBuilder.create();
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
