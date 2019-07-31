package com.tripi.wallet.ui.fragment.watch_contract_fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;

import com.tripi.wallet.dataprovider.services.update_service.UpdateService;
import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.ui.fragment.template_library_fragment.TemplateLibraryFragment;
import com.tripi.wallet.ui.fragment_factory.Factory;
import com.tripi.wallet.ui.base.base_fragment.BaseFragment;
import com.tripi.wallet.utils.FontButton;
import com.tripi.wallet.utils.FontTextView;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class WatchContractFragment extends BaseFragment implements WatchContractView {

    private WatchContractPresenter mWatchContractFragmentPresenter;

    @BindView(com.tripi.wallet.R.id.et_contract_name)
    protected TextInputEditText mEditTextContractName;

    @BindView(com.tripi.wallet.R.id.et_contract_address)
    protected TextInputEditText mEditTextContractAddress;

    @BindView(com.tripi.wallet.R.id.et_abi_interface)
    protected EditText mEditTextABIInterface;

    @BindView(com.tripi.wallet.R.id.tv_toolbar_watch)
    protected FontTextView mTextViewToolbar;

    @BindView(com.tripi.wallet.R.id.rv_templates)
    protected RecyclerView mRecyclerViewTemplates;

    @BindView(com.tripi.wallet.R.id.bt_ok)
    FontButton mButtonConfirm;

    private boolean isEmptyContractName = true;
    private boolean isEmptyContractAddress = true;
    private boolean isEmptyABIInterface = true;
    private UpdateService mUpdateService;

    @BindView(com.tripi.wallet.R.id.bt_choose_from_library)
    FontButton mFontButtonChooseFromLibrary;

    public static BaseFragment newInstance(Context context) {
        Bundle args = new Bundle();
        BaseFragment fragment = Factory.instantiateFragment(context, WatchContractFragment.class);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initializeViews() {
        super.initializeViews();

        mTextViewToolbar.setText(getString(com.tripi.wallet.R.string.watch_contract));

        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(getContext())
                .build();
        mRecyclerViewTemplates.setLayoutManager(chipsLayoutManager);
        mEditTextABIInterface.setHorizontallyScrolling(false);
        mEditTextABIInterface.setMaxLines(Integer.MAX_VALUE);
        mEditTextABIInterface.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isEmptyABIInterface = editable.toString().isEmpty();
                checkForNoEmpty(isEmptyABIInterface, isEmptyContractAddress, isEmptyContractName);
            }
        });

        mEditTextContractAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isEmptyContractAddress = editable.toString().isEmpty();
                checkForNoEmpty(isEmptyABIInterface, isEmptyContractAddress, isEmptyContractName);
            }
        });

        mEditTextContractName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isEmptyContractName = editable.toString().isEmpty();
                checkForNoEmpty(isEmptyABIInterface, isEmptyContractAddress, isEmptyContractName);
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUpdateService = getMainActivity().getUpdateService();
    }

    @Override
    protected void createPresenter() {
        mWatchContractFragmentPresenter = new WatchContractPresenterImpl(this, new WatchContractInteractorImpl(getContext()));
    }

    @Override
    protected WatchContractPresenter getPresenter() {
        return mWatchContractFragmentPresenter;
    }

    @Override
    public void onResume() {
       // hideBottomNavView(false);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
       // showBottomNavView(false);
    }

    @OnClick({com.tripi.wallet.R.id.ibt_back, com.tripi.wallet.R.id.bt_ok, com.tripi.wallet.R.id.bt_cancel, com.tripi.wallet.R.id.bt_choose_from_library})
    public void onClick(View view) {
        switch (view.getId()) {
            case com.tripi.wallet.R.id.bt_cancel:
            case com.tripi.wallet.R.id.ibt_back:
                getActivity().onBackPressed();
                break;
            case com.tripi.wallet.R.id.bt_ok:
                String name = mEditTextContractName.getText().toString();
                String address = mEditTextContractAddress.getText().toString();
                String jsonInterface = mEditTextABIInterface.getText().toString();
                getPresenter().onOkClick(name, address, jsonInterface);
                break;
            case com.tripi.wallet.R.id.bt_choose_from_library:
                BaseFragment templateLibraryFragment = TemplateLibraryFragment.newInstance(getContext());
                openFragmentForResult(templateLibraryFragment);
                break;
        }
    }

    @Override
    public void setABIInterface(String name, String abiInterface) {
        mEditTextABIInterface.setText(abiInterface);
    }

    public void setABIInterfaceForResult(String name, String abiInterface) {
        mEditTextABIInterface.setText(abiInterface);
        ((TemplatesAdapter) mRecyclerViewTemplates.getAdapter()).setSelection(name);
    }

    private void checkForNoEmpty(boolean... isEmptyParams) {
        for (boolean isEmpty : isEmptyParams) {
            if (isEmpty) {
                mButtonConfirm.setEnabled(false);
                return;
            }
        }
        mButtonConfirm.setEnabled(true);
    }

    @Override
    public void notifyAdapter(int adapterPosition) {
        ((TemplatesAdapter) mRecyclerViewTemplates.getAdapter()).setSelection(adapterPosition);
    }

    @Override
    public OnTemplateClickListener getTemplateClickListener() {
        return new OnTemplateClickListener() {
            @Override
            public void updateSelection(int adapterPosition) {
                notifyAdapter(adapterPosition);
            }

            @Override
            public void onTemplateClick(ContractTemplate contractTemplate) {
                getPresenter().onTemplateClick(contractTemplate);
            }
        };
    }

    @Override
    public AlertDialogCallBack getAlertCallback() {
        return new BaseFragment.AlertDialogCallBack() {
            @Override
            public void onButtonClick() {
                FragmentManager fm = getFragmentManager();
                int count = fm.getBackStackEntryCount() - 2;
                for (int i = 0; i < count; ++i) {
                    fm.popBackStack();
                }
            }

            @Override
            public void onButton2Click() {
            }
        };
    }

}