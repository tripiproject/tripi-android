package com.tripi.wallet.ui.fragment.tripi_cash_management_fragment;

import com.tripi.wallet.model.AddressWithBalance;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentView;

import java.util.List;

public interface AddressListView extends BaseFragmentView {
    void updateAddressList(List<AddressWithBalance> deterministicKeyWithBalance);
}
