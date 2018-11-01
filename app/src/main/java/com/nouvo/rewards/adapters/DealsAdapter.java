package com.nouvo.rewards.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.nouvo.rewards.R;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.entities.Deals;
import com.nouvo.rewards.helpers.UIHelper;
import com.nouvo.rewards.interfaces.DealAdapterResponseInterface;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class DealsAdapter extends RecyclerView.Adapter {

    private final Context context;
    private DealAdapterResponseInterface adapterResponseInterface;
    private ArrayList<Deals> dealsList;
    private int dealsSizeWithoutFilter;
    private boolean isEndOfPaginationReached;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public DealsAdapter(Context context, ArrayList<Deals> dealsList, DealAdapterResponseInterface adapterResponseInterface) {
        this.context = context;
        this.adapterResponseInterface = adapterResponseInterface;
        this.dealsList = dealsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_deals, parent, false);
            return new DealViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_progress_bar, parent, false);
            return new ProgressViewHolder(view);
        }
    }

    public void updateList(ArrayList<Deals> list) {
        dealsList = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof DealViewHolder) {
            ((DealViewHolder) holder).tvStoreName.setText(dealsList.get(position).getEntityName());
            String cashBack = String.format("%.2f", dealsList.get(position).getCashBonus());
            if (dealsList.get(position).getCashBonus() == 0 || cashBack.equalsIgnoreCase("0,00")) {
                ((DealViewHolder) holder).tvCashBack.setText("Upto 25% Cashback");
                ((DealViewHolder) holder).tvIncreasedPool.setVisibility(View.GONE);
            } else {
                ((DealViewHolder) holder).tvCashBack.setText("$" + cashBack);
            }

            ((DealViewHolder) holder).tvValidity.setText("Available on " + dealsList.get(position).getEndDate());

            String increasedPoolServer = dealsList.get(position).getIncreasedPool();

            if (increasedPoolServer == null || increasedPoolServer.trim().equalsIgnoreCase("")) {
                increasedPoolServer = "0";
            }

            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.UK);
            increasedPoolServer = numberFormat.format(Double.parseDouble(increasedPoolServer));
            ((DealViewHolder) holder).tvIncreasedPool.setText("(+" + increasedPoolServer + ")");

            if (!TextUtils.isEmpty(dealsList.get(position).getIcon())) {
                UIHelper.getInstance().loadImageOnline(context, dealsList.get(position).getIcon().replace(" ", "%20"), ((DealViewHolder) holder).ivIcon, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            }
            if (position >= dealsSizeWithoutFilter - 1 && !isEndOfPaginationReached) {
                adapterResponseInterface.loadMoreDeals();
            }
        } else {
            ((ProgressViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setDealsSizeWithoutFilter(int dealsSizeWithoutFilter) {
        this.dealsSizeWithoutFilter = dealsSizeWithoutFilter;
    }


    public void setEndOfPaginationReached(boolean endOfPaginationReached) {
        isEndOfPaginationReached = endOfPaginationReached;
    }

    @Override
    public int getItemCount() {
        return dealsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_ITEM;
    }

    class DealViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView ivIcon;
        private AppCompatTextView tvStoreName;
        private AppCompatTextView tvCashBack;
        private AppCompatTextView tvValidity;
        private AppCompatTextView tvIncreasedPool;
        private RelativeLayout rlParent;

        DealViewHolder(View view) {
            super(view);
            ivIcon = view.findViewById(R.id.iv_icon);
            tvStoreName = view.findViewById(R.id.tv_store_name);
            tvCashBack = view.findViewById(R.id.tv_cashback);
            tvValidity = view.findViewById(R.id.tv_validity);
            rlParent = view.findViewById(R.id.rl_parent);
            tvIncreasedPool = view.findViewById(R.id.tv_increased_pool);

            rlParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ISwipe.LATITUDE, dealsList.get(getAdapterPosition()).getLatitude());
                    bundle.putString(ISwipe.LONGITUDE, dealsList.get(getAdapterPosition()).getLongitude());
                    adapterResponseInterface.getAdapterResponse(bundle);
                }
            });


        }
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar1);
        }
    }

}
