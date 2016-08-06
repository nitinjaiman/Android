package com.palup.widgets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        CustomTextView customTextView= (CustomTextView) findViewById(R.id.customTextView);

           customTextView.setTextView("djkhfsgkdshbfhs sdhfbjkhsdhjfhs jlkadflkjsdahgj jasdhlfkjshdiugh jksdaflkjghsdjgh jhhggfhjfghfygfghfhgfhjgfhgfgfhgfhgf bvmhghjvghjfjhgfgfghfhg jghvjghfjghfghjfghfg jhkgkhjkhjghjghkjgjhgkhj");

    }
}
