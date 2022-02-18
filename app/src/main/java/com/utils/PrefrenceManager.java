package com.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;


public class PrefrenceManager {

    public static void setToken(Context activity, String u_id) {
        Prefrence.writeString(activity, Prefrence.TOKEN, u_id);
    }

    public static String getToken(Context activity) {
        return Prefrence.readString(activity, Prefrence.TOKEN, "");
    }

    public static void setUserData(Context activity, String u_name) {
        Prefrence.writeString(activity, Prefrence.USER_DATA, u_name);
    }

    public static String getUserData(Context activity) {
        return Prefrence.readString(activity, Prefrence.USER_DATA, null);
    }

    public static void setClientData(Context activity, String u_name) {
        Prefrence.writeString(activity, Prefrence.CLIENT_DATA, u_name);
    }

    public static String getClientData(Context activity) {
        return Prefrence.readString(activity, Prefrence.CLIENT_DATA, null);
    }

    public static void setUserClientData(Context activity, String u_name) {
        Prefrence.writeString(activity, Prefrence.USER_CLIENT_DATA, u_name);
    }

    public static String getUserClientData(Context activity) {
        return Prefrence.readString(activity, Prefrence.USER_CLIENT_DATA, null);
    }

    public static void setIsLogedin(Context activity, boolean is_finished) {
        Prefrence.writeBoolean(activity, Prefrence.ISLOGGEDIN, is_finished);
    }

    public static boolean getIsLogedin(Context activity) {
        return Prefrence.readBoolean(activity, Prefrence.ISLOGGEDIN, false);
    }

    public static UserModel setUserModel(Context activity, UserModel loginUserDataModel) {
        Gson gson = new Gson();
        String json = gson.toJson(loginUserDataModel);
        PrefrenceManager.setUserData(activity, json);
        return null;
    }

    public static UserModel getUserModel(Context activity) {
        Gson gson = new Gson();
        if (PrefrenceManager.getUserData(activity) != null) {
            UserModel loginUserDataModel = gson.fromJson(PrefrenceManager.getUserData(activity), UserModel.class);
            return loginUserDataModel;
        }
        return null;
    }


    public static void clear(Context context) {
        Prefrence.clear(context);
    }

    public static class Prefrence {
        public static final String PREF_NAME = "CourseBook";
        public static final String TYPE = "TYPE";
        public static final String TOKEN = "TOKEN";
        public static final String USER_DATA = "USER_DATA";
        public static final String CLIENT_DATA = "CLIENT_DATA";
        public static final String USER_CLIENT_DATA = "USER_CLIENT_DATA";
        public static final String ISLOGGEDIN = "ISLOGGEDIN";

        public static void writeBoolean(Context context, String key, boolean value) {
            getEditor(context).putBoolean(key, value).commit();
        }

        public static boolean readBoolean(Context context, String key, boolean defValue) {
            return getPreferences(context).getBoolean(key, defValue);
        }

        public static void writeInteger(Context context, String key, int value) {
            getEditor(context).putInt(key, value).commit();
        }

        public static int readInteger(Context context, String key, int defValue) {
            return getPreferences(context).getInt(key, defValue);
        }

        public static void writeString(Context context, String key, String value) {
            getEditor(context).putString(key, value).commit();

        }

        public static String readString(Context context, String key, String defValue) {
            return getPreferences(context).getString(key, defValue);
        }

        public static void writeFloat(Context context, String key, float value) {
            getEditor(context).putFloat(key, value).commit();
        }

        public static float readFloat(Context context, String key, float defValue) {
            return getPreferences(context).getFloat(key, defValue);
        }

        public static void writeLong(Context context, String key, long value) {
            getEditor(context).putLong(key, value).commit();
        }

        public static long readLong(Context context, String key, long defValue) {
            return getPreferences(context).getLong(key, defValue);
        }

        public static SharedPreferences getPreferences(Context context) {
            return context.getSharedPreferences(PREF_NAME, 0);
        }

        public static SharedPreferences.Editor getEditor(Context context) {
            return getPreferences(context).edit();
        }

        public static void clear(Context act) {
            getEditor(act).clear().commit();
        }
    }

}
