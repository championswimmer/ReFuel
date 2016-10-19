package in.championswimmer.refuel.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import java.util.ArrayList;

import in.championswimmer.refuel.models.Expense;
import in.championswimmer.refuel.models.Refuel;
import in.championswimmer.refuel.utils.Utils;

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

    public static final String CMD_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                    + ID + " INTEGER PRIMARY KEY , "
                    + ODOMETER + " REAL , "
                    + LITRE + " REAL , "
                    + RATE + " REAL , "
                    + PUMP_NAME + " TEXT , "
                    + FULL_TANK + " INTEGER , "
                    + EXP_ID + " REFERENCES " + ExpenseTable.TABLE_NAME + " ON DELETE CASCADE ON UPDATE CASCADE"
                    + " );";

    public static Refuel getById (SQLiteDatabase db, long id) {
        Cursor c = db.query(
                RefuelTable.TABLE_NAME + " , " + ExpenseTable.TABLE_NAME,
                Utils.concat(RefuelTable.PROJECTION, ExpenseTable.PROJECTION),
                RefuelTable.EXP_ID + " = " + ExpenseTable.ID + " AND " + RefuelTable.ID + " = " +  id,
                null,
                null,
                null,
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

    public static ArrayList<Refuel> getByArg ( SQLiteDatabase db, String whereClause, String sortBy ) {
        Cursor c = db.query(
                RefuelTable.TABLE_NAME + " , " + ExpenseTable.TABLE_NAME,
                Utils.concat(RefuelTable.PROJECTION, ExpenseTable.PROJECTION),
                RefuelTable.EXP_ID + " = " + ExpenseTable.ID + ((whereClause != null) ? " AND " + whereClause : ""),
                null,
                null,
                null,
                (sortBy != null) ? sortBy : ID + " DESC"
        );

        ArrayList<Refuel> refuels = new ArrayList<>();
        c.moveToFirst();
        while (! c.isAfterLast()) {
            refuels.add(
                    new Refuel(
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
                    )
            );
        }
        c.close();
        return refuels;

    }

    public static int deleteById (SQLiteDatabase db, Refuel rf) {
        /*
        We can just delete the parent expense row.
        The ON DELETE CASCADE clause, will make sure the
        refuel row is also deleted.
         */
        return ExpenseTable.deleteById(db, rf.getExpense());
    }

    public static int update (SQLiteDatabase db, Refuel rf) {
        try {
            rf.getExpense().setDesc("Refuel at " + rf.getPumpName());
            rf.getExpense().setType("refuel");
            ExpenseTable.update(db, rf.getExpense());

            ContentValues cv = new ContentValues();
            cv.put(ODOMETER, rf.getOdometer());
            cv.put(LITRE, rf.getLitre());
            cv.put(RATE, rf.getRate());
            cv.put(PUMP_NAME, rf.getPumpName());
            cv.put(FULL_TANK, rf.isFullTank());
            cv.put(EXP_ID, rf.getExpense().getId());

            return db.update(
                    TABLE_NAME,
                    cv,
                    ID + "=" + rf.getId(),
                    null
            );
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }

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
