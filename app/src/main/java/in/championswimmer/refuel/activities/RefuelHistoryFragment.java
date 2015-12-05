package in.championswimmer.refuel.activities;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.championswimmer.refuel.R;
import in.championswimmer.refuel.dbhelper.RefuelDbHelper;
import in.championswimmer.refuel.dialogs.AddRefuelEntryDialog;
import in.championswimmer.refuel.uihelper.RefuelEntryCardAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RefuelHistoryFragment extends Fragment {

    RecyclerView rvHistoryContainer;


    public static RefuelHistoryFragment newInstance() {

        Bundle args = new Bundle();

        RefuelHistoryFragment fragment = new RefuelHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }



    public RefuelHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_refuel_history, container, false);
        rvHistoryContainer = (RecyclerView) rootView.findViewById(R.id.refuel_history_container);
        rvHistoryContainer.setLayoutManager(new LinearLayoutManager(getActivity()));
        final RefuelEntryCardAdapter rfEntryCardAdapter = new RefuelEntryCardAdapter(RefuelDbHelper.getRefuelHistory(getActivity().getApplicationContext()));
        rvHistoryContainer.setAdapter(rfEntryCardAdapter);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddRefuelEntryDialog.newInstance(getActivity(), new AddRefuelEntryDialog.RefuelUpdateListener() {
                    @Override
                    public void onRefuelUpdate() {
                        rfEntryCardAdapter.updateRefuelHistory(RefuelDbHelper.getRefuelHistory(getContext().getApplicationContext()));
                    }
                }).show();
            }
        });

        return rootView;
    }

}
