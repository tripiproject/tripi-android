package com.tripi.wallet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.tripi.wallet.model.gson.history.History;
import com.tripi.wallet.model.gson.history.Vin;
import com.tripi.wallet.model.gson.history.Vout;
import com.tripi.wallet.ui.fragment.transaction_fragment.TransactionInteractor;
import com.tripi.wallet.ui.fragment.transaction_fragment.TransactionPresenterImpl;
import com.tripi.wallet.ui.fragment.transaction_fragment.TransactionView;

import io.realm.RealmList;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class TransactionPresenterTest {

    private static final RealmList<Vout> vouts = new RealmList<>();//Arrays.asList(new Vout("address"), new Vout("address"));
    private static final RealmList<Vin> vins = new RealmList<>();//Arrays.asList(new Vin("address"), new Vin("address"));
    private static final History TEST_HISTORY_WITH_BLOCK_TIME = new History(Long.valueOf("12"), vouts, vins, "10", 10);
    private static final History TEST_HISTORY_WITHOUT_BLOCK_TIME = new History(vouts, vins, "10", 10);

    @Mock
    private TransactionView view;
    @Mock
    private TransactionInteractor interactor;
    private TransactionPresenterImpl presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        presenter = new TransactionPresenterImpl(view, interactor);
    }

    @Test
    public void openTransactionView_FullDate() {
        when(interactor.getHistory(anyString()))
                .thenReturn(TEST_HISTORY_WITH_BLOCK_TIME);
        when(interactor.getFullDate(anyLong()))
                .thenReturn("full date");

        presenter.openTransactionView(anyString());

        verify(interactor, times(1)).getFullDate(anyLong());
        verify(view, times(1)).setUpTransactionData(anyString(), anyString(), anyString(), anyBoolean(), anyBoolean());

        verify(interactor, never()).getUnconfirmedDate();
    }

    @Test
    public void openTransactionView_UnconfirmedDate() {
        when(interactor.getHistory(anyString()))
                .thenReturn(TEST_HISTORY_WITHOUT_BLOCK_TIME);
        when(interactor.getFullDate(anyLong()))
                .thenReturn("full date");

        presenter.openTransactionView(anyString());

        verify(interactor, times(1)).getUnconfirmedDate();
        verify(view, times(1)).setUpTransactionData(anyString(), anyString(), anyString(), anyBoolean(), anyBoolean());

        verify(interactor, never()).getFullDate(anyLong());

    }


}
