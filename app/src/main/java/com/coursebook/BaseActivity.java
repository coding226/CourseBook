package com.coursebook;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.utils.CommonFunctions;


// This is base activity and it is extended in all activities to use its methods
public class BaseActivity extends AppCompatActivity {

    private static String TAG = BaseActivity.class.getSimpleName();

    protected Activity mActivity;

    protected Context mContext;

    private ProgressDialog mProgressDialog;

    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = CommonFunctions.createProgressDialog(mContext);
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void initContext(Context context, Activity activity) {
        mContext = context;

        mActivity = activity;

    }

    protected boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }

    public void showToast(String message) {

        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void hideKeyBoard(View view) {
        CommonFunctions.hideKeyboard(mContext, view);
    }

    protected void finishActivity() {
        CommonFunctions.finishActivity(mActivity);
    }

}
