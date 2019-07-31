package com.tripi.wallet.ui.fragment.news_detail_fragment.dark;

import org.jsoup.select.Elements;
import com.tripi.wallet.R;
import com.tripi.wallet.ui.activity.main_activity.MainActivity;
import com.tripi.wallet.ui.fragment.news_detail_fragment.NewsDetailFragment;

public class NewsDetailFragmentDark extends NewsDetailFragment {

    @Override
    protected int getLayout() {
        return com.tripi.wallet.R.layout.fragment_news_detail;
    }

    @Override
    public void initializeViews() {
        ((MainActivity) getActivity()).showBottomNavigationView(com.tripi.wallet.R.color.colorPrimary);
        super.initializeViews();
    }

    @Override
    public void setupElements(Elements elements) {
        if (elements != null) {
            ElementsAdapterDark elementsAdapter = new ElementsAdapterDark(elements);
            mRecyclerView.setAdapter(elementsAdapter);
        } else {
            setAlertDialog(R.string.error, getString(R.string.no_internet_connection), R.string.ok, PopUpType.error, new AlertDialogCallBack() {
                @Override
                public void onButtonClick() {
                    dismiss();
                }

                @Override
                public void onButton2Click() {
                }
            });
        }
    }
}
