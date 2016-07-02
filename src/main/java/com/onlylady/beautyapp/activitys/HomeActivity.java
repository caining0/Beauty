package com.onlylady.beautyapp.activitys;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.adapters.TabsFragmentAdapter;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.configs.EventBusC;
import com.onlylady.beautyapp.fragments.FindFragment;
import com.onlylady.beautyapp.fragments.HomeFragment;
import com.onlylady.beautyapp.fragments.LiveFragment;
import com.onlylady.beautyapp.fragments.PersonalFragment;
import com.onlylady.beautyapp.utils.DialogUtils;
import com.onlylady.beautyapp.utils.NetWorkState;
import com.onlylady.beautyapp.views.EasySlidingTabs;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.sharesdk.framework.ShareSDK;

public class HomeActivity extends BaseActivity {
    @Bind(R.id.easyslidingtabs)
    EasySlidingTabs easySlidingTabs;
    @Bind(R.id.easy_vp)
    ViewPager easyVp;
    //    public static  int GOTOWHITCH =0;//0 1 ,2 ,3 分别是第几页
    public static boolean isFocus = false;
    private int livecount = 0;


    private TabsFragmentAdapter adapter;
    List<Fragment> fragments = new ArrayList<>();


    @Override
    public boolean useEventbus() {
        return true;
    }

    @Override
    public void createview() {
        ShareSDK.initSDK(this);
        setContentView(R.layout.activity_home);
    }



    @Override
    public void initlisener() {
        NetWorkState.getInstance().registReceiver(this);
    }


    @Override
    public void initData() {
        fragments.add(new HomeFragment());

        fragments.add(new LiveFragment());
        fragments.add(new FindFragment());
        fragments.add(new PersonalFragment());
        easyVp.setOffscreenPageLimit(4);
        this.adapter = new TabsFragmentAdapter(this.getSupportFragmentManager(), Configs.TITLES, this.fragments);
        this.easyVp.setAdapter(this.adapter);
        this.easySlidingTabs.setViewPager(this.easyVp);
        easySlidingTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1 && livecount == 0) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        isFocus = true;
    }


//    private long preTime;
//    private static final long TWO_SECOND = 2 * 1000;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            long currentTime = new Date().getTime();
//            if ((currentTime - preTime) > TWO_SECOND) {
//                Toast.makeText(this, "再按一次返回键退出应用",
//                        Toast.LENGTH_SHORT).show();
//                preTime = currentTime;
//                return true;
//            }

            DialogUtils.getInstance().quite(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK();
        NetWorkState.getInstance().unregistReceiver(this);
    }

    public void onEvent(EventBusC c) {
        if (c.getFrom() == EventBusC.GOLIVE) {
            easyVp.setCurrentItem(1);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        isFocus =false;
    }
}
