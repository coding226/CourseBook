package com.coursebook;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.utils.AppUtils;


public class CoursedetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llActionBar;
    private ImageButton ibMenu;
    private WebView webViewcourseview;
    public static int position = 0;
    TextView tv_title;
    Button btn_course_subscribe;

    String[] names = new String[]{"socialengineering", "malware", "cyberattack"};
    private static String TAG = CoursedetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursedetails);
        findViews();

        String id = getIntent().getStringExtra("position");

        Bundle extras = getIntent().getExtras();
        String userName;

        if (extras != null) {
            userName = extras.getString("position");
            // and get whatever type user account id is
            Log.e(TAG, "position: " + userName);

        }

        if (AppUtils.subscriptions.contains(names[position])) {
            btn_course_subscribe.setText("UnSubscribe");
        }

        Log.e(TAG, "position: " + id);
        WebSettings webSettings = webViewcourseview.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webViewcourseview.loadUrl("file:///android_asset/" + names[position] + ".html");

        tv_title.setText("" + names[position]);

        btn_course_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtils.subscriptions.contains(names[position])) {
                    AppUtils.subscriptions.remove(names[position]);
                }else {
                    AppUtils.subscriptions.add(names[position]);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == ibMenu) {
            onBackPressed();
        }
    }

    private void findViews() {
        llActionBar = (LinearLayout) findViewById(R.id.llActionBar);
        ibMenu = (ImageButton) findViewById(R.id.ibMenu);
        webViewcourseview = (WebView) findViewById(R.id.webViewcourseview);
        tv_title = (TextView) findViewById(R.id.tv_title);
        btn_course_subscribe = (Button) findViewById(R.id.btn_course_subscribe);

        ibMenu.setOnClickListener(this);
        btn_course_subscribe.setOnClickListener(this);
    }
}