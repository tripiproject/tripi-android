package com.tripi.wallet.ui.fragment.other_tokens;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.tripi.wallet.R;
import com.tripi.wallet.datastorage.TinyDB;
import com.tripi.wallet.model.contract.Token;
import com.tripi.wallet.model.gson.token_balance.TokenBalance;
import com.tripi.wallet.dataprovider.services.update_service.listeners.TokenBalanceChangeListener;
import com.tripi.wallet.ui.fragment.token_fragment.TokenFragment;
import com.tripi.wallet.utils.ContractManagementHelper;
import com.tripi.wallet.utils.FontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TokenViewHolder extends RecyclerView.ViewHolder implements TokenBalanceChangeListener {

    @BindView(R.id.root_layout)
    RelativeLayout rootLayout;

    @BindView(R.id.token_name)
    FontTextView tokenName;

    @BindView(R.id.token_balance)
    FontTextView tokenBalanceView;

    @BindView(R.id.token_symbol)
    FontTextView mTextViewSymbol;

    @BindView(R.id.spinner)
    ProgressBar spinner;

    @BindView(R.id.unsupported_view)
    View unsupportedView;

    @BindView(R.id.ll_balance)
    View balanceRootView;

    @BindView(R.id.unsupported_icon)
    View unsupportedIcon;

    private Token mToken;
    private Context mContext;

    private UpdateSocketInstance socketInstance;

    public TokenViewHolder(View itemView, UpdateSocketInstance socketInstance, Context context, final OnTokenClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.socketInstance = socketInstance;
        mContext = context;
        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getVisibility() == View.GONE) {
                    listener.onTokenClick(getAdapterPosition());
                }
            }
        });
    }

    public void bind(final Token token) {
        unsupportedIcon.setVisibility(View.GONE);
        unsupportedView.setVisibility(View.GONE);
        balanceRootView.setVisibility(View.VISIBLE);
        tokenName.setText("");
        tokenBalanceView.setText("0.0");
        mTextViewSymbol.setText("");

        if (this.mToken != null) {
            socketInstance.getSocketInstance().removeTokenBalanceChangeListener(token.getContractAddress(), this);
        }

        this.mToken = token;

        ContractManagementHelper.getPropertyValue(TokenFragment.symbol, token, mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String string) {
                        spinner.setVisibility(View.GONE);
                        if (TextUtils.isEmpty(tokenBalanceView.getText().toString())) {
                            tokenBalanceView.setVisibility(View.VISIBLE);
                            tokenBalanceView.setText("0.0");
                        }
                        mToken = new TinyDB(itemView.getContext()).setTokenSymbol(token, string);
                        mTextViewSymbol.setVisibility(View.VISIBLE);
                        mTextViewSymbol.setText(String.format(" %s", string));
                    }
                });


        tokenName.setText(token.getContractName());
        tokenBalanceView.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        if (socketInstance.getSocketInstance() != null) {
            socketInstance.getSocketInstance().addTokenBalanceChangeListener(token.getContractAddress(), this);
        }
    }


    @Override
    public void onBalanceChange(final TokenBalance tokenBalance) {
        if (mToken.getContractAddress().equals(tokenBalance.getContractAddress())) {
            mToken.setLastBalance(tokenBalance.getTotalBalance());
            ContractManagementHelper.getPropertyValue(TokenFragment.decimals, mToken, itemView.getContext())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            mToken = new TinyDB(itemView.getContext()).setTokenDecimals(mToken, 129);
                           updateBalance();
                        }

                        @Override
                        public void onNext(String string) {
                            mToken = new TinyDB(itemView.getContext()).setTokenDecimals(mToken, Integer.valueOf(string));
                            updateBalance();
                        }
                    });
        }
    }

    private void updateBalance() {
        rootLayout.post(new Runnable() {
            @Override
            public void run() {

                spinner.setVisibility(View.GONE);
                String s = mToken.getTokenBalanceWithDecimalUnits().toString();
                tokenBalanceView.setLongNumberText(s, itemView.getContext().getResources().getDisplayMetrics().widthPixels / 2);
                tokenBalanceView.setVisibility(View.VISIBLE);

                if(!mToken.getSupportFlag()){
                    unsupportedIcon.setVisibility(View.VISIBLE);
                    unsupportedView.setVisibility(View.VISIBLE);
                    balanceRootView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
