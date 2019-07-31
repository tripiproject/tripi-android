package com.tripi.wallet.ui.fragment.start_page_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tripi.wallet.R;
import com.tripi.wallet.datastorage.TripiSharedPreference;
import com.tripi.wallet.ui.base.base_fragment.BaseFragment;
import com.tripi.wallet.ui.fragment.import_wallet_fragment.ImportWalletFragment;
import com.tripi.wallet.ui.fragment.pin_fragment.PinFragment;
import com.tripi.wallet.ui.fragment_factory.Factory;
import com.tripi.wallet.utils.FontButton;
import com.tripi.wallet.utils.ThemeUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tripi.wallet.ui.fragment.pin_fragment.PinAction.AUTHENTICATION;
import static com.tripi.wallet.ui.fragment.pin_fragment.PinAction.CREATING;

public abstract class StartPageFragment extends BaseFragment implements StartPageView {

    private StartPagePresenter mStartPageFragmentPresenter;

    @BindView(R.id.bt_create_new)
    protected Button mButtonCreateNew;
    @BindView(R.id.bt_import_wallet)
    Button mButtonImportWallet;
    @BindView(R.id.tv_start_page_you_dont_have)
    TextView mTextViewYouDontHave;
    @BindView(R.id.tv_start_page_create)
    TextView mTextViewStartPageCreate;
    @BindView(R.id.rl_button_container)
    RelativeLayout mRelativeLayoutButtonContainer;

    @BindView(R.id.bt_login)
    protected FontButton mButtonLogin;

    @BindView(R.id.logo_view)
    ImageView logoView;

    @BindView(R.id.root_layout)
    RelativeLayout rootLayout;

    public static BaseFragment newInstance(Context context) {
        Bundle args = new Bundle();
        BaseFragment fragment = Factory.instantiateFragment(context, StartPageFragment.class);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().hideBottomNavigationView(ThemeUtils.currentTheme.equals(ThemeUtils.THEME_DARK) ? R.color.background : R.color.accent_red_color);
        getMainActivity().unregisterKeyboardListener();
    }

    @OnClick({R.id.bt_import_wallet, R.id.bt_create_new, R.id.bt_login})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_create_new:
                hideLoginButton();
                clearWallet();
                BaseFragment pinFragment = PinFragment.newInstance(CREATING, getContext());
                openFragment(pinFragment);
                break;
            case R.id.bt_import_wallet:
                hideLoginButton();

                clearWallet();
                BaseFragment importWalletFragment = ImportWalletFragment.newInstance(getContext());
                openFragment(importWalletFragment);
                break;
            case R.id.bt_login:
                if (TripiSharedPreference.getInstance().getKeyGeneratedInstance(getContext())) {
                    BaseFragment fragment = PinFragment.newInstance(AUTHENTICATION, getContext());
                    openFragment(fragment);
                }
                break;
        }
    }

    @Override
    protected void createPresenter() {
        mStartPageFragmentPresenter = new StartPagePresenterImpl(this, new StartPageInteractorImpl(getContext(), getMainActivity().getRealm()));
    }

    @Override
    protected StartPagePresenter getPresenter() {
        return mStartPageFragmentPresenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_start_page;
    }

    private void clearWallet() {
        getMainActivity().onLogout();
        getMainActivity().stopUpdateService();
        getPresenter().clearWallet();
    }
}
