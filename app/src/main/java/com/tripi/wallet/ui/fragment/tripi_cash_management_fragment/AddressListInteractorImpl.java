package com.tripi.wallet.ui.fragment.tripi_cash_management_fragment;

import com.tripi.wallet.dataprovider.rest_api.tripi.TripiService;
import com.tripi.wallet.datastorage.KeyStorage;

import com.tripi.wallet.model.gson.UnspentOutput;

import java.util.List;

import rx.Observable;

public class AddressListInteractorImpl implements AddressListInteractor {

    AddressListInteractorImpl() {
    }

    @Override
    public List<String> getAddresses() {
        return KeyStorage.getInstance().getAddresses();
    }

    @Override
    public Observable<List<UnspentOutput>> getUnspentOutputs(List<String> addresses) {
        return TripiService.newInstance().getUnspentOutputsForSeveralAddresses(addresses);
    }
}
