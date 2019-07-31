package com.tripi.wallet.ui.fragment.addresses_fragment.dark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripi.wallet.ui.fragment.addresses_fragment.AddressHolder;
import com.tripi.wallet.ui.fragment.addresses_fragment.AddressesAdapter;
import com.tripi.wallet.ui.fragment.addresses_fragment.OnAddressClickListener;

import java.util.List;

public class AddressesAdapterDark extends AddressesAdapter {

    public AddressesAdapterDark(List<String> deterministicKeys, OnAddressClickListener listener) {
        super(deterministicKeys, listener);
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(com.tripi.wallet.R.layout.item_single_checkable, parent, false);
        return new AddressHolderDark(view, listener);
    }

    @Override
    public void onBindViewHolder(AddressHolder holder, int position) {
        mAddress = mAddresses.get(position);
        ((AddressHolderDark) holder).bindAddress(mAddress, position);
    }
}
