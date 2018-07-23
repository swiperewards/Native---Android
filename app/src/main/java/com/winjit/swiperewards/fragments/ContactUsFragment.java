package com.winjit.swiperewards.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.winjit.swiperewards.entities.TicketType;
import com.winjit.swiperewards.helpers.ValidationHelper;
import com.winjit.swiperewards.mvpviews.TicketView;
import com.winjit.swiperewards.presenters.TicketPresenter;


public class ContactUsFragment extends BaseFragment implements View.OnClickListener,TicketView {
    private Spinner spFeedbackType;
    private TextInputEditText etFeedback;
    private Button btnSubmit;
    private TicketPresenter ticketPresenter;
    private TicketType[] ticketTypes;

    public static ContactUsFragment newInstance() {
        Bundle args = new Bundle();
        ContactUsFragment fragment = new ContactUsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ticketPresenter = new TicketPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        initViews(view);
        showProgress(getActivity().getResources().getString(R.string.please_wait));
        ticketPresenter.getTicketTypes();
        return view;
    }

    private void initViews(View mRootView) {
        spFeedbackType = (Spinner) mRootView.findViewById(R.id.sp_feedback_type);
        etFeedback = (TextInputEditText) mRootView.findViewById(R.id.et_feedback);
        btnSubmit = (Button) mRootView.findViewById(R.id.bt_submit);
        btnSubmit.setOnClickListener(this);


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
                if (isValidInputsEntered() && isFeedbackTypeSelected()){
                    showProgress(getActivity().getResources().getString(R.string.please_wait));
                    int ticketTypeId = getSelectedTicketTypeId(spFeedbackType.getSelectedItemPosition()-1);
                    ticketPresenter.generateTicketRequest(ticketTypeId,ISwipe.USER_CATEGORY,etFeedback.getText().toString());
                }
                break;
        }
    }

    private int getSelectedTicketTypeId(int selectedItemPosition) {
        try{
            return ticketTypes[selectedItemPosition].getId();
        }catch (ArrayIndexOutOfBoundsException ae){
            return 0;
        }catch (Exception e){
            return 0;
        }
    }

    private boolean isFeedbackTypeSelected() {
        if (spFeedbackType.getSelectedItemPosition() > 0) {
            return true;
        } else {
            showMessage("Please select feedback type");
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

    @Override
    public void onTicketTypesReceived(TicketType[] ticketTypes) {
        this.ticketTypes = ticketTypes;
        setAdaptersForSpinners(getActivity(), spFeedbackType, getStringArrayForAdapter(ticketTypes));
    }

    private String[] getStringArrayForAdapter(TicketType[] ticketTypes) {
        String[] ticketArrayForAdapter = new String[ticketTypes.length+1];
        ticketArrayForAdapter[0] = "Select ticket type";
        for (int i = 0; i < ticketTypes.length; i++) {
            ticketArrayForAdapter[i+1] = ticketTypes[i].getTicketTypeName();
        }
        return ticketArrayForAdapter;
    }

    @Override
    public void onTicketRaisedSuccessfully() {
        showLongToast(getActivity().getResources().getString(R.string.ticket_raised));
        clearInputFields();

    }

    private void clearInputFields() {
        spFeedbackType.setSelection(0);
        etFeedback.setText("");
    }
}
