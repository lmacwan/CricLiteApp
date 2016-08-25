package com.leon.android.app.criclite.ui.drawer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 25/08/2016.
 */

public class Drawer implements Serializable {
    private static Drawer mInstance;
    private List<DrawerItem> mItems;

    private Drawer() {
    }

    public static Drawer getInstance() {
        if(mInstance == null) {
            mInstance = new Drawer();
        }
        return mInstance;
    }

    public void addDrawerItem(DrawerItem item) {
        if(mItems == null) {
            mItems = new ArrayList<DrawerItem>();
        }
        if(item != null && mItems.contains(item) == false) {
            mItems.add(item);
        }
    }

    public List<DrawerItem> getmItems() {
        return mItems;
    }
}
