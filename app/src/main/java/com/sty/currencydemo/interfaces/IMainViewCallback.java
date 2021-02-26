package com.sty.currencydemo.interfaces;

import com.sty.currencydemo.model.CurrencyInfo;

import java.util.List;

/**
 * @Author: tian
 * @UpdateDate: 2021/2/26 10:34 PM
 */
public interface IMainViewCallback {

    void onLoadData(List<CurrencyInfo> currencyInfoList);

    void onSort();
}
