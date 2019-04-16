package com.example.triviaacademy.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.triviaacademy.R;
import com.example.triviaacademy.model.Question;
import com.example.triviaacademy.model.QuestionBank;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


/**
 * class TriviaCategory
 * Is a trivia category fragment that fetches questions and starts a quiz when selected
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TriviaCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TriviaCategory extends Fragment  implements OnClickListener, FetchDataCallbackInterface{

    public TriviaCategory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param categoryHeader header name for category
     * @param iconID integer id for icon
     * @return A new instance of fragment TriviaCategory.
     */
    public static TriviaCategory newInstance(String categoryHeader, int iconID) {
        TriviaCategory fragment = new TriviaCategory();
        Bundle args = new Bundle();
        args.putString(CATEGORY_HEADER, categoryHeader);
        args.putInt(ICON_ID, iconID);
        fragment.setArguments(args);
        data = null;
        return fragment;
    }

    /**
     * Fragment instance initializes with header and icon and trivia data
     * @param savedInstanceState if the fragment is being re-created from a previous
     *                           saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get arguments for header and icon
        if (getArguments() != null) {
            mCategoryHeader = getArguments().getString(CATEGORY_HEADER);
            mIconId = getArguments().getInt(ICON_ID);
        }
        // fetch server data only once
        if( data == null ) {
            // automatically calls the renderData function
            new FetchData("https://opentdb.com/api.php?amount=5&category=17&type=multiple", this).execute();
        }
        else {
            renderData();
        }
    }

    /**
     * Call fragment to instantiate its user interface view with the header and icon
     * @param inflater the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container if non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState if non-null, this fragment is re-constructed from a previous saved state
     * @return View for the trivia fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflating the layout for this fragment and add onclick listener
        View inf = inflater.inflate(R.layout.fragment_trivia_category_box, container, false);
        inf.setOnClickListener(this);

        //Add header to fragment text container
        TextView tv = (TextView) inf.findViewById(R.id.trivia_category_header);
        tv.setText(mCategoryHeader);

        //Add icon to fragment image container
        ImageView im = (ImageView) inf.findViewById(R.id.trivia_category_icon);
        im.setImageResource(mIconId);
        return inf;
    }

    /**
     *  A callback to be invoked when the fragment is clicked
     * @param v the view that was clicked
     */
    @Override
    public void onClick(View v) {
        //Change style to show box was selected
        v.setBackgroundColor(getResources().getColor(R.color.backgroundBright));
        ImageView icon = (ImageView) v.findViewById(R.id.trivia_category_icon);
        icon.setImageResource(R.drawable.ic_bacteria_light);

        //Start game with the trivia data
        Intent new_game = new Intent( getActivity(), GameActivity.class);
        new_game.putExtra("questionList", mQuestionList);
        startActivity(new_game);
    }

    /**
     * When fragment is first attached add listener
     * @param context the context of current state of the application
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Callback fetching Open Trivia API data
     * @param result json data from API
     */
    @Override
    public void fetchDataCallback(String result) {
        data = result;
        renderData();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * Render data from the trivia API
     * parses the Json question data
     */
    public void renderData() {
        try {
            JSONObject object = (JSONObject) new JSONTokener(data).nextValue();
            int resultID = object.getInt("response_code");

            //Successful API request and data found
            if( resultID == 0 ) {
                JSONArray arr = (JSONArray) object.getJSONArray("results");

                // Iterate through Questions
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject j = arr.getJSONObject(i);
                    //Pull data
                    String question = j.getString("question");
                    String correctAns = j.getString("correct_answer");
                    JSONArray incorrectAns = j.getJSONArray("incorrect_answers");
                    Question q = new Question(question, correctAns, incorrectAns);
                    mQuestionList.addQuestion(q);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //Fragment initialization parameters
    private static final String CATEGORY_HEADER = "categoryHeader";
    private static final String ICON_ID = "iconID";
    private static String data;
    private QuestionBank mQuestionList = new QuestionBank();
    private String mCategoryHeader;
    private int mIconId;
    private OnFragmentInteractionListener mListener;

}
