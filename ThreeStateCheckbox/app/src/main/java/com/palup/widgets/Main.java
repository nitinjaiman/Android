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

        findViewById(R.id.threeStateCheckbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreeStateCheckbox.State state = ((ThreeStateCheckbox) v).getState();
                Log.i("Main ", state.toString());
            }
        });

    }
}
