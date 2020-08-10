package com.example.anujmore.bmi;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.view.Window;
import android.view.WindowManager;
public class SplashActivity extends AppCompatActivity {
    ImageView ivbmi;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivbmi= findViewById(R.id.ivbmi);
        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.a1);
        ivbmi.startAnimation(animation);

        Window window = SplashActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(SplashActivity.this, R.color.colorPrimary));

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }).start();

    }
}
