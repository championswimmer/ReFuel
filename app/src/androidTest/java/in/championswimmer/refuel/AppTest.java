package in.championswimmer.refuel;

import android.Manifest;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;
import android.support.test.runner.lifecycle.ApplicationLifecycleCallback;
import android.support.test.runner.lifecycle.ApplicationLifecycleMonitor;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.test.InstrumentationTestRunner;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by championswimmer on 31/12/15.
 */
@RunWith(AndroidJUnit4.class)
public class AppTest {

    private Application app;
    public final InstrumentationTestCase instRunner = new InstrumentationTestCase();

    @Before
    public void setUp() throws Exception {

        instRunner.injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        app = Instrumentation.newApplication(Application.class, instRunner.getInstrumentation().getTargetContext());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAppName() throws Exception {
        Assert.assertEquals(BuildConfig.APPLICATION_ID, app.getPackageName());
    }

    @Test
    public void testAppVersion() throws Exception {
        PackageInfo pInfo = app.getPackageManager().getPackageInfo(app.getPackageName(), 0);
        Assert.assertEquals(BuildConfig.VERSION_CODE, pInfo.versionCode);
        Assert.assertEquals(BuildConfig.VERSION_NAME, pInfo.versionName);
    }
}
