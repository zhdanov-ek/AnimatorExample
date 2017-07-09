package com.example.gek.swipebutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;



public class ExpandingPanel extends RelativeLayout {
    private static final String TAG = "EXPAND";
    private RelativeLayout rlPanel;
    private ImageView ivArrow;
    private Button btnAction;
    private Drawable arrowLeft;
    private Drawable arrowRight;
    private boolean isPanelExpanded = true;
    private int expandedPanelWidth;

    public ExpandingPanel(Context context) {
        super(context);
        init(context, null, -1, -1);
    }

    public ExpandingPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1, -1);
    }

    public ExpandingPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, -1);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandingPanel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        arrowLeft = ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_left);
        arrowRight = ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_right);

        // create expanded panel
        rlPanel = new RelativeLayout(context);
        LayoutParams lpPanel = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lpPanel.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        rlPanel.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_panel));
        addView(rlPanel, lpPanel);

        final Button btnAction = new Button(context);
        this.btnAction = btnAction;
        btnAction.setText("Show");
        LayoutParams lpButton = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lpButton.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        rlPanel.addView(btnAction, lpButton);


        // create arrow
        final ImageView arrow = new ImageView(context);
        ivArrow = arrow;
        ivArrow.setImageDrawable(arrowLeft);
        ivArrow.setPadding(30, 10, 30, 10);
        LayoutParams lpArrow = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lpArrow.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        lpArrow.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        ivArrow.setOnClickListener(arrowClickListener);
        rlPanel.addView(ivArrow, lpArrow);

    }

    OnClickListener arrowClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isPanelExpanded){
                expandedPanelWidth = rlPanel.getWidth();
                final ValueAnimator widthAnimator = ValueAnimator.ofInt(
                        rlPanel.getWidth(),
                        ivArrow.getWidth());
                widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ViewGroup.LayoutParams params =  rlPanel.getLayoutParams();
                        params.width = (Integer) widthAnimator.getAnimatedValue();
                        if (params.width  < (btnAction.getWidth() + 50)){
                            btnAction.setVisibility(GONE);
                        }
                        rlPanel.setLayoutParams(params);
                    }
                });
                widthAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isPanelExpanded = false;
                        ivArrow.setImageDrawable(arrowRight);
                    }
                });
                widthAnimator.start();

            } else {
                final ValueAnimator widthAnimator = ValueAnimator.ofInt(
                        ivArrow.getWidth(),
                        expandedPanelWidth);
                widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ViewGroup.LayoutParams params =  rlPanel.getLayoutParams();
                        params.width = (Integer) widthAnimator.getAnimatedValue();
                        if (params.width > btnAction.getWidth() + 50){
                            btnAction.setVisibility(VISIBLE);
                        }
                        rlPanel.setLayoutParams(params);
                    }
                });
                widthAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isPanelExpanded = true;
                        ivArrow.setImageDrawable(arrowLeft);
                    }
                });
                widthAnimator.start();
            }
        }
    };

}
