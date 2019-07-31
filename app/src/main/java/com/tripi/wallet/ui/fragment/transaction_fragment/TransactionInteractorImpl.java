package com.tripi.wallet.ui.fragment.transaction_fragment;

import android.content.Context;

import com.tripi.wallet.R;
import com.tripi.wallet.model.gson.history.History;
import com.tripi.wallet.model.gson.history.TransactionReceipt;
import com.tripi.wallet.model.gson.token_history.TokenHistory;
import com.tripi.wallet.utils.DateCalculator;

import java.lang.ref.WeakReference;

import io.realm.Realm;

class TransactionInteractorImpl implements TransactionInteractor {

    private WeakReference<Context> mContext;

    public TransactionInteractorImpl(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    @Override
    public History getHistory(String txHash) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(History.class)
                .equalTo("txHash", txHash)
                .findFirst();
    }

    @Override
    public TokenHistory getTokenHistory(String txHash) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(TokenHistory.class)
                .equalTo("txHash", txHash)
                .findFirst();
    }

    @Override
    public String getFullDate(long l) {
        return DateCalculator.getFullDate(l);
    }

    @Override
    public String getUnconfirmedDate() {
        return mContext.get().getString(R.string.unconfirmed);
    }

    @Override
    public TransactionReceipt getHistoryReceipt(Realm realm, String txHash) {
        return realm.where(TransactionReceipt.class).equalTo("transactionHash", txHash).findFirst();
    }
}
