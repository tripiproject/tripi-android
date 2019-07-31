package com.tripi.wallet.ui.fragment.set_your_token_fragment.dark;

import android.view.View;

import com.tripi.wallet.ui.fragment.set_your_token_fragment.InputViewHolder;
import com.tripi.wallet.ui.fragment.set_your_token_fragment.OnValidateParamsListener;
import com.tripi.wallet.utils.FontManager;

public class InputViewHolderDark extends InputViewHolder {

    public InputViewHolderDark(View itemView, OnValidateParamsListener listener) {
        super(itemView, listener);
        tilParam.setTypeface(FontManager.getInstance().getFont(tilParam.getContext().getString(com.tripi.wallet.R.string.simplonMonoRegular)));
        etParam.setTypeface(FontManager.getInstance().getFont(etParam.getContext().getString(com.tripi.wallet.R.string.simplonMonoRegular)));
    }
}
