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
import com.example.maxmilhas.api.models.Outbound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutboundAdapter extends RecyclerView.Adapter<OutboundAdapter.ViewHolder> {

    private List<Outbound> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public OutboundAdapter(Context context, List<Outbound> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Outbound flight = mData.get(position);

        // hide flights that do not match filters
        // probably not the best solution for performance, but it works okay =)
        if (!flight.isOutboundEnable()) {
            ViewGroup.LayoutParams params = holder.layoutFlight.getLayoutParams();
            params.height = 0;
            ((ViewGroup.MarginLayoutParams) params).topMargin = 0;
            holder.layoutFlight.setLayoutParams(params);
        } else {
            ViewGroup.LayoutParams params = holder.layoutFlight.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
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
        for (Outbound outbound : mData) {
            if (outbound.isOutboundEnable())
                count++;
        }
        return count;
    }

    public void updateList(List<Outbound> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void sortBySmallerPrice() {
        Comparator<Outbound> compareByPrice = new Comparator<Outbound>() {
            @Override
            public int compare(Outbound o1, Outbound o2) {
                return o1.pricing.getSaleTotal().compareTo(o2.pricing.getSaleTotal());
            }
        };

        Collections.sort(mData, compareByPrice);
        notifyDataSetChanged();
    }

    public void sortByBiggestPrice() {
        Comparator<Outbound> compareByPrice = new Comparator<Outbound>() {
            @Override
            public int compare(Outbound o1, Outbound o2) {
                return o2.pricing.getSaleTotal().compareTo(o1.pricing.getSaleTotal());
            }
        };

        Collections.sort(mData, compareByPrice);
        notifyDataSetChanged();
    }

    public void sortBySmallerPriceAndFlightDuration() {
        Comparator<Outbound> priceDuration = new Comparator<Outbound>() {
            @Override
            public int compare(Outbound o1, Outbound o2) {
                Double x1 = ((Outbound) o1).pricing.getSaleTotal();
                Double x2 = ((Outbound) o2).pricing.getSaleTotal();
                int sComp = x1.compareTo(x2);

                if (sComp != 0) {
                    return sComp;
                }

                Integer x3 = ((Outbound) o1).duration;
                Integer x4 = ((Outbound) o2).duration;
                return x3.compareTo(x4);
            }
        };

        Collections.sort(mData, priceDuration);
        notifyDataSetChanged();
    }

    // maybe not the best way to filter, but I have only few hours to finish the test and it works okay
    public void filterAll(ArrayList<String> selectedTimeTypes, ArrayList<String> selectedStopsTypes) {
        List<Outbound> filteredTimeList = new ArrayList<>();
        List<Outbound> filteredStopsList = new ArrayList<>();
        Iterator<Outbound> it = mData.iterator();
        Iterator<Outbound> it2;

        // reset filters when apply again
        for(Outbound outbound : mData) {
            outbound.setOutboundEnable(true);
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
            Outbound outbound = it.next();
            boolean addTimeFilter = (outbound.isMorningTime() && morning) || (outbound.isAfternoonTime() && afternoon) || (outbound.isNightTime() && night) || (outbound.isDawnTime() && dawn);
            if (addTimeFilter) {
                filteredTimeList.add(outbound);
            }
        }

        if (filteredTimeList.isEmpty() && !(morning || afternoon || night || dawn)) {
            it2 = mData.iterator();
        } else {
            it2 = filteredTimeList.iterator();
        }

        // filter by flight stops
        while(it2.hasNext()) {
            Outbound outbound = it2.next();
            boolean addStopsFilter = (outbound.isNonStopFlight() && nonStop) || (outbound.isOneStopFlight() && oneStop);
            if (addStopsFilter) {
                filteredStopsList.add(outbound);
            }
        }

        // update results to main list
        if (!filteredStopsList.isEmpty()) {
            for(Outbound outbound : mData) {
                outbound.setOutboundEnable(filteredStopsList.contains(outbound));
            }
        } else {
            for(Outbound outbound : mData) {
                outbound.setOutboundEnable(filteredTimeList.contains(outbound) && !(nonStop || oneStop));
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

    public Outbound getItem(int id) {
        return mData.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
