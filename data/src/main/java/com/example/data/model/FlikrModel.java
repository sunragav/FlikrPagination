package com.example.data.model;

/**
 * Created by Sundararaghavan on 7/24/2018.
 */

public class FlikrModel {
    public final String id;
    public final String secret;
  //  public final String imageUrl;
    public final String server;
    public final String farm;


    public FlikrModel(String id, String secret, String server,String farm) {
        this.secret = secret;
        this.id = id;
        this.server = server;
        this.farm = farm;

    }
}
