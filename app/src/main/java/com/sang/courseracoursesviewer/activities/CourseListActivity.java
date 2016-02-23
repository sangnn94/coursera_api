package com.sang.courseracoursesviewer.activities;

import android.support.v4.app.Fragment;

import com.sang.courseracoursesviewer.fragments.CourseListFragment;

public class CourseListActivity extends SingleFragmentActivity {
    /*Host Activity of CourseListFragment*/
    @Override
    protected Fragment createFragment() {
        return CourseListFragment.newInstance();
    }
}
