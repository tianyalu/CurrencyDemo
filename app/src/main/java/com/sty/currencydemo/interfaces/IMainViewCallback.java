package com.sty.currencydemo.interfaces;

import com.sty.currencydemo.model.CurrencyInfo;

import java.util.ArrayList;

public interface IMainViewCallback {

    //to load the data and display
    void onLoadData(ArrayList<CurrencyInfo> A);

    //For sorting currency list
    void onSort();
}
