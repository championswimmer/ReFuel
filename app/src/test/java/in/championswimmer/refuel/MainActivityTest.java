package in.championswimmer.refuel;

import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.*;

/**
 * Created by championswimmer on 31/12/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "./src/main/AndroidManifest.xml")
public class MainActivityTest {
    MainActivity mainActivity;
    ActivityController<MainActivity> mainActivityActivityController;

    @Before
    public void setUp() throws Exception {

        mainActivityActivityController = Robolectric.buildActivity(MainActivity.class);
        mainActivityActivityController.create();
        mainActivity = mainActivityActivityController.get();

    }

    @After
    public void tearDown() throws Exception {
        mainActivityActivityController.destroy();
    }

    @Test
    public void testCheckOnCreate() throws Exception {
        assertEquals("Rohini Delhi", ((TextView)mainActivity.findViewById(R.id.hellotext)).getText().toString());

    }
}
