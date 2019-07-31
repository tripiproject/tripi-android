package com.tripi.wallet.ui.fragment.qstore_by_type.dark;

import com.tripi.wallet.model.gson.qstore.QSearchItem;
import com.tripi.wallet.ui.fragment.qstore_by_type.QStoreByTypeFragment;
import com.tripi.wallet.ui.fragment.qstore_by_type.StoreSearchAdapter;

import java.util.List;

public class QStoreByTypeFragmentDark extends QStoreByTypeFragment {

    @Override
    protected int getLayout() {
        return com.tripi.wallet.R.layout.lyt_q_store;
    }

    @Override
    public void setSearchResult(List<QSearchItem> items) {
        searchAdapter = new StoreSearchAdapter(items, this, com.tripi.wallet.R.layout.lyt_store_search_list_item);
        contentList.setAdapter(searchAdapter);
    }
}
