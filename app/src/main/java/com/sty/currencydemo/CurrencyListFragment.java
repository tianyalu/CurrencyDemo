package com.sty.currencydemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sty.currencydemo.interfaces.IFragmentCallback;
import com.sty.currencydemo.interfaces.IMainViewCallback;
import com.sty.currencydemo.model.CurrencyInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Author: tian
 * @UpdateDate: 2021/2/26 10:28 PM
 */
public class CurrencyListFragment extends Fragment implements IMainViewCallback{
    private IFragmentCallback fragmentCallback;

    public static CurrencyListFragment newInstance() {
        CurrencyListFragment fragment = new CurrencyListFragment();
        return fragment;
    }

    public void setFragmentCallback(IFragmentCallback fragmentCallback) {
        this.fragmentCallback = fragmentCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onLoadData(List<CurrencyInfo> currencyInfoList) {

    }

    @Override
    public void onSort() {

    }
}
