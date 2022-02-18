package com.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coursebook.BaseActivity;
import com.coursebook.R;

import java.util.Collections;
import java.util.List;

public class GamePlayActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llActionBar;
    private ImageButton ibMenu;
    private TextView tv_playgame_questionnumber;
    private TextView tv_playgame_timing;
    private TextView tv_playgame_questiontitle;
    private Button btn_playgame_option1;
    private Button btn_playgame_option2;
    private Button btn_playgame_option3;
    private Button btn_playgame_option4;
    private Button btn_playgame_option5;
    private Button btn_playgame_finish;


    QuestionsListResponsePOJO questionsListResponsePOJO;
    String totalQuestions;
    int questionNumber = 1;
    CountDownTimer countDownTimer;
    int timeValue = 20;
    int correctAnswer = 0;
    TriviaQuizHelper triviaQuizHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        findViews();

        triviaQuizHelper = new TriviaQuizHelper(this);
        triviaQuizHelper.getWritableDatabase();

        ImageButton ibMenu = findViewById(R.id.ibMenu);
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            questionsListResponsePOJO = (QuestionsListResponsePOJO) bundle.getSerializable("questionsListResponsePOJO");
            totalQuestions = bundle.getString("totalQuestions");
        }
        setQestion();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_playgame_option1) {
            checkAnswer(btn_playgame_option1);

        } else if (v == btn_playgame_option2) {
            checkAnswer(btn_playgame_option2);

        } else if (v == btn_playgame_option3) {
            checkAnswer(btn_playgame_option3);

        } else if (v == btn_playgame_option4) {
            checkAnswer(btn_playgame_option4);

        } else if (v == btn_playgame_option5) {
            checkAnswer(btn_playgame_option5);

        } else if (v == btn_playgame_finish) {
            if (questionNumber < Integer.parseInt(totalQuestions)) {
                questionNumber = questionNumber + 1;
                setQestion();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("totalQuestions", totalQuestions);
                bundle.putString("correctAnswer", String.valueOf(correctAnswer));

                TriviaQuestionModel triviaQuestionModel = new TriviaQuestionModel();
                triviaQuestionModel.setTotalQuestions("" + totalQuestions);
                triviaQuestionModel.setCorrectAnswers("" + correctAnswer);
                triviaQuizHelper.insertUserQuiz(triviaQuestionModel);


                Log.e("TAG", "onClick:totalQuestions " + totalQuestions);
                Log.e("TAG", "onClick:correctAnswer " + correctAnswer);

                Intent intent = new Intent(GamePlayActivity.this, ResultScreenActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                GamePlayActivity.this.finish();
            }
        }
    }

    private void setQestion() {
        List<String> optionss = questionsListResponsePOJO.getResults().get(questionNumber - 1).getOptions();
        //qust.add(questionsListResponsePOJO.getResults().get(questionNumber - 1).getCorrectAnswer());
        //Collections.shuffle(qust);
        Log.e("TAG", "setQestion: " + optionss);

        tv_playgame_questiontitle.setText("" + questionsListResponsePOJO.getResults().get(questionNumber - 1).getQuestion().toString());
        tv_playgame_questionnumber.setText("" + (questionNumber) + "/" + totalQuestions);

        //iv_playgame_image.setImageResource(questionsListResponsePOJO.getResults().get(questionNumber - 1).getImage());

        btn_playgame_option1.setText(optionss.get(0));
        btn_playgame_option2.setText(optionss.get(1));
        btn_playgame_option3.setText(optionss.get(2));
        btn_playgame_option4.setText(optionss.get(3));

        btn_playgame_option3.setVisibility(View.VISIBLE);
        btn_playgame_option4.setVisibility(View.VISIBLE);


        if (optionss.size() == 5) {
            btn_playgame_option5.setVisibility(View.VISIBLE);
            btn_playgame_option5.setText(optionss.get(4));
        } else {
            btn_playgame_option5.setVisibility(View.GONE);
        }

        Log.e("TAG", "setQestion: " + optionss.size());
        if ((optionss.get(2) == null) || (optionss.get(2) != null && optionss.get(2).equals("")))
            btn_playgame_option3.setVisibility(View.GONE);

        if ((optionss.get(3) == null) || (optionss.get(3) != null && optionss.get(3).equals("")))
            btn_playgame_option4.setVisibility(View.GONE);

        if (optionss.size() > 4 && ((optionss.get(4) == null) || (optionss.get(4) != null && optionss.get(4).equals(""))))
            btn_playgame_option5.setVisibility(View.GONE);

        setertTimer();
        resetColor();
        enableButton();
    }

    private void checkAnswer(Button answercheck) {
        countDownTimer.cancel();
        disableButton();
        if (questionsListResponsePOJO.getResults().get(questionNumber - 1).getCorrectAnswer().equals(answercheck.getText().toString())) {
            answercheck.setBackgroundResource(R.drawable.bg_selector_option_true);
            correctAnswer = correctAnswer + 1;
        } else {
            answercheck.setBackgroundResource(R.drawable.bg_selector_option_false);

            if (questionsListResponsePOJO.getResults().get(questionNumber - 1).getCorrectAnswer().equals(btn_playgame_option1.getText().toString()))
                btn_playgame_option1.setBackgroundResource(R.drawable.bg_selector_option_true);
            else if (questionsListResponsePOJO.getResults().get(questionNumber - 1).getCorrectAnswer().equals(btn_playgame_option2.getText().toString()))
                btn_playgame_option2.setBackgroundResource(R.drawable.bg_selector_option_true);
            else if (questionsListResponsePOJO.getResults().get(questionNumber - 1).getCorrectAnswer().equals(btn_playgame_option3.getText().toString()))
                btn_playgame_option3.setBackgroundResource(R.drawable.bg_selector_option_true);
            else if (questionsListResponsePOJO.getResults().get(questionNumber - 1).getCorrectAnswer().equals(btn_playgame_option4.getText().toString()))
                btn_playgame_option4.setBackgroundResource(R.drawable.bg_selector_option_true);
            else if (questionsListResponsePOJO.getResults().get(questionNumber - 1).getCorrectAnswer().equals(btn_playgame_option5.getText().toString()))
                btn_playgame_option5.setBackgroundResource(R.drawable.bg_selector_option_true);
        }
    }

    private void setertTimer() {
        timeValue = 20;
        countDownTimer = new CountDownTimer(22000, 1000) {
            public void onTick(long millisUntilFinished) {
                tv_playgame_timing.setText(String.valueOf(timeValue) + "\"");
                timeValue -= 1;
                if (timeValue == -1) {
                    tv_playgame_timing.setText("Timer Up");
                    disableButton();
                }
            }

            public void onFinish() {
                tv_playgame_timing.setText("Timer Up");
                //questionNumber = questionNumber + 1;
                //setQestion();
                btn_playgame_finish.performClick();
            }
        }.start();
    }

    public void resetColor() {
        btn_playgame_option1.setBackgroundResource(R.drawable.bg_selector_option_default);
        btn_playgame_option2.setBackgroundResource(R.drawable.bg_selector_option_default);
        btn_playgame_option3.setBackgroundResource(R.drawable.bg_selector_option_default);
        btn_playgame_option4.setBackgroundResource(R.drawable.bg_selector_option_default);
        btn_playgame_option5.setBackgroundResource(R.drawable.bg_selector_option_default);
    }

    public void disableButton() {
        btn_playgame_option1.setEnabled(false);
        btn_playgame_option2.setEnabled(false);
        btn_playgame_option3.setEnabled(false);
        btn_playgame_option4.setEnabled(false);
        btn_playgame_option5.setEnabled(false);
    }

    public void enableButton() {
        btn_playgame_option1.setEnabled(true);
        btn_playgame_option2.setEnabled(true);
        btn_playgame_option3.setEnabled(true);
        btn_playgame_option4.setEnabled(true);
        btn_playgame_option5.setEnabled(true);
    }

    private void findViews() {
        llActionBar = (LinearLayout) findViewById(R.id.llActionBar);
        ibMenu = (ImageButton) findViewById(R.id.ibMenu);
        tv_playgame_questionnumber = (TextView) findViewById(R.id.tv_playgame_questionnumber);
        tv_playgame_timing = (TextView) findViewById(R.id.tv_playgame_timing);
        tv_playgame_questiontitle = (TextView) findViewById(R.id.tv_playgame_questiontitle);
        btn_playgame_option1 = (Button) findViewById(R.id.btn_playgame_option1);
        btn_playgame_option2 = (Button) findViewById(R.id.btn_playgame_option2);
        btn_playgame_option3 = (Button) findViewById(R.id.btn_playgame_option3);
        btn_playgame_option4 = (Button) findViewById(R.id.btn_playgame_option4);
        btn_playgame_option5 = (Button) findViewById(R.id.btn_playgame_option5);
        btn_playgame_finish = (Button) findViewById(R.id.btn_playgame_finish);

        ibMenu.setOnClickListener(this);
        btn_playgame_option1.setOnClickListener(this);
        btn_playgame_option2.setOnClickListener(this);
        btn_playgame_option3.setOnClickListener(this);
        btn_playgame_option4.setOnClickListener(this);
        btn_playgame_option5.setOnClickListener(this);
        btn_playgame_finish.setOnClickListener(this);
    }
}