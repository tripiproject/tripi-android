package com.tripi.wallet.ui.fragment.other_tokens;

import com.tripi.wallet.model.contract.Token;

import java.util.List;

import rx.Observable;

public interface OtherTokensInteractor {
    Observable<List<Token>> getTokenObservable();
}
