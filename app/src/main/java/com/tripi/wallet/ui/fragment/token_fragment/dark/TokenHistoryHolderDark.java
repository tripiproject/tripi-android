package com.tripi.wallet.ui.fragment.token_fragment.dark;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tripi.wallet.R;

import com.tripi.wallet.model.gson.token_history.TokenHistory;
import com.tripi.wallet.ui.fragment.token_fragment.TokenHistoryClickListener;
import com.tripi.wallet.utils.ClipboardUtils;
import com.tripi.wallet.utils.DateCalculator;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;
import rx.Subscriber;
import rx.Subscription;

public class TokenHistoryHolderDark extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_value)
    TextView mTextViewValue;

    @BindView(R.id.tv_date)
    TextView mTextViewDate;

    @BindView(R.id.tv_id)
    TextView mTextViewID;

    @BindView(R.id.tv_operation_type)
    TextView mTextViewOperationType;

    @BindView(R.id.iv_icon)
    ImageView mImageViewIcon;

    @BindView(R.id.ll_transaction)
    LinearLayout mLinearLayoutTransaction;

    @BindView(R.id.progress_indicator)
    View progressIndicator;

    Subscription mSubscription;
    String mSymbol;
    TokenHistory mTokenHistory;

    int decimalUnits;

    @OnLongClick(R.id.tv_id)
    public boolean onIdLongClick() {
        ClipboardUtils.copyToClipBoard(mTextViewID.getContext(), mTextViewID.getText().toString(), new ClipboardUtils.CopyCallback() {
            @Override
            public void onCopyToClipBoard() {
                Toast.makeText(mTextViewID.getContext(), mTextViewID.getContext().getString(R.string.copied), Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    public TokenHistoryHolderDark(View itemView, final TokenHistoryClickListener listener, int decimalUnits) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTokenHistoryClick(mTokenHistory.getTxHash());
            }
        });
        ButterKnife.bind(this, itemView);
        this.decimalUnits = decimalUnits;
    }

    void bindTransactionData(final TokenHistory history, String symbol) {
        mTokenHistory = history;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        mSymbol = symbol;
        if (history.getTxTime() != null) {
            mSubscription = DateCalculator.getUpdater().subscribe(new Subscriber() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(Object o) {
                    mTextViewDate.setText(DateCalculator.getShortDate(history.getTxTime() * 1000L));
                }
            });
            mTextViewDate.setText(DateCalculator.getShortDate(history.getTxTime() * 1000L));

        } else {
            mTextViewDate.setText(mTextViewDate.getContext().getString(R.string.unconfirmed));
        }
        progressIndicator.setVisibility(View.GONE);
        if (history.isReceiptUpdated()) {
            switch (history.getHistoryType()) {
                case Sent:
                    mImageViewIcon.setImageResource(R.drawable.ic_sent);
                    break;
                case Received:
                    mImageViewIcon.setImageResource(R.drawable.ic_received);
                    break;
                case Internal_Transaction:
                    mImageViewIcon.setImageResource(R.drawable.ic_sent_to_myself_dark);
            }
        } else {
            mTextViewOperationType.setText(R.string.getting_info);
            progressIndicator.setVisibility(View.VISIBLE);
            mImageViewIcon.setVisibility(View.GONE);
        }
        mTextViewOperationType.setText(history.getHistoryType().toString());
        mTextViewID.setText(history.getTxHash());

        String resultamount = history.getAmount();

        if(decimalUnits > 0) {
            resultamount = new BigDecimal(history.getAmount()).divide(new BigDecimal("10").pow(decimalUnits)).toString();
        }

        mTextViewValue.setText(resultamount + mSymbol);
    }
}