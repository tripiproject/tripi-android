package com.tripi.wallet.ui.fragment.backup_wallet_fragment.dark;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tripi.wallet.R;
import com.tripi.wallet.ui.fragment.backup_wallet_fragment.BackUpWalletFragment;

import butterknife.BindView;

public class BackUpWalletFragmentDark extends BackUpWalletFragment {

    @BindView(R.id.toolbar)
    View toolbar;
    @BindView(R.id.ibt_back)
    ImageButton btnBack;
    @BindView(R.id.bt_share)
    ImageButton btnShare;
    @BindView(R.id.tv_toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int getLayout() {
        return R.layout.fragment_back_up_wallet;
    }

    @Override
    public void initializeViews() {
        super.initializeViews();
        getMainActivity().recolorStatusBar(R.color.background);
        getMainActivity().recolorStatusBar(R.color.accent_red_color);
        if (getArguments().getBoolean(IS_WALLET_CREATING)) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.background));
            btnBack.setColorFilter(getResources().getColor(R.color.colorPrimary));
            btnShare.setColorFilter(getResources().getColor(R.color.colorPrimary));
            toolbarTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

}
