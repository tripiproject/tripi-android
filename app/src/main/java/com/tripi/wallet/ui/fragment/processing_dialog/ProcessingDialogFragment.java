package com.tripi.wallet.ui.fragment.processing_dialog;

import android.support.v4.app.DialogFragment;
import android.widget.RelativeLayout;

import butterknife.BindView;

public abstract class ProcessingDialogFragment extends DialogFragment {
    @BindView(com.tripi.wallet.R.id.root_layout)
    protected
    RelativeLayout mRootLayout;
}
