package com.tripi.wallet.ui.fragment.addresses_fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tripi.wallet.R;
import com.tripi.wallet.utils.ClipboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressHolder extends RecyclerView.ViewHolder {
    @BindView(com.tripi.wallet.R.id.tv_single_string)
    protected TextView mTextViewAddress;
    @BindView(com.tripi.wallet.R.id.iv_check_indicator)
    protected ImageView mImageViewCheckIndicator;
    @BindView(com.tripi.wallet.R.id.ll_single_item)
    protected LinearLayout mLinearLayoutAddress;

    protected int defaultTextColor, selectedTextColor;

    protected AddressHolder(View itemView, final OnAddressClickListener listener) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddressClick(getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardUtils.copyToClipBoard(mTextViewAddress.getContext(), mTextViewAddress.getText().toString(), new ClipboardUtils.CopyCallback() {
                    @Override
                    public void onCopyToClipBoard() {
                        Toast.makeText(mTextViewAddress.getContext(), mTextViewAddress.getContext().getString(R.string.copied), Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
        });
        ButterKnife.bind(this, itemView);
    }
}
