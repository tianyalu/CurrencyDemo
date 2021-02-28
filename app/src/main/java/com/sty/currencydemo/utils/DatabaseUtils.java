package com.sty.currencydemo.utils;

import android.util.Log;

import com.sty.currencydemo.db.ObjectBox;

import java.util.List;

import androidx.annotation.Nullable;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.Property;
import io.objectbox.TxCallback;

/**
 * @Author: tian
 * @Date: 2021/2/28 17:36
 */
public class DatabaseUtils {
    private static BoxStore mBoxStore = ObjectBox.get();

    public static <T> Box<T> getBox(Class<T> entityClass) {
        return mBoxStore.boxFor(entityClass);
    }

    /**
     * ObjectBox 保存单个实例
     * @param entityClass bean 的类型
     * @param bean 实例
     * @param isClearTable 是否清理表数据
     * @param <T> bean 类型
     */
    public static <T> void save(final Class<T> entityClass, final T bean, boolean isClearTable){
        if (isClearTable){
            //开启事务
            mBoxStore.runInTx(new Runnable() {
                @Override
                public void run() {
                    getBox(entityClass).removeAll();
                    getBox(entityClass).put(bean);
                }
            });
        }else {
            getBox(entityClass).put(bean);
        }
    }

    /**
     * ObjectBox 保存单个实例
     * 默认 isClearTable 为 false
     * @param entityClass bean 的类型
     * @param bean 实例
     * @param <T> bean 类型
     */
    public static <T> void save(final Class<T> entityClass, final T bean){
        save(entityClass,bean,false);
    }


    /**
     * ObjectBox,清空指定表的数据
     * @param entityClass 指定类型
     * @param <T> 类型
     */
    public static <T> void clearTable(final Class<T> entityClass){
        getBox(entityClass).removeAll();
    }

    /**
     * ObjectBox,删除
     * @param entityClass bean 类型
     * @param bean 需要删除的数据
     * @param <T> 类型
     */
    public static <T> void remove(final Class<T> entityClass, final T bean){
        getBox(entityClass).remove(bean);
    }

    public static <T> void remove(final Class<T> entityClass,final List<T> list){
        getBox(entityClass).remove(list);
    }

    public static <T> void remove(final Class<T> entityClass, Property property, String value){
        List val = query(entityClass,property,value);
        getBox(entityClass).remove(val);
    }

    /**
     * ObjectBox,批量保存
     * @param entityClass bean 类型
     * @param list list
     * @param isClearTable 是否清空原数据
     * @param <T> 类型
     */
    public static <T> void bulkSave(final Class<T> entityClass, final List<T> list, boolean isClearTable){
        if (isClearTable){
            //开启事务
            mBoxStore.runInTx(new Runnable() {
                @Override
                public void run() {
                    getBox(entityClass).removeAll();
                    getBox(entityClass).put(list);
                }
            });
        }else {
            getBox(entityClass).put(list);
        }
    }

    /**
     * ObjectBox,批量保存
     * @param entityClass   需要保存的数据类型
     * @param list  需要保存的数据列表
     * @param isClearTable  是否需要清空原有数据
     * @param listener  保存完成的回调
     * @param <T> 数据类型
     */
    public static <T> void bulkSave(final Class<T> entityClass, final List<T> list, final boolean isClearTable, final OnSaveFinishedListener listener){
        mBoxStore.runInTxAsync(new Runnable() {
            @Override
            public void run() {
                if (isClearTable){
                    getBox(entityClass).removeAll();
                }
                getBox(entityClass).put(list);
            }
        }, new TxCallback<Void>() {
            @Override
            public void txFinished(@Nullable Void aVoid, @Nullable Throwable throwable) {
                if (listener != null){
                    Log.e("on DatabaseUtil", "bulkSave txFinished: " +aVoid + "," + throwable);
                    listener.onFinished();
                }
            }
        });
    }

    public static <T> void bulkSave(final Class<T> entityClass, final List<T> list){
        bulkSave(entityClass,list,false);
    }

    /**
     * 查询指定表中的所有数据
     * @param entityClass bean 类型
     * @param <T> 类型
     * @return list
     */
    public static <T> List<T> findListAll(Class<T> entityClass){
        return getBox(entityClass).query().build().find();
    }

    /**
     * ObjectBox,根据条件查询数据
     * @param entityClass bean 类型
     * @param property 查询的字段
     * @param value 查询的值
     * @param <T> 类型
     * @return list
     */
    public static <T> List<T> query(Class<T> entityClass,Property property, String value){
        return getBox(entityClass).query().equal(property,value).build().find();
    }

    public static <T> List<T> query(Class<T> entityClass,Property property, int value){
        return getBox(entityClass).query().equal(property,value).build().find();
    }

    public static <T> T queryByField(Class<T> entityClass,Property property, String value){
        List<T> list = query(entityClass, property, value);
        if (!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public static interface OnSaveFinishedListener{
        void onFinished();
    }
}
