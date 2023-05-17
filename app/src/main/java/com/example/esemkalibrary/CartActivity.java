package com.example.esemkalibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esemkalibrary.adapter.BookAdapter;
import com.example.esemkalibrary.adapter.CartAdapter;
import com.example.esemkalibrary.adapter.RecyclerViewOnClickListener;
import com.example.esemkalibrary.models.Book;
import com.example.esemkalibrary.models.Cart;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView labelEmpty;
    CartAdapter adapter;
    ArrayList<Book> books;

    EditText dateFrom, dateTo;
    Calendar calendar = Calendar.getInstance();
    Calendar calendarTo = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dateFrom = findViewById(R.id.dateFrom);
        dateTo = findViewById(R.id.dateTo);

        DatePickerDialog.OnDateSetListener setDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendarTo.set(Calendar.YEAR, year);
                calendarTo.set(Calendar.MONTH, month);
                calendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth + 3);
                updateDateFrom();
            }

            private void updateDateFrom() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                dateFrom.setText(dateFormat.format(calendar.getTime()));
                dateTo.setText(dateFormat.format(calendarTo.getTime()));
            }
        };

        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CartActivity.this, setDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        LoadBooks();
    }

    private void LoadBooks() {
        labelEmpty = findViewById(R.id.labelEmpty);
        recyclerView = findViewById(R.id.recyclerView);
        books = Cart.getItems();
        if (books.size() == 0) {
            labelEmpty.setVisibility(View.VISIBLE);
        }
        adapter = new CartAdapter(CartActivity.this, books, removeBook());
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    private RecyclerViewOnClickListener removeBook() {
        return new RecyclerViewOnClickListener() {
            @Override
            public void OnClickListener(int position) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CartActivity.this);
                alert.setMessage("Are you sure to remove this book from cart?");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cart.removeItem(books.get(position));
                        LoadBooks();
                        Toast.makeText(CartActivity.this, "Book removed.", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        };
    }
}