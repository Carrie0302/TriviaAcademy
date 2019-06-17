package com.example.triviaacademy.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.triviaacademy.R;
import com.example.triviaacademy.model.Question;
import com.example.triviaacademy.model.QuestionBank;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * class GameActivity
 * Controls the game activity, displaying questions, checking answers, and
 * evaluating the score
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Initialization when game activity is starting
     * @param savedInstanceState if the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the
     *                           data it most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Get transferred trivia data from intent
        Intent intent = getIntent();
        mQuestionBank = (QuestionBank) intent.getParcelableExtra("questionList");

        //Get text and buttons from view
        mQuestionText = (TextView) findViewById(R.id.activity_game_question_text);
        mAnswer1 = (Button) findViewById(R.id.activity_game_answer1_btn);
        mAnswer2 = (Button) findViewById(R.id.activity_game_answer2_btn);
        mAnswer3 = (Button) findViewById(R.id.activity_game_answer3_btn);
        mAnswer4 = (Button) findViewById(R.id.activity_game_answer4_btn);
        mAnswer1.setTag(0);
        mAnswer2.setTag(1);
        mAnswer3.setTag(2);
        mAnswer4.setTag(3);

        //Add on click listeners to answers
        mAnswer1.setOnClickListener(this);
        mAnswer2.setOnClickListener(this);
        mAnswer3.setOnClickListener(this);
        mAnswer4.setOnClickListener(this);

        //Display first question
        mCurrentQuestion = mQuestionBank.getNextQuestion();
        this.displayQuestion(mCurrentQuestion);

        //Set up number of questions and score
        mNumberOfQuestions = NUMBER_OF_QUESTIONS;
        mScore = 0;

        //Save which questions and answers are wrong
        mAnswersWrong = new Vector();
        mQuestionsWrong = new Vector();

        startTime = System.nanoTime();
    }

    /**
     * When a answer button is clicked the answer is checked
     * and then the next question populates the view
     * @param v view for the game activities UI
     */
    @Override
    public void onClick(View v) {
        int responseTag = (int) v.getTag();
        this.checkAnswer(responseTag);
        this.nextQuestion();
    }

    /**
     * Update the view with the current question and answers
     * @param question the current question
     */
    private void displayQuestion(final Question question) {
        String q = question.getQuestion();
        mQuestionText.setText(q);
        List<String> choices = question.getChoiceList();
        mAnswer1.setText(choices.get(0));
        mAnswer2.setText(choices.get(1));
        mAnswer3.setText(choices.get(2));
        mAnswer4.setText(choices.get(3));
    }

    /**
     * Check user's answer, update score, and populate message
     * @param guess is the user's answer
     */
    private void checkAnswer( int guess ){
        if( guess == mCurrentQuestion.getAnswerIndex()){
            // Correct answer
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            mScore++;
        }
        else{
            //Wrong answer
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            int correct = mCurrentQuestion.getAnswerIndex();
            mAnswersWrong.add(  mCurrentQuestion.getChoiceList().get(correct));
            mQuestionsWrong.add( mCurrentQuestion.getQuestion() );

        }
    }

    /**
     * Get the next question and populate the view
     * If no questions are left a message appears with the score
     */
    private void nextQuestion(){
        mNumberOfQuestions--;
        if( mNumberOfQuestions == 0){
            //No questions left
            this.endGame();
        }
        else{
            //Next question
            mCurrentQuestion = mQuestionBank.getNextQuestion();
            this.displayQuestion(mCurrentQuestion);
        }
    }

    /**
     * Display incorrect questions and answers
     * @return string with incorrect q and a
     */
    private String displayWrongQandA(){
        String result = "";
        if( mQuestionsWrong.size() == 0 ){
            return "None!";
        }
        else {
            for (int i = 0; i < mQuestionsWrong.size(); i++) {
                result += mQuestionsWrong.get(i);
                result += "\n\tCorrect: ";
                result += mAnswersWrong.get(i) + "\n";
            }
            return result;
        }
    }

    /**
     * Evaluates user score
     * @return string statement saying whether they did well or not
     */
    private String evaluateScore(){
        String titleResult = "";
        if( mScore == 0 ){
            titleResult = getString(R.string.score_bad);
        }
        else if( NUMBER_OF_QUESTIONS /mScore  == 1 ){
            titleResult = getString(R.string.score_excellent);
        }
        else {
            titleResult = getString(R.string.score_ok);
        }
        return titleResult;
    }

    /**
     * Calculate time to take quiz
     */
    private String durationOfQuiz(){
        endTime = System.nanoTime();
        double duration = (endTime - startTime)/ 1_000_000_000.0;
        return String.format("%.1f sec", duration);
    }

    /**
     * Show score at end of game
     */
    //TODO show which answers were wrong and the correct results
    private void endGame(){
        AlertDialog.Builder end = new AlertDialog.Builder(this, R.style.AppTheme_DialogStyle);
        LayoutInflater inflater = this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_score_results, null);

        //Get the ids from the dialog layout and update the content
        final TextView endMessage = alertLayout.findViewById(R.id.dialog_end_message);
        final TextView endScore = alertLayout.findViewById(R.id.dialog_end_score);
        final TextView endIncorrect = alertLayout.findViewById(R.id.dialog_incorrect);

        //Add score
        endMessage.setText(this.evaluateScore());
        String result = getString(R.string.dialog_score) + " " + mScore + " out of " + NUMBER_OF_QUESTIONS;
        result += "   Time: " + durationOfQuiz();

        endScore.setText(result);

        //Add incorrect Q and A
        String resultIncT = displayWrongQandA();
        endIncorrect.setText(resultIncT);

        end.setView(alertLayout)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create()
                .show();
    }

    final private static int NUMBER_OF_QUESTIONS = 5;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private TextView mQuestionText;
    private Button mAnswer1;
    private Button mAnswer2;
    private Button mAnswer3;
    private Button mAnswer4;
    private int mScore;
    private int mNumberOfQuestions;
    private Vector mAnswersWrong;
    private Vector mQuestionsWrong;
    private long startTime;
    private long endTime;

}
