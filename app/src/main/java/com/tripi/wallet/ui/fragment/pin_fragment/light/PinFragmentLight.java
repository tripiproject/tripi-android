package com.tripi.wallet.ui.fragment.pin_fragment.light;

import com.tripi.wallet.R;

import com.tripi.wallet.ui.fragment.pin_fragment.PinFragment;
import com.tripi.wallet.ui.wave_visualizer.WaveHelper;
import com.tripi.wallet.ui.wave_visualizer.WaveView;

import butterknife.BindView;

public class PinFragmentLight extends PinFragment {

    @BindView(com.tripi.wallet.R.id.wave_view)
    WaveView waveView;
    private WaveHelper mWaveHelper;

    @Override
    protected int getLayout() {
        return com.tripi.wallet.R.layout.fragment_pin_light;
    }

    @Override
    public void initializeViews() {
        super.initializeViews();
        waveView.setShapeType(WaveView.ShapeType.SQUARE);
        mWaveHelper = new WaveHelper(waveView);

    }

    @Override
    public void onResume() {
        super.onResume();
        mWaveHelper.start();
    }

    @Override
    public void onPause() {
        mWaveHelper.cancel();
        super.onPause();
    }

    @Override
    public int getThemedStatusBarColor() {
        return R.color.accent_red_color;
    }
}
