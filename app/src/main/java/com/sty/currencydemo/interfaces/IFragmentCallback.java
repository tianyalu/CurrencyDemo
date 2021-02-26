package com.sty.currencydemo.interfaces;

import com.sty.currencydemo.model.CurrencyInfo;

/**
 * @Author: tian
 * @UpdateDate: 2021/2/26 10:31 PM
 */
public interface IFragmentCallback {
    void onItemClicked(CurrencyInfo currencyInfo);
}
