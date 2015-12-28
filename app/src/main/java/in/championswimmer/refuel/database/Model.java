package in.championswimmer.refuel.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by championswimmer on 29/12/15.
 */
public class Model {
    
    public static final boolean DEBUG = false;

    public static final String TAG = "Model";
    public int _id;

    public int getId() {
        return _id;
    }

    public String getTableName() {
        return getClass().getSimpleName();
    }

    public HashMap<String, String> getColTypeMap() throws IllegalAccessException {
        HashMap<String, String> colValPairs = new HashMap<>();
        String key = null, value = null;
        for (Field f : getClass().getDeclaredFields()) {
            if (f.getName().contains("$")) continue;
            Class<?> clazz = f.getType();
            if (f.getName().equals("_id")) {
                value = "INTEGER PRIMARY KEY AUTOINCREMENT";
            } else if (clazz == Integer.class || clazz == int.class) {
                value = "INTEGER";
            } else if (clazz == Long.class || clazz == long.class) {
                value = "INTEGER";
            } else if (clazz == Float.class || clazz == float.class) {
                value = "REAL";
            } else if (clazz == Double.class || clazz == double.class) {
                value = "REAL";
            } else if (clazz == String.class
                    || clazz == char[].class
                    || clazz == CharSequence.class) {
                value = "TEXT";
            }
            key = f.getName();
            if (DEBUG) Log.d(TAG, "getColTypeMap: " + key + " = " + value);
            colValPairs.put(key, value);
        }
        return colValPairs;
    }

    public ContentValues getContentValues() throws IllegalAccessException {
        ContentValues cv = new ContentValues();
        String col = null, value = null;
        for (Field f : getClass().getDeclaredFields()) {
            if (f.getName().contains("$")) continue;
            Class<?> clazz = f.getType();
            value = String.valueOf(f.get(this));
            col = f.getName();
            if (DEBUG) Log.d(TAG, "getContentValues: " + col + " = " + value);
            cv.put(col, value);
        }
        return cv;
    }

    public void createTable(SQLiteDatabase db) throws IllegalAccessException {
        String sqlCmd = "create table if not exists " + getTableName() + " ( ";
        for (HashMap.Entry<String, String> dbCol : getColTypeMap().entrySet()) {
            sqlCmd += " , " + dbCol.getKey() + " " + dbCol.getValue() + " ";
        }
        sqlCmd += " );";
        db.execSQL(sqlCmd);
    }

    public String[] getFullProjection() {
        try {
            HashMap<String, String> colTypeMap = getColTypeMap();
            return colTypeMap.keySet().toArray(new String[colTypeMap.size()]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(SQLiteDatabase db) {
        try {
            db.insert(getTableName(), null, getContentValues());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public <T extends Model> ArrayList<T> getAll(SQLiteDatabase db)
            throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        if (DEBUG) Log.d(TAG, "getAll: called");
        ArrayList<T> modelList = new ArrayList<>();

        Cursor c = db.query(
                getTableName(),
                getFullProjection(),
                null, null, null, null, null
        );
        if (DEBUG) Log.d(TAG, "getAll: cursor" + c.toString() + " c=" + c.getCount());
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (DEBUG) Log.d(TAG, "getAll: curson moving");
            T row = (T) getClass().newInstance();
            for (Field f : getClass().getDeclaredFields()) {
                if (f.getName().contains("$")) continue;
                Class<?> clazz = f.getType();
                int colIndex = c.getColumnIndexOrThrow(f.getName());
                if (DEBUG) Log.d(TAG, "getAll: f=" + f.getName() + " c=" + colIndex + " d=" + c.getString(colIndex));
                if (clazz == Integer.class || clazz == int.class) {
                    f.set(row, c.getInt(colIndex));
                } else if (clazz == Long.class || clazz == long.class) {
                    f.set(row, c.getLong(colIndex));
                } else if (clazz == Float.class || clazz == float.class) {
                    f.set(row, c.getFloat(colIndex));
                } else if (clazz == Double.class || clazz == double.class) {
                    f.set(row, c.getDouble(colIndex));
                } else if (clazz == String.class
                        || clazz == char[].class
                        || clazz == CharSequence.class) {
                    f.set(row, c.getString(colIndex));
                }
            }
            modelList.add(row);
            c.moveToNext();
        }
        c.close();
        return modelList;

    }

}
