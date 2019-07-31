package com.tripi.wallet.ui.fragment.transaction_fragment;

import com.tripi.wallet.ui.base.base_fragment.BaseFragmentPresenter;

interface TransactionPresenter extends BaseFragmentPresenter {
    void openTransactionView(String txHash, HistoryType historyType);
}
