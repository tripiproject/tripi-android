package com.tripi.wallet.ui.fragment.tripi_cash_management_fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;

import com.tripi.wallet.R;
import com.tripi.wallet.model.AddressWithBalance;
import com.tripi.wallet.model.gson.UnspentOutput;
import com.tripi.wallet.utils.FontTextView;

import java.math.BigDecimal;
import java.util.List;

public abstract class AddressesWithBalanceSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context mContext;
    private List<AddressWithBalance> mKeyWithBalanceList;

    public AddressesWithBalanceSpinnerAdapter(@NonNull Context context, List<AddressWithBalance> keyWithBalanceList) {
        mContext = context;
        mKeyWithBalanceList = keyWithBalanceList;
    }

    @Override
    public int getCount() {
        return mKeyWithBalanceList.size();
    }

    @Override
    public Object getItem(int i) {
        return mKeyWithBalanceList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    public View getCustomView(int position, int resId, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resId, parent, false);
        FontTextView textViewAddress = (FontTextView) view.findViewById(R.id.address_name);

        final FontTextView textViewBalance = (FontTextView) view.findViewById(R.id.address_balance);
        final FontTextView textViewSymbol = (FontTextView) view.findViewById(R.id.address_symbol);

        BigDecimal balance = new BigDecimal("0");
        BigDecimal amount;
        for (UnspentOutput unspentOutput : mKeyWithBalanceList.get(position).getUnspentOutputList()) {
            amount = new BigDecimal(String.valueOf(unspentOutput.getAmount()));
            balance = balance.add(amount);
        }
        textViewSymbol.setText(" TRIPI");
        textViewBalance.setLongNumberText(balance.toString(), textViewBalance.getContext().getResources().getDisplayMetrics().widthPixels / 2);
        textViewAddress.setText(mKeyWithBalanceList.get(position).getAddress());
        return view;
    }


}
