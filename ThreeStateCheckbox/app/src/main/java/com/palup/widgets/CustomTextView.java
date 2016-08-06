package com.palup.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

/**
 * Created by nitinjaiman on 04/07/16.
 */
public class CustomTextView extends LinearLayout {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.text_view_custom, this);

    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    TextView mainTextView;
    TextView titleTextView;



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mainTextView= (TextView) findViewById(R.id.textView);
        titleTextView= (TextView) findViewById(R.id.textView2);
        titleTextView.setVisibility(GONE);
        final int[] lines = {mainTextView.getLineCount()};



        mainTextView.post(new Runnable() {
            @Override
            public void run() {
                 lines[0] = mainTextView.getLineCount();
                if(lines[0] >2){
                    titleTextView.setVisibility(VISIBLE);

                    mainTextView.setHeight((mainTextView.getHeight()/lines[0])*2);


                                }
                // Perform any actions you want based on the line count here.
            }
        });


        titleTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                mainTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            }
        });

    }



    void setTextView(String text){


        mainTextView.setText(text);

        int lines=  mainTextView.getLineCount();


        if(lines>2){
            titleTextView.setVisibility(VISIBLE);


        }

    }





}
