package com.sty.currencydemo.interfaces;

import com.sty.currencydemo.model.CurrencyInfo;

public interface IFragmentCallback {
    // provide a hook of item click listener to the parent
    void onItemClicked(CurrencyInfo currencyInfo);
}
