package com.palup.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

/**
 * Created by nitinjaiman on 04/07/16.
 */
public class CustomImageView extends RelativeLayout {
    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.list_item_photo, this);

    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    ImageView imageView;
    ImageButton imageButton;


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageView = (ImageView) findViewById(R.id.imageView);
        imageButton = (ImageButton) findViewById(R.id.cross);

        imageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Log.e("imageview","removed");
                YoYo.with(new CustomAnimator()).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Log.e("A","started");

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        imageButton.setVisibility(INVISIBLE);
                        Log.e("A","ended");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Log.e("A","cancel");

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                        Log.e("A","repeat");

                    }
                })
                        .duration(700)
                        .playOn(imageButton);



             //   imageButton.setVisibility(INVISIBLE);

            }
        });




    }


    public void setImageView(int path){

        Bitmap icon = BitmapFactory.decodeResource(getResources(), path);

        imageView.setImageBitmap(icon);





    }








}
