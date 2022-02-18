package com.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TriviaQuizHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME = "TQuiz.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "TQ";
    private static final String UID = "_UID";
    private static final String USERNAME = "USERNAME";
    private static final String TOTALQUESTIONS = "TOTALQUESTIONS";
    private static final String CORRECTANSWERS = "CORRECTANSWERS";
    private static final String TEST1 = "TEST1";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + USERNAME + " VARCHAR(255), " + TOTALQUESTIONS + " VARCHAR(255), " + CORRECTANSWERS + " VARCHAR(255), " + TEST1 + " VARCHAR(255));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public TriviaQuizHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //OnCreate is called only once
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //OnUpgrade is called when ever we upgrade or increment our database version no
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }


    public void insertUserQuiz(TriviaQuestionModel triviaQuestionModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(USERNAME, triviaQuestionModel.getName());
            values.put(TOTALQUESTIONS, triviaQuestionModel.getTotalQuestions());
            values.put(CORRECTANSWERS, triviaQuestionModel.getCorrectAnswers());
            values.put(TEST1, triviaQuestionModel.getTest1());
            db.insert(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    public List<TriviaQuestionModel> getAllQuizResult() {
        List<TriviaQuestionModel> questionsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[] = {UID, USERNAME, TOTALQUESTIONS, CORRECTANSWERS, TEST1};
        Cursor cursor = db.query(TABLE_NAME, coloumn, null, null, null, null, null);


        while (cursor.moveToNext()) {
            TriviaQuestionModel question = new TriviaQuestionModel();
            question.setId(cursor.getInt(0));
            question.setName(cursor.getString(1));
            question.setTotalQuestions(cursor.getString(2));
            question.setCorrectAnswers(cursor.getString(3));
            question.setTest1(cursor.getString(4));
            questionsList.add(question);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return questionsList;
    }

    public void deleteItem(String VId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, UID + " = ? ", new String[]{VId});
    }

}
