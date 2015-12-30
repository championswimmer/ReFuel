package in.championswimmer.refuel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by championswimmer on 30/12/15.
 */
public class AppDb extends SQLiteOpenHelper {

    public static final String DB_NAME = "ReFuel.db";
    public static final int DB_VER = 1;

    public static final String CMD_ENABLE_FOREIGN_KEYS = "PRAGMA foreign_keys = ON;";

    public static AppDb getInstance (Context c) {
        return new AppDb(c);
    }

    public AppDb (Context c) {
        super(c, DB_NAME, null, DB_VER);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        } else {
            db.execSQL(CMD_ENABLE_FOREIGN_KEYS);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ExpenseTable.CMD_CREATE_TABLE);
        db.execSQL(RefuelTable.CMD_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}