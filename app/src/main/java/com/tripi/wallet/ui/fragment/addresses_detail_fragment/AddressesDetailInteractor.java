package com.tripi.wallet.ui.fragment.addresses_detail_fragment;


import com.tripi.wallet.model.gson.history.History;
import com.tripi.wallet.model.gson.token_history.TokenHistory;


public interface AddressesDetailInteractor {
    History getHistory(String txHash);
    TokenHistory getTokenHistory(String txHash);
}
