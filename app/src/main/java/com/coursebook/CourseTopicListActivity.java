package com.coursebook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.pojo.JResponsePOJO;
import com.quiz.GamePlayActivity;
import com.quiz.QuestionsListResponsePOJO;
import com.quiz.QuizResponse;
import com.quiz.ResultsItem;
import com.utils.AppUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        Gson gson = new Gson();
        JResponsePOJO jResponsePOJO = gson.fromJson(Jsonfile, JResponsePOJO.class);

        ll_cours_topics_list.removeAllViews();
        for (int i = 0; i < jResponsePOJO.getData().get(position).getDetails().size(); i++) {
            JResponsePOJO.DataItem.DetailsItem detailsItem = jResponsePOJO.getData().get(position).getDetails().get(i);
            View view = getLayoutInflater().inflate(R.layout.row_appitem, null, false);
            LinearLayout img_nm = (LinearLayout) view.findViewById(R.id.img_nm);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            TextView name = (TextView) view.findViewById(R.id.name);
            ImageView iv_topic_selection = (ImageView) view.findViewById(R.id.iv_topic_selection);

            name.setText("" + detailsItem.getTopic());
            iv_topic_selection.setVisibility(View.VISIBLE);

            name.setTextSize(20);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (detailsItem.getTopic().equals("Quiz")) {
                        String Jsonfile = getQuestions();
                        Gson gson = new Gson();
                        QuizResponse quizResponse = gson.fromJson(Jsonfile, QuizResponse.class);

                        QuestionsListResponsePOJO questionsListResponsePOJO = new QuestionsListResponsePOJO();
                        List<ResultsItem> datas = new ArrayList<>();
                        for (int j = 0; j < quizResponse.getData().get(position).getDetails().size(); j++) {
                            ResultsItem resultsItem = new ResultsItem();
                            QuizResponse.Detail detail = quizResponse.getData().get(position).getDetails().get(j);
                            resultsItem.setCorrectAnswer(detail.getAnswer());
                            resultsItem.setQuestion(detail.getQuestion());
                            resultsItem.setOptions(detail.getOptions());
                            datas.add(resultsItem);
                        }
                        questionsListResponsePOJO.setResults(datas);

                        Log.e(TAG, "onClick: " + datas.get(0).getOptions().size());
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "" + quizResponse.getData().get(position).getTitle());
                        bundle.putString("totalQuestions", String.valueOf(datas.size()));
                        bundle.putSerializable("questionsListResponsePOJO", questionsListResponsePOJO);
                        Intent intent = new Intent(CourseTopicListActivity.this, GamePlayActivity.class);
                        intent.putExtras(bundle);
                        CourseTopicListActivity.this.startActivity(intent);
                    } else {
                        Intent intent = new Intent(CourseTopicListActivity.this, TopicDetailsActivity.class);
                        intent.putExtra("topictitle", detailsItem.getTopic());
                        intent.putExtra("topicdetails", detailsItem.getDesc());
                        startActivity(intent);
                    }
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

    private String getQuestions() {
        String json = null;
        try {
            InputStream is = getAssets().open("questions.json");
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