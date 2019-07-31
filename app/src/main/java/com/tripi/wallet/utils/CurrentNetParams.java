package com.tripi.wallet.utils;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TripiMainNetParams;
import org.bitcoinj.params.TripiTestNetParams;

import com.tripi.wallet.BuildConfig;

public class CurrentNetParams {

    public  CurrentNetParams(){}

    public static NetworkParameters getNetParams(){
        return BuildConfig.USE_MAIN_NET? TripiMainNetParams.get() : TripiTestNetParams.get();
    }

    public static String getUrl(){
        return BuildConfig.API_URL;
    }
}
