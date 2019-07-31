package com.tripi.wallet.ui.fragment.pin_fragment;

import com.tripi.wallet.ui.base.base_fragment.BaseFragmentPresenter;

import javax.crypto.Cipher;

interface PinPresenter extends BaseFragmentPresenter {
    void confirm(String password);

    void cancel();

    void setAction(PinAction action);

    void onAuthenticationSucceeded(Cipher cipher);
}
