package com.tripi.wallet.ui.fragment.store_contract;

import com.tripi.wallet.model.gson.qstore.QstoreContract;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentView;

public interface StoreContractView extends BaseFragmentView {
    void setContractData(QstoreContract contract);

    void openAbiViewer(String abi);

    void openSourceCode(String sourceCode);

    void openDetails(String abi);

    void setContractPayStatus(String status);

    void disablePurchase();
}

