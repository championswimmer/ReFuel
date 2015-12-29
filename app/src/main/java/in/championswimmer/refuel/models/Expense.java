package in.championswimmer.refuel.models;

/**
 * Created by championswimmer on 30/12/15.
 */
public class Expense {

    int id;
    double amount;
    long timestamp;
    String desc;
    String type;

    public Expense(int id, double amount, long timestamp, String desc, String type) {
        this.id = id;
        this.amount = amount;
        this.timestamp = timestamp;
        this.desc = desc;
        this.type = type;
    }

    public Expense(double amount, long timestamp, String desc, String type) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.desc = desc;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
