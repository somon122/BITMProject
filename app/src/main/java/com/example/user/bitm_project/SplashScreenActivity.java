package com.example.user.bitm_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashScreenActivity extends AppCompatActivity {

private ProgressBar progressBar;
private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);


        setTitle("Tour Mate ");

        progressBar = findViewById(R.id.progressBar_id);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doTheWork();
                startApp();
            }
        });
        thread.start();



    }

    private void startApp() {
        startActivity(new Intent(SplashScreenActivity.this,LogIn_Activity.class));
        finish();
    }

    private void doTheWork() {

        for (progress = 20; progress <= 100; progress = progress+20){
            try {
                Thread.sleep(1000);
                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
