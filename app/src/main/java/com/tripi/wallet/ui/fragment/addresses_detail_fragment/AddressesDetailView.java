package com.tripi.wallet.ui.fragment.addresses_detail_fragment;


import com.tripi.wallet.model.gson.history.Vin;
import com.tripi.wallet.model.gson.history.Vout;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentView;
import com.tripi.wallet.ui.fragment.transaction_fragment.HistoryType;

import java.util.List;

interface AddressesDetailView extends BaseFragmentView{
    void setUpRecyclerView(List<Vin> transactionFrom, List<Vout> transactionTo, String symbol);
    String getTxHash();
    HistoryType getHistoryType();
    int getDecimalUnits();
    String getSymbol();
}
