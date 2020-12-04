package com.example.lab2_moya.Api;

import com.example.lab2_moya.Entities.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface INetwork {
    @GET("/comments")
    public Call<List<Comment>> getAllComments();

    @GET("/posts/{id}")
    public Call<Comment> getCommentWithID(@Path("id") int id);
}
