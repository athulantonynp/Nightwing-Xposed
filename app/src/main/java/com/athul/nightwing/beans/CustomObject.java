package com.athul.nightwing.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by athul on 19/8/17.
 */

public class CustomObject extends Object implements Parcelable  {

    public List<Object> tiles;


    protected CustomObject(Parcel in) {
    }

    public static final Creator<CustomObject> CREATOR = new Creator<CustomObject>() {
        @Override
        public CustomObject createFromParcel(Parcel in) {
            return new CustomObject(in);
        }

        @Override
        public CustomObject[] newArray(int size) {
            return new CustomObject[size];
        }
    };

    public CustomObject() {

    }

    public List<Object> getTiles() {
        return tiles;
    }

    public void setTiles(List<Object> tiles) {
        this.tiles = tiles;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
