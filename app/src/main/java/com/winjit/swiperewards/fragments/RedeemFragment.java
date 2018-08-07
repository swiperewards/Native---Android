package com.winjit.swiperewards.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
import com.winjit.swiperewards.entities.RedeemModes;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.helpers.ValidationHelper;
import com.winjit.swiperewards.interfaces.MessageDialogConfirm;
import com.winjit.swiperewards.mvpviews.RedeemView;
import com.winjit.swiperewards.presenters.RedeemPresenter;

import java.util.HashMap;


public class RedeemFragment extends BaseFragment implements View.OnClickListener, RedeemView {
    private AppCompatSpinner spRedeemMode;
    private AppCompatSpinner spRedeemModeOptions;
    private TextInputEditText etAccountNumber;
    private AppCompatEditText etAmount;
    private AppCompatEditText etName;
    private AppCompatEditText etAddress;
    private TextInputLayout tilName;
    private TextInputLayout tilAddress;
    private TextInputLayout tilAccountNumber;
    private Button btConfirm;
    private RedeemPresenter redeemPresenter;
    private RedeemModes[] redeemModes;


    public static RedeemFragment newInstance() {
        Bundle args = new Bundle();
        RedeemFragment fragment = new RedeemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        redeemPresenter = new RedeemPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_redeem, container, false);
        initViews(view);
        showProgress(getActivity().getResources().getString(R.string.please_wait));
        redeemPresenter.getRedeemModes();
        return view;
    }

    private void initViews(View mRootView) {

        spRedeemMode = (AppCompatSpinner) mRootView.findViewById(R.id.sp_redeem_via);
        spRedeemModeOptions = (AppCompatSpinner) mRootView.findViewById(R.id.sp_redeem_vendor);
        etAccountNumber = (TextInputEditText) mRootView.findViewById(R.id.et_account_number);
        etAmount = (AppCompatEditText) mRootView.findViewById(R.id.et_amount);

        etName = (AppCompatEditText) mRootView.findViewById(R.id.et_name);
        etAddress = (AppCompatEditText) mRootView.findViewById(R.id.et_address);
        tilName = mRootView.findViewById(R.id.til_name);
        tilAddress = mRootView.findViewById(R.id.til_address);
        tilAccountNumber = mRootView.findViewById(R.id.til_account_number);

        btConfirm = (Button) mRootView.findViewById(R.id.bt_confirm);
        setListeners();


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


    public void setAdaptersForRedeemMode(Context context, final Spinner spinner, String[] dropDownValues) {
        if(context==null)
            return;
        if (dropDownValues != null && dropDownValues.length > 0) {
            spinner.setAdapter(new ArrayAdapter<>(context, R.layout.row_spinner, dropDownValues));
        }

        //Setting account details after user select the VPA.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spRedeemModeOptions.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.row_spinner, getRedeemOptions(String.valueOf(spinner.getSelectedItem()))));
                updateUiBasedOnSelectedRedeemMOde(String.valueOf(spinner.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void updateUiBasedOnSelectedRedeemMOde(String selectedItem) {
        if (selectedItem.equalsIgnoreCase(ISwipe.CHEQUE)) {
            spRedeemModeOptions.setVisibility(View.GONE);
            tilAddress.setVisibility(View.VISIBLE);
            tilName.setVisibility(View.VISIBLE);
            tilAccountNumber.setVisibility(View.GONE);
        } else {
            spRedeemModeOptions.setVisibility(View.VISIBLE);
            tilAddress.setVisibility(View.GONE);
            tilAccountNumber.setVisibility(View.VISIBLE);
            tilName.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirm:
                if (isValidInputsEntered()) {
                    showConfirmationDialog();
                }
                break;
        }
    }


    private void showConfirmationDialog() {
        String amount = etAmount.getText().toString();
        String dialogInterfaceMessage = "You are creating a redeem request of $" + amount + " to your account XXXX. Do you want to continue?";

        UIHelper.configureShowConfirmDialog(dialogInterfaceMessage, getActivity(),
                R.string.confirm, R.string.btn_cancel,R.string.confirm,
                new MessageDialogConfirm() {
                    @Override
                    public void onPositiveClick() {
                        initiateRedeemRequest();
                    }

                    @Override
                    public void onNegativeClick() {
                    }
                });

    }

    private void initiateRedeemRequest() {
        String selectedMode = (String) spRedeemMode.getSelectedItem();
        int selectedModeId = redeemModes[spRedeemMode.getSelectedItemPosition()].getModeId();
        String amount = etAmount.getText().toString();

        HashMap<String, Object> map = new HashMap<>();
        map.put("redeemModeId", selectedModeId);
        map.put("amount", amount);

        if (!selectedMode.equalsIgnoreCase(ISwipe.CHEQUE)) {
            int selectedModeOptionId = redeemModes[spRedeemMode.getSelectedItemPosition()].getModeOptions()[spRedeemModeOptions.getSelectedItemPosition()].getModeSubId();
            String accountNumber = etAccountNumber.getText().toString();
            map.put("redeemModeOptionId", selectedModeOptionId);
            map.put("details", accountNumber);
        } else {
            String nameAsPerCheque = etName.getText().toString();
            String address = etAddress.getText().toString();
            map.put("redeemModeOptionId", 0);
            map.put("details", nameAsPerCheque);
            map.put("extraField", address);
        }

        redeemPresenter.raiseRedeemRequest(map);


    }


    private boolean isValidInputsEntered() {
        ValidationHelper validationHelper = new ValidationHelper();
        if (spRedeemMode.getAdapter() == null || spRedeemMode.getAdapter().getCount() == 0) {
            showMessage(getString(R.string.err_valid_redeem_mode));
            return false;
        } else {
            String selectedMode = (String) spRedeemMode.getSelectedItem();
            if (selectedMode.equalsIgnoreCase(ISwipe.CHEQUE) && validationHelper.isValidEditTexts(getActivity(), etName, etAddress, etAmount)) {
                return true;
            } else {
                if (spRedeemModeOptions.getAdapter() == null || spRedeemModeOptions.getAdapter().getCount() == 0) {
                    showMessage(getString(R.string.err_valid_redeem_option));
                    return false;
                } else if (validationHelper.isValidEditTexts(getActivity(), etAccountNumber, etAmount)) {
                    return true;
                }
                return false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((HomeActivity) getActivity()) != null) {
            ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_REDEEM);
        }
    }


    @Override
    public void onRedeemModesReceived(RedeemModes[] redeemModes) {
        this.redeemModes = redeemModes;
        setAdaptersForRedeemMode(getActivity(), spRedeemMode, getRedeemModes(redeemModes));
        btConfirm.setEnabled(true);
        btConfirm.setClickable(true);
    }

    private String[] getRedeemModes(RedeemModes[] redeemModes) {
        String[] arrRedeemModes = new String[redeemModes.length];
        for (int i = 0; i < redeemModes.length; i++) {
            arrRedeemModes[i] = redeemModes[i].getMode();
        }
        return arrRedeemModes;
    }


    private String[] getRedeemOptions(String selectedMode) {
        String[] arrRedeemModeOptions = null;
        for (int i = 0; i < redeemModes.length; i++) {
            if (selectedMode.equalsIgnoreCase(redeemModes[i].getMode())) {
                arrRedeemModeOptions = new String[redeemModes[i].getModeOptions().length];
                for (int j = 0; j < redeemModes[i].getModeOptions().length; j++) {
                    arrRedeemModeOptions[j] = redeemModes[i].getModeOptions()[j].getName();
                }
                break;
            }

        }
        return arrRedeemModeOptions;
    }


    @Override
    public void onRedeemRequestGenerated() {
        clearFields();
        showMessage(getActivity().getResources().getString(R.string.redeem_request_raised));
//        ((HomeActivity) getActivity()).setDefaultHomeIndex();
    }

    private void clearFields() {
        etAddress.setText("");
        etName.setText("");
        etAmount.setText("");
        etAccountNumber.setText("");
    }
}
