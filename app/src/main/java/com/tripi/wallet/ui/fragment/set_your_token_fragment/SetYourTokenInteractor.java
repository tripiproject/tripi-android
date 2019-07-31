package com.tripi.wallet.ui.fragment.set_your_token_fragment;

import com.tripi.wallet.model.contract.ContractMethod;

public interface SetYourTokenInteractor {
    ContractMethod getContractConstructor(String uiid);
}
