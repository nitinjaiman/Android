package com.palup.widgets;

import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by nitinjaiman on 04/07/16.
 */
public class CustomAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        ViewGroup parent = (ViewGroup)target.getParent();
        float x = (target.getWidth() - target.getPaddingLeft() - target.getPaddingRight())/2
                + target.getPaddingLeft();
        float y = target.getHeight() - target.getPaddingBottom();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 1, 0),
                ObjectAnimator.ofFloat(target,"translationY",0,-target.getBottom()),
                ObjectAnimator.ofFloat(target, "rotation", 24,-24,6,-6,0),
                ObjectAnimator.ofFloat(target, "pivotX", x, x,x,x,x),
                ObjectAnimator.ofFloat(target, "pivotY", y, y,y,y,y)


        );
    }
}
