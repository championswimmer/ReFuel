package in.championswimmer.refuel;

import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by championswimmer on 31/12/15.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {


    private MainActivity mainActivity;

    @Rule
    public final ActivityTestRule<MainActivity> act = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        mainActivity = act.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mainActivity.finish();
    }

    @Test
    public void testOnCreate() throws Exception {
        Assert.assertEquals("Rohini Delhi", ((TextView)mainActivity.findViewById(R.id.hellotext)).getText().toString());
    }
}
