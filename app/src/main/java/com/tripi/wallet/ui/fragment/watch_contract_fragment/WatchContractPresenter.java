package com.tripi.wallet.ui.fragment.watch_contract_fragment;

import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentPresenter;

public interface WatchContractPresenter extends BaseFragmentPresenter {
    void onOkClick(String name, String address, String ABIInterface);

    void onTemplateClick(ContractTemplate contractTemplate);
}
