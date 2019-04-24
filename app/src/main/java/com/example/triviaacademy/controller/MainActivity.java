package com.example.triviaacademy.controller;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import com.example.triviaacademy.R;
import com.example.triviaacademy.model.Category;
import com.example.triviaacademy.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MainActivity
 * Populates the main content page and related fragment categories serving as a gateway to trivia games
 */
public class MainActivity extends AppCompatActivity implements TriviaCategory.OnFragmentInteractionListener {
    private User mUser;
    private EditText mNameInput;
    private List<Category> mCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUser = new User();
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mCategories = new ArrayList<>();
        populateCategoriesList();
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
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        //TODO Ask user for number of questions
        int numberOfQuestions = 5;
        int fragCount = 4;
        List<Fragment> fragments = manager.getFragments();

        //On new screen, does not account for refresh
        if(fragments.size() == 0){
            for (int i = 0; i < fragCount; i++) {
                String tag = "triviaF" + i;
                TriviaCategory category = TriviaCategory.newInstance(mCategories.get(i), numberOfQuestions);
                if( i % 2 == 0) {
                    ft.add(R.id.trivia_fragment_container, category, tag);
                }
                else{
                    ft.add(R.id.trivia_fragment_containerB, category, tag);
                }
            }
        }



        ft.commit();
    }

    public void populateCategoriesList(){
        //Create categories
        Category catScience = new Category("Science and Nature", 17);
        Category catComp = new Category("Computers & Tech", 18);
        Category catArt = new Category("Art History", 25);
        Category catGeog = new Category("Geography", 22);

        //Assign icons
        catScience.setIcons( R.drawable.ic_bacteria, R.drawable.ic_bacteria_light );
        catComp.setIcons(R.drawable.ic_mario, R.drawable.ic_mario_light );
        catArt.setIcons(R.drawable.ic_shakespeare, R.drawable.ic_shakespeare_light);
        catGeog.setIcons(R.drawable.ic_big_ben, R.drawable.ic_big_ben_light);

        //Add to categories list
        mCategories.add(catScience);
        mCategories.add(catComp);
        mCategories.add(catArt);
        mCategories.add(catGeog);
        Collections.shuffle(mCategories);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
