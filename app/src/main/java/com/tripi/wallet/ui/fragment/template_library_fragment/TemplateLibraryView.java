package com.tripi.wallet.ui.fragment.template_library_fragment;

import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentView;

import java.util.List;

public interface TemplateLibraryView extends BaseFragmentView {

    void setUpTemplateList(List<ContractTemplate> contractTemplateList);

}
