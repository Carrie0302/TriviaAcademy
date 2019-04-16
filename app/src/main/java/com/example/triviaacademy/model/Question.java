package com.example.triviaacademy.model;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Question {
    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;
    private static Random mRand = new Random();

    /**
     * Constructor Question with all choices
     * @param question string with question
     * @param choices string with all choices
     * @param answer index of correct answer
     */
    public Question(String question, List<String> choices, int answer) {
        this.setQuestion(question);
        this.setChoiceList(choices);
        this.setAnswer(answer);
    }

    /**
     * Constructor Question for JSON
     * @param question string with question
     * @param correctAnswer string with correct answer
     * @param incorrectAnswer JSONArray with incorrect choices
     */
    public Question(String question, String correctAnswer, JSONArray incorrectAnswer) {
        this.setQuestion(question);

        //Randomly add correct answer to a location in the choice list
        List<String> choices = convertJsontoList(incorrectAnswer);
        int randomAns = mRand.nextInt( choices.size() + 1);
        choices.add(randomAns, correctAnswer);
        this.setChoiceList(choices);
        this.setAnswer(randomAns);
    }

    /**
     * Converts the json array to a list
     * @param arr Json array
     * @return list with contents in Json
     */
    public List<String> convertJsontoList(JSONArray arr){
        List<String> list = new ArrayList<String>();
        if ( arr != null) {
            for (int i = 0; i < arr.length(); i++) {
                try {
                    list.add(arr.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * Set Answer and check that it is inbounds
     * @param answer index in list
     */
    private void setAnswer(int answer) {
        if (answer < 0 || answer > mChoiceList.size()) {
            throw new IllegalArgumentException("Answer does not exist, index not in choice list.");
        } else {
            mAnswerIndex = answer;
        }
    }

    /**
     * Set Choice List
     * @param choices all choices can not be null and must contain 4 answers
     */
    private void setChoiceList(List<String> choices) {
        if (choices == null) {
            throw new IllegalArgumentException("Answer choices can not be null.");
        } else if (choices.size() > 4) {
            throw new IllegalArgumentException("There should only be 4 answer choices.");
        } else {
            mChoiceList = choices;
        }
    }

    /**
     * SetQuestion
     * @param question trivia question can not be empty
     */
    private void setQuestion(String question) {
        if (question.length() == 0) {
            throw new IllegalArgumentException("Question should not be empty");
        } else {
            mQuestion = question;
        }
    }

    /**
     * Retrieve question
     * @return string value for question
     */
    public String getQuestion() {
        return mQuestion;
    }

    /**
     * Retrieve Choices
     * @return get list of all choices
     */
    public List<String> getChoiceList() {
        return mChoiceList;
    }

    /**
     * Get Answer Index
     * @return index of answer in choice list
     */
    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    /**
     * Returns the question, related choices, and answers
     * @return string with question, choices, and answers
     */
    @Override
    public String toString() {
        return "Question{" +
                "mQuestion='" + mQuestion + '\'' +
                ", mChoiceList=" + mChoiceList +
                ", mAnswerIndex=" + mAnswerIndex +
                '}';
    }


}
