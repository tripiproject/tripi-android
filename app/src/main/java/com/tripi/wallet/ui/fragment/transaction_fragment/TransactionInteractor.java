package com.tripi.wallet.ui.fragment.transaction_fragment;

import com.tripi.wallet.model.gson.history.History;
import com.tripi.wallet.model.gson.history.TransactionReceipt;
import com.tripi.wallet.model.gson.token_history.TokenHistory;

import io.realm.Realm;

public interface TransactionInteractor {

    History getHistory(String txHash);

    TokenHistory getTokenHistory(String txHash);

    String getFullDate(long l);

    String getUnconfirmedDate();

    TransactionReceipt getHistoryReceipt(Realm realm, String txHash);
}
