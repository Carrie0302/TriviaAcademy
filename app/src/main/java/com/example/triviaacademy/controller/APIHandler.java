package com.example.triviaacademy.controller;

public class APIHandler  {
    private static final String API_URL = "https://opentdb.com/api.php?";
    private int mCountOfQuestions;
    private int mCategoryId;
    private String mResponse;

    public APIHandler( int countOfQuestions, int categoryId){
        this.mCategoryId = categoryId;
        this.mCountOfQuestions = countOfQuestions;
        this.mResponse = null;
    }
}