package com.tripi.wallet.ui.fragment.import_wallet_fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.tripi.wallet.ui.fragment.pin_fragment.PinAction;
import com.tripi.wallet.ui.fragment.pin_fragment.PinFragment;
import com.tripi.wallet.ui.fragment_factory.Factory;
import com.tripi.wallet.ui.base.base_fragment.BaseFragment;
import com.tripi.wallet.utils.FontButton;
import com.tripi.wallet.utils.FontEditText;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class ImportWalletFragment extends BaseFragment implements ImportWalletView {

    private final int LAYOUT = com.tripi.wallet.R.layout.fragment_import_wallet;

    private ImportWalletPresenter mImportWalletFragmentPresenter;

    @BindView(com.tripi.wallet.R.id.bt_cancel)
    FontButton mButtonCancel;
    @BindView(com.tripi.wallet.R.id.bt_import)
    FontButton mButtonImport;
    @BindView(com.tripi.wallet.R.id.et_your_brain_code)
    FontEditText mEditTextYourBrainCode;

    @OnClick({com.tripi.wallet.R.id.bt_cancel, com.tripi.wallet.R.id.bt_import})
    public void onClick(View view) {
        switch (view.getId()) {
            case com.tripi.wallet.R.id.bt_cancel:
                getMainActivity().onBackPressed();
                break;
            case com.tripi.wallet.R.id.bt_import:
                getPresenter().onImportClick(mEditTextYourBrainCode.getText().toString().trim());
                break;
        }
    }

    public static BaseFragment newInstance(Context context) {
        Bundle args = new Bundle();
        BaseFragment fragment = Factory.instantiateFragment(context, ImportWalletFragment.class);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void createPresenter() {
        mImportWalletFragmentPresenter = new ImportWalletPresenterImpl(this, new ImportWalletInteractorImpl(getContext()));
    }

    @Override
    protected ImportWalletPresenter getPresenter() {
        return mImportWalletFragmentPresenter;
    }

    @Override
    public void openPinFragment(String passphrase, PinAction action) {
        BaseFragment pinFragment = PinFragment.newInstance(action, passphrase, getContext());
        openRootFragment(pinFragment);
    }

    @Override
    public void initializeViews() {
        super.initializeViews();

        mEditTextYourBrainCode.setFocusableInTouchMode(true);
        mEditTextYourBrainCode.requestFocus();
        mButtonImport.setEnabled(false);
        mEditTextYourBrainCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                getPresenter().onPassphraseChange(editable.toString());
            }
        });
        showSoftInput();
    }

    @Override
    protected int getLayout() {
        return LAYOUT;
    }

    @Override
    public void enableImportButton() {
        mButtonImport.setEnabled(true);
    }

    @Override
    public void disableImportButton() {
        mButtonImport.setEnabled(false);
    }
}
