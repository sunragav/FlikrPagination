package com.example.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.data.entities.FlikrEntity;


@Database(entities = {FlikrEntity.class}, version = 1, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {

    static private final String DATABASE_NAME = "flikrdb";
    private static RoomDb INSTANCE;

    public abstract FlikrDao flikrDao();

    public static RoomDb getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDb.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }

}
