package in.championswimmer.refuel.database.models;

import android.database.sqlite.SQLiteDatabase;

import in.championswimmer.refuel.database.Column;
import in.championswimmer.refuel.database.Table;

/**
 * Created by championswimmer on 28/12/15.
 */
public class ReFuel extends Table{

    Column.Integer fuelFilled;
    Column.Integer ratePerLit;
    Column.Integer expense;
    Column.Integer odoReading;

    public boolean save(SQLiteDatabase db) {
        String tableName = getTableName();
        return false;
    }

}
