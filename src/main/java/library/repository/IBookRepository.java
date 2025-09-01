package library.repository;

import library.model.Book;

import java.util.List;

public interface IBookRepository {
    Book saveBook(Book book);

    Book findBookById(int id);

    List<Book> findAllBooks();

    void updateBook(Book book);

}
