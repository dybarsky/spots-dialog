package dmax.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 13.01.15 at 14:22
 */
public class DotsDialog  extends AlertDialog {

    private static final int COUNT = 5;
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

    public DotsDialog(Context context) {
        super(context);
    }

    public DotsDialog(Context context, int theme) {
        super(context, theme);
    }

    public DotsDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog);
        setCanceledOnTouchOutside(false);

        ((TextView) findViewById(R.id.title)).setText(R.string.importing);

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

        spots = new AnimatedView[COUNT];
        int size = getContext().getResources().getDimensionPixelSize(R.dimen.spot_size);
        int progressWidth = getContext().getResources().getDimensionPixelSize(R.dimen.progress_width);
        for (int i = 0; i < COUNT; i++) {
            AnimatedView v = new AnimatedView(getContext());
            v.setBackgroundResource(R.drawable.spot);
            v.setTarget(progressWidth);
            v.setXFactor(-1f);
            progress.addView(v, size, size);
            spots[i] = v;
        }
    }

    private Animator[] createAnimations() {
        Animator[] animators = new Animator[COUNT];
        for (int i = 0; i < COUNT; i++) {
            Animator move = ObjectAnimator.ofFloat(spots[i], "xFactor", 0, 1);
            move.setDuration(DURATION);
            move.setInterpolator(new HesitateInterpolator());
            move.setStartDelay(DELAY * i);
            animators[i] = move;
        }
        return animators;
    }
}
