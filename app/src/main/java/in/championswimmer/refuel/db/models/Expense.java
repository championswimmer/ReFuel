package in.championswimmer.refuel.db.models;

import in.championswimmer.refuel.db.Model;

/**
 * Created by championswimmer on 29/12/15.
 */
public class Expense extends Model {

    int value;
    long timeStamp;

    @Override
    public String createTableCmd() {
        Class e = Expense.class;
        return "CREATE TABLE IF NOT EXISTS " + getTableName(e) + " ( "
                + _ID + " "+ TYPE_INTEGER + " " + AUTO_INC_PRIME_KEY + COMMA
                + colCreate(e, "value")
                + colCreate(e, "timeStamp")
                + " );";
    }

}
