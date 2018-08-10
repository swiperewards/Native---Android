package com.winjit.swiperewards.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.constants.CardType;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.customviews.MonthYearPickerDialog;
import com.winjit.swiperewards.customviews.creditcard.CardEditText;
import com.winjit.swiperewards.entities.WalletCard;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.helpers.ValidationHelper;
import com.winjit.swiperewards.mvpviews.WalletCardView;
import com.winjit.swiperewards.presenters.WalletPresenter;


public class AddNewCardFragment extends BaseFragment implements View.OnClickListener, WalletCardView {

    private TextInputEditText etExpiryDetails;
    private TextInputEditText etCvv;
    private TextInputEditText etName;
    private Button btSubmit;
    private WalletPresenter walletPresenter;
    private String expiryMonth;
    private String expiryYear;
    private CardEditText tvCardNumber;
    private TextInputLayout tilCvv;

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
        etExpiryDetails = (TextInputEditText) mRootView.findViewById(R.id.et_expiry_details);
        etCvv = (TextInputEditText) mRootView.findViewById(R.id.et_cvv);
        etName = (TextInputEditText) mRootView.findViewById(R.id.et_name);
        tilCvv = (TextInputLayout) mRootView.findViewById(R.id.til_cvv);
        tilCvv.setHintAnimationEnabled(true);

        tvCardNumber = mRootView.findViewById(R.id.tv_card_number);


        btSubmit = (Button) mRootView.findViewById(R.id.bt_submit);

        setListeners();
        setDummyData();
    }

    private void setListeners() {

        btSubmit.setOnClickListener(this);

        etExpiryDetails.setFocusable(false);
        etExpiryDetails.setClickable(true);
        etExpiryDetails.setOnClickListener(this);


        tvCardNumber.setOnCardTypeChangedListener(new CardEditText.OnCardTypeChangedListener() {
            @Override
            public void onCardTypeChanged(CardType cardType) {
                if (cardType != null) {
                    etCvv.setText("");
                    etCvv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(cardType.getSecurityCodeLength())});
                    String hint = getActivity().getResources().getString(cardType.getSecurityCodeName());
                    tilCvv.setHint(hint);
                }
            }
        });
        tvCardNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus && tvCardNumber.isValid() && (TextUtils.isEmpty(expiryMonth) || TextUtils.isEmpty((expiryYear)))) {
                    launchMonthYearCalendar();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        UIHelper.getInstance().hideKeyboard(getActivity());
        switch (v.getId()) {
            case R.id.bt_submit:
                if (isValidInputsEntered()) {
                    showProgress(getActivity().getResources().getString(R.string.please_wait));
                    walletPresenter.addWalletCard(getWalletCardInfo());
                }
                break;
            case R.id.et_expiry_details:
                launchMonthYearCalendar();
                break;
        }
    }

    private void launchMonthYearCalendar() {
        MonthYearPickerDialog pd = new MonthYearPickerDialog();
        pd.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                expiryMonth = String.format("%02d", month);
                expiryYear = String.valueOf(year);
                etExpiryDetails.setText(expiryMonth + "-" + expiryYear);
            }
        });
        pd.show(getFragmentManager(), "MonthYearPickerDialog");
    }

    private WalletCard getWalletCardInfo() {
        WalletCard walletCard = new WalletCard();
        walletCard.setCardNumber(tvCardNumber.getText().toString().replace(" ", ""));
        walletCard.setNameOnCard(etName.getText().toString());
        walletCard.setCvv(etCvv.getText().toString());
        walletCard.setExpiryMonthMM(expiryMonth);
        walletCard.setExpiryYearYYYY(expiryYear);
        return walletCard;
    }

    private boolean isValidInputsEntered() {
        ValidationHelper validationHelper = new ValidationHelper();
        return validationHelper.isValidEditTexts(getActivity(), etName, tvCardNumber, etCvv, etExpiryDetails);
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

    }


    private void setDummyData() {
        if (ISwipe.IS_DUMMY_DATA_ENABLED) {
//            tvCardNumber.setText("");
//            etName.setText("Test Winjit");
//            etCvv.setText("123");
//            etExpiryDetails.setText("12-2030");
//            expiryMonth = "12";
//            expiryYear = "2030";
        }
    }
}
