package com.tripi.wallet.ui.fragment.subscribe_tokens_fragment;

import android.content.Context;

import com.tripi.wallet.model.contract.Token;
import com.tripi.wallet.datastorage.TinyDB;

import java.util.List;

public class SubscribeTokensInteractorImpl implements SubscribeTokensInteractor {

    private Context mContext;

    public SubscribeTokensInteractorImpl(Context context) {
        mContext = context;
    }

    @Override
    public List<Token> getTokenList() {
        return (new TinyDB(mContext)).getTokenList();
    }

    @Override
    public void saveTokenList(List<Token> tokenList) {
        (new TinyDB(mContext)).putTokenList(tokenList);
    }
}
