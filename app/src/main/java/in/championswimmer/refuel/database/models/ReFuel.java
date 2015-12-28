package in.championswimmer.refuel.database.models;

import android.support.annotation.NonNull;

import in.championswimmer.refuel.database.Model;

/**
 * Created by championswimmer on 29/12/15.
 */
public class ReFuel extends Model {

    Integer odoReading;
    Integer fuelFilled;
    Double rate;

    public ReFuel() {
    }

    public ReFuel(@NonNull Integer odoReading, @NonNull Integer fuelFilled, @NonNull Double rate) {
        this.odoReading = odoReading;
        this.fuelFilled = fuelFilled;
        this.rate = rate;
    }

    public Integer getOdoReading() {
        return odoReading;
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


}
