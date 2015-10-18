package in.championswimmer.refuel.uihelper;

import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        holder.fuelFilled.setText(String.valueOf(refuelHistoryList.get(position).getFuelFilled()));

    }




    @Override
    public int getItemCount() {
        return refuelHistoryList.size();
    }

    public class RefuelEntryViewHolder extends RecyclerView.ViewHolder {

        TextView fuelFilled;

        public RefuelEntryViewHolder(View itemView) {
            super(itemView);
            fuelFilled = (TextView) itemView.findViewById(R.id.tv_fuel_filled);
        }


    }
}
