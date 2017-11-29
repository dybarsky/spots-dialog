package dmax.dialog;

import android.animation.*;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 05.05.15 at 14:45
 */
class AnimatorPlayer extends AnimatorListenerAdapter {

    private boolean interrupted = false;
    private Animator[] animators;

    AnimatorPlayer(Animator[] animators) {
        this.animators = animators;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if (!interrupted) animate();
    }

    void play() {
        animate();
    }

    void stop() {
        interrupted = true;
    }

    private void animate() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        set.addListener(this);
        set.start();
    }
}
