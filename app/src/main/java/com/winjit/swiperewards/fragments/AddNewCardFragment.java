package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.WalletCard;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.helpers.ValidationHelper;
import com.winjit.swiperewards.mvpviews.WalletCardView;
import com.winjit.swiperewards.presenters.WalletPresenter;


public class AddNewCardFragment extends BaseFragment implements View.OnClickListener, WalletCardView {

    private TextInputEditText etCardNumber;
    private TextInputEditText etExpiryDetails;
    private TextInputEditText etCvv;
    private TextInputEditText etName;
    private Button btSubmit;
    private WalletPresenter walletPresenter;
    private String expiryMonth;
    private String expiryYear;

    public static AddNewCardFragment newInstance() {
        Bundle args = new Bundle();
        AddNewCardFragment fragment = new AddNewCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        walletPresenter = new WalletPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_new_card, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View mRootView) {
        etCardNumber = (TextInputEditText) mRootView.findViewById(R.id.et_card_number);
        etExpiryDetails = (TextInputEditText) mRootView.findViewById(R.id.et_expiry_details);
        etCvv = (TextInputEditText) mRootView.findViewById(R.id.et_cvv);
        etName = (TextInputEditText) mRootView.findViewById(R.id.et_name);

        btSubmit = (Button) mRootView.findViewById(R.id.bt_submit);
        btSubmit.setOnClickListener(this);
        setDummyData();
    }


    @Override
    public void onClick(View v) {
        UIHelper.getInstance().hideKeyboard(getActivity());
        switch (v.getId()) {
            case R.id.bt_submit:
                if (isValidInputsEntered()) {
                    walletPresenter.addWalletCard(getWalletCardInfo());
                }
                break;
        }
    }

    private WalletCard getWalletCardInfo() {
        WalletCard walletCard = new WalletCard();
        walletCard.setCardNumber(etCardNumber.getText().toString());
        walletCard.setNameOnCard(etName.getText().toString());
        walletCard.setCvv(etCvv.getText().toString());
        walletCard.setExpiryMonthMM(expiryMonth);
        walletCard.setExpiryYearYYYY(expiryYear);
        return walletCard;
    }

    private boolean isValidInputsEntered() {
        ValidationHelper validationHelper = new ValidationHelper();
        return validationHelper.isValidEditTexts(getActivity(), etName, etCardNumber, etCvv, etExpiryDetails);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((HomeActivity) getActivity()) != null) {
            ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_ADD_NEW_CARD);
        }
    }

    @Override
    public void onWalletCardListReceived(WalletCard[] walletCards) {

    }

    @Override
    public void onCardAddedSuccessfully() {
        showMessage(getActivity().getResources().getString(R.string.card_added));
        UIHelper.getInstance().popFragment(getActivity().getSupportFragmentManager());
    }

    @Override
    public void onCardDeletedSuccessfully() {
        showLongToast(getActivity().getResources().getString(R.string.card_added));
        UIHelper.getInstance().popFragment(getActivity().getSupportFragmentManager());

    }


    private void setDummyData() {
        if (ISwipe.IS_DUMMY_DATA_ENABLED) {
            etCardNumber.setText("123456789012");
            etName.setText("Test Winjit");
            etCvv.setText("123");
            etExpiryDetails.setText("12-2030");
            expiryMonth = "12";
            expiryYear = "2030";
        }
    }
}
