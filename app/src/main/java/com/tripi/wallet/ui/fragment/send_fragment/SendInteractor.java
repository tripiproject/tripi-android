package com.tripi.wallet.ui.fragment.send_fragment;

import com.tripi.wallet.model.contract.Token;
import com.tripi.wallet.model.gson.UnspentOutput;
import com.tripi.wallet.model.gson.call_smart_contract_response.CallSmartContractResponse;

import java.math.BigDecimal;
import java.util.List;

import rx.Observable;

public interface SendInteractor {
    void getUnspentOutputs(SendInteractorImpl.GetUnspentListCallBack callBack);

    void getUnspentOutputs(String address, final SendInteractorImpl.GetUnspentListCallBack callBack);

    void sendTx(String from, String address, String amount, String fee, SendInteractorImpl.SendTxCallBack callBack, String passphrase);

    void sendTx(String txHex, SendInteractorImpl.SendTxCallBack callBack);

    void createTx(String from, String address, String amount, String fee, BigDecimal estimateFeePerKb, SendInteractorImpl.CreateTxCallBack callBack, String passphrase);

    List<String> getAddresses();

    List<Token> getTokenList();

    String validateTokenExistance(String tokenAddress);

    String getValidatedFee(Double fee);

    String createTransactionHash(String abiParams, String contractAddress, List<UnspentOutput> unspentOutputs, int gasLimit, int gasPrice, String fee, String passphrase);

    Observable<String> createAbiMethodParamsObservable(String address, String resultAmount, String transfer);

    Observable<CallSmartContractResponse> callSmartContractObservable(Token token, String hash, String fromAddress);

    BigDecimal getFeePerKb();

    int getMinGasPrice();
}