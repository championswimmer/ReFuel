package in.championswimmer.refuel.db;

import android.app.Activity;
import android.app.RobolectricActivityManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.RenamingDelegatingContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

import in.championswimmer.refuel.BuildConfig;
import in.championswimmer.refuel.db.AppDb;
import in.championswimmer.refuel.db.ExpenseTable;
import in.championswimmer.refuel.db.RefuelTable;

/**
 * Created by championswimmer on 30/12/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml")
public class AppDbTest {

    private Activity mActivity;
    private SQLiteDatabase db;
    @Before
    public void setUp() throws Exception {
        Activity activity = Robolectric.setupActivity(Activity.class);
        activity.deleteDatabase(AppDb.DB_NAME);
        db = AppDb.getInstance(activity).getWritableDatabase();
    }

    @Test
    public void testDbVer() throws Exception {
        assertTrue(db.isOpen());
        assertEquals(AppDb.DB_VER, db.getVersion());
    }

    @Test
    public void testDbName() throws Exception {
        assertEquals(AppDb.DB_NAME, AppDb.getInstance(mActivity).getDatabaseName());
    }

    private String checkTableCmd(String tableName) {
        String sqlCmd = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "';";
        Cursor c = db.rawQuery(sqlCmd, null);
        c.moveToFirst();
        String tblName = c.getString(c.getColumnIndexOrThrow("name"));
        c.close();
        return tblName;
    }

    @Test
    public void testCreateTables() throws Exception {
        db.execSQL(ExpenseTable.CMD_CREATE_TABLE);
        assertEquals(ExpenseTable.TABLE_NAME, checkTableCmd(ExpenseTable.TABLE_NAME));
        db.execSQL(RefuelTable.CMD_CREATE_TABLE);
        assertEquals(RefuelTable.TABLE_NAME, checkTableCmd(RefuelTable.TABLE_NAME));
    }


    @After
    public void tearDown() throws Exception {
        db.close();

    }
}
