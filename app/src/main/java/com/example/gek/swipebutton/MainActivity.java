package com.example.gek.swipebutton;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// https://habrahabr.ru/company/livetyping/blog/274135/
public class MainActivity extends AppCompatActivity {
    private boolean isVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible){
                    ObjectAnimator.ofFloat(button1, View.ALPHA, 1, 0).start();

                    isVisible = false;
                } else {
                    ObjectAnimator.ofFloat(button1, View.ALPHA, 0, 1).start();
                    isVisible = true;
                }
            }
        });
    }
}
