package in.championswimmer.refuel.pojos;

/**
 * Created by championswimmer on 18/10/15.
 */
public class RefuelEntry {

    Float fuelFilled;
    Float ratePerLit;
    Float moneyPaid;
    Integer odometer;
    Long timestamp;

    public RefuelEntry(Float fuelFilled, Float ratePerLit, Float moneyPaid, Integer odometer, Long timestamp) {
        this.fuelFilled = fuelFilled;
        this.ratePerLit = ratePerLit;
        this.moneyPaid = moneyPaid;
        this.odometer = odometer;
        this.timestamp = timestamp;
    }

    public Float getFuelFilled() {
        return fuelFilled;
    }

    public void setFuelFilled(Float fuelFilled) {
        this.fuelFilled = fuelFilled;
    }

    public Float getRatePerLit() {
        return ratePerLit;
    }

    public void setRatePerLit(Float ratePerLit) {
        this.ratePerLit = ratePerLit;
    }

    public Float getMoneyPaid() {
        return moneyPaid;
    }

    public void setMoneyPaid(Float moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
