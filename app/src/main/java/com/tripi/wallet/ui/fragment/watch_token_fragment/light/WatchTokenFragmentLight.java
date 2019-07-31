package com.tripi.wallet.ui.fragment.watch_token_fragment.light;

import com.tripi.wallet.ui.fragment.watch_token_fragment.WatchTokenFragment;
import com.tripi.wallet.utils.FontManager;

public class WatchTokenFragmentLight extends WatchTokenFragment {

    @Override
    protected int getLayout() {
        return com.tripi.wallet.R.layout.fragment_watch_token_light;
    }

    @Override
    public void initializeViews() {
        super.initializeViews();
        mEditTextContractName.setTypeface(FontManager.getInstance().getFont(getResources().getString(com.tripi.wallet.R.string.proximaNovaSemibold)));
        mEditTextContractAddress.setTypeface(FontManager.getInstance().getFont(getResources().getString(com.tripi.wallet.R.string.proximaNovaSemibold)));
    }

}
