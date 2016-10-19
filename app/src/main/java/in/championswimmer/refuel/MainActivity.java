package in.championswimmer.refuel;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.championswimmer.refuel.db.AppDb;
import in.championswimmer.refuel.db.RefuelTable;
import in.championswimmer.refuel.models.Expense;
import in.championswimmer.refuel.models.Refuel;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainAct";
    public static final String NEXT = "Next";


    Refuel refuelToBeAdded;
    Expense expenseToBeAdded;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                odometerDialog();

            }
        });


    }

    public void init() {

        refuelToBeAdded = new Refuel();
        expenseToBeAdded = new Expense();
        db = AppDb.getInstance(this).getWritableDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void odometerDialog() {

        final EditText editText = new EditText(this);

        new AlertDialog.Builder(this).setTitle("Odometer").setMessage("Enter Odometer reading : ").setView(editText)
                .setNeutralButton(NEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refuelToBeAdded.setOdometer(Double.valueOf(editText.getText().toString()));
                        rateDialog();
                    }
                }).show();
    }

    public void rateDialog() {
        final EditText editText = new EditText(this);

        new AlertDialog.Builder(this).setTitle("Rate").setMessage("Enter Rate : ").setView(editText)
                .setNeutralButton(NEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refuelToBeAdded.setRate(Double.valueOf(editText.getText().toString()));
                        litreDialog();
                    }
                }).show();
    }

    public void litreDialog() {

        final EditText editText = new EditText(this);

        new AlertDialog.Builder(this).setTitle("Litre").setMessage("Enter Litres : ").setView(editText)
                .setNeutralButton(NEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refuelToBeAdded.setLitre(Double.valueOf(editText.getText().toString()));
                        pumpDialog();
                    }
                }).show();
    }

    public void pumpDialog() {

        final EditText editText = new EditText(this);

        new AlertDialog.Builder(this).setTitle("Pump").setMessage("Enter Name of the pump : ").setView(editText)
                .setNeutralButton(NEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refuelToBeAdded.setPumpName(String.valueOf(editText.getText().toString()));
                        fullTankDialog();
                    }
                }).show();
    }

    public void fullTankDialog() {

        final CheckBox checkBox = new CheckBox(this);


        new AlertDialog.Builder(this).setTitle("Full Tank").setMessage("Did you get full tank? ").setView(checkBox)
                .setNeutralButton(NEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refuelToBeAdded.setFullTank(checkBox.isChecked());
                        amountDialog();
                    }
                }).show();
    }

    public void amountDialog() {

        final EditText editText = new EditText(this);

        new AlertDialog.Builder(this).setTitle("Amount").setMessage("Enter Amount paid : ").setView(editText)
                .setNeutralButton(NEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        expenseToBeAdded.setAmount(Double.valueOf(editText.getText().toString()));
                        timeStampDialog();
                    }
                }).show();
    }

    public void timeStampDialog() {

        final EditText editText = new EditText(this);

        new AlertDialog.Builder(this).setTitle("Time Stamp").setMessage("Enter Time stamp : ").setView(editText)
                .setNeutralButton(NEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        expenseToBeAdded.setTimestamp(Long.valueOf(editText.getText().toString()));
                        descDialog();
                    }
                }).show();
    }

    public void descDialog() {

        final EditText editText = new EditText(this);

        new AlertDialog.Builder(this).setTitle("Description").setMessage("Enter Description : ").setView(editText)
                .setNeutralButton(NEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        expenseToBeAdded.setDesc(String.valueOf(editText.getText().toString()));
                        typeDialog();
                    }
                }).show();
    }

    public void typeDialog() {

        final EditText editText = new EditText(this);

        new AlertDialog.Builder(this).setTitle("Type").setMessage("Enter Type : ").setView(editText)
                .setNeutralButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        expenseToBeAdded.setType(String.valueOf(editText.getText().toString()));
                        refuelToBeAdded.setExpense(expenseToBeAdded);
                        add();
                        Toast.makeText(MainActivity.this, "Entry Recorded", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    public void add() {

        db.beginTransaction();
        RefuelTable.save(db, refuelToBeAdded);
        db.setTransactionSuccessful();
        db.endTransaction();


        db.beginTransaction();
        refuelToBeAdded = RefuelTable.getById(db, 1);
        ((TextView) findViewById(R.id.hellotext)).setText(refuelToBeAdded.toString());
        db.setTransactionSuccessful();
        db.endTransaction();
    }

}
