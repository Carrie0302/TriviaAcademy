package com.example.triviaacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * class Category
 * Is a category of study that includes an id and header
 * as well as associated icons
 */
public class Category implements Parcelable {

    /**
     * Constructor Category
     *
     * @param name header title
     * @param id   associated with API call
     */
    public Category(String name, int id) {
        this.mName = name;
        this.mId = id;
        this.mIconsOn = 0;
        this.mIconsOff = 0;
    }

    /**
     * Allow Category to be parsed to another component
     *
     * @param in parcel
     */
    protected Category(Parcel in) {
        mName = in.readString();
        mIconsOn = in.readInt();
        mIconsOff = in.readInt();
        mId = in.readInt();
    }

    /**
     * Generates instances of the Parcelable Category from a Parcel
     * for passing across Activities
     */
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    /**
     * Set the icons for on and off
     *
     * @param icon_reg   icon id from R.drawable associated with on icon
     * @param icon_light icon id from R.drawable associated with off icon
     */
    public void setIcons(int icon_reg, int icon_light) {
        this.mIconsOn = icon_reg;
        this.mIconsOff = icon_light;
    }

    /**
     * Get on icon
     *
     * @return icon id from R.drawable associated with on icon
     */
    public Integer getDarkIcon() {
        return mIconsOn;
    }

    /**
     * Get off icon
     *
     * @return icon id from R.drawable associated with off icon
     */
    public Integer getLightIcon() {
        return mIconsOff;
    }

    /**
     * Get id associated with API call
     *
     * @return integer id
     */
    public int getId() {
        return mId;
    }

    /**
     * Get name
     *
     * @return string name of the category
     */
    public String getName() {
        return mName;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation.
     *
     * @return 0 means no special object types marshaled
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write all object members into parcel objects for parsing
     *
     * @param dest  the Parcel in which the object should be written.
     * @param flags additional flags about how the object should be written.
     *              May be 0 or PARCELABLE_WRITE_RETURN_VALUE. Value is either
     *              0 or a combination of PARCELABLE_WRITE_RETURN_VALUE, and
     *              android.os.Parcelable.PARCELABLE_ELIDE_DUPLICATES
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mIconsOn);
        dest.writeInt(mIconsOff);
        dest.writeInt(mId);
    }

    private String mName;
    private int mIconsOn;
    private int mIconsOff;
    private int mId;
}
