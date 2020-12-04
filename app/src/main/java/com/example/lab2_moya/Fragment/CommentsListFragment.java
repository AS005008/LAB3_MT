package com.example.lab2_moya.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.lab2_moya.Api.NetworkService;
import com.example.lab2_moya.Db.CommentDao;
import com.example.lab2_moya.Db.CommentsDatabase;
import com.example.lab2_moya.Entities.Comment;
import com.example.lab2_moya.MainActivity;
import com.example.lab2_moya.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsListFragment extends ListFragment {
    Comment[] comments;
    MyListAdapter adapter;
    CommentsDatabase db;
    boolean connection = true;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null) {
            setRetainInstance(true);
            db = MainActivity.getInstance().getDatabase();
            CommentDao commentDao = db.commentDao();
            // очистка сохраненной БД
//            for(Comment c : commentDao.getAll())
//                commentDao.delete(c);

            NetworkService.getInstance()
                    .getJSONApi()
                    .getAllComments()
                    .enqueue(new Callback<List<Comment>>() {
                        @Override
                        public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                            // если запрос на сервер завершился успешно - показать загруженный список
                            List<Comment> d = response.body();
                            comments = new Comment[d.size()];
                            d.toArray(comments);
                            adapter = new MyListAdapter(getActivity(), R.layout.comment, comments);
                            setListAdapter(adapter);

                            // кеширование объектов
                            for(Comment c : d){
                                if(commentDao.getCurrent(c.getId()).size() == 0){
                                    commentDao.insertAll(c);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Comment>> call, Throwable t) {
                            // если запрос на сервер завершился неудачно - попытаться загрузить данные из БД
                            List<Comment> _comments = commentDao.getAll();
                            //если есть сохраненные элементы - вывести их
                            if (_comments.size() > 0) {
                                comments = new Comment[_comments.size()];
                                _comments.toArray(comments);
                                adapter = new MyListAdapter(getActivity(), R.layout.comment, comments);
                                setListAdapter(adapter);
                            } else { // если сохраненных элементов нет - вывести сообщение
                                Comment comment = new Comment();
                                comment.setName("No data to display");
                                comments = new Comment[1];
                                comments[0] = comment;

                                connection = false;
                                adapter = new MyListAdapter(getActivity(), R.layout.empty_list, comments);
                                setListAdapter(adapter);
                            }
                        }
                    });
        }
        else {
            Gson gson = new Gson();
            comments = gson.fromJson(savedInstanceState.getString("CommentsList"),Comment[].class);
            adapter = new MyListAdapter(getActivity(), R.layout.comment, comments);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
        String json = gson.toJson(comments);
        outState.putString("CommentsList", json);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(!connection)
            return;
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle b = new Bundle();
        Gson gson = new Gson();
        b.putString("Comment", gson.toJson(comments[position]));
        detailsFragment.setArguments(b);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.listFragment, detailsFragment)
                .addToBackStack(null)
                .commit();
    }

    public class MyListAdapter extends ArrayAdapter {
        private Context context;
        int resourceId;

        public MyListAdapter(Context _context, int textViewResourceId, Comment[] items){
            super(_context, textViewResourceId, items);
            resourceId = textViewResourceId;
            context = _context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(resourceId, parent,
                    false);
            if(connection) {
                TextView title = (TextView) row.findViewById(R.id.commentName);
                TextView body = (TextView) row.findViewById(R.id.commentEmail);
                title.setText(comments[position].getName());
                body.setText(comments[position].getEmail());
            }
            return row;
        }
    }
}
