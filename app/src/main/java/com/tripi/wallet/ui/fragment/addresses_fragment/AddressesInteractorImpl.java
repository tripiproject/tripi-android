package com.tripi.wallet.ui.fragment.addresses_fragment;

import android.content.Context;

import com.tripi.wallet.datastorage.KeyStorage;

import java.util.List;

class AddressesInteractorImpl implements AddressesInteractor {

    Context mContext;

    AddressesInteractorImpl(Context context) {
        mContext = context;
    }

    @Override
    public List<String> getKeyList() {
        return KeyStorage.getInstance().getAddresses();
    }
}
