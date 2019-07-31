package com.tripi.wallet.ui.fragment.pin_fragment;

import android.support.annotation.StringRes;

import com.tripi.wallet.ui.activity.main_activity.MainActivity;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentView;

public interface PinView extends BaseFragmentView {
    void confirmError(String errorText);

    void confirmError(@StringRes int resId);

    void updateState(int state);

    void clearError();

    void setToolBarTitle(int titleID);

    void setPin(String pin);

    void prepareSensor();

    boolean checkTouchIdEnable();

    String getPassphrase();

    void checkSensorState(MainActivity.SensorStateListener sensorStateListener);

    void openTouchIDPreferenceFragment(boolean isImporting, String pin);

    void openBackUpWalletFragment(boolean isWalletCreating, String pin);

    void openSendFragment(boolean qrCodeRecognition, String address, String amount, String tokenAddress);

    boolean checkAvailabilityTouchId();

    String getAddressForSendAction();

    String getAmountForSendAction();

    String getTokenForSendAction();

    void setDigitPin(int digit);

    void onLogin();

    void onAuth();

    void onCancelClick();

    void onBackPressed();

    void setCheckAuthenticationShowFlag(boolean checkAuthenticationShowFlag);

    void clearPin();

    int getThemedStatusBarColor();

    void unregisterKeyboardListener();
}
