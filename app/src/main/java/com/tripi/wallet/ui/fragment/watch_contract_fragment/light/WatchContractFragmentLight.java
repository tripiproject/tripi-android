package com.tripi.wallet.ui.fragment.watch_contract_fragment.light;

import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.ui.fragment.watch_contract_fragment.OnTemplateClickListener;
import com.tripi.wallet.ui.fragment.watch_contract_fragment.TemplatesAdapter;
import com.tripi.wallet.ui.fragment.watch_contract_fragment.WatchContractFragment;
import com.tripi.wallet.utils.FontManager;

import java.util.List;

public class WatchContractFragmentLight extends WatchContractFragment {

    @Override
    protected int getLayout() {
        return com.tripi.wallet.R.layout.fragment_watch_contract_light;
    }

    @Override
    public void initializeViews() {
        super.initializeViews();
        mEditTextContractName.setTypeface(FontManager.getInstance().getFont(getResources().getString(com.tripi.wallet.R.string.proximaNovaSemibold)));
        mEditTextContractAddress.setTypeface(FontManager.getInstance().getFont(getResources().getString(com.tripi.wallet.R.string.proximaNovaSemibold)));
    }

    @Override
    public void setUpTemplatesList(List<ContractTemplate> contractTemplateList, OnTemplateClickListener listener) {
        mRecyclerViewTemplates.setAdapter(new TemplatesAdapter(contractTemplateList, listener, com.tripi.wallet.R.layout.item_template_chips_light, com.tripi.wallet.R.drawable.chip_selected_corner_background));
    }
}
