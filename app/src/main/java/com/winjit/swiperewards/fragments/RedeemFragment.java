package com.winjit.swiperewards.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.interfaces.MessageDialogConfirm;
import com.winjit.swiperewards.utils.UIHelper;
import com.winjit.swiperewards.utils.ValidationHelper;


public class RedeemFragment extends Fragment implements View.OnClickListener {
    private AppCompatSpinner spRedeemVia;
    private AppCompatSpinner spRedeemVendor;
    private TextInputEditText etAccountNumber;
    private AppCompatEditText etAmount;
    private Button btConfirm;

    public RedeemFragment() {
    }

    public static RedeemFragment newInstance() {
        Bundle args = new Bundle();
        RedeemFragment fragment = new RedeemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_redeem, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View mRootView) {

        spRedeemVia = (AppCompatSpinner) mRootView.findViewById(R.id.sp_redeem_via);
        spRedeemVendor = (AppCompatSpinner) mRootView.findViewById(R.id.sp_redeem_vendor);
        etAccountNumber = (TextInputEditText) mRootView.findViewById(R.id.et_account_number);
        etAmount = (AppCompatEditText) mRootView.findViewById(R.id.et_amount);
        btConfirm = (Button) mRootView.findViewById(R.id.bt_confirm);
        setListeners();
        String[] dummyArray = new String[]{"item 1", "item 2", "item 3"};
        String[] dummySubArray = new String[]{"sub-item 1", "sub-item 2", "sub-item 3"};
        setAdaptersForSpinners(getActivity(), spRedeemVia, dummyArray);
        setAdaptersForSpinners(getActivity(), spRedeemVendor, dummySubArray);
    }

    private void setListeners() {
        btConfirm.setOnClickListener(this);

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && !charSequence.equals("")) {
                    String strAmount = etAmount.getText().toString();
                    int indexOfDec = strAmount.indexOf(".");

                    if (indexOfDec >= 0) {
                        if (strAmount.substring(indexOfDec).length() > 3) {
                            strAmount = strAmount.substring(0, strAmount.length() - 1);
                            etAmount.setText(strAmount);
                            etAmount.setSelection(strAmount.length());
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    public void setAdaptersForSpinners(Context context, Spinner spinner, String[] dropDownValues) {
        if (dropDownValues != null && dropDownValues.length > 0) {
            spinner.setAdapter(new ArrayAdapter<>(context, R.layout.row_spinner, dropDownValues));
        }


        //Setting account details after user select the VPA.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirm:
                showConfirmationDialog();
                break;
        }
    }


    private void showConfirmationDialog(){
        String dialogInterfaceMessage = "You are creating a redeem request of $XX.XX to your account XXXX. Do you want to continue?";

        UIHelper.configureShowConfirmDialog(dialogInterfaceMessage, getActivity(),
                R.string.confirm, R.string.btn_cancel,
                new MessageDialogConfirm() {
                    @Override
                    public void onPositiveClick() {
                        ((HomeActivity) getActivity()).setDefaultHomeIndex();
                    }

                    @Override
                    public void onNegativeClick() {
                    }
                });

    }


    private boolean isValidInputsEntered() {
        ValidationHelper validationHelper = new ValidationHelper();
        return validationHelper.isValidEditTexts(getActivity(), etAccountNumber, etAmount);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((HomeActivity) getActivity()) != null) {
            ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_REDEEM);
        }
    }
}
