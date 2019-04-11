package com.example.triviaacademy.model;

import android.graphics.drawable.Drawable;
import android.util.Pair;

public class Category {

    private String name;
    private Pair<Drawable, Drawable> icons;
    private int id;

    public Category( String name, Drawable icon_reg, Drawable icon_light, int id ){
        this.name =  name;
        this.icons = new Pair<>(icon_reg, icon_light);
        this.id = id;
    }

    public Drawable getDarkIcon(){
        return icons.first;
    }

    public Drawable getLightIcon(){
        return icons.second;
    }

    public int getId(){
        return id;
    }
}
