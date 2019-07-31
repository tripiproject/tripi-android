package com.tripi.wallet.ui.fragment.token_fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tripi.wallet.R;
import com.tripi.wallet.dataprovider.receivers.network_state_receiver.NetworkStateReceiver;
import com.tripi.wallet.dataprovider.receivers.network_state_receiver.listeners.NetworkStateListener;
import com.tripi.wallet.model.contract.Contract;
import com.tripi.wallet.model.contract.Token;
import com.tripi.wallet.model.gson.token_history.TokenHistory;
import com.tripi.wallet.ui.fragment.receive_fragment.ReceiveFragment;
import com.tripi.wallet.ui.fragment.token_cash_management_fragment.AddressesListFragmentToken;
import com.tripi.wallet.ui.fragment.wallet_fragment.HistoryCountChangeListener;
import com.tripi.wallet.ui.fragment_factory.Factory;
import com.tripi.wallet.ui.base.base_fragment.BaseFragment;
import com.tripi.wallet.ui.fragment.token_fragment.dialogs.ShareDialogFragment;
import com.tripi.wallet.utils.ClipboardUtils;
import com.tripi.wallet.utils.FontTextView;

import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import io.realm.OrderedCollectionChangeSet;
import io.realm.Realm;
import rx.Subscriber;

public abstract class TokenFragment extends BaseFragment implements TokenView, TokenHistoryClickListener {

    private static final String tokenKey = "tokenInfo";
    private static final String tripiAddressKey = "tripiAddressKey";

    public static final String totalSupply = "totalSupply";
    public static final String decimals = "decimals";
    public static final String symbol = "symbol";
    public static final String name = "name";
    protected Token token;

    public static BaseFragment newInstance(Context context, Contract token) {
        Bundle args = new Bundle();
        BaseFragment fragment = Factory.instantiateFragment(context, TokenFragment.class);
        args.putSerializable(tokenKey, token);
        fragment.setArguments(args);
        return fragment;
    }

    private TokenPresenter presenter;

    @OnClick(R.id.bt_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }

    @BindView(R.id.ll_balance)
    protected LinearLayout mLinearLayoutBalance;

    @BindView(R.id.tv_balance)
    protected FontTextView mTextViewBalance;

    @BindView(R.id.tv_currency)
    protected FontTextView mTextViewCurrency;

    @BindView(R.id.available_balance_title)
    protected FontTextView balanceTitle;

    @BindView(R.id.tv_unconfirmed_balance)
    protected FontTextView uncomfirmedBalanceValue;

    @BindView(R.id.unconfirmed_balance_title)
    protected FontTextView uncomfirmedBalanceTitle;

    @BindView(R.id.balance_view)
    protected FrameLayout balanceView;

    @BindView(R.id.fade_divider_root)
    RelativeLayout fadeDividerRoot;

    @BindView(R.id.app_bar)
    protected
    AppBarLayout mAppBarLayout;

    @BindView(R.id.tv_token_address)
    FontTextView tokenAddress;

    @BindView(R.id.tv_token_name)
    protected FontTextView mTextViewTokenName;

    @BindView(R.id.contract_address_value)
    FontTextView contractAddress;

    @BindView(R.id.initial_supply_value)
    protected
    FontTextView totalSupplyValue;

    @BindView(R.id.decimal_units_value)
    protected
    FontTextView decimalsValue;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.token_histories_placeholder)
    TextView mTextViewHistoriesPlaceholder;

    @BindView(R.id.recycler_token_history)
    protected RecyclerView mRecyclerView;
    protected TokenHistoryAdapter mAdapter;
    protected LinearLayoutManager mLinearLayoutManager;

    ShareDialogFragment shareDialog;

    protected int visibleItemCount;
    protected int totalItemCount;
    protected int pastVisibleItems;
    protected boolean mLoadingFlag = false;
    private NetworkStateReceiver mNetworkStateReceiver;
    private NetworkStateListener mNetworkStateListener;

    @OnLongClick(R.id.tv_token_address)
    public boolean onAddressLongClick() {
        ClipboardUtils.copyToClipBoard(getContext(), tokenAddress.getText().toString(), new ClipboardUtils.CopyCallback() {
            @Override
            public void onCopyToClipBoard() {
                showToast(getString(R.string.copied));
            }
        });
        return true;
    }

    @OnLongClick(R.id.contract_address_value)
    public boolean onAContractLongClick() {
        ClipboardUtils.copyToClipBoard(getContext(), contractAddress.getText().toString(), new ClipboardUtils.CopyCallback() {
            @Override
            public void onCopyToClipBoard() {
                showToast(getString(R.string.copied));
            }
        });
        return true;
    }

    @OnClick(R.id.bt_share)
    public void onShareClick() {
        shareDialog = ShareDialogFragment.newInstance(presenter.getToken().getContractAddress(), presenter.getAbi());
        shareDialog.show(getFragmentManager(), shareDialog.getClass().getCanonicalName());
    }

    @OnClick(R.id.token_addr_btn)
    public void onTokenAddrClick() {
        BaseFragment receiveFragment = ReceiveFragment.newInstance(getContext(), presenter.getToken().getContractAddress(), mTextViewBalance.getText().toString(), mTextViewCurrency.getText().toString());
        openFragment(receiveFragment);
    }

    @OnClick(R.id.iv_choose_address)
    public void onChooseAddressClick() {
        if (!TextUtils.isEmpty(getCurrency())) {
            BaseFragment addressListFragment = AddressesListFragmentToken.newInstance(getContext(), getPresenter().getToken(), getCurrency());
            openFragment(addressListFragment);
        }
    }

    @Override
    public String getCurrency() {
        return mTextViewCurrency.getText().toString().trim();
    }

    @Override
    protected void createPresenter() {
        token = (Token) getArguments().getSerializable(tokenKey);
        presenter = new TokenPresenterImpl(this, new TokenInteractorImpl(getContext(),getMainActivity().getRealm(),token.getContractAddress()));
    }

    @Override
    protected TokenPresenter getPresenter() {
        return presenter;
    }

    protected float headerPAdding = 0;
    protected float percents = 1;
    protected float prevPercents = 1;

    @Override
    public void onActivityCreated(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNetworkStateReceiver = getMainActivity().getNetworkReceiver();
        mNetworkStateListener = new NetworkStateListener() {
            @Override
            public void onNetworkStateChanged(boolean networkConnectedFlag) {
                getPresenter().onNetworkStateChanged(networkConnectedFlag);
            }
        };
        mNetworkStateReceiver.addNetworkStateListener(mNetworkStateListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mNetworkStateListener != null) {
            mNetworkStateReceiver.removeNetworkStateListener(mNetworkStateListener);
        }
        if(mAdapter!=null){
            mAdapter.setHistoryCountChangeListener(null);
        }
    }

    @Override
    public void initializeViews() {
        super.initializeViews();

        uncomfirmedBalanceValue.setVisibility(View.GONE);
        uncomfirmedBalanceTitle.setVisibility(View.GONE);

        presenter.setToken(token);
        if (token.getLastBalance() != null) {
            setBalance(token.getLastBalance().toPlainString());
        }
        setTokenAddress(token.getContractAddress());
        setSenderAddress(token.getSenderAddress());
        headerPAdding = convertDpToPixel(16, getContext());

        mRecyclerView.setNestedScrollingEnabled(false);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (!mLoadingFlag) {
                        visibleItemCount = mLinearLayoutManager.getChildCount();
                        totalItemCount = mLinearLayoutManager.getItemCount();
                        pastVisibleItems = mLinearLayoutManager.findFirstVisibleItemPosition();
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount - 1) {
                            getPresenter().onLastItem(totalItemCount - 1);
                        }
                    }
                }
            }
        });
        createAdapter();
        mAdapter.setHistoryCountChangeListener(new HistoryCountChangeListener() {
            @Override
            public void onCountChange(int newCount) {
                if (newCount == 0) {
                    mTextViewHistoriesPlaceholder.setVisibility(View.VISIBLE);
                } else {
                    mTextViewHistoriesPlaceholder.setVisibility(View.GONE);
                }
            }
        });
    }

    protected abstract void createAdapter();


    protected boolean expanded = false;

    private static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    protected int getTotalRange() {
        return mAppBarLayout.getTotalScrollRange();
    }

    protected void animateText(float percents, View view, float fringe) {
        if (percents > fringe) {
            view.setScaleX(percents);
            view.setScaleY(percents);
        } else {
            view.setScaleX(fringe);
            view.setScaleY(fringe);
        }
    }

    @Override
    public void setTokenAddress(String address) {
        if (!TextUtils.isEmpty(address)) {
            contractAddress.setText(address);
        }
    }

    @Override
    public void setTripiAddress(String address) {
        if (!TextUtils.isEmpty(address)) {
            tokenAddress.setText(address);
        }
    }

    @Override
    public void updateHistory(List<TokenHistory> histories, @Nullable OrderedCollectionChangeSet changeSet, int visibleItemCount) {
        mLoadingFlag = false;
        mAdapter.setHistoryList(histories);
        if (changeSet == null) {
            mAdapter.notifyDataSetChanged();
            return;
        }

        OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
        for (int i = deletions.length - 1; i >= 0; i--) {
            OrderedCollectionChangeSet.Range range = deletions[i];
            if (range.startIndex <= visibleItemCount) {
                int length = range.length;
                if (range.startIndex + range.length > visibleItemCount) {
                    length = visibleItemCount - range.startIndex;
                }
                mAdapter.notifyItemRangeRemoved(range.startIndex, length);
            }
        }

        OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
        for (OrderedCollectionChangeSet.Range range : insertions) {
            if (range.startIndex <= visibleItemCount) {
                int length = range.length;
                if (range.startIndex + range.length > visibleItemCount) {
                    length = visibleItemCount - range.startIndex;
                }
                mAdapter.notifyItemRangeInserted(range.startIndex + 1, length);
            }
        }

        OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
        for (OrderedCollectionChangeSet.Range range : modifications) {
            if (range.startIndex <= visibleItemCount) {
                int length = range.length;
                if (range.startIndex + range.length > visibleItemCount) {
                    length = visibleItemCount - range.startIndex;
                }
                mAdapter.notifyItemRangeChanged(range.startIndex, length);
            }
        }
    }

    @Override
    public void updateHistory(List<TokenHistory> histories, int startIndex, int insertCount) {
        mLoadingFlag = false;
        mAdapter.setHistoryList(histories);
        mAdapter.notifyItemRangeChanged(startIndex, insertCount);
    }

    @Override
    public void setSenderAddress(String address) {
    }

    @Override
    public boolean isAbiEmpty(String abi) {
        return TextUtils.isEmpty(abi);
    }

    @Override
    public Subscriber<String> getTotalSupplyValueCallback() {
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                onContractPropertyUpdated(TokenFragment.totalSupply, presenter.onTotalSupplyPropertySuccess(getPresenter().getToken(), s));
            }
        };
    }

    @Override
    public Subscriber<String> getDecimalsValueCallback() {
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                onContractPropertyUpdated(TokenFragment.decimals, s);
                if (s != null) {
                    getPresenter().onDecimalsPropertySuccess(s);
                }
            }
        };
    }

    @Override
    public Subscriber<String> getSymbolValueCallback() {
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                onContractPropertyUpdated(TokenFragment.symbol, s);
                mAdapter.setSymbol(" " + s);
                mAdapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    public Subscriber<String> getNameValueCallback() {
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                onContractPropertyUpdated(TokenFragment.name, s);
            }
        };
    }

    @Override
    public void onTokenHistoryClick(String txHash) {
        getPresenter().onTransactionClick(txHash);
    }

    @Override
    public void showBottomLoader() {
        mLoadingFlag = true;
        mAdapter.setLoadingFlag(true);
        mAdapter.notifyItemChanged(totalItemCount - 1);
    }

    @Override
    public void hideBottomLoader() {
        mLoadingFlag = false;
        mAdapter.setLoadingFlag(false);
        mAdapter.notifyItemChanged(totalItemCount - 1);
    }

    @Override

    public void offlineModeView() {
    }

    @Override
    public void onlineModeView() {
    }

    @Override
    public void clearAdapter() {

    }

    @Override
    public Realm getRealm() {
        return getMainActivity().getRealm();
    }
}