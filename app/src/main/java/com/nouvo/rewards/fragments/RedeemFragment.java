package com.nouvo.rewards.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.nouvo.rewards.R;
import com.nouvo.rewards.activities.HomeActivity;
import com.nouvo.rewards.appdata.SingletonAppCache;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.entities.RedeemModes;
import com.nouvo.rewards.helpers.UIHelper;
import com.nouvo.rewards.helpers.ValidationHelper;
import com.nouvo.rewards.interfaces.MessageDialogConfirm;
import com.nouvo.rewards.mvpviews.RedeemView;
import com.nouvo.rewards.presenters.RedeemPresenter;

import java.util.HashMap;


public class RedeemFragment extends BaseFragment implements View.OnClickListener, RedeemView {
    private AppCompatSpinner spRedeemMode;
    private AppCompatSpinner spRedeemModeOptions;
    private TextInputEditText etAccountNumber;
    private AppCompatEditText etAmount;
    private AppCompatEditText etName;
    private AppCompatEditText etExtraField;
    private TextInputLayout tilName;
    private TextInputLayout tillExtraField;
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
        etExtraField = (AppCompatEditText) mRootView.findViewById(R.id.et_extra_field);
        tilName = mRootView.findViewById(R.id.til_name);
        tillExtraField = mRootView.findViewById(R.id.till_extra_field);
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
        if (context == null)
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
            tillExtraField.setVisibility(View.VISIBLE);
            tilName.setVisibility(View.VISIBLE);
            tilAccountNumber.setVisibility(View.GONE);
            tillExtraField.setHint(getActivity().getResources().getString(R.string.address));
        } else if (selectedItem.equalsIgnoreCase(ISwipe.BANK_ACCOUNT)) {
            //tillExtraField
            spRedeemModeOptions.setVisibility(View.VISIBLE);
            tilName.setVisibility(View.GONE);
            tilAccountNumber.setVisibility(View.VISIBLE);
            tilAccountNumber.setHint(getActivity().getResources().getString(R.string.acc_number));
            tillExtraField.setVisibility(View.VISIBLE);
            etExtraField.setText("");
            etExtraField.setInputType(InputType.TYPE_CLASS_PHONE);
            etExtraField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
            tillExtraField.setHint(getActivity().getResources().getString(R.string.routin_number));
        } else {
            spRedeemModeOptions.setVisibility(View.VISIBLE);
            tilName.setVisibility(View.GONE);
            tilAccountNumber.setVisibility(View.VISIBLE);
            tilAccountNumber.setHint(getActivity().getResources().getString(R.string.wallet_address));
            tillExtraField.setVisibility(View.GONE);
            etExtraField.setText("");
            etExtraField.setInputType(InputType.TYPE_CLASS_TEXT);
            etExtraField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(128)});

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirm:
                if (isValidInputsEntered() && isValidRedeemAmountEntered()) {
                    showConfirmationDialog();
                }
                break;
        }
    }

    private boolean isValidRedeemAmountEntered() {
        try {
            Float redeemAmount = Float.parseFloat(etAmount.getText().toString());
            if (redeemAmount == null || redeemAmount <= 0) {
                showMessage("Redeem amount should be greater than $0");
                return false;
            } else if (SingletonAppCache.getInstance().getUserProfile() != null &&
                    SingletonAppCache.getInstance().getUserProfile().getWalletBalance() < redeemAmount) {
                showMessage("You cannot redeem more than $" + SingletonAppCache.getInstance().getUserProfile().getWalletBalance());
                return false;
            }
            return true;
        } catch (Exception e) {
            showMessage(getActivity().getResources().getString(R.string.err_generic));
            return false;
        }
    }


    private void showConfirmationDialog() {
        String amount = etAmount.getText().toString();
        String dialogInterfaceMessage = "You are creating a redeem request of $" + amount + " to your account XXXX. Do you want to continue?";

        UIHelper.configureShowConfirmDialog(dialogInterfaceMessage, getActivity(),
                R.string.confirm, R.string.btn_cancel, R.string.confirm,
                new MessageDialogConfirm() {
                    @Override
                    public void onPositiveClick(Bundle bundle) {
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

        if (selectedMode.equalsIgnoreCase(ISwipe.BANK_ACCOUNT)) { // Bank Account
            int selectedModeOptionId = redeemModes[spRedeemMode.getSelectedItemPosition()].getModeOptions()[spRedeemModeOptions.getSelectedItemPosition()].getModeSubId();
            String accountNumber = etAccountNumber.getText().toString();
            String routingNumber = etExtraField.getText().toString();
            map.put("redeemModeOptionId", selectedModeOptionId);
            map.put("details", accountNumber);
            map.put("extraField", routingNumber);

        } else if (selectedMode.equalsIgnoreCase(ISwipe.CHEQUE)) { // Cheque
            String nameAsPerCheque = etName.getText().toString();
            String address = etExtraField.getText().toString();
            map.put("redeemModeOptionId", 0);
            map.put("details", nameAsPerCheque);
            map.put("extraField", address);
        } else {// CryptoCurrency
            int selectedModeOptionId = redeemModes[spRedeemMode.getSelectedItemPosition()].getModeOptions()[spRedeemModeOptions.getSelectedItemPosition()].getModeSubId();
            String accountNumber = etAccountNumber.getText().toString();
            map.put("redeemModeOptionId", selectedModeOptionId);
            map.put("details", accountNumber);
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
            if (selectedMode.equalsIgnoreCase(ISwipe.CHEQUE) && validationHelper.isValidEditTexts(getActivity(), etName, etExtraField, etAmount)) {
                return true;
            } else if (selectedMode.equalsIgnoreCase(ISwipe.BANK_ACCOUNT) && validationHelper.isValidEditTexts(getActivity(), etName,etAccountNumber, etExtraField, etAmount)) {
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
//        ((HomeActivity) getActivity()).setNavigationSelectedItem();
    }

    private void clearFields() {
        etExtraField.setText("");
        etName.setText("");
        etAmount.setText("");
        etAccountNumber.setText("");
    }
}
