package com.tripi.wallet.ui.fragment.deleted_contract_fragment;


import com.tripi.wallet.ui.base.base_fragment.BaseFragmentPresenter;

public interface DeletedContractPresenter extends BaseFragmentPresenter {
    void onUnubscribeClick(String contractAddress);
}
