package in.championswimmer.refuel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by championswimmer on 29/12/15.
 */
public class AppDb extends SQLiteOpenHelper {

    public static final String DB_NAME = "ReFuel.db";
    public static final int DB_VER = 1;

    private static AppDb dbInstance;

    public AppDb (Context c) {
        super(c, DB_NAME, null, DB_VER);
    }

    public static AppDb getAppDb (Context c) {
        if (dbInstance == null) {
            dbInstance = new AppDb(c);
        }
        return dbInstance;
    }

    public SQLiteDatabase writeInstance () {
        return this.getWritableDatabase();
    }

    public SQLiteDatabase readInstance () {
        return this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
