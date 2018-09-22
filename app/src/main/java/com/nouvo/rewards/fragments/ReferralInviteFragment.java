package com.nouvo.rewards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nouvo.rewards.R;
import com.nouvo.rewards.appdata.SingletonAppCache;
import com.nouvo.rewards.helpers.CommunicationHelper;


public class ReferralInviteFragment extends Fragment implements View.OnClickListener {
    private TextView tvReferralCode;
    private Button btInvite;


    public static ReferralInviteFragment newInstance() {
        Bundle args = new Bundle();
        ReferralInviteFragment fragment = new ReferralInviteFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_referral_invite, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View mRootView) {
        tvReferralCode = (TextView) mRootView.findViewById(R.id.tv_referral_code);
        tvReferralCode.setText(SingletonAppCache.getInstance().getUserProfile().getReferralCode());
        btInvite = (Button) mRootView.findViewById(R.id.bt_invite);
        btInvite.setVisibility(View.VISIBLE);
        btInvite.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_invite:
                String url = "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
                String referralCode = SingletonAppCache.getInstance().getUserProfile().getReferralCode();
                String message = getActivity().getResources().getString(R.string.nouvo_referral_invite, referralCode);
                new CommunicationHelper().shareOnSocial(getActivity(), url, message);
                break;
        }
    }
}
