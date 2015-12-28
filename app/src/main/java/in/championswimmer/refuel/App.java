package in.championswimmer.refuel;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import in.championswimmer.refuel.database.AppDb;
import in.championswimmer.refuel.database.Table;
import in.championswimmer.refuel.database.models.ReFuel;

/**
 * Created by championswimmer on 28/12/15.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteDatabase db = AppDb.getInstance(this).getWritableDatabase();
        ReFuel r = new ReFuel();
        db.execSQL(r.cmdCreateTable());
    }
}
