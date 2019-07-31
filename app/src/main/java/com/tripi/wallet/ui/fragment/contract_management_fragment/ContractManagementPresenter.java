package com.tripi.wallet.ui.fragment.contract_management_fragment;

import com.tripi.wallet.model.contract.Contract;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentPresenter;

public interface ContractManagementPresenter extends BaseFragmentPresenter {
    Contract getContractByAddress(String contractAddress);
}
