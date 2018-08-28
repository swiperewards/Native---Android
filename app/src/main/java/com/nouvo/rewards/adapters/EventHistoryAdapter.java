package com.nouvo.rewards.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nouvo.rewards.R;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.entities.EventDetails;
import com.nouvo.rewards.helpers.DateUtil;
import com.nouvo.rewards.interfaces.AdapterResponseInterface;


public class EventHistoryAdapter extends RecyclerView.Adapter<EventHistoryAdapter.AccountDetailViewHolder> {

    private final Context context;
    private AdapterResponseInterface adapterResponseInterface;
    private EventDetails[] eventDetails;

    public EventHistoryAdapter(Context context, AdapterResponseInterface adapterResponseInterface, EventDetails[] eventDetails) {
        this.context = context;
        this.adapterResponseInterface = adapterResponseInterface;
        this.eventDetails = eventDetails;
    }

    @Override
    public AccountDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_event_history, parent, false);
        return new AccountDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountDetailViewHolder holder, final int position) {
        EventDetails event = eventDetails[position];
        switch (event.getEventType()) {
            case ISwipe.EVENT_TYPE_GENERAL:
                holder.ivIcon.setImageResource(R.drawable.vc_notification);
                break;
            case ISwipe.EVENT_TYPE_REWARD:
                holder.ivIcon.setImageResource(R.drawable.vc_award_cup);
                break;
            case ISwipe.EVENT_TYPE_TRANSACTION:
                holder.ivIcon.setImageResource(R.drawable.vc_transfer);
                break;

        }
        String formattedDate = DateUtil.getFormattedDate(event.getNotificationDate(), DateUtil.DEAL_API_FORMAT, DateUtil.DISPLAY_DATE_FORMAT);
        holder.tvDate.setText(formattedDate);
        holder.tvEventDesc.setText(event.getNotificationDetails());

        if (event.getTransactionAmount() != null && event.getTransactionAmount() > 0) {
            holder.tvAmount.setVisibility(View.VISIBLE);
            if (event.getCredit() != null) {
                if (event.getCredit()) {
                    holder.tvAmount.setText("+$" + event.getTransactionAmount());
                    holder.tvAmount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.wrapper_arrow_up, 0);
                } else {
                    holder.tvAmount.setText("-$" + event.getTransactionAmount());
                    holder.tvAmount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.wrapper_arrow_down, 0);
                }
            } else {
                holder.tvAmount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        } else {
            holder.tvAmount.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        if (eventDetails == null)
            return 0;
        return eventDetails.length;
    }

    class AccountDetailViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView ivIcon;
        private AppCompatTextView tvAmount;
        private AppCompatTextView tvEventDesc;
        private AppCompatTextView tvDate;

        AccountDetailViewHolder(View view) {
            super(view);
            ivIcon = (AppCompatImageView) view.findViewById(R.id.iv_icon);
            tvAmount = (AppCompatTextView) view.findViewById(R.id.tv_amount);
            tvEventDesc = (AppCompatTextView) view.findViewById(R.id.tv_event_history);
            tvDate = (AppCompatTextView) view.findViewById(R.id.tv_date);
        }
    }

}
