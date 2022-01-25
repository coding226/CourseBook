package com.coursebook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.pojo.JResponsePOJO;
import com.utils.AppUtils;

import java.io.IOException;
import java.io.InputStream;

public class CourseTopicListActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llActionBar;
    private ImageButton ibMenu;
    public static int position = 0;
    TextView tv_title;
    Button btn_course_subscribe;
    LinearLayout ll_cours_topics_list;

    String[] names = new String[]{"socialengineering", "malware", "cyberattack"};
    private static String TAG = CourseTopicListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_topiclist);
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

        String Jsonfile = getJSONFile();
        JResponsePOJO jResponsePOJO = new JResponsePOJO();
        Gson gson = new Gson();
        jResponsePOJO = gson.fromJson(Jsonfile, JResponsePOJO.class);

        ll_cours_topics_list.removeAllViews();
        for (int i = 0; i < jResponsePOJO.getData().get(position).getDetails().size(); i++) {
            JResponsePOJO.DataItem.DetailsItem detailsItem = jResponsePOJO.getData().get(position).getDetails().get(i);
            View view = getLayoutInflater().inflate(R.layout.row_appitem, null, false);
            LinearLayout img_nm = (LinearLayout) view.findViewById(R.id.img_nm);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            TextView name = (TextView) view.findViewById(R.id.name);

            name.setText("" + detailsItem.getTopic());

            name.setTextSize(20);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CourseTopicListActivity.this, TopicDetailsActivity.class);
                    intent.putExtra("topictitle" , detailsItem.getTopic());
                    intent.putExtra("topicdetails" , detailsItem.getDesc());

                    startActivity(intent);
                }
            });

            ll_cours_topics_list.addView(view);

        }
        tv_title.setText("" + names[position]);

        btn_course_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtils.subscriptions.contains(names[position])) {
                    AppUtils.subscriptions.remove(names[position]);
                } else {
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
        tv_title = (TextView) findViewById(R.id.tv_title);
        btn_course_subscribe = (Button) findViewById(R.id.btn_course_subscribe);
        ll_cours_topics_list = findViewById(R.id.ll_cours_topics_list);
        ibMenu.setOnClickListener(this);
        btn_course_subscribe.setOnClickListener(this);
    }

    private String getJSONFile() {
        String json = null;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}