package com.tripi.wallet.ui.fragment.touch_id_preference_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.tripi.wallet.ui.fragment.backup_wallet_fragment.BackUpWalletFragment;
import com.tripi.wallet.ui.fragment_factory.Factory;
import com.tripi.wallet.ui.base.base_fragment.BaseFragment;

import butterknife.OnClick;

public abstract class TouchIDPreferenceFragment extends BaseFragment implements TouchIDPreferenceView {

    private static final String IS_IMPORTING = "is_importing";
    private static final String PIN = "pin";
    private boolean mIsImporting;

    @OnClick({com.tripi.wallet.R.id.bt_enable_touch_id, com.tripi.wallet.R.id.bt_not_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case com.tripi.wallet.R.id.bt_enable_touch_id:
                getPresenter().onEnableTouchIdClick();
            case com.tripi.wallet.R.id.bt_not_now:
                if (!mIsImporting) {
                    BaseFragment backUpWalletFragment = BackUpWalletFragment.newInstance(getContext(), true, getPin());
                    openFragment(backUpWalletFragment);
                } else {
                    getMainActivity().onLogin();
                }

                break;
        }
    }

    public static BaseFragment newInstance(Context context, boolean isImporting, String pin) {
        Bundle args = new Bundle();
        args.putBoolean(IS_IMPORTING, isImporting);
        args.putString(PIN, pin);
        BaseFragment fragment = Factory.instantiateFragment(context, TouchIDPreferenceFragment.class);
        fragment.setArguments(args);
        return fragment;
    }

    private TouchIDPreferencePresenter mTouchIDPreferencePresenterImpl;

    @Override
    public void initializeViews() {
        super.initializeViews();
        mIsImporting = getArguments().getBoolean(IS_IMPORTING);
    }

    @Override
    public void onResume() {
        super.onResume();
        hideBottomNavView(false);
    }

    @Override
    protected void createPresenter() {
        mTouchIDPreferencePresenterImpl = new TouchIDPreferencePresenterImpl(this, new TouchIDInterractorImpl(getContext()));
    }

    @Override
    protected TouchIDPreferencePresenter getPresenter() {
        return mTouchIDPreferencePresenterImpl;
    }

    @Override
    public String getPin() {
        return getArguments().getString(PIN);
    }
}