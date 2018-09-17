package com.nouvo.rewards.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nouvo.rewards.R;
import com.nouvo.rewards.activities.HomeActivity;
import com.nouvo.rewards.adapters.DealsAdapter;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.entities.CachedHomeState;
import com.nouvo.rewards.entities.CityDetails;
import com.nouvo.rewards.entities.Deals;
import com.nouvo.rewards.helpers.CommonHelper;
import com.nouvo.rewards.helpers.GPSTracker;
import com.nouvo.rewards.helpers.PermissionUtils;
import com.nouvo.rewards.helpers.UIHelper;
import com.nouvo.rewards.interfaces.DealAdapterResponseInterface;
import com.nouvo.rewards.mvpviews.DealsView;
import com.nouvo.rewards.presenters.DealsPresenter;

import java.util.ArrayList;
import java.util.Arrays;


public class HomeFragment extends BaseFragment implements View.OnClickListener, DealAdapterResponseInterface, DealsView {

    private RecyclerView rvDeals;
    private DealsPresenter dealsPresenter;
    private RelativeLayout rlLocation;
    private RelativeLayout rlChangeLocation;
    private AppCompatTextView tvEnableLocation;
    private AppCompatTextView tvChangeLocationError;
    private AppCompatAutoCompleteTextView etSearchDeals;
    private ArrayList<Deals> dealsArrayList;
    private DealsAdapter dealAdapter;
    private int currentDealPageNumber = 0;
    private Bundle savedState = null;
    private CityDetails[] cityDetails;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealsPresenter = new DealsPresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);

        /* If the Fragment was destroyed in between (screen rotation), we need to recover the savedState first */
        /* However, if it was not, it stays in the instance from the last onDestroyView() and we don't want to overwrite it */
        if (savedInstanceState != null && savedState == null) {
            savedState = savedInstanceState.getBundle(ISwipe.CachedHomeState);
        }
        if (savedState != null && savedState.containsKey(ISwipe.CachedHomeState)) {
            CachedHomeState cachedHomeState = new Gson().fromJson(savedState.get(ISwipe.CachedHomeState).toString(), CachedHomeState.class);
            setCachedDataToViews(cachedHomeState);
        } else {
            showProgress(getActivity().getResources().getString(R.string.please_wait));
            dealsPresenter.getCities();
        }
        savedState = null;
        getLocationPermissions(); // to check device location service on-off
        return view;
    }

    private void setCachedDataToViews(CachedHomeState cachedHomeState) {
        onDealsReceived(cachedHomeState.getDeals());
        onDealCityListReceived(cachedHomeState.getCityDetails(), false);

        if (!TextUtils.isEmpty(cachedHomeState.getAppliedFilter())) {
            etSearchDeals.setText(cachedHomeState.getAppliedFilter());
        }
    }


    private void initViews(View mRootView) {
        rvDeals = mRootView.findViewById(R.id.rv_deals);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvDeals.setLayoutManager(linearLayoutManager);

        dealsArrayList = new ArrayList<Deals>();
        dealAdapter = new DealsAdapter(getActivity(), new CommonHelper().updateStartEndDateFormat(dealsArrayList), this);
        rvDeals.setAdapter(dealAdapter);
        rlLocation = mRootView.findViewById(R.id.rl_location);
        rlChangeLocation = mRootView.findViewById(R.id.rl_change_location);
        etSearchDeals = mRootView.findViewById(R.id.et_search_deals);
        tvChangeLocationError = mRootView.findViewById(R.id.tv_change_location_error);
        tvEnableLocation = mRootView.findViewById(R.id.tv_enable_location);
        tvEnableLocation.setOnClickListener(this);
        rlLocation.setOnClickListener(this);
        tvChangeLocationError.setOnClickListener(this);
        setFilterListenerForLocation();

    }

    private void setFilterListenerForLocation() {
        etSearchDeals.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable inputString) {
                if (dealsArrayList != null && dealsArrayList.size() > 0) {
                    if (inputString.length() >= 3) {
                        filter(inputString.toString());
                    } else if (inputString.length() == 0) {
                        dealAdapter.updateList(dealsArrayList);
                    }
                }
            }
        });
    }


    private void filter(String text) {
        ArrayList<Deals> filteredDealList = new ArrayList();
        for (Deals d : dealsArrayList) {
            if (d.getLocation().toLowerCase().contains(text.toLowerCase()) ||
                    d.getShortDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredDealList.add(d);
            }
        }
        dealAdapter.updateList(filteredDealList);
    }


    private void getDealsIfLocationEnabled() {
        if (PermissionUtils.checkPermissionGranted(getActivity(), "android.permission.ACCESS_COARSE_LOCATION") &&
                PermissionUtils.checkPermissionGranted(getActivity(), "android.permission.ACCESS_FINE_LOCATION")) {

            GPSTracker gpsTracker = new GPSTracker(getActivity());
            boolean isLocationEnabled = gpsTracker.isLocationEnabled(getActivity());
            initiateDealsAndUpdateBottomVisibility(isLocationEnabled);
        } else {
            hideProgress();
            requestPermission();
        }
    }

    private void initiateDealsAndUpdateBottomVisibility(boolean isLocationEnabled) {
        if (isLocationEnabled) {
            showHideBottomError(ISwipe.BottomErrorType.ERROR_ENABLE_LOCATION, false);
            String cityName;
            int attempts = 0;
            GPSTracker gpsTracker = new GPSTracker(getActivity());

            do {
                attempts++;
                cityName = gpsTracker.getCityName(getActivity());
            } while (attempts < ISwipe.MAX_GET_CITY_ATTEMPTS && TextUtils.isEmpty(cityName));


            if (TextUtils.isEmpty(cityName)) {
                showHideBottomError(ISwipe.BottomErrorType.ERROR_NO_DEALS_AVAILABLE, true);
                onDealsReceived(null);
                return;
            }
            if (getActivity() != null) {
                if (!TextUtils.isEmpty(cityName)) {
                    ((HomeActivity) getActivity()).updateCityLocation(cityName);
                }
            }
            if (!TextUtils.isEmpty(cityName)) {
//                dealsPresenter.getDeals(cityName);
                dealsPresenter.getDealsWithPagination(cityName, currentDealPageNumber, ISwipe.DEFAULT_DEALS_PAGE_SIZE);
            } else {
                onDealCityListReceived(null, false);
            }
        } else {
            hideProgress();
            showHideBottomError(ISwipe.BottomErrorType.ERROR_ENABLE_LOCATION, true);
        }
    }


    private void getLocationPermissions() {
        //Checking SDK to ensure runtime permissions.
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
    }

    private void requestPermission() {
        String[] strPermission = {"android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.ACCESS_FINE_LOCATION"};
        requestPermissions(strPermission, ISwipe.LOCATION_PERMISSION);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_enable_location:
            case R.id.rl_location:
                Intent viewIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(viewIntent);
                break;
            case R.id.rl_change_location:
            case R.id.tv_change_location_error:
                etSearchDeals.showDropDown();
                break;
        }
    }

    @Override
    public void getAdapterResponse(Bundle bundle) {
        new CommonHelper().navigateToDealLocation(getActivity(), bundle);

    }

    @Override
    public void loadMoreDeals() {
        if (getActivity() != null && !TextUtils.isEmpty(((HomeActivity) getActivity()).getCurrentLocation())) {
            String currentCity = ((HomeActivity) getActivity()).getCurrentLocation();
//             dealsPresenter.getDeals(currentCity);
            dealsPresenter.getDealsWithPagination(currentCity, currentDealPageNumber, ISwipe.DEFAULT_DEALS_PAGE_SIZE);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((HomeActivity) getActivity()).setTopBarTitle(getActivity().getResources().getString(R.string.title_home).toUpperCase());
        }

    }

    @Override
    public void onDealsReceived(Deals[] dealsList) {
        if (dealsList != null && dealsList.length > 0) {
            showHideBottomError(ISwipe.BottomErrorType.ERROR_NO_DEALS_AVAILABLE, false);
            if (currentDealPageNumber == 0) {
                dealsArrayList = new CommonHelper().updateStartEndDateFormat(new ArrayList<Deals>(Arrays.asList(dealsList)));
            } else {
                dealsArrayList.addAll(new CommonHelper().updateStartEndDateFormat(new ArrayList<Deals>(Arrays.asList(dealsList))));

            }
            dealAdapter.setDealsSizeWithoutFilter(dealsArrayList.size());
            dealAdapter.setEndOfPaginationReached(false);
            currentDealPageNumber++;
            dealAdapter.updateList(dealsArrayList);
        } else {
            //Setting an empty list
            if (currentDealPageNumber == 0) {
                dealsArrayList = new ArrayList<Deals>();
                dealAdapter.setEndOfPaginationReached(true);
                dealAdapter.updateList(dealsArrayList);

                if (!(rlLocation.getVisibility() == View.VISIBLE)) {
                    String currentLocation = ((HomeActivity) getActivity()).getCurrentLocation();
                    tvChangeLocationError.setText(getActivity().getResources().getString(R.string.no_deal_error, currentLocation));
                    showHideBottomError(ISwipe.BottomErrorType.ERROR_NO_DEALS_AVAILABLE, true);
                }
            } else {
                dealAdapter.setEndOfPaginationReached(true);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ISwipe.LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocationPermissions();
                    getDealsIfLocationEnabled();
                } else {
                    showMessage(getActivity().getResources().getString(R.string.permission_error));
                }
                break;
        }
    }

    @Override
    public void onDealCityListReceived(CityDetails[] cityDetails, boolean shouldCallGetDealsAPI) {
        if (cityDetails != null && cityDetails.length > 0) {
            this.cityDetails = cityDetails;
            String[] cityList = new String[cityDetails.length];

            for (int i = 0; i < cityDetails.length; i++) {
                cityList[i] = cityDetails[i].getName();
            }
            if (getActivity() != null)
                setupCityList(cityList);
            if (shouldCallGetDealsAPI)
                getDealsIfLocationEnabled();
        }

    }

    private void setupCityList(String[] cityList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), R.layout.row_spinner, cityList);
        //Getting the instance of AutoCompleteTextView
        etSearchDeals.setThreshold(0);//will start working from first character
        etSearchDeals.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        etSearchDeals.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!etSearchDeals.isPopupShowing()) {
                    ((HomeActivity) getActivity()).getTopView().setExpanded(false);
                    etSearchDeals.showDropDown();

                }
                return false;
            }
        });
        etSearchDeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String city = null;
                if (view != null && view instanceof TextView) {
                    city = ((TextView) view).getText().toString();
                    if (!TextUtils.isEmpty(city)) {
                        UIHelper.getInstance().hideKeyboard(getActivity());
                        ((HomeActivity) getActivity()).updateCityLocation(city);
                        showProgress(getActivity().getResources().getString(R.string.please_wait));
                        currentDealPageNumber = 0;
//                        dealsPresenter.getDeals(city);
                        dealsPresenter.getDealsWithPagination(city, currentDealPageNumber, ISwipe.DEFAULT_DEALS_PAGE_SIZE);
                        etSearchDeals.setText("");

                    }

                }
            }
        });

    }


    private void showHideBottomError(ISwipe.BottomErrorType bottomErrorType, boolean isVisible) {
        if (isVisible) {
            if (bottomErrorType == ISwipe.BottomErrorType.ERROR_NO_DEALS_AVAILABLE && rlLocation.getVisibility() == View.GONE) {
                rlChangeLocation.setVisibility(View.VISIBLE);
            } else {
                rlChangeLocation.setVisibility(View.GONE);
                rlLocation.setVisibility(View.VISIBLE);
            }

        } else {
            if (bottomErrorType == ISwipe.BottomErrorType.ERROR_NO_DEALS_AVAILABLE) {
                rlChangeLocation.setVisibility(View.GONE);
            } else {
                rlLocation.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        savedState = saveState(); /* vstup defined here for sure */
    }

    private Bundle saveState() { /* called either from onDestroyView() or onSaveInstanceState() */
        Bundle state = new Bundle();
        state.putSerializable(ISwipe.CachedHomeState, new Gson().toJson(getDataToBeCached()));
        return state;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /* If onDestroyView() is called first, we can use the previously savedState but we can't call saveState() anymore */
        /* If onSaveInstanceState() is called first, we don't have savedState, so we need to call saveState() */
        /* => (?:) operator inevitable! */
        outState.putBundle(ISwipe.CachedHomeState, (savedState != null) ? savedState : saveState());
    }

    public CachedHomeState getDataToBeCached() {
        CachedHomeState cachedHomeState = new CachedHomeState();
        cachedHomeState.setDeals(new CommonHelper().getDealsArrayFromArrayList(dealsArrayList));
        cachedHomeState.setCityDetails(cityDetails);
        cachedHomeState.setAppliedFilter(etSearchDeals.getText().toString());
        cachedHomeState.setSelectedCity(((HomeActivity) getActivity()).getCurrentLocation());
        return cachedHomeState;
    }


}
