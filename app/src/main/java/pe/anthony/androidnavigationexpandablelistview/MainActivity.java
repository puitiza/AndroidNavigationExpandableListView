package pe.anthony.androidnavigationexpandablelistview;

import android.app.ExpandableListActivity;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import pe.anthony.androidnavigationexpandablelistview.Adapter.CustomExpandableListAdapter;
import pe.anthony.androidnavigationexpandablelistview.Helper.FragmentNavigationManager;
import pe.anthony.androidnavigationexpandablelistview.Interface.NavigationManager;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mAcitivityTitle;
    private String[] items;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String,List<String>> lstChild;
    private NavigationManager navigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init View
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mAcitivityTitle = getTitle().toString();
        expandableListView = findViewById(R.id.navList);
        navigationManager = FragmentNavigationManager.getmInstance(this);
        initItems();
        View ListHearderView = getLayoutInflater().inflate(R.layout.nav_header,null,false);
        expandableListView.addHeaderView(ListHearderView);
        getData();
        addDrawerItems();
        setupDrawer();
        if(savedInstanceState == null){
            selectFirstItemAsDefault();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("EDMTDev");
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectFirstItemAsDefault() {
        if(navigationManager != null){
            String firstItem = lstTitle.get(0);
            navigationManager.showFragment(firstItem);
            getSupportActionBar().setTitle(firstItem);
        }
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle("EDMTDev");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                getSupportActionBar().setTitle(mAcitivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void addDrawerItems() {
        adapter = new CustomExpandableListAdapter(this,lstTitle,lstChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(lstTitle.get(groupPosition).toString());
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                getSupportActionBar().setTitle("EDMTDev");
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               String selectedItem = ((List)(lstChild.get(lstTitle.get(groupPosition)))).get(childPosition).toString();
               getSupportActionBar().setTitle(selectedItem);

               /*Con ese IF puedes exactamente controlar el evento al hacer click en cualquiera de los 3 hijos*/
//               if(items[0].equals(lstTitle.get(groupPosition))){
                    navigationManager.showFragment(selectedItem);
//               }else {
//                   throw new IllegalArgumentException("No support fragment");
//               }
               mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void getData() {
        List<String> title = Arrays.asList("Android Programing","Xamarin Programming","iOS Programing");
        List<String> childitem = Arrays.asList("Beginner","Intermediate","Advanced","Professional");

        lstChild = new TreeMap<>();
        lstChild.put(title.get(0),childitem);
        lstChild.put(title.get(1),childitem);
        lstChild.put(title.get(2),childitem);

        lstTitle = new ArrayList<>(lstChild.keySet());
    }

    private void initItems() {
        items = new String[]{"Android Programing","Xamarin Programming","iOS Programing"};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
