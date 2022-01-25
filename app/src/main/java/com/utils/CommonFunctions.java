package com.utils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.coursebook.R;

public class CommonFunctions {
    static Toast toast;
    static String TAG = CommonFunctions.class.getSimpleName();


    public static void finishActivity(Context context) {
        ((Activity) context).finish();
    }

    public static void changeActivityNoANIM(Activity activity, Class cls, Bundle bundle, boolean isfinish) {
        Intent i = new Intent(activity, cls);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        activity.startActivity(i);
        if (isfinish) {
            activity.finish();
        }
    }

    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = null;
        try {
            dialog = new ProgressDialog(mContext, R.style.MyProgressDialog);
            dialog.show();
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setProgressDrawable((new ColorDrawable(Color.GRAY)));
            dialog.setContentView(R.layout.dialog_progress);
        } catch (WindowManager.BadTokenException e) {
            dialog = new ProgressDialog(mContext);
        } catch (Exception e) {
            dialog = new ProgressDialog(mContext);
        }
        return dialog;
    }

    public static void showAlertDialog(final Activity activity, String title, String message, Drawable dr) {
       /* Log.e(TAG, "showAlertDialog: " + message);
        CharSequence charSequence = Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getString(R.string.app_name));
        builder.setMessage(charSequence);
        builder.setCancelable(true)
                .setPositiveButton(MyPrefFunctions.getLang(activity).equals(Language.ENGLISH) ?
                        activity.getResources().getString(R.string.str_en_close) : activity.getResources().getString(R.string.str_vie_close), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alertdialog = builder.create();
        alertdialog.show();*/

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(title);
        WebView wv = new WebView(activity);
        wv.loadData(message, "text/html", "utf-8");
        wv.setBackgroundColor(Color.TRANSPARENT);
        alert.setView(wv);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alert.show();

    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getDeviceId(Context context) {
        return Settings.System.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
