package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.utils.UIHelper;


public class AddNewCardFragment extends BaseFragment implements View.OnClickListener {


    private Button btSubmit;


    public AddNewCardFragment() {
    }

    public static AddNewCardFragment newInstance() {
        Bundle args = new Bundle();
        AddNewCardFragment fragment = new AddNewCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View mRootView) {
        btSubmit = (Button) mRootView.findViewById(R.id.bt_submit);
        btSubmit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                UIHelper.getInstance().popFragment(getActivity().getSupportFragmentManager());
                break;
        }
    }

}
