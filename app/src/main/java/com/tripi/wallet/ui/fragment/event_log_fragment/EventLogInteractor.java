package com.tripi.wallet.ui.fragment.event_log_fragment;


import com.tripi.wallet.model.gson.history.History;

public interface EventLogInteractor {
    History getHistory(String txHash);
}
