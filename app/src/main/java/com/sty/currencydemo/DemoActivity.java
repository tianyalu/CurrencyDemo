package com.sty.currencydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sty.currencydemo.interfaces.IFragmentCallback;
import com.sty.currencydemo.model.CurrencyInfo;

public class DemoActivity extends AppCompatActivity implements IFragmentCallback {
    private AppCompatButton btnLoadData;
    private AppCompatButton btnSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        initView();
        replaceFragment();
    }

    private void initView() {
        btnLoadData = findViewById(R.id.btn_load_data);
        btnSort = findViewById(R.id.btn_sort);
        btnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnLoadDataClicked();
            }
        });
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSortClicked();
            }
        });
    }

    private void onBtnLoadDataClicked() {

    }

    private void onBtnSortClicked() {

    }

    private void replaceFragment() {
        CurrencyListFragment fragment = CurrencyListFragment.newInstance();
        fragment.setFragmentCallback(this);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.cl_container, fragment)
                .commit();
    }

    @Override
    public void onItemClicked(CurrencyInfo currencyInfo) {
        Toast.makeText(this, currencyInfo.toString() + " is clicked", Toast.LENGTH_SHORT).show();
    }
}