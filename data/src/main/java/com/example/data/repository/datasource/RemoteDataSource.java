package com.example.data.repository.datasource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.data.entities.FlikrEntity;
import com.example.data.mappers.FlickrMapper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sundararaghavan on 7/24/2018.
 */

public class RemoteDataSource implements DataSource<List<FlikrEntity>> {
    private static final String TAG = RemoteDataSource.class.getSimpleName();
    private static final String API_KEY = "2a469c4c92c1d3261e6a747f20f8d5cc";
    private static final int PAGE_SIZE = 15;

    private static final String URL_SEARCH = "https://api.flickr.com/services/rest/"
            + "?format=json&nojsoncallback=1&api_key=%s&method=flickr.photos.search&tags=mode&per_page=%s&page=%d&text=%s";

    private final RequestQueue mQueue;
    private final FlickrMapper mObjMapper;
    private final MutableLiveData<String> mError = new MutableLiveData<>();
    private final MutableLiveData<List<FlikrEntity>> mDataApi = new MutableLiveData<>();

    public RemoteDataSource(Context appContext, FlickrMapper objMapper) {
        mQueue = Volley.newRequestQueue(appContext);
        mObjMapper = objMapper;
    }

    @Override
    public LiveData<List<FlikrEntity>> getDataStream() {
        return mDataApi;
    }

    @Override
    public LiveData<String> getErrorStream() {
        return mError;
    }

    @Override
    public void clearData() {

    }

    public void fetch(String searchText, int page) {
        String searchURL = String.format(URL_SEARCH, API_KEY, PAGE_SIZE, page, searchText);
        final JsonObjectRequest jsonObjReq =
                new JsonObjectRequest(Request.Method.GET, searchURL, null,
                        response -> {
                            Log.d(TAG, "Thread->" +
                                    Thread.currentThread().getName() + "\tGot some network response");
                            Log.d(TAG, "Thread->" + Thread.currentThread().getName() + "\n Response:" + response.toString());
                            final ArrayList<FlikrEntity> data = mObjMapper.mapJSONToEntity(response.toString());
                            mDataApi.setValue(data);
                        },
                        error -> {
                            Log.d(TAG, "Thread->" +
                                    Thread.currentThread().getName() + "\tGot network error");
                            mError.setValue(error.toString());
                        });

        mQueue.add(jsonObjReq);
    }


}
