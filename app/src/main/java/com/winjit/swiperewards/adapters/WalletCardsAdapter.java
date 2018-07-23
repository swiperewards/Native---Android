package com.winjit.swiperewards.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.winjit.swiperewards.R;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.WalletCard;
import com.winjit.swiperewards.interfaces.AdapterResponseInterface;

import java.util.ArrayList;

public class WalletCardsAdapter extends RecyclerSwipeAdapter<WalletCardsAdapter.SimpleViewHolder> {

    private Context mContext;
    private ArrayList<WalletCard> walletCards;
    private AdapterResponseInterface adapterResponseInterface;

    public WalletCardsAdapter(Context context, ArrayList<WalletCard> walletCards, AdapterResponseInterface adapterResponseInterface) {
        this.mContext = context;
        this.walletCards = walletCards;
        this.adapterResponseInterface = adapterResponseInterface;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_cards, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        if (position == getItemCount() - 1) {
            viewHolder.swipeLayout.setSwipeEnabled(false);
            viewHolder.tvCardName.setText("Add New Card");
            viewHolder.tvCardName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.wrapper_add, 0, 0, 0);
            viewHolder.swipeLayout.setClickable(true);
            viewHolder.swipeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("swiped", "onClick: " + position);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(ISwipe.ACTION_IS_ADD_NEW_CARD, true);
                    adapterResponseInterface.getAdapterResponse(bundle);
                }
            });

        } else {
            String lastFourDigits = getLastFourDigits(walletCards.get(position).getCardNumber());
            viewHolder.tvCardName.setText(walletCards.get(position).getNameOnCard() + " " + lastFourDigits);
            viewHolder.tvCardName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.wrapper_wallet, 0, 0, 0);
            viewHolder.swipeLayout.setSwipeEnabled(true);
            viewHolder.swipeLayout.setClickable(false);
        }

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
            }
        });


        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(ISwipe.ACTION_DELETE_CARD, true);
                bundle.putInt(ISwipe.CARD_ID, walletCards.get(position).getid);
                adapterResponseInterface.getAdapterResponse(bundle);
            }
        });

        mItemManger.bindView(viewHolder.itemView, position);
    }

    private String getLastFourDigits(String cardNumber) {
        if (!TextUtils.isEmpty(cardNumber) && cardNumber.length() > 4) {
            return cardNumber.substring(cardNumber.length() - 4);
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return walletCards.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        private SwipeLayout swipeLayout;
        private AppCompatImageView ivDelete;
        private AppCompatImageView ivEdit;
        private AppCompatTextView tvCardName;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            ivDelete = (AppCompatImageView) itemView.findViewById(R.id.iv_delete);
            ivEdit = (AppCompatImageView) itemView.findViewById(R.id.iv_edit);
            tvCardName = (AppCompatTextView) itemView.findViewById(R.id.tv_card_name);

        }
    }
}
