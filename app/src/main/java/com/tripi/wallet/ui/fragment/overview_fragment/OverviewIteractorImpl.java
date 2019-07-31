package com.tripi.wallet.ui.fragment.overview_fragment;


import android.content.Context;

import com.tripi.wallet.model.gson.history.History;
import com.tripi.wallet.model.gson.history.TransactionReceipt;
import com.tripi.wallet.model.gson.token_history.TokenHistory;

import java.lang.ref.WeakReference;

import io.realm.Realm;

public class OverviewIteractorImpl implements OverviewIteractor{

    WeakReference<Context> mContext;
    private Realm mRealm;

    OverviewIteractorImpl(Context context, Realm realm){
        mContext = new WeakReference<Context>(context);
        mRealm = realm;
    }

    public History getHistory(String txHash) {
        return mRealm.where(History.class)
                .equalTo("txHash", txHash)
                .findFirst();
    }

    @Override
    public TokenHistory getTokenHistory(String txHash) {
        return mRealm.where(TokenHistory.class)
                .equalTo("txHash", txHash)
                .findFirst();
    }

    @Override
    public TransactionReceipt getReceiptByRxhHashFromRealm(String txHash) {
        return mRealm.where(TransactionReceipt.class).equalTo("transactionHash", txHash).findFirst();
    }

}
