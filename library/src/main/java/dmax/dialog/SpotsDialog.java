package dmax.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 13.01.15 at 14:22
 */
public class SpotsDialog extends AlertDialog {

    private static final int DEFAULT_COUNT = 6;
    private static final int DELAY = 200;
    private static final int DURATION = 1500;

    private AnimatedView[] spots;
    private Animator[] animators;

    private AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            animate();
        }
    };

    public SpotsDialog(Context context) {
        this(context, R.style.Default);
    }

    public SpotsDialog(Context context, int theme) {
        super(context, theme);
    }

    public SpotsDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog);
        setCanceledOnTouchOutside(false);

        initProgress();
    }

    @Override
    protected void onStart() {
        super.onStart();

        animate();
    }

    //~

    private void animate() {
        if (animators == null) animators = createAnimations();
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        set.addListener(listener);
        set.start();
    }

    private void initProgress() {
        FrameLayout progress = (FrameLayout) findViewById(R.id.progress);

        spots = new AnimatedView[DEFAULT_COUNT];
        int size = getContext().getResources().getDimensionPixelSize(R.dimen.spot_size);
        int progressWidth = getContext().getResources().getDimensionPixelSize(R.dimen.progress_width);
        for (int i = 0; i < DEFAULT_COUNT; i++) {
            AnimatedView v = new AnimatedView(getContext());
            v.setBackgroundResource(R.drawable.spot);
            v.setTarget(progressWidth);
            v.setXFactor(-1f);
            progress.addView(v, size, size);
            spots[i] = v;
        }
    }

    private Animator[] createAnimations() {
        Animator[] animators = new Animator[DEFAULT_COUNT];
        for (int i = 0; i < DEFAULT_COUNT; i++) {
            Animator move = ObjectAnimator.ofFloat(spots[i], "xFactor", 0, 1);
            move.setDuration(DURATION);
            move.setInterpolator(new HesitateInterpolator());
            move.setStartDelay(DELAY * i);
            animators[i] = move;
        }
        return animators;
    }
}
