package com.example.binder.remote;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Moosphon
 * @date 2021/11/6 9:08 上午
 * @desc Book data class implement parcel.
 */
public class Book implements Parcelable {
    private int id;
    private String name;

    protected Book(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public Book(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }
}
