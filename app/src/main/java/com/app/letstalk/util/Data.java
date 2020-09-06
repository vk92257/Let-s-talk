package com.app.letstalk.util;

import android.app.Application;

public class Data  {
    private String name;
    private String status;
    private String profilePic;
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static Data instance;
    public static Data getInstance(){
        if (instance==null)

            instance=new Data();

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

    public Data() {
    }
}
