package com.tripi.wallet.ui.fragment.set_your_token_fragment;

import com.tripi.wallet.model.contract.ContractMethod;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentPresenterImpl;

public class SetYourTokenPresenterImpl extends BaseFragmentPresenterImpl implements SetYourTokenPresenter {

    private SetYourTokenView view;
    private SetYourTokenInteractor interactor;
    private ContractMethod contractMethod;

    public SetYourTokenPresenterImpl(SetYourTokenView view, SetYourTokenInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public SetYourTokenView getView() {
        return view;
    }

    public void getConstructorByUiid(String uiid) {
        contractMethod = getInteractor().getContractConstructor(uiid);
        getView().onContractConstructorPrepared(contractMethod.getInputParams());
    }

    public SetYourTokenInteractor getInteractor() {
        return interactor;
    }
}