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
import com.example.maxmilhas.api.models.Inbound;

import com.example.maxmilhas.main.adapters.InboundAdapter;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InboundFragment extends Fragment implements InboundAdapter.ItemClickListener {

    InboundAdapter adapter;

    @BindView(R.id.text_view_inbound)
    TextView textViewInbound;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static InboundFragment newInstance() {
        return new InboundFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), getResources().getString(R.string.inbound_fragment_informative_toast) + adapter.getItem(position).pricing.getSaleTotal(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void updateRecyclerView(Flight flight) {
        adapter.updateList(flight.inbound);
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
            textViewInbound.setVisibility(View.GONE);
        } else {
            textViewInbound.setVisibility(View.VISIBLE);
            textViewInbound.setText(getResources().getString(R.string.inbound_fragment_filters_label) + " " + adapter.getFilteredItemCount() + " " + getResources().getString(R.string.inbound_fragment_filters_label2));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbound, container, false);

        ButterKnife.bind(this, view);

        // data to populate the RecyclerView
        ArrayList<Inbound> inboundsFlights = new ArrayList<>();
        initializeUI(inboundsFlights);

        return view;
    }

    private void initializeUI(ArrayList<Inbound> inboundFlights) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new InboundAdapter(getActivity(), inboundFlights);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setFocusable(false);
    }
}