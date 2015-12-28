package in.championswimmer.refuel.database;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by championswimmer on 29/12/15.
 */
public class Model {

    public static final String TAG = "Model";

    public String getTableName() {
        return getClass().getSimpleName();
    }

    public HashMap<String, String> getColTypeMap() throws IllegalAccessException {
        HashMap<String, String> colValPairs = new HashMap<>();
        String key = null, value = null;
        for (Field f : getClass().getDeclaredFields()) {
            if (f.getName().contains("$")) continue;
            Class<?> clazz = f.getType();
            if (clazz == Integer.class || clazz == int.class) {
                value = "INTEGER";
            } else if (clazz == Long.class || clazz == long.class) {
                value = "INTEGER";
            } else if (clazz == Float.class || clazz == float.class) {
                value = "REAL";
            } else if (clazz == Double.class || clazz == double.class) {
                value = "REAL";
            }
            else if (clazz == String.class
                    || clazz == char[].class
                    || clazz == CharSequence.class) {
                value = "TEXT";
            }
            key = f.getName();
            Log.d(TAG, "getColTypeMap: "+ key + " = " + value);
            colValPairs.put(key,value);
        }
        return colValPairs;
    }

    public ContentValues getContentValues () throws IllegalAccessException {
        ContentValues cv = new ContentValues();
        String col = null, value = null;
        for (Field f : getClass().getDeclaredFields()) {
            if (f.getName().contains("$")) continue;
            Class<?> clazz = f.getType();
//            if (clazz == Integer.class || clazz == int.class) {
//                value = String.valueOf(f.get(this));
//            } else if (clazz == Long.class || clazz == long.class) {
//                value = String.valueOf(f.get(this));
//            } else if (clazz == Float.class || clazz == float.class) {
//                value = String.valueOf(f.get(this));
//            } else if (clazz == Double.class || clazz == double.class) {
//                value = String.valueOf(f.get(this));
//            }
//            else if (clazz == String.class
//                    || clazz == char[].class
//                    || clazz == CharSequence.class) {
//                value = String.valueOf(f.get(this));
//            }
            value = String.valueOf(f.get(this));
            col = f.getName();
            Log.d(TAG, "getContentValues: "+ col + " = " + value);
            cv.put(col,value);
        }
        return cv;
    }

    public void createTable (SQLiteDatabase db) throws IllegalAccessException {
        String sqlCmd = "create table if not exists " + getTableName() + " ( ";
        sqlCmd += " id INTEGER PRIMARY KEY AUTOINCREMENT";
        for (HashMap.Entry<String,String> dbCol : getColTypeMap().entrySet()) {
            sqlCmd += " , " + dbCol.getKey() + " " + dbCol.getValue() + " ";
        }
        sqlCmd += " );";
        db.execSQL(sqlCmd);
    }

    public String[] getFullProjection () {
        try {
            HashMap<String,String> colTypeMap = getColTypeMap();
            return colTypeMap.keySet().toArray(new String[colTypeMap.size()]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save (SQLiteDatabase db) {
        try {
            db.insert(getTableName(), null, getContentValues());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public <T> ArrayList<T> getAll (Class<T> modelClass, SQLiteDatabase db) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Log.d(TAG, "getAll: called");
        ArrayList<T> modelList = new ArrayList<>();

        Cursor c = db.query(
                getTableName(),
                getFullProjection(),
                null, null, null, null, null
        );
        Log.d(TAG, "getAll: cursor" + c.toString() + " c=" + c.getCount() );
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Log.d(TAG, "getAll: curson moving");
            T row = modelClass.newInstance();
//            for (HashMap.Entry<String,String> dbCol : getColTypeMap().entrySet()) {
//                Field fld = modelClass.getDeclaredField(dbCol.getKey());
//                if (dbCol.getValue() == "TEXT") {
//                    fld.set(row, c.getString(c.getColumnIndexOrThrow(dbCol.getKey())));
//                } else if (dbCol.getValue() == "INTEGER") {
//                    fld.set(row, c.getInt(c.getColumnIndexOrThrow(dbCol.getKey())));
//                } else if (dbCol.getValue() == "REAL") {
//                    fld.set(row, c.getDouble(c.getColumnIndexOrThrow(dbCol.getKey())));
//                }
//            }
            for (Field f : modelClass.getDeclaredFields()) {
                if (f.getName().contains("$")) continue;
                Class<?> clazz = f.getType();
                int colIndex = c.getColumnIndexOrThrow(f.getName());
                Log.d(TAG, "getAll: f=" + f.getName() + " c="+colIndex + " d=" + c.getString(colIndex));
                if (clazz == Integer.class || clazz == int.class) {
                    f.set(row, c.getInt(colIndex));
                } else if (clazz == Long.class || clazz == long.class) {
                    f.set(row, c.getLong(colIndex));
                } else if (clazz == Float.class || clazz == float.class) {
                    f.set(row, c.getFloat(colIndex));
                } else if (clazz == Double.class || clazz == double.class) {
                    f.set(row, c.getDouble(colIndex));
                }
                else if (clazz == String.class
                        || clazz == char[].class
                        || clazz == CharSequence.class) {
                    f.set(row, c.getString(colIndex));
                }
            }
            modelList.add(row);
            c.moveToNext();
        }
        return modelList;

    }

}
