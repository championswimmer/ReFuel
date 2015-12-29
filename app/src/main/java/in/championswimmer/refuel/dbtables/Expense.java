package in.championswimmer.refuel.dbtables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by championswimmer on 29/12/15.
 */
public class Expense {

    public static final String TABLE_NAME = "EXPENSE";

    public static final String ID = "_id";
    public static final String DESC = "desc";
    public static final String AMOUNT = "amount";
    public static final String TIMESTAMP = "timeStamp";
    public static final String TYPE = "type";
    public static final String REF_ID = "ref_id";


    int id;
    String desc;
    double amount;
    long timestamp;
    String type;
    int ref_id;

    private Expense(int id, String desc, double amount, long timestamp, String type, int ref_id) {
        this.id = id;
        this.desc = desc;
        this.amount = amount;
        this.timestamp = timestamp;
        this.type = type;
        this.ref_id = ref_id;
    }

    public Expense(String desc, double amount, long timestamp, String type, int ref_id) {
        this.desc = desc;
        this.amount = amount;
        this.timestamp = timestamp;
        this.type = type;
        this.ref_id = ref_id;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public int getRef_id() {
        return ref_id;
    }

    private ContentValues getContentValues () {
        ContentValues cv = new ContentValues();
        cv.put(DESC, desc);
        cv.put(AMOUNT, amount);
        cv.put(TIMESTAMP, timestamp);
        cv.put(TYPE, type);
        cv.put(REF_ID, ref_id);
        return cv;
    }

    private static Expense getFromCursor(Cursor c) {
        return new Expense(
                c.getInt(c.getColumnIndexOrThrow(ID)),
                c.getString(c.getColumnIndexOrThrow(DESC)),
                c.getDouble(c.getColumnIndexOrThrow(AMOUNT)),
                c.getLong(c.getColumnIndexOrThrow(TIMESTAMP)),
                c.getString(c.getColumnIndexOrThrow(TYPE)),
                c.getInt(c.getColumnIndexOrThrow(REF_ID))
        );
    }

    public static Expense getById (SQLiteDatabase db, int id) {
        Cursor c = db.query(
                TABLE_NAME,
                FULL_PROJECTION,
                "_id="+id,
                null, null, null, null
        );
        c.moveToFirst();
        return getFromCursor(c);
    }

    public static ArrayList<Expense> getByArgs (SQLiteDatabase db, String selection, String[] selectionArgs) {
        Cursor c = db.query(
                TABLE_NAME,
                FULL_PROJECTION,
                selection, selectionArgs, null, null, null
        );
        ArrayList<Expense> expenses = new ArrayList<>();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            expenses.add(getFromCursor(c));
            c.moveToNext();
        }
        return expenses;

    }

    public long save(SQLiteDatabase db) {
        return db.insert(
                TABLE_NAME,
                null,
                getContentValues()
        );
    }


    public static final String CREATE_TABLE_COMMAND =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                    + ID + " INTEGER PRIMARY KEY NOT NULL" + " , "
                    + DESC + " TEXT" + " , "
                    + AMOUNT + " REAL " + " , "
                    + TIMESTAMP + " INTEGER " + " , "
                    + TYPE + " TEXT " + " , "
                    + REF_ID + " INTEGER "
                    + " );";

    public static final String[] FULL_PROJECTION = {
            ID, DESC, AMOUNT, TIMESTAMP, TYPE, REF_ID
    };
}
