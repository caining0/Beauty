package com.onlylady.beautyapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.TextUtils;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.views.EasySlidingTabs;

import java.util.List;

/**
 * Description：
 * Created by：CaMnter
 * Time：2015-10-15 14:58
 */
public class TabsFragmentAdapter extends FragmentPagerAdapter implements EasySlidingTabs.TabsTitleInterface {

    private String[] titles;
    private List<Fragment> fragments;
    private int selector[] = {R.drawable.selector_home,R.drawable.selector_live,R.drawable.selector_find,R.drawable.selector_personal};
    public TabsFragmentAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public SpannableString getTabTitle(int position) {
        CharSequence title = this.getPageTitle(position);
        if (TextUtils.isEmpty(title)) return new SpannableString("");
        SpannableString spannableString = new SpannableString(title);
        return spannableString;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position < titles.length) {
            return titles[position];
        } else {
            return "";
        }
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = this.fragments.get(position);
        if (fragment != null) {
            return this.fragments.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getTabDrawableBottom(int position) {
        return 0;
    }

    @Override
    public int getTabDrawableLeft(int position) {
        return 0;
    }

    @Override
    public int getTabDrawableRight(int position) {
        return 0;
    }

    @Override
    public int getTabDrawableTop(int position) {
        return selector[position] ;
    }


    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return this.fragments.size();
    }

}