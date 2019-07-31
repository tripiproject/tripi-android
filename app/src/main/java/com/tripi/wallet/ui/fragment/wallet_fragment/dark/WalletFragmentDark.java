package com.tripi.wallet.ui.fragment.wallet_fragment.dark;

import android.support.design.widget.AppBarLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.tripi.wallet.R;
import com.tripi.wallet.model.gson.history.History;
import com.tripi.wallet.ui.fragment.wallet_fragment.WalletFragment;
import com.tripi.wallet.utils.ResizeWidthAnimation;
import java.util.ArrayList;
import butterknife.BindView;

public class WalletFragmentDark extends WalletFragment {

    float headerPAdding = 0;
    float prevPercents = 1;

    float noInternetViewHeight;

    @BindView(R.id.fade_divider)
    View fadeDivider;

    @BindView(R.id.page_indicator)
    public View pagerIndicator;

    @BindView(R.id.no_internet_title)
    View mNoInternetTitleTextView;

    @BindView(R.id.scroll_content)
    View scrollContent;

    @BindView(R.id.recycler_content)
    View recyclerContent;

    @Override
    protected int getLayout() {
        return R.layout.fragment_wallet;
    }

    final DisplayMetrics dm = new DisplayMetrics();

    boolean expanded = false;

    public void doDividerExpand() {
        if (!expanded) {
            expanded = true;
            fadeDivider.clearAnimation();
            fadeDivider.setVisibility(View.VISIBLE);
            ResizeWidthAnimation anim = new ResizeWidthAnimation(fadeDivider, getResources().getDisplayMetrics().widthPixels);
            anim.setDuration(300);
            anim.setFillEnabled(true);
            anim.setFillAfter(true);
            fadeDivider.startAnimation(anim);
        }
    }

    public void doDividerCollapse() {
        if (expanded) {
            fadeDivider.clearAnimation();
            fadeDivider.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams lp = fadeDivider.getLayoutParams();
            lp.width = 0;
            fadeDivider.setLayoutParams(lp);
            expanded = false;
        }
    }

    @Override
    public void initializeViews() {
        super.initializeViews();

        showBottomNavView(R.color.primary_text_color);

        headerPAdding = convertDpToPixel(16, getContext());

        uncomfirmedBalanceValue.setVisibility(View.GONE);
        uncomfirmedBalanceTitle.setVisibility(View.GONE);

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (mAppBarLayout != null) {

                    percents = (((getTotalRange() - Math.abs(verticalOffset)) * 1.0f) / getTotalRange());

                    balanceView.setAlpha((percents > 0.5f) ? percents : 1 - percents);

                    scrollContent.setY(noInternetViewHeight * percents - pxFromDp(1));

                    if (percents == 0) {
                        doDividerExpand();
                    } else {
                        doDividerCollapse();
                    }

                    final float textPercent = (percents >= .5f) ? percents : .5f;
                    final float textPercent3f = (percents >= .3f) ? percents : .3f;

                    if (uncomfirmedBalanceTitle.getVisibility() == View.VISIBLE) {
                        animateText(percents, balanceLayout, .5f);
                        balanceLayout.setX(balanceView.getWidth() - (balanceView.getWidth() / 2 * percents + (balanceLayout.getWidth() * textPercent) / 2) - balanceLayout.getWidth() * (1 - textPercent) - headerPAdding * (1 - percents));
                        balanceLayout.setY(balanceView.getHeight() / 2 - balanceTitle.getHeight() * percents - balanceLayout.getHeight() * percents - balanceLayout.getHeight() * (1 - percents));

                        animateText(percents, balanceTitle, .7f);
                        balanceTitle.setX(balanceView.getWidth() / 2 * percents - (balanceTitle.getWidth() * textPercent3f) / 2 + headerPAdding * (1 - percents));
                        balanceTitle.setY(balanceView.getHeight() / 2 - balanceTitle.getHeight() * percents - balanceTitle.getHeight() * (1 - percents));

                        animateText(percents, uncomfirmedBalanceValue, .5f);
                        uncomfirmedBalanceValue.setX(balanceView.getWidth() - (balanceView.getWidth() / 2 * percents + (uncomfirmedBalanceValue.getWidth() * textPercent) / 2) - uncomfirmedBalanceValue.getWidth() * (1 - textPercent) - headerPAdding * (1 - percents));

                        animateText(percents, uncomfirmedBalanceTitle, .7f);
                        uncomfirmedBalanceTitle.setY(balanceView.getHeight() / 2 + uncomfirmedBalanceValue.getHeight() * percents - (uncomfirmedBalanceTitle.getHeight() * percents * (1 - percents)));
                        uncomfirmedBalanceTitle.setX(balanceView.getWidth() / 2 * percents - (uncomfirmedBalanceTitle.getWidth() * textPercent3f) / 2 + headerPAdding * (1 - percents));

                    } else {
                        animateText(percents, balanceTitle, .7f);
                        balanceTitle.setX(balanceView.getWidth() / 2 * percents - (balanceTitle.getWidth() * textPercent3f) / 2 + headerPAdding * (1 - percents));
                        balanceTitle.setY(balanceView.getHeight() / 2 + balanceTitle.getHeight() / 2 * percents - balanceTitle.getHeight() / 2 * (1 - percents));

                        animateText(percents, balanceLayout, .5f);
                        balanceLayout.setX(balanceView.getWidth() - (balanceView.getWidth() / 2 * percents + (balanceLayout.getWidth() * textPercent) / 2) - balanceLayout.getWidth() * (1 - textPercent) - headerPAdding * (1 - percents));
                        balanceLayout.setY(balanceView.getHeight() / 2 - balanceLayout.getHeight() * percents - balanceLayout.getHeight() / 2 * (1 - percents));
                    }
                    prevPercents = percents;
                }
            }
        });

    }

    public int getTotalRange() {
        return mAppBarLayout.getTotalScrollRange();
    }

    protected void animateText(float percents, View view, float fringe) {
        if (percents > fringe) {
            view.setScaleX(percents);
            view.setScaleY(percents);
        } else {
            view.setScaleX(fringe);
            view.setScaleY(fringe);
        }
    }


    @Override
    public void updateBalance(String balance, String unconfirmedBalance) {
        try {
            balanceValue.setText(String.format("%s TRIPI", balance));
            if (unconfirmedBalance != null) {
                uncomfirmedBalanceValue.setVisibility(View.VISIBLE);
                uncomfirmedBalanceTitle.setVisibility(View.VISIBLE);
                uncomfirmedBalanceValue.setText(String.format("%s TRIPI", unconfirmedBalance));
            } else {
                uncomfirmedBalanceValue.setVisibility(View.GONE);
                uncomfirmedBalanceTitle.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            Log.d("WalletFragmentDark", "updateBalance: " + e.getMessage());
        }
    }

    @Override
    protected void createAdapter() {
        mTransactionAdapter = new TransactionAdapterDark(new ArrayList<History>(), getAdapterListener());
        mRecyclerView.setAdapter(mTransactionAdapter);
//
//        ViewGroup.LayoutParams layoutParams = recyclerContent.getLayoutParams();
//        layoutParams.height = layoutParams.height + pxFromDp(72);
//        recyclerContent.setLayoutParams(layoutParams);
//        recyclerContent.requestLayout();
//
//        mRecyclerView.requestLayout();
    }


    @Override
    public void offlineModeView() {
        noInternetViewHeight = pxFromDp(72);
        super.offlineModeView();
        resizeNoInetConnection();
    }

    @Override
    public void onlineModeView() {
        noInternetViewHeight = pxFromDp(18);
        super.onlineModeView();
        resizeNoInetConnection();
    }

    private void resizeNoInetConnection() {
        mLinearLayoutNoInternetConnection.getLayoutParams().height = (int) (noInternetViewHeight * percents);
        mLinearLayoutNoInternetConnection.requestLayout();
    }

    public int pxFromDp(final float dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

}
