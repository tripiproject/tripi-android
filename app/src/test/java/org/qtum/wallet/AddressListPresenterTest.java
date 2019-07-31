package com.tripi.wallet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.tripi.wallet.model.AddressWithBalance;
import com.tripi.wallet.model.gson.UnspentOutput;
import com.tripi.wallet.ui.fragment.tripi_cash_management_fragment.AddressListInteractor;
import com.tripi.wallet.ui.fragment.tripi_cash_management_fragment.AddressListPresenterImpl;
import com.tripi.wallet.ui.fragment.tripi_cash_management_fragment.AddressListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class AddressListPresenterTest {

    @Mock
    private AddressListView view;
    @Mock
    private AddressListInteractor interactor;
    private AddressListPresenterImpl presenter;

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

        presenter = new AddressListPresenterImpl(view, interactor);
    }

    List<UnspentOutput> UNSPENT_OUTPUTS = new ArrayList<>();

    @Test
    public void initialize_Success() {
        UNSPENT_OUTPUTS.add(new UnspentOutput(12, true, new BigDecimal(123)));
        when(interactor.getUnspentOutputs((List<String>) any()))
                .thenReturn(Observable.just(UNSPENT_OUTPUTS));

        presenter.onViewCreated();

        verify(view, times(1)).dismissProgressDialog();
        verify(view, times(1)).updateAddressList((List<AddressWithBalance>) any());
    }


    @Test
    public void initialize_Error() {
        when(interactor.getUnspentOutputs((List<String>) any()))
                .thenReturn(Observable.<List<UnspentOutput>>error(new Throwable("Error")));

        presenter.onViewCreated();

        verify(view, times(1)).dismissProgressDialog();
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().reset();
    }

}
