package com.nouvo.rewards.entities;

public class CachedHomeState{

    public static CachedHomeState cachedHomeState;
    private Deals[] deals;
    private String appliedFilter;
    private String selectedCity;
    private CityDetails[] cityDetails;
    private int dealListIndex;

    public static CachedHomeState getCachedHomeState() {
        return cachedHomeState;
    }

    public static void setCachedHomeState(CachedHomeState cachedHomeState) {
        CachedHomeState.cachedHomeState = cachedHomeState;
    }

    public Deals[] getDeals() {
        return deals;
    }

    public void setDeals(Deals[] deals) {
        this.deals = deals;
    }

    public String getAppliedFilter() {
        return appliedFilter;
    }

    public void setAppliedFilter(String appliedFilter) {
        this.appliedFilter = appliedFilter;
    }

    public String getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(String selectedCity) {
        this.selectedCity = selectedCity;
    }

    public CityDetails[] getCityDetails() {
        return cityDetails;
    }

    public void setCityDetails(CityDetails[] cityDetails) {
        this.cityDetails = cityDetails;
    }

    public int getDealListIndex() {
        return dealListIndex;
    }

    public void setDealListIndex(int dealListIndex) {
        this.dealListIndex = dealListIndex;
    }
}