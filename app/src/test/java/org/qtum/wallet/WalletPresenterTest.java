package com.tripi.wallet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.tripi.wallet.model.gson.history.History;
import com.tripi.wallet.model.gson.history.HistoryResponse;
import com.tripi.wallet.model.gson.history.Vin;
import com.tripi.wallet.model.gson.history.Vout;
import com.tripi.wallet.ui.fragment.wallet_fragment.WalletInteractor;
import com.tripi.wallet.ui.fragment.wallet_fragment.WalletPresenterImpl;
import com.tripi.wallet.ui.fragment.wallet_fragment.WalletView;

import java.util.Arrays;
import java.util.List;

import io.realm.RealmList;
import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class WalletPresenterTest {

    @Mock
    private WalletView view;
    @Mock
    private WalletInteractor interactor;
    private WalletPresenterImpl presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });
        presenter = new WalletPresenterImpl(view, interactor);
    }

    private static final History TEST_HISTORY = new History();
    private static final List<History> TEST_HISTORY_LIST = Arrays.asList(TEST_HISTORY);
    private static final HistoryResponse TEST_HISTORY_RESPONSE = new HistoryResponse(10, Arrays.asList(TEST_HISTORY));


    @Test
    public void onLastItemWithNetwork() {

        when(interactor.getHistoryList(anyInt(), anyInt())).thenReturn(Observable.just(TEST_HISTORY_RESPONSE));
        presenter.setNetworkConnectedFlag(true);
        presenter.setTotalItem(50);
        presenter.onLastItem(0);


        verify(view, times(1)).showBottomLoader();
        verify(interactor, times(1)).getHistoryList(anyInt(), anyInt());
    }

    @Test
    public void networkStateChanged_Connected() {
        when(interactor.getHistoryList(anyInt(), anyInt())).thenReturn(Observable.just(TEST_HISTORY_RESPONSE));
        presenter.onNetworkStateChanged(true);

        verify(view, times(1)).onlineModeView();
        verify(view, times(1)).clearAdapter();
        verify(interactor, times(1)).getHistoryList(anyInt(), anyInt());
    }

    @Test
    public void networkStateChanged_Disconnected() {
        presenter.onNetworkStateChanged(false);

        verify(view, times(1)).offlineModeView();
    }


    private static final History TEST_HISTORY_WITH_BLOCK_TIME = new History(Long.valueOf("12"), new RealmList<Vout>(), new RealmList<Vin>(), "12", 12);
    private static final History TEST_HISTORY_WITHOUT_BLOCK_TIME = new History(null, new RealmList<Vout>(), new RealmList<Vin>(), "12", 12);
//    @Test
//    public void onNewHistory_BlockTime_NewHistory() {
//        when(interactor.setHistory((History) any()))
//                .thenReturn(null);
//
//        presenter.onNewHistory(TEST_HISTORY_WITH_BLOCK_TIME);
//
//        verify(view, times(1)).notifyNewHistory();
//        verify(view, never()).notifyConfirmHistory(anyInt());
//        verify(interactor, never()).addToHistoryList((History) any());
//
//    }
//
//    @Test
//    public void onNewHistory_BlockTime_ConfirmHistory() {
//        when(interactor.setHistory((History) any()))
//                .thenReturn(1);
//
//        presenter.onNewHistory(TEST_HISTORY_WITH_BLOCK_TIME);
//
//        verify(view, times(1)).notifyConfirmHistory(anyInt());
//
//        verify(view, never()).notifyNewHistory();
//        verify(interactor, never()).addToHistoryList((History) any());
//    }
//
//    @Test
//    public void onNewHistory_NoBlockTime() {
//        presenter.onNewHistory(TEST_HISTORY_WITHOUT_BLOCK_TIME);
//
//        verify(view, times(1)).notifyNewHistory();
//        verify(interactor, times(1)).addToHistoryList((History) any());
//
//        verify(view, never()).notifyConfirmHistory(anyInt());
//    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().reset();
    }

}
