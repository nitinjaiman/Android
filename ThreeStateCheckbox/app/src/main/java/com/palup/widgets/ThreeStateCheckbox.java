package com.palup.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * <h1>Three State Checkbox</h1>
 *
 * Conventionally, Checkboxes are two mState widgets where checked mState means true and unchecked
 * mState means false. ThreeStateCheckbox is extension of Checkbox. It have an additional mState i.e.
 * partial checked.
 *
 * Created by kuldeep on 11/06/16.
 */
public class ThreeStateCheckbox extends View {

    /**
     * Paint object to draw
     */
    private Paint mPaint;

    /**
     * Current state of checkbox
     */
    private State mState;

    /**
     * Bitmap resource for unchecked image
     */
    private Bitmap uncheckedBitmap;

    /**
     * Bitmap resource for partially checked image
     */
    private Bitmap partialBitmap;

    /**
     * Bitmap resource for checked image
     */
    private Bitmap checkedBitmap;

    /**
     * Click listener
     */
    private OnClickListener onClickListener;

    public ThreeStateCheckbox(Context context) {
        super(context);
    }

    public ThreeStateCheckbox(Context context, AttributeSet attrs) throws State.BadStateCodeException {
        super(context, attrs);
        init(context, attrs);
    }

    public ThreeStateCheckbox(Context context, AttributeSet attrs, int defStyleAttr) throws State.BadStateCodeException {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ThreeStateCheckbox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) throws State.BadStateCodeException {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * Initialize fields.
     *
     * @param context
     * @param attrs
     * @throws State.BadStateCodeException
     */
    private void init(Context context, AttributeSet attrs) throws State.BadStateCodeException {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ThreeStateCheckbox,
                0, 0);

        // By default state of checkbox is Unchecked(0)
        int stateCode = a.getInt(R.styleable.ThreeStateCheckbox_state, 0);
        this.mState = State.toEnum(stateCode);
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        uncheckedBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.unchecked);
        partialBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.partial);
        checkedBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.checked);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * @return checkbox's current state.
     */
    public State getState() {
        return mState;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            boolean touchEventResult =  super.onTouchEvent(event);

            if(this.mState == State.UNCHECKED) {
                this.mState = State.PARTIAL;
            } else if (this.mState == State.PARTIAL) {
                this.mState = State.CHECKED;
            } else if(mState == State.CHECKED){
                this.mState = State.UNCHECKED;
            }

            invalidate();
            requestLayout();
            if (this.onClickListener != null){
                this.onClickListener.onClick(this);
            }
            return touchEventResult;
        }

        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mState == State.UNCHECKED) {
            canvas.drawBitmap(uncheckedBitmap, 0, 0, mPaint);
        } else if (mState == State.PARTIAL) {
            canvas.drawBitmap(partialBitmap, 0, 0, mPaint);
        } else if (mState == State.CHECKED) {
            canvas.drawBitmap(checkedBitmap, 0, 0, mPaint);
        } else {
            throw new RuntimeException("bad state code");
        }
    }

    /**
     * States of ThreeStateCheckbox
     */
    public enum State {

        UNCHECKED,  // Unchecked(0) mState of checkbox
        PARTIAL,    // Partially(1) checked mState
        CHECKED;    // Checked(2) mState

        /**
         * Convert state code to state.
         *
         * @param stateCode the state code
         * @return State corresponding to the state code
         * @throws BadStateCodeException if state code is not in set {0,1,2}
         */
        public static State toEnum(int stateCode) throws BadStateCodeException {
            if (stateCode == 0) {
                return State.UNCHECKED;
            } else if (stateCode == 1) {
                return State.PARTIAL;
            } else if (stateCode == 2) {
                return State.CHECKED;
            } else {
                throw new BadStateCodeException("Invalid mState code");
            }
        }

        /**
         * Bad state code exception.
         */
        public static class BadStateCodeException extends Exception {

            public BadStateCodeException(String detailMessage) {
                super(detailMessage);
            }
        }
    }

}
