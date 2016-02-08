package com.example.k.hw1_instagram;

import android.content.Context;
import android.graphics.Color;
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
 * Created by Komal on 2/7/2016.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {
    Transformation transformation;
    public CommentAdapter(Context context, ArrayList<Comment> comment) {
        super(context, 0, comment);

        transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(5)
                .cornerRadiusDp(100)
                .oval(false)
                .build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Comment comment = getItem(position);



        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_fragment_content, parent, false);
        }

        // Lookup view for data population
        TextView commentView = (TextView) convertView.findViewById(R.id.comment);
        ImageView picView = (ImageView) convertView.findViewById(R.id.commetPicView);

        commentView.setText(comment.getComment());
        Picasso.with(getContext()).load(comment.getPicUrl()).transform(transformation).into(picView);

        return convertView;
    }

}
