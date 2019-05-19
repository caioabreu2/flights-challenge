package com.example.maxmilhas.main;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.maxmilhas.R;
import com.example.maxmilhas.Utils.PreferenceUtils;
import com.example.maxmilhas.api.models.Flight;
import com.example.maxmilhas.filter.FilterActivity;
import com.example.maxmilhas.main.adapters.FlightAdapter;
import com.example.maxmilhas.main.fragments.InboundFragment;
import com.example.maxmilhas.main.fragments.OutboundFragment;
import com.example.maxmilhas.sort.SortActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View, Communicator {

    MainContract.Presenter mainPresenter;

    FlightAdapter flightPagerAdapter;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.toolbar_title)
    public TextView backTitleToolbarTextView;

    @OnClick(R.id.image_button_back)
    public void backImageButtonClicked() {
        finish();
    }

    @OnClick(R.id.button_filter)
    public void onFilterBtnClicked() {
        FilterActivity.startActivityForResult(this);
    }

    @OnClick(R.id.button_order)
    public void onOrderBtnClicked() {
        SortActivity.startActivityForResult(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        PreferenceUtils.removeAll();
        initializeUI();

        mainPresenter = new MainPresenter(this);
        mainPresenter.getFlights();
    }

    private void initializeUI() {
        backTitleToolbarTextView.setText(getResources().getString(R.string.activity_main_toolbar_title));
        flightPagerAdapter = new FlightAdapter(getSupportFragmentManager());
        viewPager.setAdapter(flightPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SortActivity.CODE_GET_ORDER:
                if (resultCode == Activity.RESULT_OK) {
                    String result = data.getStringExtra("order_type");
                    sortAndUpdateFlights(result);
                }
                break;
            case FilterActivity.CODE_GET_FILTER:
                if (resultCode == Activity.RESULT_OK) {
                    ArrayList<String> resultSelectedTimeFilters = data.getStringArrayListExtra("filter_time_selected_types");
                    ArrayList<String> resultSelectedStopsFilters = data.getStringArrayListExtra("filter_stops_selected_types");
                    filterAndUpdateFlights(resultSelectedTimeFilters, resultSelectedStopsFilters);
                }
                break;
        }
    }

    @Override
    public void respond(Flight flight) {
        OutboundFragment outboundFragment = (OutboundFragment) flightPagerAdapter.getItem(0);
        InboundFragment inboundFragment = (InboundFragment) flightPagerAdapter.getItem(1);
        outboundFragment.updateRecyclerView(flight);
        inboundFragment.updateRecyclerView(flight);
    }

    @Override
    public void sortAndUpdateFlights(String type) {
        int typeInt = Integer.parseInt(type);
        OutboundFragment outboundFragment = (OutboundFragment) flightPagerAdapter.getItem(0);
        InboundFragment inboundFragment = (InboundFragment) flightPagerAdapter.getItem(1);
        outboundFragment.sortFlights(typeInt);
        inboundFragment.sortFlights(typeInt);
    }

    @Override
    public void filterAndUpdateFlights(ArrayList<String> resultSelectedTimeFilters, ArrayList<String> resultSelectedStopsFilters) {
        OutboundFragment outboundFragment = (OutboundFragment) flightPagerAdapter.getItem(0);
        InboundFragment inboundFragment = (InboundFragment) flightPagerAdapter.getItem(1);
        outboundFragment.filterFlights(resultSelectedTimeFilters, resultSelectedStopsFilters);
        inboundFragment.filterFlights(resultSelectedTimeFilters, resultSelectedStopsFilters);
    }
}
