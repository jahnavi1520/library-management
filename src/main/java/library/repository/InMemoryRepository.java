package library.repository;

import library.model.Book;
import library.model.BookRent;
import library.model.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository implements IBookRepository, IUserRepository, IBookRentRepository {
    List<User> users = new ArrayList<>();
    List<Book> books = new ArrayList<>();
    List<BookRent> bookRents = new ArrayList<>();

    @Override
    public User saveUser(User user) {
        int userId = 0;
        if (!this.users.isEmpty()) {
            userId = this.users.getLast().getId();
        }
        user.setId(userId + 1);
        this.users.add(user);
        return user;
    }

    @Override
    public User findUserById(int id) {
        for (User user : this.users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        return this.users;
    }

    @Override
    public Book saveBook(Book book) {
        int bookId = 0;
        if (!this.books.isEmpty()) {
            bookId = this.books.getLast().getId();
        }
        book.setId(bookId + 1);
        this.books.add(book);
        return book;
    }

    @Override
    public Book findBookById(int id) {
        for (Book book : this.books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    @Override
    public List<Book> findAllBooks() {
        return this.books;
    }

    @Override
    public BookRent save(BookRent bookRent) {
        int bookRentId = 0;
        if (!this.bookRents.isEmpty()) {
            bookRentId = this.users.getLast().getId();
        }
        bookRent.setId(bookRentId + 1);
        this.bookRents.add(bookRent);
        return bookRent;
    }

    @Override
    public BookRent findBookRentById(int id) {
        for (BookRent bookRent : this.bookRents) {
            if (bookRent.getId() == id) {
                return bookRent;
            }
        }
        return null;
    }

    @Override
    public void update(BookRent bookRent) {
        for (int i = 0; i < this.bookRents.size(); i++) {
            if (this.bookRents.get(i).getId() == bookRent.getId()) {
                this.bookRents.set(i, bookRent);
                return;
            }
        }
    }


    public void updateBook(Book book) {
        for (int i = 0; i < this.books.size(); i++) {
            if (this.books.get(i).getId() == book.getId()) {
                this.books.set(i, book);
                return;
            }
        }
    }
}
