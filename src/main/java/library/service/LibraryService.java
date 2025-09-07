package library.service;

import library.exception.*;
import library.model.Book;
import library.model.BookRent;
import library.model.PaymentStatus;
import library.model.User;
import library.repository.IRepository;
import library.util.Helper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LibraryService implements ILibraryService {
    private final IRepository repository;

    public LibraryService(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public User signup(User user) {
        return this.repository.saveUser(user);
    }

    @Override
    public Book addBook(Book book) {
        book.setAvailableCopies(book.getTotalCopies());
        return this.repository.saveBook(book);
    }

    @Override
    public List<Book> addBooks(List<Book> books) {
        List<Book> persistedBooks = new ArrayList<>();
        for (Book book : books) {
            persistedBooks.add(this.addBook(book));
        }
        return persistedBooks;
    }

    @Override
    public BookRent borrowBook(User user, Book book) {
        User persistedUser = this.repository.findUserById(user.getId());
        if (persistedUser == null) {
            throw new UserNotSignedUpException("User not signed up: " + user.getName());
        }
        Book persistedBook = this.repository.findBookById(book.getId());
        if (persistedBook == null || persistedBook.getAvailableCopies() < 1) {
            throw new CopiesNotAvailableException("Copies not available of book: " + book.getBookName());
        }
        persistedBook.setAvailableCopies(persistedBook.getAvailableCopies() - 1);
        this.repository.updateBook(persistedBook);
        BookRent bookRent = new BookRent();
        bookRent.setUser(persistedUser);
        bookRent.setBook(persistedBook);
        bookRent.setTransactionDate(Date.from(Instant.now()));
        bookRent.setIssuedPricePerDay(persistedBook.getRentPricePerDay());
        bookRent.setPaymentStatus(PaymentStatus.NOT_PAID);
        bookRent = this.repository.save(bookRent);
        return bookRent;
    }

    @Override
    public float getPaymentDetails(BookRent bookRent) {
        BookRent persistedBookRent = this.repository.findBookRentById(bookRent.getId());
        if (persistedBookRent == null) {
            throw new WrongBookRentIdException("Wrong Book Rent Id");
        }
        if (persistedBookRent.getPaymentStatus() == PaymentStatus.PAID) {
            throw new BookAlreadyReturnedException("Book already returned");
        }
        Date currentDate = Date.from(Instant.now());
        return persistedBookRent.getIssuedPricePerDay() * Helper.getDaysBetweenDates(currentDate, persistedBookRent.getTransactionDate());
    }

    @Override
    public void returnBook(BookRent bookRent, float paid) {
        BookRent persistedBookRent = this.repository.findBookRentById(bookRent.getId());
        if (persistedBookRent == null) {
            throw new WrongBookRentIdException("Wrong Book Rent Id");
        }
        if (persistedBookRent.getReturnDate() != null ||
                persistedBookRent.getPaymentStatus() == PaymentStatus.PAID) {
            throw new BookAlreadyReturnedException("Book already returned");
        }
        Date currentDate = Date.from(Instant.now());
        float paymentDue = Helper.getDaysBetweenDates(currentDate, persistedBookRent.getTransactionDate());
        if (paid < paymentDue) {
            throw new LessRentPaymentException("Less Rent Payment. Due: " + paymentDue);
        }
        persistedBookRent.setReturnDate(currentDate);
        persistedBookRent.setPaymentStatus(PaymentStatus.PAID);
        this.repository.update(persistedBookRent);
        Book book = this.repository.findBookById(persistedBookRent.getBook().getId());
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        this.repository.updateBook(book);
    }
}
