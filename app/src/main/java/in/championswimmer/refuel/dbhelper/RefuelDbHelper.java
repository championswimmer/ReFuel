package in.championswimmer.refuel.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import in.championswimmer.refuel.pojos.RefuelEntry;

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
        cv.put(RefuelDbContracts.RefuelEntry.COL_TIMESTAMP, System.currentTimeMillis());

        db.insert(RefuelDbContracts.RefuelEntry.TABLE_NAME, null, cv);
        return true;
    }

    public static ArrayList<RefuelEntry> getRefuelHistory(Context ctx) {
        SQLiteDatabase db = getInstance(ctx).getReadableDatabase();
        String[] projection = {
                RefuelDbContracts.RefuelEntry._ID,
                RefuelDbContracts.RefuelEntry.COL_FUEL_FILLED,
                RefuelDbContracts.RefuelEntry.COL_MONEY_PAID,
                RefuelDbContracts.RefuelEntry.COL_RATE_PER_LIT,
                RefuelDbContracts.RefuelEntry.COL_ODOMETER_READING,
                RefuelDbContracts.RefuelEntry.COL_TIMESTAMP,
//                RefuelDbContracts.RefuelEntry.COL_DID_FULL_TANK,
        };
        Cursor c = db.query(
                RefuelDbContracts.RefuelEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                RefuelDbContracts.RefuelEntry.COL_TIMESTAMP + " DESC"
        );
        ArrayList<RefuelEntry> refuelEntries = new ArrayList<>(c.getCount());
        c.moveToFirst();
        while (!c.isAfterLast()) {
            refuelEntries.add(c.getPosition(), new RefuelEntry(
                    c.getFloat(c.getColumnIndexOrThrow(RefuelDbContracts.RefuelEntry.COL_FUEL_FILLED)),
                    c.getFloat(c.getColumnIndexOrThrow(RefuelDbContracts.RefuelEntry.COL_RATE_PER_LIT)),
                    c.getFloat(c.getColumnIndexOrThrow(RefuelDbContracts.RefuelEntry.COL_MONEY_PAID)),
                    c.getInt(c.getColumnIndexOrThrow(RefuelDbContracts.RefuelEntry.COL_ODOMETER_READING)),
                    c.getLong(c.getColumnIndexOrThrow(RefuelDbContracts.RefuelEntry.COL_TIMESTAMP))
            ));
            c.moveToNext();
        }

        return refuelEntries;
    }

    public static RefuelEntry getLastRefuel(Context ctx) {
        SQLiteDatabase db = getInstance(ctx).getReadableDatabase();
        String[] projection = {
                RefuelDbContracts.RefuelEntry._ID,
                RefuelDbContracts.RefuelEntry.COL_FUEL_FILLED,
                RefuelDbContracts.RefuelEntry.COL_MONEY_PAID,
                RefuelDbContracts.RefuelEntry.COL_RATE_PER_LIT,
                RefuelDbContracts.RefuelEntry.COL_ODOMETER_READING,
                RefuelDbContracts.RefuelEntry.COL_TIMESTAMP,
//                RefuelDbContracts.RefuelEntry.COL_DID_FULL_TANK,
        };
        Cursor c = db.query(
                RefuelDbContracts.RefuelEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                RefuelDbContracts.RefuelEntry.COL_TIMESTAMP + " DESC",
                "1"
        );
        c.moveToFirst();

        return new RefuelEntry(
                c.getFloat(c.getColumnIndexOrThrow(RefuelDbContracts.RefuelEntry.COL_FUEL_FILLED)),
                c.getFloat(c.getColumnIndexOrThrow(RefuelDbContracts.RefuelEntry.COL_RATE_PER_LIT)),
                c.getFloat(c.getColumnIndexOrThrow(RefuelDbContracts.RefuelEntry.COL_MONEY_PAID)),
                c.getInt(c.getColumnIndexOrThrow(RefuelDbContracts.RefuelEntry.COL_ODOMETER_READING)),
                c.getLong(c.getColumnIndexOrThrow(RefuelDbContracts.RefuelEntry.COL_TIMESTAMP))
        );
    }
}
