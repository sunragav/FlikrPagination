package com.example.sundararaghavan.flickrpagingdemo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.data.model.FlikrModel;
import com.example.data.repository.FlikrRepository;
import com.example.data.repository.FlikrRepositoryImpl;

import java.util.List;

/**
 * Created by Sundararaghavan on 7/24/2018.
 */

public class FlikrViewModel extends AndroidViewModel {
    private static final String TAG = FlikrViewModel.class.getSimpleName();
    private FlikrRepository flikrRepository;

    public LiveData<List<FlikrModel>> getFlikerModels() {
        return flikrRepository.getFlikrModels();
    }

    public LiveData<String> getErrorUpdates() {
        return flikrRepository.getErrorStream();
    }


    public FlikrViewModel(@NonNull Application application) {
        super(application);
        flikrRepository = FlikrRepositoryImpl.create(application);
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared() called");
        super.onCleared();
    }

    public void fetchData(String searchText, int page) {
        flikrRepository.fetchData(searchText, page);
    }

    @VisibleForTesting
    public FlikrViewModel(@NonNull Application application, FlikrRepositoryImpl repo) {
        super(application);
        this.flikrRepository = repo;
    }

    public void clearData() {
        flikrRepository.clearData();
    }
}

