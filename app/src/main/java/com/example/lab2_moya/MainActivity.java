package com.example.lab2_moya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.example.lab2_moya.Db.CommentsDatabase;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;
    private CommentsDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        database = Room.databaseBuilder(getApplicationContext(), CommentsDatabase.class, "comments")
                .allowMainThreadQueries()
                .build();
    }

    public static MainActivity getInstance(){
        return instance;
    }
    public CommentsDatabase getDatabase(){
        return database;
    }
}