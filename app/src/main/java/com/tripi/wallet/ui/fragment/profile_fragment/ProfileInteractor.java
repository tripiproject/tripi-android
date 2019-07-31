package com.tripi.wallet.ui.fragment.profile_fragment;

import com.tripi.wallet.datastorage.listeners.LanguageChangeListener;

public interface ProfileInteractor {
    void clearWallet();

    void setupLanguageChangeListener(LanguageChangeListener listener);

    void removeLanguageListener(LanguageChangeListener listener);

    boolean isTouchIdEnable();

    void saveTouchIdEnable(boolean isChecked);
}
