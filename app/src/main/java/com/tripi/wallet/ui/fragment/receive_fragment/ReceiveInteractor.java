package com.tripi.wallet.ui.fragment.receive_fragment;

public interface ReceiveInteractor {
    String getCurrentReceiveAddress();

    String formatReceiveAddress(String addr);

    String formatAmount(String amount);

    String formatTokenAddress(String addr);

}
