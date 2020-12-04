package com.example.lab2_moya.Db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.lab2_moya.Entities.Comment;

@Database(entities = {Comment.class}, version = 1)
public abstract class CommentsDatabase extends RoomDatabase {
    public abstract CommentDao commentDao();
}
