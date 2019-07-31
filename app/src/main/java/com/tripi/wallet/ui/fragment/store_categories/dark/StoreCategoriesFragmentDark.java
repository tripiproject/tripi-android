package com.tripi.wallet.ui.fragment.store_categories.dark;

import com.tripi.wallet.R;
import com.tripi.wallet.model.gson.QstoreContractType;
import com.tripi.wallet.ui.fragment.store_categories.StoreCategoriesAdapter;
import com.tripi.wallet.ui.fragment.store_categories.StoreCategoriesFragment;
import com.tripi.wallet.ui.fragment.store_categories.StoreCategoryViewHolder;

import java.util.List;

public class StoreCategoriesFragmentDark extends StoreCategoriesFragment {

    @Override
    protected int getLayout() {
        return R.layout.lyt_store_categories;
    }

    @Override
    public void setUpCategoriesList(List<QstoreContractType> list, StoreCategoryViewHolder.OnCategoryClickListener listener) {
        adapter = new StoreCategoriesAdapter(list, R.layout.lyt_store_category_list_item, listener);
        contentList.setAdapter(adapter);
    }
}
