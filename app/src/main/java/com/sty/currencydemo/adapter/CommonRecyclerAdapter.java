package com.sty.currencydemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: tian
 * @Date: 2021/2/28 16:24
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    protected Context mContext;
    protected List<T> mDataList;
    protected LayoutInflater mInflater;
    private int mLayoutId;

    public CommonRecyclerAdapter(Context context, List<T> dataList, int layoutId) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDataList = dataList;
        mLayoutId = layoutId;
    }

    public void setData(List<T> data){
        mDataList = data;
    }

    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonRecyclerViewHolder(mContext,LayoutInflater.from(mContext)
                .inflate(mLayoutId,parent,false));
    }

    @Override
    public void onBindViewHolder(final CommonRecyclerViewHolder holder, final int position) {
        convert(holder,position,mDataList.get(position));
        holder.onItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(holder,position,mDataList.get(position));
            }
        });
        holder.onItemLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClick(holder, position, mDataList.get(position));
                return true;
            }
        });
    }

    public abstract void convert(CommonRecyclerViewHolder holder,int position, T t);

    public abstract void onItemClick(CommonRecyclerViewHolder holder, int position,T t);

    public void onItemLongClick(CommonRecyclerViewHolder holder, int position, T t){};

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
