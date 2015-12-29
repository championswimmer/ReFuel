package in.championswimmer.refuel.models;

/**
 * Created by championswimmer on 30/12/15.
 */
public class Refuel {

    int id;
    double odometer;
    double rate;
    double litre;
    String pumpName;
    boolean fullTank;
    Expense expense;

    public Refuel(int id, double odometer, double rate, double litre, String pumpName, boolean fullTank, Expense expense) {
        this.id = id;
        this.odometer = odometer;
        this.rate = rate;
        this.litre = litre;
        this.pumpName = pumpName;
        this.fullTank = fullTank;
        this.expense = expense;
    }

    public Refuel(double odometer, double rate, double litre, String pumpName, boolean fullTank, Expense expense) {
        this.odometer = odometer;
        this.rate = rate;
        this.litre = litre;
        this.pumpName = pumpName;
        this.fullTank = fullTank;
        this.expense = expense;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getOdometer() {
        return odometer;
    }

    public void setOdometer(double odometer) {
        this.odometer = odometer;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getLitre() {
        return litre;
    }

    public void setLitre(double litre) {
        this.litre = litre;
    }

    public String getPumpName() {
        return pumpName;
    }

    public void setPumpName(String pumpName) {
        this.pumpName = pumpName;
    }

    public boolean isFullTank() {
        return fullTank;
    }

    public void setFullTank(boolean fullTank) {
        this.fullTank = fullTank;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}
