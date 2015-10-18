package in.championswimmer.refuel.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by championswimmer on 18/10/15.
 */
public class RefuelDbHelper extends SQLiteOpenHelper{

    public static final int DB_VER = 1;
    public static final String DB_NAME = "Refuel.db";

    private static RefuelDbHelper refuelDbSingleton = null;

    public static RefuelDbHelper getInstance(Context c) {
        if (refuelDbSingleton == null ) {
            refuelDbSingleton = new RefuelDbHelper(c.getApplicationContext());

        }
        return refuelDbSingleton;
    }

    public RefuelDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(RefuelDbContracts.RefuelEntry.COMMAND_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static boolean addNewRefuelEntry(Context ctx, float fuelAmt, float rate, float moneyAmt, int odometer) {
        SQLiteDatabase db = getInstance(ctx).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RefuelDbContracts.RefuelEntry.COL_FUEL_FILLED, String.valueOf(fuelAmt));
        cv.put(RefuelDbContracts.RefuelEntry.COL_RATE_PER_LIT, String.valueOf(rate));
        cv.put(RefuelDbContracts.RefuelEntry.COL_MONEY_PAID, String.valueOf(moneyAmt));
        cv.put(RefuelDbContracts.RefuelEntry.COL_ODOMETER_READING, String.valueOf(odometer));

        db.insert(RefuelDbContracts.RefuelEntry.TABLE_NAME, null, cv);
        return true;
    }
}
