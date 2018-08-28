package com.nouvo.rewards.constants;

public interface ISwipe {
    boolean IS_DUMMY_DATA_ENABLED = false;
    String SESSION_USER = "SESSION_USER";
    String ADAPTER_POSITION = "ADAPTER_POSITION";
    String TAG = "SwipeLog";
    String IS_FROM_SIGN_UP = "IS_FROM_SIGN_UP";
    int HIDE_TOP_VIEW = 0;
    int SHOW_TOP_VIEW = 1;
    String ACTION_NAME = "ACTION_NAME";
    String ACTION_ADD_NEW_CARD = "ACTION_ADD_NEW_CARD";
    String ACTION_DELETE_CARD = "ACTION_DELETE_CARD";
    String WEB_URL = "WEB_URL";
    String TITLE_HOME = "HOME";
    String TITLE_WALLET = "WALLET";
    String TITLE_REDEEM = "REDEEM";
    String TITLE_HISTORY = "HISTORY";
    String TITLE_SETTINGS = "SETTINGS";
    String TITLE_CHANGE_PASSWORD = "CHANGE PASSWORD";
    String TITLE_CONTACT_US = "CONTACT US";
    String TITLE_PRIVACY_SECURITY = "PRIVACY & SECURITY";
    String TITLE_TERMS_OF_USE = "TERMS OF USE";
    String TITLE_ADD_NEW_CARD = "ADD NEW CARD";
    String PLATFORM = "Android";
    int SUCCESS = 200;
    String DUMMY_CITY = "new york";
    String USER_CATEGORY = "Customer";
    String CARD_ID = "CARD_ID";
    String LATITUDE = "LATITUDE";
    String LONGITUDE = "LONGITUDE";

    int CAMERA_REQUEST = 1888;
    int FILE_REQUEST = 1889;
    int LOCATION_PERMISSION = 1;

    int EVENT_TYPE_GENERAL = 1;
    int EVENT_TYPE_REWARD = 2;
    int EVENT_TYPE_TRANSACTION = 3;

    String CHEQUE = "Cheque";
    String BANK_ACCOUNT = "Bank Account";

    int CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE_WITH_CAMERA = 100;
    int CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE_WITHOUT_CAMERA = 101;
    int CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE = 2011;
    int THUMBNAIL_SIZE = 256;

    String KEY_EMAIL = "key_email";
    int MAX_GET_CITY_ATTEMPTS = 5;
    String APP_STACK = "APP_STACK";
    String HOME_STACK = "HOME_STACK";
    String CachedHomeState="CachedHomeState";
    int DEFAULT_DEALS_PAGE_SIZE = 0;

    enum BottomErrorType {

        ERROR_ENABLE_LOCATION(1),
        ERROR_NO_DEALS_AVAILABLE(2);

        BottomErrorType(int i) {
        }
    }

    String FragTagAddNewCardFragment="AddNewCardFragment";
    String FragTagChangePasswordFragment="ChangePasswordFragment";
    String FragTagContactUsFragment="ContactUsFragment";
    String FragTagEventHistoryFragment="EventHistoryFragment";
    String FragTagForgotPasswordFragment="ForgotPasswordFragment";
    String FragTagHomeFragment="HomeFragment";
    String FragTagLoginFragment="LoginFragment";
    String FragTagRedeemFragment="RedeemFragment";
    String FragTagRegisterFragment="RegisterFragment";
    String FragTagSetNewPasswordFragment="SetNewPasswordFragment";
    String FragTagSettingsFragment="SettingsFragment";
    String FragTagSuccessFragment="SuccessFragment";
    String FragTagWalletFragment="WalletFragment";
    String FragTagWebViewFragment="WebViewFragment";

}
