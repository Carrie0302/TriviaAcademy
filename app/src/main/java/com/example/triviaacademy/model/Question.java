package com.example.triviaacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class Question
 * Creates a question with a list of choices and an index of the correct answer
 */
public class Question implements Parcelable {

    /**
     * Constructor Question for JSON
     *
     * @param question        string with question
     * @param correctAnswer   string with correct answer
     * @param incorrectAnswer JSONArray with incorrect choices
     */
    public Question(String question, String correctAnswer, List<String> incorrectAnswer) {
        this.setQuestion(question);

        //Randomly add correct answer to a location in the choice list
        int randomAns = mRand.nextInt(incorrectAnswer.size() + 1);
        incorrectAnswer.add(randomAns, correctAnswer);
        this.setChoiceList(incorrectAnswer);
        this.setAnswer(randomAns);
    }

    /**
     * Allow Question to be parsed to another component
     *
     * @param in parcel
     */
    protected Question(Parcel in) {
        mQuestion = in.readString();
        this.mChoiceList = new ArrayList<>();
        in.readList(this.mChoiceList, getClass().getClassLoader());
        mAnswerIndex = in.readInt();
    }

    /**
     * Generates instances of the Parcelable Question from a Parcel
     * for passing across Activities
     */
    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
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
        dest.writeString(mQuestion);
        dest.writeList(mChoiceList);
        dest.writeInt(mAnswerIndex);
    }

    /**
     * Retrieve question
     *
     * @return string value for question
     */
    public String getQuestion() {
        return mQuestion;
    }

    /**
     * Retrieve Choices
     *
     * @return get list of all choices
     */
    public List<String> getChoiceList() {
        return mChoiceList;
    }

    /**
     * Get Answer Index
     *
     * @return index of answer in choice list
     */
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

    /**
     * Converts the json array to a list
     *
     * @param arr Json array
     * @return list with contents in Json
     */
    private List<String> convertJsontoList(JSONArray arr) {
        List<String> list = new ArrayList<>();
        if (arr != null) {
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
     *
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
     *
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
     * Set Question
     *
     * @param question trivia question can not be empty
     */
    private void setQuestion(String question) {
        if (question.length() == 0) {
            throw new IllegalArgumentException("Question should not be empty");
        } else {
            mQuestion = question;
        }
    }

    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;
    private static Random mRand = new Random();

}
