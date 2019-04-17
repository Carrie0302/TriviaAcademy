package com.example.triviaacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * class QuestionBank
 * Creates a bank of questions and answers that can be iterated through
 */
public class QuestionBank implements Parcelable {

    /**
     * Constructor for empty question list
     */
    public QuestionBank() {
        mNextQuestionIndex = 0;
        mQuestionList = new ArrayList<>();
    }

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
     * Allow Question Bank to be parsed to another component
     *
     * @param in parcel
     */
    protected QuestionBank(Parcel in) {
        this.mNextQuestionIndex = in.readInt();
        this.mQuestionList = new ArrayList<>();
        in.readList(this.mQuestionList, getClass().getClassLoader());
    }

    /**
     * Generates instances of the Parcelable QuestionBank from a Parcel
     * for passing across Activities
     */
    public static final Creator<QuestionBank> CREATOR = new Creator<QuestionBank>() {
        @Override
        public QuestionBank createFromParcel(Parcel in) {
            return new QuestionBank(in);
        }

        @Override
        public QuestionBank[] newArray(int size) {
            return new QuestionBank[size];
        }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation.
     *
     * @return 0 means no special object types marshaled
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write all object members into parcel objects for parsing
     *
     * @param dest  the Parcel in which the object should be written.
     * @param flags additional flags about how the object should be written.
     *              May be 0 or PARCELABLE_WRITE_RETURN_VALUE. Value is either
     *              0 or a combination of PARCELABLE_WRITE_RETURN_VALUE, and
     *              android.os.Parcelable.PARCELABLE_ELIDE_DUPLICATES
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mNextQuestionIndex);
        dest.writeList(mQuestionList);
    }

    /**
     * Add Questions to the current bank
     *
     * @param q Question
     */
    public void addQuestion(Question q) {
        mQuestionList.add(q);
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

    /**
     * Is Empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty(){
        return mQuestionList.size() == 0;
    }

    private List<Question> mQuestionList;
    private int mNextQuestionIndex;
}
