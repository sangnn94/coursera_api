package com.sang.courseracoursesviewer.networks;

import android.net.Uri;
import android.util.Log;

import com.sang.courseracoursesviewer.models.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sang on 23/02/2016.
 */
public class FetchData {
    private static final String TAG = "FetchData";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Data> fetchDatas(int position) { // position to fetch new data
        List<Data> datas = new ArrayList<>();
        List<String> partner = new ArrayList<>();
        try {
            String url = Uri.parse("https://api.coursera.org/api/courses.v1/")
                    .buildUpon()
                    .appendQueryParameter("start", String.valueOf(position))
                    .appendQueryParameter("limit", "25")
                    .appendQueryParameter("fields", "photoUrl,description,workload")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseData(datas, jsonBody);
        } catch (IOException e) {
            Log.e(TAG, "Failed to fetch data", e);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON", e);
        }
        return datas;
    }

    private void parseData(List<Data> list, JSONObject jsonBody) throws JSONException {
        JSONArray courseJsonArray = jsonBody.getJSONArray("elements");

        for (int i = 0; i < courseJsonArray.length(); i++) {
            JSONObject course = courseJsonArray.getJSONObject(i);
            Data data = new Data();
            data.setCourseId(course.getString("id"));
            data.setCourseName(course.getString("name"));
            data.setCourseWorkload(course.getString("workload"));
            data.setCoursePhotoUrl(course.getString("photoUrl"));
            data.setCourseDescription(course.getString("description"));
            list.add(data);
        }
    }
}
