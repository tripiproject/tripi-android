package com.tripi.wallet.ui.fragment.about_fragment;

import com.tripi.wallet.model.Version;
import com.tripi.wallet.ui.base.base_fragment.BaseFragmentView;

public interface AboutView extends BaseFragmentView {
    void updateVersion(Version version);
}
