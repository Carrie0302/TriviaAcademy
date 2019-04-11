package com.example.triviaacademy.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.triviaacademy.R;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TriviaCategoryBox#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TriviaCategoryBox extends Fragment  implements OnClickListener {

    //Fragment initialization parameters
    private static final String CATEGORY_HEADER = "categoryHeader";
    private static final String ICON_ID = "iconID";
    private String mCategoryHeader;
    private int mIconId;
    private OnFragmentInteractionListener mListener;

    public TriviaCategoryBox() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param categoryHeader header name for category
     * @param iconID integer id for icon
     * @return A new instance of fragment TriviaCategoryBox.
     */
    public static TriviaCategoryBox newInstance(String categoryHeader, int iconID) {
        TriviaCategoryBox fragment = new TriviaCategoryBox();
        Bundle args = new Bundle();
        args.putString(CATEGORY_HEADER, categoryHeader);
        args.putInt(ICON_ID, iconID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoryHeader = getArguments().getString(CATEGORY_HEADER);
            mIconId = getArguments().getInt(ICON_ID);
        }
    }

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


    @Override
    public void onClick(View v) {
        //Change style to show box was selected
        v.setBackgroundColor(getResources().getColor(R.color.backgroundBright));
        ImageView icon = (ImageView) v.findViewById(R.id.trivia_category_icon);
//        DrawableCompat.setTint(
//                icon.getDrawable(),
//                ContextCompat.getColor(getContext(), R.color.white)
//        );
        icon.setImageResource(R.drawable.ic_bacteria_light);

        //Start game
        Intent new_game = new Intent( getActivity(), GameActivity.class);
        startActivity(new_game);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
}
