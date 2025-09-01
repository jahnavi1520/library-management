package library.service;

import library.model.Book;
import library.model.BookRent;
import library.model.User;

import java.util.List;

public interface ILibraryService {
    User signup(User user);

    Book addBook(Book book);

    List<Book> addBooks(List<Book> books);

    BookRent borrowBook(User user, Book book);

    float getPaymentDetails(BookRent bookRent);

    void returnBook(BookRent bookRent, float rent);
}
