package com.leon.android.app.criclite.ui.drawer;

import java.io.Serializable;

/**
 * Created by admin on 24/08/2016.
 */

public class DrawerItem implements Serializable {
    private int mResPictire;
    private int mResText;

    public DrawerItem(int resPictire, int resText) {
        this.mResPictire = resPictire;
        this.mResText = resText;
    }

    public int getItemPictureResource() {
        return mResPictire;
    }

    public int getItemTextResource() {
        return mResText;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass()
                && obj instanceof DrawerItem
                &&  ((DrawerItem) obj).getItemTextResource() == this.mResText;
    }
}
