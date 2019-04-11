package com.example.triviaacademy.controller;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * FetchData
 * Pulls trivia questions from the API, given the url and a callable.
 * The callable is an object of a class implementing the FetchDataCallbackInterface
 * which defines the callback method fetchDataCallback.
 */
public class FetchData extends AsyncTask<Void, Void, String> {
    private HttpURLConnection urlConnection;
    private String url;
    private FetchDataCallbackInterface callbackInterface;

    /**
     * Constructor
     * @param url of api
     * @param callbackInterface class which defines the callback method
     */
    public FetchData(String url, FetchDataCallbackInterface callbackInterface) {
        this.url = url;
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
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            //TODO: Add call to default_data.json file as a backup
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
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