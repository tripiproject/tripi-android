package com.tripi.wallet.ui.fragment.wallet_fragment.dark;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tripi.wallet.R;
import com.tripi.wallet.model.gson.history.History;
import com.tripi.wallet.ui.fragment.wallet_fragment.TransactionClickListener;
import com.tripi.wallet.utils.ClipboardUtils;
import com.tripi.wallet.utils.DateCalculator;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;

public class TransactionHolderDark extends RecyclerView.ViewHolder {

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

    @BindView(R.id.view_contract_indicator)
    View contractIndicator;

    @BindView(R.id.progress_indicator)
    View progressIndicator;

    Subscription mSubscription;
    History mHistory;

    public TransactionHolderDark(View itemView, final TransactionClickListener listener) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (mHistory.isReceiptUpdated()) {
                    listener.onTransactionClick(mHistory.getTxHash());
                //}
            }
        });
        ButterKnife.bind(this, itemView);
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardUtils.copyToClipBoard(mTextViewID.getContext(), mTextViewID.getText().toString(), new ClipboardUtils.CopyCallback() {
                    @Override
                    public void onCopyToClipBoard() {
                        Toast.makeText(mTextViewID.getContext(), mTextViewID.getContext().getString(R.string.copied), Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
        });
    }

    void bindTransactionData(final History history) {

        mHistory = history;

        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }

        if (history.getBlockTime() != null) {
            mSubscription = DateCalculator.getUpdater().subscribe(new Subscriber() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(Object o) {
                    mTextViewDate.setText(DateCalculator.getShortDate(history.getBlockTime() * 1000L));
                }
            });
            mTextViewDate.setText(DateCalculator.getShortDate(history.getBlockTime() * 1000L));
            if (history.isReceiptUpdated()) {
                mImageViewIcon.setVisibility(View.VISIBLE);
                if (history.isContractType()) {
                    mTextViewOperationType.setText(R.string.sent_contract);
                    mImageViewIcon.setImageResource(R.drawable.ic_sent_cont_dark);
                    contractIndicator.setBackgroundResource(R.color.colorAccent);
                } else {
                    contractIndicator.setBackgroundColor(Color.TRANSPARENT);
                    switch (history.getHistoryType()) {
                        case Received:
                            mImageViewIcon.setImageResource(R.drawable.ic_received);
                            mTextViewOperationType.setText(R.string.received);
                            break;
                        case Sent:
                            mImageViewIcon.setImageResource(R.drawable.ic_sent);
                            mTextViewOperationType.setText(R.string.sent);
                            break;
                        case Internal_Transaction:
                            mImageViewIcon.setImageResource(R.drawable.ic_sent_to_myself_dark);
                            mTextViewOperationType.setText(R.string.internal_transaction);
                            break;
                    }
                }
            } else {
                mTextViewOperationType.setText(R.string.getting_info);
                progressIndicator.setVisibility(View.VISIBLE);
                mImageViewIcon.setVisibility(View.GONE);
                contractIndicator.setBackgroundColor(Color.TRANSPARENT);
            }
        } else {
            mTextViewDate.setText(mTextViewDate.getContext().getString(R.string.unconfirmed));
        }
        progressIndicator.setVisibility(View.GONE);

        mTextViewID.setText(history.getTxHash());
        mTextViewValue.setText(history.getChangeInBalance());
    }
}