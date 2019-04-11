package com.example.triviaacademy.controller;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.example.triviaacademy.R;
import com.example.triviaacademy.model.User;

public class MainActivity extends AppCompatActivity implements TriviaCategoryBox.OnFragmentInteractionListener {
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
        TriviaCategoryBox categoryOne =  TriviaCategoryBox.newInstance("Science & Nature", icon_science);
        TriviaCategoryBox categoryTwo =  TriviaCategoryBox.newInstance("Computers & Tech", icon_comp);
        ft.add(R.id.fragment_category_one, categoryOne, "Science and Nature");
        ft.add(R.id.fragment_category_two, categoryTwo, "Computers and Tech");
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
