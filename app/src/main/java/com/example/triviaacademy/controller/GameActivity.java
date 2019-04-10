package com.example.triviaacademy.controller;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triviaacademy.R;
import com.example.triviaacademy.model.Question;
import com.example.triviaacademy.model.QuestionBank;

import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private TextView mQuestionText;
    private Button mAnswer1;
    private Button mAnswer2;
    private Button mAnswer3;
    private Button mAnswer4;
    private int mScore;
    private int mNumberOfQuestions;
    final private static int NUMBER_OF_QUESTIONS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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

        mAnswer1.setOnClickListener(this);
        mAnswer2.setOnClickListener(this);
        mAnswer3.setOnClickListener(this);
        mAnswer4.setOnClickListener(this);

        //Generate Question List and display first question
        mQuestionBank = this.generateQuestions();
        mCurrentQuestion = mQuestionBank.getNextQuestion();
        this.displayQuestion(mCurrentQuestion);

        //Set up number of questions and score
        mNumberOfQuestions = NUMBER_OF_QUESTIONS;
        mScore = 0;
    }

    /**
     * When a answer button is clicked the answer is checked
     * and then the next question populates the view
     * @param v the view
     */
    @Override
    public void onClick(View v) {
        int responseTag = (int) v.getTag();
        this.checkAnswer(responseTag);
        this.nextQuestion();
    }

    /**
     * Update the view with the current question
     * @param question the current question
     */
    private void displayQuestion(final Question question) {
        // Set the text for the question text view and the four buttons
        String q = question.getQuestion();
        mQuestionText.setText(q);

        List<String> choices = question.getChoiceList();
        mAnswer1.setText(choices.get(0));
        mAnswer2.setText(choices.get(1));
        mAnswer3.setText(choices.get(2));
        mAnswer4.setText(choices.get(3));
    }


    //TODO connect to a model that pulls data from the api https://opentdb.com/api_config.php
    private QuestionBank generateQuestions() {
        Question question1 = new Question("What is the name of the current french president?",
                Arrays.asList("François Hollande", "Emmanuel Macron", "Jacques Chirac", "François Mitterand"),
                1);

        Question question2 = new Question("How many countries are there in the European Union?",
                Arrays.asList("15", "24", "28", "32"),
                2);

        Question question3 = new Question("Who is the creator of the Android operating system?",
                Arrays.asList("Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"),
                0);

        Question question4 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958", "1962", "1967", "1969"),
                3);

        Question question5 = new Question("What is the capital of Romania?",
                Arrays.asList("Bucarest", "Warsaw", "Budapest", "Berlin"),
                0);

        Question question6 = new Question("Who did the Mona Lisa paint?",
                Arrays.asList("Michelangelo", "Leonardo Da Vinci", "Raphael", "Carravagio"),
                1);

        Question question7 = new Question("In which city is the composer Frédéric Chopin buried?",
                Arrays.asList("Strasbourg", "Warsaw", "Paris", "Moscow"),
                2);

        Question question8 = new Question("What is the country top-level domain of Belgium?",
                Arrays.asList(".bg", ".bm", ".bl", ".be"),
                3);

        Question question9 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42", "101", "666", "742"),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9));
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
        endMessage.setText(this.evaluateScore());
        String result = getString(R.string.dialog_score) +  mScore + " out of " + NUMBER_OF_QUESTIONS;
        endScore.setText(result);

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
}
