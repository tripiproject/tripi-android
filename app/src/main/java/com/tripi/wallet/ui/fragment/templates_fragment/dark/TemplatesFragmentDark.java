package com.tripi.wallet.ui.fragment.templates_fragment.dark;

import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.ui.fragment.templates_fragment.TemplatesFragment;

import java.util.List;

public class TemplatesFragmentDark extends TemplatesFragment {

    @Override
    protected int getLayout() {
        return com.tripi.wallet.R.layout.fragment_templates;
    }

    @Override
    public void setUpTemplateList(List<ContractTemplate> contractTemplateList) {
        initializeRecyclerView(contractTemplateList, com.tripi.wallet.R.layout.item_template);
    }
}
