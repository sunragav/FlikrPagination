package com.example.data.repository.datasource;

import android.arch.lifecycle.LiveData;


/**
 * Created by Sundararaghavan on 7/24/2018.
 */
public interface DataSource<T> {

    LiveData<T> getDataStream();
    LiveData<String> getErrorStream();
    void clearData();
}
