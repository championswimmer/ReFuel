package in.championswimmer.refuel.dbtables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Ref;
import java.util.ArrayList;

/**
 * Created by championswimmer on 29/12/15.
 */
public class Refuel {

    public static final String TABLE_NAME = "REFUEL";

    public static final String ID = "_id";
    public static final String LITRES = "litres";
    public static final String RATE = "rate";
    public static final String ODOMETER = "odometer";
    public static final String FULL_TANK = "full_tank";

    int id;
    double litres;
    double rate;
    double odometer;
    boolean fullTank;

    private Refuel(int id, double litres, double rate, double odometer, boolean fullTank) {
        this.id = id;
        this.litres = litres;
        this.rate = rate;
        this.odometer = odometer;
        this.fullTank = fullTank;
    }

    public Refuel(double litres, double rate, double odometer, boolean fullTank) {
        this.litres = litres;
        this.rate = rate;
        this.odometer = odometer;
        this.fullTank = fullTank;
    }

    public int getId() {
        return id;
    }

    public double getLitres() {
        return litres;
    }

    public double getRate() {
        return rate;
    }

    public double getOdometer() {
        return odometer;
    }

    public boolean isFullTank() {
        return fullTank;
    }

    private ContentValues getContentValues () {
        ContentValues cv = new ContentValues();
        cv.put(LITRES, litres);
        cv.put(RATE, rate);
        cv.put(ODOMETER, odometer);
        cv.put(FULL_TANK, fullTank);
        return cv;
    }

    public long save(SQLiteDatabase db) {
        return db.insert(
                TABLE_NAME,
                null,
                getContentValues()
        );
    }

    private static Refuel getFromCursor(Cursor c) {
        return new Refuel(
                c.getInt(c.getColumnIndexOrThrow(ID)),
                c.getDouble(c.getColumnIndexOrThrow(LITRES)),
                c.getDouble(c.getColumnIndexOrThrow(RATE)),
                c.getInt(c.getColumnIndexOrThrow(ODOMETER)),
                (c.getInt(c.getColumnIndexOrThrow(FULL_TANK)) != 0)
        );
    }

    public static Refuel getById (SQLiteDatabase db, int id) {
        Cursor c = db.query(
                TABLE_NAME,
                FULL_PROJECTION,
                "_id="+id,
                null, null, null, null
        );
        c.moveToFirst();
        return getFromCursor(c);
    }

    public static ArrayList<Refuel> getByArgs (SQLiteDatabase db, String selection, String[] selectionArgs) {
        Cursor c = db.query(
                TABLE_NAME,
                FULL_PROJECTION,
                selection, selectionArgs, null, null, null
        );
        ArrayList<Refuel> refuels = new ArrayList<>();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            refuels.add(getFromCursor(c));
            c.moveToNext();
        }
        return refuels;

    }

    public static final String CREATE_TABLE_COMMAND =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                    + ID + " INTEGER PRIMARY KEY NOT NULL" + " , "
                    + LITRES + " REAL" + " , "
                    + RATE + " REAL" + " , "
                    + ODOMETER + " REAL" + " , "
                    + FULL_TANK + " INTEGER"
                    + " );";

    public static final String[] FULL_PROJECTION = {
            ID, LITRES, RATE, ODOMETER, FULL_TANK
    };
}
