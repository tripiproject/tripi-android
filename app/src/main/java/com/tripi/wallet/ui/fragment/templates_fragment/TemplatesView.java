package com.tripi.wallet.ui.fragment.templates_fragment;

import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentView;

import java.util.List;

public interface TemplatesView extends BaseFragmentView {
    void setUpTemplateList(List<ContractTemplate> contractTemplateList);
}
