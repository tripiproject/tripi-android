package com.tripi.wallet.ui.fragment.my_contracts_fragment;

import com.tripi.wallet.model.contract.Contract;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentPresenter;

public interface MyContractsPresenter extends BaseFragmentPresenter {
    void onWizardClose();
    void onContractClick(Contract contract);
    void onUnsubscribeClick();
    void onRenameContract(Contract contract);
}
