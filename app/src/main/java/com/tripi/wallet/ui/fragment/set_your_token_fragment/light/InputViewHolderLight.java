package com.tripi.wallet.ui.fragment.set_your_token_fragment.light;

import android.view.View;

import com.tripi.wallet.R;
import com.tripi.wallet.ui.fragment.set_your_token_fragment.InputViewHolder;
import com.tripi.wallet.ui.fragment.set_your_token_fragment.OnValidateParamsListener;
import com.tripi.wallet.utils.FontManager;

public class InputViewHolderLight extends InputViewHolder {

    public InputViewHolderLight(View itemView, OnValidateParamsListener listener) {
        super(itemView, listener);
        tilParam.setTypeface(FontManager.getInstance().getFont(tilParam.getContext().getString(R.string.proximaNovaRegular)));
        etParam.setTypeface(FontManager.getInstance().getFont(etParam.getContext().getString(R.string.proximaNovaSemibold)));
    }
}
