package com.tripi.wallet.ui.activity.main_activity;

import com.tripi.wallet.ui.base.base_activity.BasePresenter;

public interface MainActivityPresenter extends BasePresenter {
    void onLogin();

    void onLogout();

    void resetAuthFlags();

    void setCheckAuthenticationFlag(boolean checkAuthenticationFlag);

    void setCheckAuthenticationShowFlag(boolean checkAuthenticationShowFlag);

    void setSendFromIntent(boolean sendFromIntent);

    boolean getAuthenticationFlag();

    boolean shouldShowPin();

    boolean isCheckAuthenticationShowFlag();

    void updateNetworkSate(boolean networkConnectedFlag);
}
