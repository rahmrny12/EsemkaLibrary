package com.example.esemkalibrary;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.esemkalibrary.adapter.BookAdapter;
import com.example.esemkalibrary.adapter.RecyclerViewOnClickListener;
import com.example.esemkalibrary.models.Book;
import com.example.esemkalibrary.services.AsyncCallBack;
import com.example.esemkalibrary.services.GetAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    ProgressBar progressBar;
    private String TAG = "DEBUG MAIN";
    RecyclerView recyclerView;
    Button btnToCart;
    BookAdapter booksAdapter;
    ArrayList<Book> books;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        btnToCart = view.findViewById(R.id.btnToCart);

        btnToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        new GetAsyncTask(getContext(), "Book?searchText=" + "", new AsyncCallBack() {
            @Override
            public void OnComplete(int statusCode, String result) {
                progressBar.setVisibility(View.GONE);
                if (statusCode == 200) {
                    try {
                        JSONArray array = new JSONArray(result);
                        books = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            Book book = new Book();
                            book.setId(object.getString("id"));
                            book.setName(object.getString("name"));
                            book.setAuthors(object.getString("authors"));
                            books.add(book);
                        }
                        booksAdapter = new BookAdapter(getActivity(), books, new RecyclerViewOnClickListener() {
                            @Override
                            public void OnClickListener(int position) {
                                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                                intent.putExtra("id", books.get(position).getId());
                                startActivity(intent);
                            }
                        });

                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        recyclerView.setAdapter(booksAdapter);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                } else if (statusCode == 401) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnLoading() {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).execute();

        return view;
    }

}