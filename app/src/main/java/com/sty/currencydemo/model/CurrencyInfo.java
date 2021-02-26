package com.sty.currencydemo.model;

/**
 * @Author: tian
 * @UpdateDate: 2021/2/26 10:32 PM
 */
public class CurrencyInfo {
    //The db primary key
    private String id;
    //The display name of the currency
    private String name;
    //The display symbol
    private String symbol;

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
}
