package com.example.triviaacademy.controller;

import com.example.triviaacademy.model.QuestionBank;

/**
 * FetchDataCallbackInterface
 * Interface defining a callable to be used as callback when fetching server data
 */
public interface FetchDataCallbackInterface {
    // method called when server's data is fetched
    public void fetchDataCallback (QuestionBank questions);

}