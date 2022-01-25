package com.fragments;

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

import com.coursebook.CoursedetailsActivity;
import com.coursebook.R;
import com.utils.AppUtils;

import java.util.Arrays;
import java.util.List;

public class MyCoursesFragment extends Fragment implements View.OnClickListener {
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mycourses, container, false);

        view = rootView;

        return rootView;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppUtils.subscriptions != null && AppUtils.subscriptions.size() > 0) {
            RecyclerView recyclerView = view.findViewById(R.id.rvCourses);
            //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            Log.e("TAG", "onCreateView: " + AppUtils.subscriptions);
            MySubscriptions adapter = new MySubscriptions(getActivity(), AppUtils.subscriptions);
            recyclerView.setAdapter(adapter);
        }
    }

    public class MySubscriptions extends RecyclerView.Adapter<MySubscriptions.AppsHolder> {
        private List<String> names;
        int[] courseImages = {R.drawable.socialengicon, R.drawable.malwareicons, R.drawable.cyberattackicon, R.drawable.mitigationicon};

        Activity activity;

        public class AppsHolder extends RecyclerView.ViewHolder {
            CardView itemView;
            ImageView logo;
            TextView title;

            AppsHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.name);
                logo = (ImageView) view.findViewById(R.id.icon);
                itemView = (CardView) view.findViewById(R.id.itemView);
            }
        }

        public MySubscriptions(Activity act, List<String> list) {
            activity = act;
            names = list;
        }

        public MySubscriptions.AppsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MySubscriptions.AppsHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_appitem, viewGroup, false));
        }

        public void onBindViewHolder(MySubscriptions.AppsHolder appsHolder, int i) {
            appsHolder.title.setText("" + Html.fromHtml(names.get(i)));
            appsHolder.title.setSelected(true);
            appsHolder.logo.getLayoutParams().width = (int) (getWidth(activity) * (0.20));
            appsHolder.logo.getLayoutParams().height = (int) (getWidth(activity) * (0.20));
            appsHolder.logo.setImageResource(courseImages[i]);
            appsHolder.logo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int position = i;
                    if (position < 3) {
                        CoursedetailsActivity.position = position;
                        Intent intentHomefragment = new Intent(getActivity(), CoursedetailsActivity.class);
                        intentHomefragment.putExtra("position", position);
                        startActivity(intentHomefragment);
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
}