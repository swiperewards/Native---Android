package com.winjit.swiperewards.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.winjit.swiperewards.R;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.interfaces.AdapterResponseInterface;

import java.util.ArrayList;

public class MyCardsAdapter extends RecyclerSwipeAdapter<MyCardsAdapter.SimpleViewHolder> {

    private Context mContext;
    private ArrayList<String> mDataset;
    private AdapterResponseInterface adapterResponseInterface;

    public MyCardsAdapter(Context context, ArrayList<String> objects, AdapterResponseInterface adapterResponseInterface) {
        this.mContext = context;
        this.mDataset = objects;
        this.adapterResponseInterface=adapterResponseInterface;
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
            viewHolder.tvCardName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vc_add, 0, 0, 0);
            viewHolder.swipeLayout.setClickable(true);
            viewHolder.swipeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("swiped", "onClick: " + position);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(ISwipe.IS_ADD_NEW_CARD,true);
                    adapterResponseInterface.getAdapterResponse(bundle);
                }
            });

        } else {
            viewHolder.tvCardName.setText("Visa **** 4445");
            viewHolder.tvCardName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vc_wallet, 0, 0, 0);
            viewHolder.swipeLayout.setSwipeEnabled(true);
            viewHolder.swipeLayout.setClickable(false);
        }

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
            }
        });


//        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
//                mDataset.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, mDataset.size());
//                mItemManger.closeAllItems();
//                Toast.makeText(view.getContext(), "Deleted " + viewHolder.textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();
//            }
//        });

        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return 3;
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
