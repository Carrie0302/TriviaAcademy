package com.example.triviaacademy.controller;

import android.os.AsyncTask;
import com.example.triviaacademy.model.Question;
import com.example.triviaacademy.model.QuestionBank;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

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
    private QuestionBank mQuestionList;

    /**
     * Constructor for all difficulty levels
     * @param numberOfQuestions number of questions
     * @param categoryId id for category
     * @param callbackInterface class which defines the callback method
     */
    public FetchData(int numberOfQuestions, int categoryId, FetchDataCallbackInterface callbackInterface) {
        this.url = "https://opentdb.com/api.php?amount=" + numberOfQuestions +
                "&category="+ categoryId + "&type=multiple";
        this.callbackInterface = callbackInterface;
        this.mQuestionList = new QuestionBank();
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
        parseData( result.toString());
        return result.toString();
    }


    /**
     * Parse data from the trivia API
     * converts HTML Entities to text and creates a question bank
     * with the results
     */
    public void parseData(String data) {
        String question;
        String correctAns;

        try {
            JSONObject object = new JSONObject(data);
            int resultID = object.getInt("response_code");

            //Successful API request and data found
            if( resultID == 0 ) {
                JSONArray arr = (JSONArray) object.getJSONArray("results");

                // Iterate through Questions and Get data
                for (int i = 0; i < arr.length(); i++) {
                    List<String> incorrectAns = new ArrayList<>();
                    JSONObject j = arr.getJSONObject(i);
                    String questS = j.getString("question");
                    String correctAnsRaw = j.getString("correct_answer");
                    JSONArray incorrectAnsRaw = j.getJSONArray("incorrect_answers");

                    //Remove HTML Entities from text
                    question =  Jsoup.parse(questS).text();
                    correctAns = Jsoup.parse(correctAnsRaw).text();
                    for( int k = 0; k < incorrectAnsRaw.length(); k++){
                        String cleaned = Jsoup.parse(incorrectAnsRaw.get(k).toString()).text();
                        incorrectAns.add(cleaned);
                    }

                    Question q = new Question(question, correctAns, incorrectAns);
                    mQuestionList.addQuestion(q);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Pass the result to the callback function
     * @param result questions from trivia api
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        //pass the questions back
        this.callbackInterface.fetchDataCallback(mQuestionList);
    }

}