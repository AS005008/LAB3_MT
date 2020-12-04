package com.example.lab2_moya.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab2_moya.Entities.Comment;
import com.example.lab2_moya.R;
import com.google.gson.Gson;

public class DetailsFragment extends Fragment {
    Comment comment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            Bundle bundle = this.getArguments();
            Gson gson = new Gson();
            comment = gson.fromJson(bundle.getString("Comment"), Comment.class);
        }
        else {
            Gson gson = new Gson();
            comment = gson.fromJson(savedInstanceState.getString("Comment"), Comment.class);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
        outState.putString("Comment",gson.toJson(comment));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        TextView textView = v.findViewById(R.id.detailsEmail);
        textView.setText(comment.getEmail());
        textView = v.findViewById(R.id.detailsName);
        textView.setText(comment.getName());
        textView = v.findViewById(R.id.detailsBody);
        textView.setText(comment.getBody());
        return v;
    }
}