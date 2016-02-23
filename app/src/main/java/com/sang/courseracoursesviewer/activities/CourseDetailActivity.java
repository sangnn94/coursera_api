package com.sang.courseracoursesviewer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sang.courseracoursesviewer.fragments.CourseDetailFragment;
import com.sang.courseracoursesviewer.models.Data;

/**
 * Created by sang on 23/02/2016.
 */
public class CourseDetailActivity extends SingleFragmentActivity {
    public static final String EXTRA = "com.sang.course_id";
    private Data mCourse;
    public static Intent newIntent(Context context, Data course){
        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra(EXTRA, course);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        mCourse = (Data)getIntent().getSerializableExtra(EXTRA);
        return CourseDetailFragment.newInstance(mCourse);
    }
}
