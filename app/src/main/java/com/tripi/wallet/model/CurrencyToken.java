package com.tripi.wallet.model;

import com.tripi.wallet.model.contract.Token;

public class CurrencyToken extends Currency {

    private Token mToken;

    public CurrencyToken(String name, Token token) {
        super(name);
        mToken = token;
    }

    public Token getToken() {
        return mToken;
    }

    public void setToken(Token token) {
        mToken = token;
    }
}
