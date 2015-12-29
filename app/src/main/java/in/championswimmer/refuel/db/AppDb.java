package in.championswimmer.refuel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by championswimmer on 30/12/15.
 */
public class AppDb extends SQLiteOpenHelper {

    public static final String DB_NAME = "ReFuel.db";
    public static final int DB_VER = 1;

    private static AppDb appDb = null;

    public static AppDb getInstance (Context c) {
        if (appDb == null) {
            appDb = new AppDb(c);
        }
        return appDb;
    }

    public AppDb (Context c) {
        super(c, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ExpenseTable.TABLE_CREATE_COMMAND);
        db.execSQL(RefuelTable.TABLE_CREATE_COMMAND);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}