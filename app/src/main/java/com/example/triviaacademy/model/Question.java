package com.example.triviaacademy.model;

import java.util.List;

public class Question {
    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;

    public Question(String question, List<String> choices, int answer) {
        this.setQuestion(question);
        this.setChoiceList(choices);
        this.setAnswer(answer);
    }

    private void setAnswer(int answer) {
        if (answer < 0 || answer > mChoiceList.size()) {
            throw new IllegalArgumentException("Answer does not exist, index not in choice list.");
        } else {
            mAnswerIndex = answer;
        }
    }

    private void setChoiceList(List<String> choices) {
        if (choices == null) {
            throw new IllegalArgumentException("Answer choices can not be null.");
        } else if (choices.size() > 4) {
            throw new IllegalArgumentException("There should only be 4 answer choices.");
        } else {
            mChoiceList = choices;
        }
    }

    private void setQuestion(String question) {
        if (question.length() == 0) {
            throw new IllegalArgumentException("Question should not be empty");
        } else {
            mQuestion = question;
        }
    }

    public String getQuestion() {
        return mQuestion;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    /**
     * Returns the question, related choices, and answers
     *
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
