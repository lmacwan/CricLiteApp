package com.leon.android.app.criclite;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.leon.android.app.criclite.ui.drawer.DrawerItem;

import java.util.List;

import layout.HomeFragment;
import layout.MatchesFragment;
import layout.PlayersFragment;
import layout.RankingsFragment;

public class DrawerActivity extends AppCompatActivity {

    private List<DrawerItem> mDrawableItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        initialize();

        prepareDrawerItems();

        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    private void initialize() {
        mDrawableItems = (List<DrawerItem>)getIntent().getSerializableExtra("drawerItemsList");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    item.setIcon(R.drawable.ic_menu);
                    mDrawerLayout.closeDrawer(Gravity.LEFT, true);
                }
                else {
                    item.setIcon(R.drawable.ic_clear);
                    mDrawerLayout.openDrawer(Gravity.LEFT, true);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void prepareDrawerItems() {
        // Set the adapter for the list view
        mDrawerList.setAdapter(new DrawerListAdapter(this, mDrawableItems));

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }

            /** Swaps fragments in the main content view */
            private void selectItem(int position) {
                // Create a new fragment and specify the planet to show based on position
                Fragment fragment = getFragmentByPosition(position);

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                // Highlight the selected item, update the title, and close the drawer
                mDrawerList.setItemChecked(position, true);
                setTitle(mDrawableItems.get(position).getItemTextResource());
                mDrawerLayout.closeDrawer(mDrawerList);
            }

            private Fragment getFragmentByPosition(int position) {
                DrawerItem item = mDrawableItems.get(position);
                if(item != null) {
                    switch(item.getItemTextResource()) {
                        case R.string.title_activity_home:
                            return HomeFragment.newInstance();
                        case R.string.title_activity_match:
                            return MatchesFragment.newInstance();
                        case R.string.title_activity_players:
                            return PlayersFragment.newInstance();
                        case R.string.title_activity_rankings:
                            return RankingsFragment.newInstance();
                        default:
                            break;
                    }
                }
                return null;
            }
        });
    }

    private class DrawerListAdapter extends ArrayAdapter<DrawerItem> {

        public DrawerListAdapter(Context context, List<DrawerItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.layout_drawer_list_item, null);
            }

            DrawerItem drawerItem = getItem(position);

            if (drawerItem != null) {
                ImageView picture = (ImageView) v.findViewById(R.id.drawerItemPicture);
                TextView text = (TextView) v.findViewById(R.id.drawerItemText);

                if (picture != null) {
                    picture.setImageDrawable(getDrawable(drawerItem.getItemPictureResource()));
                }

                if (text != null) {
                    text.setText(getString(drawerItem.getItemTextResource()));
                }
            }

            return v;
        }
    }
}
