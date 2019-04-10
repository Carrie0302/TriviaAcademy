package com.example.triviaacademy.controller;

import android.content.Intent;
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

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
        TriviaCategoryBox categoryOne = new TriviaCategoryBox();
        ft.add(R.id.fragment_category_one, categoryOne, "Science and Nature");

        ft.add(R.id.fragment_category_two, new TriviaCategoryBox(), "Computers and Tech");
        ft.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
