package in.championswimmer.refuel.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import in.championswimmer.refuel.models.Expense;
import in.championswimmer.refuel.models.Refuel;

/**
 * Created by championswimmer on 30/12/15.
 */
public class RefuelTable{
    public static final String TABLE_NAME = "refuel";

    public static final String ID = "refuel_id";

    public static final String ODOMETER = "odometer";
    public static final String LITRE = "litre";
    public static final String RATE = "rate";
    public static final String PUMP_NAME = "pump_name";
    public static final String FULL_TANK = "full_tank";
    public static final String EXP_ID = "exp_id";

    public static final String[] PROJECTION =
            {ID, ODOMETER, LITRE, RATE, PUMP_NAME, FULL_TANK, EXP_ID};

    public static final String TABLE_CREATE_COMMAND =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                    + ID + " INTEGER PRIMARY KEY , "
                    + ODOMETER + " REAL , "
                    + LITRE + " REAL , "
                    + RATE + " REAL , "
                    + PUMP_NAME + " TEXT , "
                    + FULL_TANK + " INTEGER , "
                    + EXP_ID + " INTEGER , "
                    + " FOREIGN KEY ( " + EXP_ID + " ) REFERENCES " + ExpenseTable.TABLE_NAME + " ( " + ExpenseTable.ID + " ) "
                    + " );";

    public static Refuel getById (SQLiteDatabase db, long id) {
        String rawQuery = "SELECT * FROM " + RefuelTable.TABLE_NAME + " INNER JOIN " + ExpenseTable.TABLE_NAME
                + " ON " + RefuelTable.EXP_ID + " = " + ExpenseTable.ID
                + " WHERE " + RefuelTable.ID + " = " +  id;
        Cursor c = db.rawQuery(
                rawQuery,
                null
        );
        c.moveToFirst();
        Refuel ref = new Refuel(
                c.getInt(c.getColumnIndexOrThrow(ID)),
                c.getDouble(c.getColumnIndexOrThrow(ODOMETER)),
                c.getDouble(c.getColumnIndexOrThrow(RATE)),
                c.getDouble(c.getColumnIndexOrThrow(LITRE)),
                c.getString(c.getColumnIndexOrThrow(PUMP_NAME)),
                (c.getInt(c.getColumnIndexOrThrow(FULL_TANK)) != 0),
                new Expense(
                        c.getInt(c.getColumnIndexOrThrow(ExpenseTable.ID)),
                        c.getDouble(c.getColumnIndexOrThrow(ExpenseTable.AMOUNT)),
                        c.getLong(c.getColumnIndexOrThrow(ExpenseTable.TIMESTAMP)),
                        c.getString(c.getColumnIndexOrThrow(ExpenseTable.DESC)),
                        c.getString(c.getColumnIndexOrThrow(ExpenseTable.TYPE))
                )
        );
        c.close();
        return ref;

    }
    public static long save (SQLiteDatabase db, Refuel rf) {
        ContentValues cv = new ContentValues();
        cv.put(ODOMETER, rf.getOdometer());
        cv.put(LITRE, rf.getLitre());
        cv.put(RATE, rf.getRate());
        cv.put(PUMP_NAME, rf.getPumpName());
        cv.put(FULL_TANK, rf.isFullTank());
        rf.getExpense().setDesc("Refuel at " + rf.getPumpName());
        rf.getExpense().setType("refuel");
        cv.put(EXP_ID, ExpenseTable.save(db, rf.getExpense()));

        return db.insert(
                TABLE_NAME,
                null,
                cv
        );
    }

}
