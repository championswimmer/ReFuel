package in.championswimmer.refuel;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import in.championswimmer.refuel.database.AppDb;
import in.championswimmer.refuel.database.models.ReFuel;

/**
 * Created by championswimmer on 28/12/15.
 */
public class App extends Application {

    public static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteDatabase db = AppDb.getInstance(this).getWritableDatabase();
        ReFuel r = new ReFuel();
        try {
            r.createTable(db);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        r = new ReFuel(10, 10, 4.4);
        r.save(db);

        try {
            ArrayList<ReFuel> rf = r.getAll(ReFuel.class, db);
            Log.d(TAG, "onCreate: get Array" + rf.size());
            for (ReFuel r2 : rf) {
                Log.d(TAG, "onCreate: " + r2.getFuelFilled() + " " + r2.getOdoReading() + " " + r2.getRate());
            }
        } catch (Exception e) {
            Log.d(TAG, "onCreate: Exception");
            e.printStackTrace();
        }




    }
}
