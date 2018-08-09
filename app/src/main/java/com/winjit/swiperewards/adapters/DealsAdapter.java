package com.winjit.swiperewards.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.Deals;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.interfaces.AdapterResponseInterface;

import java.util.ArrayList;


public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.AccountDetailViewHolder> {

    private final Context context;
    private AdapterResponseInterface adapterResponseInterface;
    private ArrayList<Deals> dealsList;

    public DealsAdapter(Context context, ArrayList<Deals> dealsList, AdapterResponseInterface adapterResponseInterface) {
        this.context = context;
        this.adapterResponseInterface = adapterResponseInterface;
        this.dealsList = dealsList;
    }

    @Override
    public AccountDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_deals, parent, false);
        return new AccountDetailViewHolder(view);
    }

    public void updateList(ArrayList<Deals> list){
        dealsList = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(AccountDetailViewHolder holder, final int position) {
        holder.tvStoreName.setText(dealsList.get(position).getShortDescription());
        holder.tvLocationName.setText(dealsList.get(position).getLocation());
        holder.tvCashBack.setText("" + dealsList.get(position).getCashBonus());
        holder.tvValidity.setText(dealsList.get(position).getStartDate() + "-" + dealsList.get(position).getEndDate());
        holder.rlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(ISwipe.LATITUDE,dealsList.get(position).getLatitude());
                bundle.putString(ISwipe.LONGITUDE,dealsList.get(position).getLongitude());
                adapterResponseInterface.getAdapterResponse(bundle);
            }
        });

        if (!TextUtils.isEmpty(dealsList.get(position).getIcon())) {
            UIHelper.getInstance().loadImageOnline(context, dealsList.get(position).getIcon().replace(" ", "%20"), holder.ivIcon, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return dealsList.size();
    }

    class AccountDetailViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView ivIcon;
        private AppCompatTextView tvStoreName;
        private AppCompatTextView tvLocationName;
        private AppCompatTextView tvCashBack;
        private AppCompatTextView tvValidity;
        private RelativeLayout rlParent;

        AccountDetailViewHolder(View view) {
            super(view);
            ivIcon = (AppCompatImageView) view.findViewById(R.id.iv_icon);
            tvStoreName = (AppCompatTextView) view.findViewById(R.id.tv_store_name);
            tvLocationName = (AppCompatTextView) view.findViewById(R.id.tv_location_name);
            tvCashBack = (AppCompatTextView) view.findViewById(R.id.tv_cashback);
            tvValidity = (AppCompatTextView) view.findViewById(R.id.tv_validity);
            rlParent = view.findViewById(R.id.rl_parent);

        }
    }

}
