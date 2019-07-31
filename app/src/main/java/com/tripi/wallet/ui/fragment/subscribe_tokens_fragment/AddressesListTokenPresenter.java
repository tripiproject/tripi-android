package com.tripi.wallet.ui.fragment.subscribe_tokens_fragment;

import com.tripi.wallet.model.contract.Token;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentPresenter;

public interface AddressesListTokenPresenter extends BaseFragmentPresenter {
    void onSubscribeChanged(Token token);
}
