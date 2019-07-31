package com.tripi.wallet.ui.fragment.contract_management_fragment;

import com.tripi.wallet.model.contract.Contract;
import com.tripi.wallet.model.contract.ContractMethod;

import java.util.List;

public interface ContractManagementInteractor {
    List<ContractMethod> getContractListByUiid(String uiid);

    List<ContractMethod> getContractListByAbi(String abi);

    Contract getContractByAddress(String contractAddress);
}
