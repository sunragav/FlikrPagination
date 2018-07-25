package com.example.data.repository.datasource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.VisibleForTesting;


import com.example.data.db.RoomDb;
import com.example.data.entities.FlikrEntity;

import java.util.List;


/**
 * Created by Sundararaghavan on 7/24/2018.
 */
public class LocalDataSource implements DataSource<List<FlikrEntity>>{
    private final RoomDb mDb;
    private final MutableLiveData<String> mError=new MutableLiveData<>();
    public LocalDataSource(Context mAppContext) {
        mDb= RoomDb.getDatabase(mAppContext);
    }
    @Override
    public LiveData<List<FlikrEntity>> getDataStream() {
        return mDb.flikrDao().getAllFlikrsLive();
    }
    @Override
    public LiveData<String> getErrorStream() {
        return mError;
    }

    @Override
    public void clearData() {
        new Thread() {
            @Override
            public void run() {
                mDb.flikrDao().deleteAllFlikrs();
            }
        }.start();

    }

    public void writeData(List<FlikrEntity> Flikrs) {
        try {
            mDb.flikrDao().insertFlikrs(Flikrs);
        }catch(Exception e)
        {
            e.printStackTrace();
            mError.postValue(e.getMessage());
        }
    }

    public List<FlikrEntity> getAllFlikrs() {
        return mDb.flikrDao().getAllFlikrs();
    }

    @VisibleForTesting
    public void deleteAllFlikrs()
    {
        mDb.flikrDao().deleteAllFlikrs();
    }
}
