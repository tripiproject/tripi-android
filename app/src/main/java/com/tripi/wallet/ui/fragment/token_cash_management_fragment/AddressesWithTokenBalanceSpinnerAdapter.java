package com.tripi.wallet.ui.fragment.token_cash_management_fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;

import com.tripi.wallet.R;
import com.tripi.wallet.model.AddressWithTokenBalance;
import com.tripi.wallet.utils.FontTextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public abstract class AddressesWithTokenBalanceSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context mContext;
    private List<AddressWithTokenBalance> mKeyWithBalanceList;
    String currency;
    int decimalUnits;

    public AddressesWithTokenBalanceSpinnerAdapter(@NonNull Context context, List<AddressWithTokenBalance> keyWithBalanceList, String currency, int decimalUnits) {
        mContext = context;
        mKeyWithBalanceList = keyWithBalanceList;
        this.currency = currency;
        this.decimalUnits = decimalUnits;
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

    public View getCustomView(int position, @Nullable int resId, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resId, parent, false);
        FontTextView textViewAddress = (FontTextView) view.findViewById(R.id.address_name);
        final FontTextView textViewBalance = (FontTextView) view.findViewById(R.id.address_balance);
        final FontTextView textViewSymbol = (FontTextView) view.findViewById(R.id.address_symbol);
        textViewSymbol.setText(String.format(" %s", currency));

        String balance = (mKeyWithBalanceList.get(position).getBalance() != null
                && !mKeyWithBalanceList.get(position).getBalance().toString().equals("0"))
                ? String.valueOf(mKeyWithBalanceList.get(position).getBalance().divide(new BigDecimal(Math.pow(10, decimalUnits)), MathContext.DECIMAL128))
                : "0";

        textViewBalance.setLongNumberText(balance, textViewBalance.getContext().getResources().getDisplayMetrics().widthPixels / 2);
        textViewAddress.setText(mKeyWithBalanceList.get(position).getAddress());
        return view;
    }
}
