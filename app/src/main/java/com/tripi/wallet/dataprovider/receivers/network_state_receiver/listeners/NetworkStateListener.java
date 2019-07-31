package com.tripi.wallet.dataprovider.receivers.network_state_receiver.listeners;

public interface NetworkStateListener {
    void onNetworkStateChanged(boolean networkConnectedFlag);
}
