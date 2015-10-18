package in.championswimmer.refuel.dbhelper;

import android.provider.BaseColumns;

/**
 * Created by championswimmer on 18/10/15.
 */
public interface RefuelDbContracts {

    String TEXT_TYPE = " TEXT";
    String INTEGER_TYPE = " INTEGER";
    String COMMA_SEP = ",";



    interface RefuelEntry extends BaseColumns {
        String TABLE_NAME = "refuel_entry";

        String COL_FUEL_FILLED = "fuel_filled";
        String COL_MONEY_PAID = "money_paid";
        String COL_RATE_PER_LIT = "rate_per_lit";
        String COL_ODOMETER_READING = "odometer_reading";
        String COL_DID_FULL_TANK = "did_full_tank";
        //TODO: Add columns for timestamp and name of station

        String COMMAND_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COL_FUEL_FILLED + TEXT_TYPE + COMMA_SEP +
                        COL_MONEY_PAID + TEXT_TYPE + COMMA_SEP +
                        COL_RATE_PER_LIT + TEXT_TYPE + COMMA_SEP +
                        COL_ODOMETER_READING + TEXT_TYPE + COMMA_SEP +
                        COL_DID_FULL_TANK + INTEGER_TYPE +
                " )";

    }
}
