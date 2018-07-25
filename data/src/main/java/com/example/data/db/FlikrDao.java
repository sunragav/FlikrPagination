package com.example.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.VisibleForTesting;


import com.example.data.entities.FlikrEntity;

import java.util.List;


@Dao
public interface FlikrDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFlikrs(List<FlikrEntity> flikrs);

    @Query("SELECT * FROM flikrs")
    LiveData<List<FlikrEntity>> getAllFlikrsLive();

    @Query("SELECT * FROM flikrs")
    List<FlikrEntity> getAllFlikrs();

    @Query("SELECT * FROM flikrs LIMIT :limit")
    LiveData<List<FlikrEntity>> getFlikrs(int limit);

    @Query("SELECT * FROM flikrs WHERE id=:id")
    LiveData<FlikrEntity> getFlikr(String id);

    @Query("DELETE FROM flikrs")
    void deleteAllFlikrs();


}

