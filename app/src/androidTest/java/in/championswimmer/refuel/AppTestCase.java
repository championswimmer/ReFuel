package in.championswimmer.refuel;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.test.RenamingDelegatingContext;

/**
 * Created by championswimmer on 30/12/15.
 */
public class AppTestCase extends ApplicationTestCase<Application> {

    protected RenamingDelegatingContext testContext;
    private Context mContext;

    public AppTestCase() {
        super(Application.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        testContext = new RenamingDelegatingContext(getContext(), "test_");
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
