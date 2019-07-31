package com.tripi.wallet.ui.fragment.news_detail_fragment;

import com.tripi.wallet.model.news.News;

public interface NewsDetailInteractor {
    News getNews(int newsPosition);
}
