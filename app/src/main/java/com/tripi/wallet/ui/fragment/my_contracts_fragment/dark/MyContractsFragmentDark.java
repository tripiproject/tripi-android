package com.tripi.wallet.ui.fragment.my_contracts_fragment.dark;

import com.tripi.wallet.model.contract.Contract;
import com.tripi.wallet.ui.fragment.my_contracts_fragment.ContractItemListener;
import com.tripi.wallet.ui.fragment.my_contracts_fragment.MyContractsFragment;

import java.util.List;

public class MyContractsFragmentDark extends MyContractsFragment {

    @Override
    protected int getLayout() {
        return com.tripi.wallet.R.layout.fragment_my_contracts;
    }


    @Override
    public void setUpRecyclerView(List<Contract> contractList, ContractItemListener contractItemListener) {
        mContractAdapter = new ContractAdapter(contractList, com.tripi.wallet.R.layout.item_contract_list, contractItemListener);
        mRecyclerView.setAdapter(mContractAdapter);
    }
}
