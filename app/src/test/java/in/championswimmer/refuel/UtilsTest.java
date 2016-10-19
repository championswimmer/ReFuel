package in.championswimmer.refuel;

import org.junit.Test;

import in.championswimmer.refuel.utils.Utils;

import static org.junit.Assert.*;


/**
 * Created by championswimmer on 30/12/15.
 */
public class UtilsTest {

    @Test
    public void arrayConcat_works() throws Exception {
        assertArrayEquals(new String[]{"A", "B", "C", "D"}, Utils.concat(new String[]{"A", "B"}, new String[] {"C", "D"}));
    }
}
