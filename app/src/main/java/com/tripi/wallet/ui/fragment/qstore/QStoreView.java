package com.tripi.wallet.ui.fragment.qstore;

import com.tripi.wallet.model.gson.qstore.QSearchItem;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentView;
import com.tripi.wallet.ui.fragment.qstore.categories.QstoreCategory;

import java.util.List;

public interface QStoreView extends BaseFragmentView {
    void setCategories(List<QstoreCategory> categories);

    void setSearchResult(List<QSearchItem> items);

    void setSearchBarText(String text);

    String getSeacrhBarText();
}
