package in.championswimmer.refuel.db;

import android.provider.BaseColumns;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by championswimmer on 29/12/15.
 */
public abstract class Model implements BaseColumns {

    public static final String TAG = "Model";

    protected static final String COMMA = " , ";
    public static final String TYPE_INTEGER = "INTEGER";
    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_REAL = "REAL";
    public static final String AUTO_INC_PRIME_KEY = "AUTOINCREMENT PRIMARY KEY NOT NULL";


    public static String getTableName(Class<? extends Model> clazz) {
        return clazz.getSimpleName();
    }


    public abstract String createTableCmd();

    public static String crtTableCmd(Class<? extends Model> clazz) {
        String cmd = "CREATE TABLE IF NOT EXISTS " + getTableName(clazz) + " ( "
                + _ID + " " + TYPE_INTEGER + " " + AUTO_INC_PRIME_KEY + COMMA;
        for (Field f : clazz.getDeclaredFields()) {
            if (f.getName().contains("$") || f.getName().substring(0,3).equals("fk_")) {
                continue;
            }
            cmd += colCreate(clazz, f.getName());
        }

        cmd += " );";
        return cmd;

    }

    protected static String fkCreate(Class<? extends Model> cls, String s, Class<? extends Model> foreignClass) {
        try {
            String cmd = " FOREIGN KEY + ( " + cls.getField(s) + " ) " +
                    "REFERENCES " + foreignClass.getSimpleName() + " ( " + _ID + " )";
            return cmd;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return "";
        }
    }

    protected static String colCreate(Class<? extends Model> cls, String s) {
        Field f = null;
        Log.d(TAG, "colCreate: " + cls.getClass().getSimpleName());
        Log.d(TAG, "colCreate: " + s);
        try {
            f = cls.getField(s);
            Log.d(TAG, "colCreate: " + f.getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return "";
        }
        if ((f.getType() == int.class) || (f.getType() == Integer.class)) {
            return COMMA + f.getName() + " " + TYPE_INTEGER;
        } else if ((f.getType() == long.class) || (f.getType() == Long.class)) {
            return COMMA + f.getName() + " " + TYPE_INTEGER;
        } else if ((f.getType() == float.class) || (f.getType() == Float.class)) {
            return COMMA + f.getName() + " " + TYPE_REAL;
        } else if ((f.getType() == double.class) || (f.getType() == Double.class)) {
            return COMMA + f.getName() + " " + TYPE_REAL;
        } else if ((f.getType() == String.class) || (f.getType() == char[].class)) {
            return COMMA + f.getName() + " " + TYPE_TEXT;
        }
        return "";
    }

//    public abstract String save

}
