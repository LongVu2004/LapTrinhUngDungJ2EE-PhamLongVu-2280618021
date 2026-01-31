package com.example.bai2.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bai2.Models.Book;


@Service
public class BookService {
    private List<Book> books = new ArrayList<>(List.of(
        new Book(1, "To Kill a Mockingbird", "Harper Lee"),
        new Book(2, "1984", "George Orwell"),
        new Book(3, "The Great Gatsby", "F. Scott Fitzgerald"),
        new Book(4, "The Catcher in the Rye", "J.D. Salinger"),
        new Book(5, "Tôi thấy hoa vàng trên cỏ xanh", "Nguyễn Nhật Ánh")
    ));
    
    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst().orElse(null);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }

    public void updateBook(int id, Book updatedBook) {
        books.stream()
            .filter(book -> book.getId() == id)
            .findFirst()
            .ifPresent(book -> {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
            });
    }
}