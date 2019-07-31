package com.tripi.wallet.ui.fragment.addresses_fragment.light;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripi.wallet.R;
import com.tripi.wallet.ui.fragment.addresses_fragment.AddressHolder;
import com.tripi.wallet.ui.fragment.addresses_fragment.AddressesAdapter;
import com.tripi.wallet.ui.fragment.addresses_fragment.OnAddressClickListener;

import java.util.List;

public class AddressesAdapterLight extends AddressesAdapter {

    public AddressesAdapterLight(List<String> deterministicKeys, OnAddressClickListener listener) {
        super(deterministicKeys, listener);
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_single_checkable_light, parent, false);
        return new AddressHolderLight(view, listener);
    }

    @Override
    public void onBindViewHolder(AddressHolder holder, int position) {
        mAddress = mAddresses.get(position);
        ((AddressHolderLight) holder).bindAddress(mAddress, position);
    }
}