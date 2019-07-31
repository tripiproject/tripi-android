package com.tripi.wallet.ui.fragment.watch_contract_fragment;

import com.tripi.wallet.model.ContractTemplate;

public interface OnTemplateClickListener {
    void updateSelection(int adapterPosition);

    void onTemplateClick(ContractTemplate contractTemplate);
}
