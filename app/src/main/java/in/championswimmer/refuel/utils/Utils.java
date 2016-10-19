package in.championswimmer.refuel.utils;

/**
 * Created by championswimmer on 30/12/15.
 */
public class Utils {

    public static String[] concat(String[] A, String[] B) {
        int aLen = A.length;
        int bLen = B.length;
        String[] C= new String[aLen+bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
    }
}
