package com.example.esemkalibrary.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static ArrayList<Book> books = new ArrayList<>();

    public static void addItem(Book book) {
        books.add(book);
    }

    public static void removeItem(Book book) {
        books.remove(book);
    }

    public static ArrayList<Book> getItems() {
        return books;
    }

}
