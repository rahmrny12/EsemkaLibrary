package com.example.esemkalibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esemkalibrary.models.Book;
import com.example.esemkalibrary.models.Cart;
import com.example.esemkalibrary.services.AsyncCallBack;
import com.example.esemkalibrary.services.GetAsyncTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class BookDetailActivity extends AppCompatActivity {
    TextView id , name, authors, isbn, publisher, available, description;
    FloatingActionButton btnAddToCart;
    Book book = new Book();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        name = findViewById(R.id.name);
        authors = findViewById(R.id.authors);
        isbn = findViewById(R.id.isbn);
        publisher = findViewById(R.id.publisher);
        available = findViewById(R.id.available);
        description = findViewById(R.id.description);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        ProgressDialog progressBar = new ProgressDialog(BookDetailActivity.this);
        progressBar.setMessage("Loading...");

        Intent intent = getIntent();
        new GetAsyncTask(BookDetailActivity.this, "book/" + intent.getStringExtra("id"), new AsyncCallBack() {
            @Override
            public void OnComplete(int statusCode, String result) {
                progressBar.dismiss();
                try {
                    JSONObject json = new JSONObject(result);
                    book.setName(json.getString("name"));
                    book.setAuthors(json.getString("authors"));
                    book.setIsbn(json.getString("isbn"));
                    book.setPublisher(json.getString("publisher"));
                    book.setAvailable(json.getString("available"));
                    book.setDescription(json.getString("description"));
                    LoadBook();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            private void LoadBook() {
                name.setText(book.getName());
                authors.setText(book.getAuthors());
                isbn.setText(book.getIsbn());
                publisher.setText(book.getPublisher());
                available.setText(book.getAvailable());
                description.setText(book.getDescription());
            }

            @Override
            public void OnLoading() {
                progressBar.show();
            }
        }).execute();

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.addItem(book);
                Toast.makeText(BookDetailActivity.this, "Book added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}