package com.example.triviaacademy.controller;

import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * class FetchData
 * Pulls trivia questions from the API, given the url and a callable.
 * The callable is an object of a class implementing the FetchDataCallbackInterface
 * which defines the callback method fetchDataCallback.
 */
public class FetchData extends AsyncTask<Void, Void, String> {
    private HttpURLConnection urlConnection;
    private String url;
    private FetchDataCallbackInterface callbackInterface;

    /**
     * Constructor for all difficulty levels
     * @param numberOfQuestions number of questions
     * @param categoryId id for category
     * @param callbackInterface class which defines the callback method
     */
    public FetchData(int numberOfQuestions, int categoryId, FetchDataCallbackInterface callbackInterface) {
        this.url = "https://opentdb.com/api.php?amount=" + numberOfQuestions +
                "&category="+ categoryId + "&type=multiple&encode=url3986";
        this.callbackInterface = callbackInterface;
    }

    /**
     * Pull trivia data from API
     * @param voids
     * @return resulting questions from trivia API
     */
    @Override
    protected String doInBackground(Void... voids) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            String decode;
            while ((line = reader.readLine()) != null) {
                decode =  java.net.URLDecoder.decode(line, "UTF_8");
                result.append(decode);
            }
        } catch (Exception e) {
            //TODO: Add call to default_data.json file as a backup
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        System.out.println(result.toString());
        return result.toString();
    }

    /**
     * Pass the result to the callback function
     * @param result questions from trivia api
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // pass the result to the callback function
        this.callbackInterface.fetchDataCallback(result);
    }

}