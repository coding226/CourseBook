package com.coursebook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.utils.PrefrenceManager;

public class SplashActivity extends AppCompatActivity {
    TextView tv_login;
    private static String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_login = findViewById(R.id.tv_login);


        cheCheckUserExistace();
    }


    private void cheCheckUserExistace() {
        if (PrefrenceManager.getIsLogedin(SplashActivity.this)) {
            changeActivity(MainActivity.class);
        } else {
            tv_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeActivity(LoginActivity.class);
                }
            });
        }
    }

    private void changeActivity(Class classActivity) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, classActivity);
                startActivity(intent);
                finish();
            }
        }, 200);
    }

}
