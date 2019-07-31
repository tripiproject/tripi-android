package com.tripi.wallet.ui.fragment.addresses_fragment.dark;

import com.tripi.wallet.R;
import com.tripi.wallet.ui.fragment.addresses_fragment.AddressesFragment;

import java.util.List;

public class AddressesFragmentDark extends AddressesFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_addresses;
    }

    @Override
    public void updateAddressList(List<String> deterministicKeys) {
        mAddressAdapter = new AddressesAdapterDark(deterministicKeys, this);
        mRecyclerView.setAdapter(mAddressAdapter);
    }
}
