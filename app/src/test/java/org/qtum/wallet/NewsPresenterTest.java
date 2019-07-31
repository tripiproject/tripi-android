package com.tripi.wallet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.tripi.wallet.ui.fragment.news_fragment.NewsInteractor;
import com.tripi.wallet.ui.fragment.news_fragment.NewsPresenterImpl;
import com.tripi.wallet.ui.fragment.news_fragment.NewsView;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class NewsPresenterTest {

    @Mock
    private NewsView view;
    @Mock
    private NewsInteractor interactor;
    private NewsPresenterImpl presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        presenter = new NewsPresenterImpl(view, interactor);
    }

    @Test
    public void onNetworkStateChangedFalse() {
        presenter.onNetworkStateChanged(false);

        verify(view, times(1)).updateNews(anyList());
    }

}