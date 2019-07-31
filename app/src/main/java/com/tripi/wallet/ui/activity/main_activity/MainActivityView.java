package com.tripi.wallet.ui.activity.main_activity;

import android.support.v4.app.Fragment;

import com.tripi.wallet.ui.base.base_activity.BaseContextView;
import com.tripi.wallet.ui.fragment.pin_fragment.PinAction;

public interface MainActivityView extends BaseContextView {
    void openRootFragment(Fragment fragment);

    void popBackStack();

    void setIconChecked(int position);

    void resetMenuText();

    boolean getNetworkConnectedFlag();

    void openFragment(Fragment fragment);

    MainActivity getActivity();

    void showToast(String s);

    void clearService();

    void openPinFragmentRoot(PinAction action);

    void openPinFragment(PinAction action);

    void openStartPageFragment(boolean isLogin);
}
