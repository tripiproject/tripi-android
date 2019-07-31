package com.tripi.wallet.ui.fragment.wallet_main_fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.tripi.wallet.R;
import com.tripi.wallet.dataprovider.services.update_service.UpdateService;
import com.tripi.wallet.dataprovider.services.update_service.listeners.TokenListener;
import com.tripi.wallet.ui.activity.main_activity.MainActivity;
import com.tripi.wallet.ui.base.base_nav_fragment.BaseNavFragment;
import com.tripi.wallet.ui.fragment_factory.Factory;
import com.tripi.wallet.ui.fragment.other_tokens.OtherTokensFragment;
import com.tripi.wallet.ui.fragment.wallet_fragment.WalletFragment;

import butterknife.BindView;

public abstract class WalletMainFragment extends BaseNavFragment implements WalletMainView {

    private WalletFragment mWalletFragment;
    private OtherTokensFragment mOtherTokensFragment;
    private UpdateService mUpdateService;

    public static WalletMainFragment newInstance(Context context) {
        Bundle args = new Bundle();
        WalletMainFragment fragment = (WalletMainFragment) Factory.instantiateFragment(context, WalletMainFragment.class);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void activateTab() {
        getMainActivity().setIconChecked(0);
    }

    @Override
    public String getNavigationTag() {
        return WalletMainFragment.class.getCanonicalName();
    }

    private WalletMainPresenter mWalletMainPresenter;

    @BindView(com.tripi.wallet.R.id.view_pager)
    protected
    ViewPager pager;

    @Override
    public int getRootView() {
        return R.id.fragment_container;
    }

    @Override
    protected void createPresenter() {
        mWalletMainPresenter = new WalletMainPresenterImpl(this, new WalletMainInteractorImpl(getContext()));
    }

    @Override
    protected WalletMainPresenter getPresenter() {
        return mWalletMainPresenter;
    }

    @Override
    public void initializeViews() {
        super.initializeViews();
        pager.setAdapter(new FragmentAdapter(getChildFragmentManager()));
        showBottomNavView(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMainActivity().setIconChecked(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkOtherTokens();
    }

    private void checkOtherTokens(){
        getMainActivity().subscribeServiceConnectionChangeEvent(new MainActivity.OnServiceConnectionChangeListener() {
            @Override
            public void onServiceConnectionChange(boolean isConnecting) {
                if (isConnecting) {
                    mUpdateService = getMainActivity().getUpdateService();
                    mUpdateService.addTokenListener(new TokenListener() {
                        @Override
                        public void newToken() {
                            getPresenter().checkOtherTokens();
                        }
                    });
                }
            }
        });
        getPresenter().checkOtherTokens();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden) {
            checkOtherTokens();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUpdateService != null) {
            mUpdateService.removeTokenListener();
        }
    }

    @Override
    public void showOtherTokens(boolean isShow) {
        if (pager.getAdapter() != null) {
            ((FragmentAdapter) pager.getAdapter()).showOtherTokens(isShow);
        }
    }

    public class FragmentAdapter extends FragmentStatePagerAdapter {

        int NUM_ITEMS = 1;

        private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        public WalletFragment getWalletFragment() {
            return (WalletFragment) registeredFragments.get(0);
        }

        OtherTokensFragment getOtherTokensFragment() {
            return (OtherTokensFragment) registeredFragments.get(1);
        }

        public void showOtherTokens(boolean show) {
            NUM_ITEMS = (show) ? 2 : 1;
            notifyDataSetChanged();
            if (show) {
                showPageIndicator();
                getOtherTokensFragment().notifyTokenChange();
            } else {
                hidePageIndicator();
            }
        }
        
        public FragmentAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    mWalletFragment = (WalletFragment) WalletFragment.newInstance(getContext());
                    return mWalletFragment;
                case 1:
                    mOtherTokensFragment = (OtherTokensFragment) OtherTokensFragment.newInstance(getContext());
                    return mOtherTokensFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWalletFragment != null) {
            mWalletFragment.dismiss();
        }
        if (mOtherTokensFragment != null) {
            mOtherTokensFragment.dismiss();
        }
    }
}
