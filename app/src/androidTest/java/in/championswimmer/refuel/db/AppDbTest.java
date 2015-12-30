package in.championswimmer.refuel.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import in.championswimmer.refuel.AppTestCase;

/**
 * Created by championswimmer on 30/12/15.
 */
public class AppDbTest extends AppTestCase {

    public static final String TAG = "AppDbTest";

    private SQLiteDatabase db = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        db = AppDb.getInstance(testContext).getWritableDatabase();
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }

    public void testDbVer() throws Exception {
        assertTrue(db.isOpen());
        assertEquals(AppDb.DB_VER, db.getVersion());
    }

    public void testDbName() throws Exception {
        assertEquals(AppDb.DB_NAME, AppDb.getInstance(testContext).getDatabaseName());
    }

    private String checkTableCmd(String tableName) {
        String sqlCmd = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "';";
        Cursor c = db.rawQuery(sqlCmd, null);
        c.moveToFirst();
        String tblName = c.getString(c.getColumnIndexOrThrow("name"));
        c.close();
        return tblName;
    }

    public void testCreateTables() throws Exception {
        db.execSQL(ExpenseTable.CMD_CREATE_TABLE);
        assertEquals(ExpenseTable.TABLE_NAME, checkTableCmd(ExpenseTable.TABLE_NAME));
        db.execSQL(RefuelTable.CMD_CREATE_TABLE);
        assertEquals(RefuelTable.TABLE_NAME, checkTableCmd(RefuelTable.TABLE_NAME));
    }
}
