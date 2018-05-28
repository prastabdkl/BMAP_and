package com.example.prastabdkl.bmap;

import com.google.gson.JsonObject;

/**
 * Created by prastab dhakal on 2/22/17.
 */

public class TokenAndId {
    private static TokenAndId tokenAndIdInstance;
    public static String token;
    public static int userId;
    public static boolean is_admin;

    public TokenAndId(String token, int userId){
        this.token = token;
        this.userId = userId;
    }

    private TokenAndId(){}

    public static synchronized TokenAndId getTokenAndIdInstance() {
        if (tokenAndIdInstance == null){
            tokenAndIdInstance = new TokenAndId();
        }
        return tokenAndIdInstance;
    }



    public String check_if_null(JsonObject personal_detail, String KEY){
        if(personal_detail.get(KEY).isJsonNull()){
            return null;
        }
        else {
            return personal_detail.get(KEY).getAsString();
        }
    }

    public void setIs_admin(boolean is_admin){
        this.is_admin = is_admin;
    }
}
