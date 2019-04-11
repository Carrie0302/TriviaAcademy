package com.example.triviaacademy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionBank {
    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    /**
     * Creates a copy of the question list and shuffles them
     *
     * @param questions list of questions
     */
    public QuestionBank(List<Question> questions) {
        mNextQuestionIndex = 0;
        mQuestionList = new ArrayList<>();
        mQuestionList.addAll(questions);
        Collections.shuffle(mQuestionList);
    }

    /**
     * Get next question
     *
     * @return next question, if you run out of questions then it starts over
     */
    public Question getNextQuestion() {
        if (mNextQuestionIndex == mQuestionList.size()) {
            mNextQuestionIndex = 0;
        }
        Question next = mQuestionList.get(mNextQuestionIndex);
        mNextQuestionIndex++;
        return next;
    }

}
