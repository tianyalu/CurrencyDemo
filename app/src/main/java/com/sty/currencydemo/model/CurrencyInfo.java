package com.sty.currencydemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;

@Entity
public class CurrencyInfo implements Parcelable {
    //The db primary key1, Auto increment
    @Id
    @NameInDb("id")
    private long _id;
    //The db primary key2, for business logic
    @NameInDb("_id")
    private String id;
    //The display name of the currency
    private String name;
    //The display symbol
    private String symbol;

    public CurrencyInfo(String id, String name, String symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    protected CurrencyInfo(Parcel in) {
        _id = in.readLong();
        id = in.readString();
        name = in.readString();
        symbol = in.readString();
    }

    public static final Creator<CurrencyInfo> CREATOR = new Creator<CurrencyInfo>() {
        @Override
        public CurrencyInfo createFromParcel(Parcel in) {
            return new CurrencyInfo(in);
        }

        @Override
        public CurrencyInfo[] newArray(int size) {
            return new CurrencyInfo[size];
        }
    };

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "CurrencyInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(_id);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(symbol);
    }
}
