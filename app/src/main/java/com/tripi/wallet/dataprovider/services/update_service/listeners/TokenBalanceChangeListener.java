package com.tripi.wallet.dataprovider.services.update_service.listeners;

import com.tripi.wallet.model.gson.token_balance.TokenBalance;

public interface TokenBalanceChangeListener {
    void onBalanceChange(TokenBalance tokenBalance);
}
