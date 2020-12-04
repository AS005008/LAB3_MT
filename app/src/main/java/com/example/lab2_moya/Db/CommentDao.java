package com.example.lab2_moya.Db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lab2_moya.Entities.Comment;

import java.util.List;

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comments")
    List<Comment> getAll();

    @Query("SELECT * FROM comments WHERE id=:id")
    List<Comment> getCurrent(Integer id);

    @Insert
    void insertAll(Comment... movies);

    @Delete
    void delete(Comment movie);
}
