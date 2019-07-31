package com.tripi.wallet.ui.fragment.templates_fragment.light;

import com.tripi.wallet.R;
import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.ui.fragment.templates_fragment.TemplatesFragment;

import java.util.List;

public class TemplatesFragmentLight extends TemplatesFragment {

    @Override
    protected int getLayout() {
        return R.layout.fragment_templates_light;
    }

    @Override
    public void setUpTemplateList(List<ContractTemplate> contractTemplateList) {
        initializeRecyclerView(contractTemplateList, R.layout.item_template_light);
    }
}
