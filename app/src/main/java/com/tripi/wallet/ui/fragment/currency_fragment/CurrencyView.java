package com.tripi.wallet.ui.fragment.currency_fragment;

import com.tripi.wallet.model.Currency;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentView;

import java.util.List;

public interface CurrencyView extends BaseFragmentView {
    void setCurrencyList(List<Currency> currencyList);
}
