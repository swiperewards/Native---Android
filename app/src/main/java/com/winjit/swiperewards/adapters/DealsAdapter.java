package com.winjit.swiperewards.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.interfaces.AdapterResponseInterface;


public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.AccountDetailViewHolder> {

    private final Context context;
    private AdapterResponseInterface adapterResponseInterface;

    public DealsAdapter(Context context, AdapterResponseInterface adapterResponseInterface) {
        this.context = context;
        this.adapterResponseInterface = adapterResponseInterface;
    }

    @Override
    public AccountDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_deals, parent, false);
        return new AccountDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountDetailViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class AccountDetailViewHolder extends RecyclerView.ViewHolder {

        AccountDetailViewHolder(View view) {
            super(view);
        }
    }

}
