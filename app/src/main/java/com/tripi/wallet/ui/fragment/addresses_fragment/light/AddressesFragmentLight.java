package com.tripi.wallet.ui.fragment.addresses_fragment.light;

import com.tripi.wallet.R;
import com.tripi.wallet.ui.fragment.addresses_fragment.AddressesFragment;

import java.util.List;

public class AddressesFragmentLight extends AddressesFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_addresses_light;
    }

    @Override
    public void updateAddressList(List<String> deterministicKeys) {
        mAddressAdapter = new AddressesAdapterLight(deterministicKeys, this);
        mRecyclerView.setAdapter(mAddressAdapter);
    }
}
