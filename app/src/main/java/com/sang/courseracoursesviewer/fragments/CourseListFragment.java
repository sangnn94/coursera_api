package com.sang.courseracoursesviewer.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sang.courseracoursesviewer.activities.CourseDetailActivity;
import com.sang.courseracoursesviewer.networks.FetchData;
import com.sang.courseracoursesviewer.models.Data;
import com.sang.courseracoursesviewer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sang on 23/02/2016.
 */
public class CourseListFragment extends Fragment { // Fragment of all courses
    private RecyclerView mRecyclerView;
    private static final String TAG = "CourseListFragment";
    private List<Data> mFetchedDataList = new ArrayList<>();
    private boolean firstTimeFetch = true; // check if it's first time fetch data or not
    private AsyncTask mAsyncTask;
    public static CourseListFragment newInstance() {
        return new CourseListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.mAsyncTask = new FetchDataTask(0).execute();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_course_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (!recyclerView.canScrollVertically(1) && (mAsyncTask.getStatus() == AsyncTask.Status.FINISHED)) { // cant scroll down anymore
                    onScrolledToBottom(recyclerView.getAdapter().getItemCount()); // load more
                }
            }

            public void onScrolledToBottom(int lastPosition) {
                mAsyncTask = new FetchDataTask(lastPosition).execute(); // load more from last position in adapter
                Toast.makeText(getActivity(), "Loading next page....", Toast.LENGTH_SHORT).show();
            }

        });
        setUpAdapter();
        return view;
    }

    private void setUpAdapter() {
        if (isAdded()) //Check fragment was attached to host activity before set adapter
            mRecyclerView.setAdapter(new CourseAdapter(mFetchedDataList));

    }

    private class CourseHolder extends RecyclerView.ViewHolder implements CardView.OnClickListener {
        private CardView mCardView;
        private ImageView mCourseImageView;
        private TextView mCourseNameTextView;
        private TextView mWorkloadTextView;
        private Data mData;

        public CourseHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mCourseImageView = (ImageView) itemView.findViewById(R.id.course_image);
            mCourseNameTextView = (TextView) itemView.findViewById(R.id.course_name);
            mWorkloadTextView = (TextView) itemView.findViewById(R.id.workload);
            mCardView.setOnClickListener(this);
        }

        public void bindData(Data data) {
            mData = data; //Save currently course data to passing information when click event call
            mCourseNameTextView.setText(mData.getCourseName());
            mWorkloadTextView.setText(mData.getCourseWorkload());
            if (mData.getCoursePhotoUrl() != null) // Check photoUrl available
                Glide.with(getActivity()).load(mData.getCoursePhotoUrl())
                        .fitCenter()
                        .centerCrop()
                        .crossFade()
                        .into(mCourseImageView);
                // Glide libarary for better caching and load image
            else
                Glide.clear(mCourseImageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = CourseDetailActivity.newIntent(getActivity(), mData);
            startActivity(intent);
        }
    }

    public class CourseAdapter extends RecyclerView.Adapter<CourseHolder> {
        private List<Data> mDataList;


        public CourseAdapter(List<Data> list) {
            this.mDataList = list;
        }

        @Override
        public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.course_item, parent, false);
            CourseHolder holder = new CourseHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(CourseHolder holder, int position) {
            Data data = mDataList.get(position);
            holder.bindData(data);

        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }
    }

    private class FetchDataTask extends AsyncTask<Void, Void, List<Data>> {
        private int lastPosition;
        public FetchDataTask(int position){
            this.lastPosition = position;
        }

        @Override
        protected List<Data> doInBackground(Void... params) {
            Log.i(TAG, "Fetched 25 course from position " + lastPosition);
            return new FetchData().fetchDatas(lastPosition);
        }

        @Override
        protected void onPostExecute(List<Data> datas) {
            if (firstTimeFetch) { // first time fetch data
                mFetchedDataList = datas;
                setUpAdapter();
            } else {  // update new data to adapter
                mFetchedDataList.addAll(datas); // add new data to adapter
                mRecyclerView.getAdapter().notifyDataSetChanged(); // refresh adapter
            }
            firstTimeFetch = false;
        }
    }

}
