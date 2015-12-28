package in.championswimmer.refuel.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by championswimmer on 28/12/15.
 */
public class AppDb extends SQLiteOpenHelper {


    public static final int DB_VER = 1;
    public static final String DB_NAME = "Refuel.db";

    private static AppDb AppDbSingleton = null;

    public static AppDb getInstance(Context c) {
        if (AppDbSingleton == null ) {
            AppDbSingleton = new AppDb(c.getApplicationContext());

        }
        return AppDbSingleton;
    }

    public AppDb(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
