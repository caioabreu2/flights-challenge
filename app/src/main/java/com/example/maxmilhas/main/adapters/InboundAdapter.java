package com.example.maxmilhas.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maxmilhas.R;
import com.example.maxmilhas.Utils.TimeUtils;
import com.example.maxmilhas.api.models.Inbound;
import com.example.maxmilhas.api.models.Outbound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InboundAdapter extends RecyclerView.Adapter<InboundAdapter.ViewHolder> {

    private List<Inbound> mData;
    private LayoutInflater mInflater;
    private InboundAdapter.ItemClickListener mClickListener;

    public InboundAdapter(Context context, List<Inbound> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public InboundAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new InboundAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InboundAdapter.ViewHolder holder, int position) {
        Inbound flight = mData.get(position);

        // hide flights that do not match filters
        // probably not the best solution for performance, but it works okay =)
        if (!flight.isInboundEnable()) {
            ViewGroup.LayoutParams params = holder.layoutFlight.getLayoutParams();
            params.height = 0;
            ((ViewGroup.MarginLayoutParams) params).topMargin = 0;
            holder.layoutFlight.setLayoutParams(params);
        } else {
            ViewGroup.LayoutParams params = holder.layoutFlight.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
            ((ViewGroup.MarginLayoutParams) params).topMargin = 25;
            holder.layoutFlight.setLayoutParams(params);
        }

        holder.departureTextView.setText(flight.from);
        holder.departureTimeTextView.setText(TimeUtils.getFormattedTime(flight.departureDate));
        holder.arrivalTextView.setText(flight.to);
        holder.arrivalTimeTextView.setText(TimeUtils.getFormattedTime(flight.arrivalDate));
        holder.durationStopsTextView.setText(flight.getFormattedDurationAndStops());
        holder.totalPriceTextView.setText(flight.pricing.getFormattedTotalPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public int getFilteredItemCount() {
        int count = 0;
        for (Inbound inbound : mData) {
            if (inbound.isInboundEnable())
                count++;
        }
        return count;
    }

    public void updateList(List<Inbound> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void sortBySmallerPrice() {
        Comparator<Inbound> compareByPrice = new Comparator<Inbound>() {
            @Override
            public int compare(Inbound o1, Inbound o2) {
                return o1.pricing.getSaleTotal().compareTo(o2.pricing.getSaleTotal());
            }
        };

        Collections.sort(mData, compareByPrice);
        notifyDataSetChanged();
    }

    public void sortByBiggestPrice() {
        Comparator<Inbound> compareByPrice = new Comparator<Inbound>() {
            @Override
            public int compare(Inbound o1, Inbound o2) {
                return o2.pricing.getSaleTotal().compareTo(o1.pricing.getSaleTotal());
            }
        };

        Collections.sort(mData, compareByPrice);
        notifyDataSetChanged();
    }

    public void sortBySmallerPriceAndFlightDuration() {
        Comparator<Inbound> priceDuration = new Comparator<Inbound>() {
            @Override
            public int compare(Inbound o1, Inbound o2) {
                Double x1 = ((Inbound) o1).pricing.getSaleTotal();
                Double x2 = ((Inbound) o2).pricing.getSaleTotal();
                int sComp = x1.compareTo(x2);

                if (sComp != 0) {
                    return sComp;
                }

                Integer x3 = ((Inbound) o1).duration;
                Integer x4 = ((Inbound) o2).duration;
                return x3.compareTo(x4);
            }
        };

        Collections.sort(mData, priceDuration);
        notifyDataSetChanged();
    }

    // maybe not the best way to filter, but I have only few hours to finish the test and it works okay
    public void filterAll(ArrayList<String> selectedTimeTypes, ArrayList<String> selectedStopsTypes) {
        List<Inbound> filteredTimeList = new ArrayList<>();
        List<Inbound> filteredStopsList = new ArrayList<>();
        Iterator<Inbound> it = mData.iterator();
        Iterator<Inbound> it2;

        // reset filters when apply again
        for(Inbound inbound : mData) {
            inbound.setInboundEnable(true);
        }

        // get selected filters
        boolean morning = selectedTimeTypes.contains("1");
        boolean afternoon = selectedTimeTypes.contains("2");
        boolean night = selectedTimeTypes.contains("3");
        boolean dawn = selectedTimeTypes.contains("4");
        boolean nonStop = selectedStopsTypes.contains("5");
        boolean oneStop = selectedStopsTypes.contains("6");

        // if nothing is selected, so just return
        if (!(morning || afternoon || night || dawn || nonStop || oneStop)) {
            notifyDataSetChanged();
            return;
        }

        // filter by time
        while(it.hasNext()) {
            Inbound inbound = it.next();
            boolean addTimeFilter = (inbound.isMorningTime() && morning) || (inbound.isAfternoonTime() && afternoon) || (inbound.isNightTime() && night) || (inbound.isDawnTime() && dawn);
            if (addTimeFilter) {
                filteredTimeList.add(inbound);
            }
        }

        if (filteredTimeList.isEmpty() && !(morning || afternoon || night || dawn)) {
            it2 = mData.iterator();
        } else {
            it2 = filteredTimeList.iterator();
        }

        // filter by flight stops
        while(it2.hasNext()) {
            Inbound inbound = it2.next();
            boolean addStopsFilter = (inbound.isNonStopFlight() && nonStop) || (inbound.isOneStopFlight() && oneStop);
            if (addStopsFilter) {
                filteredStopsList.add(inbound);
            }
        }

        // update results to main list
        if (!filteredStopsList.isEmpty()) {
            for(Inbound inbound : mData) {
                inbound.setInboundEnable(filteredStopsList.contains(inbound));
            }
        } else {
            for(Inbound inbound : mData) {
                inbound.setInboundEnable(filteredTimeList.contains(inbound) && !(nonStop || oneStop));
            }
        }

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.layout_flight)
        LinearLayout layoutFlight;

        @BindView(R.id.text_view_departure)
        TextView departureTextView;

        @BindView(R.id.text_view_departure_time)
        TextView departureTimeTextView;

        @BindView(R.id.text_view_arrival)
        TextView arrivalTextView;

        @BindView(R.id.text_view_arrival_time)
        TextView arrivalTimeTextView;

        @BindView(R.id.text_view_duration_stops)
        TextView durationStopsTextView;

        @BindView(R.id.text_view_price)
        TextView totalPriceTextView;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public Inbound getItem(int id) {
        return mData.get(id);
    }

    public void setClickListener(InboundAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}