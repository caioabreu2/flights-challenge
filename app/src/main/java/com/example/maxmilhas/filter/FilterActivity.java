package com.example.maxmilhas.filter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.maxmilhas.R;
import com.example.maxmilhas.Utils.PreferenceUtils;
import com.example.maxmilhas.api.models.Filter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity {

    public static final int CODE_GET_FILTER = 600;

    Filter filter;

    @BindView(R.id.layout_with_views)
    RelativeLayout relativeLayout;

    @BindView(R.id.afternoon)
    CheckBox afternoonCheckbox;

    @BindView(R.id.morning)
    CheckBox morningCheckbox;

    @BindView(R.id.night)
    CheckBox nightCheckbox;

    @BindView(R.id.dawn)
    CheckBox dawnCheckbox;

    @BindView(R.id.nonstop_flight)
    CheckBox nonStopCheckbox;

    @BindView(R.id.one_stop_flight)
    CheckBox oneStopCheckbox;

    @BindView(R.id.toolbar_title)
    public TextView backTitleToolbarTextView;

    @BindView(R.id.button_clear)
    public Button clearFilterButton;

    @OnClick(R.id.image_button_back)
    public void backImageButtonClicked() {
        finish();
    }

    @OnClick(R.id.button_clear)
    public void onClearFiltersButtonClicked() {
        afternoonCheckbox.setChecked(false);
        morningCheckbox.setChecked(false);
        nightCheckbox.setChecked(false);
        dawnCheckbox.setChecked(false);
        nonStopCheckbox.setChecked(false);
        oneStopCheckbox.setChecked(false);
    }

    @OnClick(R.id.button_apply_filter)
    public void onApplyOrderButtonClicked() {
        List<String> selectedTimeFilters = new ArrayList<>();
        List<String> selectedStopsFilters = new ArrayList<>();

        if (afternoonCheckbox.isChecked()) {
            selectedTimeFilters.add(afternoonCheckbox.getTag().toString());
        }
        if (morningCheckbox.isChecked()) {
            selectedTimeFilters.add(morningCheckbox.getTag().toString());
        }
        if (nightCheckbox.isChecked()) {
            selectedTimeFilters.add(nightCheckbox.getTag().toString());
        }
        if (dawnCheckbox.isChecked()) {
            selectedTimeFilters.add(dawnCheckbox.getTag().toString());
        }

        if (nonStopCheckbox.isChecked()) {
            selectedStopsFilters.add(nonStopCheckbox.getTag().toString());
        }
        if (oneStopCheckbox.isChecked()) {
            selectedStopsFilters.add(oneStopCheckbox.getTag().toString());
        }

        if (selectedTimeFilters.isEmpty() && selectedStopsFilters.isEmpty()) {
            resetFilters();
        } else {
            // save filter preferences
            ArrayList<String> allFilters = new ArrayList<String>(selectedTimeFilters);
            allFilters.addAll(selectedStopsFilters);
            filter = new Filter(allFilters);
            PreferenceUtils.set(PreferenceUtils.FILTERS, filter);
        }

        Intent returnIntent = new Intent();
        returnIntent.putStringArrayListExtra("filter_time_selected_types", (ArrayList<String>) selectedTimeFilters);
        returnIntent.putStringArrayListExtra("filter_stops_selected_types", (ArrayList<String>) selectedStopsFilters);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        ButterKnife.bind(this);

        initializeUI();
    }

    private void initializeUI() {
        backTitleToolbarTextView.setText(getResources().getString(R.string.activity_filter_toolbar_title));
        clearFilterButton.setVisibility(View.VISIBLE);
        initializeFilters();
    }

    private void initializeFilters() {
        Filter filters = PreferenceUtils.get(PreferenceUtils.FILTERS, Filter.class);
        if (filters != null) {
            for (String filterTag : filters.getSelectedFilters()) {
                CheckBox checkBox = (CheckBox) relativeLayout.findViewWithTag(filterTag);
                checkBox.setChecked(true);
            }
        }
    }

    private void resetFilters() {
        PreferenceUtils.removeByKey(PreferenceUtils.FILTERS);
    }

    public static void startActivityForResult(Activity activity) {
        Intent intent = new Intent(activity, FilterActivity.class);
        ((Activity) activity).startActivityForResult(intent, CODE_GET_FILTER);
    }
}
