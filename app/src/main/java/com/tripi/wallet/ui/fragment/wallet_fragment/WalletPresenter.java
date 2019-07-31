package com.tripi.wallet.ui.fragment.wallet_fragment;

import com.tripi.wallet.model.gson.history.History;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentPresenter;

public interface WalletPresenter extends BaseFragmentPresenter {

    void onTransactionClick(String txHash);

    void onLastItem(int currentItemCount);

    void onNetworkStateChanged(boolean networkConnectedFlag);

    void onNewHistory(History history);

    boolean getVisibility();

    void updateVisibility(boolean value);

}
