package com.tripi.wallet.ui.fragment.qstore.dark;

import com.tripi.wallet.model.gson.qstore.QSearchItem;
import com.tripi.wallet.ui.fragment.qstore.QStoreFragment;
import com.tripi.wallet.ui.fragment.qstore.StoreAdapter;
import com.tripi.wallet.ui.fragment.qstore.StoreSearchAdapter;
import com.tripi.wallet.ui.fragment.qstore.categories.QstoreCategory;

import java.util.List;

public class QStoreFragmentDark extends QStoreFragment {

    @Override
    protected int getLayout() {
        return com.tripi.wallet.R.layout.lyt_q_store;
    }

    @Override
    public void setCategories(List<QstoreCategory> categories) {
        storeAdapter = new StoreAdapter(categories, this, com.tripi.wallet.R.layout.lyt_store_list_item, com.tripi.wallet.R.layout.lyt_store_token_list_item);
        contentList.setAdapter(storeAdapter);
    }

    @Override
    public void setSearchResult(List<QSearchItem> items) {
        searchAdapter = new StoreSearchAdapter(items, this, com.tripi.wallet.R.layout.lyt_store_search_list_item);
        contentList.setAdapter(searchAdapter);
    }
}
