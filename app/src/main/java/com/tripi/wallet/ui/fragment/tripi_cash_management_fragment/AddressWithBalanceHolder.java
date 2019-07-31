package com.tripi.wallet.ui.fragment.tripi_cash_management_fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.tripi.wallet.R;
import com.tripi.wallet.model.AddressWithBalance;
import com.tripi.wallet.utils.ClipboardUtils;
import com.tripi.wallet.utils.FontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressWithBalanceHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.address_name)
    FontTextView mTextViewAddress;

    @BindView(R.id.address_balance)
    FontTextView mTextViewAddressBalance;

    @BindView(R.id.address_symbol)
    FontTextView mTextViewSymbol;

    AddressWithBalance mDeterministicKeyWithBalance;
    OnAddressClickListener listener;

    protected AddressWithBalanceHolder(View itemView, final OnAddressClickListener listener) {
        super(itemView);
        this.listener = listener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick();
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

    public void bindDeterministicKeyWithBalance(final AddressWithBalance deterministicKeyWithBalance) {
        mDeterministicKeyWithBalance = deterministicKeyWithBalance;
        mTextViewAddress.setText(deterministicKeyWithBalance.getAddress());

        String balance = deterministicKeyWithBalance.getBalance().toString();

        mTextViewAddressBalance.setText(balance);

        mTextViewSymbol.setText(" TRIPI");
    }

    private long mLastClickTime = System.currentTimeMillis();
    private static final long CLICK_TIME_INTERVAL = 1000;

    private void onItemClick(){
        long now = System.currentTimeMillis();
        if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
            return;
        }
        mLastClickTime = now;
        listener.onItemClick(mDeterministicKeyWithBalance);
    }
}
