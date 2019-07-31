package com.tripi.wallet.ui.fragment.other_tokens;

import com.tripi.wallet.model.contract.Token;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentView;

import java.util.List;

public interface OtherTokensView extends BaseFragmentView {
    void setTokensData(List<Token> tokensData);

    void updateTokensData(List<Token> tokensData);
}
