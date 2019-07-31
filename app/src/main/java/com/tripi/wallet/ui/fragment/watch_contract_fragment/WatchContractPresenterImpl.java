package com.tripi.wallet.ui.fragment.watch_contract_fragment;

import com.tripi.wallet.R;
import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.model.contract.Contract;
import com.tripi.wallet.ui.base.base_fragment.BaseFragment;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentPresenterImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WatchContractPresenterImpl extends BaseFragmentPresenterImpl implements WatchContractPresenter {

    private WatchContractView mWatchContractView;
    private WatchContractInteractor mWatchContractInteractor;

    public WatchContractPresenterImpl(WatchContractView view, WatchContractInteractor interactor) {
        mWatchContractView = view;
        mWatchContractInteractor = interactor;
    }

    @Override
    public void initializeViews() {
        super.initializeViews();

        List<ContractTemplate> contractFullTemplateList = new ArrayList<>();
        List<ContractTemplate> contractTemplateList = getInteractor().getContractTemplates();

        for (ContractTemplate contractTemplate : contractTemplateList) {
            if (contractTemplate.isFullContractTemplate()) {
                contractFullTemplateList.add(contractTemplate);
            }
        }
        Collections.sort(contractFullTemplateList, new Comparator<ContractTemplate>() {
            @Override
            public int compare(ContractTemplate contractInfo, ContractTemplate t1) {
                if(contractInfo.getDate()==null || t1.getDate()==null){
                    return 0;
                } else {
                    return contractInfo.getDate().compareTo(t1.getDate());
                }
            }
        });

        getView().setUpTemplatesList(contractFullTemplateList, getView().getTemplateClickListener());
    }

    @Override
    public WatchContractView getView() {
        return mWatchContractView;
    }

    @Override
    public void onOkClick(String name, String address, String ABIInterface) {
        getView().setProgressDialog();
        if (!getInteractor().isValidContractAddress(address)) {
            getView().setAlertDialog(R.string.invalid_token_address, R.string.ok, BaseFragment.PopUpType.error);
            return;
        }
        List<Contract> allContractList = getInteractor().getContracts();
        for (Contract contract : allContractList) {
            if (contract.getContractAddress().equals(address)) {
                getView().setAlertDialog(R.string.token_with_same_address_already_exists, R.string.ok, BaseFragment.PopUpType.error);
                return;
            }
        }
        getInteractor().handleContractWithoutToken(name, address, ABIInterface);
        getView().setAlertDialog(R.string.token_was_added_to_your_wallet, "", R.string.ok, BaseFragment.PopUpType.confirm, getView().getAlertCallback());
    }

    private WatchContractInteractor getInteractor() {
        return mWatchContractInteractor;
    }

    @Override
    public void onTemplateClick(ContractTemplate contractTemplate) {
        String abi = getInteractor().readAbiContract(contractTemplate.getUuid());
        getView().setABIInterface(contractTemplate.getName(), abi);
    }
}
