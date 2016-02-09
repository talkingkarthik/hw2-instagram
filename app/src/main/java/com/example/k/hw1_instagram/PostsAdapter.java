package com.example.k.hw1_instagram;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Komal on 2/5/2016.
 */
public class PostsAdapter extends ArrayAdapter<Post> {

    private PostFragment postFrag;
    FragmentManager fm;

    Transformation transformation;
    public PostsAdapter(Context context, ArrayList<Post> posts, FragmentManager fm ) {
        super(context, 0, posts);

        this.fm = fm;
        this.postFrag = new PostFragment ();

        transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(5)
                .cornerRadiusDp(100)
                .oval(false)
                .build();
    }

    private void showCommentsDialog(Post post) {
        PostFragment dialog = PostFragment.getInstance(getContext (),  post);
        dialog.show(this.fm,"comments");
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Post post = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post, parent, false);
        }
        // Lookup view for data population
        TextView username = (TextView) convertView.findViewById(R.id.name);
        TextView caption = (TextView) convertView.findViewById(R.id.caption);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        ImageView picView = (ImageView) convertView.findViewById(R.id.picView);
        TextView likesCount = (TextView) convertView.findViewById(R.id.likes);
        TextView lastLiked = (TextView) convertView.findViewById(R.id.lastLiked);


        // Populate the data into the template view using the data object
        username.setText(post.getUser());
        caption.setText(post.getCaption());
        likesCount.setText(post.getLikes().toString());
        lastLiked.setText(post.getLastLiked());

        Picasso.with(getContext()).load(post.getImgUrl()).placeholder(R.drawable.downloading).error(R.drawable.error).into(imageView);
        Picasso.with(getContext()).load(post.getUserImgUrl()).placeholder(R.drawable.downloading).error(R.drawable.error).transform(transformation).into(picView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentsDialog(post);
            }
        });


        Log.d("kartik", "image url: " + post.getImgUrl());
        // Return the completed view to render on screen
        return convertView;
    }
}