package com.cjhwong.slicendice;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class OrderInfo implements Parcelable {
    public static final Creator<OrderInfo> CREATOR = new Creator<OrderInfo>() {
        @Override
        public OrderInfo createFromParcel(Parcel in) {
            return new OrderInfo(in);
        }

        @Override
        public OrderInfo[] newArray(int size) {
            return new OrderInfo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String date, time;
    private int amount;
    private ArrayList<PizzaInfo> pizzas;
    private String pizzasString;
    private int pieces;
    private String name, phone, address, note;
    private boolean halfBox, paid, taken;

    protected OrderInfo() {
        id = "";
        date = "";
        time = "";
        amount = 0;
        pizzas = new ArrayList<>();
        pizzasString = "";
        pieces = 0;
        name = "";
        phone = "";
        address = "";
        note = "";
        halfBox = false;
        paid = false;
        taken = false;
    }

    protected OrderInfo(Parcel in) {
        id = in.readString();
        date = in.readString();
        time = in.readString();
        amount = in.readInt();
        pizzas = in.createTypedArrayList(PizzaInfo.CREATOR);
        pizzasString = in.readString();
        pieces = in.readInt();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        note = in.readString();
        halfBox = in.readByte() != 0;
        paid = in.readByte() != 0;
        taken = in.readByte() != 0;
        pizzasString = in.readString();
    }

    public String getPizzasString() {
        return pizzasString;
    }

    public void setPizzasString(String pizzasString) {
        this.pizzasString = pizzasString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ArrayList<PizzaInfo> getPizzas() {
        return pizzas;
    }

    public void setPizzas(ArrayList<PizzaInfo> pizzas) {
        this.pizzas = pizzas;
    }

    public boolean isHalfBox() {
        return halfBox;
    }

    public void setHalfBox(boolean halfBox) {
        this.halfBox = halfBox;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeInt(amount);
        dest.writeTypedList((List) pizzas);
        dest.writeString(pizzasString);
        dest.writeInt(pieces);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(note);
        dest.writeByte((byte) (halfBox ? 1 : 0));
        dest.writeByte((byte) (paid ? 1 : 0));
        dest.writeByte((byte) (taken ? 1 : 0));
        dest.writeString(pizzasString);
    }
}