package com.hajirashidli.italianthroughcontext;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hajirashidli.italianthroughcontext.QuizContract.*;

import java.util.ArrayList;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 2; //increasing this updates database

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
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
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Lei può __________?", "piacere", "veramente", "riportarlo", 3);
        addQuestion(q1);
        Question q2 = new Question("Io __________ le mie lezioni quasi ogni giorno.", "ripasso", "prendo", "presti", 1);
        addQuestion(q2);
        Question q3 = new Question("Lui vuole dei __________ rossi per la macchina.", "vetri", "raccogliere", "onore", 1);
        addQuestion(q3);
        Question q4 = new Question("È lei che ci __________.", "spade", "perde", "agenzia", 2);
        addQuestion(q4);
        Question q5 = new Question("Mi __________ una birra.", "paghi", "impedire", "riga", 1);
        addQuestion(q5);
        Question q6 = new Question("Lei può __________?", "inizia", "riportarlo", "beneficio", 2);
        addQuestion(q6);
        Question q7 = new Question("Lei ignora anche la più semplice __________ di scienze.", "indipendenza", "servire", "nozione", 3);
        addQuestion(q7);
        Question q8 = new Question("La situazione si fa __________.", "grave", "trasferito", "periodo", 1);
        addQuestion(q8);
        Question q9 = new Question("Io voglio essere __________.", "spiegarmi", "capre", "prudente", 3);
        addQuestion(q9);
        Question q10 = new Question("Io ho bevuto anche due __________ di tè.", "tazze", "adatto", "dopodomani", 1);
        addQuestion(q10);
//        Question qx = new Question("B is correct again", "A", "B", "C", 2);
//        addQuestion(qx);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    @SuppressLint("Range")
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}