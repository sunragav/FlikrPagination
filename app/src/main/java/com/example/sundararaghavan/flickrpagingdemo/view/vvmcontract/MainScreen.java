package com.example.sundararaghavan.flickrpagingdemo.view.vvmcontract;

import com.example.data.model.FlikrModel;

import java.util.List;

/**
 * Created by Sundararaghavan on 7/24/2018.
 */

public interface MainScreen {

    void updateData(List<FlikrModel> data);
    void setError(String msg);
}
