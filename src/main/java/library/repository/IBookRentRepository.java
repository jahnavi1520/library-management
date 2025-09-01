package library.repository;

import library.model.BookRent;

public interface IBookRentRepository {
    BookRent save(BookRent bookRent);

    BookRent findBookRentById(int id);

    void update(BookRent bookRent);
}
