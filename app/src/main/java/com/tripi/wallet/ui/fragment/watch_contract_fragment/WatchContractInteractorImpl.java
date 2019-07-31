package com.tripi.wallet.ui.fragment.watch_contract_fragment;

import android.content.Context;

import com.tripi.wallet.datastorage.FileStorageManager;
import com.tripi.wallet.datastorage.TinyDB;
import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.model.contract.Contract;
import com.tripi.wallet.model.contract.ContractCreationStatus;
import com.tripi.wallet.utils.DateCalculator;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WatchContractInteractorImpl implements WatchContractInteractor {

    private WeakReference<Context> mContext;

    public WatchContractInteractorImpl(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    @Override
    public List<ContractTemplate> getContractTemplates() {
        TinyDB tinyDB = new TinyDB(mContext.get());
        return tinyDB.getContractTemplateList();
    }

    @Override
    public List<Contract> getContracts() {
        TinyDB tinyDB = new TinyDB(mContext.get());
        return tinyDB.getContractList();
    }

    @Override
    public int compareDates(String date, String date1) {
        return DateCalculator.equals(date, date1);
    }

    @Override
    public String readAbiContract(String uuid) {
        return FileStorageManager.getInstance().readAbiContract(mContext.get(), uuid);
    }

    @Override
    public boolean isValidContractAddress(String address) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]{40,}$");
        Matcher m = p.matcher(address);
        return m.matches();
    }

    @Override
    public void handleContractWithoutToken(String name, String address, String ABIInterface) {
        ContractTemplate contractTemplate = FileStorageManager.getInstance().importTemplate(mContext.get(), null, null, ABIInterface, "contract", "no_name",
                DateCalculator.getCurrentDate(), UUID.randomUUID().toString());
        TinyDB tinyDB = new TinyDB(mContext.get());
        List<Contract> contractList = tinyDB.getContractListWithoutToken();
        Contract contract = new Contract(address, contractTemplate.getUuid(), ContractCreationStatus.Created, DateCalculator.getCurrentDate(), "", name);
        contractList.add(contract);
        tinyDB.putContractListWithoutToken(contractList);
    }

}
