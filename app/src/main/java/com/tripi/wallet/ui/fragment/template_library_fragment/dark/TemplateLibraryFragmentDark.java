package com.tripi.wallet.ui.fragment.template_library_fragment.dark;

import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.ui.fragment.template_library_fragment.TemplateLibraryFragment;

import java.util.List;

public class TemplateLibraryFragmentDark extends TemplateLibraryFragment {

    @Override
    protected int getLayout() {
        return com.tripi.wallet.R.layout.fragment_template_library;
    }

    @Override
    public void setUpTemplateList(List<ContractTemplate> contractTemplateList) {
        initializeRecyclerView(contractTemplateList, com.tripi.wallet.R.layout.item_template);
    }
}
