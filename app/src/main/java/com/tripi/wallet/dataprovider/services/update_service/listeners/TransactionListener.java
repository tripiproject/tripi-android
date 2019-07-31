package com.tripi.wallet.dataprovider.services.update_service.listeners;

import com.tripi.wallet.model.gson.history.History;

public interface TransactionListener {
    void onNewHistory(History history);

    boolean getVisibility();
}
