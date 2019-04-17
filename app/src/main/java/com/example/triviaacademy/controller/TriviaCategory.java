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
import com.example.triviaacademy.model.Category;
import com.example.triviaacademy.model.QuestionBank;


public class TriviaCategory extends Fragment  implements OnClickListener, FetchDataCallbackInterface{

    public TriviaCategory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param category includes the name, id, and icons of a category
     * @param numberOfQuestions number of questions user asks for
     * @return A new instance of fragment TriviaCategory.
     */
    public static TriviaCategory newInstance(Category category, int numberOfQuestions) {
        TriviaCategory fragment = new TriviaCategory();
        Bundle args = new Bundle();
        args.putParcelable(CATEGORY_OBJ, category);
        args.putInt(NUMBER_QUESTIONS, numberOfQuestions);
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
            mCategory = (Category) getArguments().get(CATEGORY_OBJ);
            mNumberOfQuestions = getArguments().getInt(NUMBER_QUESTIONS);
            mId = mCategory.getId();
        }
        // fetch server data only once
        if( data == null ) {
            // automatically calls the renderData function
            new FetchData(mNumberOfQuestions , mId, this).execute();
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
        tv.setText(mCategory.getName());

        //Add icon to fragment image container
        ImageView im = inf.findViewById(R.id.trivia_category_icon);
        im.setImageResource(mCategory.getDarkIcon());
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
        ImageView icon = v.findViewById(R.id.trivia_category_icon);
        icon.setImageResource(mCategory.getLightIcon());

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

    @Override
    public void fetchDataCallback(QuestionBank questions) {
        mQuestionList = questions;
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
    private static final String CATEGORY_OBJ = "category";
    private static final String NUMBER_QUESTIONS = "numberOfQuestions";
    private static String data;
    private QuestionBank mQuestionList = new QuestionBank();
    private Category mCategory;
    private int mId;
    private int mNumberOfQuestions;
    private OnFragmentInteractionListener mListener;

}
