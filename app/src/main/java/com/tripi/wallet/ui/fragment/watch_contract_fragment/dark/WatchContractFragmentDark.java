package com.tripi.wallet.ui.fragment.watch_contract_fragment.dark;

import com.tripi.wallet.R;
import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.ui.fragment.watch_contract_fragment.OnTemplateClickListener;
import com.tripi.wallet.ui.fragment.watch_contract_fragment.TemplatesAdapter;
import com.tripi.wallet.ui.fragment.watch_contract_fragment.WatchContractFragment;
import com.tripi.wallet.utils.FontManager;

import java.util.List;

public class WatchContractFragmentDark extends WatchContractFragment {

    @Override
    protected int getLayout() {
        return R.layout.fragment_watch_contract;
    }

    @Override
    public void initializeViews() {
        super.initializeViews();
        mEditTextContractName.setTypeface(FontManager.getInstance().getFont(getResources().getString(R.string.simplonMonoMedium)));
        mEditTextContractAddress.setTypeface(FontManager.getInstance().getFont(getResources().getString(R.string.simplonMonoMedium)));
    }

    @Override
    public void setUpTemplatesList(List<ContractTemplate> contractTemplateList, OnTemplateClickListener listener) {
        mRecyclerViewTemplates.setAdapter(new TemplatesAdapter(contractTemplateList, listener, R.layout.item_template_chips, R.color.accent_red_color));
    }
}
