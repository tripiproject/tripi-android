package com.tripi.wallet.ui.fragment.transaction_fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripi.wallet.R;

import com.tripi.wallet.ui.base.base_fragment.BaseFragment;
import com.tripi.wallet.ui.fragment.addresses_detail_fragment.AddressesDetailFragment;
import com.tripi.wallet.ui.fragment.event_log_fragment.EventLogFragment;
import com.tripi.wallet.ui.fragment.overview_fragment.OverviewFragment;
import com.tripi.wallet.ui.fragment_factory.Factory;
import com.tripi.wallet.utils.FontTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;

public abstract class TransactionFragment extends BaseFragment implements TransactionView {

    @BindView(R.id.tv_value)
    TextView mTextViewValue;

    @BindView(R.id.tv_received_time)
    TextView mTextViewReceivedTime;

    @BindView(R.id.view_pager_transaction)
    ViewPager mViewPager;

    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.toolbar_layout)
    protected CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.tab_addresses)
    protected
    FontTextView tabAddresses;

    @BindView(R.id.tab_event_log)
    protected
    FontTextView tabEventLog;

    @BindView(R.id.tab_overview)
    protected
    FontTextView tabOverview;

    @BindView(R.id.not_confirmed_flag)
    protected
    FontTextView notConfFlag;

    @BindView(R.id.tv_fee)
    TextView mTextViewFee;

    @BindView(R.id.tv_symbol)
    TextView mTextViewSymbol;

    @BindView(R.id.ll_fee)
    LinearLayout mLinearLayoutFee;

    @OnClick({R.id.ibt_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibt_back:
                getActivity().onBackPressed();
                break;
        }
    }

    private final static String TX_HASH = "tx_hash";
    private final static String HISTORY_TYPE = "history_type";
    private final static String TOKEN_DECIMAL_UNITS = "token_decimal_units";
    private final static String TOKEN_SYMBOL = "token_symbol";

    private TransactionPresenter mTransactionPresenter;

    public static BaseFragment newInstance(Context context, String txHash, HistoryType historyType, Integer tokenDecimals, String tokenSymbol) {
        BaseFragment transactionFragment = Factory.instantiateFragment(context, TransactionFragment.class);
        Bundle args = new Bundle();
        args.putString(TX_HASH, txHash);
        args.putSerializable(HISTORY_TYPE, historyType);
        args.putInt(TOKEN_DECIMAL_UNITS, tokenDecimals);
        args.putString(TOKEN_SYMBOL, tokenSymbol);

        transactionFragment.setArguments(args);
        return transactionFragment;
    }

    public static BaseFragment newInstance(Context context, String txHash, HistoryType historyType) {
        BaseFragment transactionFragment = Factory.instantiateFragment(context, TransactionFragment.class);
        Bundle args = new Bundle();
        args.putString(TX_HASH, txHash);
        args.putSerializable(HISTORY_TYPE, historyType);

        transactionFragment.setArguments(args);
        return transactionFragment;
    }

    @Override
    protected void createPresenter() {
        mTransactionPresenter = new TransactionPresenterImpl(this, new TransactionInteractorImpl(getContext()));
    }

    @Override
    protected TransactionPresenter getPresenter() {
        return mTransactionPresenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().openTransactionView(getArguments().getString(TX_HASH), (HistoryType) getArguments().getSerializable(HISTORY_TYPE));
    }

    @Override
    public void initializeViews() {
        super.initializeViews();
        tabAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0, true);
            }
        });
        tabOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(1, true);
            }
        });
        tabEventLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(2, true);
            }
        });
        switch ((HistoryType)getArguments().getSerializable(HISTORY_TYPE)){
            case History:
                mLinearLayoutFee.setVisibility(View.VISIBLE);
                break;
            case Token_History:
                mLinearLayoutFee.setVisibility(View.GONE);
                break;
        }
    }

    public abstract void recolorTab(int position);

    @Override
    public void setUpTransactionData(String value, String symbol,String fee, String receivedTime, boolean confirmed, boolean isContractCall) {
        if (mViewPager.getAdapter() == null) {
            mTextViewValue.setText(value);
            mTextViewSymbol.setText(symbol);
            mTextViewFee.setText(fee);
            mTextViewReceivedTime.setText(receivedTime);
            FragmentPagerAdapter transactionPagerAdapter;
            if (isContractCall) {
                transactionPagerAdapter = new ContractTransactionPagerAdapter(getChildFragmentManager());
            } else {
                transactionPagerAdapter = new TransactionPagerAdapter(getChildFragmentManager());
                tabEventLog.setVisibility(View.GONE);
            }
            mViewPager.setAdapter(transactionPagerAdapter);
            recolorTab(0);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }

                @Override
                public void onPageSelected(int position) {
                    recolorTab(position);
                }
            });
        }
    }

    private class TransactionPagerAdapter extends FragmentPagerAdapter {

        public TransactionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return AddressesDetailFragment.newInstance(getContext(), getArguments().getString(TX_HASH), (HistoryType) getArguments().getSerializable(HISTORY_TYPE));
                }
                case 1: {
                    return OverviewFragment.newInstance(getContext(), getArguments().getString(TX_HASH), (HistoryType) getArguments().getSerializable(HISTORY_TYPE));
                }
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    private class ContractTransactionPagerAdapter extends FragmentPagerAdapter {


        public ContractTransactionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return AddressesDetailFragment.newInstance(getContext(), getArguments().getString(TX_HASH),(HistoryType) getArguments().getSerializable(HISTORY_TYPE),getDecimalUnits(),getSymbol());
                }
                case 1: {
                    return OverviewFragment.newInstance(getContext(), getArguments().getString(TX_HASH), (HistoryType) getArguments().getSerializable(HISTORY_TYPE));
                }
                case 2: {
                    return EventLogFragment.newInstance(getContext(), getArguments().getString(TX_HASH));
                }
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public Realm getRealm() {
        return getMainActivity().getRealm();
    }

    @Override
    public int getDecimalUnits() {
        return getArguments().getInt(TOKEN_DECIMAL_UNITS);
    }

    @Override
    public String getSymbol() {
        return getArguments().getString(TOKEN_SYMBOL);
    }
}
