package com.example.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.data.entities.FlikrEntity;
import com.example.data.mappers.FlickrMapper;
import com.example.data.model.FlikrModel;
import com.example.data.repository.datasource.LocalDataSource;
import com.example.data.repository.datasource.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Sundararaghavan on 7/24/2018.
 */

public class FlikrRepositoryImpl implements FlikrRepository {

    private static final String TAG = FlikrRepositoryImpl.class.getSimpleName();
    private final FlickrMapper mMapper;
    private ExecutorService mExecutor = Executors.newFixedThreadPool(5);
    private final RemoteDataSource mRemoteDataSource;
    private final LocalDataSource mLocalDataSource;
    private MediatorLiveData<List<FlikrModel>> mDataMerger = new MediatorLiveData<>();
    private MediatorLiveData<String> mErrorMerger = new MediatorLiveData<>();
    private final List<FlikrModel> dummy=new ArrayList<>();

    private FlikrRepositoryImpl(RemoteDataSource mRemoteDataSource, LocalDataSource mLocalDataSource, FlickrMapper mapper) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
        mMapper = mapper;
        mDataMerger.addSource(this.mRemoteDataSource.getDataStream(), entities ->
                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "mDataMerger\tmRemoteDataSource onChange invoked");

                        mLocalDataSource.writeData(entities);
                        if(entities==null || entities.size()==0)
                            mDataMerger.postValue(dummy);
                        //List<FlikrModel> list = mMapper.mapEntityToModel(entities);
                        //mDataMerger.postValue(list);


                    }
                })
        );
        mDataMerger.addSource(this.mLocalDataSource.getDataStream(), entities ->
                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "mDataMerger\tmLocalDataSource onChange invoked");
                        List<FlikrModel> models = mMapper.mapEntityToModel(entities);
                        mDataMerger.postValue(models);
                    }
                })

        );
        mErrorMerger.addSource(mRemoteDataSource.getErrorStream(), errorStr -> {
                    mErrorMerger.setValue(errorStr);
                    Log.d(TAG, "Network error -> fetching from LocalDataSource");
                    mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            List<FlikrEntity> entities = (mLocalDataSource.getAllFlikrs());
                            mDataMerger.postValue(mMapper.mapEntityToModel(entities));
                        }
                    });

                }
        );
        mErrorMerger.addSource(mLocalDataSource.getErrorStream(), errorStr -> mErrorMerger.setValue(errorStr));
    }

    public static FlikrRepository create(Context mAppContext) {
        final FlickrMapper mapper = new FlickrMapper();
        final RemoteDataSource remoteDataSource = new RemoteDataSource(mAppContext, mapper);
        final LocalDataSource localDataSource = new LocalDataSource(mAppContext);
        return new FlikrRepositoryImpl(remoteDataSource, localDataSource, mapper);
    }

    @VisibleForTesting
    public static FlikrRepositoryImpl createImpl(Context mAppContext) {
        final FlickrMapper mapper = new FlickrMapper();
        final RemoteDataSource remoteDataSource = new RemoteDataSource(mAppContext, mapper);
        final LocalDataSource localDataSource = new LocalDataSource(mAppContext);
        return new FlikrRepositoryImpl(remoteDataSource, localDataSource, mapper);
    }

    @Override
    public void fetchData(String searchText, int page) {
        mRemoteDataSource.fetch(searchText, page);
    }

    @Override
    public void clearData() {
        mLocalDataSource.clearData();
    }

    @Override
    public LiveData<List<FlikrModel>> getFlikrModels() {
        return mDataMerger;
    }

    @Override
    public LiveData<String> getErrorStream() {
        return mErrorMerger;
    }



    @VisibleForTesting
    public void insertAllFlikrs(List<FlikrEntity> entities) {
        mLocalDataSource.writeData(entities);
    }

    @VisibleForTesting
    public void deleteAllFlikrs() {
        mLocalDataSource.deleteAllFlikrs();
    }
}

