package com.tripi.wallet.ui.fragment.wallet_main_fragment.dark;

import android.view.View;

import com.tripi.wallet.ui.fragment.wallet_fragment.dark.WalletFragmentDark;
import com.tripi.wallet.ui.fragment.wallet_main_fragment.WalletMainFragment;

public class WalletMainFragmentDark extends WalletMainFragment {
    @Override
    protected int getLayout() {
        return com.tripi.wallet.R.layout.fragment_wallet_main;
    }

    @Override
    public void showPageIndicator() {
        ((WalletFragmentDark) ((FragmentAdapter) pager.getAdapter()).getWalletFragment()).pagerIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePageIndicator() {
        ((WalletFragmentDark) ((FragmentAdapter) pager.getAdapter()).getWalletFragment()).pagerIndicator.setVisibility(View.INVISIBLE);
    }
}
