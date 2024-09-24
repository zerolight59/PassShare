package com.example.passshare;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PRE_NAME="session";
    String SESSION_KEY="session_user";

    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void saveSession(DBUsers user){
        String uname=user.getUname();
        editor.putString(SESSION_KEY,uname).commit();
    }

    public String getSession(){
        return sharedPreferences.getString(SESSION_KEY,"");
    }

    public void removeSession(){
        editor.putString(SESSION_KEY,"").commit();
    }
}
