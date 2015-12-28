package in.championswimmer.refuel.database;

/**
 * Created by championswimmer on 28/12/15.
 */
public abstract class Column implements CharSequence {

    private final String actualString;

    public Column(String actualString) {
        this.actualString = actualString;
    }

    public String getColName() {
        return getClass().getSimpleName();
    }

    public abstract TYPE getType();

    @Override
    public int length() {
        return actualString.length();
    }

    @Override
    public char charAt(int i) {
        return actualString.charAt(i);
    }

    @Override
    public CharSequence subSequence(int i, int i1) {
        return actualString.subSequence(i, i1);
    }

    private enum TYPE {
        REAL("REAL"),
        TEXT("TEXT"),
        INTEGER("INTEGER");
        private String value;

        private TYPE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public class Integer extends Column {

        public Integer(int v) {
            super(String.valueOf(v));
        }

        public Integer(long v) {
            super(String.valueOf(v));
        }

        public TYPE getType() {
            return TYPE.INTEGER;
        }
    }

    public class Text extends Column {
        public Text(char[] v) {
            super(String.valueOf(v));
        }

        public Text(String v) {
            super(String.valueOf(v));
        }

        public TYPE getType() {
            return TYPE.TEXT;
        }
    }
}
