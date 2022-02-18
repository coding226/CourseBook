package com.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coursebook.CourseTopicListActivity;
import com.coursebook.R;
import com.google.gson.Gson;
import com.quiz.GamePlayActivity;
import com.quiz.QuestionsListResponsePOJO;
import com.quiz.QuizResponse;
import com.quiz.ResultsItem;
import com.utils.PrefrenceManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    String[] CourseNames;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        findViews(rootView);

        CourseNames = getResources().getStringArray(R.array.courseNames);

        // set up the RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.rvCourses);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        TrendingAppsAdapter adapter = new TrendingAppsAdapter(getActivity(), Arrays.asList(CourseNames.clone()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onClick(View v) {
    }


    private void findViews(View rootView) {
    }


    public class TrendingAppsAdapter extends RecyclerView.Adapter<TrendingAppsAdapter.AppsHolder> {
        private List<String> names;
        int[] courseImages = {R.drawable.socialengicon, R.drawable.malwareicons, R.drawable.cyberattackicon, R.drawable.mitigationicon};

        Activity activity;

        public class AppsHolder extends RecyclerView.ViewHolder {
            CardView itemView;
            ImageView logo;
            TextView title;
            ImageView iv_topic_selection;

            AppsHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.name);
                logo = (ImageView) view.findViewById(R.id.icon);
                iv_topic_selection = (ImageView) view.findViewById(R.id.iv_topic_selection);
                itemView = (CardView) view.findViewById(R.id.itemView);
            }
        }

        public TrendingAppsAdapter(Activity act, List<String> list) {
            activity = act;
            names = list;
        }

        public AppsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new AppsHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_appitem, viewGroup, false));
        }

        public void onBindViewHolder(AppsHolder appsHolder, @SuppressLint("RecyclerView") int i) {
            appsHolder.title.setText("" + Html.fromHtml(names.get(i)));
            appsHolder.title.setSelected(true);
            appsHolder.logo.getLayoutParams().width = (int) (getWidth(activity) * (0.20));
            appsHolder.logo.getLayoutParams().height = (int) (getWidth(activity) * (0.20));
            appsHolder.logo.setImageResource(courseImages[i]);

            Log.e("TAG", "onBindViewHolder: " + PrefrenceManager.getToken(getActivity()));
            if (PrefrenceManager.getToken(getActivity()).contains(String.valueOf(i))) {
                appsHolder.iv_topic_selection.setVisibility(View.VISIBLE);
            }

            appsHolder.logo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int position = i;
                    if (position < 3) {
                        CourseTopicListActivity.position = position;

                        if (!PrefrenceManager.getToken(getActivity()).contains(String.valueOf(position)))
                            PrefrenceManager.setToken(getActivity(), PrefrenceManager.getToken(getActivity()) + position);

                        Intent intentHomefragment = new Intent(getActivity(), CourseTopicListActivity.class);
                        intentHomefragment.putExtra("position", position);
                        startActivity(intentHomefragment);
                    } else {
                        String Jsonfile = getQuestions();
                        Gson gson = new Gson();
                        QuizResponse quizResponse = gson.fromJson(Jsonfile, QuizResponse.class);

                        QuestionsListResponsePOJO questionsListResponsePOJO = new QuestionsListResponsePOJO();
                        List<ResultsItem> datas = new ArrayList<>();
                        for (int j = 0; j < quizResponse.getData().get(0).getDetails().size(); j++) {
                            ResultsItem resultsItem = new ResultsItem();
                            QuizResponse.Detail detail = quizResponse.getData().get(0).getDetails().get(j);
                            resultsItem.setCorrectAnswer(detail.getAnswer());
                            resultsItem.setQuestion(detail.getQuestion());
                            resultsItem.setOptions(detail.getOptions());
                            datas.add(resultsItem);
                        }
                        questionsListResponsePOJO.setResults(datas);

                        Log.e("TAG", "onClick: " + datas.get(0).getOptions().size());
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "" + quizResponse.getData().get(0).getTitle());
                        bundle.putString("totalQuestions", String.valueOf(datas.size()));
                        bundle.putSerializable("questionsListResponsePOJO", questionsListResponsePOJO);
                        Intent intent = new Intent(getActivity(), GamePlayActivity.class);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                    }
                }
            });
        }

        public int getItemCount() {
            return names.size();
        }

        public int getWidth(Activity activity) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowmanager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
            int deviceWidth = displayMetrics.widthPixels;

            return deviceWidth;
        }
    }


    private String getQuestions() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("quizquestions.json");
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