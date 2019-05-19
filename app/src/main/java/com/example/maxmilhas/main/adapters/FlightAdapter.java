package com.example.maxmilhas.main.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.maxmilhas.main.fragments.InboundFragment;
import com.example.maxmilhas.main.fragments.OutboundFragment;

public class FlightAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public FlightAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
        childFragments = new Fragment[] {
                OutboundFragment.newInstance(), // index: 0
                InboundFragment.newInstance() // index: 1
        };
    }

    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "VOO DE IDA";
            case 1:
                return "VOO DE VOLTA";
            default:
                return null;
        }
    }

}
