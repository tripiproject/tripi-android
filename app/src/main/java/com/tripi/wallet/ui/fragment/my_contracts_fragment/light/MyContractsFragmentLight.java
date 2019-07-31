package com.tripi.wallet.ui.fragment.my_contracts_fragment.light;

import com.tripi.wallet.R;
import com.tripi.wallet.model.contract.Contract;
import com.tripi.wallet.ui.fragment.my_contracts_fragment.ContractItemListener;
import com.tripi.wallet.ui.fragment.my_contracts_fragment.MyContractsFragment;

import java.util.List;

public class MyContractsFragmentLight extends MyContractsFragment {

    @Override
    protected int getLayout() {
        return R.layout.fragment_my_contracts_light;
    }

    @Override
    public void setUpRecyclerView(List<Contract> contractList, ContractItemListener contractItemListener) {
        mContractAdapter = new ContractAdapter(contractList, R.layout.item_contract_list_light, contractItemListener);
        mRecyclerView.setAdapter(mContractAdapter);
    }
}
