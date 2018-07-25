package com.example.data.repository;

import android.arch.lifecycle.LiveData;

import com.example.data.model.FlikrModel;

import java.util.List;

/**
 * Created by Sundararaghavan on 7/24/2018.
 */

public interface FlikrRepository {
    LiveData<List<FlikrModel>> getFlikrModels();
    LiveData<String> getErrorStream();
    void fetchData(String searchText, int page);
    void clearData();
}