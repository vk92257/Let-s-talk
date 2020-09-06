package com.app.letstalk;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.letstalk.fragments.ChatsFragment;
import com.app.letstalk.fragments.FriendsFragment;
import com.app.letstalk.fragments.RequestsFragment;

class SectionPageAdapter extends FragmentPagerAdapter {
private  Context context;

    public SectionPageAdapter(Context ctx, @NonNull FragmentManager fm) {
        super(fm);
        this.context = ctx;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                RequestsFragment requestsFragment = new RequestsFragment();
                return requestsFragment;

            case 1:
                ChatsFragment chatsFragment = new ChatsFragment();
              return chatsFragment;

            case 2:
                FriendsFragment  friendsFragment = new FriendsFragment();
                return friendsFragment;

            default:
                return null;


        }

    }

  @Override
    public int getCount() {
        return 3;
    }
    public String getPageTitle(int position){
        switch (position) {
            case 0:
                return "REQUESTS";
            case 1:
                return "CHATS";
            case 2:
                return "FRIENDS";
            default:
                return null;
        }
    }
}

