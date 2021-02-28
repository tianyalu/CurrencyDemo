package com.sty.currencydemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;
import com.sty.currencydemo.adapter.CommonRecyclerAdapter;
import com.sty.currencydemo.common.Constants;
import com.sty.currencydemo.interfaces.IFragmentCallback;
import com.sty.currencydemo.interfaces.IMainViewCallback;
import com.sty.currencydemo.model.CurrencyInfo;
import com.sty.currencydemo.utils.DatabaseUtils;
import com.sty.currencydemo.utils.ThreadPoolUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class DemoActivity extends AppCompatActivity implements IFragmentCallback {
    private static final String BUNDLE_KEY = "currency_list";
    // button to load the data and display
    private AppCompatButton btnLoadData;
    // button for sorting currency list
    private AppCompatButton btnSort;
    private static CurrencyListFragment fragment;
    private MyHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        initDataToDb();
        initView();
        replaceFragment();
    }

    private void initDataToDb() {
        ThreadPoolUtils.getMiscExecutor().execute(new Runnable() {
            @Override
            public void run() {
                //Let's assume this data is from server
                ArrayList<CurrencyInfo> dataList = new Gson().fromJson(Constants.DATA,
                        new TypeToken<ArrayList<CurrencyInfo>>(){}.getType());
                DatabaseUtils.bulkSave(CurrencyInfo.class, dataList, true);
            }
        });
    }

    private void initView() {
        btnLoadData = findViewById(R.id.btn_load_data);
        btnSort = findViewById(R.id.btn_sort);
        mHandler = new MyHandler(this);

        RxView.clicks(btnLoadData)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        onBtnLoadDataClicked();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //deal with concurrency issue when do sorting (fast double click of sorting button)
        RxView.clicks(btnSort)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        onBtnSortClicked();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void onBtnLoadDataClicked() {
        ThreadPoolUtils.getMiscExecutor().execute(new Runnable() {
            @Override
            public void run() {
                //Get data from database
                ArrayList<CurrencyInfo> dataList = (ArrayList<CurrencyInfo>) DatabaseUtils.findListAll(CurrencyInfo.class);

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("currency_list", dataList);
                Message message = mHandler.obtainMessage();
                message.setData(bundle);
                mHandler.sendMessage(message);
            }
        });
    }

    private void onBtnSortClicked() {
        if(fragment != null){
            ((IMainViewCallback) fragment).onSort();
        }
    }

    private void replaceFragment() {
        fragment = CurrencyListFragment.newInstance();
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

    private static class MyHandler extends Handler {
        //WeakReference
        private final WeakReference<DemoActivity> mActivty;
        public MyHandler(DemoActivity activity){
            mActivty =new WeakReference<DemoActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            DemoActivity activity = mActivty.get();
            super.handleMessage(msg);
            if(activity!=null){
                //Provide Currency List A of CurrencyInfo to CurrencyListFragment
                if(fragment != null){
                    Bundle bundle = msg.getData();
                    ArrayList<CurrencyInfo> dataList = bundle.getParcelableArrayList(BUNDLE_KEY);
                    if(dataList != null && !dataList.isEmpty()) {
                        ((IMainViewCallback) fragment).onLoadData(dataList);
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}