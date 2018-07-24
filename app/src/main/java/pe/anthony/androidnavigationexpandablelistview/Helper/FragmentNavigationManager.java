package pe.anthony.androidnavigationexpandablelistview.Helper;



import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import pe.anthony.androidnavigationexpandablelistview.BuildConfig;
import pe.anthony.androidnavigationexpandablelistview.Fragments.FragmentContent;
import pe.anthony.androidnavigationexpandablelistview.Interface.NavigationManager;
import pe.anthony.androidnavigationexpandablelistview.MainActivity;
import pe.anthony.androidnavigationexpandablelistview.R;

/**
 * Created by ANTHONY on 18/02/2018.
 */

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager mInstance;

    private FragmentManager mFragmentManager;
    private MainActivity mainActivity;

    public static FragmentNavigationManager getmInstance(MainActivity mainActivity){
        if (mInstance == null)
            mInstance = new FragmentNavigationManager();
        mInstance.configure(mainActivity);
        return mInstance;
    }

    private void configure(MainActivity main){
        mainActivity = main;
        mFragmentManager = mainActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragment(String title) {
        showFragment(FragmentContent.newInstance(title),false);
    }


    public void showFragment(Fragment fragmentContent, boolean b) {
        FragmentManager fm = mFragmentManager;
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.container, fragmentContent);
        ft.addToBackStack(null);
        if (b || !BuildConfig.DEBUG)
            ft.commitAllowingStateLoss();
        else
            ft.commit();
        fm.executePendingTransactions();
    }

}
