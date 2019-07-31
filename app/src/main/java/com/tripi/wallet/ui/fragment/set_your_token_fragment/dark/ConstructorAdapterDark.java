package com.tripi.wallet.ui.fragment.set_your_token_fragment.dark;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tripi.wallet.model.contract.ContractMethodParameter;
import com.tripi.wallet.ui.fragment.set_your_token_fragment.ConstructorAdapter;
import com.tripi.wallet.ui.fragment.set_your_token_fragment.InputViewHolder;
import com.tripi.wallet.ui.fragment.set_your_token_fragment.OnValidateParamsListener;

import java.util.List;

public class ConstructorAdapterDark extends ConstructorAdapter {

    public ConstructorAdapterDark(List<ContractMethodParameter> params, OnValidateParamsListener listener) {
        super(params, listener);
    }

    @Override
    public InputViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InputViewHolderDark(LayoutInflater.from(parent.getContext()).inflate(com.tripi.wallet.R.layout.lyt_constructor_input, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(InputViewHolder holder, int position) {
        holder.bind(params.get(position), position == getItemCount() - 1);
    }
}
