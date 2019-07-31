package com.tripi.wallet.ui.fragment.qstore_by_type;

import com.tripi.wallet.model.gson.qstore.QSearchItem;

import java.util.List;

import rx.Observable;

public interface QStoreByTypeInteractor {
    Observable<List<QSearchItem>> searchContractsObservable(int searchOffset, String mType, String tag, boolean byTag);
}
