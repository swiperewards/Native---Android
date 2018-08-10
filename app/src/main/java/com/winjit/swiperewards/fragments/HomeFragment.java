package com.winjit.swiperewards.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.adapters.DealsAdapter;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.Deals;
import com.winjit.swiperewards.helpers.CommonHelper;
import com.winjit.swiperewards.helpers.GPSTracker;
import com.winjit.swiperewards.helpers.PermissionUtils;
import com.winjit.swiperewards.interfaces.AdapterResponseInterface;
import com.winjit.swiperewards.mvpviews.DealsView;
import com.winjit.swiperewards.presenters.DealsPresenter;

import java.util.ArrayList;
import java.util.Arrays;


public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterResponseInterface, DealsView {

    private RecyclerView rvDeals;
    private DealsPresenter dealsPresenter;
    private RelativeLayout rlLocation;
    private AppCompatTextView tvEnableLocation;
    private LocationManager lm = null;
    private GnssStatus.Callback gnsCallBack;
    private AppCompatEditText etSearchDeals;
    private ArrayList<Deals> dealsArrayList;
    private DealsAdapter dealAdapter;

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
        setLocationListener();
        return view;
    }


    private void initViews(View mRootView) {
        rvDeals = mRootView.findViewById(R.id.rv_deals);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvDeals.setLayoutManager(linearLayoutManager);

        rlLocation = mRootView.findViewById(R.id.rl_location);
        etSearchDeals = mRootView.findViewById(R.id.et_search_deals);
        tvEnableLocation = mRootView.findViewById(R.id.tv_enable_location);
        tvEnableLocation.setOnClickListener(this);

        getDealsIfLocationEnabled();
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
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getLocation().contains(text) || d.getShortDescription().contains(text)) {
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
            requestPermission();
        }
    }

    private void initiateDealsAndUpdateBottomVisibility(boolean isLocationEnabled) {
        if (isLocationEnabled) {
            rlLocation.setVisibility(View.GONE);
            if (rvDeals == null || rvDeals.getAdapter() == null || rvDeals.getAdapter().getItemCount() == 0) {
                if (getActivity() != null)
                    showProgress(getActivity().getResources().getString(R.string.please_wait));
                dealsPresenter.getDeals(ISwipe.DUMMY_CITY);
            }
        } else {
            rlLocation.setVisibility(View.VISIBLE);
        }
    }


    private void setLocationListener() {

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (lm != null) {
            //Checking SDK to ensure runtime permissions.
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                //Checking SDK to to set Gps status listener which is deprecated in android N.
                if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                    requestLocUpdates(lm);
                } else {
                    lm.registerGnssStatusCallback(gnsCallBack);
                    requestLocUpdates(lm);
                }
            } else {
                //Setting GPS listener without checking runtime permission for devices below Android M.
//                lm.addGpsStatusListener(new GpsStatus.Listener() {
//                    public void onGpsStatusChanged(int event) {
//                        switch (event) {
//                            case GPS_EVENT_STARTED:
//                                initiateDealsAndUpdateBottomVisibility(true);
//                                break;
//                            case GPS_EVENT_STOPPED:
//                                initiateDealsAndUpdateBottomVisibility(false);
//                                break;
//                        }
//                    }
//                });
                requestLocUpdates(lm);
            }

        }


    }

    private void requestLocUpdates(LocationManager lm) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, new LocationListener() {
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
        });

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
                Intent viewIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(viewIntent);
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
        dealsArrayList = new ArrayList<Deals>(Arrays.asList(dealsList));
        dealAdapter = new DealsAdapter(getActivity(), new CommonHelper().updateStartEndDateFormat(dealsArrayList), this);
        rvDeals.setAdapter(dealAdapter);
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
    public void onDestroy() {
        super.onDestroy();
        if (lm != null && gnsCallBack != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            lm.unregisterGnssStatusCallback(gnsCallBack);
        }
    }


}
