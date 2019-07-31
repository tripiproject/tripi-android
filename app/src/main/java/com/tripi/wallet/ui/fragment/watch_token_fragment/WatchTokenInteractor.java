package com.tripi.wallet.ui.fragment.watch_token_fragment;

import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.model.contract.Contract;
import com.tripi.wallet.model.gson.ContractParams;

import java.util.List;

import rx.Observable;

public interface WatchTokenInteractor {
    List<ContractTemplate> getContractTemplates();

    int compareDates(String date, String date1);

    String readAbiContract(String uuid);

    boolean isValidContractAddress(String address);

    List<Contract> getContracts();

    String handleContractWithToken(String name, String address, String ABIInterface);

    String getTRC20TokenStandardAbi();

    Observable<ContractParams> getContractParams(String contractAddress);
}
