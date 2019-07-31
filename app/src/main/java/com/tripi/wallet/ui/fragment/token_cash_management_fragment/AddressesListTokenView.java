package com.tripi.wallet.ui.fragment.token_cash_management_fragment;

import android.os.Handler;

import com.tripi.wallet.dataprovider.services.update_service.UpdateService;
import com.tripi.wallet.model.AddressWithTokenBalance;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentView;

import java.util.List;

public interface AddressesListTokenView extends BaseFragmentView {
    void updateAddressList(List<AddressWithTokenBalance> deterministicKeyWithBalance, String currency);

    UpdateService getSocketInstance();

    Handler getHandler();

    void hideTransferDialog();

    void goToSendFragment(AddressWithTokenBalance keyWithTokenBalanceFrom, AddressWithTokenBalance keyWithBalanceTo, String amountString, String contractAddress);
}
