package com.winjit.swiperewards.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.helpers.ValidationHelper;


public class ContactUsFragment extends BaseFragment implements View.OnClickListener {
    private Spinner spFeedbackType;
    private TextInputEditText etFeedback;
    private Button btnSubmit;

    public ContactUsFragment() {
    }

    public static ContactUsFragment newInstance() {
        Bundle args = new Bundle();
        ContactUsFragment fragment = new ContactUsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View mRootView) {
        spFeedbackType = (Spinner) mRootView.findViewById(R.id.sp_feedback_type);
        etFeedback = (TextInputEditText) mRootView.findViewById(R.id.et_feedback);
        btnSubmit = (Button) mRootView.findViewById(R.id.bt_submit);
        btnSubmit.setOnClickListener(this);

        String[] dummyArray = new String[]{"Select type", "item 1", "item 2", "item 3"};

        setAdaptersForSpinners(getActivity(), spFeedbackType, dummyArray);
    }


    public void setAdaptersForSpinners(Context context, Spinner spinner, String[] dropDownValues) {
        if (dropDownValues != null && dropDownValues.length > 0) {
            spinner.setAdapter(new ArrayAdapter<>(context, R.layout.row_spinner, dropDownValues));
        }


        //Setting account details after user select the VPA.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(ContextCompat.getColor(getActivity(), R.color.grey_500));
                } else {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(ContextCompat.getColor(getActivity(), R.color.color_text));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                if (isValidInputsEntered() && isFeedbackTypeSelected())
                    new UIHelper().popFragment(getActivity().getSupportFragmentManager());
                break;
        }
    }

    private boolean isFeedbackTypeSelected() {
        if (spFeedbackType.getSelectedItemPosition() > 0) {
            return true;
        } else {
            showError("Please select feedback type");
        }
        return false;
    }

    private boolean isValidInputsEntered() {
        ValidationHelper validationHelper = new ValidationHelper();
        return validationHelper.isValidEditTexts(getActivity(), etFeedback);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((HomeActivity) getActivity()) != null) {
            ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_CONTACT_US);
        }
    }
}
