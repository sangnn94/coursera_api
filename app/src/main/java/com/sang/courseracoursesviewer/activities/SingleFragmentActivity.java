package com.sang.courseracoursesviewer.activities;

/**
 * Created by sang on 23/02/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.sang.courseracoursesviewer.R;
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment(); // Abstract class for all Activity want to host Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) { // Check and replace fragment
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}