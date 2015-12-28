package in.championswimmer.refuel.database.models;

import in.championswimmer.refuel.database.Model;

/**
 * Created by championswimmer on 29/12/15.
 */
public class ReFuel extends Model{

    public ReFuel() {
    }



    Integer odoReading;
    Integer fuelFilled;

    public Integer getOdoReading() {
        return odoReading;
    }

    public ReFuel(Integer odoReading, Integer fuelFilled, Double rate) {
        this.odoReading = odoReading;
        this.fuelFilled = fuelFilled;
        this.rate = rate;
    }

    public void setOdoReading(Integer odoReading) {
        this.odoReading = odoReading;
    }

    public Integer getFuelFilled() {
        return fuelFilled;
    }

    public void setFuelFilled(Integer fuelFilled) {
        this.fuelFilled = fuelFilled;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    Double rate;


}
