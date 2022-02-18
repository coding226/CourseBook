package com.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.coursebook.BaseActivity;
import com.coursebook.MainActivity;
import com.coursebook.R;


public class ResultScreenActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_result_rightanswers;
    private TextView tv_result_result_condition;
    private TextView tv_result_percentage;
    private TextView tv_result_passfail;

    private Button btn_result_playagain;
    String totalQuestions;
    String correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        findViews();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            totalQuestions = bundle.getString("totalQuestions");
            correctAnswer = bundle.getString("correctAnswer");

            //int total_prog = ((Integer.valueOf(correctAnswer) / Integer.valueOf(totalQuestions)) * 100);
            float total_prog = ((Float.valueOf(correctAnswer) / Float.valueOf(totalQuestions)) * 100);
            Log.e("TAG", "onCreate: " + total_prog);
            String percentage = String.format("%.2f", total_prog);
            tv_result_percentage.setText("" + percentage + "%");

            if (total_prog > 65)
                tv_result_passfail.setText("Pass");
            else
                tv_result_passfail.setText("Fail");

            if (Integer.valueOf(correctAnswer) >= 10)
                tv_result_result_condition.setText("WELL DONE");
            else
                tv_result_result_condition.setText("Its Okay let's try again");

            tv_result_rightanswers.setText("Total Questions: " + totalQuestions + "\n" + "Correct Answers: " + correctAnswer);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn_result_playagain) {
            Intent intent = new Intent(ResultScreenActivity.this, MainActivity.class);
            startActivity(intent);
            ResultScreenActivity.this.finish();
        }
    }

    private void findViews() {
        tv_result_rightanswers = (TextView) findViewById(R.id.tv_result_rightanswers);
        tv_result_result_condition = (TextView) findViewById(R.id.tv_result_result_condition);
        btn_result_playagain = (Button) findViewById(R.id.btn_result_playagain);
        tv_result_percentage = findViewById(R.id.tv_result_percentage);
        tv_result_passfail = findViewById(R.id.tv_result_passfail);

        btn_result_playagain.setOnClickListener(this);
        ImageButton ibMenu = findViewById(R.id.ibMenu);
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ResultScreenActivity.this, MainActivity.class);
        startActivity(intent);
        ResultScreenActivity.this.finish();
    }
}






