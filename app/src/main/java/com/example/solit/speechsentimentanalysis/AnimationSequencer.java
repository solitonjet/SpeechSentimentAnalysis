package com.example.solit.speechsentimentanalysis;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Handler;

import java.util.List;

/**
 * Created by solit on 2018/01/12.
 */

public class AnimationSequencer<T> {

    public static abstract class AnimationSequencerCallback {
        public void onAnimationEnd() {}
    }

    public class AnimationSequenceParams {
        int type;
        float[] values;
        int time;
    }

    public static final int ANIM_ALPHA      = 100;
    public static final int ANIM_ROTATION   = 101;
    public static final int ANIM_SCALE      = 102;
    public static final int ANIM_TRANSLATE  = 103;

    public AnimationSequencer(T view, AnimationSequencerCallback callback) {
        mView = view;
        mCallback = callback;
        mObjectAnimator = new ObjectAnimator();
    }

    public void doAnimate (List<AnimationSequenceParams> sequence) {
        setSequenseList(sequence);

        mAnimSequenceHandler.post(mAnimSequencer);
    }

    /**
     * Private definitions
     */
    private final AnimationSequencerCallback mCallback;
    private ObjectAnimator mObjectAnimator;
    private List<AnimationSequenceParams> mSequenseList;

    private int iSeqId;
    private T mView;

    /**
     * Animation Sequence Handler
     */
    private Handler mAnimSequenceHandler = new Handler();
    private Runnable mAnimSequencer = new Runnable() {
        @Override
        public void run() {
            if (null == getNextSequenceParam()) {
                // finished
                mCallback.onAnimationEnd();
                return;
            }

            float[] values = getNextSequenceParam().values;
            int time = getNextSequenceParam().time;

            switch( getNextSequenceParam().type) {
                case ANIM_ALPHA:
                    animateAlpha(mView, values, time);
                    break;
                case ANIM_ROTATION:
                    animateRotation(mView, values, time);
                    break;
                case ANIM_SCALE:
                    animateScale(mView, values, time);
                    break;
                case ANIM_TRANSLATE:
                    animateTranslation(mView, values, time);
                    break;
            }
        }
    };

    private void setSequenseList(List<AnimationSequenceParams> sequenseList) {
        mSequenseList = sequenseList;
    }

    private List<AnimationSequenceParams> getSequenseList() {
        return mSequenseList;
    }

    private int getSequenceId() {
        return iSeqId;
    }

    private void inclementSequenceId() {
        iSeqId++;
    }

    private AnimationSequenceParams getNextSequenceParam() {
        if (getSequenseList().size() > getSequenceId()) {
            return getSequenseList().get(getSequenceId());
        }

        return null;
    }

    private AnimatorListenerAdapter mAnimAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            inclementSequenceId();
            mAnimSequenceHandler.post(mAnimSequencer);
        }
    };

    private void animateAlpha( T target, float[] values, int time ) {

        mObjectAnimator = ObjectAnimator.ofFloat( target, "alpha", 0f, 1f );
        mObjectAnimator.setDuration( time );
        mObjectAnimator.addListener(mAnimAdapter);

        mObjectAnimator.start();
    }

    private void animateRotation( T target, float[] values, int time ) {

        float start = values[0];
        float end = values[1];

        mObjectAnimator = ObjectAnimator.ofFloat( target, "rotation", start, end );
        mObjectAnimator.setDuration( time );
        mObjectAnimator.addListener(mAnimAdapter);

        mObjectAnimator.start();
    }

    private void animateScale( T target, float[] values, int time ) {

        float fromX = values[0];
        float toX = values[1];
        float fromY = values[2];
        float toY = values[3];

        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat( "scaleX", fromX, toX );
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat( "scaleY", fromY, toY );

        mObjectAnimator = ObjectAnimator.ofPropertyValuesHolder( target, holderX, holderY );
        mObjectAnimator.setDuration( time );
        mObjectAnimator.addListener(mAnimAdapter);

        mObjectAnimator.start();

    }

    private void animateTranslation( T target, float[] values, int time ) {

        float fromX = values[0];
        float toX = values[1];
        float fromY = values[2];
        float toY = values[3];

        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat( "translationX", fromX, toX );
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat( "translationY", fromY, toY );

        mObjectAnimator = ObjectAnimator.ofPropertyValuesHolder( target, holderX, holderY );
        mObjectAnimator.setDuration( time );
        mObjectAnimator.addListener(mAnimAdapter);

        mObjectAnimator.start();

    }
}
