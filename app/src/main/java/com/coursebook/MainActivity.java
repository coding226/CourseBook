package com.coursebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.adapter.MainPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.utils.AppUtils;
import com.utils.Constants;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Activity mActivity;
    private Context mContext;

    private ViewPager mViewPager;
    private ArrayList<String> mFragmentItems;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
        initViews();
        initFunctionality();
        initListeners();
    }

    private void initVars() {
        mActivity = MainActivity.this;
        mContext = mActivity.getApplicationContext();
        mFragmentItems = new ArrayList<>();
    }

    private void initViews() {
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
    }

    private void initFunctionality() {
        if ((ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.PERMISSION_REQ);
        } else {
            setUpViewPager();
        }
    }

    private void initListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        mViewPager.setCurrentItem(0, true);
                        break;
                    case R.id.nav_mycourses:
                        mViewPager.setCurrentItem(1, true);
                        break;
                    case R.id.nav_account:
                        mViewPager.setCurrentItem(2, true);
                        break;
                }
                return true;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    bottomNavigationView.setSelectedItemId(R.id.nav_home);
                } else if (position == 1) {
                    bottomNavigationView.setSelectedItemId(R.id.nav_mycourses);
                } else if (position == 2) {
                    bottomNavigationView.setSelectedItemId(R.id.nav_account);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUpViewPager() {
        mFragmentItems.add("Home");
        mFragmentItems.add("My Courses");
        mFragmentItems.add("Account");

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragmentItems);
        mViewPager.setAdapter(mainPagerAdapter);
        mainPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == Constants.PERMISSION_REQ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpViewPager();
            } else {
                AppUtils.showToast(mContext, "Permission Not Granted");
            }

        }
    }

    @Override
    public void onBackPressed() {
        AppUtils.tapToExit(mActivity);
    }
}
