package com.sty.currencydemo;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sty.currencydemo.adapter.CommonRecyclerAdapter;
import com.sty.currencydemo.adapter.CommonRecyclerViewHolder;
import com.sty.currencydemo.interfaces.IFragmentCallback;
import com.sty.currencydemo.interfaces.IMainViewCallback;
import com.sty.currencydemo.model.CurrencyInfo;
import com.sty.currencydemo.utils.ThreadPoolUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CurrencyListFragment extends Fragment implements IMainViewCallback{
    private View rootView;
    private IFragmentCallback fragmentCallback; //provide a hook of item click listener to the parent
    private CommonRecyclerAdapter<CurrencyInfo> adapter;
    private ArrayList<CurrencyInfo> A;
    private RecyclerView recyclerView;


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
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_currency_list, container, false);
            initView(rootView);
        }
        return rootView;
    }

    private void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerview);
    }

    @Override
    public void onLoadData(ArrayList<CurrencyInfo> A) {
        this.A = A;
        setListView(A);
    }

    // I don't know what it(Please show how to handle multi threading operation) means, so I just simply
    // sort these datas with length and alphabet sequence in child thread and update UI in main thread.
    @Override
    public void onSort() {
        ThreadPoolUtils.getMiscExecutor().execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                if(A != null && !A.isEmpty()) {
                    A.sort((c1, c2) ->
                            c1.getName().length() == c2.getName().length()
                                    ? c2.getName().charAt(0) - c1.getName().charAt(0)
                                    : c1.getName().length() - c2.getName().length()
                    );
                    if(getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setListView(A);
                            }
                        });
                    }
                }
            }
        });

    }

    private void setListView(List<CurrencyInfo> currencyInfoList) {
        if(adapter == null) {
            adapter = new CommonRecyclerAdapter<CurrencyInfo>(getActivity(), currencyInfoList, R.layout.item_curency_list) {
                @Override
                public void convert(CommonRecyclerViewHolder holder, int position, CurrencyInfo currencyInfo) {
                    String name = currencyInfo.getName();
                    if(!TextUtils.isEmpty(name)) {
                        holder.setText(R.id.tv_alphabet, name.substring(0, 1).toUpperCase());
                        holder.setText(R.id.tv_name, name);
                    }
                    holder.setText(R.id.tv_symbol, currencyInfo.getSymbol());
                }

                @Override
                public void onItemClick(CommonRecyclerViewHolder holder, int position, CurrencyInfo currencyInfo) {
                    if(fragmentCallback != null) {
                        // provide a hook of item click listener to the parent
                        fragmentCallback.onItemClicked(currencyInfo);
                    }
                }
            };
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }else {
            adapter.setData(currencyInfoList);
            adapter.notifyDataSetChanged();
        }
    }
}
