package in.championswimmer.refuel.uihelper;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import in.championswimmer.refuel.R;
import in.championswimmer.refuel.pojos.RefuelEntry;

/**
 * Created by championswimmer on 19/10/15.
 */
public class RefuelEntryCardAdapter extends RecyclerView.Adapter<RefuelEntryCardAdapter.RefuelEntryViewHolder> {

    ArrayList<RefuelEntry> refuelHistoryList;

    public RefuelEntryCardAdapter(ArrayList<RefuelEntry> refuelHistoryList) {
        this.refuelHistoryList = refuelHistoryList;
    }

    public void updateRefuelHistory(ArrayList<RefuelEntry> refuelHistoryList) {
        this.refuelHistoryList = refuelHistoryList;
        notifyDataSetChanged();
    }

    @Override
    public RefuelEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_refuel_entry, parent, false);
        RefuelEntryViewHolder revh = new RefuelEntryViewHolder(v);
        return revh;
    }

    @Override
    public void onBindViewHolder(RefuelEntryViewHolder holder, int position) {
        String rateFormatted = new DecimalFormat("####0.00").format(refuelHistoryList.get(position).getRatePerLit());
        String ratePerLit = "\u20b9" + rateFormatted + "/L";
        String fuelFormatted = new DecimalFormat("####0.00").format(refuelHistoryList.get(position).getFuelFilled());
        String fuelFilled = fuelFormatted + " L";
        String odometerReading = String.valueOf(refuelHistoryList.get(position).getOdometer()) + " Km";
        String moneyPaid = "\u20b9 " + String.valueOf(refuelHistoryList.get(position).getMoneyPaid());

        holder.fuelFilled.setText(fuelFilled);
        holder.ratePerLit.setText(ratePerLit);
        holder.odometerReading.setText(odometerReading);
        holder.moneyPaid.setText(moneyPaid);

    }




    @Override
    public int getItemCount() {
        return refuelHistoryList.size();
    }

    public class RefuelEntryViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView fuelFilled;
        AppCompatTextView ratePerLit;
        AppCompatTextView odometerReading;
        AppCompatTextView moneyPaid;

        public RefuelEntryViewHolder(View itemView) {
            super(itemView);
            fuelFilled = (AppCompatTextView) itemView.findViewById(R.id.tv_fuel_filled);
            ratePerLit = (AppCompatTextView) itemView.findViewById(R.id.tv_rate_per_lit);
            odometerReading = (AppCompatTextView) itemView.findViewById(R.id.tv_odometer_reading);
            moneyPaid = (AppCompatTextView) itemView.findViewById(R.id.tv_money_paid);
        }


    }
}
