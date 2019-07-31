package com.tripi.wallet.ui.fragment.event_log_fragment;

import com.tripi.wallet.model.gson.history.Log;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentView;

import java.util.List;


public interface EventLogView extends BaseFragmentView {
    String getTxHash();
    void updateEventLog(List<Log> logs);
}
