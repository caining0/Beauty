package com.onlylady.beautyapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import java.util.List;


/**
 * create by cnn
 */
public class FragmentTabUtils implements RadioGroup.OnCheckedChangeListener {
    private List<Fragment> fragments;
    private RadioGroup rgs;
    private FragmentManager fragmentManager;
    private int fragmentContentId;
    private int currentTab;

    public FragmentTabUtils(RadioGroup rgs, FragmentManager fragmentManager, List<Fragment> fragments, int fragmentContentId) {
        this.fragments = fragments;
        this.rgs = rgs;
        this.fragmentManager = fragmentManager;
        this.fragmentContentId = fragmentContentId;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(fragmentContentId, fragments.get(0));
        ft.commit();
        rgs.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for (int i = 0; i < rgs.getChildCount(); i++) {
            if (rgs.getChildAt(i).getId() == checkedId) {
                Fragment fragment = fragments.get(i);
                FragmentTransaction ft = obtainFragmentTransaction(i);

                getCurrentFragment().onStop();
                if (fragment.isAdded()) {
                    fragment.onStart();
                } else {
                    ft.add(fragmentContentId, fragment);
                    ft.commit();
                }
                showTab(i);
            }
        }
    }

    private void showTab(int idx) {
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(idx);
            if (idx == i) {
                ft.show(fragment);

            } else {
                ft.hide(fragment);

            }
            ft.commit();
        }
        currentTab = idx; // 更新目标tab为当前tab

    }

    private FragmentTransaction obtainFragmentTransaction(int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
      /*  if (index > currentTab) {
            ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        } else {
            ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
        }*/
        return ft;
    }

    /*public int getCurrentTab() {
        return currentTab;
    }*/

    public Fragment getCurrentFragment() {
        return fragments.get(currentTab);
    }


}
