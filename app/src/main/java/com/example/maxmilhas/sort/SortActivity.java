package com.example.maxmilhas.sort;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.maxmilhas.R;
import com.example.maxmilhas.Utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SortActivity extends AppCompatActivity {

    public static final int CODE_GET_ORDER = 500;

    @BindView(R.id.radio_group_order)
    public RadioGroup orderRadioGroup;

    @BindView(R.id.toolbar_title)
    public TextView backTitleToolbarTextView;

    @OnClick(R.id.image_button_back)
    public void backImageButtonClicked() {
        finish();
    }

    @OnClick(R.id.button_apply_order)
    public void onApplyOrderButtonClicked() {
        // get selected radio button from radioGroup
        int selectedId = orderRadioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton radioButton = (RadioButton) findViewById(selectedId);

        if (radioButton != null) {
            // save checked option
            PreferenceUtils.set(PreferenceUtils.ORDER, radioButton.getTag().toString());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("order_type", radioButton.getTag().toString());
            setResult(Activity.RESULT_OK, returnIntent);
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        ButterKnife.bind(this);

        initializeUI();
    }

    private void initializeUI() {
        backTitleToolbarTextView.setText(getResources().getString(R.string.activity_order_toolbar_title));
        String selectedOrderTag = PreferenceUtils.get(PreferenceUtils.ORDER, String.class);
        if (selectedOrderTag != null) {
            int selectedId = orderRadioGroup.findViewWithTag(selectedOrderTag).getId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            radioButton.setChecked(true);
        }
    }

    public static void startActivityForResult(Activity activity) {
        Intent intent = new Intent(activity, SortActivity.class);
        ((Activity) activity).startActivityForResult(intent, CODE_GET_ORDER);
    }
}
