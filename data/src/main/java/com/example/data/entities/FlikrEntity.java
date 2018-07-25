package com.example.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)

@Entity(tableName = "flikrs",
        indices = {@Index("id")})

public class FlikrEntity {
    //We are going to get a list of these entities from our api call - this entity is immutable
    @JsonProperty("id")
    @PrimaryKey
    @NonNull
    private String id="";

    @JsonProperty("farm")
    @ColumnInfo(name = "farm")
    private String farm;

    @JsonProperty("secret")
    @ColumnInfo(name = "secret")
    private String secret;

    @JsonProperty("server")
    @ColumnInfo(name = "server")
    private String server;

    @JsonIgnore
    @JsonProperty("id")
    @NonNull
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(@NonNull String id) {
        this.id = id;
    }

    @JsonProperty("server")
    public String getServer() {
        return server;
    }

    @JsonProperty("server")
    public void setServer(String server) {
        this.server = server;
    }

    @JsonProperty("secret")
    public String getSecret() {
        return secret;
    }

    @JsonProperty("secret")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @JsonProperty("farm")
    public String getFarm() {
        return farm;
    }

    @JsonProperty("farm")
    public void setFarm(String farm) {
        this.farm = farm;
    }


    @Override
    public String toString() {
        return "FlikrEntity{" +
                "id='" + id + '\'' +
                ", secret='" + secret + '\'' +
                ", farm='" + farm + '\'' +
                ", server='" + server + '\'' +
                '}';
    }

   /* @VisibleForTesting
    public FlikrEntity(String id, String name, @NonNull String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }*/
}
