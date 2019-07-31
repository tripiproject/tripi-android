package com.tripi.wallet.ui.fragment.token_cash_management_fragment.dark;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.tripi.wallet.R;

import com.tripi.wallet.model.AddressWithTokenBalance;
import com.tripi.wallet.ui.fragment.token_cash_management_fragment.AddressesListFragmentToken;
import com.tripi.wallet.ui.fragment.token_cash_management_fragment.TokenAddressesAdapter;
import com.tripi.wallet.utils.FontTextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class AddressesListFragmentTokenDark extends AddressesListFragmentToken {
    @Override
    protected int getLayout() {
        return com.tripi.wallet.R.layout.fragment_address_list;
    }

    @Override
    public void updateAddressList(List<AddressWithTokenBalance> deterministicKeyWithBalance, String currency) {
        if (mRecyclerView != null) {
            adapter = new TokenAddressesAdapter(deterministicKeyWithBalance, com.tripi.wallet.R.layout.item_address, this, currency, getPresenter().getDecimalUnits());
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(AddressWithTokenBalance item) {
        List<AddressWithTokenBalance> deterministicKeyWithBalances = new ArrayList<>(getPresenter().getKeysWithTokenBalance());
        deterministicKeyWithBalances.remove(item);
        showTransferDialogFragment(item, deterministicKeyWithBalances, getPresenter().getDecimalUnits());
    }

    protected void showTransferDialogFragment(final AddressWithTokenBalance keyWithBalanceTo, List<AddressWithTokenBalance> keyWithBalanceList, final int decimalUnits) {
        View view = LayoutInflater.from(getContext()).inflate(com.tripi.wallet.R.layout.dialog_transfer_balance_fragment, null);
        final TextInputEditText mEditTextAmount = (TextInputEditText) view.findViewById(com.tripi.wallet.R.id.et_amount);
        final Spinner spinner = (Spinner) view.findViewById(com.tripi.wallet.R.id.spinner_transfer);
        FontTextView mEditTextAddressTo = (FontTextView) view.findViewById(com.tripi.wallet.R.id.tv_address_to);
        final FontTextView tvBalanceFrom = (FontTextView) view.findViewById(R.id.balance_from_tv);

        mEditTextAddressTo.setText(keyWithBalanceTo.getAddress());
        AddressesWithTokenBalanceSpinnerAdapterDark spinnerAdapter = new AddressesWithTokenBalanceSpinnerAdapterDark(getContext(), keyWithBalanceList, getPresenter().getCurrency(), decimalUnits);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AddressWithTokenBalance item = (AddressWithTokenBalance) spinner.getItemAtPosition(i);
                getPresenter().setKeyWithTokenBalanceFrom(item);

                String balance = (item.getBalance() != null
                        && !item.getBalance().toString().equals("0"))
                        ? String.valueOf(item.getBalance().divide(new BigDecimal(Math.pow(10, decimalUnits)), MathContext.DECIMAL128))
                        : "0";
                tvBalanceFrom.setText(String.format("%s %s", balance, getPresenter().getCurrency()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        view.findViewById(com.tripi.wallet.R.id.bt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTransferDialog.dismiss();
            }
        });

        view.findViewById(com.tripi.wallet.R.id.bt_transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProgressDialog();
                getPresenter().transfer(keyWithBalanceTo, getPresenter().getKeyWithTokenBalanceFrom(), mEditTextAmount.getText().toString());
            }
        });

        mTransferDialog = new AlertDialog
                .Builder(getContext())
                .setView(view)
                .create();

        if (mTransferDialog.getWindow() != null) {
            mTransferDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        mTransferDialog.setCanceledOnTouchOutside(false);
        mTransferDialog.show();
    }
}
