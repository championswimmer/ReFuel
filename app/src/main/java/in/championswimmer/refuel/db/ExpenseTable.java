package in.championswimmer.refuel.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import in.championswimmer.refuel.models.Expense;

/**
 * Created by championswimmer on 30/12/15.
 */
public class ExpenseTable{
    public static final String TABLE_NAME = "expense";

    public static final String ID = "expense_id";

    public static final String AMOUNT = "amount";
    public static final String TIMESTAMP = "timestamp";
    public static final String DESC = "desc";
    public static final String TYPE = "type";

    public static final String[] PROJECTION = {ID, AMOUNT, TIMESTAMP, DESC, TYPE};

    public static final String TABLE_CREATE_COMMAND =
            " CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
            + ID + " INTEGER PRIMARY KEY , "
            + AMOUNT + " INTEGER , "
            + TIMESTAMP + " INTEGER , "
            + DESC + " TEXT , "
            + TYPE + " TEXT CHECK ( TYPE in ('refuel', 'service', 'misc') ) "
            + " );";

    public static Expense getById (SQLiteDatabase db, long id) {
        Cursor c = db.query(
                TABLE_NAME,
                PROJECTION,
                ID + "=" + id,
                null,
                null,
                null,
                null
        );
        c.moveToFirst();
        Expense exp = new Expense(
                c.getInt(c.getColumnIndexOrThrow(ID)),
                c.getDouble(c.getColumnIndexOrThrow(AMOUNT)),
                c.getLong(c.getColumnIndexOrThrow(TIMESTAMP)),
                c.getString(c.getColumnIndexOrThrow(DESC)),
                c.getString(c.getColumnIndexOrThrow(TYPE))
        );
        c.close();
        return exp;
    }

    public static int update (SQLiteDatabase db, Expense exp) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(AMOUNT, exp.getAmount());
            cv.put(TIMESTAMP, exp.getTimestamp());
            cv.put(DESC, exp.getDesc());
            cv.put(TYPE, exp.getType());
            return db.update(
                    TABLE_NAME,
                    cv,
                    ID + "=" + exp.getId(),
                    null
            );
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long save (SQLiteDatabase db, Expense exp) {
        ContentValues cv = new ContentValues();
        cv.put(AMOUNT, exp.getAmount());
        cv.put(TIMESTAMP, exp.getTimestamp());
        cv.put(DESC, exp.getDesc());
        cv.put(TYPE, exp.getType());
        return db.insert(
                TABLE_NAME,
                null,
                cv
        );
    }
}
