package com.example.android.mcq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.mcq.QuizContract.QuestionsTable;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MCQ.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    public QuizDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT " +
                " ) ";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
    onCreate(db);
    }
    private void fillQuestionsTable(){
        Question q1 = new Question("EASY: A is correct " ,"A","B","C",1,Question.DIFFICULTY_EASY );
        addQuestion(q1);
        Question q2 = new Question("MEDIUM: B is correct " ,"A","B","C",2,Question.DIFFICULTY_MEDIUM );
        addQuestion(q2);
        Question q3 = new Question("HARD: C is correct " ,"A","B","C",3,Question.DIFFICULTY_HARD );
        addQuestion(q3);
        Question q4 = new Question("EASY: B is correct " ,"A","B","C",2,Question.DIFFICULTY_EASY);
        addQuestion(q4);
        Question q5 = new Question("MEDIUM: B is correct " ,"A","B","C",2,Question.DIFFICULTY_MEDIUM );
        addQuestion(q5);
        Question q6 = new Question("EASY: B is correct " ,"A","B","C",2,Question.DIFFICULTY_EASY);
        addQuestion(q6);
        Question q7 = new Question("MEDIUM: c is correct " ,"A","B","C",3,Question.DIFFICULTY_MEDIUM );
        addQuestion(q7);

    }
    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION,question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2,question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3,question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR,question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY,question.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME,null,cv);

    }
    public ArrayList<Question> getAllQuestions(){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c =db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()){
            do{
              Question question = new Question();
              question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
              question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
              question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
              question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
              question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
              question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
              questionList.add(question);
            }
            while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(String difficulty){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] SelectionArgs = new String[]{difficulty};
        Cursor c =db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
              " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " =?", SelectionArgs);
        if (c.moveToFirst()){
            do{
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            }
            while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

}
