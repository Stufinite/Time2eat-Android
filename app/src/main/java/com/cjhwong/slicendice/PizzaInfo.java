package com.cjhwong.slicendice;

import android.os.Parcel;
import android.os.Parcelable;

public class PizzaInfo implements Parcelable {
    public static final Creator<PizzaInfo> CREATOR = new Creator<PizzaInfo>() {
        @Override
        public PizzaInfo createFromParcel(Parcel in) {
            return new PizzaInfo(in);
        }

        @Override
        public PizzaInfo[] newArray(int size) {
            return new PizzaInfo[size];
        }
    };
    private String flavour;
    private boolean halfPie;
    private boolean made;

    protected PizzaInfo() {

    }

    protected PizzaInfo(Parcel in) {
        flavour = in.readString();
        halfPie = in.readByte() != 0;
        made = in.readByte() != 0;
    }

    public boolean isMade() {
        return made;
    }

    public void setMade(boolean made) {
        this.made = made;
    }

    public boolean isHalfPie() {
        return halfPie;
    }

    public void setHalfPie(boolean halfPie) {
        this.halfPie = halfPie;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(flavour);
        dest.writeByte((byte) (halfPie ? 1 : 0));
        dest.writeByte((byte) (made ? 1 : 0));
    }
}
