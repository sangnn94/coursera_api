package com.sang.courseracoursesviewer.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sang.courseracoursesviewer.R;
import com.sang.courseracoursesviewer.activities.CourseDetailActivity;
import com.sang.courseracoursesviewer.models.Data;
import com.sang.courseracoursesviewer.networks.FetchData;

import java.util.List;

/**
 * Created by sang on 23/02/2016.
 */
public class CourseDetailFragment extends Fragment {
    private static final String ARG_COURSE_ID = "courseId";
    private Data mCourse;
    private ImageView mImageView;
    private TextView mNameTextView;
    private TextView mDescription;

    public static CourseDetailFragment newInstance(Data course) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_COURSE_ID, course);
        CourseDetailFragment fragment = new CourseDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mCourse = (Data) getArguments().getSerializable(ARG_COURSE_ID);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_detail, container, false);
        mImageView = (ImageView) view.findViewById(R.id.course_image_view);
        mNameTextView = (TextView) view.findViewById(R.id.name_text_view);
        mDescription = (TextView) view.findViewById(R.id.description_text_view);
        mNameTextView.setText(mCourse.getCourseName());
        mDescription.setText(mCourse.getCourseDescription());
        Glide.with(getActivity()).load(mCourse.getCoursePhotoUrl())
                .centerCrop()
                .crossFade()
                .into(mImageView);
        return view;
    }

}
