package com.example.data.mappers;

import android.support.annotation.NonNull;
import android.util.Log;


import com.example.data.entities.FlikrEntity;
import com.example.data.model.FlikrModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FlickrMapper extends ObjectMapper {
    private static final String TAG = FlickrMapper.class.getSimpleName();

    public ArrayList<FlikrEntity> mapJSONToEntity(String jsonStr) {
        ArrayList<FlikrEntity> data = null;
        try {
            JSONArray jsonArray = new JSONObject(jsonStr).getJSONObject("photos").getJSONArray("photo");
            data = readValue(jsonArray.toString(), new TypeReference<ArrayList<FlikrEntity>>() {
            });
        } catch (Exception e) {
            Log.d(TAG, "Thread->" + Thread.currentThread().getName() + "\t Exception while parsing and mapping the FlikrEntity from the service responce");
        }
        return data;
    }

    @NonNull
    public List<FlikrModel> mapEntityToModel(List<FlikrEntity> data) {
        final ArrayList<FlikrModel> listData = new ArrayList<>();
        FlikrEntity entity;
        for (int i = 0; i < data.size(); i++) {
            entity = data.get(i);
            listData.add(new FlikrModel(entity.getId(), entity.getSecret(),
                    entity.getServer(), entity.getFarm()));
        }

        return listData;
    }

    public String mapEntitiesToString(List<FlikrEntity> data) throws JsonProcessingException {
        return writeValueAsString(data);

    }
}
