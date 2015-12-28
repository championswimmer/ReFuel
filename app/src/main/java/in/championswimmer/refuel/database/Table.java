package in.championswimmer.refuel.database;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by championswimmer on 28/12/15.
 */
public abstract class Table {


    public String getTableName() {
        return getClass().getSimpleName();
    }

    public ArrayList<Column> getColumns() {
        ArrayList<Column> tblColumns = new ArrayList<>();
        for (Field f : getClass().getFields()) {
            if (f.getType().isAssignableFrom(Column.class)) {
                try {
                    tblColumns.add((Column) f.get(Column.class));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return tblColumns;
    }

    public String cmdCreateTable() {
        String columnAssignments = " id integer primary key autoincrement ";
        for (Column c : getColumns()) {
            columnAssignments += ", " + c.getColName() + " " + c.getType() + " ";
        }
        return "create table if not exists "
                + getTableName()
                + " ( " + columnAssignments + " );";
    }
}
