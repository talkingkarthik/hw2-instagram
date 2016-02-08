package com.example.k.hw1_instagram;

import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Komal on 2/7/2016.
 */
public class PostFragment extends DialogFragment{
    private static PostFragment ourInstance;
    private ArrayList<Comment> comments;
    private CommentAdapter commentAdapter;

    public static PostFragment getInstance(Context context, Post post) {
        if (ourInstance ==  null) {
           ourInstance = new PostFragment();
            ourInstance.comments = new ArrayList<Comment>();
            ourInstance.commentAdapter = new CommentAdapter(context, ourInstance.comments);
        }
        ourInstance.comments.clear();
        ourInstance.comments.addAll(post.getComments());
        ourInstance.commentAdapter.notifyDataSetChanged();
        return ourInstance;
    }

    public PostFragment() {

        //Deliberately Empty constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.post_fragment, container);

        ListView lv = (ListView) view.findViewById(R.id.commentList);
        lv.setAdapter(ourInstance.commentAdapter);

        return view;
    }


}
