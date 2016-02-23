package com.sang.courseracoursesviewer.models;

import java.io.Serializable;

/**
 * Created by sang on 23/02/2016.
 */
public class Data implements Serializable {
    private String mCourseName;
    private String mCourseId;
    private String mCoursePhotoUrl;
    private String mCourseWorkload;
    private String mCourseDescription;

    public String getCourseDescription() {
        return mCourseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        mCourseDescription = courseDescription;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public void setCourseName(String courseName) {
        mCourseName = courseName;
    }

    public String getCourseId() {
        return mCourseId;
    }

    public void setCourseId(String courseId) {
        mCourseId = courseId;
    }

    public String getCoursePhotoUrl() {
        return mCoursePhotoUrl;
    }

    public void setCoursePhotoUrl(String coursePhotoUrl) {
        mCoursePhotoUrl = coursePhotoUrl;
    }

    public String getCourseWorkload() {
        return mCourseWorkload;
    }

    public void setCourseWorkload(String courseWorkload) {
        mCourseWorkload = courseWorkload;
    }
}
