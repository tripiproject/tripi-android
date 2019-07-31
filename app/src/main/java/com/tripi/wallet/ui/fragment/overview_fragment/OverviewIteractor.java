package com.tripi.wallet.ui.fragment.overview_fragment;


import com.tripi.wallet.model.gson.history.History;
import com.tripi.wallet.model.gson.history.TransactionReceipt;
import com.tripi.wallet.model.gson.token_history.TokenHistory;

public interface OverviewIteractor {
    History getHistory(String txHash);
    TokenHistory getTokenHistory(String txHash);
    TransactionReceipt getReceiptByRxhHashFromRealm(String txHash);
}
