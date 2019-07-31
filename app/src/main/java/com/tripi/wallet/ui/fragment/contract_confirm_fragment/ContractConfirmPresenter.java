package com.tripi.wallet.ui.fragment.contract_confirm_fragment;

import com.tripi.wallet.model.contract.ContractMethodParameter;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentPresenter;

import java.util.List;

public interface ContractConfirmPresenter extends BaseFragmentPresenter {
    void onConfirmContract(final String uiid, final int gasLimit, final int gasPrice, final String fee, String passphrase);

    void setContractMethodParameterList(List<ContractMethodParameter> contractMethodParameterList);

    List<ContractMethodParameter> getContractMethodParameterList();
}
