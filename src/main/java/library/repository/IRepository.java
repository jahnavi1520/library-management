package library.repository;

import library.model.Book;
import library.model.BookRent;
import library.model.User;

import java.util.List;

public interface IRepository {
    User saveUser(User user);

    User findUserById(int id);

    List<User> findAllUsers();

    Book saveBook(Book book);

    Book findBookById(int id);

    List<Book> findAllBooks();

    void updateBook(Book book);

    BookRent save(BookRent bookRent);

    BookRent findBookRentById(int id);

    void update(BookRent bookRent);
}
