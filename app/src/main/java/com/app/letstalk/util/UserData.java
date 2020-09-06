package com.app.letstalk.util;

import android.app.Application;

public class UserData extends Application {
    private String name;
    private String UserId;

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserId() {
        return UserId;
    }

    private String status;
    private String profilePic;
    public static UserData instance;
    public static UserData getInstance(){
        if (instance==null)

            instance=new UserData();

        return instance;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public UserData() {
    }
}
