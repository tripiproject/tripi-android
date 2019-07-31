package com.tripi.wallet.ui.fragment.addresses_detail_fragment;


import com.tripi.wallet.model.gson.history.History;
import com.tripi.wallet.model.gson.history.Vin;
import com.tripi.wallet.model.gson.history.Vout;
import com.tripi.wallet.model.gson.token_history.TokenHistory;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentPresenterImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class AddressesDetailPresenterImpl extends BaseFragmentPresenterImpl implements AddressesDetailPresenter {

    private AddressesDetailInteractor mAddressesDetailInteractor;
    private AddressesDetailView mAddressesDetailView;

    AddressesDetailPresenterImpl(AddressesDetailView transactionDetailFragmentView, AddressesDetailInteractor addressesDetailInteractor) {
        mAddressesDetailInteractor = addressesDetailInteractor;
        mAddressesDetailView = transactionDetailFragmentView;
    }

    @Override
    public AddressesDetailView getView() {
        return mAddressesDetailView;
    }

    private AddressesDetailInteractor getInteractor() {
        return mAddressesDetailInteractor;
    }

    @Override
    public void initializeViews() {
        super.initializeViews();
        switch (getView().getHistoryType()){
            case History:
                History history = getInteractor().getHistory(getView().getTxHash());
                if (history != null) {
                    getView().setUpRecyclerView(history.getVin(), history.getVout(), "TRIPI");
                }
                break;
            case Token_History:
                TokenHistory  tokenHistory = getInteractor().getTokenHistory(getView().getTxHash());
                if(tokenHistory!=null){
                    List<Vin> vinList = new ArrayList<>();
                    List<Vout> voutList = new ArrayList<>();
                    vinList.add(new Vin(tokenHistory.getFrom(), new BigDecimal(tokenHistory.getAmount()).divide(new BigDecimal("10").pow(getView().getDecimalUnits())).toString()));
                    voutList.add(new Vout(tokenHistory.getTo(), new BigDecimal(tokenHistory.getAmount()).divide(new BigDecimal("10").pow(getView().getDecimalUnits())).toString()));
                    getView().setUpRecyclerView(vinList, voutList, getView().getSymbol());
                }
                break;
        }


    }
}
