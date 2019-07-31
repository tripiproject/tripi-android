package com.tripi.wallet.ui.fragment.watch_token_fragment.dark;

import com.tripi.wallet.R;

import com.tripi.wallet.ui.fragment.watch_token_fragment.WatchTokenFragment;
import com.tripi.wallet.utils.FontManager;

public class WatchTokenFragmentDark extends WatchTokenFragment {

    @Override
    protected int getLayout() {
        return R.layout.fragment_watch_token;
    }

    @Override
    public void initializeViews() {
        super.initializeViews();
        mEditTextContractName.setTypeface(FontManager.getInstance().getFont(getResources().getString(R.string.simplonMonoMedium)));
        mEditTextContractAddress.setTypeface(FontManager.getInstance().getFont(getResources().getString(R.string.simplonMonoMedium)));
    }

}
