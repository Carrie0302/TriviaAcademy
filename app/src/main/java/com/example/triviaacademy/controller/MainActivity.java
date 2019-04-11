package com.example.triviaacademy.controller;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.triviaacademy.R;
import com.example.triviaacademy.model.User;

/**
 * MainActivity
 * Populates the main content page and related fragment categories serving as a gateway to trivia games
 */
public class MainActivity extends AppCompatActivity implements TriviaCategory.OnFragmentInteractionListener {
    private User mUser;
    private EditText mNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUser = new User();
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        addTriviaCategories();
    }

    public void onClickPlay(View view){
        //Save user name
        String firstName = mNameInput.getText().toString();
        mUser.setFirstName(firstName);
        //Start game
        Intent new_game = new Intent(MainActivity.this, GameActivity.class);
        startActivity(new_game);
    }

    public void addTriviaCategories(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //TODO Use Category to pass in data
        int icon_science = R.drawable.ic_dinosaur;
        int icon_comp = R.drawable.ic_mario;
        TriviaCategory categoryOne =  TriviaCategory.newInstance("Science & Nature", icon_science);
        TriviaCategory categoryTwo =  TriviaCategory.newInstance("Computers & Tech", icon_comp);
        ft.add(R.id.fragment_category_one, categoryOne, "Science and Nature");
        ft.add(R.id.fragment_category_two, categoryTwo, "Computers and Tech");
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
