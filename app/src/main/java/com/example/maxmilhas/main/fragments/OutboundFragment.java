package com.example.maxmilhas.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maxmilhas.R;
import com.example.maxmilhas.api.models.Flight;
import com.example.maxmilhas.api.models.Outbound;
import com.example.maxmilhas.main.adapters.OutboundAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutboundFragment extends Fragment implements OutboundAdapter.ItemClickListener {

    OutboundAdapter adapter;

    @BindView(R.id.text_view_outbound)
    TextView textViewOutbound;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static OutboundFragment newInstance() {
        return new OutboundFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), getResources().getString(R.string.outbound_fragment_informative_label) + adapter.getItem(position).pricing.getSaleTotal(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void updateRecyclerView(Flight flight) {
        adapter.updateList(flight.outbound);
    }

    public void sortFlights(int type) {
        switch (type) {
            case 1:
                adapter.sortByBiggestPrice();
                break;
            case 2:
                adapter.sortBySmallerPrice();
                break;
            case 3:
                adapter.sortBySmallerPriceAndFlightDuration();
                break;
        }
    }

    public void filterFlights(ArrayList<String> resultSelectedTimeFilters, ArrayList<String> resultSelectedStopsFilters) {
        adapter.filterAll(resultSelectedTimeFilters, resultSelectedStopsFilters);
        if (resultSelectedTimeFilters.isEmpty() && resultSelectedStopsFilters.isEmpty()) {
            textViewOutbound.setVisibility(View.GONE);
        } else {
            textViewOutbound.setVisibility(View.VISIBLE);
            textViewOutbound.setText(getResources().getString(R.string.outbound_fragment_filters) + " " + adapter.getFilteredItemCount() + " " + getResources().getString(R.string.outbound_fragment_filters2));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outbound, container, false);

        ButterKnife.bind(this, view);

        // data to populate the RecyclerView
        ArrayList<Outbound> outboundsFlights = new ArrayList<>();
        initializeUI(outboundsFlights);

        return view;
    }

    private void initializeUI(ArrayList<Outbound> outboundFlights) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new OutboundAdapter(getActivity(), outboundFlights);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setFocusable(false);
    }
}
