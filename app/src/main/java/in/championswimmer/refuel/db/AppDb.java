package in.championswimmer.refuel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import in.championswimmer.refuel.dbtables.Expense;
import in.championswimmer.refuel.dbtables.Refuel;

/**
 * Created by championswimmer on 29/12/15.
 */
public class AppDb extends SQLiteOpenHelper {

    public static final String DB_NAME = "ReFuel.db";
    public static final int DB_VER = 1;

    private static AppDb appDb;

    public static AppDb getInstance (Context c) {
        if (appDb == null) {
            appDb = new AppDb(c);
        }
        createTables(appDb.getWritableDatabase());
        return appDb;
    }

    public static void createTables (SQLiteDatabase db) {
        db.execSQL(Expense.CREATE_TABLE_COMMAND);
        db.execSQL(Refuel.CREATE_TABLE_COMMAND);
    }

    public AppDb (Context c) {
        super(c, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
