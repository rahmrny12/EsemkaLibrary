package com.example.esemkalibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.esemkalibrary.R;
import com.example.esemkalibrary.models.Book;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<Book> books;
    RecyclerViewOnClickListener listener;

    public CartAdapter(Context context, ArrayList<Book> books, RecyclerViewOnClickListener listener) {
        this.context = context;
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_card_vertical, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Book book = books.get(position);
        holder.name.setText(book.getName());
        holder.authors.setText(book.getAuthors());
        holder.available.setText(book.getAvailable());
        holder.btnRemoveFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClickListener(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView name, authors, available;
        private Button btnRemoveFromCart;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            authors = itemView.findViewById(R.id.authors);
            available = itemView.findViewById(R.id.available);
            btnRemoveFromCart = itemView.findViewById(R.id.btnRemoveFromCart);
        }
    }
}
