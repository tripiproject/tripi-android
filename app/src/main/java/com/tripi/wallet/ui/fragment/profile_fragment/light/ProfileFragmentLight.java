package com.tripi.wallet.ui.fragment.profile_fragment.light;

import com.tripi.wallet.R;
import com.tripi.wallet.ui.fragment.profile_fragment.DividerItemDecoration;
import com.tripi.wallet.ui.fragment.profile_fragment.ProfileFragment;

public class ProfileFragmentLight extends ProfileFragment {

    @Override
    protected int getLayout() {
        return R.layout.lyt_profile_preference_light;
    }

    @Override
    public void initializeViews() {
        super.initializeViews();
        showBottomNavView(R.color.accent_red_color);
        adapter = new PrefAdapterLight(getPresenter().getSettingsData(), this, R.layout.lyt_profile_pref_list_item_light);
        dividerItemDecoration = new DividerItemDecoration(getContext(), R.drawable.color_primary_divider_light, R.drawable.section_setting_divider_light, getPresenter().getSettingsData());
        prefList.addItemDecoration(dividerItemDecoration);
        prefList.setAdapter(adapter);
    }

}
