package com.tripi.wallet.ui.fragment.wallet_fragment;

import com.tripi.wallet.model.gson.history.History;
import com.tripi.wallet.model.gson.history.HistoryResponse;
import com.tripi.wallet.model.gson.history.TransactionReceipt;

import java.util.List;

import rx.Observable;

public interface WalletInteractor {

    Observable<HistoryResponse> getHistoryList(int limit, int offset);

    Observable<List<TransactionReceipt>> getTransactionReceipt(String txHash);

    String getAddress();

    List<String> getAddresses();

    void updateHistoryInRealm(List<History> histories);

    void updateHistoryInRealm(History histories);

    void updateReceiptInRealm(TransactionReceipt transactionReceipt);

    TransactionReceipt getReceiptByRxhHashFromRealm(String txHash);

    void setUpHistoryReceipt(String txHash, boolean isContract);

    void addHistoryInDbChangeListener(HistoryInDbChangeListener listener);

    int getHistoryDbCount();

    List<History> getHistorySubList(int startIndex, int length);

    History getHistory(String txHash);
}