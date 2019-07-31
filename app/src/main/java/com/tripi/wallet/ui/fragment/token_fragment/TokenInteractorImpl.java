package com.tripi.wallet.ui.fragment.token_fragment;

import android.content.Context;

import com.tripi.wallet.dataprovider.rest_api.tripi.TripiService;
import com.tripi.wallet.datastorage.FileStorageManager;
import com.tripi.wallet.datastorage.KeyStorage;
import com.tripi.wallet.datastorage.TinyDB;
import com.tripi.wallet.model.contract.Token;
import com.tripi.wallet.model.gson.history.TransactionReceipt;
import com.tripi.wallet.model.gson.token_history.TokenHistory;
import com.tripi.wallet.model.gson.token_history.TokenHistoryResponse;
import com.tripi.wallet.ui.fragment.wallet_fragment.HistoryInDbChangeListener;
import com.tripi.wallet.utils.ContractManagementHelper;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import javax.annotation.Nullable;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TokenInteractorImpl implements TokenInteractor {

    private WeakReference<Context> mContext;
    private Realm mRealm;
    private RealmResults<TokenHistory> mTokenHistories;
    private HistoryInDbChangeListener<TokenHistory> mHistoryInDbChangeListener;

    public TokenInteractorImpl(Context context, Realm realm, String contractAddress) {
        this.mContext = new WeakReference<>(context);
        mRealm = realm;
        mTokenHistories = realm.where(TokenHistory.class).equalTo("contractAddress",contractAddress).sort("txTime", Sort.DESCENDING).findAll();
        mTokenHistories.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<TokenHistory>>() {
            @Override
            public void onChange(RealmResults<TokenHistory> histories, @Nullable OrderedCollectionChangeSet changeSet) {
                if(mHistoryInDbChangeListener!=null){
                    mHistoryInDbChangeListener.onHistoryChange(histories, changeSet);
                }
            }
        });
    }

    @Override
    public String getCurrentAddress() {
        return KeyStorage.getInstance().getCurrentAddress();
    }

    @Override
    public String readAbiContract(String uiid) {
        return FileStorageManager.getInstance().readAbiContract(mContext.get(), uiid);
    }

    @Override
    public void setupPropertyTotalSupplyValue(Token token, Subscriber<String> stringSubscriber) {
        ContractManagementHelper.getPropertyValue(TokenFragment.totalSupply, token, mContext.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    @Override
    public void setupPropertyDecimalsValue(Token token, Subscriber<String> stringSubscriber) {
        ContractManagementHelper.getPropertyValue(TokenFragment.decimals, token, mContext.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    @Override
    public void setupPropertySymbolValue(Token token, Subscriber<String> stringSubscriber) {
        ContractManagementHelper.getPropertyValue(TokenFragment.symbol, token, mContext.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    @Override
    public void setupPropertyNameValue(Token token, Subscriber<String> stringSubscriber) {
        ContractManagementHelper.getPropertyValue(TokenFragment.name, token, mContext.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSubscriber);
    }

    @Override
    public Token setTokenDecimals(Token token, String value) {
        return new TinyDB(mContext.get()).setTokenDecimals(token, Integer.valueOf(value));
    }

    @Override
    public String handleTotalSupplyValue(Token token, String value) {
        BigDecimal bigDecimalTotalSupply = new BigDecimal(value);
        if (token.getDecimalUnits() != null) {
            BigDecimal divide = bigDecimalTotalSupply.divide(new BigDecimal(Math.pow(10, token.getDecimalUnits())), MathContext.DECIMAL128);
            value = divide.toPlainString();
        }
        return value;
    }


    @Override
    public Observable<TokenHistoryResponse> getHistoryList(String contractAddress, int limit, int offset) {
        return TripiService.newInstance().getTokenHistoryList(contractAddress,limit,offset,getAddresses());
    }

    @Override
    public List<String> getAddresses() {
        return KeyStorage.getInstance().getAddresses();
    }

    @Override
    public int getTokenHistoryDbCount() {
        return mTokenHistories.size();
    }

    @Override
    public List<TokenHistory> getTokenHistoryDb(int startIndex, int length) {
        return mTokenHistories.subList(startIndex, length);
    }

    @Override
    public void updateHistoryInRealm(final List<TokenHistory> histories) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(histories);
            }
        });
    }

    @Override
    public void addHistoryInDbChangeListener(HistoryInDbChangeListener listener) {
        mHistoryInDbChangeListener = listener;
    }

    @Override
    public TransactionReceipt getReceiptByRxhHashFromRealm(String txHash) {
        return mRealm.where(TransactionReceipt.class).equalTo("transactionHash", txHash).findFirst();
    }

    @Override
    public Observable<List<TransactionReceipt>> getTransactionReceipt(String txHash) {
        return TripiService.newInstance().getTransactionReceipt(txHash);
    }

    @Override
    public void setUpHistoryReceipt(final String txHash) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TokenHistory history = realm.where(TokenHistory.class).equalTo("txHash", txHash).findFirst();
                history.setReceiptUpdated(true);
                realm.insertOrUpdate(history);
            }
        });
    }

    @Override
    public void updateReceiptInRealm(final TransactionReceipt transactionReceipt) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(transactionReceipt);
            }
        });

    }
}