package com.winjit.swiperewards.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.adapters.DealsAdapter;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.CityDetails;
import com.winjit.swiperewards.entities.Deals;
import com.winjit.swiperewards.helpers.CommonHelper;
import com.winjit.swiperewards.helpers.GPSTracker;
import com.winjit.swiperewards.helpers.PermissionUtils;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.interfaces.AdapterResponseInterface;
import com.winjit.swiperewards.mvpviews.DealsView;
import com.winjit.swiperewards.presenters.DealsPresenter;

import java.util.ArrayList;
import java.util.Arrays;


public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterResponseInterface, DealsView {

    private RecyclerView rvDeals;
    private DealsPresenter dealsPresenter;
    private RelativeLayout rlLocation;
    private RelativeLayout rlChangeLocation;
    private AppCompatTextView tvEnableLocation;
    private AppCompatTextView tvChangeLocationError;
    private LocationManager locationManager = null;
    private AppCompatAutoCompleteTextView etSearchDeals;
    private ArrayList<Deals> dealsArrayList;
    private DealsAdapter dealAdapter;
    private LocationListener locationListener;

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
        setLocationListener(); // to check device location service on-off
        return view;
    }


    private void initViews(View mRootView) {
        rvDeals = mRootView.findViewById(R.id.rv_deals);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvDeals.setLayoutManager(linearLayoutManager);

        rlLocation = mRootView.findViewById(R.id.rl_location);
        rlChangeLocation = mRootView.findViewById(R.id.rl_change_location);
        etSearchDeals = mRootView.findViewById(R.id.et_search_deals);
        tvChangeLocationError = mRootView.findViewById(R.id.tv_change_location_error);
        tvEnableLocation = mRootView.findViewById(R.id.tv_enable_location);
        tvEnableLocation.setOnClickListener(this);
        rlLocation.setOnClickListener(this);
        tvChangeLocationError.setOnClickListener(this);
        showProgress(getActivity().getResources().getString(R.string.please_wait));
        dealsPresenter.getCities();
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
        if (PermissionUtils.checkPermissionGranted((AppCompatActivity) getActivity(), "android.permission.ACCESS_COARSE_LOCATION") &&
                PermissionUtils.checkPermissionGranted((AppCompatActivity) getActivity(), "android.permission.ACCESS_FINE_LOCATION")) {

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
            rlLocation.setVisibility(View.GONE);
            String cityName;
            int attempts = 0;
            GPSTracker gpsTracker = new GPSTracker(getActivity());

            do {
                attempts++;
                cityName = gpsTracker.getCityName(getActivity());
            } while (attempts < ISwipe.MAX_GET_CITY_ATTEMPTS && TextUtils.isEmpty(cityName));


            if (TextUtils.isEmpty(cityName)) {
                rlChangeLocation.setVisibility(View.VISIBLE);
                onDealsReceived(null);
                return;
            }

            if (rvDeals == null || rvDeals.getAdapter() == null || rvDeals.getAdapter().getItemCount() == 0) {
                if (getActivity() != null) {
                    if (!TextUtils.isEmpty(cityName)) {
                        ((HomeActivity) getActivity()).updateCityLocation(cityName);
                    }
                }
                if (!TextUtils.isEmpty(cityName)) {
                    dealsPresenter.getDeals(cityName);
                } else {
                    onDealCityListReceived(null);
                }
            }
        } else {
            hideProgress();
            rlChangeLocation.setVisibility(View.GONE);
            rlLocation.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }


    private void setLocationListener() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            //Checking SDK to ensure runtime permissions.
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }

                @Override
                public void onProviderEnabled(String s) {
                    initiateDealsAndUpdateBottomVisibility(true);
                }

                @Override
                public void onProviderDisabled(String s) {
                    initiateDealsAndUpdateBottomVisibility(false);
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locationListener);
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
    public void onResume() {
        super.onResume();
        if (((HomeActivity) getActivity()) != null) {
            ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_HOME);
        }
    }

    @Override
    public void onDealsReceived(Deals[] dealsList) {
        if (dealsList != null && dealsList.length > 0) {
            rlChangeLocation.setVisibility(View.GONE);
            dealsArrayList = new ArrayList<Deals>(Arrays.asList(dealsList));
            dealAdapter = new DealsAdapter(getActivity(), new CommonHelper().updateStartEndDateFormat(dealsArrayList), this);
            rvDeals.setAdapter(dealAdapter);
        } else {
            //Setting an empty list
            dealsArrayList = new ArrayList<Deals>();
            dealAdapter = new DealsAdapter(getActivity(), new CommonHelper().updateStartEndDateFormat(dealsArrayList), this);
            rvDeals.setAdapter(dealAdapter);


            GPSTracker gpsTracker = new GPSTracker(getActivity());
            boolean isLocationEnabled = gpsTracker.isLocationEnabled(getActivity());
            if (isLocationEnabled) {
                String currentLocation = ((HomeActivity) getActivity()).getCurrentLocation();
                tvChangeLocationError.setText(getActivity().getResources().getString(R.string.no_deal_error, currentLocation));
                rlChangeLocation.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ISwipe.LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setLocationListener();
                    getDealsIfLocationEnabled();
                } else {
                    showMessage(getActivity().getResources().getString(R.string.permission_error));
                }
                break;
        }
    }

    @Override
    public void onDealCityListReceived(CityDetails[] cityDetails) {
        if (cityDetails != null && cityDetails.length > 0) {

            String[] cityList = new String[cityDetails.length];

            for (int i = 0; i < cityDetails.length; i++) {
                cityList[i] = cityDetails[i].getName();
            }
            setupCityList(cityList);
        }
        getDealsIfLocationEnabled();
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
                        dealsPresenter.getDeals(city);
                        etSearchDeals.setText("");

                    }

                }
            }
        });

    }

}
