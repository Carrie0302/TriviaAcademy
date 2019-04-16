package com.example.triviaacademy.model;

/**
 * class User
 * Users has first name and can track score
 */
public class User {
    /**
     * Get User Name
     * @return name of user
     */
    public String getFirstName() {
        return mFirstName;
    }

    /**
     * Set User Name
     * @param mFirstName is name of user
     */
    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    private String mFirstName;
}
