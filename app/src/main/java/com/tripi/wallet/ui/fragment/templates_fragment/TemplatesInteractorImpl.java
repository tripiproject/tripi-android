package com.tripi.wallet.ui.fragment.templates_fragment;

import android.content.Context;

import com.tripi.wallet.datastorage.TinyDB;
import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.utils.DateCalculator;

import java.lang.ref.WeakReference;
import java.util.List;

public class TemplatesInteractorImpl implements TemplatesInteractor {

    private WeakReference<Context> mContext;

    public TemplatesInteractorImpl(Context context) {
        mContext = new WeakReference<>(context);
    }

    @Override
    public List<ContractTemplate> getContractTemplates() {
        TinyDB tinyDB = new TinyDB(mContext.get());
        return tinyDB.getContractTemplateList();
    }

    @Override
    public int compareDates(String date, String date1) {
        return DateCalculator.equals(date, date1);
    }
}
