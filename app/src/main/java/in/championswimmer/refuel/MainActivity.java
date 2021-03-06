package in.championswimmer.refuel;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import in.championswimmer.refuel.db.AppDb;
import in.championswimmer.refuel.db.RefuelTable;
import in.championswimmer.refuel.models.Expense;
import in.championswimmer.refuel.models.Refuel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SQLiteDatabase db = AppDb.getInstance(this).getWritableDatabase();

        Refuel rf = new Refuel(7688.1, 66.1, 10.45, "Rohini Delhi", false,
                new Expense(450.5, 1203970, "Refuel at Rohini", "refuel"));

        db.beginTransaction();
        RefuelTable.save(db,rf);
        db.setTransactionSuccessful();
        db.endTransaction();


        db.beginTransaction();
        rf = RefuelTable.getById(db, 1);
        ((TextView) findViewById(R.id.hellotext)).setText(rf.getPumpName());
        db.setTransactionSuccessful();
        db.endTransaction();


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
}
