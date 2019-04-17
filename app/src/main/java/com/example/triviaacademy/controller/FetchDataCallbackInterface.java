package com.example.triviaacademy.controller;

/**
 * FetchDataCallbackInterface
 * Interface defining a callable to be used as callback when fetching server data
 */
public interface FetchDataCallbackInterface {
    // method called when server's data is fetched
    public void fetchDataCallback (String result);
}