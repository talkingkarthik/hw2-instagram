package com.example.k.hw1_instagram;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Post> parray = new ArrayList<Post>();
    private PostsAdapter padapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Initate the listview
        padapter = new PostsAdapter (this, parray, getSupportFragmentManager() );
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(padapter);

       // parray.add(new Post ("http://i.imgur.com/DvpvklR.png", "test", "http://i.imgur.com/DvpvklR.png"));

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                try {
                    getPopular1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        try {
            getPopular1();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void getPopular1 () {
        String url = "https://api.instagram.com/v1/media/popular";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("client_id", "e05c462ebd86446ea48a5af73769b602");
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here

                ArrayList<Post> parrayNew = new ArrayList<Post>();
                Log.d("kartik", "rest success code: " + statusCode);
                try {

                    JSONArray arr = response.getJSONArray("data");
                    Log.d("kartik", "data len: " + arr.length());
                    for (int i =0; i < arr.length(); i++){
                        JSONObject item = (JSONObject) arr.get(i);

                        // Extract Image URL
                        JSONObject images = item.getJSONObject("images");
                        JSONObject urlObj = images.getJSONObject("standard_resolution");
                        String url = urlObj.getString("url");

                        //Exract User deails
                        JSONObject user = item.getJSONObject("user");
                        String name = user.getString("full_name");
                        String picUrl = user.getString("profile_picture");

                        //Extract Likes
                        JSONObject likes = item.getJSONObject("likes");
                        Integer count = likes.getInt("count");
                        JSONArray likers = likes.getJSONArray("data");
                        String lastLiked = "None";
                        if (likers.length() > 0)
                        {
                            lastLiked = ((JSONObject)likers.get(0)).getString("full_name");
                        }


                        JSONObject commentsObj = item.getJSONObject("comments");
                        JSONArray comments = commentsObj.getJSONArray("data");
                        ArrayList<Comment> postComments = new ArrayList<Comment>();

                        for (int j=0; j < comments.length(); j++) {

                            JSONObject comment = null;
                            comment = (JSONObject)comments.get(j);
                            String text = comment.getString("text");
                            JSONObject from = comment.getJSONObject("from");
                            String commenter_pic = from.getString("profile_picture");
                            Comment postComment = new Comment(commenter_pic, text);
                            postComments.add(postComment);


                        }

                        parrayNew.add(new Post(url, name, picUrl, count, lastLiked, postComments));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
                parray.removeAll(parray);
                parray.addAll(parrayNew);
                padapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("kartik", "rest fail code: " + statusCode);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
