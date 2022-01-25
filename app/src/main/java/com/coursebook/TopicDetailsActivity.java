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

public class TopicDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton ibMenu;
    private WebView webViewcourseview;
    TextView tv_title;

    private static String TAG = TopicDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_details);
        findViews();

        String id = getIntent().getStringExtra("position");

        Bundle extras = getIntent().getExtras();
        String topictitle = "null";
        String topicdetails = "socialengineering";
        if (extras != null) {
            topictitle = extras.getString("topictitle");
            Log.e(TAG, "topictitle: " + topictitle);
            topicdetails = extras.getString("topicdetails");
            Log.e(TAG, "topicdetails: " + topicdetails);
        }

        Log.e(TAG, "position: " + id);
        WebSettings webSettings = webViewcourseview.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webViewcourseview.loadUrl("file:///android_asset/" + topicdetails + ".html");

        tv_title.setText("" + topictitle);
    }

    @Override
    public void onClick(View v) {
        if (v == ibMenu) {
            onBackPressed();
        }
    }

    private void findViews() {
        ibMenu = (ImageButton) findViewById(R.id.ibMenu);
        webViewcourseview = (WebView) findViewById(R.id.webViewcourseview);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ibMenu.setOnClickListener(this);
    }
}