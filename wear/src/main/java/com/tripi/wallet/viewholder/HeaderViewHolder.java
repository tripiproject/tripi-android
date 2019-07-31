package com.tripi.wallet.viewholder;

import android.support.wear.widget.WearableRecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tripi.wallet.R;
import com.tripi.wallet.entity.HeaderData;
import com.tripi.wallet.listener.HeaderClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirillvolkov on 22.11.2017.
 */

public class HeaderViewHolder extends WearableRecyclerView.ViewHolder {

    @BindView(R.id.tv_balance_value)
    TextView balance;

    @BindView(R.id.tv_unc_balance_value)
    TextView unconfirmedBalance;

    @BindView(R.id.tv_unc_balance_curr)
    TextView unconfirmedBalanceCurr;

    @BindView(R.id.tv_address)
    TextView address;

    HeaderData headerData;

    public HeaderViewHolder(View itemView, final HeaderClickListener headerClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setClickable(true);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerClickListener.onHeaderClick(headerData);
            }
        });
    }

    public void bind(HeaderData headerData) {
        this.headerData = headerData;
        balance.setText(headerData.getBalance());
        if (headerData.getUnconfirmedBalance() != null && Float.valueOf(headerData.getUnconfirmedBalance()) != 0f) {
            unconfirmedBalance.setVisibility(View.VISIBLE);
            unconfirmedBalanceCurr.setVisibility(View.VISIBLE);
            unconfirmedBalance.setText(headerData.getUnconfirmedBalance());
        } else {
            unconfirmedBalance.setVisibility(View.GONE);
            unconfirmedBalanceCurr.setVisibility(View.GONE);
        }

        address.setText(headerData.getAddress());
    }
}
